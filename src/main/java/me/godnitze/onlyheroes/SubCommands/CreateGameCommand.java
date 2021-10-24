package me.godnitze.onlyheroes.SubCommands;

import me.godnitze.onlyheroes.Manager.ConfigManager;
import me.godnitze.onlyheroes.Manager.SubCommand;
import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CreateGameCommand extends SubCommand {
    private OnlyHeroes onlyHeroes = null;

    public CreateGameCommand(OnlyHeroes onlyHeroes){this.onlyHeroes = onlyHeroes;}

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
            if(onlyHeroes.gameManager.getGame(args[1]) != null)
            {
                player.sendMessage(ChatUtil.format("&9 OnlyHeroes &7>> &c" + args[1] + " &c game already exists"));
                return;
            }
            ConfigManager configManager = ConfigManager.getInstance();
            FileConfiguration gamesFile = onlyHeroes.gamesFile;

            configManager.setData(gamesFile, "games." + args[1] + ".displayName", args[1]);
            configManager.setData(gamesFile, "games." + args[1] + ".minPlayers", Integer.parseInt(args[2]));
            configManager.setData(gamesFile, "games." + args[1] + ".maxPlayers", Integer.parseInt(args[3]));
            configManager.setData(gamesFile, "games." + args[1] + ".worldName", args[4]);
            configManager.setData(gamesFile, "games." + args[1] + ".lobbyPoint", "X:0, Y:0, Z:0");

            for(int i = 0; i <= Integer.parseInt(args[3]) - 1; ++i){
                configManager.setData(gamesFile, "games." + args[1] + ".spawnPoints" + "." + i, "X:0, Y:0, Z:0");
            }

            if(onlyHeroes.gameManager.getGame(args[1]) != null){
                onlyHeroes.gameManager.removeGame(onlyHeroes.gameManager.getGame(args[1]));
            }
            Game game = new Game(args[1],onlyHeroes);
            boolean status = onlyHeroes.gameManager.registerGame(game);
            if (!status) {
                player.sendMessage(ChatUtil.format("&9 OnlyHeroes &7>> &c Try setting single-server-mode: false or increasing the max-games value "));
            }
            else{
                player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &a successfully created the game " + args[1]));

            }

        }
        else{
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &cYou did not provide the correct arguments!") );
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &c/oh create <name> <minPlayers> <maxPlayers> <worldName>"));
        }

    }
}
