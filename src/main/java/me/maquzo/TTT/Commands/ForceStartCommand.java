package me.maquzo.TTT.Commands;

import me.maquzo.TTT.Manager.gamestates.LobbyState.LobbyState;
import me.maquzo.TTT.TTT;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceStartCommand implements CommandExecutor {

    private static final int SECONDS_TILL_START = 10;

    private TTT plugin;

    public ForceStartCommand(TTT plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp() || player.hasPermission("TTT.Forcestart")) {
                if (args.length == 0) {
                    if (plugin.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                        LobbyState lobbyState = (LobbyState) plugin.getGameStateManager().getCurrentGameState();
                        if (lobbyState.getLobbyCountdown().isRunning() && lobbyState.getLobbyCountdown().getSeconds() > SECONDS_TILL_START) {
                            lobbyState.getLobbyCountdown().setSeconds(SECONDS_TILL_START);
                            player.sendMessage(TTT.prefix + "Du hast das Spiel §c§lForce §7gestartet!");
                            Bukkit.broadcastMessage(TTT.prefix + "Das spiel wurde §c§lgestartet!");
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLAZE_DEATH, 3, 3);
                            }
                        } else
                            player.sendMessage(TTT.error + "Das §c§lSpiel startet bereits.");
                    } else
                        player.sendMessage(TTT.error + "Das §c§lSpiel startet bereits.");
                }else
                    player.sendMessage(TTT.error + "Bitte benutze §c/forcestart");
            } else
                player.sendMessage(TTT.noperms);
        }


        return false;
    }

}
