package me.godnitze.onlyheroes.SubCommands;

import me.godnitze.onlyheroes.Manager.ConfigManager;
import me.godnitze.onlyheroes.Manager.SubCommand;
import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CreateGame extends SubCommand {
    private OnlyHeroes onlyHeroes = null;

    public CreateGame(OnlyHeroes onlyHeroes){this.onlyHeroes = onlyHeroes;}

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "create a new game";
    }

    @Override
    public String getSyntax() {
        return "/oh create <name> <minPlayers> <maxPlayers> <worldName>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 5){
            //TODO
            ConfigManager configManager = ConfigManager.getInstance();
            FileConfiguration gamesFile = onlyHeroes.gamesFile;

            configManager.setData(gamesFile, "games." + args[1] + ".displayName", args[1]);
            configManager.setData(gamesFile, "games." + args[1] + ".maxPlayers", Integer.parseInt(args[2]));
            configManager.setData(gamesFile, "games." + args[1] + ".minPlayers", Integer.parseInt(args[3]));
            configManager.setData(gamesFile, "games." + args[1] + ".worldName", args[4]);
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &a successfully created the game " + args[1]));
        }
        else{
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &cYou did not provide the correct arguments!") );
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &c/oh create <name> <minPlayers> <maxPlayers> <worldName>"));
        }

    }
}
