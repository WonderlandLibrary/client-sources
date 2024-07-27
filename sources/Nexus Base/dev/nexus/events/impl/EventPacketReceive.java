package dev.nexus.events.impl;

import dev.nexus.events.types.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.network.Packet;

@Getter
@AllArgsConstructor
public class EventPacketReceive extends CancellableEvent {
    Packet<?> packet;
}
