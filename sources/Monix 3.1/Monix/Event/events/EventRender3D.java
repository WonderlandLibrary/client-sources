/*
 * Decompiled with CFR 0_122.
 */
package Monix.Event.events;

import Monix.Event.Event;

public class EventRender3D
extends Event {
    public static float particlTicks;

    public EventRender3D(float particlTicks) {
        EventRender3D.particlTicks = particlTicks;
    }

    public static float getParticlTicks() {
        return particlTicks;
    }

    public void setParticlTicks(float particlTicks) {
        EventRender3D.particlTicks = particlTicks;
    }
}

