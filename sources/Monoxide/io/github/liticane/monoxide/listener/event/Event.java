package io.github.liticane.monoxide.listener.event;

import io.github.liticane.monoxide.listener.handling.EventHandling;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {
    private boolean cancelled;

    @SuppressWarnings("unchecked")
    public <T extends Event> T publishItself() {
        EventHandling.getInstance().publishEvent(this);
        return (T) this;
    }

}
