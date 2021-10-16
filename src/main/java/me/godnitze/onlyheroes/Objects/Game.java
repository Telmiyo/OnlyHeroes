package me.godnitze.onlyheroes.Objects;

import me.godnitze.onlyheroes.Data.DataHandler;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Set;

public class Game {

    private DataHandler dataHandler;
    public Game(DataHandler dataHandler){
        this.dataHandler = dataHandler;
    }

    //Basic Config Options
    private String displayName;
    private int maxPlayers;
    private int minPlayer;
    private World world;
    private Set<Location> spawnPoint;

    //Active Game Activation
    private Set<GamePlayer> players;
    private Set<GamePlayer> spectator;

    public Game(String gameName){
        FileConfiguration filesConfiguration = dataHandler.getGameInfo();

        this.displayName = filesConfiguration.getString("games" +  gameName + ".displayName" );
        this.maxPlayers = filesConfiguration.getInt("games." + gameName + ".maxPlayers");
        this.minPlayer = filesConfiguration.getInt(("games." + gameName + ".minPlayers"));

    }





}
