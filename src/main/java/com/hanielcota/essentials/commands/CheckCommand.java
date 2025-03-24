package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.hanielcota.essentials.menus.CheckMenu;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("check|playerinfo")
@Description("Exibe informações detalhadas de um jogador")
@CommandPermission("essentials.check")
@RequiredArgsConstructor
public class CheckCommand extends BaseCommand {

    @Default
    public void onCheck(Player sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Uso correto: /check <jogador>");
            return;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Jogador '" + args[0] + "' não encontrado ou está offline!");
            return;
        }

        new CheckMenu(target).open(sender);
    }
}
