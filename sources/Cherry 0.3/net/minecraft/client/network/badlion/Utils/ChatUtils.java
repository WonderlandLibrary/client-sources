// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Utils;

import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.client.Minecraft;

public class ChatUtils
{
    public static void sendMessageToPlayer(final String s) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§7DaddyCherry§8]§7>§r " + s));
    }
    
    public static void sendMessageFromPlayer(final String p_71165_1_) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage(p_71165_1_);
    }
}
