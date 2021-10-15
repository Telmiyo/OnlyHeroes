package me.godnitze.onlyheroes.CommandManager;


import me.godnitze.onlyheroes.SubCommands.JoinCommand;
import me.godnitze.onlyheroes.SubCommands.LeaveCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    private ArrayList<SubCommand> subcommands;

    public CommandManager(){
        subcommands = new ArrayList<>();
        subcommands.add(new JoinCommand());
        subcommands.add(new LeaveCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;

            if (args.length > 0){
                for (int i = 0; i < getSubcommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                        getSubcommands().get(i).perform(p, args);
                    }
                }
            }else if(args.length == 0){
                p.sendMessage(ChatColor.GREEN + "-----------Only Heroes------------");
                for (int i = 0; i < getSubcommands().size(); i++){
                    p.sendMessage(ChatColor.GOLD + getSubcommands().get(i).getSyntax() + " - " + ChatColor.GRAY + getSubcommands().get(i).getDescription());
                }
                p.sendMessage(ChatColor.LIGHT_PURPLE + "--------------------------------");
            }

        }


        return true;
    }

    public ArrayList<SubCommand> getSubcommands(){
        return subcommands;
    }

}
