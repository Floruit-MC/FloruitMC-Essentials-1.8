package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.hanielcota.essentials.utils.RepairUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("repair")
@Description("Repara o item na mão ou todos os itens do inventário")
@CommandPermission("essentials.repair")
public class RepairCommand extends BaseCommand {

    @Default
    public void onRepair(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Este comando só pode ser usado por jogadores!");
            return;
        }

        Player player = (Player) sender;
        ItemStack itemInHand = player.getInventory().getItemInHand();

        if (args.length == 0) {
            if (itemInHand == null || itemInHand.getType() == Material.AIR) {
                player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você precisa segurar um item para reparar!");
                return;
            }

            if (!RepairUtils.repairSingleItem(itemInHand)) return;

            player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ O item na sua mão foi reparado!");
            player.updateInventory();
            return;
        }

        if (!args[0].equalsIgnoreCase("all")) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Uso: /repair [all]");
            return;
        }

        if (!player.hasPermission("essentials.repair.all")) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não tem permissão para reparar todos os itens!");
            return;
        }

        RepairUtils.repairAllItems(player);
        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Todos os itens no seu inventário foram reparados!");
    }
}