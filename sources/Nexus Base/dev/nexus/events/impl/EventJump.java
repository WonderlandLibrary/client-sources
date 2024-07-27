package dev.nexus.events.impl;

import dev.nexus.events.types.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventJump extends CancellableEvent {
    private float jumpMotion;
    private float yaw;
}