package net.futureclient.client.modules.miscellaneous.notifications;

import net.futureclient.client.events.Event;
import java.awt.TrayIcon;
import net.futureclient.client.pg;
import org.lwjgl.opengl.Display;
import net.futureclient.client.events.EventWorld;
import net.futureclient.client.modules.miscellaneous.Notifications;
import net.futureclient.client.fF;
import net.futureclient.client.n;

public class Listener2 extends n<fF>
{
    public final Notifications k;
    
    public Listener2(final Notifications k) {
        this.k = k;
        super();
    }
    
    public void M(final EventWorld eventWorld) {
        if (!Display.isActive() && this.k.queue.M() && Notifications.getMinecraft().getCurrentServerData() != null && Notifications.getMinecraft11().getCurrentServerData().serverIP.equalsIgnoreCase("2b2t.org") && Notifications.e(this.k).M(10000L)) {
            pg.M().M().k.displayMessage("Connected to the server", "You have finished going through the queue.", TrayIcon.MessageType.NONE);
        }
    }
    
    public void M(final Event event) {
        this.M((EventWorld)event);
    }
}
