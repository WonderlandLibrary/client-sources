package dev.lvstrng.argon.event.listeners;

import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.events.AttackEvent;

public interface AttackListener extends EventListener {
    void onAttack(final AttackEvent event);
}
