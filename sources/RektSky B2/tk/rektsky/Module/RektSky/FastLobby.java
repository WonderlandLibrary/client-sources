package tk.rektsky.Module.RektSky;

import tk.rektsky.Module.*;
import tk.rektsky.Event.*;
import net.minecraft.network.play.server.*;
import tk.rektsky.Event.Events.*;

public class FastLobby extends Module
{
    public static String[] hubCommands;
    private boolean keepGoing;
    
    public FastLobby() {
        super("FastLobby", "Use /lobby, /l, /hub once to go back to lobby", 0, Category.REKTSKY);
        this.keepGoing = false;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof PacketReceiveEvent && ((PacketReceiveEvent)event).getPacket() instanceof S02PacketChat) {
            final S02PacketChat packet = (S02PacketChat)((PacketReceiveEvent)event).getPacket();
            try {
                if (packet.isChat() && packet.getChatComponent().getUnformattedText().equalsIgnoreCase("Voc\u00ea tem certeza? Utilize /lobby novamente para voltar ao lobby.")) {
                    ((PacketReceiveEvent)event).setCanceled(true);
                    this.mc.thePlayer.sendChatMessage("/hub");
                    this.keepGoing = true;
                }
            }
            catch (Exception e) {
                return;
            }
        }
        if (event instanceof ChatEvent) {
            for (final String cmd : FastLobby.hubCommands) {
                final String msg = ((ChatEvent)event).getMessage();
                if (msg.equalsIgnoreCase(cmd)) {
                    ((ChatEvent)event).setCanceled(true);
                    this.mc.thePlayer.sendChatMessage("/hub");
                }
            }
        }
    }
    
    static {
        FastLobby.hubCommands = new String[] { "/hub", "/lobby" };
    }
}
