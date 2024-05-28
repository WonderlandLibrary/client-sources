package dev.vertic.event;

import dev.vertic.Client;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {

    private boolean cancelled;

    public void call() {
        Client.instance.getEventBus().call(this);
    }

    public void cancel() {
        cancelled = true;
    }
}
