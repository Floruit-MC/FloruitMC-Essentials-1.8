package com.hanielcota.essentials.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class GodUtils {

    private final Set<UUID> godPlayers = new HashSet<>();
    private final JavaPlugin plugin;

    public boolean enableGod(Player player) {
        if (player == null || !player.isOnline()) {
            return false;
        }

        UUID uuid = player.getUniqueId();
        if (godPlayers.contains(uuid)) {
            return false;
        }

        godPlayers.add(uuid);
        return true;
    }

    public boolean enableGodWithTimeout(Player player, int seconds) {
        if (!enableGod(player)) {
            return false;
        }

        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> disableGod(player), seconds * 20L);
        return true;
    }

    public boolean disableGod(Player player) {
        if (player == null) {
            return false;
        }

        return godPlayers.remove(player.getUniqueId());
    }

    public boolean isGod(Player player) {
        if (player == null) {
            return false;
        }

        return godPlayers.contains(player.getUniqueId());
    }

    public void clearGodPlayers() {
        godPlayers.clear();
    }

    public int getGodCount() {
        return godPlayers.size();
    }
}