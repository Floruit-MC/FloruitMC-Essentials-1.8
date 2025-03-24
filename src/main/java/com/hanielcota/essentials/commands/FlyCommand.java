package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@CommandAlias("fly")
@CommandPermission("essentials.fly")
public class FlyCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    public void onCommand(Player player, String[] args) {
        if (args.length == 0) {
            if (hasNativeFlight(player)) {
                player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você já possui voo nativo neste modo de jogo.");
                return;
            }

            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Modo de voo desligado!");
                return;
            }

            player.setAllowFlight(true);
            player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Modo de voo ativado!");
            return;
        }

        Player target = Bukkit.getServer().getPlayerExact(args[0]);
        if (target == null) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ O jogador '" + args[0] + "' não foi encontrado ou está offline!");
            return;
        }

        if (!player.hasPermission("essentials.fly.others")) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não tem permissão para alterar o voo de outros jogadores!");
            return;
        }

        if (target.equals(player)) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Use o comando sem argumentos para alterar seu próprio modo de voo.");
            return;
        }

        if (target.getAllowFlight()) {
            target.setAllowFlight(false);
            target.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Modo de voo desligado!");
            player.sendMessage("§e§lFLORUIT MC §f➤ §e✔ Modo de voo do jogador " + target.getName() + " foi desligado!");
            return;
        }

        target.setAllowFlight(true);
        target.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Modo de voo ativado!");
        player.sendMessage("§e§lFLORUIT MC §f➤ §e✔ Modo de voo do jogador " + target.getName() + " foi ativado!");
    }

    private boolean hasNativeFlight(Player player) {
        if (player.getGameMode() == GameMode.CREATIVE) return true;
        return player.getGameMode() == GameMode.SPECTATOR;
    }
}