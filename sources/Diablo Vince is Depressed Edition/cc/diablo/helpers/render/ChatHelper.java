/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.helpers.render;

import cc.diablo.Main;
import cc.diablo.helpers.render.ChatColor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatHelper {
    public static void addChat(String text) {
        if (Minecraft.theWorld != null) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText((Object)((Object)ChatColor.WHITE) + "[" + (Object)((Object)ChatColor.DARK_PURPLE) + Main.name.charAt(0) + (Object)((Object)ChatColor.WHITE) + "]" + (Object)((Object)ChatColor.RESET) + " " + text));
        }
    }

    public static void print(String text) {
        if (Minecraft.theWorld != null) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text));
        }
    }

    public static String translateColorCodes(String toTranslate) {
        return toTranslate.replaceAll("&", "\u00a7");
    }
}

