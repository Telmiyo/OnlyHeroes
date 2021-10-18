package me.godnitze.onlyheroes;

import me.godnitze.onlyheroes.Manager.ConfigManager;
import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.Manager.GameManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import me.godnitze.onlyheroes.Manager.CommandManager;

public final class OnlyHeroes extends JavaPlugin {

    public GameManager gameManager;
    public CommandManager commandManager;
    private boolean isSingleServerMode = false;

    private ConfigManager configManager;

    public FileConfiguration configFile;
    public FileConfiguration gamesFile;


    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.print("OnlyHeroes initialized correctly");

        //initialize Managers
        this.gameManager = new GameManager(this);
        this.commandManager = new CommandManager();
        ConfigManager.getInstance().setPlugin(this);

        //SetCommands
        getCommand("oh").setExecutor(commandManager);

        //Get Config
         configManager = ConfigManager.getInstance();
         configFile = configManager.getConfig("config.yml");// Create Config
         gamesFile = configManager.getConfig("onlyheroes.yml");// Create Config

        //Add Properties
        configManager.setData(configFile, "single-server-mode",true);
        configManager.setData(configFile, "max-games",-1);

        this.isSingleServerMode = configManager.getBoolean(configFile,"single-server-mode");

        if (this.isSingleServerMode) { // If we're using single server
            this.gameManager.gamesLimit = 1;
        } else {
            this.gameManager.gamesLimit = configManager.getInt(configFile,"max-games");
        }

       if (gamesFile.getConfigurationSection("games") != null) {
            for (String gameName : gamesFile.getConfigurationSection("games").getKeys(false)) {
                Game game = new Game(gameName,this);
                boolean status = this.gameManager.registerGame(game);
                if (!status) {
                    getLogger().warning("Can't load game " + gameName + "! Reached game limit for this server.");
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


