package com.hanielcota.essentials.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@UtilityClass
public class InventoryUtils {

    /**
     * Clears the player's entire inventory, including armor slots.
     *
     * @param player The player whose inventory will be cleared.
     */
    public void clearPlayerInventory(Player player) {
        if (player == null || !player.isOnline()) {
            return;
        }

        final PlayerInventory inventory = player.getInventory();
        if (inventory == null) {
            return;
        }

        inventory.clear();
        inventory.setArmorContents(new ItemStack[4]);
        player.updateInventory(); // Necessário para 1.8.9
    }

    /**
     * Checks if the player's inventory is completely empty, including armor slots.
     *
     * @param player The player whose inventory will be checked.
     * @return true if the inventory is empty or the player is invalid, false otherwise.
     */
    public boolean isInventoryEmpty(Player player) {
        if (player == null || !player.isOnline()) {
            return true;
        }

        final PlayerInventory inventory = player.getInventory();
        if (inventory == null) {
            return true;
        }

        final ItemStack[] contents = inventory.getContents();
        if (!isItemArrayEmpty(contents)) {
            return false;
        }

        return isItemArrayEmpty(inventory.getArmorContents());
    }

    /**
     * Checks if an array of ItemStacks is empty (all slots are null or have zero amount).
     *
     * @param items The array of ItemStacks to check.
     * @return true if all items are null or have zero amount, false otherwise.
     */
    private boolean isItemArrayEmpty(ItemStack[] items) {
        for (ItemStack item : items) {
            if (item == null) {
                continue;
            }

            if (item.getAmount() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Counts the total amount of a specific item in the player's main inventory (excludes armor).
     *
     * @param player The player whose inventory will be checked.
     * @param itemToCount The item to count, must not be null.
     * @return The total amount of the specified item in the inventory.
     */
    public int countItem(Player player, ItemStack itemToCount) {
        if (player == null || !player.isOnline() || itemToCount == null) {
            return 0;
        }

        final PlayerInventory inventory = player.getInventory();
        if (inventory == null) {
            return 0;
        }

        return countSimilarItems(inventory.getContents(), itemToCount);
    }

    /**
     * Counts the total amount of items similar to the specified item in an array.
     *
     * @param items The array of items to check.
     * @param itemToCount The item to count.
     * @return The total amount of similar items.
     */
    private int countSimilarItems(ItemStack[] items, ItemStack itemToCount) {
        int totalCount = 0;
        for (ItemStack item : items) {
            if (item != null && item.isSimilar(itemToCount)) {
                totalCount += item.getAmount();
            }
        }
        return totalCount;
    }

    /**
     * Checks if there is enough space in the player's inventory to add the specified item.
     *
     * @param player The player whose inventory will be checked.
     * @param item The item to check space for, must not be null and have a positive amount.
     * @return true if there is space for the item, false otherwise.
     */
    public boolean hasSpaceForItem(Player player, ItemStack item) {
        if (player == null || !player.isOnline() || item == null || item.getAmount() <= 0) {
            return false;
        }

        final PlayerInventory inventory = player.getInventory();
        if (inventory == null) {
            return false;
        }

        return hasSpaceInInventory(inventory, item);
    }

    /**
     * Internal method to check if the inventory has space for an item, handling stackable and unstackable items.
     *
     * @param inventory The player's inventory.
     * @param item The item to check space for.
     * @return true if there is space, false otherwise.
     */
    private boolean hasSpaceInInventory(PlayerInventory inventory, ItemStack item) {
        if (item.getMaxStackSize() == 1) {
            final ItemStack[] contents = inventory.getContents();
            for (ItemStack slot : contents) {
                if (slot == null || slot.getType() == Material.AIR) {
                    return true;
                }
            }
            return false;
        }

        int amountLeftToAdd = item.getAmount();
        final ItemStack[] contents = inventory.getContents();
        for (ItemStack slot : contents) {
            if (slot == null || slot.getType() == Material.AIR) {
                return true;
            }
            if (!canStack(slot, item)) {
                continue;
            }

            amountLeftToAdd -= (slot.getMaxStackSize() - slot.getAmount());
            if (amountLeftToAdd <= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a slot can stack with the given item.
     *
     * @param slot The slot to check.
     * @param item The item to stack.
     * @return true if the slot can stack the item, false otherwise.
     */
    private boolean canStack(ItemStack slot, ItemStack item) {
        if (!slot.isSimilar(item)) {
            return false;
        }
        return slot.getAmount() < slot.getMaxStackSize();
    }

    /**
     * Removes a specified amount of an item from the player's inventory.
     *
     * @param player The player whose inventory will be modified.
     * @param itemToRemove The item to remove, must not be null.
     * @param amount The amount to remove, must be positive.
     * @return true if the specified amount was fully removed, false otherwise.
     */
    public boolean removeItem(Player player, ItemStack itemToRemove, int amount) {
        if (player == null || !player.isOnline() || itemToRemove == null || amount <= 0) {
            return false;
        }

        int totalAvailable = countItem(player, itemToRemove);
        if (totalAvailable < amount) {
            return false;
        }

        final PlayerInventory inventory = player.getInventory();
        if (inventory == null) {
            return false;
        }

        boolean removedSuccessfully = removeItemFromInventory(inventory, itemToRemove, amount);

        if (removedSuccessfully) {
            player.updateInventory();
        }
        return removedSuccessfully;
    }

    /**
     * Internal method to remove items from the inventory.
     *
     * @param inventory The player's inventory.
     * @param itemToRemove The item to remove.
     * @param amount The amount to remove.
     * @return true if the amount was fully removed, false otherwise.
     */
    private boolean removeItemFromInventory(PlayerInventory inventory, ItemStack itemToRemove, int amount) {
        int amountLeftToRemove = amount;

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null || !item.isSimilar(itemToRemove)) {
                continue;
            }

            int currentAmount = item.getAmount();
            if (currentAmount <= amountLeftToRemove) {
                amountLeftToRemove -= currentAmount;
                inventory.setItem(i, null);
            }

            if (currentAmount > amountLeftToRemove) {
                item.setAmount(currentAmount - amountLeftToRemove);
                return true;
            }

            if (amountLeftToRemove <= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an item to the player's inventory, dropping any excess on the ground if there's no space.
     *
     * @param player The player whose inventory will be modified.
     * @param item The item to add, must not be null and have a positive amount.
     */
    public void addItemOrDrop(Player player, ItemStack item) {
        if (player == null || !player.isOnline() || item == null || item.getAmount() <= 0) {
            return;
        }

        final PlayerInventory inventory = player.getInventory();
        if (inventory == null) {
            return;
        }

        ItemStack itemToAdd = item.clone();
        ItemStack excessItem = inventory.addItem(itemToAdd).get(0);

        if (excessItem != null && excessItem.getAmount() > 0) {
            player.getWorld().dropItem(player.getLocation(), excessItem);
        }
        player.updateInventory();
    }
}