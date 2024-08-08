package lol.point.returnclient.util.minecraft;

import lol.point.Return;
import lol.point.returnclient.util.MinecraftInstance;
import net.minecraft.util.ChatComponentText;

public final class ChatUtil implements MinecraftInstance {

    public static void addChatMessage(String msg) {
        mc.thePlayer.addChatMessage(new ChatComponentText(Return.INSTANCE.clientPrefix + " " + msg));
    }

    public static void addChatMessage(String msg, boolean prefix) {
        if (prefix) {
            mc.thePlayer.addChatMessage(new ChatComponentText(Return.INSTANCE.clientPrefix + " " + msg));
        } else {
            mc.thePlayer.addChatMessage(new ChatComponentText(msg));
        }
    }

}
