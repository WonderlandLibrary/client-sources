package dev.nexus.events.impl;

import dev.nexus.events.types.CancellableEvent;
import dev.nexus.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class EventStrafe extends CancellableEvent implements Utils {

    private float forward;
    private float strafe;
    private float friction;
    private float yaw;
}