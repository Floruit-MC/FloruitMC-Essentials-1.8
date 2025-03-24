package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.hanielcota.essentials.utils.VanishUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("vanish|v")
@Description("Torna a si mesmo ou outro jogador invisível")
@CommandPermission("essentials.vanish")
public class VanishCommand extends BaseCommand {

    @Default
    public void onVanish(CommandSender sender, String[] args) {
        if (!(sender instanceof Player) && args.length < 1) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Este comando requer um jogador ou um alvo quando usado no console!");
            return;
        }

        if (args.length < 1) {
            Player player = (Player) sender;
            boolean isNowVanished = VanishUtils.toggleVanish(player);

            if (isNowVanished) {
                player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Você agora está invisível!");
                return;
            }

            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você agora está visível!");
            return;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ O jogador '" + args[0] + "' não foi encontrado ou está offline!");
            return;
        }

        boolean isNowVanished = VanishUtils.toggleVanish(target);
        if (isNowVanished) {
            target.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Você agora está invisível!");
            sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ O jogador '" + target.getName() + "' agora está invisível!");
            return;
        }

        target.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você agora está visível!");
        sender.sendMessage("§e§lFLORUIT MC §f➤ §e✔ O jogador '" + target.getName() + "' agora está visível!");
    }
}