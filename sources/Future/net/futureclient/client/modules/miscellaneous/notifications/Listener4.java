package net.futureclient.client.modules.miscellaneous.notifications;

import net.futureclient.client.events.Event;
import java.awt.TrayIcon;
import net.futureclient.client.pg;
import org.lwjgl.opengl.Display;
import net.futureclient.client.modules.miscellaneous.Notifications;
import net.futureclient.client.kE;
import net.futureclient.client.n;

public class Listener4 extends n<kE>
{
    public final Notifications k;
    
    public Listener4(final Notifications k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final kE ke) {
        if (!Display.isActive() && this.k.kick.M() && Notifications.b(this.k).M(10000L)) {
            pg.M().M().k.displayMessage("Kicked from the server", ke.M(), TrayIcon.MessageType.NONE);
        }
    }
    
    public void M(final Event event) {
        this.M((kE)event);
    }
}
