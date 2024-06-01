package best.actinium.util.render;

import best.actinium.util.IAccess;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Formatting;

public class ChatUtil implements IAccess {
    public static void display(final Object message) {
        if (mc.thePlayer != null) {
            mc.thePlayer.addChatMessage(new ChatComponentText(getPrefix() + message));
        }
    }

    public static void sendIRC(final Object message) {
        if (mc.thePlayer != null) {
            mc.thePlayer.addChatMessage(new ChatComponentText(Formatting.AQUA + "[IRC] " + Formatting.WHITE + message));
        }
    }

    private static String getPrefix() {
        return Formatting.LIGHT_PURPLE +  "[A] " + Formatting.WHITE;
    }
}
