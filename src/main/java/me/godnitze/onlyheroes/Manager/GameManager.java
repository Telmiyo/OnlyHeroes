package me.godnitze.onlyheroes.Manager;

import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.Tasks.GameStartCountDown;

import java.util.HashSet;
import java.util.Set;

public class GameManager {

    //Classes
    public GameState currentState = GameState.LOBBY;

    private final OnlyHeroes plugin;
    private GameStartCountDown gameStartCountDown;

    //Properties
    public int gamesLimit = 0;
    public Set<Game> games = new HashSet<>();

    public GameManager(OnlyHeroes plugin){
        this.plugin = plugin;
    }

    public Set<Game> getGames() {
        return games;
    }

    public boolean registerGame(Game game) {

        if (games.size() == gamesLimit && gamesLimit != -1) { // If we're at our limit, don't add a game
            plugin.getLogger().warning("Can't load game " + game.getDisplayName() + "! Reached game limit for this server.");
            plugin.getLogger().warning("Game Size " + Integer.toString(games.size()));

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

    public void printLog(Game game){ game.printLog(); }

    public void cleanup(){

    }

}
