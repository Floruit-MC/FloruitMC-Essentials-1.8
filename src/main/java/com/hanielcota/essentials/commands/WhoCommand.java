package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("who|list|online")
@Description("Lista os jogadores online no servidor")
@CommandPermission("essentials.who")
public class WhoCommand extends BaseCommand {

    @Default
    public void onWho(CommandSender sender) {
        int onlineCount = Bukkit.getOnlinePlayers().size();
        if (onlineCount == 0) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Nenhum jogador está online no momento.");
            return;
        }

        StringBuilder playerList = new StringBuilder();
        for (Player player : Bukkit.getOnlinePlayers()) {
            appendPlayerName(playerList, player);
        }

        sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Jogadores online (§e" + onlineCount + "§a):");
        sender.sendMessage(playerList.toString());
    }

    private void appendPlayerName(StringBuilder builder, Player player) {
        if (builder.length() > 0) {
            builder.append("§7, ");
        }

        String displayName = player.getDisplayName();
        builder.append("§f").append(displayName);
    }
}
