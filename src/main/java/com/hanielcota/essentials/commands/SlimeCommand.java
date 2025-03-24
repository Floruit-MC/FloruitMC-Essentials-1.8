package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

@CommandAlias("slime|slimechunk")
@Description("Verifica se você está em um slime chunk")
@CommandPermission("essentials.slime")
public class SlimeCommand extends BaseCommand {

    @Default
    public void onSlime(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Este comando só pode ser usado por jogadores!");
            return;
        }

        Player player = (Player) sender;
        Chunk chunk = player.getLocation().getChunk();

        if (!isSlimeChunk(player.getWorld(), chunk.getX(), chunk.getZ())) {
            player.sendMessage("§c§lFLORUIT MC §f➤ §c✘ Você não está em um slime chunk. (X: " + chunk.getX() + ", Z: " + chunk.getZ() + ")");
            return;
        }

        player.sendMessage("§a§lFLORUIT MC §f➤ §a✔ Você está em um slime chunk! (X: " + chunk.getX() + ", Z: " + chunk.getZ() + ")");
    }

    private boolean isSlimeChunk(World world, int chunkX, int chunkZ) {
        long seed = world.getSeed();
        Random random = new Random(seed + ((long) chunkX * chunkX * 0x4c1906) +
                (chunkX * 0x5ac0dbL) +
                ((long) chunkZ * chunkZ) * 0x4307a7L +
                (chunkZ * 0x5f24fL) ^ 0x3ad8025f
        );

        return random.nextInt(10) == 0;
    }
}