/*
 * Decompiled with CFR 0.150.
 */
package skizzle.events.listeners;

import net.minecraft.util.MathHelper;
import skizzle.events.Event;

public class EventStrafing
extends Event<EventStrafing> {
    public float yaw;
    public double strafe;
    public double friction;
    public double forward;
    public double z;
    public double x;

    public static {
        throw throwable;
    }

    public EventStrafing(float Nigga, double Nigga2, double Nigga3, double Nigga4) {
        EventStrafing Nigga5;
        Nigga5.x = MathHelper.sin(Nigga * Float.intBitsToFloat(1.04944243E9f ^ 0x7EC4375D) / Float.intBitsToFloat(1.03620755E9f ^ 0x7EF745DF));
        Nigga5.z = MathHelper.cos(Nigga * Float.intBitsToFloat(1.06315142E9f ^ 0x7F176890) / Float.intBitsToFloat(1.00999104E9f ^ 0x7F073D63));
        Nigga5.forward = Nigga4;
        Nigga5.strafe = Nigga3;
        Nigga5.yaw = Nigga;
        Nigga5.friction = Nigga2;
    }

    public void setZ(float Nigga) {
        Nigga.z = MathHelper.cos(Nigga * Float.intBitsToFloat(1.04586387E9f ^ 0x7E1F920F) / Float.intBitsToFloat(1.01359366E9f ^ 0x7F5E3633));
    }

    public double getZ() {
        EventStrafing Nigga;
        return Nigga.z;
    }

    public void setX(float Nigga) {
        Nigga.x = MathHelper.sin(Nigga * Float.intBitsToFloat(1.06078336E9f ^ 0x7F734B39) / Float.intBitsToFloat(1.03636026E9f ^ 0x7EF19A29));
    }

    public double getX() {
        EventStrafing Nigga;
        return Nigga.x;
    }

    public double getFriction() {
        EventStrafing Nigga;
        return Nigga.friction;
    }

    public void setFriction(double Nigga) {
        Nigga.friction = Nigga;
    }
}

