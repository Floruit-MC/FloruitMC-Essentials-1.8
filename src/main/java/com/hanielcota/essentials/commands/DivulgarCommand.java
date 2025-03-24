package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.hanielcota.essentials.utils.CooldownBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
@CommandAlias("divulgar|announce")
@Description("Envia uma mensagem de divulgação para todos os jogadores")
@CommandPermission("essentials.divulgar")
@RequiredArgsConstructor
public class DivulgarCommand extends BaseCommand {

    private static final String COOLDOWN_NAME = "divulgar";
    private final Map<UUID, CooldownBuilder> playerCooldowns = new HashMap<>();

    @Default
    public void onDivulgar(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Este comando só pode ser usado por jogadores!");
            return;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Uso correto: /divulgar <mensagem>");
            return;
        }

        String message = String.join(" ", args).trim();
        if (message.isEmpty()) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ A mensagem de divulgação não pode estar vazia!");
            return;
        }

        if (isInCooldown(player)) {
            long remainingTime = getCooldown(player).getRemainingTime(player);
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Aguarde " + remainingTime + " segundos para usar /divulgar novamente!");
            return;
        }

        broadcastMessage("§e§lFLORUIT MC §f➤ §e✔ [Divulgação] " + message);
        setCooldown(player);
        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Mensagem de divulgação enviada com sucesso!");
    }

    private boolean isInCooldown(Player player) {
        CooldownBuilder cooldown = playerCooldowns.get(player.getUniqueId());
        if (cooldown == null) return false;
        return cooldown.hasCooldown(player);
    }

    private CooldownBuilder getCooldown(Player player) {
        return playerCooldowns.computeIfAbsent(player.getUniqueId(), id -> CooldownBuilder.builder()
                .duration(1)
                .timeUnit(TimeUnit.HOURS)
                .cooldownName(COOLDOWN_NAME)
                .build());
    }

    private void setCooldown(Player player) {
        getCooldown(player).setCooldown(player);
    }

    private void broadcastMessage(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player == null) continue;
            player.sendMessage(message);
        }
    }
}