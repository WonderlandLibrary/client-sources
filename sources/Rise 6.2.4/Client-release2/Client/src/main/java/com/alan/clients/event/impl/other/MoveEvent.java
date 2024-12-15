package com.alan.clients.event.impl.other;

import com.alan.clients.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class MoveEvent extends CancellableEvent {

    private double posX, posY, posZ;
}
