package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.hanielcota.essentials.utils.CompactUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("compactar")
@Description("Compacta os blocos no seu inventário")
@CommandPermission("essentials.compactar")
public class CompactarCommand extends BaseCommand {

    @Default
    public void onCompactar(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Este comando só pode ser usado por jogadores!");
            return;
        }

        Player player = (Player) sender;
        if (!CompactUtils.compactInventory(player)) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Não havia itens para compactar no seu inventário!");
            return;
        }

        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Itens compactados com sucesso!");
    }
}