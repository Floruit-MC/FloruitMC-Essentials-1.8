package com.hanielcota.essentials.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.EnumSet;

public class ExplosionDamageListener implements Listener {

    private static final EnumSet<EntityDamageEvent.DamageCause> EXPLOSION_CAUSES = EnumSet.of(
            EntityDamageEvent.DamageCause.BLOCK_EXPLOSION,
            EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
    );

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getCause() == null) {
            return;
        }

        if (!EXPLOSION_CAUSES.contains(event.getCause())) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.blockList() != null) {
            event.blockList().clear();
        }
    }
}
