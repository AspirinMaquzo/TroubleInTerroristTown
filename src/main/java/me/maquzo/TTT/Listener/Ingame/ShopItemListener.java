package me.maquzo.TTT.Listener.Ingame;

import me.maquzo.TTT.Listener.GUIS.ShopGUI;
import me.maquzo.TTT.Listener.Lobby.JoinListener;
import me.maquzo.TTT.Manager.Roles.Role;
import me.maquzo.TTT.Manager.gamestates.IngameState.IngameState;
import me.maquzo.TTT.TTT;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ShopItemListener implements Listener {

    private TTT plugin;

    public ShopItemListener(TTT plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void creeperArrow(ProjectileHitEvent event) {
        if (event.getEntity().getType() != EntityType.ARROW) return;
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        Player player = (Player) event.getEntity().getShooter();
        Role role = plugin.getRoleManager().getRole(player);
        if (role != Role.TRAITOR) return;

        if (ShopGUI.removeShopItem(player, Material.SULPHUR)) {
            World world = event.getEntity().getWorld();
            world.spawnEntity(event.getEntity().getLocation(), EntityType.CREEPER);
            event.getEntity().remove();
        }
    }

    @EventHandler
    public void Medkit(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!(plugin.getGameStateManager().getCurrentGameState() instanceof IngameState)) return;
        ItemStack itemStack = player.getItemInHand();
        if (itemStack.getType() == null) return;
        if (event.getAction() != Action.RIGHT_CLICK_AIR || event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            Role role = plugin.getRoleManager().getRole(player);
            if (itemStack.getType() == null) return;
            if (itemStack.getType() == Material.CAKE) {
                if (role == Role.DETECTIVE) {
                    if (player.getHealth() == 20.0D) {
                        player.sendMessage(TTT.error + "Du bist schon voll geheilt!");
                        return;
                    }
                    ShopGUI.removeShopItem(player, Material.CAKE);
                    player.setHealth(player.getHealth() + 8.0D);
                }
            }

        }
    }

}
