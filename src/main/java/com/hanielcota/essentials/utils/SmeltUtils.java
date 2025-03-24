package com.hanielcota.essentials.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.EnumMap;
import java.util.Map;

@UtilityClass
public class SmeltUtils {

    private static final Map<Material, Material> SMELT_MAP = new EnumMap<>(Material.class);

    static {
        SMELT_MAP.put(Material.IRON_ORE, Material.IRON_INGOT);
        SMELT_MAP.put(Material.GOLD_ORE, Material.GOLD_INGOT);
        SMELT_MAP.put(Material.COAL_ORE, Material.COAL);
        SMELT_MAP.put(Material.DIAMOND_ORE, Material.DIAMOND);
        SMELT_MAP.put(Material.LAPIS_ORE, Material.INK_SACK);
        SMELT_MAP.put(Material.REDSTONE_ORE, Material.REDSTONE);
        SMELT_MAP.put(Material.EMERALD_ORE, Material.EMERALD);
        SMELT_MAP.put(Material.RAW_FISH, Material.COOKED_FISH);
        SMELT_MAP.put(Material.RAW_BEEF, Material.COOKED_BEEF);
        SMELT_MAP.put(Material.RAW_CHICKEN, Material.COOKED_CHICKEN);
        SMELT_MAP.put(Material.PORK, Material.GRILLED_PORK);
        SMELT_MAP.put(Material.POTATO_ITEM, Material.BAKED_POTATO);
    }

    /**
     * Derrete todos os itens derretíveis no inventário do jogador.
     *
     * @param player O jogador cujo inventário será processado.
     * @return true se algo foi derretido, false caso contrário.
     */
    public boolean smeltInventory(Player player) {
        if (player == null) return false;
        if (!player.isOnline()) return false;

        PlayerInventory inventory = player.getInventory();
        boolean smelted = false;

        for (Map.Entry<Material, Material> entry : SMELT_MAP.entrySet()) {
            Material rawMaterial = entry.getKey();
            Material smeltedMaterial = entry.getValue();
            int totalItems = countItems(inventory, rawMaterial);

            if (totalItems > 0) {
                removeItems(inventory, rawMaterial, totalItems);
                ItemStack smeltedStack = createSmeltedItem(smeltedMaterial, totalItems);
                inventory.addItem(smeltedStack);
                smelted = true;
            }
        }

        player.updateInventory();
        return smelted;
    }

    private int countItems(PlayerInventory inventory, Material material) {
        int total = 0;
        for (ItemStack item : inventory.getContents()) {
            if (item == null) continue;
            if (item.getType() == material) total += item.getAmount();
        }
        return total;
    }

    private void removeItems(PlayerInventory inventory, Material material, int amount) {
        int remaining = amount;
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null && item.getType() == material) {
                int itemAmount = item.getAmount();
                if (itemAmount <= remaining) {
                    inventory.setItem(i, null);
                    remaining -= itemAmount;
                }

                if (itemAmount > remaining) {
                    item.setAmount(itemAmount - remaining);
                    return;
                }

                if (remaining == 0) return;
            }
        }
    }

    private ItemStack createSmeltedItem(Material material, int amount) {
        ItemStack item = new ItemStack(material, amount);
        if (material == Material.INK_SACK) {
            item.setDurability((short) 4);
        }
        return item;
    }
}