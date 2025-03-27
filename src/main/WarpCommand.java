package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import com.hanielcota.essentials.utils.ConfigUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@CommandAlias("warps|warp")
public class WarpCommand extends BaseCommand {

    private final JavaPlugin plugin;
    private final ConfigUtils configUtils;

    public WarpCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configUtils = new ConfigUtils(plugin, "warps.yml");
    }

    @CommandAlias("warps|warp")
    @CommandPermission("essentials.warps")
    public void onDefault(Player player, String[] args) {
        if (args.length > 0) {
            teleportToWarp(player, args[0]);
            return;
        }

        List<String> warps = getWarpList();
        if (warps.isEmpty()) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Nenhum warp disponível no momento!");
            return;
        }

        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Warps disponíveis: " + String.join(", ", warps));
    }

    @Subcommand("set")
    @CommandPermission("essentials.warps.set")
    public void onSet(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Uso: /warps set <nome>");
            return;
        }

        if (args.length > 1) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Uso: /warps set <nome> (apenas um nome é permitido)");
            return;
        }

        String warpName = args[0].toLowerCase();
        setWarp(player, warpName);
    }

    private void teleportToWarp(Player player, String warpName) {
        Location warpLocation = getWarpLocation(warpName.toLowerCase());
        if (warpLocation == null) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ O warp '" + warpName + "' não existe!");
            return;
        }

        player.teleport(warpLocation);
        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Teleportado para o warp '" + warpName + "'!");
    }

    private void setWarp(Player player, String warpName) {
        Location location = player.getLocation();

        String path = "warps." + warpName + ".";
        configUtils.set(path + "world", location.getWorld().getName());
        configUtils.set(path + "x", location.getX());
        configUtils.set(path + "y", location.getY());
        configUtils.set(path + "z", location.getZ());
        configUtils.set(path + "yaw", location.getYaw());
        configUtils.set(path + "pitch", location.getPitch());

        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Warp '" + warpName + "' definido com sucesso!");
    }

    private Location getWarpLocation(String warpName) {
        String path = "warps." + warpName + ".";
        if (!configUtils.contains(path + "world")) return null;

        String worldName = configUtils.get(path + "world", "");
        double x = configUtils.get(path + "x", 0.0);
        double y = configUtils.get(path + "y", 0.0);
        double z = configUtils.get(path + "z", 0.0);

        Number yawNumber = configUtils.get(path + "yaw", 0.0);
        Number pitchNumber = configUtils.get(path + "pitch", 0.0);
        float yaw = yawNumber.floatValue();
        float pitch = pitchNumber.floatValue();

        World world = plugin.getServer().getWorld(worldName);
        if (world == null) {
            plugin.getLogger().warning("Mundo inválido no warp '" + warpName + "': " + worldName);
            return null;
        }

        return new Location(world, x, y, z, yaw, pitch);
    }

    private List<String> getWarpList() {
        List<String> warps = new ArrayList<>();
        if (!configUtils.contains("warps")) return warps;

        Object warpsSection = configUtils.get("warps", null);
        if (!(warpsSection instanceof ConfigurationSection)) return warps;

        ConfigurationSection section = (ConfigurationSection) warpsSection;
        warps.addAll(section.getKeys(false));
        return warps;
    }
}
