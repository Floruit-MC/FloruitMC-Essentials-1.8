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

@CommandAlias("top")
@Description("Teleporta o jogador para o topo do local atual")
@CommandPermission("essentials.top")
public class TopCommand extends BaseCommand {

    @Default
    public void onTop(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Este comando só pode ser usado por jogadores!");
            return;
        }

        Player player = (Player) sender;
        Location topLocation = TeleportUtils.findTopLocation(player.getLocation());
        if (topLocation == null) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Não foi possível encontrar um local seguro para teleportar!");
            return;
        }

        player.teleport(topLocation);
        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Teleportado para o topo!");
    }
}