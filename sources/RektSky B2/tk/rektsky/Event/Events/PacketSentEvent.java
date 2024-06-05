package tk.rektsky.Event.Events;

import tk.rektsky.Event.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class PacketSentEvent extends Event
{
    private Packet<INetHandlerPlayServer> packet;
    private boolean canceled;
    
    public PacketSentEvent(final Packet<INetHandlerPlayServer> packet) {
        this.packet = packet;
    }
    
    public Packet<INetHandlerPlayServer> getPacket() {
        return this.packet;
    }
    
    public void setPacket(final Packet<INetHandlerPlayServer> packet) {
        this.packet = packet;
    }
    
    public boolean isCanceled() {
        return this.canceled;
    }
    
    public void setCanceled(final boolean canceled) {
        this.canceled = canceled;
    }
}
