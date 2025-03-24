package com.hanielcota.essentials.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class TeleportUtils {

    private static final Map<UUID, Location> lastLocations = new HashMap<>();
    private static final CooldownBuilder backCooldown = CooldownBuilder.builder()
            .duration(5) // 5 segundos de cooldown
            .timeUnit(TimeUnit.SECONDS)
            .cooldownName("back")
            .build();

    /**
     * Encontra o local mais alto seguro acima da posição inicial fornecida.
     *
     * @param start A localização inicial para começar a busca.
     * @return A localização segura mais alta ou null se não encontrada.
     */
    public Location findTopLocation(Location start) {
        if (start == null) return null;
        if (start.getWorld() == null) return null;

        World world = start.getWorld();
        int x = start.getBlockX();
        int z = start.getBlockZ();
        int maxHeight = world.getMaxHeight() - 1;

        for (int y = maxHeight; y > start.getBlockY(); y--) {
            Location check = new Location(world, x + 0.5, y, z + 0.5);
            if (!isSafeLocation(check)) continue;
            return check;
        }

        return null;
    }

    /**
     * Verifica se uma localização é segura para teleportar (pés e cabeça livres, chão sólido).
     *
     * @param location A localização a ser verificada.
     * @return true se segura, false caso contrário.
     */
    public boolean isSafeLocation(Location location) {
        if (location == null) return false;
        if (location.getWorld() == null) return false;

        World world = location.getWorld();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        Material feetBlock = world.getBlockAt(x, y, z).getType();
        Material headBlock = world.getBlockAt(x, y + 1, z).getType();
        Material belowBlock = world.getBlockAt(x, y - 1, z).getType();

        if (feetBlock.isSolid()) return false;
        if (headBlock.isSolid()) return false;
        if (belowBlock == Material.AIR) return false;
        return belowBlock != Material.LAVA;
    }

    /**
     * Armazena a última localização de teleporte ou morte do jogador.
     *
     * @param player O jogador.
     * @param location A localização a ser armazenada.
     */
    public void storeLastLocation(Player player, Location location) {
        lastLocations.put(player.getUniqueId(), location);
    }

    /**
     * Obtém a última localização armazenada do jogador.
     *
     * @param player O jogador.
     * @return A última localização ou null se não houver.
     */
    public Location getLastLocation(Player player) {
        return lastLocations.get(player.getUniqueId());
    }

    /**
     * Verifica se o jogador está em cooldown para o comando /back.
     *
     * @param player O jogador.
     * @return true se está em cooldown, false caso contrário.
     */
    public boolean hasBackCooldown(Player player) {
        return backCooldown.hasCooldown(player);
    }

    /**
     * Define o cooldown para o comando /back.
     *
     * @param player O jogador.
     */
    public void setBackCooldown(Player player) {
        backCooldown.setCooldown(player);
    }

    /**
     * Obtém o tempo restante do cooldown em segundos.
     *
     * @param player O jogador.
     * @return O tempo restante em segundos.
     */
    public long getBackRemainingTime(Player player) {
        return backCooldown.getRemainingTime(player);
    }
}