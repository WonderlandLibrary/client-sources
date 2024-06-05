package club.mega.util;

import club.mega.Mega;
import club.mega.interfaces.MinecraftInterface;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.ChatComponentText;

public final class ChatUtil implements MinecraftInterface {

    public static void sendMessage(final String message) {
        MC.thePlayer.addChatMessage(new ChatComponentText(ChatFormatting.RED + Mega.INSTANCE.getName() + ChatFormatting.GRAY + ": " + ChatFormatting.WHITE + message));
    }

    public static void sendMessage(final double message) {
        sendMessage(String.valueOf(message));
    }

}
