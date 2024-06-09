package dev.vertic.event.impl.packet;

import dev.vertic.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.Packet;

@Getter
@Setter
@AllArgsConstructor
public final class PacketSendEvent extends Event {
    private Packet<?> packet;
}