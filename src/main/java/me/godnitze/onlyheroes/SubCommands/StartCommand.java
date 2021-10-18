package me.godnitze.onlyheroes.SubCommands;

import me.godnitze.onlyheroes.Manager.GameManager;
import me.godnitze.onlyheroes.Manager.GameState;
import me.godnitze.onlyheroes.Manager.SubCommand;
import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.Objects.GamePlayer;
import me.godnitze.onlyheroes.OnlyHeroes;
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
        return "/oh start";
    }

    @Override
    public void perform(Player player, String[] args) {

        for(Game games: onlyHeroes.gameManager.getGames()){
            games.startGame(new GamePlayer(player));
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
