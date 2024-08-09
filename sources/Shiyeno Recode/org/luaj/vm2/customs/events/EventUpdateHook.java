package org.luaj.vm2.customs.events;

import org.luaj.vm2.customs.EventHook;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;

public class EventUpdateHook extends EventHook {

    private EventUpdate update;

    public EventUpdateHook(Event event) {
        super(event);
    }

    @Override
    public String getName() {
        return "update_event";
    }
}
