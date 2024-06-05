package de.tired.event.events;

import de.tired.event.Event;
import lombok.Getter;
import lombok.Setter;

public class EventClip extends Event {

    @Setter
    @Getter
    boolean shouldDoNoClip;

    public EventClip(boolean clip) {
        this.shouldDoNoClip = clip;
    }

}
