package net.futureclient.client.modules.movement.entitycontrol;

import net.futureclient.client.events.Event;
import net.futureclient.loader.mixin.common.entity.living.player.wrapper.IEntityPlayerSP;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.modules.movement.EntityControl;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final EntityControl k;
    
    public Listener1(final EntityControl k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        if (EntityControl.M(this.k).B().doubleValue() > 0.0) {
            ((IEntityPlayerSP)EntityControl.getMinecraft().player).setHorseJumpPower(1.0f);
        }
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}
