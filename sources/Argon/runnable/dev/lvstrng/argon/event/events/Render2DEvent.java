package dev.lvstrng.argon.event.events;

import dev.lvstrng.argon.event.Event;
import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.listeners.Render2DListener;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;

public class Render2DEvent extends Event {
    public DrawContext ctx;
    public float delta;

    public Render2DEvent(final DrawContext context, final float delta) {
        this.ctx = context;
        this.delta = delta;
    }

    @Override
    public void callListeners(final ArrayList<EventListener> listeners) {
        listeners.stream().filter(listener -> listener instanceof Render2DListener).map(listener -> (Render2DListener) listener).forEach(listener -> listener.onRender2D(this));
    }

    @Override
    public Class<?> getClazz() {
        return Render2DListener.class;
    }
}
