package me.godnitze.onlyheroes;

import org.bukkit.plugin.java.JavaPlugin;
import me.godnitze.onlyheroes.CommandManager.CommandManager;

public final class OnlyHeroes extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
    System.out.print("OnlyHeroes initialized correctly");

        //SetCommands
        getCommand("oh").setExecutor(new CommandManager());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.print("OnlyHeroes shut down correctly");

    }
}
