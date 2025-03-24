package com.hanielcota.essentials.listeners;

import com.hanielcota.essentials.utils.TeleportUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class NoVoidListener implements Listener {

    private static final double VOID_THRESHOLD = 0.0; // Y < 0 é considerado Void

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location to = event.getTo();
        if (to.getY() >= VOID_THRESHOLD) return;

        World world = player.getWorld();
        Location spawnLocation = world.getSpawnLocation();
        if (!TeleportUtils.isSafeLocation(spawnLocation)) {
            spawnLocation = TeleportUtils.findTopLocation(spawnLocation);
        }

        if (spawnLocation == null) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Não foi possível encontrar um local seguro no spawn!");
            return;
        }

        TeleportUtils.storeLastLocation(player, player.getLocation());
        player.teleport(spawnLocation);
        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Você foi teleportado para o spawn para evitar o Void!");
    }
}