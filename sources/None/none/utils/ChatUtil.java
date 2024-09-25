package none.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentText;

public class ChatUtil {
	private static Minecraft mc = Minecraft.getMinecraft();
	
    public static void printChat(String text) {
        mc.thePlayer.addChatMessage(new ChatComponentText(text));
    }

    public static void sendChat_NoFilter(String text) {
        mc.thePlayer.connection.sendPacket(new C01PacketChatMessage(text));
    }

    public static void sendChat(String text) {
        mc.thePlayer.sendChatMessage(text);
    }
    
    public static void sendChatBypassCommand(String text) {
        mc.thePlayer.sendChatMessageNoEvent(text);
    }
}
