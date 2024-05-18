package net.minecraft.src;

import java.io.*;

public class Packet63WorldParticles extends Packet
{
    private String particleName;
    private float posX;
    private float posY;
    private float posZ;
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    private float speed;
    private int quantity;
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.particleName = Packet.readString(par1DataInputStream, 64);
        this.posX = par1DataInputStream.readFloat();
        this.posY = par1DataInputStream.readFloat();
        this.posZ = par1DataInputStream.readFloat();
        this.offsetX = par1DataInputStream.readFloat();
        this.offsetY = par1DataInputStream.readFloat();
        this.offsetZ = par1DataInputStream.readFloat();
        this.speed = par1DataInputStream.readFloat();
        this.quantity = par1DataInputStream.readInt();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        Packet.writeString(this.particleName, par1DataOutputStream);
        par1DataOutputStream.writeFloat(this.posX);
        par1DataOutputStream.writeFloat(this.posY);
        par1DataOutputStream.writeFloat(this.posZ);
        par1DataOutputStream.writeFloat(this.offsetX);
        par1DataOutputStream.writeFloat(this.offsetY);
        par1DataOutputStream.writeFloat(this.offsetZ);
        par1DataOutputStream.writeFloat(this.speed);
        par1DataOutputStream.writeInt(this.quantity);
    }
    
    public String getParticleName() {
        return this.particleName;
    }
    
    public double getPositionX() {
        return this.posX;
    }
    
    public double getPositionY() {
        return this.posY;
    }
    
    public double getPositionZ() {
        return this.posZ;
    }
    
    public float getOffsetX() {
        return this.offsetX;
    }
    
    public float getOffsetY() {
        return this.offsetY;
    }
    
    public float getOffsetZ() {
        return this.offsetZ;
    }
    
    public float getSpeed() {
        return this.speed;
    }
    
    public int getQuantity() {
        return this.quantity;
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleWorldParticles(this);
    }
    
    @Override
    public int getPacketSize() {
        return 64;
    }
}
