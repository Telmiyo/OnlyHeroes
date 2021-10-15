package me.godnitze.onlyheroes.SubCommands;

import me.godnitze.onlyheroes.Manager.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class JoinCommand extends SubCommand {

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "join a player into a game";
    }

    @Override
    public String getSyntax() {
        return "/oh join <game name>";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length > 1){

            Player target = Bukkit.getPlayer(args[1]);

            player.sendMessage("You successfully joined " + target.getDisplayName());

            target.playSound(target.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);

        }else if(args.length == 1){
            player.sendMessage("You did not provide a game!");
            player.sendMessage("Do it like this: /oh join <game name>");
        }

        //SAVE PLAYER INV

    }
}
