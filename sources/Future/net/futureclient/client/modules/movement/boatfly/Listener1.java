package net.futureclient.client.modules.movement.boatfly;

import net.futureclient.client.events.Event;
import java.util.Objects;
import net.futureclient.client.modules.movement.BoatFly;
import net.futureclient.client.xe;
import net.futureclient.client.n;

public class Listener1 extends n<xe>
{
    public final BoatFly k;
    
    public Listener1(final BoatFly k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final xe xe) {
        if (BoatFly.getMinecraft6().player != null && BoatFly.getMinecraft7().player.getRidingEntity() != null && Objects.equals(BoatFly.getMinecraft1().player.getRidingEntity().getControllingPassenger(), BoatFly.getMinecraft().player)) {
            BoatFly.getMinecraft2().player.getRidingEntity().motionY = (BoatFly.getMinecraft4().gameSettings.keyBindJump.isKeyDown() ? this.k.upSpeed.B().floatValue() : ((double)(-this.k.downSpeed.B().floatValue())));
            if (BoatFly.M(this.k).M()) {
                BoatFly.getMinecraft3().player.getRidingEntity().rotationYaw = BoatFly.getMinecraft5().player.rotationYaw;
            }
        }
    }
    
    public void M(final Event event) {
        this.M((xe)event);
    }
}
