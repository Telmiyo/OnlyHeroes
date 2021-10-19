package me.godnitze.onlyheroes.Listeners;

import me.godnitze.onlyheroes.Manager.GameState;
import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.OnlyHeroes;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockInteract implements Listener {
    private OnlyHeroes onlyHeroes = null;

    public BlockInteract(OnlyHeroes onlyHeroes){this.onlyHeroes = onlyHeroes; }

    @EventHandler
    public void OnBlockBreak(BlockBreakEvent e){ handle(e, e.getPlayer()); }

    private void handle(Cancellable event, Player player){
        Game game = onlyHeroes.gameManager.getGamePlayer(player);

        if(game != null){
            event.setCancelled(true);
            return;
        }
    }


}
