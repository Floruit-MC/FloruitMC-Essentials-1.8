package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.hanielcota.essentials.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("title")
@Description("Envia um título personalizado para jogadores")
@CommandPermission("essentials.title")
public class TitleCommand extends BaseCommand {

    @Default
    public void onTitle(CommandSender sender, String[] args) {
        if (args == null || args.length < 2) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Uso: /title [jogador] <título> <subtítulo>");
            return;
        }

        if (args.length == 2) {
            String title = args[0];
            String subtitle = args[1];

            if (title.trim().isEmpty() || subtitle.trim().isEmpty()) {
                sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Título ou subtítulo não podem ser vazios!");
                return;
            }

            TitleUtils.sendTitleToAll(title, subtitle);
            sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Título enviado para todos os jogadores!");
            return;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Jogador '" + args[0] + "' não encontrado ou offline!");
            return;
        }

        String title = args[1];
        String subtitle = args[2];
        if (title.trim().isEmpty() || subtitle.trim().isEmpty()) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Título ou subtítulo não podem ser vazios!");
            return;
        }

        TitleUtils.sendTitle(target, title, subtitle);
        sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Título enviado para " + target.getName() + "!");
    }
}