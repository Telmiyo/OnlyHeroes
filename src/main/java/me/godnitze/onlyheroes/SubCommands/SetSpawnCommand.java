package me.godnitze.onlyheroes.SubCommands;

import me.godnitze.onlyheroes.Manager.ConfigManager;
import me.godnitze.onlyheroes.Manager.SubCommand;
import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetSpawnCommand extends SubCommand {
    private OnlyHeroes onlyHeroes = null;

    public SetSpawnCommand(OnlyHeroes onlyHeroes){this.onlyHeroes = onlyHeroes;}
    @Override
    public String getName() {
        return "setspawn";
    }

    @Override
    public String getDescription() {
        return "sets an spawnPoint on a game";
    }

    @Override
    public String getSyntax() {
        return "/oh setspawn <gameName> <spawnId>";
    }

    @Override
    public void perform(Player player, String[] args) {

        if(args.length == 3){
            //TODO
            ConfigManager configManager = ConfigManager.getInstance();
            FileConfiguration gamesFile = onlyHeroes.gamesFile;
            Location tmpLoc = player.getLocation();
            configManager.setData(gamesFile, "games." + args[1] + ".spawnPoints" + "." + args[2], "X:" + tmpLoc.getX() +  ", Y:" + tmpLoc.getY() + ", Z:" + tmpLoc.getZ());
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &a successfully created the spawnPoint number " + args[1] + "on " + args[0]));

        }
        else{
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &cYou did not provide the correct arguments!") );
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &c/oh setspawn <gameName> <spawnId>"));
        }

    }
}
