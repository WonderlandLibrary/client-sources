package discordrpc.callbacks;

import com.sun.jna.Callback;
import discordrpc.DiscordUser;

/**
 * @author HypherionSA
 * Callback for when the RPC has connected successfully
 */
public interface ReadyCallback extends Callback {

    /**
     * Called when the RPC is connected and ready to be used
     * @param user The user the RPC is displayed on
     * @see DiscordUser
     */
    void apply(DiscordUser user);
}
