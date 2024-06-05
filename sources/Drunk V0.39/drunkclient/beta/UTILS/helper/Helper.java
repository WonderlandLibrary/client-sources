/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS.helper;

import drunkclient.beta.UTILS.helper.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Helper {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static void sendMessage(String message) {
        new ChatUtils.ChatMessageBuilder(true, true).appendText(message).setColor(EnumChatFormatting.GRAY).build().displayClientSided();
    }

    public static boolean onServer(String server) {
        if (mc.isSingleplayer()) return false;
        if (!Helper.mc.getCurrentServerData().serverIP.toLowerCase().contains(server)) return false;
        return true;
    }

    public static void sendMessageWithoutPrefix(String string) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(string));
    }
}

