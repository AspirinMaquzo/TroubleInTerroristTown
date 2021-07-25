package me.maquzo.TTT.Listener.GUIS;

import lombok.Getter;
import me.maquzo.TTT.Listener.Lobby.JoinListener;
import me.maquzo.TTT.TTT;
import me.maquzo.TTT.Voting.MapVoting;
import me.maquzo.TTT.utils.ItemBuilder;
import me.maquzo.TTT.utils.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AchievementGUI implements Listener {

    @Getter
    public static final String FIRST_WIN = "Dein erster Sieg :3";
    @Getter
    public static final String FIRST_KILL = "Mache deinen Ersten Kill!";
    @Getter
    public static final String FIRST_DETECITVE_WIN = "Dein erster Detective Sieg";
    @Getter
    public static final String FIRST_TRAITOR_WIN = "Dein erster Traitor Sieg";
    @Getter
    public static final String KILLS_1 = "Mache 10 kills";
    @Getter
    public static final String KILLS_2 = "Mache 25 kills";
    @Getter
    public static final String KILLS_3 = "Mache 50 kills";
    @Getter
    public static final String KILLS_4 = "Mache 75 kills";
    @Getter
    public static final String KILLS_5 = "Mache 100 kills";
    @Getter
    public static final String WINS_1 = "Mache 10 Siege";
    @Getter
    public static final String WINS_2 = "Mache 25 Siege";
    @Getter
    public static final String WINS_3 = "Du hast 50 Siege erreicht. Respekt";
    @Getter
    public static final String WINS_4 = "Mache 75 Siege. Respekt";
    @Getter
    public static final String WINS_5 = "Mache 100 Siege. Respekt";
    @Getter
    public static final String KARMA_1 = "Bekomme 20.000 Karma";
    @Getter
    public static final String KARMA_2 = "Bekomme 50.000 Karma";
    @Getter
    public static final String KARMA_3 = "Bekomme 100.000 Karma";
    @Getter
    public static final String TRAITOR_TESTER_USED = "Teste dich als Traitor";
    @Getter
    public static final String TESTER_USED = "Teste dich zum erstenmal";

    @Getter
    public static final String ACHIEVEMENT_SHOP_TITLE = "§c§lAchievements";

    @Getter
    private Inventory achievementShop;

    private TTT plugin;

    public AchievementGUI(TTT plugin) {
        this.plugin = plugin;
        achievementShop = Bukkit.createInventory(null, 27, ACHIEVEMENT_SHOP_TITLE);
    }

    @EventHandler
    public void onPlayerRightClickVotingItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack itemStack = player.getItemInHand();
            if (itemStack.getItemMeta() == null) return;
            if (itemStack.getItemMeta().getDisplayName() == null) return;
            if (itemStack.getItemMeta().getDisplayName().equals(JoinListener.ACHIEVEMENTS_NAME)) {
                Profile profile = plugin.getProfileManager().getProfile(player);
                if (!profile.getData().getStats().isFirstWinDone()) {
                    achievementShop.setItem(0, new ItemBuilder(Material.BARRIER).setName("§c" + FIRST_WIN).build());
                } else {
                    achievementShop.setItem(0, new ItemBuilder(Material.EMERALD).setName("§a" + FIRST_WIN + " §aDone").build());
                }
                if (!profile.getData().getStats().isFirstKillDone()) {
                    achievementShop.setItem(1, new ItemBuilder(Material.BARRIER).setName("§c" + FIRST_KILL).build());
                } else {
                    achievementShop.setItem(1, new ItemBuilder(Material.EMERALD).setName("§a" + FIRST_KILL + " §aDone").build());
                }
                if (!profile.getData().getStats().isKILLS_1_Done()) {
                    achievementShop.setItem(2, new ItemBuilder(Material.BARRIER).setName("§c" + KILLS_1).build());
                } else {
                    achievementShop.setItem(2, new ItemBuilder(Material.EMERALD).setName("§a" + KILLS_1 + " §aDone").build());
                }
                if (!profile.getData().getStats().isKILLS_2_Done()) {
                    achievementShop.setItem(3, new ItemBuilder(Material.BARRIER).setName("§c" + KILLS_2).build());
                } else {
                    achievementShop.setItem(3, new ItemBuilder(Material.EMERALD).setName("§a" + KILLS_2 + " §aDone").build());
                }
                if (!profile.getData().getStats().isKILLS_3_Done()) {
                    achievementShop.setItem(4, new ItemBuilder(Material.BARRIER).setName("§c" + KILLS_3).build());
                } else {
                    achievementShop.setItem(4, new ItemBuilder(Material.EMERALD).setName("§a" + KILLS_3 + " §aDone").build());
                }
                if (!profile.getData().getStats().isKILLS_4_Done()) {
                    achievementShop.setItem(5, new ItemBuilder(Material.BARRIER).setName("§c" + KILLS_4).build());
                } else {
                    achievementShop.setItem(5, new ItemBuilder(Material.EMERALD).setName("§a" + KILLS_4 + " §aDone").build());
                }
                if (!profile.getData().getStats().isKILLS_5_Done()) {
                    achievementShop.setItem(6, new ItemBuilder(Material.BARRIER).setName("§c" + KILLS_5).build());
                } else {
                    achievementShop.setItem(6, new ItemBuilder(Material.EMERALD).setName("§a" + KILLS_5 + " §aDone").build());
                }
                if (!profile.getData().getStats().isWINS_1_Done()) {
                    achievementShop.setItem(7, new ItemBuilder(Material.BARRIER).setName("§c" + WINS_1).build());
                } else {
                    achievementShop.setItem(7, new ItemBuilder(Material.EMERALD).setName("§a" + WINS_1 + " §aDone").build());
                }
                if (!profile.getData().getStats().isWINS_2_Done()) {
                    achievementShop.setItem(8, new ItemBuilder(Material.BARRIER).setName("§c" + WINS_2).build());
                } else {
                    achievementShop.setItem(8, new ItemBuilder(Material.EMERALD).setName("§a" + WINS_2 + " §aDone").build());
                }
                if (!profile.getData().getStats().isWINS_3_Done()) {
                    achievementShop.setItem(9, new ItemBuilder(Material.BARRIER).setName("§c" + WINS_3).build());
                } else {
                    achievementShop.setItem(9, new ItemBuilder(Material.EMERALD).setName("§a" + KILLS_3 + " §aDone").build());
                }
                if (!profile.getData().getStats().isWINS_4_Done()) {
                    achievementShop.setItem(10, new ItemBuilder(Material.BARRIER).setName("§c" + WINS_4).build());
                } else {
                    achievementShop.setItem(10, new ItemBuilder(Material.EMERALD).setName("§a" + WINS_4 + " §aDone").build());
                }
                if (!profile.getData().getStats().isKILLS_5_Done()) {
                    achievementShop.setItem(11, new ItemBuilder(Material.BARRIER).setName("§c" + WINS_5).build());
                } else {
                    achievementShop.setItem(11, new ItemBuilder(Material.EMERALD).setName("§a" + WINS_5 + " §aDone").build());
                }
                if (!profile.getData().getStats().isFIRST_TRAITOR_WIN_Done()) {
                    achievementShop.setItem(12, new ItemBuilder(Material.BARRIER).setName("§c" + FIRST_TRAITOR_WIN).build());
                } else {
                    achievementShop.setItem(12, new ItemBuilder(Material.EMERALD).setName("§a" + FIRST_TRAITOR_WIN + " Done").build());
                }
                if (!profile.getData().getStats().isFIRST_DET_WIN_Done()) {
                    achievementShop.setItem(13, new ItemBuilder(Material.BARRIER).setName("§c" + FIRST_DETECITVE_WIN).build());
                } else {
                    achievementShop.setItem(13, new ItemBuilder(Material.EMERALD).setName("§a" + FIRST_DETECITVE_WIN + " Done").build());
                }
                if (!profile.getData().getStats().isTraitor_tester_used()) {
                    achievementShop.setItem(14, new ItemBuilder(Material.BARRIER).setName("§c" + TRAITOR_TESTER_USED).build());
                } else {
                    achievementShop.setItem(14, new ItemBuilder(Material.EMERALD).setName("§a" + TRAITOR_TESTER_USED + " Done").build());
                }
                if (!profile.getData().getStats().isTester_used_done()) {
                    achievementShop.setItem(15, new ItemBuilder(Material.BARRIER).setName("§c" + TESTER_USED).build());
                } else {
                    achievementShop.setItem(15, new ItemBuilder(Material.EMERALD).setName("§a" + TESTER_USED + " Done").build());
                }
                if (!profile.getData().getStats().isKARMA_1()) {
                    achievementShop.setItem(16, new ItemBuilder(Material.BARRIER).setName("§c" + KARMA_1).build());
                } else {
                    achievementShop.setItem(16, new ItemBuilder(Material.EMERALD).setName("§a" + KARMA_1 + " Done").build());
                }
                if (!profile.getData().getStats().isKARMA_2()) {
                    achievementShop.setItem(17, new ItemBuilder(Material.BARRIER).setName("§c" + KARMA_2).build());
                } else {
                    achievementShop.setItem(17, new ItemBuilder(Material.EMERALD).setName("§a" + KARMA_2 + " Done").build());
                }
                if (!profile.getData().getStats().isKARMA_3()) {
                    achievementShop.setItem(18, new ItemBuilder(Material.BARRIER).setName("§c" + KARMA_3).build());
                } else {
                    achievementShop.setItem(18, new ItemBuilder(Material.EMERALD).setName("§a" + KARMA_3 + " Done").build());
                }

                player.openInventory(achievementShop);
            }
        }
    }

    @EventHandler
    public void onHandleVoteMenuClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (!event.getInventory().getTitle().equals(ACHIEVEMENT_SHOP_TITLE)) return;
        event.setCancelled(true);
    }

}
