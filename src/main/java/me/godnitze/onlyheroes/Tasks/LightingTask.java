package me.godnitze.onlyheroes.Tasks;
import me.godnitze.onlyheroes.Objects.GamePlayer;
import me.godnitze.onlyheroes.OnlyHeroes;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class LightingTask{
    private final int minRadius = 0;
    private final int maxRadius = 0;
    private final OnlyHeroes plugin;
    private final List<GamePlayer> players;
    public LightingTask(OnlyHeroes plugin, List<GamePlayer> players)
    {
        this.plugin = plugin;
        this.players = players;
        new BukkitRunnable() {
            int timeLeft = 6;
            @Override
            public void run() {
                System.out.println("LIGHTING STAETED" + Integer.toString(timeLeft));
                timeLeft--;
                if (timeLeft == 0) {
                   timeLeft = 6;
                   //TODO IF PLAYER IS IN THE AREA, STRIKE

                    System.out.println("STRIKE");
                    CheckDamage();
                    return;
                }
            }
        }.runTaskTimer(plugin, 20, 20);

    }

    public void CheckDamage()
    {
        for(GamePlayer p : players){
           p.getPlayer().getWorld().strikeLightning(p.getPlayer().getLocation());
        }
    }
    public void Damage(Player player)
    {


    }

    public void ReduceSpace(){}


}
