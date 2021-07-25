package me.maquzo.TTT.Tasks;

import me.maquzo.TTT.TTT;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class EndCountdown extends Countdown{

    private static final int END_SECONDS = 15;

    private TTT plugin;
    private int seconds;

    public EndCountdown(TTT plugin) {
        this.plugin = plugin;
        seconds = END_SECONDS;
    }

    @Override
    public void start() {
        taskID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {

                switch (seconds) {
                    case 15: case 10: case 5: case 3: case 2:
                        Bukkit.broadcastMessage(TTT.prefix + "Die Runde endet in §c§l" + seconds + " §7Sekunden.");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.LAVA_POP, 3, 3);
                        }
                        break;

                    case 1:
                        Bukkit.broadcastMessage(TTT.prefix + "Die Runde endet in §c§leiner §7Sekunden.");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.LAVA_POP, 3, 3);
                        }
                        break;

                    case 0:
                        plugin.getGameStateManager().getCurrentGameState().stop();
                        stop();
                        break;

                    default:
                        break;
                }

                seconds--;
            }
        }, 0, 20);
    }

    @Override
    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
    }
}
