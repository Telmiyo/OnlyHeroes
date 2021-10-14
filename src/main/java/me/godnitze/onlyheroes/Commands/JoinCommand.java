package me.godnitze.onlyheroes.Commands;

import me.godnitze.onlyheroes.utils.Globals;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            String oh = ChatColor.WHITE + "[" + ChatColor.GOLD + "OnlyHeroes" + ChatColor.WHITE + "]";

            Player p = (Player) sender;

            // plugin.yml will have command: onlyheroes then aliases: [oh,onlyh,oheroes]
            if (!command.getName().equalsIgnoreCase("OnlyHeroes"))
            {
                return false;
            }

            if (args == null || args.length == 0) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNot enough args"));
                return true;
            }

            switch (args[0]) {

                case "join":
                    p.sendMessage(oh + " " + ChatColor.AQUA + p.getDisplayName() + ChatColor.WHITE + " has joined the game");
                    // other sethome code here
                    return true;


                case "leave":
                    // test command
                    p.sendMessage(oh + " " + ChatColor.AQUA + p.getDisplayName() + ChatColor.WHITE + " has left the game");
                    return true;

                default:
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cInvalid Command"));
                    return true;
            }

        }
        return true;
    }
}
