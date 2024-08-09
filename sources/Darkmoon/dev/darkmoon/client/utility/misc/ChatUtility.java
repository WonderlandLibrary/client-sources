package dev.darkmoon.client.utility.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.utility.Utility;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
public class ChatUtility implements Utility {

    public static String chatPrefix = "\2477[" + TextFormatting.WHITE + "DarkMoon" + "\2477] " + ChatFormatting.RESET;

    public static void addChatMessage(String message) {
        mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_GRAY + "\2477(" + ChatFormatting.RESET + "DarkMoon" + ChatFormatting.RESET + ChatFormatting.DARK_GRAY + "\2477) " + ChatFormatting.RESET + message));
    }

    public static void addChatMessage(String message, boolean prefix) {
        mc.player.sendMessage(!prefix ? new TextComponentString(message) : new TextComponentString("\2477*" + ChatFormatting.RESET + "DarkMoon" + ChatFormatting.RESET + "\2477) " + ChatFormatting.RESET + message));
    }
}