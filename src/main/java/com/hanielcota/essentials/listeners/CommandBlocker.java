package com.hanielcota.essentials.listeners;

import com.hanielcota.essentials.utils.ConfigUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

@RequiredArgsConstructor
public class CommandBlocker implements Listener {

    private final ConfigUtils configUtils;
    private final List<String> blockedCommands;

    public CommandBlocker(ConfigUtils configUtils) {
        this.configUtils = configUtils;
        this.blockedCommands = configUtils.get("blocked-commands", List.of("op", "deop", "reload", "stop", "gamemode"));
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (player.isOp()) return;

        String command = extractCommand(event.getMessage());

        if (!blockedCommands.contains(command)) return;

        event.setCancelled(true);
        player.sendMessage("§cVocê não tem permissão para usar este comando!");
    }

    private String extractCommand(String message) {
        return message.toLowerCase().split(" ")[0].replace("/", "");
    }
}