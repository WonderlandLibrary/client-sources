package dev.lvstrng.argon.event.listeners;

import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.events.MouseMoveEvent;

public interface MouseMoveListener extends EventListener {
    void onMouseMove(final MouseMoveEvent event);
}
