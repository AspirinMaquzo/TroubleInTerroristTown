package me.maquzo.TTT.Manager.gamestates.LobbyState;

import me.maquzo.TTT.Manager.gamestates.GameState;
import me.maquzo.TTT.Manager.gamestates.GameStateManager;
import me.maquzo.TTT.TTT;
import me.maquzo.TTT.Tasks.LobbyCountdown;
import me.maquzo.TTT.Voting.Map;
import me.maquzo.TTT.utils.Profile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import javax.persistence.Lob;

public class LobbyState extends GameState {

    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 15;

    private LobbyCountdown lobbyCountdown;

    private TTT plugin;

    public LobbyState(GameStateManager gameStateManager, TTT plugin) {
        lobbyCountdown = new LobbyCountdown(gameStateManager);
        this.plugin = plugin;
    }


    @Override
    public void start() {
        lobbyCountdown.startIdle();
    }

    @Override
    public void stop() {
        
    }

    public LobbyCountdown getLobbyCountdown() {
        return lobbyCountdown;
    }
}
