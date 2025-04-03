package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import com.hanielcota.essentials.utils.ConfigUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@RequiredArgsConstructor
@CommandAlias("vip")
public class VipCommand extends BaseCommand {

    private final JavaPlugin plugin;
    private final ConfigUtils configUtils;

    public VipCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        configUtils = new ConfigUtils(plugin, "vip.yml");
    }

    @CommandAlias("vip")
    @CommandPermission("essentials.vip")
    public void onDefault(Player player, String[] args) {
        if (args.length > 0) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Uso: /vip ou /vip set");
            return;
        }

        Location vipLocation = getVipLocation();
        if (vipLocation == null) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ A localização VIP ainda não foi definida!");
            return;
        }

        player.teleport(vipLocation);
        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Teleportado para a localização VIP!");
    }

    @Subcommand("set")
    @CommandPermission("essentials.vip.set")
    public void onSet(Player player, String[] args) {
        if (args.length > 0) { // Corrigido para > 0, pois "set" não deve aceitar argumentos extras
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Uso: /vip set");
            return;
        }

        setVipLocation(player);
    }

    private void setVipLocation(Player player) {
        Location location = player.getLocation();

        configUtils.set("vip-location.world", location.getWorld().getName());
        configUtils.set("vip-location.x", location.getX());
        configUtils.set("vip-location.y", location.getY());
        configUtils.set("vip-location.z", location.getZ());
        configUtils.set("vip-location.yaw", location.getYaw());
        configUtils.set("vip-location.pitch", location.getPitch());

        configUtils.saveConfig(); // Garante que as alterações sejam salvas no arquivo
        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Localização VIP definida com sucesso!");
    }

    private Location getVipLocation() {
        if (!configUtils.contains("vip-location.world")) return null;

        String worldName = configUtils.get("vip-location.world", "");
        if (plugin.getServer().getWorld(worldName) == null) return null; // Verifica se o mundo existe

        double x = configUtils.get("vip-location.x", 0.0);
        double y = configUtils.get("vip-location.y", 0.0);
        double z = configUtils.get("vip-location.z", 0.0);
        Number yawValue = configUtils.get("vip-location.yaw", 0.0); // Usa Number para evitar cast direto
        Number pitchValue = configUtils.get("vip-location.pitch", 0.0);

        float yaw = yawValue.floatValue(); // Converte para float
        float pitch = pitchValue.floatValue(); // Converte para float

        return new Location(plugin.getServer().getWorld(worldName), x, y, z, yaw, pitch);
    }
}