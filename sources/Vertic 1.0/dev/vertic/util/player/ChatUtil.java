package dev.vertic.util.player;

import dev.vertic.Client;
import dev.vertic.Utils;
import net.minecraft.util.ChatComponentText;
import static net.minecraft.util.EnumChatFormatting.*;

public interface ChatUtil extends Utils {

    String clientName = Client.name;

    static void addChatMessage(Object message) {
        if (mc.thePlayer != null) {
            mc.thePlayer.addChatMessage(new ChatComponentText(AQUA + Client.name + GOLD + " » " + WHITE + message));
        }
    }

}
