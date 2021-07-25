package me.maquzo.TTT.Tasks;

import me.maquzo.TTT.Manager.gamestates.IngameState.IngameState;
import me.maquzo.TTT.utils.ActionBar;
import me.maquzo.TTT.utils.Profile;
import org.bukkit.Bukkit;
import me.maquzo.TTT.*;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import me.maquzo.TTT.Manager.Roles.*;

import java.util.ArrayList;

public class RoleCountdown extends Countdown {

    private int seconds = 30;
    private TTT plugin;


    public RoleCountdown(TTT plugin) {
        this.plugin = plugin;
    }

    @Override
    public void start() {
        taskID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                switch (seconds) {
                    case 30:
                    case 20:
                    case 10:
                    case 3:
                    case 2:
                        for (Player all : plugin.getPlayers()) {
                            all.sendMessage(TTT.prefix + "Die Rollen werden in §c§l" + seconds + " §7Sekunden bekannt gegeben");
                            all.playSound(all.getLocation(), Sound.LAVA_POP, 4, 4);
                        }
                        break;
                    case 1:
                        for (Player all : plugin.getPlayers()) {
                            all.sendMessage(TTT.prefix + "Die Rollen werden in §c§leiner §7Sekunden bekannt gegeben");
                            all.playSound(all.getLocation(), Sound.LAVA_POP, 4, 4);
                        }
                        break;

                    case 0:
                        stop();
                        IngameState ingameState = (IngameState) plugin.getGameStateManager().getCurrentGameState();
                        ingameState.setGrace(false);
                        plugin.getRoleManager().roleManager();
                        ArrayList<String> traitorList = plugin.getRoleManager().getTraitorList();
                        for (Player all : plugin.getPlayers()) {
                            Role playerRole = plugin.getRoleManager().getRole(all);
                            Profile profile = plugin.getProfileManager().getProfile(all);
                            Role finalRole = playerRole;
                            if (profile.getData().getStats().isDetectivePassActive()) {
                                finalRole = Role.DETECTIVE;
                            }
                            if (profile.getData().getStats().isTraitorPassActive()) {
                                finalRole = Role.TRAITOR;
                            }
                            if (!profile.getData().getStats().isTraitorPassActive() || (!profile.getData().getStats().isDetectivePassActive())) {
                                finalRole = Role.INNOCENT;
                            }
                            ActionBar.sendActionBarTime(all, "§8• §cRolle §8• " + playerRole.getRoleColor() + playerRole.getName(), Integer.MAX_VALUE);
                            all.playSound(all.getLocation(), Sound.CLICK, 4, 4);
                            all.sendMessage(" ");
                            all.sendMessage(TTT.prefix + "Rolle: §l" + playerRole.getRoleColor() + playerRole.getName());
                            all.sendMessage(" ");
                            all.setDisplayName(playerRole.getRoleColor() + playerRole.getName());

                            if (playerRole == Role.TRAITOR)
                                all.sendMessage(TTT.prefix + "Traitor Partner: §c§l" + String.join(", ", traitorList));
                        }
                        break;

                    default:
                        break;
                }

                seconds--;

                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.setLevel(seconds);
                }

            }
        }, 0, 20);
    }

    @Override
    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

    public int getSeconds() {
        return seconds;
    }
}
