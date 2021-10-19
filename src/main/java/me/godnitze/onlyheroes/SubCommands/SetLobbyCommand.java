package me.godnitze.onlyheroes.SubCommands;

import me.godnitze.onlyheroes.Manager.ConfigManager;
import me.godnitze.onlyheroes.Manager.SubCommand;
import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetLobbyCommand extends SubCommand {
    private OnlyHeroes onlyHeroes = null;

    public SetLobbyCommand(OnlyHeroes onlyHeroes){this.onlyHeroes = onlyHeroes;}

    @Override
    public String getName() {
        return "setlobby";
    }

    @Override
    public String getDescription() {
        return "set the spawn of a game lobby";
    }

    @Override
    public String getSyntax() {
        return "/oh setlobby <gameName>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 2){
            ConfigManager configManager = ConfigManager.getInstance();
            FileConfiguration gamesFile = onlyHeroes.gamesFile;
            Location tmpLoc = player.getLocation();
            configManager.setData(gamesFile, "games." + args[1] + ".lobbyPoint", "X:" + tmpLoc.getX() +  ", Y:" + tmpLoc.getY() + ", Z:" + tmpLoc.getZ());
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &a successfully created the lobby spawn on " + args[1]));

        }
        else{
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &cYou did not provide the correct arguments!") );
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &c/oh setlobby <gameName>"));
        }

    }
}
