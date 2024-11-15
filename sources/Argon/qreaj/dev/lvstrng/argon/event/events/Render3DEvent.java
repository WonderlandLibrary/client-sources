package dev.lvstrng.argon.event.events;

import dev.lvstrng.argon.event.Event;
import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.listeners.Render3DListener;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

public class Render3DEvent extends Event {
    public MatrixStack matrices;
    public float delta;

    public Render3DEvent(final MatrixStack matrices, final float delta) {
        this.matrices = matrices;
        this.delta = delta;
    }

    @Override
    public void callListeners(final ArrayList<EventListener> listeners) {
        listeners.stream().filter(listener -> listener instanceof Render3DListener).map(listener -> (Render3DListener) listener).forEach(listener -> listener.onRender3D(this));
    }

    @Override
    public Class<?> getClazz() {
        return Render3DListener.class;
    }
}