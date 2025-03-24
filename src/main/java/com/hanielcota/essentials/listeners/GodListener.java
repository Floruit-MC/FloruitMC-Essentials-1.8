package com.hanielcota.essentials.listeners;

import com.hanielcota.essentials.utils.GodUtils;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@AllArgsConstructor
public class GodListener implements Listener {

    private final GodUtils godUtils;

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        if (!godUtils.isGod(player)) {
            return;
        }

        event.setCancelled(true);
    }
}