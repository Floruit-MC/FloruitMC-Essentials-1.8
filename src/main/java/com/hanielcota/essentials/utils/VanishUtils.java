package com.hanielcota.essentials.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class VanishUtils {

    private static final Set<Player> vanishedPlayers = new HashSet<>();

    public boolean toggleVanish(Player player) {
        if (player == null || !player.isOnline()) {
            return false;
        }

        if (vanishedPlayers.contains(player)) {
            vanishedPlayers.remove(player);
            showPlayerToAll(player);
            return false;
        }

        vanishedPlayers.add(player);
        hidePlayerFromAll(player);
        return true;
    }

    private void hidePlayerFromAll(Player player) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online == player) {
                continue;
            }
            online.hidePlayer(player);
        }
    }

    private void showPlayerToAll(Player player) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online == player) {
                continue;
            }
            online.showPlayer(player);
        }
    }

    public boolean isVanished(Player player) {
        if (player == null || !player.isOnline()) {
            return false;
        }
        return vanishedPlayers.contains(player);
    }

    public void removeVanish(Player player) {
        if (player == null || !player.isOnline()) {
            return;
        }
        if (!vanishedPlayers.contains(player)) {
            return;
        }
        vanishedPlayers.remove(player);
        showPlayerToAll(player);
    }

    public void clearVanishedPlayers() {
        for (Player player : vanishedPlayers) {
            showPlayerToAll(player);
        }
        vanishedPlayers.clear();
    }
}