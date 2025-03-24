package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("puxar|pull")
@Description("Puxa um jogador ou todos até você.")
@CommandPermission("essentials.puxar")
@RequiredArgsConstructor
public class PuxarCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    public void onPuxar(Player sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Uso correto: /puxar <jogador> ou /puxar all");
            return;
        }

        String targetName = args[0].toLowerCase();
        if (targetName.equals("all")) {
            pullAllPlayers(sender);
            return;
        }

        Player target = Bukkit.getPlayer(targetName);
        if (target == null || !target.isOnline()) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Jogador §e" + targetName + " §cnão encontrado ou está offline!");
            return;
        }

        if (target.equals(sender)) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não pode puxar a si mesmo!");
            return;
        }

        target.teleport(sender.getLocation());
        sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Você puxou §e" + target.getName() + " §apara sua localização!");
        target.sendMessage("§e§lFLORUIT MC §f➤ §e✔ Você foi puxado por §a" + sender.getName() + "!");
    }

    private void pullAllPlayers(Player sender) {
        int count = 0;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.equals(sender)) continue;

            player.teleport(sender.getLocation());
            player.sendMessage("§e§lFLORUIT MC §f➤ §e✔ Você foi puxado por §a" + sender.getName() + "!");
            count++;
        }

        sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Você puxou §e" + count + " §ajogador(es) para sua localização!");
    }
}