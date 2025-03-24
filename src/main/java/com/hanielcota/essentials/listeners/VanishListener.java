package com.hanielcota.essentials.listeners;

import com.hanielcota.essentials.utils.VanishUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VanishListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!VanishUtils.isVanished(player)) {
            return;
        }

        VanishUtils.removeVanish(player);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damaged = event.getEntity();
        if (!(damaged instanceof Player)) {
            return;
        }
        Player player = (Player) damaged;

        if (!VanishUtils.isVanished(player)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        Entity target = event.getTarget();
        if (!(target instanceof Player)) {
            return;
        }
        Player player = (Player) target;
        if (!VanishUtils.isVanished(player)) {
            return;
        }

        event.setCancelled(true);
    }
}