package me.maquzo.TTT.Listener.GUIS;

import me.maquzo.TTT.TTT;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class NavGui implements Listener {

    public static TTT plugin;

    public NavGui(TTT plugin) {
        this.plugin = plugin;
    }

    Inventory inv = Bukkit.createInventory(null, 54, ChatColor.AQUA + "Online Spieler");

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == event.getAction().RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            if (player.getItemInHand().getType() == Material.SKULL_ITEM) {
                event.setCancelled(true);
                inv.clear();
                for (Player players : Bukkit.getOnlinePlayers()) {

                    inv.addItem(getSkull(player.getName()));

                }
                player.openInventory(inv);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        player.getInventory().addItem(getSkull(player.getName()));
    }

    public ItemStack getSkull(String name) {
        ItemStack skull = new ItemStack(397, 1, (short) 3);
        SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
        skullmeta.setDisplayName(name);
        skullmeta.setOwner(name);
        skull.setItemMeta(skullmeta);
        return skull;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getName().contains("Online Spieler")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            if (event.getSlotType() == InventoryType.SlotType.CONTAINER) {
                SkullTeleport(player, event.getCurrentItem());
            }
        }
    }

    public void SkullTeleport(Player player, ItemStack item) {
        if (item.getType() != Material.AIR && item != null) {
            SkullMeta skullmeta = (SkullMeta) item.getItemMeta();
            if (skullmeta.getDisplayName() != null) {
                if (Bukkit.getPlayer(skullmeta.getDisplayName()) != null) {
                    Player target = Bukkit.getPlayer(skullmeta.getDisplayName());
                    player.teleport(target);
                }
            }
        }
    }

}
