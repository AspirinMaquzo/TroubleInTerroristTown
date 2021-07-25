package me.maquzo.TTT.Listener;

import me.maquzo.TTT.Manager.Roles.Role;
import me.maquzo.TTT.Manager.gamestates.IngameState.IngameState;
import me.maquzo.TTT.TTT;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private TTT plugin;

    public ChatListener(TTT plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDefaultChat(AsyncPlayerChatEvent event) {
        if(plugin.getGameStateManager().getCurrentGameState() instanceof IngameState) return;
        event.setFormat(ChatFormat(ChatColor.WHITE, event.getPlayer()) + event.getMessage());

    }

    @EventHandler
    public void onIngameState(AsyncPlayerChatEvent event) {
        if (!(plugin.getGameStateManager().getCurrentGameState() instanceof IngameState)) return;

        IngameState ingameState = (IngameState) plugin.getGameStateManager().getCurrentGameState();
        Player player = event.getPlayer();

        if (ingameState.isInGrace()) {
            event.setFormat(ChatFormat(ChatColor.WHITE, event.getPlayer()) + " " + event.getMessage());
            return;
        }

        if (ingameState.getSpecPlayers().contains(player)) {
            event.setCancelled(true);
            for (Player all : ingameState.getSpecPlayers())
                all.sendMessage(ChatFormat(ChatColor.DARK_GRAY, player) + event.getMessage());
            return;
        }

        Role playerRole = plugin.getRoleManager().getRole(player);
        if ((playerRole == Role.DETECTIVE) || (playerRole == Role.INNOCENT)) {
            event.setFormat(ChatFormat(playerRole.getRoleColor(), player) + event.getMessage());
            return;
        }

        if (playerRole == Role.TRAITOR) {
            event.setCancelled(true);
            for (Player all : Bukkit.getOnlinePlayers()) {
                Role allRole = plugin.getRoleManager().getRole(all);
                if (allRole == Role.TRAITOR)
                    all.sendMessage(ChatFormat(Role.TRAITOR.getRoleColor(), player) + event.getMessage());
                else
                    all.sendMessage(ChatFormat(Role.INNOCENT.getRoleColor(), player) + event.getMessage());
            }
        }

    }

    private String ChatFormat(ChatColor playerColor, Player player) {
        return "§7" + playerColor + player.getName() + " §7>>";
    }
}
