package pw.latematt.xiv.event.events;

import net.minecraft.util.Vec3;
import pw.latematt.xiv.event.Event;

/**
 * @author Matthew
 */
public class LiquidVelocityEvent extends Event {
    private Vec3 velocity;

    public LiquidVelocityEvent(Vec3 velocity) {
        this.velocity = velocity;
    }

    public Vec3 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vec3 velocity) {
        this.velocity = velocity;
    }
}
