package dev.lvstrng.argon.event.listeners;

import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.events.Render2DEvent;

public interface Render2DListener extends EventListener {
    void onRender2D(final Render2DEvent p0);
}
