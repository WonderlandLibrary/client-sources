package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C09PacketHeldItemChange implements Packet
{
    private int slotId;
    
    public C09PacketHeldItemChange() {
    }
    
    public C09PacketHeldItemChange(final int p_i45262_1_) {
        this.slotId = p_i45262_1_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.slotId = data.readShort();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeShort(this.slotId);
    }
    
    public void processPacket(final INetHandlerPlayServer handler) {
        handler.processHeldItemChange(this);
    }
    
    public int getSlotId() {
        return this.slotId;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.processPacket((INetHandlerPlayServer)handler);
    }
}
