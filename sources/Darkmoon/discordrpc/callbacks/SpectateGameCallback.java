package discordrpc.callbacks;

import com.sun.jna.Callback;

/**
 * @author HypherionSA
 * Callback for when someone is requesting to spectate your game
 */
public interface SpectateGameCallback extends Callback {

    /**
     * Called when joining the game
     * @param spectateSecret Secret or Password required to let the player spectate
     */
    void apply(String spectateSecret);
}
