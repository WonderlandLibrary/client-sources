package discordrpc.callbacks;

import com.sun.jna.Callback;

/**
 * @author HypherionSA
 * Callback for when the RPC ran into an error
 */
public interface ErroredCallback extends Callback {

    /**
     * Called when an RPC error occurs
     * @param errorCode Error code if any
     * @param message Details about the error
     */
    void apply(int errorCode, String message);
}
