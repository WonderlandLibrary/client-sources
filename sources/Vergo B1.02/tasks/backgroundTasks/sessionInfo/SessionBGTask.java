package xyz.vergoclient.tasks.backgroundTasks.sessionInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S02PacketChat;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventReceivePacket;
import xyz.vergoclient.modules.OnEventInterface;

public class SessionBGTask implements OnEventInterface {

    protected static Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void onEvent(Event e) {
        if(e instanceof EventReceivePacket) {
            EventReceivePacket event = (EventReceivePacket) e;

            if(event.packet instanceof S02PacketChat) {
                S02PacketChat packet = (S02PacketChat) event.packet;

                if(packet.getChatComponent().getUnformattedText().contains("was killed by " + mc.thePlayer.getName()) || packet.getChatComponent().getUnformattedText().contains("by " + mc.thePlayer.getName() + ".")) {
                    SessionInfo.killCount += 1;
                }

                if(packet.getChatComponent().getUnformattedText().contains("You died!") || packet.getChatComponent().getUnformattedText().contains(mc.thePlayer.getName() + " was killed by")) {
                    SessionInfo.deathCount += 1;
                }

                if(packet.getChatComponent().getUnformattedText().contains("You won! Want to play again? Click here!")) {
                    SessionInfo.winCount += 1;
                }

                if(packet.getChatComponent().getUnformattedText().contains("You died! Want to play again?") || packet.getChatComponent().getUnformattedText().contains("You won! Want to play again?") ||
                        packet.getChatComponent().getUnformattedText().contains("Queued! Use the bed to return to lobby!") || packet.getChatComponent().getUnformattedText().contains("Queued! Use the bed to cancel!")) {
                    SessionInfo.gamesPlayed += 1;
                }
            }

        }
    }
}
