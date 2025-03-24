package com.hanielcota.essentials.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;

import java.util.EnumSet;

public class IceSnowControlListener implements Listener {

    private static final EnumSet<Material> PROTECTED_MATERIALS = EnumSet.of(
            Material.ICE,
            Material.SNOW
    );

    @EventHandler
    public void onBlockFade(BlockFadeEvent event) {
        if (!PROTECTED_MATERIALS.contains(event.getBlock().getType())) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockForm(BlockFormEvent event) {
        Material newMaterial = event.getNewState().getType();
        if (newMaterial != Material.ICE && newMaterial != Material.SNOW) {
            return;
        }

        event.setCancelled(true);
    }
}