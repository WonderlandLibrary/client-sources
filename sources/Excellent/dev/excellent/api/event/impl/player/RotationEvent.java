package dev.excellent.api.event.impl.player;

import dev.excellent.api.event.Event;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RotationEvent extends Event {
    public float yaw, pitch;
    public float partialTicks;
}