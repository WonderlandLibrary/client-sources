// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

import intent.AquaDev.aqua.Aqua;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.client.Minecraft;

public class ChatUtil
{
    public static void sendChatMessage(final String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }
    
    public static void sendChatMessageWithPrefix(final String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§7[§3" + Aqua.name + "§7]§f " + message));
    }
    
    public static void messageWithoutPrefix(final String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }
    
    public static void sendChatInfo(final String string) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§7[§f" + Aqua.name + "§7]§a " + string));
    }
    
    public static void sendChatError(final String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§7[§f" + Aqua.name + "§7]§c " + message));
    }
    
    public static void sendBlurFixMessage() {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(""));
    }
}
