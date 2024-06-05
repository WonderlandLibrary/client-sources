package zamorozka.ui;

import it.unimi.dsi.fastutil.chars.Char2CharArrayMap;
import it.unimi.dsi.fastutil.chars.Char2CharMap;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.text.TextComponentString;

public class ChatUtils implements MCUtil {
    public final static String chatPrefix = "\2477[\2479ZM\2477] \2478>> \247f";
    public final static String ircchatPrefix = "\2477[\2476Ex\2479IRC\2477] \247f";
    public static final StringBuilder sb = new StringBuilder();
    public static final Char2CharMap SMALL_CAPS = new Char2CharArrayMap();

    public static void printChat(String text) {
        Minecraft.player.addChatMessage(new TextComponentString(text));
    }

    public static void printChatprefix(String text) {
        Minecraft.player.addChatMessage(new TextComponentString(chatPrefix + text));
    }
    
    public static String applyFancy(String changeFrom) {
        String output = changeFrom;
        sb.setLength(0);

        for (char ch : output.toCharArray()) {
            if (SMALL_CAPS.containsKey(ch)) sb.append(SMALL_CAPS.get(ch));
            else sb.append(ch);
        }

        output = sb.toString();

        return output;
    }

    public static void printIRCChatprefix(String text) {
        Minecraft.player.addChatMessage(new TextComponentString(ircchatPrefix + text));
    }

    public static void sendChat_NoFilter(String text) {
        Minecraft.player.connection.sendPacket(new CPacketChatMessage(text));
    }

    public static void sendChat(String text) {
        Minecraft.player.sendChatMessage(text);
    }
}
