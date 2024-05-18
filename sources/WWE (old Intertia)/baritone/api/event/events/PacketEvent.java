package baritone.api.event.events;

import baritone.api.event.events.type.*;

public final class PacketEvent
{
    private final gw networkManager;
    private final EventState state;
    private final ht<?> packet;
    
    public PacketEvent(final gw networkManager, final EventState state, final ht<?> packet) {
        super();
        this.networkManager = networkManager;
        this.state = state;
        this.packet = packet;
    }
    
    public final gw getNetworkManager() {
        return this.networkManager;
    }
    
    public final EventState getState() {
        return this.state;
    }
    
    public final ht<?> getPacket() {
        return this.packet;
    }
    
    public final <T extends ht<?>> T cast() {
        return (T)this.packet;
    }
}
