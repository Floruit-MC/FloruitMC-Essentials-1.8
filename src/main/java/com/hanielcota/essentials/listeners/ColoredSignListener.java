package com.hanielcota.essentials.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class ColoredSignListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (!(event.getBlock().getState() instanceof Sign)) {
            return;
        }

        if (!event.getPlayer().hasPermission("essentials.sign.color")) {
            return;
        }

        String[] lines = event.getLines();
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].isEmpty()) {
                continue;
            }

            String coloredLine = ChatColor.translateAlternateColorCodes('&', lines[i]);
            event.setLine(i, coloredLine);
        }
    }
}