package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

@CommandAlias("killmobs")
@Description("Mata todos os mobs vivos no servidor")
@CommandPermission("essentials.killmobs")
public class KillMobsCommand extends BaseCommand {

    @Default
    public void onKillMobs(CommandSender sender, String[] args) {
        int killedCount = killAllMobs();
        if (killedCount == 0) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Não havia mobs vivos para matar!");
            return;
        }

        sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Foram mortos " + killedCount + " mobs!");
    }

    private int killAllMobs() {
        int count = 0;
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof LivingEntity)) continue;
                if (entity instanceof Player) continue;

                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.setHealth(0.0);
                count++;
            }
        }
        return count;
    }
}