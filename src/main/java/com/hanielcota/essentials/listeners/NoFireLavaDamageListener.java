package com.hanielcota.essentials.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class NoFireLavaDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (event.getCause() != EntityDamageEvent.DamageCause.FIRE &&
                event.getCause() != EntityDamageEvent.DamageCause.FIRE_TICK &&
                event.getCause() != EntityDamageEvent.DamageCause.LAVA) return;

        event.setCancelled(true);
    }
}