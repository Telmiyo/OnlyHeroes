package me.godnitze.onlyheroes.Tasks;

import me.godnitze.onlyheroes.Manager.GameState;
import me.godnitze.onlyheroes.Objects.Game;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartCountDown extends BukkitRunnable {
    private Game game;

    public GameStartCountDown(Game game){ this.game = game; }
    private int timeLeft = 10;

    @Override
    public void run() {
        --timeLeft;
        if(timeLeft <= 0){
            cancel();
            game.setCurrentState(GameState.PHASE1);
            return;
        }
        game.sendMessage(ChatColor.YELLOW + "The Game Starts in " + timeLeft);
    }
}
