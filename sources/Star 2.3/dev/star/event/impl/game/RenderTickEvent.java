package dev.star.event.impl.game;

import dev.star.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RenderTickEvent extends Event.StateEvent {
    private final float ticks;
}
