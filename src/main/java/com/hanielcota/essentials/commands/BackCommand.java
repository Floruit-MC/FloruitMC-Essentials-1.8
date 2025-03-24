package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.hanielcota.essentials.utils.TeleportUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("back")
@Description("Teleporta para o local da última morte ou teleporte")
@CommandPermission("essentials.back")
public class BackCommand extends BaseCommand {

    @Default
    public void onBack(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Este comando só pode ser usado por jogadores!");
            return;
        }

        Player player = (Player) sender;
        if (TeleportUtils.hasBackCooldown(player)) {
            long remainingTime = TeleportUtils.getBackRemainingTime(player);
            player.sendMessage("§e§lFLORUIT MC §f➤ §e✘ Aguarde " + remainingTime + " segundos para usar o /back novamente!");
            return;
        }

        Location lastLocation = TeleportUtils.getLastLocation(player);
        if (lastLocation == null) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não tem um local anterior para voltar!");
            return;
        }

        player.teleport(lastLocation);
        TeleportUtils.setBackCooldown(player);
        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Teleportado para o local anterior!");
    }
}
