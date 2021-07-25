package me.maquzo.TTT.Listener.Lobby;

import me.maquzo.TTT.Manager.gamestates.LobbyState.LobbyState;
import me.maquzo.TTT.TTT;
import me.maquzo.TTT.Tasks.LobbyCountdown;
import me.maquzo.TTT.Voting.MapVoting;
import me.maquzo.TTT.utils.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private TTT plugin;

    public QuitListener(TTT plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (!(plugin.getGameStateManager().getCurrentGameState() instanceof LobbyState)) return;
        Player player = event.getPlayer();
        Profile profile = plugin.getProfileManager().getProfile(player);
        profile.getData().getStats().setTraitorPassActive(false);
        profile.getData().getStats().setDetectivePassActive(false);
        plugin.getPlayers().remove(player);
        event.setQuitMessage(TTT.prefix + "Der Spieler §c§l" + player.getName() + " §7hat das Spiel §cverlassen §8§l[§c§l" + plugin.getPlayers().size() + "§8§l/" + "§c§l" + LobbyState.MAX_PLAYERS + "§8§l]");


        LobbyState lobbyState = (LobbyState) plugin.getGameStateManager().getCurrentGameState();
        LobbyCountdown lobbyCountdown = lobbyState.getLobbyCountdown();
        if (plugin.getPlayers().size() < LobbyState.MIN_PLAYERS) {
            if (lobbyCountdown.isRunning()) {
                lobbyCountdown.stop();
                lobbyCountdown.startIdle();
            }
        }

        MapVoting votingMap = plugin.getVoting();
        if (votingMap == null)  {
            player.sendMessage(TTT.error + "Wie es aussieht sind nicht genug §c§lMaps §7vorhanden!");
            return;
        }
        if (votingMap.getPlayerVotes().containsKey(player.getName())) {
            votingMap.getVotingMaps()[votingMap.getPlayerVotes().get(player.getName())].removeMapVote();
            votingMap.VotingGUI();
        }

    }

}
