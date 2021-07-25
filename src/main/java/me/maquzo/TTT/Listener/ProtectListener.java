package me.maquzo.TTT.Listener;

import me.maquzo.TTT.Listener.Lobby.JoinListener;
import me.maquzo.TTT.Manager.gamestates.EndingState.EndingState;
import me.maquzo.TTT.Manager.gamestates.IngameState.IngameState;
import me.maquzo.TTT.Manager.gamestates.LobbyState.LobbyState;
import me.maquzo.TTT.TTT;
import me.maquzo.TTT.Tasks.LobbyCountdown;
import me.maquzo.TTT.Tasks.RoleCountdown;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ProtectListener implements Listener {

    private TTT plugin;
    private RoleCountdown roleCountdown;


    public ProtectListener(TTT plugin) {
        this.plugin = plugin;
        roleCountdown = new RoleCountdown(plugin);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        } else
            event.setCancelled(false);

    }



    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(plugin.getGameStateManager().getCurrentGameState() instanceof IngameState) || (plugin.getGameStateManager().getCurrentGameState() instanceof EndingState)) {
            event.setCancelled(true);
            return;
        }

        IngameState ingameState = (IngameState) plugin.getGameStateManager().getCurrentGameState();
        if (ingameState.isInGrace()) {
            event.setCancelled(true);

        }
    }

    @EventHandler
    public void onDamageByBlock(EntityDamageByBlockEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(plugin.getGameStateManager().getCurrentGameState() instanceof IngameState) || (plugin.getGameStateManager().getCurrentGameState() instanceof EndingState)) {
            event.setCancelled(true);
            return;
        }

        IngameState ingameState = (IngameState) plugin.getGameStateManager().getCurrentGameState();
        if (ingameState.isInGrace()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockExplode(EntityExplodeEvent e) {
        if (!(e.getEntity() instanceof TNTPrimed || e.getEntity() instanceof Creeper)) return;
        e.setCancelled(true);
    }


    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Material material = event.getItemDrop().getItemStack().getType();
        if (material == Material.LEATHER_CHESTPLATE || material == Material.STICK) {
            event.setCancelled(true);
        } else if (material == Material.WOOD_SWORD || material == Material.STONE_SWORD || material == Material.IRON_SWORD || material == Material.DIAMOND_SWORD || material == Material.BOW || material == Material.ARROW) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMoveChestPlate(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE)
            event.setCancelled(true);
    }

    @EventHandler
    public void handleCreatureSpawn(CreatureSpawnEvent event) {

    }

    @EventHandler
    public void onHandleBedEnter(PlayerBedEnterEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handleBowShot(EntityShootBowEvent event) {
        if (!(plugin.getGameStateManager().getCurrentGameState() instanceof IngameState) || (plugin.getGameStateManager().getCurrentGameState() instanceof EndingState)) {
            event.setCancelled(true);
            return;
        }

        IngameState ingameState = (IngameState) plugin.getGameStateManager().getCurrentGameState();
        if (ingameState.isInGrace()) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        player.getInventory().setChestplate(null);
        player.getInventory().setHelmet(null);

        if (plugin.getGameStateManager().getCurrentGameState() instanceof IngameState) {
            IngameState ingameState = (IngameState) plugin.getGameStateManager().getCurrentGameState();
            ingameState.addSpec(player);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        } else
            event.setCancelled(false);
    }

    @EventHandler
    public void spawnCreature(CreatureSpawnEvent event) {
        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) return;
        Entity entity = event.getEntity();
        if (entity instanceof Creeper) {
            event.setCancelled(true);
        }
    }

}
