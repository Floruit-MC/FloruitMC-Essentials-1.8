package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@CommandAlias("luz|nightvision")
@Description("Aplica ou remove visão noturna do jogador")
@CommandPermission("essentials.luz")
public class LuzCommand extends BaseCommand {

    private static final int DURATION_TICKS = 6000; // 5 minutos (20 ticks por segundo * 60 * 5)

    @Default
    public void onLuz(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Este comando só pode ser usado por jogadores!");
            return;
        }

        Player player = (Player) sender;
        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Visão noturna desativada!");
            return;
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, DURATION_TICKS, 1, false, false));
        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Visão noturna ativada por 5 minutos!");
    }
}