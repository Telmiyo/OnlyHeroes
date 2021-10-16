package me.godnitze.onlyheroes.Tasks;

import me.godnitze.onlyheroes.Manager.GameManager;
import me.godnitze.onlyheroes.Manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartCountDown extends BukkitRunnable {
    private GameManager gameManager;

    public GameStartCountDown(GameManager gameManager){
        this.gameManager = gameManager;
    }
    private int timeLeft = 10;

    @Override
    public void run() {
        --timeLeft;
        if(timeLeft <= 0){
            cancel();
            gameManager.setGameState(GameState.PHASE1);
            return;
        }
        Bukkit.broadcastMessage(ChatColor.YELLOW + "The Game Starts in " + timeLeft);
    }
}
