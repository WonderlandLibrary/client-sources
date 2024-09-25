/*
 * Decompiled with CFR 0.150.
 */
package skizzle.events.listeners;

import net.minecraft.entity.Entity;
import skizzle.events.Event;

public class EventRenderEntity
extends Event<EventRenderEntity> {
    public double smoothZ;
    public Entity entity;
    public double smoothY;
    public double smoothX;

    public EventRenderEntity(Entity Nigga, double Nigga2, double Nigga3, double Nigga4) {
        EventRenderEntity Nigga5;
        Nigga5.entity = Nigga;
        Nigga5.smoothX = Nigga2;
        Nigga5.smoothY = Nigga3;
        Nigga5.smoothZ = Nigga4;
    }

    public static {
        throw throwable;
    }
}

