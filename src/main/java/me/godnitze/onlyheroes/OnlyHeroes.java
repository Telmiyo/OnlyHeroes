package me.godnitze.onlyheroes;

import me.godnitze.onlyheroes.Manager.GameManager;
import org.bukkit.plugin.java.JavaPlugin;
import me.godnitze.onlyheroes.Manager.CommandManager;

public final class OnlyHeroes extends JavaPlugin {

    private GameManager gameManager;
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.print("OnlyHeroes initialized correctly");

        //initialize Managers
        this.gameManager = new GameManager(this);
        this.commandManager = new CommandManager();

        //SetCommands
        getCommand("oh").setExecutor(commandManager);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.print("OnlyHeroes shut down correctly");

        gameManager.cleanup();
    }
}
