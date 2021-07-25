package me.maquzo.TTT.Listener.GUIS;

import me.maquzo.TTT.Listener.Lobby.JoinListener;
import me.maquzo.TTT.TTT;
import me.maquzo.TTT.Voting.MapVoting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class VotingGUI implements Listener {

    private TTT plugin;
    private MapVoting votingMap;

    public VotingGUI(TTT plugin) {
        this.plugin = plugin;
        votingMap = plugin.getVoting();
    }

    @EventHandler
    public void onPlayerRightClickVotingItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack itemStack = player.getItemInHand();
            if (itemStack.getItemMeta() == null) return;
            if (votingMap == null)  {
                player.closeInventory();
                player.sendMessage(TTT.error + "Wie es aussieht sind nicht genug §c§lMaps §7vorhanden!");
                return;
            }
            if (itemStack.getItemMeta().getDisplayName() == null) return;
            if (itemStack.getItemMeta().getDisplayName().equals(JoinListener.MAPVOTING_NAME))
                player.openInventory(votingMap.getVotingInventory());
        }
    }

    @EventHandler
    public void onHandleVoteMenuClick(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (!event.getInventory().getTitle().equals(MapVoting.VOTING_INV_TITLE)) return;
        event.setCancelled(true);
        player.closeInventory();
        for (int i = 0; i < votingMap.getVotingInventoryOrder().length; i++) {
            if (votingMap.getVotingInventoryOrder()[i] == event.getSlot()) {
                votingMap.voteMap(player, i);
                player.closeInventory();
                return;
            }
        }
    }

}
