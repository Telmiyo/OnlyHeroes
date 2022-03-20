package me.godnitze.onlyheroes.Manager;

import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageManager {
    private static MessageManager single_inst = null;
    private OnlyHeroes plugin = null;
    public FileConfiguration messageFile;
    private final String prefix;

    // Constructor
    public MessageManager() {
        messageFile = ConfigManager.getInstance().getConfig("OnlyMessages.yml");// Create Config
        if(messageFile == null)
            System.out.println("messageFile doesn't exist");

        prefix = messageFile.getString("prefix");
    }
    // Getters
    public String getMessage(boolean usePrefix, String key)
    {
        StringBuilder message = new StringBuilder();
        if(usePrefix)
            message.append(prefix);

        message.append(messageFile.getString(key));
        return message.toString();
    }
    public String getLongMessage(boolean usePrefix, String[] key) {

        StringBuilder message = new StringBuilder();
        if(usePrefix)
            message.append(prefix);

        for (String s : key) {
            message.append(messageFile.getString(s)).append(" ");
        }
        return message.toString();
    }


    public String getPrefix() {
        return prefix;
    }

    // Setters
    public void setPlugin(OnlyHeroes plugin) {
        this.plugin = plugin;
    }

    public static MessageManager getInstance() {
        if (single_inst == null) {
            single_inst = new MessageManager();
        }
        return single_inst;
    }
}