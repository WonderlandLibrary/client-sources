package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public static class C04PacketPlayerPosition extends C03PacketPlayer
{
    public C04PacketPlayerPosition() {
        this.moving = true;
    }
    
    public C04PacketPlayerPosition(final double p_i45942_1_, final double p_i45942_3_, final double p_i45942_5_, final boolean p_i45942_7_) {
        this.x = p_i45942_1_;
        this.y = p_i45942_3_;
        this.z = p_i45942_5_;
        this.onGround = p_i45942_7_;
        this.moving = true;
    }
    
    public C04PacketPlayerPosition(final double p_i45942_1_, final double p_i45942_3_, final double p_i45942_5_, final boolean p_i45942_7_, final boolean movinn) {
        this.x = p_i45942_1_;
        this.y = p_i45942_3_;
        this.z = p_i45942_5_;
        this.onGround = p_i45942_7_;
        this.moving = movinn;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.x = data.readDouble();
        this.y = data.readDouble();
        this.z = data.readDouble();
        super.readPacketData(data);
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeDouble(this.x);
        data.writeDouble(this.y);
        data.writeDouble(this.z);
        super.writePacketData(data);
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        super.processPacket((INetHandlerPlayServer)handler);
    }
}
