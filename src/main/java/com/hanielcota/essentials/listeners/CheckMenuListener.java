package com.hanielcota.essentials.listeners;

import com.hanielcota.essentials.menus.CheckMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CheckMenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof CheckMenu)) return;
        event.setCancelled(true);
    }
}
