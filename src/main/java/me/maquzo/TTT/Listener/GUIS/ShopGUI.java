package me.maquzo.TTT.Listener.GUIS;

import lombok.Getter;
import lombok.Setter;
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
import org.bukkit.inventory.ItemStack;

@Setter
@Getter
public class ShopGUI implements Listener {


    //Traitor Items:
    public static final String CREEPER_ARROWS = "§2Creeper-Arrows";
    public static final String TESTER_FAKER = "§cFake-Tester";

    //Detevitces Items:
    public static final String MEDKIT = "§cMedkit";

    public static final String TRAITOR_SHOP_TITLE = "§c§lTraitor-Shop";
    public static final String DETECTIVE_SHOP_TITLE = "§b§lDetective-Shop";

    private Inventory traitorShop, detectiveShop;

    private TTT plugin;

    public ShopGUI(TTT plugin) {
        this.plugin = plugin;

        traitorShop = Bukkit.createInventory(null, 9, TRAITOR_SHOP_TITLE);
        detectiveShop = Bukkit.createInventory(null, 9, DETECTIVE_SHOP_TITLE);
<<<<<<< HEAD
        traitorShop.setItem(3, new ItemBuilder(Material.SULPHUR).setName(CREEPER_ARROWS).setLore("§7Schieße mit Creeper Pfeilen! (300 Karma)").build());
        traitorShop.setItem(5, new ItemBuilder(Material.WOOD_BUTTON).setName(TESTER_FAKER).setLore("§7Lasse dich beim testen so aussehen als wenn du ein Innocent wärst. (750 Karma)").build());
        detectiveShop.setItem(4, new ItemBuilder(Material.CAKE).setName(MEDKIT).setLore("§7Heile dich um 3 Herzen (100 Karma)").build());
=======
        traitorShop.setItem(3, new ItemBuilder(Material.SULPHUR).setName(CREEPER_ARROWS).setLore("§7Schieße mit Creeper Pfeilen! (10 Karma)").build());
        traitorShop.setItem(5, new ItemBuilder(Material.WOOD_BUTTON).setName(TESTER_FAKER).setLore("§7Lasse dich beim testen so aussehen als wenn du ein Innocent wärst. (20 Karma)").build());
        detectiveShop.setItem(4, new ItemBuilder(Material.CAKE).setName(MEDKIT).setLore("§7Heile dich um 3 Herzen (10 Karma)").build());
>>>>>>> 46888185c224ca5389c203a78e9a7bd8b1274085


    }

    @EventHandler
    public void shopBuy(InventoryClickEvent event) {
        if (!(event.getInventory().getTitle().equalsIgnoreCase(TRAITOR_SHOP_TITLE) || (event.getInventory().getTitle().equalsIgnoreCase(DETECTIVE_SHOP_TITLE)))) {
            event.setCancelled(false);
            return;
        }
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        if (!(event.getWhoClicked() instanceof Player)) return;

        ItemStack itemStack = event.getCurrentItem();
        if (itemStack.getItemMeta() == null) return;

        Profile profile = plugin.getProfileManager().getProfile(player);
        if (event.getInventory().getTitle().equals(TRAITOR_SHOP_TITLE)) {
            switch (itemStack.getItemMeta().getDisplayName()) {
                case CREEPER_ARROWS:
                    player.closeInventory();
                    if (profile.getData().getStats().getKarma() >= 300) {
                        player.getInventory().addItem(new ItemBuilder(Material.SULPHUR).setName(CREEPER_ARROWS).setAmount(5).build());
                        profile.getData().getStats().decreseKarma(300);
                        profile.getData().save();
                    } else {
                        player.sendMessage(TTT.error + "Dafuer hast du nicht genug Karma");
                        player.sendMessage(TTT.error + "Karma: §c§l" + profile.getData().getStats().getKarma());
                    }
                    break;
                case TESTER_FAKER:
                    player.closeInventory();
                    if (profile.getData().getStats().getKarma() >= 750) {
                        player.getInventory().addItem(new ItemBuilder(Material.WOOD_BUTTON).setName(TESTER_FAKER).build());
                        profile.getData().getStats().decreseKarma(750);
                        profile.getData().save();
                    } else {
                        player.sendMessage(TTT.error + "Dafuer hast du nicht genug Karma");
                        player.sendMessage(TTT.error + "Karma: §c§l" + profile.getData().getStats().getKarma());
                    }

                    break;
                default:
                    break;
            }
        }

        if (event.getInventory().getTitle().equals(DETECTIVE_SHOP_TITLE)) {
            switch (itemStack.getItemMeta().getDisplayName()) {
                case MEDKIT:
                    player.closeInventory();
                    if (profile.getData().getStats().getKarma() >= 100) {
                        player.getInventory().addItem(new ItemBuilder(Material.CAKE).setName(MEDKIT).build());
                        profile.getData().getStats().decreseKarma(100);
                        profile.getData().save();
                    } else {
                        player.sendMessage(TTT.error + "Dafuer hast du nicht genug Karma");
                        player.sendMessage(TTT.error + "Karma: §c§l" + profile.getData().getStats().getKarma());
                    }

                    break;

                default:
                    break;
            }
        }


    }

    public static boolean removeShopItem(Player player, Material material) {
        if (!player.getInventory().contains(material)) return false;
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            if( player.getInventory().getContents()[i] == null) return false;
            if (player.getInventory().getContents()[i].getType() == material) {
                if (player.getInventory().getContents()[i].getAmount() <= 1)
                    player.getInventory().clear(i);
                else
                    player.getInventory().getContents()[i].setAmount(player.getInventory().getContents()[i].getAmount() - 1);
                player.updateInventory();
                return true;
            } else
                Bukkit.broadcastMessage("error");
        }
        return false;
    }


}
