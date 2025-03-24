package com.hanielcota.essentials.listeners;

import com.hanielcota.essentials.EssentialsPlugin;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@AllArgsConstructor
public class BlockCraftingListener implements Listener {

    private final EssentialsPlugin plugin;


    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        ItemStack result = event.getRecipe().getResult();
        if (result == null) return;

        Set<Material> blockedItems = getBlockedCraftingItems();
        if (!blockedItems.contains(result.getType())) return;

        event.getInventory().setResult(new ItemStack(Material.AIR));
        for (HumanEntity viewer : event.getViewers()) {
            if (!(viewer instanceof Player)) continue;

            Player player = (Player) event.getViewers();
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não pode craftar este item!");
        }
    }

    public Set<Material> getBlockedCraftingItems() {
        List<String> blockedItems = plugin.getConfigUtils().getConfig().getStringList("blocked-crafting-items");
        if (blockedItems.isEmpty()) return Collections.emptySet();

        Set<Material> materials = new HashSet<>();
        for (String itemName : blockedItems) {
            Material material = Material.getMaterial(itemName.toUpperCase());
            if (material == null) {
                plugin.getLogger().warning("Item inválido na config: " + itemName);
                continue;
            }
            materials.add(material);
        }
        return materials;
    }
}