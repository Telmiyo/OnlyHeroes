package me.godnitze.onlyheroes.Tasks;
import me.godnitze.onlyheroes.Manager.GameState;
import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.Objects.GamePlayer;
import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class LightingTask{
    private int radius = 25;
    private final OnlyHeroes plugin;
    private final List<GamePlayer> players;
    private final Game game;
    public LightingTask(OnlyHeroes plugin, Game game)
    {
        this.plugin = plugin;
        this.game = game;
        this.players = game.getPlayers();
        new BukkitRunnable() {
        int timeLeft = 6;
            @Override
            public void run() {
                if(game.isState(GameState.WON))
                {
                    cancel();
                    return;
                }
                timeLeft--;
                if (timeLeft == 0) {
                   timeLeft = 6;
                   CheckDamage();
                    return;
                }
            }
        }.runTaskTimer(plugin, 20, 20);

    }

    public void CheckDamage()
    {
        for(GamePlayer p : players){
            Double playerPos = Math.abs(p.getPlayer().getLocation().getX()) + Math.abs(p.getPlayer().getLocation().getZ());
            Double limitPos = (game.getDeathmatchCenter().getX() + radius) + Math.abs(game.getDeathmatchCenter().getZ() + radius);

            if(playerPos >= limitPos){
                 p.getPlayer().getWorld().strikeLightningEffect(p.getPlayer().getLocation());
                 p.getPlayer().damage(2.5);
                 p.getPlayer().sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &cPlease return to spawn! "));
            }
        }
    }

    public void ReduceSpace(){}
    public void setRadius(int value){radius = value;}

}
