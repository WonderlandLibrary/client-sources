package net.shoreline.client.util.chat;

import net.shoreline.client.util.Globals;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class ChatUtil implements Globals
{
    //
    private static final String PREFIX = "§7[§fCaspian§7] §f";

    /**
     * Sends a message in the {@link net.minecraft.client.gui.hud.ChatHud}
     * which is not visible to others
     *
     * @param message The message
     */
    public static void clientSendMessage(String message)
    {
        mc.inGameHud.getChatHud().addMessage(Text.of(PREFIX + message), null, null);
    }

    /**
     *
     * @param message
     * @param params
     */
    public static void clientSendMessage(String message, Object... params)
    {
        clientSendMessage(String.format(message, params));
    }

    /**
     *
     * @param message
     */
    public static void clientSendMessageRaw(String message)
    {
        mc.inGameHud.getChatHud().addMessage(Text.of(message), null, null);
    }

    /**
     *
     * @param message
     * @param params
     */
    public static void clientSendMessageRaw(String message, Object... params)
    {
        clientSendMessageRaw(String.format(message, params));
    }

    /**
     * Sends a message in the {@link net.minecraft.client.network.ClientPlayNetworkHandler}
     * which is visible to others on a server
     *
     * @param message The message
     */
    public static void serverSendMessage(String message)
    {
        if (mc.player != null)
        {
            mc.player.networkHandler.sendChatMessage(PREFIX + message);
        }
    }

    /**
     *
     *
     * @param player
     * @param message
     */
    public static void serverSendMessage(PlayerEntity player, String message)
    {
        if (mc.player != null)
        {
            String reply = "/w " + player.getEntityName() + " ";
            mc.player.networkHandler.sendChatMessage(reply + PREFIX + message);
        }
    }

    /**
     *
     *
     * @param player
     * @param message
     * @param params
     */
    public static void serverSendMessage(PlayerEntity player, String message,
                                         Object... params)
    {
        serverSendMessage(player, String.format(message, params));
    }

    /**
     *
     * @param message
     */
    public static void error(String message)
    {
        clientSendMessage(Formatting.RED + message);
    }

    /**
     *
     * @param message
     * @param params
     */
    public static void error(String message, Object... params)
    {
        clientSendMessage(Formatting.RED + message, params);
    }
}
