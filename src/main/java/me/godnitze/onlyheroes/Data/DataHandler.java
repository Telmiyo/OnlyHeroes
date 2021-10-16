package me.godnitze.onlyheroes.Data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import me.godnitze.onlyheroes.OnlyHeroes;

import java.io.File;
import java.io.IOException;

/**
 * Copyright © 2016 Jordan Osterberg and Shadow Technical Systems LLC. All rights reserved. Please email jordan.osterberg@shadowsystems.tech for usage rights and other information.
 */
public class DataHandler {
    private OnlyHeroes plugin;
    public DataHandler(OnlyHeroes plugin) {
        this.plugin = plugin;
        this.gameInfoFile = new File(plugin.getDataFolder(), "OnlyHeroes.yml");
        if (!this.gameInfoFile.exists()) {
            try {
                this.gameInfoFile.getParentFile().mkdirs();
                this.gameInfoFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.gameInfo = YamlConfiguration.loadConfiguration(this.gameInfoFile);
    }

    private File gameInfoFile;
    private FileConfiguration gameInfo;

    public FileConfiguration getGameInfo() {
        return gameInfo;
    }

    public void saveGameInfo() {
        try {
            this.gameInfo.save(this.gameInfoFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}