package me.godnitze.onlyheroes.SubCommands;

import me.godnitze.onlyheroes.Manager.GameManager;
import me.godnitze.onlyheroes.Manager.GameState;
import me.godnitze.onlyheroes.Manager.SubCommand;
import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.Objects.GamePlayer;
import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.entity.Player;

public class StartCommand extends SubCommand {

    private OnlyHeroes onlyHeroes = null;

    public StartCommand(OnlyHeroes onlyHeroes){this.onlyHeroes = onlyHeroes;}

    @Override
    public String getName() {

        return "start";
    }

    @Override
    public String getDescription() {
        return "start the game";
    }

    @Override
    public String getSyntax() {
        return "/oh start <GameName>";
    }

    @Override
    public void perform(Player player, String[] args) {

        if(args.length == 3){
            onlyHeroes.gameManager.getGame(args[1]).startGame(player);
        }
        else{
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &cYou did not provide the correct arguments!") );
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &c/oh start <GameName>"));
        }
        /*if(GameManager.instance.currentState == GameState.LOBBY)
        {
            GameManager.instance.setCurrentState(GameState.STARTING);

        }
        else
        {
            player.sendMessage("The game already started");

        }
*/
    }
}
