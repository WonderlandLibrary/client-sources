package com.polarware.event.impl.other;

import com.polarware.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import packet.type.ServerPacket;

@Getter
@RequiredArgsConstructor
public final class BackendPacketEvent implements Event {

    private final ServerPacket packet;
}
