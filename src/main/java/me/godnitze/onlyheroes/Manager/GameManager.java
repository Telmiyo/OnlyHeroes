package me.godnitze.onlyheroes.Manager;

import me.godnitze.onlyheroes.OnlyHeroes;
import org.bukkit.Bukkit;

import java.util.Timer;
import java.util.TimerTask;

public class GameManager {
    private final OnlyHeroes plugin;
    public GameState gameState = GameState.LOBBY;
    public static GameManager instance;

    public GameManager(OnlyHeroes plugin){
        GameManager.instance = this;
        this.plugin = plugin;
    }

    public void setGameState(GameState gameState){

        this.gameState = gameState;


        switch (gameState){
            case LOBBY:
                Bukkit.broadcastMessage("Lobby State");
                //They cannot break selected lobby blocks.
                break;
            case STARTING:
                Bukkit.broadcastMessage("Starting State");

                //Timer on chat
                startTimer();


                break;
            case PHASE1:
                Bukkit.broadcastMessage("Phase1 State");
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

    int counter = 10;
    public void startTimer(){
        System.out.println("I am being called");
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("The game is starting in " + counter);
                counter = counter - 1;
            }
        };

    }

    public void cleanup(){

    }

}
