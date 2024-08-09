package discordrpc;

import com.sun.jna.Structure;
import discordrpc.callbacks.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author HypherionSA
 * Class containing references to all available discord event handles.
 * Registering a handler is optional, and non-assigned handlers will be ignored
 */
public class DiscordEventHandlers extends Structure {

    // Callback for when the RPC was initialized successfully
    public ReadyCallback ready;

    // Callback for when the Discord connection was ended
    public DisconnectedCallback disconnected;

    // Callback for when a Discord Error occurs
    public ErroredCallback errored;

    // Callback for when a player joins the game
    public JoinGameCallback joinGame;

    // Callback for when a player spectates the game
    public SpectateGameCallback spectateGame;

    // Callback for when a players request to join your game
    public JoinRequestCallback joinRequest;

    /**
     * DO NOT TOUCH THIS... EVER!
     */
    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(
                "ready",
                "disconnected",
                "errored",
                "joinGame",
                "spectateGame",
                "joinRequest"
        );
    }

    public static class Builder {
        private final DiscordEventHandlers handlers;

        public Builder() {
            this.handlers = new DiscordEventHandlers();
        }

        public Builder ready(ReadyCallback readyCallback) {
            handlers.ready = readyCallback;
            return this;
        }

        public Builder disconnected(DisconnectedCallback disconnectedCallback) {
            handlers.disconnected = disconnectedCallback;
            return this;
        }

        public Builder errored(ErroredCallback erroredCallback) {
            handlers.errored = erroredCallback;
            return this;
        }

        public Builder joinGame(JoinGameCallback joinGameCallback) {
            handlers.joinGame = joinGameCallback;
            return this;
        }

        public Builder spectateGame(SpectateGameCallback spectateGameCallback) {
            handlers.spectateGame = spectateGameCallback;
            return this;
        }

        public Builder joinRequest(JoinRequestCallback joinRequestCallback) {
            handlers.joinRequest = joinRequestCallback;
            return this;
        }

        public DiscordEventHandlers build() {
            return handlers;
        }
    }
}
