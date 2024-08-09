package discordrpc.helpers;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * @author HypherionSA
 * Helper class to add Buttons to Discord Rich Presence
 * This can not be used with Join/Spectate
 */
public class RPCButton implements Serializable {

    // The label of the button
    private final String label;

    // The URL the button will open when clicked
    private final String url;

    protected RPCButton(String label, String url) {
        this.label = label;
        this.url = url;
    }

    /**
     * Create a new RPC Button
     * @param label The label of the button
     * @param url The URL the button will open when clicked
     * @return The constructed button
     */
    public static RPCButton create(@Nonnull String label, @Nonnull String url) {
        // Null check used here for users blatantly ignoring the NotNull marker
        if (label == null || label.isEmpty() || url == null || url.isEmpty()) {
            throw new IllegalArgumentException("RPC Buttons require both a label and url");
        }

        label = label.substring(0, Math.min(label.length(), 31));
        return new RPCButton(label, url);
    }

    /**
     * @return The label assigned to the button
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return The URL of the button
     */
    public String getUrl() {
        return url;
    }
}
