package me.godnitze.onlyheroes.Manager;

import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.Objects.GamePlayer;
import me.godnitze.onlyheroes.OnlyHeroes;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    //Classes
    private final OnlyHeroes plugin;

    //Properties
    public int gamesLimit = 0;
    private List<Game> games = new ArrayList<>();

    public GameManager(OnlyHeroes plugin){
        this.plugin = plugin;
    }

    public List<Game> getGames() {
        return games;
    }

    public boolean createGame(String gameName){
        Game game = new Game(gameName,plugin);
        return registerGame(game);
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

    public void saveGame(String gameName){
        getGame(gameName).saveConfig(gameName);
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

        ConfigManager.getInstance().setData(plugin.gamesFile,"games." + game.getDisplayName(),null);
        ConfigManager.getInstance().setData(plugin.gamesFile,"games." + game.getDisplayName() + ".displayName",null);
        ConfigManager.getInstance().setData(plugin.gamesFile,"games." + game.getDisplayName() + ".minPlayers",null);
        ConfigManager.getInstance().setData(plugin.gamesFile,"games." + game.getDisplayName() + ".maxPlayers",null);
        ConfigManager.getInstance().setData(plugin.gamesFile,"games." + game.getDisplayName() + ".worldName",null);
        ConfigManager.getInstance().setData(plugin.gamesFile,"games." + game.getDisplayName() + ".lobbyPoint",null);

        for(int i = 0; i <= game.getMaxPlayers() - 1; ++i){
            ConfigManager.getInstance().setData(plugin.gamesFile, "games." + game.getDisplayName() + ".spawnPoints" + "." + i, null);
        }
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
