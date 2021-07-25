package me.maquzo.TTT.Tasks;

import me.maquzo.TTT.Manager.gamestates.GameState;
import me.maquzo.TTT.Manager.gamestates.GameStateManager;
import me.maquzo.TTT.Manager.gamestates.LobbyState.LobbyState;
import me.maquzo.TTT.TTT;
import me.maquzo.TTT.Voting.Map;
import me.maquzo.TTT.Voting.MapVoting;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class LobbyCountdown extends Countdown{

    private static final int TIME_TILL_IDLE = 15;
    public static final int COUNTDOWN = 60;

    private GameStateManager gameStateManager;

    private int seconds;
    private int idleID;

    private boolean isRunning;
    private boolean isIdle;

    public LobbyCountdown(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        seconds = COUNTDOWN;
    }

    @Override
    public void start() {
        isRunning = true;
        taskID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(gameStateManager.getPlugin(), new Runnable() {
            @Override
            public void run() {
                switch (seconds) {
                    case 60: case 30: case 20: case 10: case 5: case 3: case 2:
                        Bukkit.broadcastMessage(TTT.prefix + "Das spiel startet in §c§l" + seconds + " §7Sekunden.");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.LAVA_POP, 3, 3);
                        }

                        if (seconds == 5) {
                            MapVoting votingMap = gameStateManager.getPlugin().getVoting();
                            Map winMap;
                            if (votingMap != null)
                                winMap = votingMap.getWinnerMap();
                            else {
                                ArrayList<Map> maps = gameStateManager.getPlugin().getMaps();
                                Collections.shuffle(maps);
                                winMap = maps.get(0);
                            }
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.sendMessage(" ");
                                all.sendMessage(TTT.prefix + "Es wird gespielt: §c§l" + winMap.getMapname());
                                all.sendMessage(TTT.prefix + "Gebaut von: §c§l" + winMap.getBuilder());
                                all.sendMessage(TTT.prefix + "Votes: §c§l" + winMap.getVotes());
                                all.sendMessage(" ");
                            }
                        }


                        break;
                    case 1:
                        Bukkit.broadcastMessage(TTT.prefix + "Das spiel startet in §c§leiner §7Sekunde");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.LAVA_POP, 3, 3);
                        }
                        break;
                    case 0:
                        gameStateManager.setGameState(GameState.INGAME_STATE);
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 3, 3);
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
        if (isRunning) {
            Bukkit.getScheduler().cancelTask(taskID);
            isRunning = false;
            seconds = COUNTDOWN;
        }
    }

    public void startIdle() {
        isIdle = true;
        idleID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(gameStateManager.getPlugin(), new Runnable() {

            @Override
            public void run() {
                Bukkit.broadcastMessage(TTT.prefix + "Es fehlen noch §c§l" + (LobbyState.MIN_PLAYERS - gameStateManager.getPlugin().getPlayers().size()) + " §7Spieler.");
            }
        }, 0, 20 * TIME_TILL_IDLE);
    }

    public void stopIdle() {
        if (isIdle) {
            Bukkit.getScheduler().cancelTask(idleID);
            isIdle = false;
        }
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public boolean isRunning() {
        return isRunning;
    }

}
