package me.maquzo.TTT.Listener.GUIS;

import me.maquzo.TTT.Listener.Lobby.JoinListener;
import me.maquzo.TTT.TTT;
import me.maquzo.TTT.utils.ItemBuilder;
import me.maquzo.TTT.utils.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SettingsGUI implements Listener {


    public static final String SETTINGS_INV_TITLE = "§c§lSettings";
    public static final String PLACEHOLDER = "§0Placeholder";
    public static final String SETTINGS = "§c§lEinstellung";
    public static final String PASSES = "§c§lPässe";
    public static final String TRAP = "§c§lTraitor-Trap";
    public static final String BARRIER = "§c§lX";
    public static final String FORCESTART = "§c§lForcestart";

    public static final String PASS_INV_TITLE = "§c§lPasse";
    public static final String DETECTIVE_ITEM_NAME = "§bDetective-Pass";
    public static final String TRAITOR_ITEM_NAME = "§4Traitor-Pass";

    private TTT plugin;
    private Inventory settingsInventory;
    private Inventory passInventory;


    public SettingsGUI(TTT plugin) {
        this.plugin = plugin;
        SettingsGUI();
    }

    public void SettingsGUI() {
        settingsInventory = Bukkit.createInventory(null, 9 * 3, SETTINGS_INV_TITLE);
        passInventory = Bukkit.createInventory(null, 9, PASS_INV_TITLE);
        settingsInventory.setItem(1, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(5, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(7, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(9, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(10, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(12, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(13, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(14, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(16, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(17, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(19, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(20, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(21, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(23, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(24, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());
        settingsInventory.setItem(25, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(PLACEHOLDER).build());

        settingsInventory.setItem(0, new ItemBuilder(Material.REDSTONE_COMPARATOR).setName(SETTINGS).build());
        settingsInventory.setItem(8, new ItemBuilder(Material.REDSTONE_COMPARATOR).setName(SETTINGS).build());
        settingsInventory.setItem(11, new ItemBuilder(Material.PAPER).setName(PASSES).build());
        settingsInventory.setItem(15, new ItemBuilder(Material.IRON_FENCE).setName(TRAP).build());
        settingsInventory.setItem(18, new ItemBuilder(Material.BARRIER).setName(BARRIER).build());
        settingsInventory.setItem(22, new ItemBuilder(Material.DIAMOND).setName(FORCESTART).build());
        settingsInventory.setItem(26, new ItemBuilder(Material.BARRIER).setName(BARRIER).build());

        passInventory.setItem(3, new ItemBuilder(Material.PAPER).setName(DETECTIVE_ITEM_NAME).build());
        passInventory.setItem(5, new ItemBuilder(Material.PAPER).setName(TRAITOR_ITEM_NAME).build());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack itemStack = player.getItemInHand();
            if (itemStack.getItemMeta() == null) return;
            if (itemStack.getItemMeta().getDisplayName() == null) return;
            if (itemStack.getItemMeta().getDisplayName().equals(JoinListener.SETTINGS_NAME))
                player.openInventory(settingsInventory);
        }
    }

    @EventHandler
    public void onSettingsClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!event.getInventory().getTitle().equals(SETTINGS_INV_TITLE)) return;
        if (event.getCurrentItem().getItemMeta() == null) return; player.closeInventory();
        switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
            case FORCESTART:
                player.performCommand("forcestart");
                player.closeInventory();
                break;
            case BARRIER:
            case PLACEHOLDER:
            case SETTINGS:
                player.closeInventory();
                break;
            case PASSES:
                event.setCancelled(true);
                player.openInventory(plugin.getSettings().getPassInventory());
                break;
            case TRAP:
                player.closeInventory();
                player.sendMessage(TTT.error + "In arbeit!");
                break;
            default:
                break;
        }


    }

    public Inventory getSettingsInventory() {
        return settingsInventory;
    }

    public Inventory getPassInventory() {
        return passInventory;
    }
}
