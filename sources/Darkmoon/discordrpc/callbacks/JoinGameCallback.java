package discordrpc.callbacks;

import com.sun.jna.Callback;

/**
 * @author HypherionSA
 * Callback for when someone was approved to join your game
 */
public interface JoinGameCallback extends Callback {

    /**
     * Called when someone joins a game from {@link JoinRequestCallback}
     * @param joinSecret Secret or Password required to let the player join the game
     */
    void apply(String joinSecret);
}
