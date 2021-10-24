package me.godnitze.onlyheroes.SubCommands;

import me.godnitze.onlyheroes.Manager.ConfigManager;
import me.godnitze.onlyheroes.Manager.SubCommand;
import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class RemoveGameCommand extends SubCommand {
    private OnlyHeroes onlyHeroes = null;

    public RemoveGameCommand(OnlyHeroes onlyHeroes){this.onlyHeroes = onlyHeroes;}

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getDescription() {
        return "Remove a Game";
    }

    @Override
    public String getSyntax() {
        return "/oh remove <GameName>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 2){

            if(onlyHeroes.gameManager.getGame(args[1]) == null){
                player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &cThe game you provided doesn't exist in config.yml!") );
                return;
            }

            //TODO Remove Config
            ConfigManager configManager = ConfigManager.getInstance();
            FileConfiguration gamesFile = onlyHeroes.gamesFile;

            configManager.setData(gamesFile, "games." + args[1], null);
            configManager.setData(gamesFile, "games." + args[1] + ".displayName", null);
            configManager.setData(gamesFile, "games." + args[1] + ".minPlayers", null);
            configManager.setData(gamesFile, "games." + args[1] + ".maxPlayers", null);
            configManager.setData(gamesFile, "games." + args[1] + ".worldName", null);
            configManager.setData(gamesFile, "games." + args[1] + ".lobbyPoint", null);

            for(int i = 0; i <= onlyHeroes.gameManager.getGame(args[1]).getMaxPlayers(); ++i){
                configManager.setData(gamesFile, "games." + args[1] + ".spawnPoints" + "." + i, null);
            }

            onlyHeroes.gameManager.removeGame(onlyHeroes.gameManager.getGame(args[1]));
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &a successfully deleted " + args[1]));


        }
        else{
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &cYou did not provide the correct arguments!") );
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &c/oh remove <GameName>"));
        }

    }
}
