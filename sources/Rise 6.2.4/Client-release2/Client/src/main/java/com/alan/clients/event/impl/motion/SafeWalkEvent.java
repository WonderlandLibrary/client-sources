package com.alan.clients.event.impl.motion;

import com.alan.clients.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class SafeWalkEvent implements Event {
    private double height;
}
