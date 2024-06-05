package net.minecraft.network.play.server;

import net.minecraft.util.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S36PacketSignEditorOpen implements Packet
{
    private BlockPos field_179778_a;
    
    public S36PacketSignEditorOpen() {
    }
    
    public S36PacketSignEditorOpen(final BlockPos p_i45971_1_) {
        this.field_179778_a = p_i45971_1_;
    }
    
    public void processPacket(final INetHandlerPlayClient handler) {
        handler.handleSignEditorOpen(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.field_179778_a = data.readBlockPos();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeBlockPos(this.field_179778_a);
    }
    
    public BlockPos func_179777_a() {
        return this.field_179778_a;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.processPacket((INetHandlerPlayClient)handler);
    }
}
