package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("kick")
@CommandPermission("essentials.kick")
public class KickCommand extends BaseCommand {

    @Default
    @Syntax("<jogador> [motivo] - Kicka um jogador com motivo opcional")
    @CommandCompletion("@players afk conexao disconnect")
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            p.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Utilize /kick <jogador> <motivo> para kickar um jogador.");
            return;
        }

        Player target = Bukkit.getServer().getPlayer(args[0]);
        if (target == null) {
            p.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Este jogador não está on-line!");
            return;
        }

        if (args.length == 1) {
            kickPlayer(p, target, "§c§lFLORUIT MC\n§c\n§cVocê foi kickado pelo jogador '" + p.getName() + "'.");
            return;
        }

        String reason = args[1].toLowerCase();
        if (reason.equals("afk")) {
            kickPlayer(p, target, "§c§lFLORUIT MC\n§c\n§cVocê ficou inativo e foi kickado do servidor.");
            return;
        }

        if (reason.equals("conexao")) {
            kickPlayer(p, target, "§c§lFLORUIT MC\n§c\n§cSua conexão foi perdida com o servidor.");
            return;
        }

        if (reason.equals("disconnect")) {
            kickPlayer(p, target, "§c§lFLORUIT MC\n§c\n§cVocê foi disconectado do servidor.");
            return;
        }

        kickPlayer(p, target, "§c§lFLORUIT MC\n§c\n§cVocê foi kickado pelo jogador '" + p.getName() + "'.");
    }

    private void kickPlayer(Player sender, Player target, String kickMessage) {
        sender.sendMessage("§e§lFLORUIT MC §f➤ §e✔ Você expulsou o jogador " + target.getName() + " com sucesso!");
        target.kickPlayer(kickMessage);
    }
}