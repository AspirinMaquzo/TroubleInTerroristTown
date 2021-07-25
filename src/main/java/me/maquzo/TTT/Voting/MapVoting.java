package me.maquzo.TTT.Voting;

import me.maquzo.TTT.TTT;
import me.maquzo.TTT.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MapVoting {

    public static final int MAP_AMOUNT = 2;
    public static final String VOTING_INV_TITLE = "§c§lVoting";

    private final TTT plugin;
    private final ArrayList<Map> maps;
    private final Map[] votingMaps;
    private final int[] votingInventoryOrder = new int[]{9, 17};
    private final int[] infoPlace = new int[]{18, 26};
    private final HashMap<String, Integer> playerVotes;
    private Inventory votingInventory;

    public MapVoting(TTT plugin, ArrayList<Map> maps) {
        this.plugin = plugin;
        this.maps = maps;
        votingMaps = new Map[MapVoting.MAP_AMOUNT];
        playerVotes = new HashMap<>();

        randomMaps();
        VotingGUI();
    }


    private void randomMaps() {
        for (int i = 0; i < votingMaps.length; i++) {
            Collections.shuffle(maps);
            votingMaps[i] = maps.remove(0);
        }
    }

    public void VotingGUI() {
        votingInventory = Bukkit.createInventory(null, 9 * 3, VOTING_INV_TITLE);
        for (int i = 0; i < votingMaps.length; i++) {
            Map currentMap = votingMaps[i];
            votingInventory.setItem(votingInventoryOrder[i], new ItemBuilder(Material.PAPER).setName("§c§lVote for: " + currentMap.getMapname()).build());
            votingInventory.setItem(infoPlace[i], new ItemBuilder(Material.BOOK).setName("§c§lName: §8" + currentMap.getMapname()).setLore("§c§lVotes: §8" + currentMap.getVotes(), "§c§lBuilder: §8" + currentMap.getBuilder()).build());
        }


        votingInventory.setItem(1, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(ChatColor.BLACK + "Placeholder").build());
        votingInventory.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(ChatColor.BLACK + "Placeholder").build());
        votingInventory.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(ChatColor.BLACK + "Placeholder").build());
        votingInventory.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(ChatColor.BLACK + "Placeholder").build());
        votingInventory.setItem(5, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(ChatColor.BLACK + "Placeholder").build());
        votingInventory.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(ChatColor.BLACK + "Placeholder").build());
        votingInventory.setItem(7, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(ChatColor.BLACK + "Placeholder").build());
        votingInventory.setItem(19, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(ChatColor.BLACK + "Placeholder").build());
        votingInventory.setItem(20, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(ChatColor.BLACK + "Placeholder").build());
        votingInventory.setItem(21, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(ChatColor.BLACK + "Placeholder").build());
        votingInventory.setItem(22, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(ChatColor.BLACK + "Placeholder").build());
        votingInventory.setItem(23, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(ChatColor.BLACK + "Placeholder").build());
        votingInventory.setItem(24, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(ChatColor.BLACK + "Placeholder").build());
        votingInventory.setItem(25, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(ChatColor.BLACK + "Placeholder").build());


        votingInventory.setItem(0, new ItemBuilder(Material.HOPPER).setName(ChatColor.BLACK + "Placeholder").build());
        votingInventory.setItem(8, new ItemBuilder(Material.HOPPER).setName(ChatColor.BLACK + "Placeholder").build());


    }


    public Map getWinnerMap() {
        Map winnerMap = votingMaps[0];
        for (int i = 1; i < votingMaps.length; i++) {
            if (votingMaps[i].getVotes() >= winnerMap.getVotes())
                winnerMap = votingMaps[i];
        }
        return winnerMap;
    }

    public void voteMap(Player player, int votingMap) {
        if (!playerVotes.containsKey(player.getName())) {
            votingMaps[votingMap].addMapVote();
            player.closeInventory();
            player.sendMessage(TTT.prefix + "Du hast für die Map §c§l" + votingMaps[votingMap].getMapname() + " §7gevotet!");
            playerVotes.put(player.getName(), votingMap);
            VotingGUI();
        } else
        player.sendMessage(TTT.error + "§7Du hast bereits §c§lgevotet!");
    }

    public HashMap<String, Integer> getPlayerVotes() {
        return playerVotes;
    }

    public Inventory getVotingInventory() {
        return votingInventory;
    }

    public int[] getVotingInventoryOrder() {
        return votingInventoryOrder;
    }

    public ArrayList<Map> getMaps() {
        return maps;
    }

    public Map[] getVotingMaps() {
        return votingMaps;
    }

}
