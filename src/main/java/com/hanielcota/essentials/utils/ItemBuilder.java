package com.hanielcota.essentials.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Builder class for creating customized {@link ItemStack} instances.
 * <p>
 * This class provides a fluent interface to configure item properties such as
 * name, lore, enchantments, skull textures, and leather armor colors.
 * </p>
 */
@Accessors(chain = true)
public class ItemBuilder {

    private static final Logger LOGGER = Logger.getLogger(ItemBuilder.class.getName());
    private static final Cache<String, GameProfile> PROFILE_CACHE = Caffeine.newBuilder()
            .maximumSize(100)
            .build();

    @NonNull
    private final Material material;
    private int amount = 1;
    private short durability = 0;
    private String name;
    private List<String> lore;
    private final Map<Enchantment, Integer> enchantments = new HashMap<>();
    private String skullOwner;
    private Color color;
    private String customSkullTexture;

    /**
     * Constructs an {@code ItemBuilder} with the specified material.
     *
     * @param material the material for the item; must not be null.
     */
    public ItemBuilder(@NonNull Material material) {
        this.material = material;
    }

    /**
     * Sets the amount for the item.
     *
     * @param amount the quantity of the item; must be greater than zero.
     * @return the current {@code ItemBuilder} instance.
     */
    public ItemBuilder amount(int amount) {
        if (amount > 0) {
            this.amount = amount;
        }
        return this;
    }

    /**
     * Sets the durability for the item.
     *
     * @param durability the durability value.
     * @return the current {@code ItemBuilder} instance.
     */
    public ItemBuilder durability(short durability) {
        this.durability = durability;
        return this;
    }

    /**
     * Sets the display name for the item.
     *
     * @param name the display name; must not be null or empty.
     * @return the current {@code ItemBuilder} instance.
     */
    public ItemBuilder name(@NonNull String name) {
        if (!name.trim().isEmpty()) {
            this.name = name;
        }
        return this;
    }

    /**
     * Sets the lore (description) for the item.
     *
     * @param lore a list of lore lines; must not be null or empty.
     * @return the current {@code ItemBuilder} instance.
     */
    public ItemBuilder lore(@NonNull List<String> lore) {
        if (!lore.isEmpty()) {
            this.lore = lore;
        }
        return this;
    }

    /**
     * Sets the lore (description) for the item.
     *
     * @param lines an array of lore lines; must not be empty.
     * @return the current {@code ItemBuilder} instance.
     */
    public ItemBuilder lore(@NonNull String... lines) {
        if (lines.length > 0) {
            this.lore = Arrays.asList(lines);
        }
        return this;
    }

    /**
     * Adds an enchantment with the specified level to the item.
     *
     * @param enchantment the enchantment to add; must not be null.
     * @param level       the level of the enchantment; must be greater than zero.
     * @return the current {@code ItemBuilder} instance.
     */
    public ItemBuilder addEnchantment(@NonNull Enchantment enchantment, int level) {
        if (level > 0) {
            this.enchantments.put(enchantment, level);
        }
        return this;
    }

    /**
     * Adds each specified enchantment with a level of 1 to the item.
     *
     * @param enchantments one or more enchantments to add; must not be null.
     * @return the current {@code ItemBuilder} instance.
     */
    public ItemBuilder addEnchants(@NonNull Enchantment... enchantments) {
        for (Enchantment enchantment : enchantments) {
            addEnchantment(enchantment, 1);
        }
        return this;
    }

    /**
     * Sets the skull owner for a skull item.
     *
     * @param owner the username of the skull owner; must not be null or empty.
     * @return the current {@code ItemBuilder} instance.
     */
    public ItemBuilder skullOwner(@NonNull String owner) {
        if (!owner.trim().isEmpty() && material == Material.SKULL_ITEM) {
            this.skullOwner = owner;
            this.customSkullTexture = null;
        }
        return this;
    }

    /**
     * Sets a custom skull texture for a skull item.
     *
     * @param texture the custom texture string; must not be null or empty.
     * @return the current {@code ItemBuilder} instance.
     */
    public ItemBuilder skullCustom(@NonNull String texture) {
        if (!texture.trim().isEmpty() && material == Material.SKULL_ITEM) {
            this.customSkullTexture = texture;
            this.skullOwner = null;
        }
        return this;
    }

    /**
     * Sets the color for a leather armor item.
     *
     * @param color the color to apply; must not be null.
     * @return the current {@code ItemBuilder} instance.
     */
    public ItemBuilder color(@NonNull Color color) {
        this.color = color;
        return this;
    }

