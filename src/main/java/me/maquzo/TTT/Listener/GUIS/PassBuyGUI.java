package me.maquzo.TTT.Listener.GUIS;

import lombok.Getter;
import me.maquzo.TTT.Manager.Roles.Role;
import me.maquzo.TTT.TTT;
import me.maquzo.TTT.utils.ItemBuilder;
import me.maquzo.TTT.utils.Profile;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;

import java.util.List;

public class PassBuyGUI implements Listener {

    @Getter
    public static final String TRAITOR_PASS = "§4Traitor-Pass §7(2.000 Karma)";
    @Getter
    public static final String DETECTIVE_PASS = "§bDetective-Pass §7(2.000 Karma)";

    @Getter
    public static final String PASS_SHOP_TITLE = "§c§lPASS-Shop";

    @Getter
    private Inventory passShop;

    @Getter
    private TTT plugin;

    public PassBuyGUI(TTT plugin) {
        this.plugin = plugin;
        passShop = Bukkit.createInventory(null, 9, PASS_SHOP_TITLE);
        passShop.setItem(3, new ItemBuilder(Material.PAPER).setName(DETECTIVE_PASS).build());
        passShop.setItem(5, new ItemBuilder(Material.PAPER).setName(TRAITOR_PASS).build());

    }


    @EventHandler
    public void onBuyPass(InventoryClickEvent event) {
        if (!(event.getInventory().getTitle().equalsIgnoreCase(PASS_SHOP_TITLE))) {
            event.setCancelled(false);
            return;
        }
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        if (!(event.getWhoClicked() instanceof Player)) return;

        ItemStack itemStack = event.getCurrentItem();
        if (itemStack == null) return;
        if (itemStack.getItemMeta() == null) return;

        Profile profile = plugin.getProfileManager().getProfile(player);
        Role role = plugin.getRoleManager().getRole(player);

        if (event.getInventory().getTitle().equals(PASS_SHOP_TITLE)) {
            switch (itemStack.getItemMeta().getDisplayName()) {
                case DETECTIVE_PASS:
                    if (profile.getData().getStats().getKarma() >= 2000) {
                        profile.getData().getStats().increaseDetectivePass(1);
                        profile.getData().getStats().decreseKarma(2000);
                        profile.getData().save();
                        player.closeInventory();
                        player.sendMessage(TTT.prefix + "Du hast dir einen §b§lDetecitve-Pass §7gekauft!");
                    } else if (profile.getData().getStats().getKarma() < 2000){
                        player.closeInventory();
                        player.sendMessage(TTT.error + "Dafuer hast du nicht genug §c§lKarma!");
                    }
                    break;

                case TRAITOR_PASS:
                    if (profile.getData().getStats().getKarma() >= 2000) {
                        profile.getData().getStats().increaseTraitorPass(1);
                        profile.getData().getStats().decreseKarma(2000);
                        profile.getData().save();
                        player.closeInventory();
                        player.sendMessage(TTT.prefix + "Du hast dir einen §4§lTraitor-Pass §7gekauft!");
                    } else if (profile.getData().getStats().getKarma() < 2000){
                        player.closeInventory();
                        player.sendMessage(TTT.error + "Dafuer hast du nicht genug §c§lKarma!");
                    }
                    break;

                default:
                    break;
            }
        }
    }


}
