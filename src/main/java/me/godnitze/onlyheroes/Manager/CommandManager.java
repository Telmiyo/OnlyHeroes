package me.godnitze.onlyheroes.Manager;


import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.SubCommands.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    private ArrayList<SubCommand> subcommands;
    private OnlyHeroes onlyHeroes;

    public CommandManager(OnlyHeroes onlyHeroes){
        this.onlyHeroes = onlyHeroes;

        subcommands = new ArrayList<>();
        subcommands.add(new JoinCommand(onlyHeroes));
        subcommands.add(new LeaveCommand(onlyHeroes));
        subcommands.add(new StartCommand(onlyHeroes));
        subcommands.add(new ReloadConfig());
        subcommands.add(new CreateGame(onlyHeroes));
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
