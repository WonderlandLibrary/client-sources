package com.polarware.event.impl.network;

import com.polarware.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

@Getter
@Setter
@AllArgsConstructor
public final class PacketReceiveEvent extends CancellableEvent {
    private Packet<?> packet;
    private EnumPacketDirection direction;
    private final INetHandler netHandler;
}
