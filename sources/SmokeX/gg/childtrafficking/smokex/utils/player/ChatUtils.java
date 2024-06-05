// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.utils.player;

import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.client.Minecraft;

public final class ChatUtils
{
    public static void addChatMessage(final Object message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§c[Smoke] §r" + message));
    }
}
