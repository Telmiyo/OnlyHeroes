package me.godnitze.onlyheroes.SubCommands;

import me.godnitze.onlyheroes.Manager.ConfigManager;
import me.godnitze.onlyheroes.Manager.SubCommand;
import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SaveGameCommand extends SubCommand {
    private OnlyHeroes onlyHeroes = null;

    public SaveGameCommand(OnlyHeroes onlyHeroes){this.onlyHeroes = onlyHeroes;}

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "Save & Register a Game! ";
    }

    @Override
    public String getSyntax() {
        return "/oh save <gameName>";
    }

    @Override
    public void perform(Player player, String[] args) {

        if(args.length == 2){
            //TODO

            if(onlyHeroes.gameManager.getGame(args[1]) != null){
                onlyHeroes.gameManager.removeGame(onlyHeroes.gameManager.getGame(args[1]));
            }
            Game game = new Game(args[1],onlyHeroes);
            boolean status = onlyHeroes.gameManager.registerGame(game);
            if (!status) {
                onlyHeroes.getLogger().warning("Can't load game " + args[1] + "! Reached game limit for this server.");
           }
            else{
                player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &a successfully saved the game " + args[1]));
            }

        }
        else{
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &cYou did not provide the correct arguments!") );
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &c/oh save <gameName>"));
        }
    }
}
