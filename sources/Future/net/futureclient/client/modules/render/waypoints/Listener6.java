package net.futureclient.client.modules.render.waypoints;

import net.futureclient.client.Xa;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.futureclient.client.s;
import net.futureclient.client.Lb;
import net.futureclient.client.modules.movement.Yaw;
import net.futureclient.client.EC;
import net.futureclient.client.pg;
import net.futureclient.client.modules.movement.AutoWalk;
import org.lwjgl.opengl.Display;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.Waypoints;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener6 extends n<lF>
{
    public final Waypoints k;
    
    public Listener6(final Waypoints k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
    
    public void M(final EventUpdate eventUpdate) {
        if (Waypoints.M(this.k) != null && Waypoints.M(this.k, Waypoints.M(this.k)) < 15.0f) {
            Listener6 listener6;
            if (Display.isActive()) {
                ((AutoWalk)pg.M().M().M((Class)EC.class)).M(false);
                ((Yaw)pg.M().M().M((Class)Lb.class)).M(false);
                s.M().M("You have reached your destination.");
                listener6 = this;
            }
            else if (Waypoints.getMinecraft12().getConnection() == null) {
                Waypoints.getMinecraft5().world.sendQuittingDisconnectingPacket();
                listener6 = this;
            }
            else {
                Waypoints.getMinecraft29().getConnection().getNetworkManager().closeChannel((ITextComponent)new TextComponentString("You have reached your destination."));
                listener6 = this;
            }
            Waypoints.M(listener6.k, (Xa)null);
        }
    }
}
