// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.utilities;

import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import me.kaktuswasser.client.Client;
import net.minecraft.client.Minecraft;

public class Logger
{
    public static String getPrefix(final String text) {
        return "§7[§6" + text + "§7]§r ";
    }
    
    public static void writeChat(final String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(String.valueOf(getPrefix(Client.getName())) + message));
    }
    
    public static void writeConsole(final String text) {
        System.out.println("["+Client.getName()+"] " + text);
    }
}
