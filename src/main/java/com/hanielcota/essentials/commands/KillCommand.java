package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("kill")
@Description("Mata você ou outro jogador")
@CommandPermission("essentials.kill")
public class KillCommand extends BaseCommand {

    @Default
    public void onKill(CommandSender sender, String[] args) {
        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Este comando requer um jogador ou um alvo quando usado no console!");
            return;
        }

        if (args.length == 0) {
            Player player = (Player) sender;
            player.setHealth(0.0);
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você se matou!");
            return;
        }

        if (!sender.hasPermission("essentials.kill.others")) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não tem permissão para matar outros jogadores!");
            return;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ O jogador '" + args[0] + "' não foi encontrado ou está offline!");
            return;
        }

        target.setHealth(0.0);
        target.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você foi morto por " + (sender instanceof Player ? sender.getName() : "Console") + "!");
        sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Você matou o jogador '" + target.getName() + "'!");
    }
}