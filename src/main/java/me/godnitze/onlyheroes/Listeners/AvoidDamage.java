package me.godnitze.onlyheroes.Listeners;

import me.godnitze.onlyheroes.Manager.GameState;
import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.OnlyHeroes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class AvoidDamage implements Listener {

    private OnlyHeroes onlyHeroes = null;

    public AvoidDamage(OnlyHeroes onlyHeroes){this.onlyHeroes = onlyHeroes;}

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof  Player){
            Player player = (Player) event.getEntity();
            Game game = onlyHeroes.gameManager.GetGameFromPlayer(player);
            if(game != null){
                // True = No Damage; False = Damage;
                if(game.isState(GameState.INGAME) ||  game.isState(GameState.DEATHMATCH)){
                    event.setCancelled(false);
                }
                else{
                    event.setCancelled(true);
                }
            }
        }

    }
}
