package dev.excellent.api.event;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CancellableEvent extends EventBase {

    private boolean cancelled;

    public void setCancelled() {
        this.cancelled = true;
    }

    public void cancel() {
        this.cancelled = true;
    }

}
