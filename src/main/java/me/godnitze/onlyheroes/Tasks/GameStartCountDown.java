package me.godnitze.onlyheroes.Tasks;

import me.godnitze.onlyheroes.Manager.GameState;
import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartCountDown extends BukkitRunnable {
    private Game game;

    public GameStartCountDown(Game game){ this.game = game; }
    private int timeLeft = 10;

    @Override
    public void run() {
        --timeLeft;
        if(timeLeft < 0){
            cancel();
            switch (game.getCurrentState()){
                case STARTING:
                    game.setCurrentState(GameState.INGAME);

                    break;
                case INGAME:
                    if(!game.isStarted())
                    {
                        game.setMovementFrozen(false);
                        game.setStarted(true);
                        game.setCurrentState(GameState.INGAME);
                        game.sendMessage(ChatUtil.format("&bThe game has begun!"));

                    }
                    else{
                        game.setCurrentState(GameState.DEATHMATCH);
                        game.setStarted(false);

                    }
                    break;
                case DEATHMATCH:
                   // game.setCurrentState(GameState.WON);
                    if(!game.isStarted()){
                        game.setMovementFrozen(false);
                        game.setStarted(true);
                        game.setCurrentState(GameState.DEATHMATCH);
                        game.sendMessage(ChatUtil.format("&cFight to the death!"));
                    }
                    else{
                        //CALL DE LIGHTING SYSTEM

                    }

                    break;
            }
            return;
        }
        switch (game.getCurrentState()){
            case STARTING:
                if(timeLeft <= 10){ game.sendMessage(ChatUtil.format("&7[" + "&e" + timeLeft + "&7] &c seconds until the lobby ends"));}
                break;
            case INGAME:
                if(game.isStarted() && (timeLeft == 30 || timeLeft <= 10)){ game.sendMessage(ChatUtil.format("&7[" + "&e" + timeLeft + "&7] &c seconds until deathmatch begins"));}
                else if(!game.isStarted()){game.sendMessage(ChatUtil.format("&7[" + "&e" + timeLeft + "&7] &c seconds until game begins"));}

                break;
            case DEATHMATCH:
                 if(!game.isStarted()){ game.sendMessage(ChatUtil.format("&7[" + "&e" + timeLeft + "&7] &c seconds until the until deathmatch"));}
                 else if(game.getPlayers().size() == 1){game.setCurrentState(GameState.WON);}

                break;
        }
    }

    public void setTimeLeft(int timeLeft) { this.timeLeft = timeLeft; }
}
