package me.godnitze.onlyheroes.SubCommands;

import me.godnitze.onlyheroes.CommandManager.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class LeaveCommand extends SubCommand {

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "leave the game";
    }

    @Override
    public String getSyntax() {
        return "/oh leave ";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length > 1){

            player.sendMessage("too much arguments!");
            player.sendMessage("Do it like this: /oh leave");

        }else if(args.length == 1){
            player.sendMessage(ChatColor.AQUA + player.getDisplayName() + ChatColor.GRAY + " left the game!");
        }

    }
}