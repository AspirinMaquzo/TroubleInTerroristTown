package me.maquzo.TTT.Settings;

import me.maquzo.TTT.Voting.Map;
import me.maquzo.TTT.TTT;
import me.maquzo.TTT.utils.ConfigLocation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class TrapSetup implements Listener {

    private Block lever;
    private Map map;
    private Player player;
    private boolean finished;

    private int phase;

    private TTT plugin;

    public TrapSetup(Player player, Map map,  TTT plugin) {
        this.plugin = plugin;
        this.player = player;
        this.map = map;
        phase = 1;

        Bukkit.getPluginManager().registerEvents(this, plugin);

        finished = false;
        startSetup();
    }

    @EventHandler
    public void onBlockBreake(BlockBreakEvent event) {
        if (!event.getPlayer().getName().equalsIgnoreCase(player.getName())) return;
        if (finished) return;
        event.setCancelled(true);
        switch (phase) {
            case 1:
                if ((event.getBlock().getType() == Material.LEVER)) {
                    lever = event.getBlock();
                    player.sendMessage(TTT.prefix + "Du hast einen §c§lTrap-Lever §7gesetzt!");
                    phase++;
                    startPhase(phase);
                    endSetup();
                } else
                    player.sendMessage(TTT.error + "Bitte klicke einen §c§lLever §7an!");
                break;
        }
    }


    public void endSetup() {
        player.sendMessage(TTT.prefix + "Du hast das §c§lTrap-Setup §7erfolgreich abgeschlossen");
        finished = true;
        new ConfigLocation(plugin, lever.getLocation(), "Maps." + map.getMapname() + ".Trap.lever").saveBlockLocation();
    }

    public void startPhase(int phase) {
        switch (phase) {
            case 1:
                player.sendMessage(TTT.prefix + "Klicke einen §c§lLever §7an!");
                break;
        }
    }


    public void startSetup() {
        player.sendMessage(" ");
        player.sendMessage(TTT.prefix + "§c§lTrap-Setup §7wurde gestartet!");
        player.sendMessage(" ");
    }

}
