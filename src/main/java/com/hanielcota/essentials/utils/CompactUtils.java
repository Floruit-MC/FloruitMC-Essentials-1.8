package com.hanielcota.essentials.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.EnumMap;
import java.util.Map;

@UtilityClass
public class CompactUtils {

    private static final Map<Material, Material> COMPACT_MAP = new EnumMap<>(Material.class);
    private static final int ITEMS_PER_BLOCK = 9;

    static {
        COMPACT_MAP.put(Material.IRON_INGOT, Material.IRON_BLOCK);
        COMPACT_MAP.put(Material.GOLD_INGOT, Material.GOLD_BLOCK);
        COMPACT_MAP.put(Material.DIAMOND, Material.DIAMOND_BLOCK);
        COMPACT_MAP.put(Material.EMERALD, Material.EMERALD_BLOCK);
        COMPACT_MAP.put(Material.COAL, Material.COAL_BLOCK);
        COMPACT_MAP.put(Material.REDSTONE, Material.REDSTONE_BLOCK);
        COMPACT_MAP.put(Material.LAPIS_ORE, Material.LAPIS_BLOCK);
        COMPACT_MAP.put(Material.QUARTZ, Material.QUARTZ_BLOCK);
    }

    /**
     * Compacta os itens no inventário do jogador.
     *
     * @param player O jogador cujo inventário será compactado.
     * @return true se pelo menos um item foi compactado, false caso contrário.
     */
    public boolean compactInventory(Player player) {
        if (player == null) return false;
        if (!player.isOnline()) return false;

        PlayerInventory inventory = player.getInventory();
        boolean compacted = false;

        for (Map.Entry<Material, Material> entry : COMPACT_MAP.entrySet()) {
            Material material = entry.getKey();
            Material blockMaterial = entry.getValue();
            int totalItems = countItems(inventory, material);

            if (totalItems >= ITEMS_PER_BLOCK) {
                int blocksToCreate = totalItems / ITEMS_PER_BLOCK;
                int remainingItems = totalItems % ITEMS_PER_BLOCK;

                removeItems(inventory, material, totalItems);
                ItemStack blockStack = new ItemStack(blockMaterial, blocksToCreate);
                inventory.addItem(blockStack);

                if (remainingItems > 0) {
                    ItemStack remainingStack = new ItemStack(material, remainingItems);
                    inventory.addItem(remainingStack);
                }

                compacted = true;
            }
        }

        player.updateInventory();
        return compacted;
    }

    /**
     * Conta a quantidade de um material no inventário.
     *
     * @param inventory O inventário do jogador.
     * @param material O material a ser contado.
     * @return A quantidade total do material.
     */
    private int countItems(PlayerInventory inventory, Material material) {
        int total = 0;
        for (ItemStack item : inventory.getContents()) {
            if (item == null) continue;
            if (item.getType() == material) total += item.getAmount();
        }
        return total;
    }

    /**
     * Remove uma quantidade específica de um material do inventário.
     *
     * @param inventory O inventário do jogador.
     * @param material O material a ser removido.
     * @param amount A quantidade a ser removida.
     */
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
}