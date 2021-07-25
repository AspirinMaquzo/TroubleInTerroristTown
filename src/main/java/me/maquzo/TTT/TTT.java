package me.maquzo.TTT;

import lombok.Getter;
import lombok.Setter;
import me.maquzo.TTT.Commands.*;
import me.maquzo.TTT.Files.BookConfig;
import me.maquzo.TTT.Files.MongoDBConfig;
import me.maquzo.TTT.Listener.ChatListener;
import me.maquzo.TTT.Listener.Ingame.ProgressListener;
import me.maquzo.TTT.Listener.Ingame.ShopItemListener;
import me.maquzo.TTT.Listener.Lobby.JoinListener;
import me.maquzo.TTT.Listener.Lobby.MagmaInteract;
import me.maquzo.TTT.Listener.ProtectListener;
import me.maquzo.TTT.Listener.Lobby.QuitListener;
import me.maquzo.TTT.Listener.TesterListener;
import me.maquzo.TTT.Manager.ChestManager;
import me.maquzo.TTT.Manager.gamestates.GameState;
import me.maquzo.TTT.Manager.gamestates.GameStateManager;
import me.maquzo.TTT.Listener.GUIS.SettingsGUI;
import me.maquzo.TTT.Settings.TrapListener;
import me.maquzo.TTT.Voting.Map;
import me.maquzo.TTT.Voting.MapVoting;
import me.maquzo.TTT.Listener.GUIS.*;
import me.maquzo.TTT.MongoDB.MongoManager;
import me.maquzo.TTT.utils.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import me.maquzo.TTT.Manager.Roles.*;

import java.util.ArrayList;

public class TTT extends JavaPlugin {


    FileConfiguration config = getConfig();
    private GameStateManager gameStateManager;

    private ArrayList<Player> players;
    private ArrayList<Map> maps;
    private MapVoting voting;
    private RoleManager roleManager;
    private Map map;
    private SettingsGUI settings;
    @Getter
    private ShopGUI shops;
    @Getter
    private PassBuyGUI pass;
    @Getter
    private PassGUI passGUI;


    public final static String prefix = "§c§lTTT §8x §7 ";
    public final static String noperms = prefix + "Dazu fehlen dir die §c§lRechte§7!";
    public final static String error = prefix + "§c§lError: §7";

    @Getter
    @Setter
    public boolean trap;
    @Getter
    @Setter
    public boolean wasTrap;

    @Getter
    private static TTT plugin;
    @Getter
    private ProfileManager profileManager;
    @Getter
    private MongoManager mongoManager;




    @Override
    public void onEnable() {
        plugin = this;
        trap = false;
        saveConfig();

<<<<<<< HEAD
        MongoDBConfig.setup();
        MongoDBConfig.get().addDefault("host", "host");
        MongoDBConfig.get().addDefault("password", "password");
        MongoDBConfig.get().addDefault("username", "username");
        MongoDBConfig.get().addDefault("database", "database");
        MongoDBConfig.get().options().copyDefaults(true);
        MongoDBConfig.save();

        BookConfig.setup();
        BookConfig.get().addDefault("Site1", "Site1");
        BookConfig.get().addDefault("Site2", "Site2");
        BookConfig.get().addDefault("Site3", "Site3");
        BookConfig.get().addDefault("Site4", "Site4");
        BookConfig.get().addDefault("Site5", "Site5");
        BookConfig.get().options().copyDefaults(true);
        BookConfig.save();

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");


        this.mongoManager = new MongoManager(MongoDBConfig.get().getString("host"), 27017);
=======
        this.mongoManager = new MongoManager("ICh finde dich", 27017);
>>>>>>> 46888185c224ca5389c203a78e9a7bd8b1274085
        mongoManager.connect();
        profileManager = new ProfileManager(this);


        gameStateManager = new GameStateManager(this);
        players = new ArrayList<Player>();
        gameStateManager.setGameState(GameState.LOBBY_STATE);
        Init();

    }


    public void Init() {
        Voting();
        shops = new ShopGUI(this);
        pass = new PassBuyGUI(this);
        settings = new SettingsGUI(this);
        roleManager = new RoleManager(this);
        passGUI = new PassGUI(this);
        PluginManager pluginManager = Bukkit.getPluginManager();
        getCommand("setup").setExecutor(new SetupCommand(this));
        getCommand("Forcestart").setExecutor(new ForceStartCommand(this));
        getCommand("shop").setExecutor(new ShopCommand(this));
        getCommand("stats").setExecutor(new StatsCommand(this));
        getCommand("PassShop").setExecutor(new PassShopCommand(this));
        getCommand("PassList").setExecutor(new PassListCommand(this));
        getCommand("Pass").setExecutor(new PassCommand(this));
        getCommand("reset").setExecutor(new ResetDataCommand(this));
        getCommand("Rank").setExecutor(new RankCommand(this));
        pluginManager.registerEvents(new JoinListener(this), this);
        pluginManager.registerEvents(new QuitListener(this), this);
        pluginManager.registerEvents(new ProtectListener(this), this);
        pluginManager.registerEvents(new VotingGUI(this), this);
        pluginManager.registerEvents(new ProgressListener(this), this);
        pluginManager.registerEvents(new ChatListener(this), this);
        pluginManager.registerEvents(new ChestManager(this), this);
        pluginManager.registerEvents(new TesterListener(this), this);
        pluginManager.registerEvents(new MagmaInteract(this), this);
        pluginManager.registerEvents(new ShopGUI(this), this);
        pluginManager.registerEvents(new ShopItemListener(this), this);
        pluginManager.registerEvents(new PassBuyGUI(this), this);
        pluginManager.registerEvents(new SettingsGUI(this), this);
        pluginManager.registerEvents(new PassGUI(this), this);
        pluginManager.registerEvents(new TrapListener(this), this);
        pluginManager.registerEvents(new AchievementGUI(this), this);
        pluginManager.registerEvents(new NavGui(this), this);
    }



    private void Voting() {
        maps = new ArrayList<>();
        if (getConfig().getConfigurationSection("Maps") == null || getConfig() == null) {
            getServer().dispatchCommand(getServer().getConsoleSender(), "Wenn hier ein Error kommt ist es nicht schlimm, nach dem man die Maps eingerichtet hat verschwindet er :)");
        } else {
            for (String current : getConfig().getConfigurationSection("Maps").getKeys(false)) {
                Map map = new Map(this, current);
                if (map.mapReady())
                    maps.add(map);
                else
                    Bukkit.getConsoleSender().sendMessage(TTT.error + "Map §c§l" + map.getMapname() + " §7wurde nicht geladen");
            }
            if (maps.size() >= MapVoting.MAP_AMOUNT)
                voting = new MapVoting(this, maps);
            else {
                Bukkit.getConsoleSender().sendMessage(error + "Nicht genug §c§lMaps §7für das §c§lVoting!");
                voting = null;
            }
        }
    }


    public ArrayList<Player> getPlayers() {
        return players;
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public ArrayList<Map> getMaps() {
        return maps;
    }

    public MapVoting getVoting() {
        return voting;
    }

    public RoleManager getRoleManager() {
        return roleManager;
    }

    public SettingsGUI getSettings() {
        return settings;
    }

    public ShopGUI getShops() {
        return shops;
    }

    public PassBuyGUI getPass() {
        return pass;
    }
}
