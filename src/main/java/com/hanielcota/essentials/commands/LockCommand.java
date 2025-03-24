package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.CommandPermission;
import org.bukkit.command.CommandSender;
import com.hanielcota.essentials.utils.LockUtils;

@CommandAlias("lock")
@Description("Tranca todos os baús do jogo para todos")
@CommandPermission("essentials.lock")
public class LockCommand extends BaseCommand {

    @Default
    public void onLock(CommandSender sender, String[] args) {
        if (!LockUtils.lockAllChests()) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Os baús já estão trancados!");
            return;
        }

        sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Todos os baús agora estão trancados para todos!");
    }
}