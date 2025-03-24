package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("craft|workbench")
@Description("Abre uma mesa de trabalho")
@CommandPermission("essentials.craft")
public class CraftCommand extends BaseCommand {

    @Default
    public void onCraft(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Este comando só pode ser usado por jogadores!");
            return;
        }

        Player player = (Player) sender;
        player.openWorkbench(null, true);
        player.sendMessage("§e§lFLORUIT MC §f➤ §e✔ Mesa de trabalho aberta!");
    }
}