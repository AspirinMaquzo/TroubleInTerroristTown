package me.maquzo.TTT.Settings;

import lombok.Getter;
import lombok.Setter;
import me.maquzo.TTT.Manager.Roles.Role;
import me.maquzo.TTT.Manager.gamestates.IngameState.IngameState;
import me.maquzo.TTT.Manager.gamestates.LobbyState.LobbyState;
import me.maquzo.TTT.TTT;
import me.maquzo.TTT.Voting.Map;
import me.maquzo.TTT.utils.ConfigLocation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class TrapListener implements Listener {

    @Getter
    @Setter
    private Block lever;
    private Map map;

    private TTT plugin;

    public TrapListener(TTT plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Role role = plugin.getRoleManager().getRole(player);
        if (!(plugin.getGameStateManager().getCurrentGameState() instanceof IngameState)) return;
        if (role != Role.TRAITOR) return;
        Block block = event.getClickedBlock();
        if (block == null) return;
        if (block.getType() != Material.LEVER) return;
        if (plugin.isTrap()) {
            player.sendMessage(TTT.error + "Die Trap wurde bereits aktiviert");
            return;
        }
        if (plugin.isWasTrap()) return;
        plugin.setTrap(true);
        player.sendMessage(TTT.prefix + "Du hast die §4Traitor-Falle §7Aktiviert!");
    }
}
