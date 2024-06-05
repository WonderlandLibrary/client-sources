package net.minecraft.network.play.client;

import net.minecraft.util.*;
import java.io.*;
import org.apache.commons.lang3.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C14PacketTabComplete implements Packet
{
    private String message;
    private BlockPos field_179710_b;
    
    public C14PacketTabComplete() {
    }
    
    public C14PacketTabComplete(final String msg) {
        this(msg, null);
    }
    
    public C14PacketTabComplete(final String p_i45948_1_, final BlockPos p_i45948_2_) {
        this.message = p_i45948_1_;
        this.field_179710_b = p_i45948_2_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.message = data.readStringFromBuffer(32767);
        final boolean var2 = data.readBoolean();
        if (var2) {
            this.field_179710_b = data.readBlockPos();
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeString(StringUtils.substring(this.message, 0, 32767));
        final boolean var2 = this.field_179710_b != null;
        data.writeBoolean(var2);
        if (var2) {
            data.writeBlockPos(this.field_179710_b);
        }
    }
    
    public void func_180756_a(final INetHandlerPlayServer p_180756_1_) {
        p_180756_1_.processTabComplete(this);
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    public BlockPos func_179709_b() {
        return this.field_179710_b;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.func_180756_a((INetHandlerPlayServer)handler);
    }
}
