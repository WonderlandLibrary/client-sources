// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.utilities;

import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.client.Minecraft;

public class Logger
{
    public static String getPrefix(final String text) {
        return "§7[§6" + text + "§7]§r ";
    }
    
    public static void writeChat(final String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(String.valueOf(getPrefix("Icarus")) + message));
    }
    
    public static void writeConsole(final String text) {
        System.out.println("[Icarus] " + text);
    }
}
