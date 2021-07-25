package me.maquzo.TTT.Tester;

import me.maquzo.TTT.Voting.Map;
import me.maquzo.TTT.TTT;
import me.maquzo.TTT.utils.ConfigLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class TesterSetup implements Listener {

    private Player player;
    private int phase;
    private Map map;


    private Block[] borderBlocks, lamps;
    private Block button;
    private Location testerLocation;
    private boolean finished;

    private TTT plugin;

    public TesterSetup(Player player, Map map,  TTT plugin) {
        this.plugin = plugin;
        this.player = player;
        this.map = map;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        phase = 1;

        finished = false;

        borderBlocks = new Block[3];
        lamps = new Block[2];

        startSetup();
    }

    @EventHandler
    public void onBlockBreake(BlockBreakEvent event) {
        if (!event.getPlayer().getName().equalsIgnoreCase(player.getName())) return;
        if (finished) return;
        event.setCancelled(true);
        switch (phase) {
            case 1: case 2: case 3:
                borderBlocks[phase-1] = event.getBlock();
                player.sendMessage(TTT.prefix + "Du hast einen §c§l" + phase + " §7Borderblock gesetzt!");
                phase++;
                startPhase(phase);
                break;
            case 4: case 5:
                if (event.getBlock().getType() == Material.GLASS) {
                    lamps[phase-4] = event.getBlock();
                    player.sendMessage(TTT.prefix + "Du hast einen §c§l" + (phase -3) + " §7Lamp gesetzt!");
                    phase++;
                    startPhase(phase);
                } else
                    player.sendMessage(TTT.error + "Bitte klicke einen §c§lGlassblock §7an!");
                break;

            case 6:
                if ((event.getBlock().getType() == Material.WOOD_BUTTON) || (event.getBlock().getType() == Material.STONE_BUTTON)) {
                    button = event.getBlock();
                    player.sendMessage(TTT.prefix + "Du hast einen §c§lTester-Button §7gesetzt!");
                    phase++;
                    startPhase(phase);
                } else
                    player.sendMessage(TTT.error + "Bitte klicke einen §c§lButton §7an!");
                break;
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        if (!event.getPlayer().getName().equalsIgnoreCase(player.getName())) return;
        if (finished) return;
        if (phase == 7) {
            testerLocation = player.getLocation();
            player.sendMessage(TTT.prefix + "Du hast den §c§lTester-Spawn §7gesetzt!");
            endSetup();
        }
    }

    public void endSetup() {
        player.sendMessage(TTT.prefix + "Du hast das §c§lTester-Setup §7erfolgreich abgeschlossen");
        finished = true;

        for (int i = 0; i < borderBlocks.length; i++)
            new ConfigLocation(plugin, borderBlocks[i].getLocation(), "Maps." + map.getMapname() + ".Tester.Borderblocks." + i).saveBlockLocation();
        for (int i = 0; i < lamps.length; i++)
            new ConfigLocation(plugin, lamps[i].getLocation(), "Maps." + map.getMapname() + ".Tester.Lamps." + i).saveBlockLocation();

        new ConfigLocation(plugin, button.getLocation(), "Maps." + map.getMapname() + ".Tester.Button").saveBlockLocation();
        new ConfigLocation(plugin, testerLocation, "Maps." + map.getMapname() + ".Tester.Location").setLocation();
    }

    public void startPhase(int phase) {
        switch (phase) {
            case 1: case 2: case 3:
                player.sendMessage(TTT.prefix + "Klicke einen §c§lBorderblock §7an!");
                break;
            case 4: case 5:
                player.sendMessage(TTT.prefix + "Klicke eine §c§lLamp §7an!");
                break;
            case 6:
                player.sendMessage(TTT.prefix + "Klicke einen §c§lTester-Button §7an!");
                break;
            case 7:
                player.sendMessage(TTT.prefix + "Sneake an der §c§lTester-Spawn-Location");
                break;
        }

    }

    public void startSetup() {
        player.sendMessage(" ");
        player.sendMessage(TTT.prefix + "§c§lTester-Setup §7wurde gestartet!");
        player.sendMessage(" ");
        startPhase(phase);
    }
}
