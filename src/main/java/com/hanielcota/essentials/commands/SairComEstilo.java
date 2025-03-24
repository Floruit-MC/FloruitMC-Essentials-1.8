package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Dependency;
import com.hanielcota.essentials.EssentialsPlugin;
import com.hanielcota.essentials.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("saircomestilo")
@CommandPermission("essentials.saircomestilo")
public class SairComEstilo extends BaseCommand {

    @Dependency
    private EssentialsPlugin plugin;

    @Default
    public void onCommand(Player p) {
        scheduleCountdown();
        scheduleExit(p);
    }

    private void scheduleCountdown() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> TitleUtils.sendTitleToAll("§f§l3", ""), 20L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> TitleUtils.sendTitleToAll("§f§l2", ""), 40L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> TitleUtils.sendTitleToAll("§f§l1", ""), 60L);
    }

    private void scheduleExit(Player p) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            p.kickPlayer("§c§lFLORUIT MC\n§c\n§cVocê saiu com estilo!");
            TitleUtils.sendTitleToAll("§c§l" + p.getName(), "§c§lFLORUIT MC §f➤ §c✘ Saiu do servidor.");
            Bukkit.getWorld(p.getWorld().getName()).strikeLightning(p.getLocation());
        }, 80L);
    }
}