package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("thor")
@CommandPermission("essentials.thor")
public class ThorCommand extends BaseCommand {

    @Default
    public void onCommand(Player p, String[] args) {
        if (args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                p.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Este jogador não foi encontrado!");
                return;
            }

            Bukkit.getWorld(target.getWorld().getName()).strikeLightning(target.getLocation());
            p.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Raio lançado na localização de " + target.getName() + "!");
            return;
        }

        Bukkit.getWorld(p.getWorld().getName()).strikeLightning(p.getLocation());
        p.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Raio lançado na sua localização!");
    }
}