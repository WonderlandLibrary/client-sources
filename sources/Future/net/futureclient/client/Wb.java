package net.futureclient.client;

import net.futureclient.client.events.Event;
import java.util.Objects;

public class Wb extends n<wE>
{
    public final yb k;
    
    public Wb(final yb k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final wE we) {
        if (IG.M(we.M()) && we.M() != null && (we.M().equals((Object)yb.getMinecraft14().player) || (we.M().getControllingPassenger() != null && Objects.equals(we.M().getControllingPassenger(), yb.getMinecraft13().player)))) {
            yb.M(this.k, we.M());
        }
    }
    
    public void M(final Event event) {
        this.M((wE)event);
    }
}
