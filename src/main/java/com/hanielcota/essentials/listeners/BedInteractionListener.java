package com.hanielcota.essentials.listeners;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.EnumSet;

@RequiredArgsConstructor
public class BedInteractionListener implements Listener {

    private static final EnumSet<Material> BEDS = EnumSet.of(
            Material.BED,
            Material.BED_BLOCK
    );

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.hasBlock()) return;
        if (!BEDS.contains(event.getClickedBlock().getType())) return;

        Player player = event.getPlayer();
        if (player.hasPermission("essentials.bypass.bed")) return;

        event.setCancelled(true);
        player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não pode usar camas neste servidor!");
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!BEDS.contains(event.getBlock().getType())) return;

        Player player = event.getPlayer();
        if (player.hasPermission("essentials.bypass.bed")) return;

        event.setCancelled(true);
        player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não pode colocar camas neste servidor!");
    }
}