package com.alan.clients.event.impl.other;

import com.alan.clients.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import rip.vantage.commons.packet.api.abstracts.ServerPacket;

@Getter
@RequiredArgsConstructor
public final class BackendPacketEvent implements Event {

    private final ServerPacket packet;
}
