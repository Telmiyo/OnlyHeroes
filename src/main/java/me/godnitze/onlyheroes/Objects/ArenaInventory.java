package me.godnitze.onlyheroes.Objects;

import me.godnitze.onlyheroes.Manager.GameState;
import me.godnitze.onlyheroes.OnlyHeroes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ArenaInventory implements Listener {

    private Inventory inv = null;
    private OnlyHeroes onlyHeroes = null;

    public ArenaInventory(){}

    public void createInv(OnlyHeroes onlyHeroes){
        this.onlyHeroes = onlyHeroes;
        inv = Bukkit.createInventory(null, 9, "Arenas");

        initializeItems();
    }

    private void initializeItems() {

        //TODO INITIALIZE ARENAS
        for(Game game : onlyHeroes.gameManager.getGames()) {

            if(game.isState(GameState.LOBBY) || game.isState(GameState.STARTING)) {
                if(game.getPlayers().size() == game.getMaxPlayers()) {
                    inv.addItem(createGuiItem(Material.RED_WOOL, game.getDisplayName(), game.getMinPlayers() + "/" + game.getMaxPlayers(), "§b" + game.getCurrentState()));

                }
                else{
                    inv.addItem(createGuiItem(Material.GREEN_WOOL, game.getDisplayName(), game.getMinPlayers() + "/" + game.getMaxPlayers(), "§b" + game.getCurrentState()));
                }
            }
            else{
                inv.addItem(createGuiItem(Material.YELLOW_WOOL, game.getDisplayName(), game.getMinPlayers() + "/" + game.getMaxPlayers(), "§b" + game.getCurrentState()));
            }
        }


        //inv.addItem(createGuiItem(Material.IRON_HELMET, "§bExample Helmet", "§aFirst line of the lore", "§bSecond line of the lore"));
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore){
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        //Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() != inv) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player p = (Player) e.getWhoClicked();

        // Using slots click is a best option for your inventory click's
        p.sendMessage("You clicked at slot " + e.getRawSlot());
        if(clickedItem.getType().equals(Material.GREEN_WOOL)) {
            Game game = onlyHeroes.gameManager.getGame(clickedItem.getItemMeta().getDisplayName();
            game.joinGame(new GamePlayer(p),game,p);
        }
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
        }
    }
}

}
