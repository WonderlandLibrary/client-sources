package discordrpc;

import com.sun.jna.Structure;
import discordrpc.helpers.RPCButton;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author HypherionSA
 * Class reprenting a Discord RPC activity
 */
public class DiscordRichPresence extends Structure {

    // First line of text on the RPC
    public String state;

    // Second line of text on the RPC
    public String details;

    // Time the activity started in UNIX-Timestamp format
    public long startTimestamp;

    // Time the activity will end in UNIX-Timestamp format
    public long endTimestamp;

    // URL or Asset key of the Large Image
    public String largeImageKey;

    // Hover text to display when hovering the Large Image
    public String largeImageText;

    // URL or Asset key of the Small Image
    public String smallImageKey;

    // Hover text to display when hovering the Small Image
    public String smallImageText;

    // Id of the player's party, lobby, or group.
    public String partyId;

    // Current size of the player's party, lobby, or group.
    public int partySize;

    // Maximum size of the player's party, lobby, or group.
    public int partyMax;

    // Unused
    public String partyPrivacy;

    // Unused.
    public String matchSecret;

    // Unique hashed string for chat invitations and Ask to Join.
    public String joinSecret;

    // Unique hashed string for Spectate button.
    public String spectateSecret;

    // Label of the First RPC Button
    public String button_label_1;

    // URL of the First RPC Button
    public String button_url_1;

    // Label of the Second RPC Button
    public String button_label_2;

    // URL of the Second RPC Button
    public String button_url_2;

    // Unused
    public int instance;

    /**
     * DO NOT TOUCH THIS... EVER!
     */
    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(
                "state",
                "details",
                "startTimestamp",
                "endTimestamp",
                "largeImageKey",
                "largeImageText",
                "smallImageKey",
                "smallImageText",
                "partyId",
                "partySize",
                "partyMax",
                "partyPrivacy",
                "matchSecret",
                "joinSecret",
                "spectateSecret",
                "button_label_1",
                "button_url_1",
                "button_label_2",
                "button_url_2",
                "instance"
        );
    }

    public DiscordRichPresence() {
        setStringEncoding("UTF-8");
    }

    public static class Builder {
        private final DiscordRichPresence rpc;

        public Builder() {
            rpc = new DiscordRichPresence();
        }

        public Builder setState(String state) {
            if (state != null && !state.isEmpty()) {
                rpc.state = state.substring(0, Math.min(state.length(), 128));
            }
            return this;
        }

        public Builder setDetails(String details) {
            if (details != null && !details.isEmpty()) {
                rpc.details = details.substring(0, Math.min(details.length(), 128));
            }
            return this;
        }

        public Builder setStartTimestamp(long timestamp) {
            rpc.startTimestamp = timestamp;
            return this;
        }

        public Builder setStartTimestamp(OffsetDateTime timestamp) {
            rpc.startTimestamp = timestamp.toEpochSecond();
            return this;
        }

        public Builder setEndTimestamp(long timestamp) {
            rpc.endTimestamp = timestamp;
            return this;
        }

        public Builder setEndTimestamp(OffsetDateTime timestamp) {
            rpc.endTimestamp = timestamp.toEpochSecond();
            return this;
        }

        public Builder setLargeImage(String key) {
            return this.setLargeImage(key, "");
        }

        public Builder setLargeImage(String key, String text) {
            // Null check used for users blatantly ignoring the NotNull marker
            if ((text != null && !text.isEmpty()) && key != null) {
                throw new IllegalArgumentException("Image key cannot be null when assigning a hover text");
            }

            rpc.largeImageKey = key;
            rpc.largeImageText = text;
            return this;
        }

        public Builder setSmallImage(String key) {
            return this.setSmallImage(key, "");
        }

        public Builder setSmallImage(String key, String text) {
            rpc.smallImageKey = key;
            rpc.smallImageText = text;
            return this;
        }

        public Builder setParty(String party, int size, int max) {
            // Buttons are present, ignore
            if ((rpc.button_label_1 != null && rpc.button_label_1.isEmpty()) || (rpc.button_label_2 != null && rpc.button_label_2.isEmpty()))
                return this;

            rpc.partyId = party;
            rpc.partySize = size;
            rpc.partyMax = max;
            return this;
        }

        public Builder setSecrets(String match, String join, String spectate) {
            // Buttons are present, ignore
            if ((rpc.button_label_1 != null && rpc.button_label_1.isEmpty()) || (rpc.button_label_2 != null && rpc.button_label_2.isEmpty()))
                return this;

            rpc.matchSecret = match;
            rpc.joinSecret = join;
            rpc.spectateSecret = spectate;
            return this;
        }

        public Builder setSecrets(String join, String spectate) {
            // Buttons are present, ignore
            if ((rpc.button_label_1 != null && rpc.button_label_1.isEmpty()) || (rpc.button_label_2 != null && rpc.button_label_2.isEmpty()))
                return this;

            rpc.joinSecret = join;
            rpc.spectateSecret = spectate;
            return this;
        }

        public Builder setInstance(boolean i) {
            // Buttons are present, ignore
            if ((rpc.button_label_1 != null && rpc.button_label_1.isEmpty()) || (rpc.button_label_2 != null && rpc.button_label_2.isEmpty()))
                return this;

            rpc.instance = i ? 1 : 0;
            return this;
        }

        public Builder setButtons(RPCButton button) {
            return this.setButtons(Collections.singletonList(button));
        }

        public Builder setButtons(RPCButton button1, RPCButton button2) {
            return this.setButtons(Arrays.asList(button1, button2));
        }

        public Builder setButtons(List<RPCButton> rpcButtons) {
            // Limit to 2 Buttons. Discord Limitation
            if (rpcButtons != null && !rpcButtons.isEmpty()) {
                int length = Math.min(rpcButtons.size(), 2);
                rpc.button_label_1 = rpcButtons.get(0).getLabel();
                rpc.button_url_1 = rpcButtons.get(0).getUrl();

                if (length == 2) {
                    rpc.button_label_2 = rpcButtons.get(1).getLabel();
                    rpc.button_url_2 = rpcButtons.get(1).getUrl();
                }
            }

            return this;
        }

        public DiscordRichPresence build() {
            return rpc;
        }
    }
}