    /**
     * Builds the {@link ItemStack} based on the specified attributes.
     *
     * @return the constructed {@link ItemStack}.
     */
    public ItemStack build() {
        if (material == Material.SKULL_ITEM && (skullOwner != null || customSkullTexture != null)) {
            durability = 3;
        }

        ItemStack item = new ItemStack(material, amount, durability);
        applyEnchantments(item);

        if (!hasModifications()) {
            return item;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        applyName(meta);
        applyLore(meta);
        applySkullMeta(meta);
        applyLeatherArmorMeta(meta);

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Gives the built {@link ItemStack} to the specified player.
     * If the player's inventory is full, the leftover items are dropped at the player's location.
     *
     * @param player the player to receive the item; must not be null.
     * @return the current {@code ItemBuilder} instance.
     */
    public ItemBuilder give(@NonNull Player player) {
        ItemStack item = build();
        Map<Integer, ItemStack> leftover = player.getInventory().addItem(item);

        if (leftover.isEmpty()) {
            return this;
        }

        for (ItemStack remainingItem : leftover.values()) {
            player.getWorld().dropItemNaturally(player.getLocation(), remainingItem);
        }

        return this;
    }

    /**
     * Checks if the current item is a custom skull.
     *
     * @return {@code true} if the material is a skull and a custom skull texture is set; {@code false} otherwise.
     */
    private boolean isCustomSkull() {
        return material == Material.SKULL_ITEM && customSkullTexture != null;
    }

    /**
     * Applies all specified enchantments to the given {@link ItemStack}.
     *
     * @param item the item to which enchantments will be applied.
     */
    private void applyEnchantments(ItemStack item) {
        if (!enchantments.isEmpty()) {
            enchantments.forEach(item::addUnsafeEnchantment);
        }
    }

    /**
     * Applies the display name to the {@link ItemMeta}.
     *
     * @param meta the item meta to update.
     */
    private void applyName(ItemMeta meta) {
        if (name != null && !name.trim().isEmpty()) {
            meta.setDisplayName(name);
        }
    }

    /**
     * Applies the lore (description) to the {@link ItemMeta}.
     *
     * @param meta the item meta to update.
     */
    private void applyLore(ItemMeta meta) {
        if (lore != null && !lore.isEmpty()) {
            meta.setLore(lore);
        }
    }

    /**
     * Applies skull metadata to the given {@link ItemMeta} if applicable.
     *
     * @param meta the item meta to update.
     */
    private void applySkullMeta(ItemMeta meta) {
        if (!(meta instanceof SkullMeta)) {
            return;
        }
        SkullMeta skullMeta = (SkullMeta) meta;
        if (customSkullTexture != null) {
            applyCustomSkullTexture(skullMeta);
            return;
        }
        if (skullOwner != null) {
            skullMeta.setOwner(skullOwner);
        }
    }

    /**
     * Applies a custom skull texture to the {@link SkullMeta}.
     *
     * @param skullMeta the skull meta to update.
     */
    private void applyCustomSkullTexture(SkullMeta skullMeta) {
        if (customSkullTexture.trim().isEmpty()) {
            return;
        }
        try {
            GameProfile profile = PROFILE_CACHE.get(customSkullTexture, key -> {
                GameProfile newProfile = new GameProfile(UUID.randomUUID(), null);
                String url = "http://textures.minecraft.net/texture/" + key;
                String json = String.format("{\"textures\":{\"SKIN\":{\"url\":\"%s\"}}}", url);
                String encodedData = Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
                newProfile.getProperties().put("textures", new Property("textures", encodedData));
                return newProfile;
            });

            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to apply custom skull texture", e);
        }
    }

    /**
     * Applies leather armor color modifications if applicable.
     *
     * @param meta the item meta to update.
     */
    private void applyLeatherArmorMeta(ItemMeta meta) {
        if (color == null) {
            return;
        }
        if (meta instanceof LeatherArmorMeta) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
            leatherArmorMeta.setColor(color);
        }
    }

    /**
     * Determines if any modifications have been applied to the item.
     *
     * @return {@code true} if modifications exist; {@code false} otherwise.
     */
    private boolean hasModifications() {
        return (name != null && !name.trim().isEmpty())
                || (lore != null && !lore.isEmpty())
                || skullOwner != null
                || customSkullTexture != null
                || color != null;
    }
}