package dev.excellent.api.event.impl.other;

import dev.excellent.api.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TimerEvent extends Event {
    private final long gameTime;
}