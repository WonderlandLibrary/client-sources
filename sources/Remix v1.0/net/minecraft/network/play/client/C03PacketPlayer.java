package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C03PacketPlayer implements Packet
{
    public boolean onGround;
    public boolean moving;
    protected double x;
    protected double y;
    protected double z;
    protected float yaw;
    protected float pitch;
    protected boolean rotating;
    
    public C03PacketPlayer() {
    }
    
    public C03PacketPlayer(final boolean p_i45256_1_) {
        this.onGround = p_i45256_1_;
    }
    
    public void processPacket(final INetHandlerPlayServer handler) {
        handler.processPlayer(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.onGround = (data.readUnsignedByte() != 0);
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeByte(this.onGround ? 1 : 0);
    }
    
    public double getPositionX() {
        return this.x;
    }
    
    public void setPositionX(final double x) {
        this.x = x;
    }
    
    public double getPositionY() {
        return this.y;
    }
    
    public void setPositionY(final double y) {
        this.y = y;
    }
    
    public double getPositionZ() {
        return this.z;
    }
    
    public void setPositionZ(final double z) {
        this.z = z;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public boolean func_149465_i() {
        return this.onGround;
    }
    
    public boolean func_149466_j() {
        return this.moving;
    }
    
    public boolean getRotating() {
        return this.rotating;
    }
    
    public void func_149469_a(final boolean p_149469_1_) {
        this.moving = p_149469_1_;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.processPacket((INetHandlerPlayServer)handler);
    }
    
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
}
