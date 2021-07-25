package me.maquzo.TTT.Listener.Lobby;

import me.maquzo.TTT.Files.BookConfig;
import me.maquzo.TTT.Manager.gamestates.IngameState.IngameState;
import me.maquzo.TTT.Manager.gamestates.LobbyState.LobbyState;
import me.maquzo.TTT.TTT;
import me.maquzo.TTT.Tasks.LobbyCountdown;
import me.maquzo.TTT.utils.ConfigLocation;
import me.maquzo.TTT.utils.ItemBuilder;
import me.maquzo.TTT.utils.Profile;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class JoinListener implements Listener {

    public static final String MAPVOTING_NAME = "§c§lMapvoting §7(rechtsklick)";
    public static final String MAGMA_NAME = "§c§lVerlassen §7(rechtsklick)";
    public static final String SETTINGS_NAME = "§c§lSettings §7(rechtsklick)";
    public final static String ACHIEVEMENTS_NAME = "§c§lErfolge §7(rechtsklick)";

    private TTT plugin;
    private ItemStack voteItem;

    public JoinListener(TTT plugin) {
        this.plugin = plugin;
        voteItem = new ItemBuilder(Material.PAPER).setName(MAPVOTING_NAME).setLore("§8Vote für eine Map").build();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (plugin.getGameStateManager().getCurrentGameState() instanceof IngameState) {
            player.kickPlayer(TTT.error + "Das Spiel hat bereits begonnen!");
            return;
        }




        try {
            plugin.getProfileManager().handleProfileCreation(player.getUniqueId(), player.getName());
            Profile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
            profile.getData().load();
            player.sendMessage(TTT.prefix + "§aYour Data has been Successfully loaded!");
            Bukkit.getLogger().info("§a§lMongoDB-Core§8: §aData Successfully loaded from " + player.getName());
        } catch (Exception e) {
            Bukkit.getLogger().info("§4§lMongoDB-Core§8: §cThere was an Error loading the Data from " + player.getName());
            player.sendMessage(TTT.error + "§cThere was an Error trying to load your Data please contact an Admin!");
            e.printStackTrace();
        }


        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = scoreboard.registerNewObjective("abcd", "abcd");
        LobbyState lobbyState = (LobbyState) plugin.getGameStateManager().getCurrentGameState();
        Profile profile = plugin.getProfileManager().getProfile(player);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§8× §c§lTTT §8×");
        obj.getScore("§b").setScore(7);
        obj.getScore("§8× §c§lSpieler§8:").setScore(6);
        obj.getScore("§7" + Bukkit.getOnlinePlayers().size()).setScore(5);
        obj.getScore("§f").setScore(4);
        obj.getScore("§8× §c§lKarma§8:").setScore(3);
        obj.getScore("§7" + profile.getData().getStats().getKarma()).setScore(2);
        obj.getScore("§a").setScore(1);
        obj.getScore("§crenixinside.de").setScore(0);
        player.setScoreboard(scoreboard);

        if (!(plugin.getGameStateManager().getCurrentGameState() instanceof LobbyState)) return;
        plugin.getPlayers().add(player);
        event.setJoinMessage(TTT.prefix + "Der Spieler §c§l" + player.getDisplayName() + " §7hat das Spiel §cbetreten §8§l[§c§l" + plugin.getPlayers().size() + "§8§l/" + "§c§l" + LobbyState.MAX_PLAYERS + "§8§l]");
        player.sendMessage(TTT.prefix + "Du kannst nur einmal für eine Map §c§lVoten!");


        ConfigLocation configLocation = new ConfigLocation(plugin, "Lobby");
        if (configLocation.loadLocation() == null) {
            Bukkit.getConsoleSender().sendMessage(TTT.error + "Die §c§lLobby §7wurde noch nicht gesetzt!");
        } else
            player.teleport(configLocation.loadLocation());

        player.getInventory().clear();
        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.setExhaustion(20F);
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().setChestplate(null);
        player.getInventory().setHelmet(null);


        for (Player all : Bukkit.getOnlinePlayers()) {
            all.showPlayer(player);
            all.showPlayer(all);
        }

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        ItemMeta Itemmeta = book.getItemMeta();
        Itemmeta.setDisplayName("§c§lAnleitung §7(rechtsklick)");
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setTitle("TTT Anleitung");
        meta.addPage(BookConfig.get().getString("Site1"));
        meta.addPage(BookConfig.get().getString("Site2"));
        meta.addPage(BookConfig.get().getString("Site3"));
        meta.addPage(BookConfig.get().getString("Site4"));
        meta.addPage(BookConfig.get().getString("Site5"));

        book.setItemMeta(meta);
        meta.setPage(1, "Site1");
        meta.setPage(2, "Site2");
        meta.setPage(3, "Site3");
        meta.setPage(4, "Site4");
        meta.setPage(5, "Site5");

        player.getInventory().clear();

        player.getInventory().setItem(0, new ItemBuilder(Material.NETHER_STAR).setName(ACHIEVEMENTS_NAME).setLore("§8Zeigt dir deine Erfolge").build());
        player.getInventory().setItem(2, new ItemBuilder(Material.REDSTONE_TORCH_ON).setName(SETTINGS_NAME).setLore("§8Stelle das Spiel ein").build());
        player.getInventory().setItem(4, book);
        player.getInventory().setItem(6, voteItem);
        player.getInventory().setItem(8, new ItemBuilder(Material.MAGMA_CREAM).setName(MAGMA_NAME).setLore("§8Verlasse dass Spiel").build());



        LobbyCountdown lobbyCountdown = lobbyState.getLobbyCountdown();
        if (plugin.getPlayers().size() >= LobbyState.MIN_PLAYERS) {
            if (!lobbyCountdown.isRunning()) {
                lobbyCountdown.stopIdle();
                lobbyCountdown.start();
            }
        }

        profile.getData().getStats().setDetectivePassActive(false);
        profile.getData().getStats().setTraitorPassActive(false);
        profile.getData().save();



        if (!player.hasPlayedBefore()) {
            profile.getData().getStats().increaseKarma(50);
            player.sendMessage(TTT.prefix + "§a+50 Karma Grund: Die erste TTT Runde!");
            profile.getData().save();
        }

    }


}
