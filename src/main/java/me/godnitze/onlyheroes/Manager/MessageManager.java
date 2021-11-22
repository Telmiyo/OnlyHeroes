package me.godnitze.onlyheroes.Manager;

import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.configuration.file.FileConfiguration;


public class MessageManager {
    private static MessageManager single_inst = null;
    private OnlyHeroes plugin = null;
    public FileConfiguration messageFile;

    public String getMessage(String name) {
        return ChatUtil.format(messageFile.getString(name));
    }

    public String getPrefix() {
        return messageFile.getString("prefix");
    }

    public String getMessageWithPrefix(String name){
        return ChatUtil.format(getPrefix() + messageFile.getString(name));

    }

    public MessageManager() {
        messageFile = ConfigManager.getInstance().getConfig("OnlyMessages.yml");// Create Config
    }

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