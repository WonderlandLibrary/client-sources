package fr.dog.module.impl.player;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.network.PacketReceiveEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import net.minecraft.network.play.server.S02PacketChat;

public class AutoWho extends Module {
    private static final String join = "has joined";

    public AutoWho() {
        super("AutoWho", ModuleCategory.PLAYER);
    }

    public void onEnable() {
        mc.thePlayer.sendChatMessage("/lang english");
    }

    @SubscribeEvent
    private void onPacketReceive(PacketReceiveEvent event) {
        if (!event.isCancelled() && event.getPacket() instanceof S02PacketChat s02PacketChat) {
            String chatMessage = s02PacketChat.getChatComponent().getUnformattedText();
            String username = mc.thePlayer.getName();
            if (chatMessage.contains(username + " " + join)) {
                String command = "/who";
                mc.thePlayer.sendChatMessage(command);
            }
        }
    }
}
