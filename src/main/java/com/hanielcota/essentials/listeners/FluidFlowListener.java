package com.hanielcota.essentials.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.EnumSet;

public class FluidFlowListener implements Listener {

    private static final EnumSet<Material> FLUIDS = EnumSet.of(
            Material.WATER,
            Material.STATIONARY_WATER,
            Material.LAVA,
            Material.STATIONARY_LAVA
    );

    @EventHandler
    public void onFluidFlow(BlockFromToEvent event) {
        if (!FLUIDS.contains(event.getBlock().getType())) {
            return;
        }

        event.setCancelled(true);
    }
}