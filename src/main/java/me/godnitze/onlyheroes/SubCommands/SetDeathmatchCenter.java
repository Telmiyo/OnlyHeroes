package me.godnitze.onlyheroes.SubCommands;

import me.godnitze.onlyheroes.Manager.ConfigManager;
import me.godnitze.onlyheroes.Manager.SubCommand;
import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetDeathmatchCenter extends SubCommand {
    private OnlyHeroes onlyHeroes = null;

    public SetDeathmatchCenter(OnlyHeroes onlyHeroes){this.onlyHeroes = onlyHeroes;}

    @Override
    public String getName() {
        return "setcenter";
    }

    @Override
    public String getDescription() {
        return "set the spawn of a deathmatch lobby center";
    }

    @Override
    public String getSyntax() {
        return "/oh setcenter <gameName>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 2){

            if(onlyHeroes.gameManager.getGame(args[1]) == null)
            {
                player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &c You need to create the game first! "));
                return;
            }

            ConfigManager configManager = ConfigManager.getInstance();
            FileConfiguration gamesFile = onlyHeroes.gamesFile;
            Location tmpLoc = player.getLocation();
            configManager.setData(gamesFile, "games." + args[1] + ".deathmatchCenter", "X:" + tmpLoc.getX() +  ", Y:" + tmpLoc.getY() + ", Z:" + tmpLoc.getZ());
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &a successfully created the deathmatch center spawn on " + args[1]));

        }
        else{
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &cYou did not provide the correct arguments!") );
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &c/oh setcenter <gameName>"));
        }

    }
}
