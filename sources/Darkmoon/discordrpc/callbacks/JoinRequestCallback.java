package discordrpc.callbacks;

import com.sun.jna.Callback;
import discordrpc.DiscordUser;

/**
 * @author HypherionSA
 * Callback for when someone requests to join your game
 */
public interface JoinRequestCallback extends Callback {

    /**
     * Called when someone clicks on the Join Game button
     * @param user The Discord User trying to join your game
     * @see DiscordUser
     */
    void apply(DiscordUser user);
}
