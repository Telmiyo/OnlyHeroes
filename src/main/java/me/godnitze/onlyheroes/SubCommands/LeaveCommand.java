package me.godnitze.onlyheroes.SubCommands;

import me.godnitze.onlyheroes.Manager.SubCommand;
import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.Objects.GamePlayer;
import me.godnitze.onlyheroes.OnlyHeroes;
import org.bukkit.entity.Player;


public class LeaveCommand extends SubCommand {

    private OnlyHeroes onlyHeroes;

    public LeaveCommand(OnlyHeroes onlyHeroes){this.onlyHeroes = onlyHeroes;}
    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "leave the game";
    }

    @Override
    public String getSyntax() {
        return "/oh leave ";
    }

    @Override
    public void perform(Player player, String[] args) {

        for(Game games: onlyHeroes.gameManager.getGames()){
            games.leaveGame(new GamePlayer(player));
        }

        //Return Player Inv

    }
}