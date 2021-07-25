package me.maquzo.TTT.Listener.GUIS;

import me.maquzo.TTT.TTT;
import me.maquzo.TTT.utils.ItemBuilder;
import me.maquzo.TTT.utils.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class PassGUI implements Listener {

    private Inventory passGUI;
    private TTT plugin;

    public static final String PASS_INV_TITLE = "§c§lPasse";
    public static final String DETECTIVE_ITEM_NAME = "§bDetective-Pass";
    public static final String TRAITOR_ITEM_NAME = "§4Traitor-Pass";

    public PassGUI(TTT plugin) {
        this.plugin = plugin;
        passGUI = Bukkit.createInventory(null, 9, PASS_INV_TITLE);

        passGUI.setItem(3, new ItemBuilder(Material.PAPER).setName(DETECTIVE_ITEM_NAME).build());
        passGUI.setItem(5, new ItemBuilder(Material.PAPER).setName(TRAITOR_ITEM_NAME).build());
    }

    @EventHandler
    public void onPassInvClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(!(event.getWhoClicked() instanceof Player)) return;
        Profile profile = plugin.getProfileManager().getProfile(player);
        if (!event.getInventory().getTitle().equals(PassGUI.PASS_INV_TITLE)) return;
        switch (event.getSlot()) {
            case 3:
                if (profile.getData().getStats().isTraitorPassActive() || profile.getData().getStats().isDetectivePassActive()) {
                    player.sendMessage(TTT.error + "Du hast bereits einen §c§lPass §7ausgewählt!");
                    player.closeInventory();
                    return;
                }
                if (profile.getData().getStats().getPassDetective() == 0) {
                    player.sendMessage(TTT.error + "Du hast keinen §bDetective §7Pass!");
                    player.closeInventory();
                    return;
                }
                profile.getData().getStats().setDetectivePassActive(true);
                profile.getData().getStats().decreaseDetectivePass(1);
                player.sendMessage(TTT.prefix + "Du hast einen §bDetective-Pass §7ausgewählt!");
                profile.getData().save();
                player.closeInventory();
                break;
            case 5:
                if (profile.getData().getStats().isTraitorPassActive() || profile.getData().getStats().isDetectivePassActive()) {
                    player.sendMessage(TTT.error + "Du hast bereits einen §c§lPass ausgewählt!");
                    player.closeInventory();
                    return;
                }
                if (profile.getData().getStats().getPassTraitor() == 0) {
                    player.sendMessage(TTT.error + "Du hast keinen §4Traitor §7Pass!");
                    player.closeInventory();
                    return;
                }
                profile.getData().getStats().setTraitorPassActive(true);
                profile.getData().getStats().decreaseTraitorPass(1);
                profile.getData().save();
                player.sendMessage(TTT.prefix + "Du hast einen §4Traitor-Pass ausgewählt!");
                player.closeInventory();
                break;

            default:
                break;
        }
    }

}
