package club.strifeclient.util.player;

import club.strifeclient.util.misc.MinecraftUtil;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public final class ChatUtil extends MinecraftUtil {

    public static final String CHAT_PREFIX = "&8[&cStrife&8]&7 ";
    public static final String COMMAND_PREFIX = ".";

    public static void sendMessage(Object message) {
        if (mc.thePlayer != null)
            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.translateAlternateColorCodes('&', String.valueOf(message))));
        else System.out.println(EnumChatFormatting.translateAlternateColorCodes('&', String.valueOf(message)));
    }

    public static void sendMessageWithPrefix(Object message) {
        sendMessage(CHAT_PREFIX + message);
    }

}
