/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.utils;

import java.io.PrintStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import us.amerikan.amerikan;

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
        Minecraft.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(amerikan.instance.Client_Prefix) + message));
    }

    public void sendChatError(String message) {
        Minecraft.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(amerikan.instance.Client_Prefix) + "\u00ef\u00bf\u00bdcError: " + message));
    }

    public void sendChatInfo(String message) {
        Minecraft.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(amerikan.instance.Client_Prefix) + "\u00ef\u00bf\u00bdcInfo: " + message));
    }
}

