package me.maquzo.TTT.Manager.gamestates.EndingState;

import me.maquzo.TTT.Manager.gamestates.GameState;
import me.maquzo.TTT.TTT;
import me.maquzo.TTT.Tasks.EndCountdown;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EndingState extends GameState {

    private EndCountdown endCountdown;

    private TTT plugin;

    public EndingState(TTT plugin) {
        this.plugin = plugin;
        endCountdown = new EndCountdown(plugin);
    }

    @Override
    public void start() {
        endCountdown.start();
    }

    @Override
    public void stop() {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "stop");
    }
}
