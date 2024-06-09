// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.other;

import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import xyz.niggfaclient.Client;
import net.minecraft.util.EnumChatFormatting;
import xyz.niggfaclient.utils.Utils;

public class Printer extends Utils
{
    public static void addMessage(final String message) {
        Printer.mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + Client.getInstance().clientName + EnumChatFormatting.WHITE + " » " + EnumChatFormatting.GRAY + message));
    }
}
