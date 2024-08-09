package dev.excellent.api.event.impl.server;

import dev.excellent.api.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.IPacket;

@Getter
@Setter
@AllArgsConstructor
public class PacketEvent extends CancellableEvent {
    private final Action action;
    private final IPacket<?> packet;

    public boolean isSent() {
        return this.getAction() == Action.SENT;
    }

    public boolean isReceive() {
        return this.getAction() == Action.RECEIVE;
    }

    public enum Action {
        SENT, RECEIVE
    }
}
