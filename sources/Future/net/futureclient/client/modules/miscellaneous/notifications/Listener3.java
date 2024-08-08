package net.futureclient.client.modules.miscellaneous.notifications;

import net.futureclient.client.events.Event;
import net.futureclient.client.FC;
import java.awt.TrayIcon;
import net.futureclient.client.pg;
import org.lwjgl.opengl.Display;
import net.minecraft.network.play.server.SPacketChat;
import net.futureclient.client.events.EventPacket;
import net.futureclient.client.modules.miscellaneous.Notifications;
import net.futureclient.client.we;
import net.futureclient.client.n;

public class Listener3 extends n<we>
{
    public final Notifications k;
    
    public Listener3(final Notifications k) {
        this.k = k;
        super();
    }
    
    public void M(final EventPacket eventPacket) {
        if (eventPacket.M() instanceof SPacketChat && !Display.isActive()) {
            final String formattedText = ((SPacketChat)eventPacket.M()).getChatComponent().getFormattedText();
            Listener3 listener3 = null;
            Label_0171: {
                if (this.k.name.M() && !formattedText.contains("§d") && formattedText.contains(String.format(" %s ", Notifications.getMinecraft3().player.getName()))) {
                    if (!Notifications.b() && Notifications.C(this.k).M(10000L)) {
                        pg.M().M().k.displayMessage("Name called in chat", "Your name has been written in chat.", TrayIcon.MessageType.NONE);
                        if (((FC.XC)Notifications.M(this.k).M()).equals((Object)FC.XC.a)) {
                            Notifications.e(true);
                            listener3 = this;
                            break Label_0171;
                        }
                    }
                }
                else {
                    Notifications.e(false);
                }
                listener3 = this;
            }
            if (listener3.k.pm.M() && formattedText.contains("§d") && formattedText.contains(" whispers: ")) {
                if (!Notifications.B() && Notifications.M(this.k).M(10000L)) {
                    pg.M().M().k.displayMessage("Private message received", "You have received a private message.", TrayIcon.MessageType.NONE);
                    if (((FC.XC)Notifications.M(this.k).M()).equals((Object)FC.XC.a)) {
                        Notifications.B(true);
                    }
                }
            }
            else {
                Notifications.B(false);
            }
        }
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
}
