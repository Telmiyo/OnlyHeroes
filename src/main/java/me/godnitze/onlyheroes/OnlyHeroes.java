package me.godnitze.onlyheroes;

import me.godnitze.onlyheroes.Data.DataHandler;
import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.Manager.GameManager;
import org.bukkit.plugin.java.JavaPlugin;
import me.godnitze.onlyheroes.Manager.CommandManager;

public final class OnlyHeroes extends JavaPlugin {

    private GameManager gameManager;
    private CommandManager commandManager;
    private DataHandler dataHandler;
    private int gameLimit = 0;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.print("OnlyHeroes initialized correctly");

        //initialize Managers
        this.gameManager = new GameManager(this);
        this.commandManager = new CommandManager();
        this.dataHandler = new DataHandler(this);

        //SetCommands
        getCommand("oh").setExecutor(commandManager);

        //Get Config
        getConfig().options().copyDefaults(true);
        getConfig().options().copyHeader(true);
        saveDefaultConfig();

        if(getConfig().getBoolean("single-server-mode")){
            gameLimit = 1;
        }
        else{
            gameLimit = -1;
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.print("OnlyHeroes shut down correctly");

        gameManager.cleanup();
    }

    public void registerGame(Game Game){

    }
}


