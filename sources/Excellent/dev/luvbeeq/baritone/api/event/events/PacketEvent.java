package dev.luvbeeq.baritone.api.event.events;

import dev.luvbeeq.baritone.api.event.events.type.EventState;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;

/**
 * @author Brady
 * @since 8/6/2018
 */
public final class PacketEvent {

    private final NetworkManager networkManager;

    private final EventState state;

    private final IPacket<?> packet;

    public PacketEvent(NetworkManager networkManager, EventState state, IPacket<?> packet) {
        this.networkManager = networkManager;
        this.state = state;
        this.packet = packet;
    }

    public final NetworkManager getNetworkManager() {
        return this.networkManager;
    }

    public final EventState getState() {
        return this.state;
    }

    public final IPacket<?> getPacket() {
        return this.packet;
    }

    @SuppressWarnings("unchecked")
    public final <T extends IPacket<?>> T cast() {
        return (T) this.packet;
    }
}
