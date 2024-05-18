package net.minecraft.src;

import java.io.*;

public class Packet61DoorChange extends Packet
{
    public int sfxID;
    public int auxData;
    public int posX;
    public int posY;
    public int posZ;
    private boolean disableRelativeVolume;
    
    public Packet61DoorChange() {
    }
    
    public Packet61DoorChange(final int par1, final int par2, final int par3, final int par4, final int par5, final boolean par6) {
        this.sfxID = par1;
        this.posX = par2;
        this.posY = par3;
        this.posZ = par4;
        this.auxData = par5;
        this.disableRelativeVolume = par6;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.sfxID = par1DataInputStream.readInt();
        this.posX = par1DataInputStream.readInt();
        this.posY = (par1DataInputStream.readByte() & 0xFF);
        this.posZ = par1DataInputStream.readInt();
        this.auxData = par1DataInputStream.readInt();
        this.disableRelativeVolume = par1DataInputStream.readBoolean();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.sfxID);
        par1DataOutputStream.writeInt(this.posX);
        par1DataOutputStream.writeByte(this.posY & 0xFF);
        par1DataOutputStream.writeInt(this.posZ);
        par1DataOutputStream.writeInt(this.auxData);
        par1DataOutputStream.writeBoolean(this.disableRelativeVolume);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleDoorChange(this);
    }
    
    @Override
    public int getPacketSize() {
        return 21;
    }
    
    public boolean getRelativeVolumeDisabled() {
        return this.disableRelativeVolume;
    }
}
