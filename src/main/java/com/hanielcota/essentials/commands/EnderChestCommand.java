package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.CommandPermission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("ec|enderchest")
@Description("Abre seu baú do Ender")
@CommandPermission("essentials.ec")
public class EnderChestCommand extends BaseCommand {

    @Default
    public void onEnderChest(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Este comando só pode ser usado por jogadores!");
            return;
        }

        Player player = (Player) sender;
        player.openInventory(player.getEnderChest());
        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Baú do Ender aberto!");
    }
}