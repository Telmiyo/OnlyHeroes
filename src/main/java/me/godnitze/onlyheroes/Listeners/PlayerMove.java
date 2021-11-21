package me.godnitze.onlyheroes.Listeners;

import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.OnlyHeroes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

    private OnlyHeroes onlyHeroes = null;

    public PlayerMove(OnlyHeroes onlyHeroes){this.onlyHeroes = onlyHeroes;}

    @EventHandler
    public void onMove(PlayerMoveEvent event){

        Player player = event.getPlayer();

        Game game = onlyHeroes.gameManager.GetGameFromPlayer(player);
        if(game != null && game.isMovementFrozen()){
            if (event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
                player.teleport(event.getFrom());
            }
        }
    }

}
