package com.hanielcota.essentials.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public final class MobSpawnListener implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) return;
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) return;

        // Bloquear mobs hostis
        if (isHostileMob(event.getEntityType())) {
            event.setCancelled(true);
        }
    }

    private boolean isHostileMob(EntityType type) {
        if (type == EntityType.SKELETON) return true;
        if (type == EntityType.ZOMBIE) return true;
        if (type == EntityType.CREEPER) return true;
        if (type == EntityType.SPIDER) return true;
        if (type == EntityType.CAVE_SPIDER) return true;
        if (type == EntityType.ENDERMAN) return true;
        if (type == EntityType.SLIME) return true;
        if (type == EntityType.GHAST) return true;
        if (type == EntityType.MAGMA_CUBE) return true;
        if (type == EntityType.BLAZE) return true;
        if (type == EntityType.WITCH) return true;
        return type == EntityType.WITHER;
    }
}