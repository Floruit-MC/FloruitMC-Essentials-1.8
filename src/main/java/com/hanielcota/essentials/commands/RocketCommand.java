package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("rocket")
@CommandPermission("essentials.rocket")
public class RocketCommand extends BaseCommand {

    @Default
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            p.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Utilize /rocket <raio> para lançar os jogadores pra cima.");
            return;
        }

        if (!isInt(args[0])) {
            p.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você inseriu um número inválido.");
            return;
        }

        int raio = Integer.parseInt(args[0]);
        if (raio > 100) {
            p.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não pode lançar esse raio, utilize no máximo 100.");
            return;
        }

        if (raio < 8) {
            p.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não pode lançar esse raio, utilize no mínimo 8.");
            return;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().distance(p.getLocation()) > raio) continue;

            player.sendMessage("§e§lFLORUIT MC §f➤ §e✔ Você foi lançado pelo jogador " + p.getName() + ".");
            player.teleport(player.getLocation().add(0.0D, 5.0D, 0.0D));
        }

        p.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Você lançou os jogadores em um raio de " + raio + " blocos.");
    }

    private boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}