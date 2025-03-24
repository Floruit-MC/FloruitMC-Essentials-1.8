package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.hanielcota.essentials.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("give")
@Description("Dá um item a um jogador específico")
@CommandPermission("essentials.give")
public class GiveCommand extends BaseCommand {

    @Default
    public void onGive(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Uso: /give <jogador> <item> [quantidade]");
            return;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ O jogador '" + args[0] + "' não foi encontrado ou está offline!");
            return;
        }

        Material material = Material.matchMaterial(args[1].toUpperCase());
        if (material == null) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ O item '" + args[1] + "' não foi encontrado!");
            return;
        }

        int amount = 1;
        if (args.length > 2) {
            try {
                amount = Integer.parseInt(args[2]);
                if (amount <= 0) {
                    sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ A quantidade deve ser maior que 0!");
                    return;
                }
            } catch (NumberFormatException e) {
                sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ A quantidade '" + args[2] + "' não é um número válido!");
                return;
            }
        }

        ItemStack item = new ItemStack(material, amount);
        InventoryUtils.addItemOrDrop(target, item);

        target.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Você recebeu " + amount + "x " + material.name().toLowerCase() + "!");
        sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Você deu " + amount + "x " + material.name().toLowerCase() + " para '" + target.getName() + "'!");
    }
}