package com.hanielcota.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.CommandPermission;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@CommandAlias("cores|colors")
@Description("Exibe os códigos de cores disponíveis")
@CommandPermission("essentials.cores")
public class CoresCommand extends BaseCommand {

    @Default
    public void onCores(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.GRAY + "=== Códigos de Cores ===");
        sender.sendMessage(ChatColor.BLACK + "&0 - Preto");
        sender.sendMessage(ChatColor.DARK_BLUE + "&1 - Azul Escuro");
        sender.sendMessage(ChatColor.DARK_GREEN + "&2 - Verde Escuro");
        sender.sendMessage(ChatColor.DARK_AQUA + "&3 - Ciano Escuro");
        sender.sendMessage(ChatColor.DARK_RED + "&4 - Vermelho Escuro");
        sender.sendMessage(ChatColor.DARK_PURPLE + "&5 - Roxo Escuro");
        sender.sendMessage(ChatColor.GOLD + "&6 - Dourado");
        sender.sendMessage(ChatColor.GRAY + "&7 - Cinza");
        sender.sendMessage(ChatColor.DARK_GRAY + "&8 - Cinza Escuro");
        sender.sendMessage(ChatColor.BLUE + "&9 - Azul");
        sender.sendMessage(ChatColor.GREEN + "&a - Verde");
        sender.sendMessage(ChatColor.AQUA + "&b - Ciano");
        sender.sendMessage(ChatColor.RED + "&c - Vermelho");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "&d - Roxo Claro");
        sender.sendMessage(ChatColor.YELLOW + "&e - Amarelo");
        sender.sendMessage(ChatColor.WHITE + "&f - Branco");
        sender.sendMessage(ChatColor.GRAY + "=== Formatação ===");
        sender.sendMessage(ChatColor.BOLD + "&l - Negrito");
        sender.sendMessage(ChatColor.STRIKETHROUGH + "&m - Riscado");
        sender.sendMessage(ChatColor.UNDERLINE + "&n - Sublinhado");
        sender.sendMessage(ChatColor.ITALIC + "&o - Itálico");
        sender.sendMessage(ChatColor.RESET + "&r - Resetar");
    }
}