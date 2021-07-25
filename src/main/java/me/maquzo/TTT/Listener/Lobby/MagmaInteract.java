package me.maquzo.TTT.Listener.Lobby;

import me.maquzo.TTT.Manager.gamestates.LobbyState.LobbyState;
import me.maquzo.TTT.TTT;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MagmaInteract implements Listener {

    private TTT plugin;

    public MagmaInteract(TTT plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!(event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getAction() != Action.RIGHT_CLICK_AIR)) return;
        if (!(plugin.getGameStateManager().getCurrentGameState() instanceof LobbyState)) return;
        Player player = event.getPlayer();
        ItemStack itemStack = player.getItemInHand();
        if (itemStack.getItemMeta() == null) return;

        if (itemStack.getItemMeta().getDisplayName() == null) return;
        if (itemStack.getItemMeta().getDisplayName().equals(JoinListener.MAGMA_NAME)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeUTF("Connect");
                dataOutputStream.writeUTF("Lobby-1");
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.sendPluginMessage(plugin, "BungeeCord", byteArrayOutputStream.toByteArray());
        }

    }
}
