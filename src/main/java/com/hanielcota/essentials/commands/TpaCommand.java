package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.hanielcota.essentials.EssentialsPlugin;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CommandAlias("tpa")
@Description("Solicita teletransporte para outro jogador")
@CommandPermission("essentials.tpa")
public class TpaCommand extends BaseCommand {

    private final Map<UUID, UUID> pendingRequests = new HashMap<>(); // Sender -> Target
    private static final int REQUEST_TIMEOUT = 60 * 20; // 60 segundos em ticks

    @Dependency
    private EssentialsPlugin plugin;

    @Default
    @CommandCompletion("@players")
    public void onTpa(Player sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Uso: /tpa <jogador>");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Jogador §e" + args[0] + "§c não encontrado ou offline!");
            return;
        }

        if (target.equals(sender)) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não pode solicitar teletransporte para si mesmo!");
            return;
        }

        if (pendingRequests.containsKey(sender.getUniqueId())) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você já tem uma solicitação de TPA pendente!");
            return;
        }

        pendingRequests.put(sender.getUniqueId(), target.getUniqueId());
        sendRequestMessage(sender, target);
        scheduleRequestTimeout(sender, target);
    }

    @Subcommand("accept|aceitar")
    @CommandAlias("tpaccept")
    public void onAccept(Player target) {
        UUID targetId = target.getUniqueId();
        UUID senderId = getPendingSender(targetId);
        if (senderId == null) {
            target.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não tem solicitações de TPA pendentes!");
            return;
        }

        Player sender = Bukkit.getPlayer(senderId);
        if (sender == null) {
            target.sendMessage("§c§lFLORUIT MC §f➤ §c✘ O jogador que enviou a solicitação não está mais online!");
            pendingRequests.remove(senderId);
            return;
        }

        pendingRequests.remove(senderId);
        sender.teleport(target.getLocation());
        sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Teletransporte aceito! Você foi teleportado até §e" + target.getName() + "§a!");
        target.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Você aceitou o teletransporte de §e" + sender.getName() + "§a!");
    }

    @Subcommand("deny|recusar")
    @CommandAlias("tpdeny")
    public void onDeny(Player target) {
        UUID targetId = target.getUniqueId();
        UUID senderId = getPendingSender(targetId);
        if (senderId == null) {
            target.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não tem solicitações de TPA pendentes!");
            return;
        }

        Player sender = Bukkit.getPlayer(senderId);
        pendingRequests.remove(senderId);

        target.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Solicitação de TPA recusada!");
        if (sender == null) return;

        sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ " + target.getName() + " recusou sua solicitação de TPA!");
    }

    private UUID getPendingSender(UUID targetId) {
        for (Map.Entry<UUID, UUID> entry : pendingRequests.entrySet()) {
            if (!entry.getValue().equals(targetId)) continue;
            return entry.getKey();
        }
        return null;
    }

    private void sendRequestMessage(Player sender, Player target) {
        TextComponent acceptButton = new TextComponent("§a[Aceitar]");
        acceptButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa accept"));
        acceptButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aClique para aceitar").create()));

        TextComponent denyButton = new TextComponent("§c[Recusar]");
        denyButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa deny"));
        denyButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cClique para recusar").create()));

        TextComponent message = new TextComponent("§e§lFLORUIT MC §f➤ §e✔ " + sender.getName() + "§7 quer se teleportar até você: ");
        message.addExtra(acceptButton);
        message.addExtra(" ");
        message.addExtra(denyButton);

        sender.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Solicitação de TPA enviada para §e" + target.getName() + "§a!");
        target.spigot().sendMessage(message);
    }

    private void scheduleRequestTimeout(Player sender, Player target) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!pendingRequests.containsKey(sender.getUniqueId())) return;

                pendingRequests.remove(sender.getUniqueId());
                sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Sua solicitação de TPA para §e" + target.getName() + "§c expirou!");
                target.sendMessage("§c§lFLORUIT MC §f➤ §c✘ A solicitação de TPA de §e" + sender.getName() + "§c expirou!");
            }
        }.runTaskLaterAsynchronously(plugin, REQUEST_TIMEOUT);
    }
}