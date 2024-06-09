package io.github.liticane.clients.util.misc;

import net.minecraft.util.Formatting;
import io.github.liticane.clients.util.interfaces.IMethods;
import net.minecraft.util.ChatComponentText;

public class ChatUtil implements IMethods {
    public static void display(final Object message) {
        if (mc.player != null) {
            mc.player.addChatMessage(new ChatComponentText(getPrefix() + message));
        }
    }

    private static String getPrefix() {
        final String color = Formatting.RED.toString();
        return Formatting.BOLD + color + "[Suku"
                + Formatting.RESET + color + "] "
                + Formatting.RESET;
    }
}
