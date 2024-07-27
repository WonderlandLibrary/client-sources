package dev.nexus.utils.game;

import dev.nexus.utils.Utils;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ChatUtils implements Utils {
    public static void addMessageToChat(String msg) {
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + "[" + EnumChatFormatting.RED + "N" + EnumChatFormatting.GRAY + "]" + EnumChatFormatting.RESET + " " + msg));
    }
}
