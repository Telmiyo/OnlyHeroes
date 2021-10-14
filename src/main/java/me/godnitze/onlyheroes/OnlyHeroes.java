package me.godnitze.onlyheroes;

import org.bukkit.plugin.java.JavaPlugin;

public final class OnlyHeroes extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
    System.out.print("OnlyHeroes initialized correctly");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.print("OnlyHeroes shut down correctly");

    }
}
