package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("flyspeed")
@CommandPermission("essentials.speed.fly")
public class FlySpeedCommand extends BaseCommand {

    @Default
    @CommandCompletion("reset|clear|1|2|3|4|5|6|7|8|9|10 @players")
    public void onFlySpeed(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Este comando só pode ser usado por jogadores.");
            return;
        }

        Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Use /flyspeed <valor|reset|clear> [jogador] para ajustar a velocidade.");
            return;
        }

        if (args[0].equalsIgnoreCase("reset") || args[0].equalsIgnoreCase("clear")) {
            Player target = args.length < 2 ? player : Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ O jogador '" + args[1] + "' não foi encontrado ou está offline!");
                return;
            }

            if (target != player && !player.hasPermission("essentials.speed.fly.others")) {
                player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não tem permissão para alterar a velocidade de outros jogadores!");
                return;
            }

            target.setFlySpeed(0.2F);
            target.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Sua velocidade de voo foi resetada para o padrão.");
            target.playSound(target.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
            if (target != player) {
                player.sendMessage("§e§lFLORUIT MC §f➤ §e✔ Velocidade de voo de " + target.getName() + " resetada para o padrão.");
            }
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(args[0]);
        } catch (NumberFormatException ignored) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você inseriu um número inválido.");
            return;
        }

        if (quantity < 1 || quantity > 10) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ A quantidade deve estar entre 1 e 10.");
            return;
        }

        Player target = args.length < 2 ? player : Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ O jogador '" + args[1] + "' não foi encontrado ou está offline!");
            return;
        }

        if (target != player && !player.hasPermission("essentials.speed.fly.others")) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não tem permissão para alterar a velocidade de outros jogadores!");
            return;
        }

        float speed = quantity * 0.1F;
        target.setFlySpeed(speed);
        target.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Sua velocidade ao voar foi alterada para " + quantity + ".");
        target.playSound(target.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);

        if (target != player) {
            player.sendMessage("§e§lFLORUIT MC §f➤ §e✔ Velocidade ao voar de " + target.getName() + " alterada para " + quantity + ".");
        }
    }
}