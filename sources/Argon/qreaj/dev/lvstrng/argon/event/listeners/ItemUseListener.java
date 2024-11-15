package dev.lvstrng.argon.event.listeners;

import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.events.ItemUseEvent;

public interface ItemUseListener extends EventListener {
    void onItemUse(final ItemUseEvent event);
}
