package dev.vertic.event.impl.render;

import dev.vertic.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RotationsRenderEvent extends Event {

    private float yaw, bodyYaw, pitch;
    private final float partialTicks;

}
