package net.futureclient.client.modules.render.waypoints;

import net.futureclient.client.Xa;
import net.futureclient.client.events.EventWorld;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.Waypoints;
import net.futureclient.client.fF;
import net.futureclient.client.n;

public class Listener5 extends n<fF>
{
    public final Waypoints k;
    
    public Listener5(final Waypoints k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventWorld)event);
    }
    
    public void M(final EventWorld eventWorld) {
        if (Waypoints.M(this.k) != null && (!Waypoints.M(this.k).b().equals("singleplayer") || !Waypoints.getMinecraft10().isSingleplayer()) && (Waypoints.getMinecraft().getCurrentServerData() == null || !Waypoints.M(this.k).b().equalsIgnoreCase(Waypoints.getMinecraft41().getCurrentServerData().serverIP.replaceAll(":", "_"))) && (!Waypoints.getMinecraft36().isConnectedToRealms() || !Waypoints.M(this.k).b().equals("realms"))) {
            Waypoints.M(this.k, (Xa)null);
        }
    }
}
