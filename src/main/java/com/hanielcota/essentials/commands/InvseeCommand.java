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

@CommandAlias("invsee")
@Description("Permite ver e editar o inventário de outro jogador")
@CommandPermission("essentials.invsee")
public class InvseeCommand extends BaseCommand {

    @Default
    public void onInvsee(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Este comando só pode ser usado por jogadores!");
            return;
        }

        Player player = (Player) sender;
        if (args.length < 1) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Uso: /invsee <jogador>");
            return;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ O jogador '" + args[0] + "' não foi encontrado ou está offline!");
            return;
        }

        if (target.equals(player)) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não pode ver seu próprio inventário com este comando!");
            return;
        }

        if (VanishUtils.isVanished(target)) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não pode ver o inventário de um jogador em vanish!");
            return;
        }

        player.openInventory(target.getInventory());
        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Abrindo o inventário de '" + target.getName() + "'!");
    }
}