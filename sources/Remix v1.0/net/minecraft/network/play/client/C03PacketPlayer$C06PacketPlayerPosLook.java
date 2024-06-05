package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public static class C06PacketPlayerPosLook extends C03PacketPlayer
{
    public C06PacketPlayerPosLook() {
        this.moving = true;
        this.rotating = true;
    }
    
    public C06PacketPlayerPosLook(final double p_i45941_1_, final double p_i45941_3_, final double p_i45941_5_, final float p_i45941_7_, final float p_i45941_8_, final boolean p_i45941_9_) {
        this.x = p_i45941_1_;
        this.y = p_i45941_3_;
        this.z = p_i45941_5_;
        this.yaw = p_i45941_7_;
        this.pitch = p_i45941_8_;
        this.onGround = p_i45941_9_;
        this.rotating = true;
        this.moving = true;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.x = data.readDouble();
        this.y = data.readDouble();
        this.z = data.readDouble();
        this.yaw = data.readFloat();
        this.pitch = data.readFloat();
        super.readPacketData(data);
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeDouble(this.x);
        data.writeDouble(this.y);
        data.writeDouble(this.z);
        data.writeFloat(this.yaw);
        data.writeFloat(this.pitch);
        super.writePacketData(data);
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        super.processPacket((INetHandlerPlayServer)handler);
    }
}
