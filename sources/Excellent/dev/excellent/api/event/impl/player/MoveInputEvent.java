package dev.excellent.api.event.impl.player;

import dev.excellent.api.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MoveInputEvent extends CancellableEvent {
    private float forward, strafe;
    private boolean jump, sneaking;
    private double sneakSlowDownMultiplier;
}