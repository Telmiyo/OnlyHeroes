package me.godnitze.onlyheroes.Manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ConfigManager {
    private static ConfigManager single_inst = null;

    private ArrayList<File> customFiles = new ArrayList<File>();
    private ArrayList<FileConfiguration> customConfigs = new ArrayList<FileConfiguration>();
    private ArrayList<String> configNames = new ArrayList<String>();
    private Plugin plugin = null;

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration getConfig(String name){
        plugin.getLogger().warning( Integer.toString(customConfigs.size()));

        if (customConfigs.size() > 0) {
            for (FileConfiguration conf : customConfigs) {
                if (conf.getName().equalsIgnoreCase(name)) {
                    plugin.getLogger().warning( Integer.toString(customConfigs.size()));
                    return conf;
                }
            }
        }
        plugin.getLogger().warning( name + " file created");
        return createNewCustomConfig(name);
    }

    public File getFile(String name){
        plugin.getLogger().warning( Integer.toString(customFiles.size()));

        if (customFiles.size() > 0) {
            for (File conf : customFiles) {
                if (conf.getName().equalsIgnoreCase(name)) {
                    plugin.getLogger().warning( Integer.toString(customConfigs.size()));
                    return conf;
                }
            }
        }
        plugin.getLogger().warning( name + " file created");
        return null;
    }

    public void reloadConfigs(){
        customConfigs.clear();

    }

    public FileConfiguration createNewCustomConfig(String name) {
        FileConfiguration fileConfiguration;
        File configFile = new File(plugin.getDataFolder(), name);
        customFiles.add(configFile);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource(name, false);
        }

        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(configFile);
            customConfigs.add(fileConfiguration);
            configNames.add(name);
            plugin.getLogger().warning( name + " file added correctly");
            return fileConfiguration;

        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Returns true if saved successfully, returns false in case of error and prints error to console
    public boolean setData(FileConfiguration conf, String path, Object data){
        conf.set(path, data);
        return saveData(conf);
    }

    public boolean saveData(FileConfiguration conf){
        try {
            conf.save(new File(plugin.getDataFolder(), configNames.get(customConfigs.indexOf(conf))));
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

/*    //Remove if not using placeholderAPI
    public String getString(FileConfiguration conf, String path, Player player){
        //Create dummy if not available
        if (!conf.contains(path)) {
            setData(conf, path, "dummy PAPI string");
        }
        return PlaceholderAPI.setPlaceholders(player, conf.getString(path));
    }*/

    public String getStringRaw(FileConfiguration conf, String path){
        //Create dummy if not available
        if (!conf.contains(path)) {
            setData(conf, path, "dummy string");
        }
        return conf.getString(path);
    }

    public int getInt(FileConfiguration conf, String path){
        //Create dummy if not available
        if (!conf.contains(path)) {
            setData(conf, path, 1);
        }
        return conf.getInt(path);
    }

    public double getDouble(FileConfiguration conf, String path){
        //Create dummy if not available
        if (!conf.contains(path)) {
            setData(conf, path, 1.0);
        }
        return conf.getDouble(path);
    }

    public boolean getBoolean(FileConfiguration conf, String path){
        //Create dummy if not available
        if (!conf.contains(path)) {
            setData(conf, path, false);
        }
        return conf.getBoolean(path);
    }

    public List<?> getList(FileConfiguration conf, String path){
        //Create dummy list if not available
        if (!conf.contains(path)) {
            setData(conf, path, new ArrayList<Location>().add(new Location(Bukkit.getWorld("world"), 10, 10, 10)));
        }
        return conf.getList(path);
    }

    public boolean addLocation(FileConfiguration conf, Location location, String path){
        conf.set(String.format("%s.world", path), location.getWorld().getName());
        conf.set(String.format("%s.x", path), location.getX());
        conf.set(String.format("%s.y", path), location.getY());
        conf.set(String.format("%s.z", path), location.getZ());

        return saveData(conf);
    }

    public Location getLocation(FileConfiguration conf, String path){
        String worldName = getStringRaw(conf, String.format("%s.world", path));
        Bukkit.getServer().createWorld(new WorldCreator(worldName));

        World world = Bukkit.getWorld(worldName);
        int x = getInt(conf, String.format("%s.x", path));
        int y = getInt(conf, String.format("%s.y", path));
        int z = getInt(conf, String.format("%s.z", path));

        return new Location(world, x, y, z);
    }

    private ConfigManager() {
    }

    public static ConfigManager getInstance() {
        if (single_inst == null) {
            single_inst = new ConfigManager();
        }
        return single_inst;
    }
}