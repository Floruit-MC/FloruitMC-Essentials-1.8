package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.hanielcota.essentials.utils.GodUtils;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("god")
@Description("Ativa ou desativa o modo God")
@AllArgsConstructor
public class GodCommand extends BaseCommand {

    private final GodUtils godUtils;

    @Default
    @CommandPermission("essentials.god")
    @CommandCompletion("[tempo] [jogador]")
    public void onGod(CommandSender sender, String[] args) {
        if (!(sender instanceof Player) && args.length < 2) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Apenas jogadores podem usar esse comando sem argumentos!");
            return;
        }

        if (args.length >= 2 && !sender.hasPermission("essentials.god.others")) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não tem permissão para alterar o modo God de outros jogadores!");
            return;
        }

        if (args.length >= 2) {
            Player target = Bukkit.getPlayerExact(args[1]);
            if (target == null) {
                sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ O jogador '" + args[1] + "' não foi encontrado ou está offline!");
                return;
            }
            handleTarget(sender, target, args);
            return;
        }

        Player target = (Player) sender;
        handleTarget(sender, target, args);
    }

    private void handleTarget(CommandSender sender, Player target, String[] args) {
        if (!target.isOnline()) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ O jogador especificado não está online!");
            return;
        }

        if (args.length > 0) {
            handleGodWithTime(sender, target, args[0]);
            return;
        }

        toggleGodMode(sender, target);
    }

    private void handleGodWithTime(CommandSender sender, Player target, String timeArg) {
        int seconds;
        try {
            seconds = Integer.parseInt(timeArg);
        } catch (NumberFormatException e) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Por favor, insira um número válido para o tempo!");
            return;
        }

        if (!godUtils.enableGodWithTimeout(target, seconds)) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ " + target.getName() + " já está no modo God!");
            return;
        }

        sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Modo God ativado para " + target.getName() + " por " + seconds + " segundos!");
    }

    private void toggleGodMode(CommandSender sender, Player target) {
        if (godUtils.isGod(target)) {
            godUtils.disableGod(target);
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Modo God desativado para " + target.getName() + "!");
            return;
        }

        godUtils.enableGod(target);
        sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Modo God ativado para " + target.getName() + "!");
    }
}