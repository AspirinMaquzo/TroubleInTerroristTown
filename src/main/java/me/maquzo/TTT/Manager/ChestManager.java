package me.maquzo.TTT.Manager;

import me.maquzo.TTT.Manager.gamestates.IngameState.IngameState;
import me.maquzo.TTT.TTT;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ChestManager implements Listener {

    private TTT plugin;
    private ItemStack woodenSword, stoneSword, ironSword, bow, arrows;

    public ChestManager(TTT plugin) {
        this.plugin = plugin;

        woodenSword = new ItemStack(Material.WOOD_SWORD);
        stoneSword = new ItemStack(Material.STONE_SWORD);
        ironSword = new ItemStack(Material.IRON_SWORD);
        bow = new ItemStack(Material.BOW);
        arrows = new ItemStack(Material.ARROW, 32);

    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock().getType() != Material.CHEST) return;
        event.setCancelled(true);
        if (player.getGameMode().equals(GameMode.SPECTATOR)) return;
        if (!(plugin.getGameStateManager().getCurrentGameState() instanceof IngameState)) return;
        if (player.getInventory().contains(stoneSword) && player.getInventory().contains(arrows) && player.getInventory().contains(bow)) {
            player.sendMessage(TTT.error + "Du hast schon alles was du brauchst!");
        } else if (player.getInventory().contains(ironSword) && player.getInventory().contains(arrows) && player.getInventory().contains(bow)) {
            player.sendMessage(TTT.error + "Du hast schon alles was du brauchst!");
        }
        if (!player.getInventory().contains(woodenSword))
            openChest(woodenSword, player, event.getClickedBlock());
        else if (!player.getInventory().contains(bow)) {
            openChest(bow, player, event.getClickedBlock());
            player.getInventory().addItem(arrows);
        } else if (!player.getInventory().contains(stoneSword)) {
            openChest(stoneSword, player, event.getClickedBlock());
        }
        player.updateInventory();


    }

    @EventHandler
    public void onHandleEnderChest(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock().getType() != Material.ENDER_CHEST) return;
        event.setCancelled(true);
        if (player.getGameMode().equals(GameMode.SPECTATOR)) return;
        if (!(plugin.getGameStateManager().getCurrentGameState() instanceof IngameState)) return;

        IngameState ingameState = (IngameState) plugin.getGameStateManager().getCurrentGameState();
        if (!ingameState.isInGrace())
            openChest(ironSword, player, event.getClickedBlock());
    }

    private void openChest(ItemStack itemStack, Player player, Block block) {
        player.getInventory().addItem(itemStack);
        block.setType(Material.AIR);
    }

}
