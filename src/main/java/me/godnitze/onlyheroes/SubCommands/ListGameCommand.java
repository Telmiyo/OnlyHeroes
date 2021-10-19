package me.godnitze.onlyheroes.SubCommands;

import me.godnitze.onlyheroes.Manager.SubCommand;
import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.entity.Player;

public class ListGameCommand extends SubCommand {
    private OnlyHeroes onlyHeroes = null;

    public ListGameCommand(OnlyHeroes onlyHeroes){this.onlyHeroes = onlyHeroes;}

    @Override
    public String getName() {
        return "listgames";
    }

    @Override
    public String getDescription() {
        return "List all the games registered";
    }

    @Override
    public String getSyntax() {
        return "/oh listgames";
    }

    @Override
    public void perform(Player player, String[] args) {

        if(args.length == 1){
            if(onlyHeroes.gameManager.getGames().size() == 0){
                player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &6 ----------------------------" ));
                player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &6 There is no game created yet" ));
                player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &6 ----------------------------" ));
            }
            else{
                player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &6 ----------------------------" ));
                for(Game game : onlyHeroes.gameManager.getGames()){

                    player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &6" + game.getDisplayName() + "maxP: " + game.getMaxPlayers() + "minP: " + game.getMinPlayers() + "world: " + game.getWorld()));
                }
                player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &6 ----------------------------" ));
            }

        }
        else{
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &cYou did not provide the correct arguments!") );
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &c/oh listgames"));
        }
    }
}
