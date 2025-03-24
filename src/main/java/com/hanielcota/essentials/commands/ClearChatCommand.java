package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("clearchat|cc")
@Description("Limpa o chat de todos os jogadores")
@CommandPermission("essentials.clearchat")
public class ClearChatCommand extends BaseCommand {

    @Default
    public void onClearChat(CommandSender sender, String[] args) {
        clearChat();
        broadcastMessage("§e§lFLORUIT MC §f➤ §e✔ O chat foi limpo por " + getSenderName(sender) + "!");
        sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Chat limpo com sucesso!");
    }

    private void clearChat() {
        String blankLine = " ".repeat(100);
        int linesToClear = 100;

        while (linesToClear > 0) {
            Bukkit.broadcastMessage(blankLine);
            linesToClear--;
        }
    }

    private void broadcastMessage(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player == null) continue;
            player.sendMessage(message);
        }
    }

    private String getSenderName(CommandSender sender) {
        if (!(sender instanceof Player)) return "Console";

        Player player = (Player) sender;
        if (player.getDisplayName() == null) return player.getName();

        return player.getDisplayName();
    }
}