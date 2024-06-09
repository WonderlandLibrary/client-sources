package chaos.utils;

import java.io.PrintStream;

import chaos.chaos;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Logger {
    private Minecraft mc;

    public void Loading(String text) {
        System.out.println("Loading >" + text);
    }

    public void Info(String text) {
        System.out.print("Info >" + text);
    }

    public void Error(String text) {
        System.out.println("Error >" + text);
    }

    public void Downloading(String text) {
        System.out.println("Downloading >" + text);
    }

    public void Creating(String text) {
        System.out.println("Creating >" + text);
    }
    
    public void sendChatWithPrefix(String message) {
    	mc.thePlayer.addChatMessage(new ChatComponentText(chaos.instance.Client_Prefix + message));
    }
    
    public void sendChatError(String message) {
    	mc.thePlayer.addChatMessage(new ChatComponentText(chaos.instance.Client_Prefix + "§cError: " + message));
    }
    
    public void sendChatInfo(String message) {
    	mc.thePlayer.addChatMessage(new ChatComponentText(chaos.instance.Client_Prefix + "§cInfo: " + message));
    }
}