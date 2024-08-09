package discordrpc;

import com.sun.jna.Library;
import com.sun.jna.Native;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author HypherionSA
 * Java Wrapper of the Discord-RPC Library
 */
public interface DiscordRPC extends Library {

    DiscordRPC INSTANCE = Native.loadLibrary("discord-rpc", DiscordRPC.class);

    /**
     * Open a New RPC Connection
     * @param applicationId The ID of the Application the RPC is tied to
     * @param handlers Optional Event Callback Handlers
     * @param autoRegister Auto Register the running game
     * @param steamId Steam ID of the game
     */
    void Discord_Initialize(@Nonnull String applicationId, @Nullable DiscordEventHandlers handlers, boolean autoRegister, @Nullable String steamId);

    /**
     * Shutdown the RPC instance and disconnect from discord
     */
    void Discord_Shutdown();

    /**
     * Need to be called manually at least every 2 seconds, to allow RPC updates
     * and callback handlers to fire
     */
    void Discord_RunCallbacks();

    /**
     * Not sure about this. Believe it needs to be called manually in some circumstances
     */
    void Discord_UpdateConnection();

    /**
     * Update the Rich Presence
     * @param struct Constructed {@link DiscordRichPresence}
     */
    void Discord_UpdatePresence(@Nullable DiscordRichPresence struct);

    /**
     * Clear the current Rich Presence
     */
    void Discord_ClearPresence();

    /**
     * Respond to Join/Spectate callback
     * @param userid The Discord User ID of the user that initiated the request
     * @param reply Reply to the request. See {@link DiscordReply}
     */
    void Discord_Respond(@Nonnull String userid, int reply);

    /**
     * Replace the already registered {@link DiscordEventHandlers}
     * @param handlers The new handlers to apply
     */
    void Discord_UpdateHandlers(@Nullable DiscordEventHandlers handlers);

    /**
     * Register the executable of the application/game
     * Only applicable when autoRegister is set to false
     * @param applicationId The Application ID
     * @param command The Launch command of the game
     *
     * NB: THIS DOES NOT WORK WITH MINECRAFT
     */
    void Discord_Register(String applicationId, String command);

    /**
     * Register the Steam executable of the application/game
     * @param applicationId The Application ID
     * @param steamId The Steam ID of the application/game
     */
    void Discord_RegisterSteamGame(String applicationId, String steamId);

    public enum DiscordReply {
        NO(0),
        YES(1),
        IGNORE(2);

        public final int reply;

        DiscordReply(int reply) {
            this.reply = reply;
        }
    }
}
