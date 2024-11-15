package dev.lvstrng.argon.event.listeners;

import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.events.CameraEvent;

public interface CameraListener extends EventListener {
    void onCamera(final CameraEvent event);
}
