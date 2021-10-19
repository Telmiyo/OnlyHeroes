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
        if(game.isState(GameState.PHASE1) || game.isState(GameState.PHASE2 ) || game.isState(GameState.PHASE3)) { game.setMovementFrozen(true);}
        if(timeLeft <= 0){
            cancel();
            switch (game.getCurrentState()){
                case STARTING:
                    game.setCurrentState(GameState.PHASE1);
                    break;
                case PHASE1:
                    game.setMovementFrozen(false);
                    break;
                case PHASE2:
                    game.setMovementFrozen(false);
                    break;
                case PHASE3:
                    game.setMovementFrozen(false);
                    break;
            }
            return;
        }
        game.sendMessage(ChatColor.YELLOW + "The Game Starts in " + timeLeft);
    }

    public void setTimeLeft(int timeLeft) { this.timeLeft = timeLeft; }
}
