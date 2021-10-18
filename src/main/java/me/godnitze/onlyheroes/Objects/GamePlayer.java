package me.godnitze.onlyheroes.Objects;

import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GamePlayer {

    private Player player = null;
    private Location spawnPoint = null;
    private Location joinPoint;
    private String gameId = null;
    //private GameTeam gameTeam = null;

    public GamePlayer(Player player){ this.player = player; }

    public Player getPlayer() { return player; }

    public Location getJoinPoint() { return joinPoint; }

    public void setJoinPoint(Location joinPoint) { this.joinPoint = joinPoint; }

    public String getGameId() { return gameId; }

    public String getName(){return getPlayer().getDisplayName();}

    public void setGameId(String gameId) { this.gameId = gameId; }

    public void sendMessage(String string){ getPlayer().sendMessage(ChatUtil.format(string)); }

    public void teleport(Location location){

        if(location == null)
        {
            System.out.println("LOCATION NULL");
            return;
        }

        getPlayer().teleport(location);

    }

    public enum PlayerState{

    }
}
