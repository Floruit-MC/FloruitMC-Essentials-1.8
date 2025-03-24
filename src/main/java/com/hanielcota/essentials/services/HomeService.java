package com.hanielcota.essentials.services;

import com.hanielcota.essentials.models.Home;
import com.hanielcota.essentials.repositories.HomeRepository;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;

public class HomeService {

    private final HomeRepository repository;
    private static final int MAX_HOMES = 3;

    public HomeService(HomeRepository repository) {
        this.repository = repository;
    }

    public void teleportToHome(Player player, String homeName) throws SQLException {
        Home home = repository.findHome(player.getUniqueId(), homeName);
        if (home == null) {
            sendHomeNotFoundMessage(player, homeName);
            return;
        }

        player.teleport(home.getLocation());
        player.sendMessage("&a&lFLORUIT MC &f➤ &a✔ Teleportado para o home &e" + homeName + "&a!");
    }

    public void setHome(Player player, String homeName) throws SQLException {
        if (homeName.length() > 16) {
            player.sendMessage("&c&lFLORUIT MC &f➤ &c✘ O nome do home não pode ter mais de 16 caracteres!");
            return;
        }

        int homeCount = repository.countHomes(player.getUniqueId());
        if (homeCount >= MAX_HOMES && !player.hasPermission("essentials.home.unlimited")) {
            player.sendMessage("&c&lFLORUIT MC &f➤ &c✘ Você atingiu o limite de " + MAX_HOMES + " homes! Use /delhome para remover um.");
            return;
        }

        Home home = new Home(player.getUniqueId().toString(), homeName, player.getLocation());
        repository.saveHome(home);
        player.sendMessage("&a&lFLORUIT MC &f➤ &a✔ Home &e" + homeName + "&a definido com sucesso!");
    }

    public void deleteHome(Player player, String homeName) throws SQLException {
        Home home = repository.findHome(player.getUniqueId(), homeName);
        if (home == null) {
            player.sendMessage("&c&lFLORUIT MC &f➤ &c✘ Você não tem um home chamado &e" + homeName + "&c!");
            return;
        }

        repository.deleteHome(player.getUniqueId(), homeName);
        player.sendMessage("&a&lFLORUIT MC &f➤ &a✔ Home &e" + homeName + "&a removido com sucesso!");
    }

    public List<Home> listHomes(Player player) throws SQLException {
        return repository.findHomesByPlayer(player.getUniqueId());
    }

    private void sendHomeNotFoundMessage(Player player, String homeName) throws SQLException {
        List<Home> homes = listHomes(player);
        if (homes.isEmpty()) return;

        StringBuilder message = new StringBuilder("&c&lFLORUIT MC &f➤ &c✘ Você não tem um home chamado &e" + homeName + "&c! Seus homes disponíveis (&e" + homes.size() + "&c):\n");
        for (Home home : homes) {
            message.append("§f- ")
                    .append(home.getName())
                    .append(" §7(x:")
                    .append(String.format("%.1f", home.getLocation().getX()))
                    .append(", y:")
                    .append(String.format("%.1f", home.getLocation().getY()))
                    .append(", z:")
                    .append(String.format("%.1f", home.getLocation().getZ()))
                    .append(", mundo:")
                    .append(home.getLocation().getWorld().getName())
                    .append(")\n");
        }
        player.sendMessage(message.toString());
    }
}