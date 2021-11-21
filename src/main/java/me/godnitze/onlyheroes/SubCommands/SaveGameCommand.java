package me.godnitze.onlyheroes.SubCommands;

import me.godnitze.onlyheroes.Manager.SubCommand;
import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.entity.Player;

public class SaveGameCommand extends SubCommand {
    private final OnlyHeroes onlyHeroes;

    public SaveGameCommand(OnlyHeroes onlyHeroes){this.onlyHeroes = onlyHeroes;}

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "Save & Register a Game! ";
    }

    @Override
    public String getSyntax() {
        return "/oh save <gameName>";
    }

    @Override
    public void perform(Player player, String[] args) {

        if(args.length == 2){
            if(onlyHeroes.gameManager.getGame(args[1]) == null){
                player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &a" + args[1] + " doesn't exist"));
                player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &ause /oh create"));
                return;
            }
            onlyHeroes.gameManager.saveGame(args[1]);
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &a " + args[1] + " successfully saved"));

        }
        else{
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &cYou did not provide the correct arguments!") );
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &c/oh save <gameName>"));
        }
    }
}
