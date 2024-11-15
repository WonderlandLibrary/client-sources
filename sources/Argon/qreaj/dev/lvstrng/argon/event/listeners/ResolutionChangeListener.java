package dev.lvstrng.argon.event.listeners;

import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.events.ResolutionChangedEvent;

public interface ResolutionChangeListener extends EventListener {
    void onResolutionChange(final ResolutionChangedEvent p0);
}
