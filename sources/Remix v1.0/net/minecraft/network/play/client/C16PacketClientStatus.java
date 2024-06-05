package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C16PacketClientStatus implements Packet
{
    private EnumState status;
    
    public C16PacketClientStatus() {
    }
    
    public C16PacketClientStatus(final EnumState statusIn) {
        this.status = statusIn;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.status = (EnumState)data.readEnumValue(EnumState.class);
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeEnumValue(this.status);
    }
    
    public void func_180758_a(final INetHandlerPlayServer p_180758_1_) {
        p_180758_1_.processClientStatus(this);
    }
    
    public EnumState getStatus() {
        return this.status;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.func_180758_a((INetHandlerPlayServer)handler);
    }
    
    public enum EnumState
    {
        PERFORM_RESPAWN("PERFORM_RESPAWN", 0), 
        REQUEST_STATS("REQUEST_STATS", 1), 
        OPEN_INVENTORY_ACHIEVEMENT("OPEN_INVENTORY_ACHIEVEMENT", 2);
        
        private static final EnumState[] $VALUES;
        
        private EnumState(final String p_i45947_1_, final int p_i45947_2_) {
        }
        
        static {
            $VALUES = new EnumState[] { EnumState.PERFORM_RESPAWN, EnumState.REQUEST_STATS, EnumState.OPEN_INVENTORY_ACHIEVEMENT };
        }
    }
}
