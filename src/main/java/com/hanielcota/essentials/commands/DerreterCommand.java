package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.CommandPermission;
import com.hanielcota.essentials.utils.SmeltUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("derreter|smelt")
@Description("Derrete os itens no seu inventário")
@CommandPermission("essentials.derreter")
public class DerreterCommand extends BaseCommand {

    @Default
    public void onDerreter(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Este comando só pode ser usado por jogadores!");
            return;
        }

        Player player = (Player) sender;
        if (!SmeltUtils.smeltInventory(player)) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Não havia itens para derreter no seu inventário!");
            return;
        }

        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Itens derretidos com sucesso!");
    }
}