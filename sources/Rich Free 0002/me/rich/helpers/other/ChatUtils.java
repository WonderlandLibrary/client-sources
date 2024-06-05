package me.rich.helpers.other;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class ChatUtils {
    public static void addChatMessage(String message) {
        Minecraft.getMinecraft();
        Minecraft.player.addChatMessage(new TextComponentString(ChatFormatting.RED + "[RC] " + ChatFormatting.RESET + message));
    }
}