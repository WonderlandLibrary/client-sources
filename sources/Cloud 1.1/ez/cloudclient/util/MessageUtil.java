package ez.cloudclient.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class MessageUtil {

    /*
     * Added by RemainingToast 12/07/20
     */

    public static final String CHAT_PREFIX = TextFormatting.DARK_GRAY + "[" + TextFormatting.LIGHT_PURPLE + TextFormatting.BOLD + "Cloud" + TextFormatting.DARK_GRAY + "] ";
    private static final EntityPlayerSP player = Minecraft.getMinecraft().player;

    public static void sendRawMessage(String message) {
        player.sendMessage(new TextComponentString(message));
    }

    public static void sendPublicMessage(String message) {
        player.sendChatMessage(message);
    }

    public static void sendMessage(String message, Color color) {
        switch (color) {
            case DARK_RED:
                sendRawMessage(CHAT_PREFIX + TextFormatting.DARK_RED + message);
                break;
            case RED:
                sendRawMessage(CHAT_PREFIX + TextFormatting.RED + message);
                break;
            case GOLD:
                sendRawMessage(CHAT_PREFIX + TextFormatting.GOLD + message);
                break;
            case YELLOW:
                sendRawMessage(CHAT_PREFIX + TextFormatting.YELLOW + message);
                break;
            case DARK_GREEN:
                sendRawMessage(CHAT_PREFIX + TextFormatting.DARK_GREEN + message);
                break;
            case GREEN:
                sendRawMessage(CHAT_PREFIX + TextFormatting.GREEN + message);
                break;
            case AQUA:
                sendRawMessage(CHAT_PREFIX + TextFormatting.AQUA + message);
                break;
            case DARK_AQUA:
                sendRawMessage(CHAT_PREFIX + TextFormatting.DARK_AQUA + message);
                break;
            case DARK_BLUE:
                sendRawMessage(CHAT_PREFIX + TextFormatting.DARK_BLUE + message);
                break;
            case BLUE:
                sendRawMessage(CHAT_PREFIX + TextFormatting.BLUE + message);
                break;
            case LIGHT_PURPLE:
                sendRawMessage(CHAT_PREFIX + TextFormatting.LIGHT_PURPLE + message);
                break;
            case DARK_PURPLE:
                sendRawMessage(CHAT_PREFIX + TextFormatting.DARK_PURPLE + message);
                break;
            case WHITE:
                sendRawMessage(CHAT_PREFIX + TextFormatting.WHITE + message);
                break;
            case GRAY:
                sendRawMessage(CHAT_PREFIX + TextFormatting.GRAY + message);
                break;
            case DARK_GRAY:
                sendRawMessage(CHAT_PREFIX + TextFormatting.DARK_GRAY + message);
                break;
            case BLACK:
                sendRawMessage(CHAT_PREFIX + TextFormatting.BLACK + message);
                break;
        }

    }

    //A - Z Please
    public enum Color {
        DARK_RED,
        RED,
        GOLD,
        YELLOW,
        DARK_GREEN,
        GREEN,
        AQUA,
        DARK_AQUA,
        DARK_BLUE,
        BLUE,
        LIGHT_PURPLE,
        DARK_PURPLE,
        WHITE,
        GRAY,
        DARK_GRAY,
        BLACK
    }
}
