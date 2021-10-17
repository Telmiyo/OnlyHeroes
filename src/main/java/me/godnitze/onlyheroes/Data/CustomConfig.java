package me.godnitze.onlyheroes.Data;


import me.godnitze.onlyheroes.OnlyHeroes;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomConfig {

    //Properties
    private File file;
    private FileConfiguration customFile;
    private String fileName;

    public CustomConfig(String name){
        this.fileName = name;

        file = new File(Bukkit.getServer().getPluginManager().getPlugin("OnlyHeroes").getDataFolder(), getFileName() + ".yml");
        if(!file.exists()){
            try{
                file.createNewFile();

            }catch(IOException e) {
                //BlANK
            }

            customFile = YamlConfiguration.loadConfiguration(file);
        }
    }
    public void addValue(String one, String two){
        customFile.addDefault(one,two);
    }

    public String getFileName() { return fileName; }

    public FileConfiguration getCustomFile() {
        return customFile;
    }

    public File getFile() { return file; }
}
