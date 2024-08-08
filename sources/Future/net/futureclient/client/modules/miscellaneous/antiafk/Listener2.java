package net.futureclient.client.modules.miscellaneous.antiafk;

import org.lwjgl.opengl.Display;
import net.minecraft.network.play.server.SPacketChat;
import net.futureclient.client.events.EventPacket;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.AntiAFK;
import net.futureclient.client.we;
import net.futureclient.client.n;

public class Listener2 extends n<we>
{
    public final AntiAFK k;
    
    public Listener2(final AntiAFK k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
    
    public void M(final EventPacket eventPacket) {
        final String formattedText;
        if (eventPacket.M() instanceof SPacketChat && !Display.isActive() && AntiAFK.M(this.k).M() && (formattedText = ((SPacketChat)eventPacket.M()).getChatComponent().getFormattedText()).contains("§d") && formattedText.contains(" whispers: ") && AntiAFK.b(this.k).M(10000L)) {
            AntiAFK.getMinecraft7().player.sendChatMessage("/r [Future] I am currently AFK.");
        }
    }
}
