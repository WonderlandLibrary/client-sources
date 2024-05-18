package host.kix.uzi.utilities.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

/**
 * Created by myche on 2/4/2017.
 */
public class Logger {

    public static void logToChat(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\247c[Uzi] \2477" + message));
    }
}
