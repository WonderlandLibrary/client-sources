package wtf.diablo.client.util.mc.player.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public final class ChatUtil {
    private static final String PREFIX = "§7[§cDiablo§7] §r";
    private static final String IRC_PREFIX = "§7[§IRC§7] §r";

    private ChatUtil() {}

    public static void addChatMessage(final String message) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(PREFIX + message));
    }


}