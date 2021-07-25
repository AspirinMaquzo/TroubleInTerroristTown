package me.maquzo.TTT.Tester;


import me.maquzo.TTT.Listener.GUIS.ShopGUI;
import me.maquzo.TTT.Manager.Roles.Role;
import me.maquzo.TTT.Settings.TrapListener;
import me.maquzo.TTT.Voting.Map;
import me.maquzo.TTT.TTT;
import me.maquzo.TTT.utils.ConfigLocation;
import me.maquzo.TTT.utils.Profile;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Tester {

    private Block[] borderBlocks, lamps;
    private Block button;
    private Location testerLocation;


    private boolean inUse;

    private TTT plugin;
    private Map map;
    private World world;

    private static int TESTING_TIME = 8;

    public Tester(Map map, TTT plugin) {
        borderBlocks = new Block[3];
        lamps = new Block[2];

        this.map = map;
        this.plugin = plugin;
    }


    public void test(Player player) {
        Role role = plugin.getRoleManager().getRole(player);
        Profile profile = plugin.getProfileManager().getProfile(player);
        if (role == Role.DETECTIVE) {
            player.sendMessage(TTT.error + "Als §b§lDetective §7kannst du dich nicht testen!");
            return;
        }
        if (inUse) {
            player.sendMessage(TTT.error + "Der §c§lTester §7wird schon benutzt!");
            return;
        }



        if (role == Role.TRAITOR) {
            if (ShopGUI.removeShopItem(player, Material.WOOD_BUTTON)) {
                player.sendMessage("Du wirst als Innocent getarnt!");
                role = Role.INNOCENT;
            } else {
                if (!profile.getData().getStats().isTraitor_tester_used()) {
                    profile.getData().getStats().setTraitorPassActive(true);
                    player.sendMessage(TTT.prefix + "Der Spieler §c§l" + profile.getPlayerName() + " §7Hat die Challenge: §e§k| §eTeste dich als Traitor §k| §7geschafft! (Versteckt)");
                    profile.getData().getStats().increaseKarma(100);
                    profile.getData().save();
                }
            }
        }

        if (role == Role.INNOCENT) {
            if (!profile.getData().getStats().isTester_used_done()) {
                profile.getData().getStats().setTester_used_done(true);
                Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + profile.getPlayerName() + " §7Hat die Challenge: §e§k| §eTeste dich das erste mal! §k| §7geschafft!");
                profile.getData().getStats().increaseKarma(100);
                profile.getData().save();
            }
        }


        Bukkit.broadcastMessage(TTT.prefix + "Ein Spieler hat den §c§lTester §7betreten");
        player.teleport(button.getLocation());
        inUse = true;

        for (Block current : borderBlocks)
            setColoredGlass(current.getLocation(), DyeColor.WHITE);

        for (Entity current : player.getNearbyEntities(2, 2, 2)) {
            if (current instanceof Player)
                ((Player) current).teleport(testerLocation);
        }


        if (plugin.isTrap()) {
            if (plugin.isWasTrap()) {
                return;
            } else {
                plugin.setWasTrap(true);
                player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.PRIMED_TNT);
            }
        }

        Role endRole = role;
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                endTesting(endRole);
            }
        }, TESTING_TIME * 20L);
    }

    private void endTesting(Role role) {
        for (Block current : lamps)
            setColoredGlass(current.getLocation(), (role == Role.INNOCENT) ? DyeColor.GREEN : DyeColor.RED);
        for (Block current : borderBlocks)
            world.getBlockAt(current.getLocation()).setType(Material.AIR);

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

            @Override
            public void run() {
                resetTester();
            }
        }, TESTING_TIME * 20L);
    }

    public void load() {
        for (int i = 0; i < borderBlocks.length; i++)
            borderBlocks[i] = new ConfigLocation(plugin, "Maps." + map.getMapname() + ".Tester.Borderblocks." + i).loadBlockLocation();
        for (int i = 0; i < lamps.length; i++)
            lamps[i] = new ConfigLocation(plugin, "Maps." + map.getMapname() + ".Tester.Lamps." + i).loadBlockLocation();

        button = new ConfigLocation(plugin, "Maps." + map.getMapname() + ".Tester.Button").loadBlockLocation();
        testerLocation = new ConfigLocation(plugin, "Maps." + map.getMapname() + ".Tester.Location").loadLocation();

        world = map.getSpawnLocations()[1].getWorld();
        resetTester();
    }

    private void resetTester() {
        inUse = false;

        for (Block current : borderBlocks)
            world.getBlockAt(current.getLocation()).setType(Material.AIR);
        for (Block current : lamps)
            setColoredGlass(current.getLocation(), DyeColor.BLACK);
    }

    private void setColoredGlass(Location location, DyeColor dyeColor) {
        Block block = world.getBlockAt(location);
        block.setType(Material.STAINED_GLASS);
        block.setData(dyeColor.getData());
    }

    public boolean exists() {
        return plugin.getConfig().getString("Maps." + map.getMapname() + ".Tester.Location.World") != null;
    }

    public Block getButton() {
        return button;
    }
}
