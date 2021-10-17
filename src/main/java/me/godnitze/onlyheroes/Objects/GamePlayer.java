package me.godnitze.onlyheroes.Objects;

import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GamePlayer {

    private Player player = null;
    private Location spawnPoint = null;
    //private GameTeam gameTeam = null;

    public GamePlayer(Player player){ this.player = player; }

    public Player getPlayer() { return player; }

    public String getName(){return getPlayer().getDisplayName();}

    public void sendMessage(String string){ getPlayer().sendMessage(ChatUtil.format(string)); }

    public void teleport(Location location){
        if(location == null){ return;}

        getPlayer().teleport(location);

    }

    public enum PlayerState{

    }
}
