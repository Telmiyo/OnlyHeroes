package me.godnitze.onlyheroes;

import me.godnitze.onlyheroes.Data.CustomConfig;
import me.godnitze.onlyheroes.Manager.ConfigManager;
import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.Manager.GameManager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import me.godnitze.onlyheroes.Manager.CommandManager;

public final class OnlyHeroes extends JavaPlugin {

    public GameManager gameManager;
    public CommandManager commandManager;
    public ConfigManager configManager;
    private boolean isSingleServerMode = false;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.print("OnlyHeroes initialized correctly");

        //initialize Managers
        this.gameManager = new GameManager(this);
        this.commandManager = new CommandManager();
        this.configManager = new ConfigManager(this);

        //SetCommands
        getCommand("oh").setExecutor(commandManager);

        //Get Config
        configManager.createFile("test");
        configManager.printAllFilesLog();
        if(configManager.getCustomFile("test") == null){ getLogger().warning("Test is null");}
        configManager.getCustomFile("test").addDefault("single-server-mode", "true");
        configManager.getCustomFile("test").addDefault("max-games", "-1");
        configManager.getCustomFile("test").options().copyDefaults(true);
        configManager.saveFile("test");


        this.isSingleServerMode = configManager.getCustomFile("test").getBoolean("single-server-mode");

        if (this.isSingleServerMode) { // If we're using single server
            this.gameManager.gamesLimit = 1;
        } else {
            this.gameManager.gamesLimit = configManager.getCustomFile("test").getInt("max-games");
        }

        if (configManager.getCustomFile("OnlyHeroes").getConfigurationSection("games") != null) {
            for (String gameName : configManager.getCustomFile("OnlyHeroes").getConfigurationSection("games").getKeys(false)) {
                Game game = new Game(gameName,this);
                boolean status = this.gameManager.registerGame(game);
                if (!status) {
                   // getLogger().warning("Can't load game " + gameName + "! Reached game limit for this server.");
                }
            }
        } else {
            // We can assume that no games are created
            getLogger().warning("No games have been created. Please create one using the creation command.");
        }

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.print("OnlyHeroes shut down correctly");

        gameManager.cleanup();
    }


}


