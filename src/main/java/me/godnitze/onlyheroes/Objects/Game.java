package me.godnitze.onlyheroes.Objects;

import me.godnitze.onlyheroes.Manager.ConfigManager;
import me.godnitze.onlyheroes.Manager.GameState;
import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.Tasks.GameStartCountDown;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class Game {

    //Classes
    private OnlyHeroes onlyHeroes;

    //Basic Config Options
    private String displayName;
    private int maxPlayers;
    private int minPlayers;
    private World world;
    private List<Location> spawnPoints;
    private Location lobbyPoint;

    //Active Game Activation
    private List<GamePlayer> players;
    private Set<GamePlayer> spectator;

    //STATES
    public GameState currentState = GameState.LOBBY;
    private GameStartCountDown gameStartCountDown;


    //Constructor
    public Game(String gameName, OnlyHeroes onlyHeroes){
        this.onlyHeroes = onlyHeroes;
        ConfigManager configManager = ConfigManager.getInstance();
        FileConfiguration gamesFile = onlyHeroes.gamesFile;

        this.displayName = gamesFile.getString("games." + gameName + ".displayName");
        this.maxPlayers = gamesFile.getInt("games." + gameName + ".maxPlayers");
        this.minPlayers = gamesFile.getInt("games." + gameName + ".minPlayers");

        //RollbackHandler.getRollbackHandler().rollback(fileConfiguration.getString("games." + gameName + ".worldName"));
        //this.world = Bukkit.createWorld(new WorldCreator(fileConfiguration.getString("games." + gameName + ".worldName") + "_active"));

        //TODO Spawn points
        try {
            String[] values = gamesFile.getString( "games." + gameName + ".lobbyPoint").split(","); // [X:0, Y:0, Z:0]
            double x = Double.parseDouble(values[0].split(":")[1]); // X:0 -> X, 0 -> 0
            double y = Double.parseDouble(values[1].split(":")[1]);
            double z = Double.parseDouble(values[2].split(":")[1]);
            lobbyPoint = new Location(world, x, y, z);
        } catch (Exception ex) {
            onlyHeroes.getLogger().severe("Failed to load lobbyPoint with metadata " + gamesFile.getString("games." + gameName + ".lobbyPoint") + " for gameName: '" + gameName + "'. ExceptionType: " + ex);
        }

        this.spawnPoints = new ArrayList<>();

        for (String point : gamesFile.getStringList("games." + gameName + ".spawnPoints")) {
            // X:0,Y:0,Z:0
            try {
                String[] values = point.split(","); // [X:0, Y:0, Z:0]
                double x = Double.parseDouble(values[0].split(":")[1]); // X:0 -> X, 0 -> 0
                double y = Double.parseDouble(values[1].split(":")[1]);
                double z = Double.parseDouble(values[2].split(":")[1]);
                Location location = new Location(world, x, y, z);
                spawnPoints.add(location);
            } catch (Exception ex) {
               onlyHeroes.getLogger().severe("Failed to load spawnPoint with metadata " + point + " for gameName: '" + gameName + "'. ExceptionType: " + ex);
            }
        }

        this.players = new ArrayList<>();
        this.spectator = new HashSet<>();

    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public World getWorld() { return world; }

    public String getDisplayName() {
        return displayName;
    }

    public List<GamePlayer> getPlayers() {
        return players;
    }

    public Set<GamePlayer> getSpectator() {
        return spectator;
    }

    public GameState getCurrentState(){
        return this.currentState;
    }

    public boolean isState(GameState gameState){ return getCurrentState() == gameState; }

    public boolean joinGame(GamePlayer gamePlayer, Game game){

        if(getPlayers().size() >= getMaxPlayers()) {
            gamePlayer.sendMessage("&c[!] This game is full.");
            return false;
        }

        getPlayers().add(gamePlayer);
        gamePlayer.teleport(isState(GameState.LOBBY) ? lobbyPoint : null); //If the game is Lobby go to lobbypoint otherwise nothing
        sendMessage("&a[+] &6" + gamePlayer.getName() + " &7(" + getPlayers().size() + "&a/&7" + getMaxPlayers() + ")");

        //TODO SAVE INVENTORY
        gamePlayer.getPlayer().setGameMode(GameMode.ADVENTURE);
        //gamePlayer.getPlayer().setGameMode(gamePlayer.getPlayer().getMaxHealth());
        gamePlayer.getPlayer().getInventory().setArmorContents(null);

        if(getPlayers().size() == maxPlayers){

            sendMessage("&a[*] The game will begin in 10 seconds...");
            setCurrentState(GameState.STARTING);

        }

        return false;
    }

    public void sendMessage(String string) {
        for(GamePlayer gamePlayer : getPlayers()){
            gamePlayer.sendMessage(string);
        }
    }

    public void setCurrentState(GameState currentState){

        if(!(this.currentState == GameState.LOBBY) && (currentState == GameState.STARTING)) return;
        this.currentState = currentState;

        switch (currentState){
            case LOBBY:
                Bukkit.broadcastMessage("Lobby State");
                //They cannot break selected lobby blocks.
                break;
            case STARTING:
                Bukkit.broadcastMessage("Starting State");

                //Timer on chat
                this.gameStartCountDown = new GameStartCountDown(this);
                this.gameStartCountDown.runTaskTimer(onlyHeroes,0,20);

                break;
            case PHASE1:
                Bukkit.broadcastMessage("Phase1 State");
                if(this.gameStartCountDown != null) gameStartCountDown.cancel();
                //Spawn players randomly
                break;
            case PHASE2:
                Bukkit.broadcastMessage("Phase2 State");
                break;
            case PHASE3:
                Bukkit.broadcastMessage("Phase3 State");
                break;
            case DEATHMATCH:
                Bukkit.broadcastMessage("Deathmatch State");
                break;
            case WON:
                Bukkit.broadcastMessage("Won State");
                break;
            case RESTARTING:
                Bukkit.broadcastMessage("Restarting State");
                break;

        }
    }

    public void printLog(){
        onlyHeroes.getLogger().info("GameName: " + getDisplayName());
        onlyHeroes.getLogger().info("MaxPlayers: " + Integer.toString(getMaxPlayers()));
        onlyHeroes.getLogger().info("MinPlayers: " + Integer.toString(getMinPlayers()));

        onlyHeroes.getLogger().info("LobbyPoint X " +  ": "+ Double.toString(lobbyPoint.getX())
                + " LobbyPoint Y " +  ": "+ Double.toString(lobbyPoint.getY())
                +" LobbyPoint Z " +  ": "+ Double.toString(lobbyPoint.getZ()));

        for(int i = 0;i < spawnPoints.size();++i){
            onlyHeroes.getLogger().info("SpawnPoint X " +  ": "+ Double.toString(spawnPoints.get(i).getX())
            + " SpawnPoint Y" +  ": "+ Double.toString(spawnPoints.get(i).getY())
            +" SpawnPoint Z" +  ": "+ Double.toString(spawnPoints.get(i).getZ()));

        }
    }
}
