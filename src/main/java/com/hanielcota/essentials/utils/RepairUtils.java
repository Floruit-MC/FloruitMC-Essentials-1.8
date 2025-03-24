package com.hanielcota.essentials.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@UtilityClass
public class RepairUtils {

    public void repairAllItems(Player player) {
        if (player == null || !player.isOnline()) {
            return;
        }

        PlayerInventory inventory = player.getInventory();
        repairItemArray(inventory.getContents());
        repairItemArray(inventory.getArmorContents());

        player.updateInventory();
    }

    private void repairItemArray(ItemStack[] items) {
        for (ItemStack item : items) {

            if (item == null || item.getType() == Material.AIR) {
                continue;
            }

            if (isRepairable(item)) {
                item.setDurability((short) 0);
            }
        }
    }

    public boolean repairSingleItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return false;
        }
        if (!isRepairable(item)) {
            return false;
        }
        if (item.getDurability() == 0) {
            return false;
        }

        item.setDurability((short) 0);
        return true;
    }

    private boolean isRepairable(ItemStack item) {
        Material material = item.getType();
        return material.getMaxDurability() > 0;
    }
}