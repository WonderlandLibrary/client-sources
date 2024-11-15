package dev.lvstrng.argon.event.listeners;

import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.events.Render3DEvent;

public interface Render3DListener extends EventListener {
    void onRender3D(final Render3DEvent p0);
}
