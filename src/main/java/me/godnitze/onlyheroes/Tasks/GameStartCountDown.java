package me.godnitze.onlyheroes.Tasks;

import me.godnitze.onlyheroes.Manager.GameState;
import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartCountDown extends BukkitRunnable {
    private final Game game;
    private final OnlyHeroes plugin;
    private LightingTask lightingTask;
    public GameStartCountDown(OnlyHeroes plugin, Game game)
    {
        this.game = game;
        this.plugin = plugin;
    }
    private int timeLeft = 10;

    @Override
    public void run() {
        --timeLeft;
        if(timeLeft < 0){
            switch (game.getCurrentState()){
                case STARTING:
                    game.setCurrentState(GameState.PREINGAME);
                    game.sendMessage(ChatUtil.format("&9OnlyHeroes &7>>&cPlease wait &7[&e30&7]&c seconds before the games begin."));
                    break;
                case PREINGAME:
                    game.setCurrentState(GameState.INGAME);
                    game.sendMessage(ChatUtil.format("&9OnlyHeroes &7>>&bThe game has begun!"));
                    game.sendMessage(ChatUtil.format("&9OnlyHeroes &7>>&7[&e30&7]&c seconds until deathmatch!"));
                    break;
                case INGAME:
                    game.setCurrentState(GameState.PREDEATHMATCH);
                    game.sendMessage(ChatUtil.format("&9OnlyHeroes &7>>&4Please allow &7[&e10&7]&4 seconds for all the players to load the map."));
                    break;
                case PREDEATHMATCH:
                    game.setCurrentState(GameState.DEATHMATCH);
                    lightingTask = new LightingTask(plugin,game);
                    game.sendMessage(ChatUtil.format("&9OnlyHeroes &7>>&cFight to the death!"));
                    game.sendMessage(ChatUtil.format("&9OnlyHeroes &7>>&7[&e3&7] minutes until the deathmatch ends!"));
                    break;
                case DEATHMATCH:
                    //TODO END GAME
                    game.setCurrentState(GameState.WON);
                    break;
            }
        }

        // Messages System
        switch (game.getCurrentState()){
            case STARTING:
                if(timeLeft == 60 || timeLeft <= 10){ game.sendMessage(ChatUtil.format("&9OnlyHeroes &7>>&7[" + "&e" + timeLeft + "&7] &c seconds until the lobby ends"));}
                break;
            case PREINGAME:
                if(timeLeft == 30 || timeLeft <= 10 ) { game.sendMessage(ChatUtil.format("&9OnlyHeroes &7>>&7[" + "&e" + timeLeft + "&7] &c seconds until game begins"));}
                break;
            case INGAME:
               if(timeLeft == 60 || timeLeft == 30 || timeLeft <= 10){ game.sendMessage(ChatUtil.format("&9OnlyHeroes &7>>&7[" + "&e" + timeLeft + "&7] &c seconds until deathmatch!"));}
                break;
            case PREDEATHMATCH:
                if(timeLeft <= 5){game.sendMessage(ChatUtil.format("&9OnlyHeroes &7>>&7[" + "&e" + timeLeft + "&7] &c seconds until deathmatch!"));}
                break;
            case DEATHMATCH:
                if(timeLeft == 60)
                {
                    game.sendMessage(ChatUtil.format("&9OnlyHeroes &7>>&7[&e60&7]&c seconds until deathmatch ends!"));
                    game.sendMessage(ChatUtil.format("&c&9OnlyHeroes &7>>&cStorm Has Started Moving!"));
                    lightingTask.setRadius(15);

                }
                if(timeLeft == 30)
                {
                    game.sendMessage(ChatUtil.format("&9OnlyHeroes &7>>&7[&e30&7]&c seconds until deathmatch ends!"));
                    game.sendMessage(ChatUtil.format("&9OnlyHeroes &7>>&cStorm Has Started Moving!"));
                    lightingTask.setRadius(7);

                }
                if(timeLeft == 10){ game.sendMessage(ChatUtil.format("&9OnlyHeroes &7>>&cStorm Has Started Moving!"));}
                break;
        }
    }

    public void setTimeLeft(int timeLeft) { this.timeLeft = timeLeft; }
}
