package me.godnitze.onlyheroes.Manager;

import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.Objects.GamePlayer;
import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.Tasks.GameStartCountDown;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameManager {

    //Classes
    private GameState currentState = GameState.LOBBY;

    private final OnlyHeroes plugin;
    private GameStartCountDown gameStartCountDown;

    //Properties
    public int gamesLimit = 0;
    private List<Game> games = new ArrayList<>();

    public GameManager(OnlyHeroes plugin){
        this.plugin = plugin;
    }

    public List<Game> getGames() {
        return games;
    }

    public boolean registerGame(Game game) {

        if (games.size() == gamesLimit && gamesLimit != -1) { // If we're at our limit, don't add a game
            return false;
        }

        games.add(game);

        plugin.getLogger().warning(game.getDisplayName() + " added correctly");
        printLog(game);
        return true;
    }

    public Game getGame(String gameName){

        for(Game game : getGames()){
            if(game.getDisplayName().equalsIgnoreCase(gameName)){
                return game;
            }
        }
        return null;
    }

    public void removeGame(Game game){
        games.remove(game);

        //TODO Remove Config

    }

    public GamePlayer getPlayerFromGame(Player player) {

        for(Game games : getGames()){
            return games.getPlayerFromGame(player);
        }
        return null;
    }
    public Game getGamePlayer(Player player){
        for(Game games : getGames()){
            if(games.getPlayerFromGame(player) != null){ return games;}
        }
        return null;
    }

    public void printLog(Game game){ game.printLog(); }

    public void cleanup(){

    }

}
