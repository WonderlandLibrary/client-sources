package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S25PacketBlockBreakAnim implements Packet
{
    private int breakerId;
    private BlockPos position;
    private int progress;
    
    public S25PacketBlockBreakAnim() {
    }
    
    public S25PacketBlockBreakAnim(final int breakerId, final BlockPos pos, final int progress) {
        this.breakerId = breakerId;
        this.position = pos;
        this.progress = progress;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.breakerId = data.readVarIntFromBuffer();
        this.position = data.readBlockPos();
        this.progress = data.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeVarIntToBuffer(this.breakerId);
        data.writeBlockPos(this.position);
        data.writeByte(this.progress);
    }
    
    public void handle(final INetHandlerPlayClient handler) {
        handler.handleBlockBreakAnim(this);
    }
    
    public int func_148845_c() {
        return this.breakerId;
    }
    
    public BlockPos func_179821_b() {
        return this.position;
    }
    
    public int func_148846_g() {
        return this.progress;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.handle((INetHandlerPlayClient)handler);
    }
}
