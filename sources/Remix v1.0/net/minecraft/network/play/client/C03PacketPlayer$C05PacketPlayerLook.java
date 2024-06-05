package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public static class C05PacketPlayerLook extends C03PacketPlayer
{
    public C05PacketPlayerLook() {
        this.rotating = true;
    }
    
    public C05PacketPlayerLook(final float p_i45255_1_, final float p_i45255_2_, final boolean p_i45255_3_) {
        this.yaw = p_i45255_1_;
        this.pitch = p_i45255_2_;
        this.onGround = p_i45255_3_;
        this.rotating = true;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.yaw = data.readFloat();
        this.pitch = data.readFloat();
        super.readPacketData(data);
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeFloat(this.yaw);
        data.writeFloat(this.pitch);
        super.writePacketData(data);
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        super.processPacket((INetHandlerPlayServer)handler);
    }
}
