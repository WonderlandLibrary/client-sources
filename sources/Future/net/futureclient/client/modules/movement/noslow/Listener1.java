package net.futureclient.client.modules.movement.noslow;

import net.futureclient.client.events.Event;
import net.minecraft.util.MovementInput;
import net.futureclient.client.modules.movement.NoSlow;
import net.futureclient.client.oD;
import net.futureclient.client.n;

public class Listener1 extends n<oD>
{
    public final NoSlow k;
    
    public Listener1(final NoSlow k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final oD od) {
        if (this.k.items.M() && NoSlow.getMinecraft27().player.isHandActive() && !NoSlow.getMinecraft().player.isRiding()) {
            final MovementInput m = od.M();
            m.moveStrafe /= 0.2f;
            m.moveForward /= 0.2f;
        }
    }
    
    public void M(final Event event) {
        this.M((oD)event);
    }
}
