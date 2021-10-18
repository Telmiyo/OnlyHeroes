package me.godnitze.onlyheroes.SubCommands;

import me.godnitze.onlyheroes.Manager.ConfigManager;
import me.godnitze.onlyheroes.Manager.SubCommand;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.entity.Player;

public class ReloadConfig extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "reload configuration files";
    }

    @Override
    public String getSyntax() {
        return "/oh reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        ConfigManager.getInstance().reloadConfigs();
        player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &cReloaded"));
    }
}
