package com.hanielcota.essentials;

import co.aikar.commands.PaperCommandManager;
import com.hanielcota.essentials.commands.*;
import com.hanielcota.essentials.listeners.*;
import com.hanielcota.essentials.repositories.HomeRepository;
import com.hanielcota.essentials.services.HomeService;
import com.hanielcota.essentials.utils.ConfigUtils;
import com.hanielcota.essentials.utils.GodUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

@Getter
public final class EssentialsPlugin extends JavaPlugin {

    private ConfigUtils configUtils;
    private GodUtils godUtils;
    private HomeRepository homeRepository;
    private HomeService homeService;


    @Override
    public void onEnable() {
        initializeDatabase();
        initializeUtils();
        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        try {
            if (homeRepository != null) {
                homeRepository.close();
            }
        } catch (SQLException e) {
            getLogger().severe("Erro ao fechar banco de dados: " + e.getMessage());
        }
    }

    private void initializeUtils() {
        configUtils = new ConfigUtils(this, "config.yml");
        godUtils = new GodUtils(this);
    }

    private void initializeDatabase() {
        try {
            homeRepository = new HomeRepository(getDataFolder() + "/homes.db");
            homeService = new HomeService(homeRepository);
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§cErro ao conectar com o banco de dados: " + e.getMessage());
        }
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new MobSpawnListener(), this);
        pluginManager.registerEvents(new WeatherChangeListener(), this);
        pluginManager.registerEvents(new FireSpreadListener(), this);
        pluginManager.registerEvents(new ExplosionDamageListener(), this);
        pluginManager.registerEvents(new IceSnowControlListener(), this);
        pluginManager.registerEvents(new FluidFlowListener(), this);

        pluginManager.registerEvents(new BedInteractionListener(), this);
        pluginManager.registerEvents(new DeathMessageListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(this), this);

        pluginManager.registerEvents(new MobSpawnListener(), this);
        pluginManager.registerEvents(new WeatherChangeListener(), this);
        pluginManager.registerEvents(new FireSpreadListener(), this);
        pluginManager.registerEvents(new ExplosionDamageListener(), this);
        pluginManager.registerEvents(new IceSnowControlListener(), this);
        pluginManager.registerEvents(new FluidFlowListener(), this);

        pluginManager.registerEvents(new BedInteractionListener(), this);
        pluginManager.registerEvents(new DeathMessageListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(this), this);

        pluginManager.registerEvents(new CommandBlocker(configUtils), this);
        pluginManager.registerEvents(new GodListener(godUtils), this);
        pluginManager.registerEvents(new VanishListener(), this);
        pluginManager.registerEvents(new LockListener(), this);
        pluginManager.registerEvents(new TeleportListener(), this);

        pluginManager.registerEvents(new NoFallListener(), this);
        pluginManager.registerEvents(new NoHungerListener(), this);
        pluginManager.registerEvents(new NoCactusDamageListener(), this);
        pluginManager.registerEvents(new NoFireLavaDamageListener(), this);
        pluginManager.registerEvents(new NoSuffocationDamageListener(), this);
        pluginManager.registerEvents(new NoVoidListener(), this);
        pluginManager.registerEvents(new BlockCraftingListener(this), this);

        pluginManager.registerEvents(new CheckMenuListener(), this);
        pluginManager.registerEvents(new ColoredSignListener(), this);
    }

    private void registerCommands() {
        PaperCommandManager commandManager = new PaperCommandManager(this);

        commandManager.registerCommand(new GamemodeCommand());
        commandManager.registerCommand(new FlyCommand());
        commandManager.registerCommand(new FlySpeedCommand());
        commandManager.registerCommand(new WalkSpeedCommand());
        commandManager.registerCommand(new TeleportCommand());
        commandManager.registerCommand(new HealCommand());
        commandManager.registerCommand(new FeedCommand());
        commandManager.registerCommand(new KillCommand());

        commandManager.registerCommand(new ClearCommand());
        commandManager.registerCommand(new GodCommand(godUtils));
        commandManager.registerCommand(new GiveCommand());
        commandManager.registerCommand(new VanishCommand());
        commandManager.registerCommand(new InvseeCommand());
        commandManager.registerCommand(new HatCommand());
        commandManager.registerCommand(new RepairCommand());
        commandManager.registerCommand(new TopCommand());
        commandManager.registerCommand(new UnlockCommand());
        commandManager.registerCommand(new LockCommand());
        commandManager.registerCommand(new CompactarCommand());
        commandManager.registerCommand(new BackCommand());
        commandManager.registerCommand(new KillMobsCommand());
        commandManager.registerCommand(new CoresCommand());
        commandManager.registerCommand(new ClearChatCommand());
        commandManager.registerCommand(new CraftCommand());
        commandManager.registerCommand(new CoresCommand());
        commandManager.registerCommand(new SlimeCommand());
        commandManager.registerCommand(new EnderChestCommand());
        commandManager.registerCommand(new DerreterCommand());
        commandManager.registerCommand(new AlertaCommand());
        commandManager.registerCommand(new TitleCommand());
        commandManager.registerCommand(new DivulgarCommand());
        commandManager.registerCommand(new PuxarCommand());
        commandManager.registerCommand(new HeadCommand());
        commandManager.registerCommand(new LuzCommand());
        commandManager.registerCommand(new CheckCommand());
        commandManager.registerCommand(new VipCommand(this));
        commandManager.registerCommand(new WarpCommand(this));
        commandManager.registerCommand(new RocketCommand());
        commandManager.registerCommand(new ThorCommand());
        commandManager.registerCommand(new KickCommand());
        commandManager.registerCommand(new SairComEstilo());
        commandManager.registerCommand(new WhoCommand());
        commandManager.registerCommand(new TpaCommand());
        commandManager.registerCommand(new HomeCommand(homeService));
    }


}