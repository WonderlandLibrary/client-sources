package com.alan.clients.event.impl.render;

import com.alan.clients.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class ViewBobbingEvent extends CancellableEvent {

    private int time;
}
