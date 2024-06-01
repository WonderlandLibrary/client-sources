package io.github.liticane.electron.event;

import io.github.liticane.electron.structure.interfaces.Cancellable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event implements Cancellable {
    private boolean cancelled;
    private EventType type;

    @Override
    public void cancel() {
        this.cancelled = true;
    }
}
