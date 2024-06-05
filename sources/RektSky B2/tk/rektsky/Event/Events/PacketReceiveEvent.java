package tk.rektsky.Event.Events;

import tk.rektsky.Event.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class PacketReceiveEvent extends Event
{
    private Packet<INetHandlerPlayClient> packet;
    private boolean canceled;
    
    public PacketReceiveEvent(final Packet<INetHandlerPlayClient> packet) {
        this.packet = packet;
    }
    
    public Packet<INetHandlerPlayClient> getPacket() {
        return this.packet;
    }
    
    public boolean isCanceled() {
        return this.canceled;
    }
    
    public void setCanceled(final boolean canceled) {
        this.canceled = canceled;
    }
}
