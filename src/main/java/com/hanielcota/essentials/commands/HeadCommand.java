package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.hanielcota.essentials.utils.ItemBuilder;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@CommandAlias("head|cabeça")
@Description("Obtém a cabeça de um jogador.")
@CommandPermission("essentials.head")
@RequiredArgsConstructor
public class HeadCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    public void onHead(Player sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Uso correto: /head <jogador>");
            return;
        }

        String targetName = args[0];
        OfflinePlayer target = Bukkit.getPlayerExact(targetName);
        if (target == null) {
            target = Arrays.stream(Bukkit.getOfflinePlayers())
                    .filter(p -> p.getName() != null && p.getName().equalsIgnoreCase(targetName))
                    .findFirst()
                    .orElse(null);
        }

        if (target == null || target.getName() == null) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Jogador §e" + targetName + " §cnão encontrado ou nunca entrou no servidor!");
            return;
        }

        ItemStack head = new ItemBuilder(Material.SKULL_ITEM)
                .skullOwner(target.getName())
                .name("§eCabeça de " + target.getName())
                .build();

        sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Você pegou a cabeça de §e" + target.getName() + "§a!");
        sender.getInventory().addItem(head).forEach((slot, item) ->
                sender.getWorld().dropItemNaturally(sender.getLocation(), item));
    }
}