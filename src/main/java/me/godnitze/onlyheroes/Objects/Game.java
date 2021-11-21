package me.godnitze.onlyheroes.Objects;

import me.godnitze.onlyheroes.Manager.ConfigManager;
import me.godnitze.onlyheroes.Manager.GameState;
import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.Tasks.GameStartCountDown;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class Game {

    //Classes
    private final OnlyHeroes onlyHeroes;

    //Basic Config Options
    private String displayName;
    private int maxPlayers;
    private int minPlayers;
    private World world;
    private List<Location> spawnPoints;
    private final List<Location> deathmatchSpawnPoints;
    private Location lobbyPoint;
    private boolean isStarted = false;

    //Active Game Activation
    private final List<GamePlayer> players;
    private final Set<GamePlayer> spectators;

    //STATES
    public GameState currentState = GameState.LOBBY;
    private GameStartCountDown gameStartCountDown;
    private boolean movementFrozen = false;

    //Constructor
    public Game(String gameName, OnlyHeroes onlyHeroes){
        this.onlyHeroes = onlyHeroes;

        // Initialize Config
        ConfigManager configManager = ConfigManager.getInstance();
        FileConfiguration gamesFile = onlyHeroes.gamesFile;
        if(gamesFile.getString("games." + gameName) == null)
        {
            configManager.setData(gamesFile, "games." + gameName + ".displayName", gameName);
            configManager.setData(gamesFile, "games." + gameName + ".minPlayers",1);
            configManager.setData(gamesFile, "games." + gameName + ".maxPlayers", 2);
            configManager.setData(gamesFile, "games." + gameName + ".worldName","world");
            configManager.setData(gamesFile, "games." + gameName + ".lobbyPoint", "X:0, Y:0, Z:0");

            for(int i = 0; i <= maxPlayers - 1; ++i){
                configManager.setData(gamesFile, "games." + gameName + ".spawnPoints" + "." + i, "X:0, Y:0, Z:0");
            }

        }
        else{ saveConfig(gameName); }

        //Initialize Game Values

        this.deathmatchSpawnPoints = new ArrayList<>();
        this.players = new ArrayList<>();
        this.spectators = new HashSet<>();
        this.gameStartCountDown = new GameStartCountDown(this);
        this.gameStartCountDown.setTimeLeft(20);

    }

    public void saveConfig(String gameName)
    {
        FileConfiguration gamesFile = onlyHeroes.gamesFile;

        this.displayName = gamesFile.getString("games." + gameName + ".displayName");
        this.maxPlayers = gamesFile.getInt("games." + gameName + ".maxPlayers");
        this.minPlayers = gamesFile.getInt("games." + gameName + ".minPlayers");

        //RollbackHandler.getRollbackHandler().rollback(fileConfiguration.getString("games." + gameName + ".worldName"));
        this.world = Bukkit.createWorld(new WorldCreator(gamesFile.getString("games." + gameName + ".worldName")));

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

        String point;
        for(int id = 0; id < getMaxPlayers(); ++id){
            point = gamesFile.getString("games." + gameName + ".spawnPoints" + "." + id);
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

    public boolean isStarted() { return isStarted; }

    public void setStarted(boolean started) { isStarted = started; }

    public List<Location> getSpawnPoints() { return spawnPoints; }

    public List<GamePlayer> getPlayers() {
        return players;
    }

    public Set<GamePlayer> getSpectators() {
        return spectators;
    }

    public GameState getCurrentState(){
        return this.currentState;
    }

    public boolean isMovementFrozen() { return movementFrozen; }

    public void setMovementFrozen(boolean movementFrozen) { this.movementFrozen = movementFrozen; }

    public boolean isState(GameState gameState){ return getCurrentState() == gameState; }

    public boolean startGame(Player player){

        if(!isState(GameState.LOBBY)) {
            player.sendMessage(ChatUtil.format("&b OnlyHeroes &7>> &c Game already started!"));
            return false;
        }

        setCurrentState(GameState.STARTING);
        return true;
    }

    public boolean joinGame(GamePlayer gamePlayer, Player player){

        if(!isState(GameState.LOBBY))
        {
            gamePlayer.sendMessage("Game already started");
            return false;
        }

        for (GamePlayer value : players) {
            if (gamePlayer.getName().equals(value.getName())) {
                gamePlayer.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &cYou already joined"));
                return false;
            }
        }

        if(getPlayers().size() >= getMaxPlayers()) {
            gamePlayer.sendMessage("&c[!] This game is full.");
            return false;
        }

        gamePlayer.setJoinPoint(new Location(player.getWorld(),player.getLocation().getX(),player.getLocation().getY(),player.getLocation().getZ()));


        getPlayers().add(gamePlayer);
        gamePlayer.teleport(isState(GameState.LOBBY) ? lobbyPoint : null); //If the game is Lobby go to lobbypoint otherwise nothing
        sendMessage("&a[+] &6" + gamePlayer.getName() + " &7(" + getPlayers().size() + "&a/&7" + getMaxPlayers() + ")");

        //TODO SAVE INVENTORY
        gamePlayer.getPlayer().setGameMode(GameMode.CREATIVE);
        //gamePlayer.getPlayer().setGameMode(gamePlayer.getPlayer().getMaxHealth());
        gamePlayer.getPlayer().getInventory().setArmorContents(null);

        if(getPlayers().size() == getMinPlayers()){

            sendMessage("&a[*] The game will begin in 10 seconds...");
            setCurrentState(GameState.STARTING);

        }

        return true;
    }

    public boolean leaveGame(GamePlayer gamePlayer){
        for(int i = 0;i < players.size();++i)
        {
            if(gamePlayer.getName().equals(players.get(i).getName()))
            {
                gamePlayer.teleport(players.get(i).getJoinPoint());
                sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &c" + gamePlayer.getName() + "&c Left the game!"));
                players.remove(0);

                if(players.size() > 0){ sendMessage("&a[-] &6" + gamePlayer.getName() + " &7(" + getPlayers().size() + "&a/&7" + getMaxPlayers() + ")");}
                else{ sendMessage("&a[-] &6" + gamePlayer.getName() + " &7(0 &a/&7" + getMaxPlayers() + ")");}

                return true;
            }
        }
        sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &cYou are not in any game"));

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
                this.gameStartCountDown.runTaskTimer(onlyHeroes,0,20L);

                break;
            case INGAME:
                Bukkit.broadcastMessage("INGAME State");
                if(this.gameStartCountDown != null) gameStartCountDown.cancel();
                //Spawn players randomly

                //Start CountDown
                 if(!isStarted){
                     setMovementFrozen(true);
                     spawnPlayers(spawnPoints);
                     this.gameStartCountDown = new GameStartCountDown(this);
                     this.gameStartCountDown.setTimeLeft(15);
                 }
                 else{
                     if(this.gameStartCountDown != null) gameStartCountDown.cancel();
                     this.gameStartCountDown = new GameStartCountDown(this);
                     this.gameStartCountDown.setTimeLeft(35);
                 }
                this.gameStartCountDown.runTaskTimer(onlyHeroes,0,20L);


                break;
            case DEATHMATCH:
                Bukkit.broadcastMessage("Deathmatch State");

                if(!isStarted)
                {
                    setDeathmatchSpawns();
                    spawnPlayers(deathmatchSpawnPoints);
                    setMovementFrozen(true);
                }
                if(this.gameStartCountDown != null) gameStartCountDown.cancel();
                this.gameStartCountDown = new GameStartCountDown(this);
                this.gameStartCountDown.setTimeLeft(10);
                this.gameStartCountDown.runTaskTimer(onlyHeroes,0,20L);


                break;
            case WON:
                Bukkit.broadcastMessage("Won State");
                break;
            case RESTARTING:
                Bukkit.broadcastMessage("Restarting State");
                break;

        }
    }

    public void spawnPlayers(List<Location> spawns){

        int id = 0;
        for (GamePlayer gamePlayer : getPlayers()) {
            try {
                gamePlayer.teleport(spawns.get(id));
                id += 1;
                //gamePlayer.getPlayer().setGameMode(GameMode.SURVIVAL);

            } catch (IndexOutOfBoundsException ex) {
                onlyHeroes.getLogger().severe("Not enough spawn points to satisfy game needs (Game is " + getDisplayName() + ")");
            }
        }
    }

    public void printLog(){
        onlyHeroes.getLogger().info("GameName: " + getDisplayName());
        onlyHeroes.getLogger().info("MaxPlayers: " + Integer.toString(getMaxPlayers()));
        onlyHeroes.getLogger().info("MinPlayers: " + Integer.toString(getMinPlayers()));

        onlyHeroes.getLogger().info("LobbyPoint X " +  ": "+ Double.toString(lobbyPoint.getX())
                + " LobbyPoint Y " +  ": "+ Double.toString(lobbyPoint.getY())
                +" LobbyPoint Z " +  ": "+ Double.toString(lobbyPoint.getZ()));

        for(int i = 0;i < spawnPoints.size() - 1;++i){
            onlyHeroes.getLogger().info("SpawnPoint X " +  ": "+ Double.toString(spawnPoints.get(i).getX())
            + " SpawnPoint Y" +  ": "+ Double.toString(spawnPoints.get(i).getY())
            +" SpawnPoint Z" +  ": "+ Double.toString(spawnPoints.get(i).getZ()));

        }

    }

    public GamePlayer getPlayerFromGame(Player player) {
        for (GamePlayer gamePlayer : getPlayers()) {
            if (gamePlayer.getPlayer() == player) {
                return gamePlayer;
            }
        }

        for (GamePlayer gamePlayer : getSpectators()) {
            if (gamePlayer.getPlayer() == player) {
                return gamePlayer;
            }
        }

        return null;
    }

    public void setDeathmatchSpawns() {
        int currentPlayers = getPlayers().size();
        Random random = new Random();
        Set<Integer> alreadyUsedNumbers = new HashSet<>();
        while (alreadyUsedNumbers.size() < currentPlayers) {

            // Número aleatorio entre 0 y 40, excluido el 40.
            int randomNumber = random.nextInt(currentPlayers);

            // Si no lo hemos usado ya, lo usamos y lo metemos en el conjunto de usados.
            if (!alreadyUsedNumbers.contains(randomNumber)) {
                alreadyUsedNumbers.add(randomNumber);
                deathmatchSpawnPoints.add(spawnPoints.get(randomNumber));
            }

        }
    }

}
