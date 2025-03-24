package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.hanielcota.essentials.utils.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@CommandAlias("hat")
@Description("Coloca o item segurado como chapéu")
@CommandPermission("essentials.hat")
public class HatCommand extends BaseCommand {

    @Default
    public void onHat(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Este comando só pode ser usado por jogadores!");
            return;
        }

        Player player = (Player) sender;
        ItemStack itemInHand = player.getInventory().getItemInHand();
        if (itemInHand == null || itemInHand.getType() == Material.AIR) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você precisa segurar um item para usar como chapéu!");
            return;
        }

        PlayerInventory inventory = player.getInventory();
        ItemStack currentHelmet = inventory.getHelmet();
        if (currentHelmet != null) {
            InventoryUtils.addItemOrDrop(player, currentHelmet);
        }

        inventory.setItemInHand(null);
        inventory.setHelmet(itemInHand);
        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Você colocou o item como chapéu!");
        player.updateInventory();
    }
}