package com.hanielcota.essentials.listeners;

import com.hanielcota.essentials.utils.LockUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class LockListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        if (!LockUtils.isChest(block)) {
            return;
        }

        if (!LockUtils.areChestsLocked()) {
            return;
        }

        Player player = event.getPlayer();
        event.setCancelled(true);
        player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Algum STAFF trancou os baús globalmente!");
    }
}
