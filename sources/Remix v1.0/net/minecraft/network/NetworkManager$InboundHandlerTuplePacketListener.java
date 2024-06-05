package net.minecraft.network;

import io.netty.util.concurrent.*;

static class InboundHandlerTuplePacketListener
{
    private final Packet packet;
    private final GenericFutureListener[] futureListeners;
    
    public InboundHandlerTuplePacketListener(final Packet inPacket, final GenericFutureListener... inFutureListeners) {
        this.packet = inPacket;
        this.futureListeners = inFutureListeners;
    }
}
