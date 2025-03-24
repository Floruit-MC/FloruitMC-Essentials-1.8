package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.hanielcota.essentials.utils.TitleUtils;
import org.bukkit.command.CommandSender;

@CommandAlias("alerta|alert")
@Description("Envia um alerta destacado para todos os jogadores")
@CommandPermission("essentials.alerta")
public class AlertaCommand extends BaseCommand {

    @Default
    public void onAlerta(CommandSender sender, String[] args) {
        if (args == null || args.length == 0) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Uso: /alerta <mensagem>");
            return;
        }

        String message = String.join(" ", args);
        if (message.isBlank() || message.trim().isEmpty()) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ A mensagem não pode ser vazia!");
            return;
        }

        // Envia título para todos os jogadores online
        TitleUtils.sendTitleToAll("§c§lALERTA", "§f" + message, 20, 80, 20);

        // Mensagem de sucesso para quem executou o comando
        sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Alerta enviado com sucesso!");
    }
}
