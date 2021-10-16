package me.godnitze.onlyheroes.Manager;

import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.Tasks.GameStartCountDown;
import org.bukkit.Bukkit;

public class GameManager {

    private final OnlyHeroes plugin;
    public GameState gameState = GameState.LOBBY;
    public static GameManager instance;
    private GameStartCountDown gameStartCountDown;

    public GameManager(OnlyHeroes plugin){
        GameManager.instance = this;
        this.plugin = plugin;
    }

    public void setGameState(GameState gameState){

        if(!(this.gameState == GameState.LOBBY) && (gameState == GameState.STARTING)) return;
        this.gameState = gameState;

        switch (gameState){
            case LOBBY:
                Bukkit.broadcastMessage("Lobby State");
                //They cannot break selected lobby blocks.
                break;
            case STARTING:
                Bukkit.broadcastMessage("Starting State");

                //Timer on chat
                this.gameStartCountDown = new GameStartCountDown(this);
                this.gameStartCountDown.runTaskTimer(plugin,0,20);

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

    public GameState getGameState(){
        return this.gameState;
    }

    public void cleanup(){

    }

}
