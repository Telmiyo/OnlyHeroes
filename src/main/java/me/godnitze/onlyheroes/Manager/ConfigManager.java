package me.godnitze.onlyheroes.Manager;

import me.godnitze.onlyheroes.Data.CustomConfig;
import me.godnitze.onlyheroes.OnlyHeroes;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    //Classes
    public OnlyHeroes onlyHeroes;

    //Properties
    private  List<CustomConfig> files;

    public ConfigManager(OnlyHeroes onlyHeroes){
        this.onlyHeroes = onlyHeroes;
        files =  new ArrayList<>();
    }

    public FileConfiguration getCustomFile(String name) {
        for(int i = 0; i <= files.size();++i){
            if(name == files.get(i).getFileName()){return files.get(i).getCustomFile();}
        }
        return null;
    }

    public void createFile(String name){
       CustomConfig file = new CustomConfig(name);
       files.add(file);
    }

    public void saveFile(String name) {

        for(int i =  0; i < files.size();++i)
        {
            if(name == files.get(i).getFileName())
            {
                try{
                    files.get(i).getCustomFile().save(files.get(i).getFile());
                }catch(IOException e){
                    onlyHeroes.getLogger().warning("Couldn't save file " + files.get(i).getFileName());

                }
            }
        }

    }

    public void reload(String name){

        for(int i = 0; i < files.size();++i){
            if(name == files.get(i).getFileName()){
                FileConfiguration tmpFile = files.get(i).getCustomFile();
                tmpFile =  YamlConfiguration.loadConfiguration(files.get(i).getFile());
            }
        }

    }

    public void printAllFilesLog(){
        for(int i = 0;i< files.size();++i)
        {
            onlyHeroes.getLogger().info(files.get(i).getFileName());
        }
    }
}
