package me.maquzo.TTT.Manager.gamestates;

import me.maquzo.TTT.Manager.gamestates.EndingState.EndingState;
import me.maquzo.TTT.Manager.gamestates.IngameState.IngameState;
import me.maquzo.TTT.Manager.gamestates.LobbyState.LobbyState;
import me.maquzo.TTT.TTT;

public class GameStateManager {

    private TTT plugin;
    private GameState[] gameStates;
    private GameState currentGameState;

    public GameStateManager(TTT plugin) {
        this.plugin = plugin;
        gameStates = new GameState[3];

        gameStates[GameState.LOBBY_STATE] = new LobbyState(this, plugin);
        gameStates[GameState.INGAME_STATE] = new IngameState(plugin);
        gameStates[GameState.ENDING_STATE] = new EndingState(plugin);
    }

    public void setGameState(int gameStateID) {
        if (currentGameState != null)
            currentGameState.stop();
        currentGameState = gameStates[gameStateID];
        currentGameState.start();
    }

    public void stopCurrentGameState() {
        if (currentGameState != null) {
            currentGameState.stop();
            currentGameState = null;
        }
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public TTT getPlugin() {
        return plugin;
    }

}
