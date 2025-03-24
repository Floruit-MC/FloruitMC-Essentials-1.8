package com.hanielcota.essentials.menus;

import com.hanielcota.essentials.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class CheckMenu implements InventoryHolder {

    private final Player target;
    private final Inventory inventory;

    public CheckMenu(Player target) {
        this.target = target;
        this.inventory = Bukkit.createInventory(this, 27, "Info de " + target.getName());
        setupMenu();
    }

    private void setupMenu() {
        inventory.setItem(10, createPlayerHead());
        inventory.setItem(11, createLocationItem());
        inventory.setItem(12, createHealthItem());
        inventory.setItem(13, createExpItem());
        inventory.setItem(14, createGameModeItem());
        inventory.setItem(15, createEffectsItem());
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    private ItemStack createPlayerHead() {
        return new ItemBuilder(Material.SKULL_ITEM)
                .skullOwner(target.getName())
                .name("§e" + target.getName())
                .lore(List.of("§7UUID: §f" + target.getUniqueId()))
                .build();
    }

    private ItemStack createLocationItem() {
        Location loc = target.getLocation();
        return new ItemBuilder(Material.COMPASS)
                .name("§aLocalização")
                .lore(List.of(
                        "§7X: §f" + formatDouble(loc.getX()),
                        "§7Y: §f" + formatDouble(loc.getY()),
                        "§7Z: §f" + formatDouble(loc.getZ()),
                        "§7Mundo: §f" + loc.getWorld().getName()
                )).build();
    }

    private ItemStack createHealthItem() {
        return new ItemBuilder(Material.APPLE)
                .name("§c❤ Saúde & Fome")
                .lore(List.of(
                        "§7Vida: §f" + formatDouble(target.getHealth()) + "§c ❤",
                        "§7Máximo: §f" + formatDouble(target.getMaxHealth()) + "§c ❤",
                        "§7Fome: §f" + target.getFoodLevel() + "§6 🍗"
                )).build();
    }

    private ItemStack createExpItem() {
        return new ItemBuilder(Material.EXP_BOTTLE)
                .name("§bExperiência")
                .lore(List.of(
                        "§7Nível: §f" + target.getLevel(),
                        "§7Progresso: §f" + formatDouble(target.getExp() * 100) + "%"
                )).build();
    }

    private ItemStack createGameModeItem() {
        Material modeMaterial = getGameModeMaterial(target.getGameMode());
        return new ItemBuilder(modeMaterial)
                .name("§6Modo de Jogo")
                .lore(List.of("§7Modo Atual: §f" + target.getGameMode().name()))
                .build();
    }

    private ItemStack createEffectsItem() {
        List<String> effects = getActiveEffects();
        return new ItemBuilder(Material.POTION)
                .name("§dEfeitos Ativos")
                .lore(effects.isEmpty() ? List.of("§7Nenhum efeito ativo") : effects)
                .build();
    }

    private Material getGameModeMaterial(GameMode gameMode) {
        switch (gameMode) {
            case SURVIVAL:
                return Material.STONE_SWORD;
            case CREATIVE:
                return Material.FEATHER;
            case ADVENTURE:
                return Material.MAP;
            case SPECTATOR:
                return Material.GLASS;
            default:
                return Material.BARRIER;
        }
    }

    private String formatDouble(double value) {
        return String.format("%.2f", value);
    }

    private List<String> getActiveEffects() {
        List<String> effects = new ArrayList<>();
        for (PotionEffect effect : target.getActivePotionEffects()) {
            if (effect == null) continue;
            effects.add(String.format("§7%s §f%d §8(%ds)",
                    effect.getType().getName(),
                    effect.getAmplifier() + 1,
                    effect.getDuration() / 20));
        }
        return effects;
    }
}
