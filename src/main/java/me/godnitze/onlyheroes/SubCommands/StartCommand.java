package me.godnitze.onlyheroes.SubCommands;

import me.godnitze.onlyheroes.Manager.GameManager;
import me.godnitze.onlyheroes.Manager.GameState;
import me.godnitze.onlyheroes.Manager.SubCommand;
import org.bukkit.entity.Player;

public class StartCommand extends SubCommand {

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

        if(GameManager.instance.gameState == GameState.LOBBY)
        {
            GameManager.instance.setGameState(GameState.STARTING);

        }
        else
        {
            player.sendMessage("The game already started");

        }

    }
}
