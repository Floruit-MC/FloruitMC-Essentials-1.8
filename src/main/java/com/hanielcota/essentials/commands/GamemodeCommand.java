package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@CommandAlias("gamemode|gm")
@CommandPermission("gamemode.use")
@Description("Altera o modo de jogo rapidamente")
public class GamemodeCommand extends BaseCommand {

    @Default
    @CommandCompletion("survival|creative|adventure|spectator @players")
    @CommandPermission("essentials.gamemode")
    public void onCommand(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Utilize /gamemode <modo> [jogador] para alterar o modo de jogo.");
            return;
        }

        GameMode mode = parseGamemode(args[0]);
        if (mode == null) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Modo de jogo inválido. Use survival (0), creative (1), adventure (2) ou spectator (3).");
            return;
        }

        if (args.length == 2) {
            if (!player.hasPermission("essentials.gamemode.others")) {
                player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não tem permissão para alterar o modo de jogo de outros jogadores.");
                return;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Jogador '" + args[1] + "' não encontrado ou offline.");
                return;
            }

            if (target.getGameMode() == mode) {
                player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ O jogador " + target.getName() + " já está nesse modo de jogo.");
                return;
            }

            target.setGameMode(mode);
            target.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Modo de jogo alterado para " + mode.name() + ".");
            player.sendMessage("§e§lFLORUIT MC §f➤ §e✔ Modo de jogo do jogador " + target.getName() + " foi alterado para " + mode.name() + ".");
            target.playSound(target.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
            return;
        }

        if (player.getGameMode() == mode) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você já está nesse modo de jogo.");
            return;
        }

        player.setGameMode(mode);
        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Modo de jogo alterado para " + mode.name() + ".");
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
    }

    private GameMode parseGamemode(String gamemode) {
        if (gamemode == null) return null;

        String modeLower = gamemode.toLowerCase();
        switch (modeLower) {
            case "0":
            case "s":
            case "survival":
                return GameMode.SURVIVAL;
            case "1":
            case "c":
            case "creative":
                return GameMode.CREATIVE;
            case "2":
            case "a":
            case "adventure":
                return GameMode.ADVENTURE;
            case "3":
            case "spec":
            case "spectator":
                return GameMode.SPECTATOR;
        }
        return null;
    }
}