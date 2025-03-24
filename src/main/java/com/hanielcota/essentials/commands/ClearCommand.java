package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import com.hanielcota.essentials.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("clear")
@CommandPermission("essentials.clear")
public class ClearCommand extends BaseCommand {

    @Default
    public void onClearCommand(Player player, String[] args) {
        if (args.length > 1) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Utilize: /clear <jogador> para limpar o inventário de alguém.");
            return;
        }

        if (args.length == 0 || isSelfTarget(args[0], player)) {
            if (InventoryUtils.isInventoryEmpty(player)) {
                player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Seu inventário já está vazio, não há nada para limpar!");
                return;
            }

            InventoryUtils.clearPlayerInventory(player);
            player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Seu inventário foi limpo com sucesso!");
            return;
        }

        String targetName = args[0];
        if (targetName.trim().isEmpty()) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Por favor, forneça um nome de jogador válido!");
            return;
        }

        Player target = Bukkit.getPlayer(targetName);
        if (target == null || !target.isOnline()) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ O jogador '" + targetName + "' não foi encontrado ou está offline!");
            return;
        }

        if (!player.hasPermission("essentials.clear.others")) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não tem permissão para limpar o inventário de outros jogadores!");
            return;
        }

        if (InventoryUtils.isInventoryEmpty(target)) {
            player.sendMessage("§e§lFLORUIT MC §f➤ §e✘ O inventário de '" + targetName + "' já está vazio, nada foi alterado!");
            return;
        }

        InventoryUtils.clearPlayerInventory(target);
        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Você limpou o inventário de '" + targetName + "' com sucesso!");
        target.sendMessage("§e§lFLORUIT MC §f➤ §e✔ Seu inventário foi limpo por '" + player.getName() + "'!");
    }

    private boolean isSelfTarget(String targetName, Player player) {
        return targetName != null && !targetName.trim().isEmpty() && targetName.equalsIgnoreCase(player.getName());
    }
}
