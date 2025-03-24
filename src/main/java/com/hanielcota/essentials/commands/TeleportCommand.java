package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CommandAlias("tp|teleport")
@CommandPermission("essentials.teleport")
@Description("Teleporta para um jogador, puxa um jogador para você ou teleporta para coordenadas")
public class TeleportCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players puxar")
    public void onCommand(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Uso: /tp <jogador> [puxar] ou /tp <x> <y> <z>");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target != null) {
            if (target.equals(player)) {
                player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não pode se teleportar para si mesmo.");
                return;
            }

            boolean isPullCommand = args.length == 2 && args[1].equalsIgnoreCase("puxar");
            if (isPullCommand) {
                if (!player.hasPermission("essentials.teleport.others")) {
                    player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não tem permissão para puxar outros jogadores.");
                    return;
                }

                Location playerLocation = player.getLocation();
                target.teleport(playerLocation);
                target.sendMessage("§e§lFLORUIT MC §f➤ §e✔ Você foi teleportado para " + player.getName() + ".");
                player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Você puxou " + target.getName() + " para você.");
                return;
            }

            if (args.length > 1) {
                player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Uso inválido. Use /tp <jogador> ou /tp <jogador> puxar.");
                return;
            }

            Location targetLocation = target.getLocation();
            player.teleport(targetLocation);
            player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Teleportado para " + target.getName() + ".");
            return;
        }

        if (args.length == 3) {
            try {
                double x = Double.parseDouble(args[0]);
                double y = Double.parseDouble(args[1]);
                double z = Double.parseDouble(args[2]);

                if (y < 0 || y > 255) {
                    player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ A coordenada Y deve estar entre 0 e 255.");
                    return;
                }

                if (Math.abs(x) > 30000000 || Math.abs(z) > 30000000) {
                    player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Coordenadas X ou Z fora dos limites do mundo.");
                    return;
                }

                Location location = new Location(player.getWorld(), x, y, z);
                player.teleport(location);
                player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Teleportado para " + x + ", " + y + ", " + z + ".");
                return;
            } catch (NumberFormatException ignored) {
                player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Coordenadas inválidas. Use números em /tp <x> <y> <z>.");
                return;
            }
        }

        player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Jogador '" + args[0] + "' não encontrado ou comando inválido.");
    }
}