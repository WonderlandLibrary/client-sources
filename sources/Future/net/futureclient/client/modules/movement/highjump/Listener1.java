package net.futureclient.client.modules.movement.highjump;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.HighJump;
import net.futureclient.client.fg;
import net.futureclient.client.n;

public class Listener1 extends n<fg>
{
    public final HighJump k;
    
    public Listener1(final HighJump k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final fg fg) {
        if (HighJump.getMinecraft2().player.movementInput.jump && (HighJump.M(this.k).M() || HighJump.getMinecraft1().player.onGround)) {
            fg.b(HighJump.getMinecraft().player.motionY = HighJump.M(this.k).B().doubleValue());
        }
    }
    
    public void M(final Event event) {
        this.M((fg)event);
    }
}
