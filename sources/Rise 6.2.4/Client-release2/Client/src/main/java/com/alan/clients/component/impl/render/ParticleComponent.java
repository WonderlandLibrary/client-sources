package com.alan.clients.component.impl.render;

import com.alan.clients.component.Component;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.util.interfaces.ThreadAccess;
import com.alan.clients.util.render.particle.Particle;

import java.util.concurrent.ConcurrentLinkedQueue;

import static com.alan.clients.layer.Layers.BLOOM;
import static com.alan.clients.layer.Layers.REGULAR;

public class ParticleComponent extends Component implements ThreadAccess {

    public static ConcurrentLinkedQueue<Particle> particles = new ConcurrentLinkedQueue<>();
    public static int rendered, bloomed;

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<Render2DEvent> onRender2DEvent = event -> {
        if (particles.isEmpty() || true) {
            return;
        }

        getLayer(REGULAR).add(ParticleComponent::render);
        getLayer(BLOOM).add(ParticleComponent::bloom);
    };

    public static void bloom() {
        particles.forEach(Particle::bloom);

        bloomed = mc.ingameGUI.frame;
    }

    public static void render() {
        rendered = mc.ingameGUI.frame;

        particles.forEach(particle -> {
            particle.render();

            if (particle.time.getElapsedTime() > 50 * 3 * 20) {
                particles.remove(particle);
            }
        });

        if (particles.isEmpty()) return;

        threadPool.execute(() -> {
            particles.forEach(Particle::update);
        });
    }

    public static void add(final Particle particle) {
        particles.add(particle);
    }
}
