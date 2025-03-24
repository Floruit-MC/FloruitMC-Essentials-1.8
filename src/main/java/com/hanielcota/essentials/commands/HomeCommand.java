package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.hanielcota.essentials.models.Home;
import com.hanielcota.essentials.services.HomeService;
import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;

@CommandAlias("home")
@Description("Gerencia pontos de teletransporte pessoais (homes)")
@CommandPermission("essentials.home")
@AllArgsConstructor
public class HomeCommand extends BaseCommand {

    private final HomeService homeService;

    @Default
    @Syntax("[nome] - Teletransporta para um home")
    public void onHome(Player player, String[] args) throws SQLException {
        if (args.length == 0) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Uso: /home <nome>");
            return;
        }

        String homeName = args[0];
        if (homeName == null) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Home não encontrada!");
            return;
        }

        homeService.teleportToHome(player, homeName);
    }

    @Subcommand("set")
    @CommandAlias("sethome")
    @Syntax("[nome] - Define um novo home")
    @CommandPermission("essentials.home.set")
    public void onSetHome(Player player, String[] args) throws SQLException {
        String homeName = "home";
        if (args.length > 0) {
            homeName = args[0];
        }

        homeService.setHome(player, homeName);
    }

    @Subcommand("delete|del")
    @CommandAlias("delhome")
    @Syntax("<nome> - Remove um home existente")
    @CommandPermission("essentials.home.delete")
    public void onDelHome(Player player, String[] args) throws SQLException {
        if (args.length == 0) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Uso: /delhome <nome>");
            return;
        }

        homeService.deleteHome(player, args[0]);
    }

    @Subcommand("list")
    @CommandAlias("homelist")
    @Description("Lista todos os seus homes")
    @CommandPermission("essentials.home.list")
    public void onHomeList(Player player) throws SQLException {
        List<Home> homes = homeService.listHomes(player);
        if (homes.isEmpty()) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não tem nenhum home definido!");
            return;
        }

        StringBuilder homeList = new StringBuilder("§a§lFLORUIT MC §f➤ §a✔ Seus homes (§e" + homes.size() + "§a):\n");
        for (Home home : homes) {
            homeList.append("§f- ")
                    .append(home.getName())
                    .append(" §7(")
                    .append(formatLocation(home.getLocation()))
                    .append(")\n");
        }
        player.sendMessage(homeList.toString());
    }

    private String formatLocation(Location location) {
        return String.format("x:%.1f, y:%.1f, z:%.1f, mundo:%s",
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getWorld().getName());
    }
}