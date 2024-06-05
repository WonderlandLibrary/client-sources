package zamorozka.event.events;

import zamorozka.event.Event;

/**
 * Created by Hexeption on 07/01/2017.
 */
public class EventRender3D extends Event {

    public float particlTicks;

    public EventRender3D(float particlTicks) {

        this.particlTicks = particlTicks;
    }

    public float getParticlTicks() {

        return particlTicks;
    }

    public void setParticlTicks(float particlTicks) {

        this.particlTicks = particlTicks;
    }
}
