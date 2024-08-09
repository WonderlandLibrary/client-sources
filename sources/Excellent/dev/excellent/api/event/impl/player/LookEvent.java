package dev.excellent.api.event.impl.player;

import dev.excellent.api.event.CancellableEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LookEvent extends CancellableEvent {

    public double yaw, pitch;

}