package me.godnitze.onlyheroes.SubCommands;

import me.godnitze.onlyheroes.Manager.SubCommand;
import me.godnitze.onlyheroes.Objects.ArenaInventory;
import me.godnitze.onlyheroes.Objects.Game;
import me.godnitze.onlyheroes.Objects.GamePlayer;
import me.godnitze.onlyheroes.OnlyHeroes;
import me.godnitze.onlyheroes.utils.ChatUtil;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class JoinCommand extends SubCommand {

    private OnlyHeroes onlyHeroes;

    public JoinCommand(OnlyHeroes onlyHeroes){ this.onlyHeroes = onlyHeroes; }

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
        if(args.length == 1){

            onlyHeroes.arenaInventory.openInventory(player);
            return;

        }
        if(args.length == 2) {
            Game game = onlyHeroes.gameManager.getGame(args[1]);
            if (game == null) {
                player.sendMessage(ChatUtil.format("&9OnlyHeroes &7>> &cThat game doesn't exist."));
                return;
            }

            GamePlayer gamePlayer = new GamePlayer(player);

            game.joinGame(gamePlayer,game, player);

            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            return;
        }
        player.sendMessage("You did not provide a game!");
        player.sendMessage("Do it like this: /oh join <game name>");

        }

        //SAVE PLAYER INV

    }
