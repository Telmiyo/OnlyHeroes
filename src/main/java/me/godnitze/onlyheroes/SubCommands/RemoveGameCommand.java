package me.godnitze.onlyheroes.SubCommands;

import me.godnitze.onlyheroes.Manager.SubCommand;
import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.entity.Player;

public class RemoveGameCommand extends SubCommand {
    private final OnlyHeroes onlyHeroes;

    public RemoveGameCommand(OnlyHeroes onlyHeroes){this.onlyHeroes = onlyHeroes;}

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getDescription() {
        return "Remove a Game";
    }

    @Override
    public String getSyntax() {
        return "/oh remove <GameName>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length != 2)
        {
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &cYou did not provide the correct arguments!") );
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &c/oh remove <GameName>"));
            return;
        }
        if(onlyHeroes.gameManager.getGame(args[1]) == null)
        {
            player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &cThe game you provided doesn't exist in OnlyGames.yml!") );
            return;
        }
        onlyHeroes.gameManager.removeGame(onlyHeroes.gameManager.getGame(args[1]));
        player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &aGame " + args[1] +" successfully removed from OnlyGames.yml!") );

    }
}
