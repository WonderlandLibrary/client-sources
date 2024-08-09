package discordrpc.callbacks;

import com.sun.jna.Callback;

/**
 * @author HypherionSA
 * Callback for when the Discord RPC disconnects
 */
public interface DisconnectedCallback extends Callback {

    /**
     * Called when RPC disconnected
     * @param errorCode Error code if any
     * @param message Details about the disconnection
     */
    void apply(int errorCode, String message);
}
