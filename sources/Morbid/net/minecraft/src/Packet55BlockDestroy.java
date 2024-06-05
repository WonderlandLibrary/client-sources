package net.minecraft.src;

import java.io.*;

public class Packet55BlockDestroy extends Packet
{
    private int entityId;
    private int posX;
    private int posY;
    private int posZ;
    private int destroyedStage;
    
    public Packet55BlockDestroy() {
    }
    
    public Packet55BlockDestroy(final int par1, final int par2, final int par3, final int par4, final int par5) {
        this.entityId = par1;
        this.posX = par2;
        this.posY = par3;
        this.posZ = par4;
        this.destroyedStage = par5;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.entityId = par1DataInputStream.readInt();
        this.posX = par1DataInputStream.readInt();
        this.posY = par1DataInputStream.readInt();
        this.posZ = par1DataInputStream.readInt();
        this.destroyedStage = par1DataInputStream.read();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.entityId);
        par1DataOutputStream.writeInt(this.posX);
        par1DataOutputStream.writeInt(this.posY);
        par1DataOutputStream.writeInt(this.posZ);
        par1DataOutputStream.write(this.destroyedStage);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleBlockDestroy(this);
    }
    
    @Override
    public int getPacketSize() {
        return 13;
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public int getPosX() {
        return this.posX;
    }
    
    public int getPosY() {
        return this.posY;
    }
    
    public int getPosZ() {
        return this.posZ;
    }
    
    public int getDestroyedStage() {
        return this.destroyedStage;
    }
    
    @Override
    public boolean isRealPacket() {
        return true;
    }
    
    @Override
    public boolean containsSameEntityIDAs(final Packet par1Packet) {
        final Packet55BlockDestroy var2 = (Packet55BlockDestroy)par1Packet;
        return var2.entityId == this.entityId;
    }
}
