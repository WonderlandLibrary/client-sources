package com.minus.event;

import lombok.Getter;
import lombok.Setter;
import me.zero.alpine.event.Cancellable;

@Getter
@Setter
public class CancellableEvent implements Cancellable, Event {
    private boolean cancelled;
}
