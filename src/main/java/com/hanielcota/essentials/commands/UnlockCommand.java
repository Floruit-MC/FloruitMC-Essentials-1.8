package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.hanielcota.essentials.utils.LockUtils;
import org.bukkit.command.CommandSender;

@CommandAlias("unlock")
@Description("Destranca todos os baús do jogo para todos")
@CommandPermission("essentials.unlock")
public class UnlockCommand extends BaseCommand {

    @Default
    public void onUnlock(CommandSender sender, String[] args) {
        if (!LockUtils.unlockAllChests()) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Os baús já estão destrancados!");
            return;
        }

        sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Todos os baús agora estão destrancados para todos!");
    }
}