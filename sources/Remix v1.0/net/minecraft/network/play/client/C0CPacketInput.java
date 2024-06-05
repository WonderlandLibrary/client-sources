package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C0CPacketInput implements Packet
{
    private float strafeSpeed;
    private float forwardSpeed;
    private boolean jumping;
    private boolean sneaking;
    
    public C0CPacketInput() {
    }
    
    public C0CPacketInput(final float p_i45261_1_, final float p_i45261_2_, final boolean p_i45261_3_, final boolean p_i45261_4_) {
        this.strafeSpeed = p_i45261_1_;
        this.forwardSpeed = p_i45261_2_;
        this.jumping = p_i45261_3_;
        this.sneaking = p_i45261_4_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.strafeSpeed = data.readFloat();
        this.forwardSpeed = data.readFloat();
        final byte var2 = data.readByte();
        this.jumping = ((var2 & 0x1) > 0);
        this.sneaking = ((var2 & 0x2) > 0);
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeFloat(this.strafeSpeed);
        data.writeFloat(this.forwardSpeed);
        byte var2 = 0;
        if (this.jumping) {
            var2 |= 0x1;
        }
        if (this.sneaking) {
            var2 |= 0x2;
        }
        data.writeByte(var2);
    }
    
    public void func_180766_a(final INetHandlerPlayServer p_180766_1_) {
        p_180766_1_.processInput(this);
    }
    
    public float getStrafeSpeed() {
        return this.strafeSpeed;
    }
    
    public void setStrafeSpeed(final float StrafeSpood) {
        this.strafeSpeed = StrafeSpood;
    }
    
    public float getForwardSpeed() {
        return this.forwardSpeed;
    }
    
    public void setForwardSpeed(final float ForwardSpood) {
        this.forwardSpeed = ForwardSpood;
    }
    
    public boolean isJumping() {
        return this.jumping;
    }
    
    public boolean isSneaking() {
        return this.sneaking;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.func_180766_a((INetHandlerPlayServer)handler);
    }
}
