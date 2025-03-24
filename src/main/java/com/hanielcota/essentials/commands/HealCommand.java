package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("heal")
@Description("Cura a si mesmo ou um jogador específico")
@CommandPermission("essentials.heal")
public class HealCommand extends BaseCommand {

    @Default
    public void onHeal(CommandSender sender, String[] args) {
        if (!(sender instanceof Player) && args.length < 1) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Este comando requer um jogador ou um alvo quando usado no console!");
            return;
        }

        if (args.length < 1) {
            Player player = (Player) sender;
            player.setHealth(player.getMaxHealth());
            player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Você foi curado!");
            return;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ O jogador '" + args[0] + "' não foi encontrado ou está offline!");
            return;
        }

        target.setHealth(target.getMaxHealth());
        target.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Você foi curado!");
        sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ O jogador '" + target.getName() + "' foi curado!");
    }
}