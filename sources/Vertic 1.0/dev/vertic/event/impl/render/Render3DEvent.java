package dev.vertic.event.impl.render;

import dev.vertic.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Render3DEvent extends Event {
    private final float partialTicks;
}
