package com.hanielcota.essentials.utils;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Level;

@Getter
public class ConfigUtils {

    private final JavaPlugin plugin;
    private final File configFile;
    private FileConfiguration config;
    private final Map<String, Object> cache = new HashMap<>();
    private long lastModified;

    /**
     * Construtor da ConfigUtils.
     *
     * @param plugin Instância do plugin
     * @param fileName Nome do arquivo de configuração (ex: "config.yml")
     */
    public ConfigUtils(@NonNull JavaPlugin plugin, @NonNull String fileName) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), fileName);
        this.lastModified = 0L;
        loadConfig();
    }

    /**
     * Carrega ou recarrega a configuração, atualizando o cache se necessário.
     */
    public void loadConfig() {
        if (!configFile.exists()) {
            plugin.saveResource(configFile.getName(), false);
        }

        long currentModified = configFile.lastModified();
        if (config == null || currentModified > lastModified) {
            try {
                config = YamlConfiguration.loadConfiguration(configFile);
                lastModified = currentModified;
                cache.clear();
            } catch (Exception e) {
                Supplier<String> errorMessage = () -> "Erro ao carregar " + configFile.getName();
                plugin.getLogger().log(Level.SEVERE, errorMessage.get(), e);
            }
        }
    }

    /**
     * Salva a configuração no disco.
     */
    public void saveConfig() {
        try {
            config.save(configFile);
            lastModified = configFile.lastModified();
        } catch (IOException e) {
            Supplier<String> errorMessage = () -> "Erro ao salvar " + configFile.getName();
            plugin.getLogger().log(Level.SEVERE, errorMessage.get(), e);
        }
    }

    /**
     * Obtém um valor da configuração com cache.
     *
     * @param path Caminho da configuração
     * @param defaultValue Valor padrão se não encontrado
     * @return Valor encontrado ou padrão
     */

    @SuppressWarnings("unchecked")
    public <T> T get(@NonNull String path, T defaultValue) {
        loadConfig(); // Verifica se precisa recarregar

        // Verifica o cache primeiro
        if (cache.containsKey(path)) {
            return (T) cache.get(path);
        }

        T value = (T) config.get(path, defaultValue);
        cache.put(path, value);
        return value;
    }

    /**
     * Define um valor na configuração.
     *
     * @param path Caminho da configuração
     * @param value Valor a ser definido
     */
    public void set(@NonNull String path, Object value) {
        loadConfig();
        config.set(path, value);
        cache.put(path, value); // Atualiza o cache
        saveConfig();
    }

    /**
     * Verifica se um caminho existe na configuração.
     *
     * @param path Caminho a ser verificado
     * @return true se existe, false caso contrário
     */
    public boolean contains(@NonNull String path) {
        loadConfig();
        return config.contains(path);
    }

    /**
     * Limpa o cache manualmente, se necessário.
     */
    public void clearCache() {
        cache.clear();
    }
}