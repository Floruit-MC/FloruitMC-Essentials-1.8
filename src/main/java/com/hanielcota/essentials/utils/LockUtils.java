package com.hanielcota.essentials.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.block.Block;

@UtilityClass
public class LockUtils {

    private boolean chestsLocked = false;

    /**
     * Tranca todos os baús do jogo.
     *
     * @return true se os baús foram trancados, false se já estavam trancados.
     */
    public boolean lockAllChests() {
        if (chestsLocked) {
            return false;
        }

        chestsLocked = true;
        return true;
    }

    /**
     * Destranca todos os baús do jogo.
     *
     * @return true se os baús foram destrancados, false se já estavam destrancados.
     */
    public boolean unlockAllChests() {
        if (!chestsLocked) {
            return false;
        }

        chestsLocked = false;
        return true;
    }

    /**
     * Verifica se os baús estão trancados globalmente.
     *
     * @return true se os baús estão trancados, false caso contrário.
     */
    public boolean areChestsLocked() {
        return chestsLocked;
    }

    /**
     * Verifica se um bloco é um baú.
     *
     * @param block O bloco a ser verificado.
     * @return true se é um baú, false caso contrário.
     */
    public boolean isChest(Block block) {
        if (block == null) {
            return false;
        }

        Material type = block.getType();
        return type == Material.CHEST || type == Material.TRAPPED_CHEST || type == Material.ENDER_CHEST;
    }
}