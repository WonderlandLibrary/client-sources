package net.minecraft.src;

import java.io.*;

public class Packet7UseEntity extends Packet
{
    public int playerEntityId;
    public int targetEntity;
    public int isLeftClick;
    
    public Packet7UseEntity() {
    }
    
    public Packet7UseEntity(final int par1, final int par2, final int par3) {
        this.playerEntityId = par1;
        this.targetEntity = par2;
        this.isLeftClick = par3;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.playerEntityId = par1DataInputStream.readInt();
        this.targetEntity = par1DataInputStream.readInt();
        this.isLeftClick = par1DataInputStream.readByte();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.playerEntityId);
        par1DataOutputStream.writeInt(this.targetEntity);
        par1DataOutputStream.writeByte(this.isLeftClick);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleUseEntity(this);
    }
    
    @Override
    public int getPacketSize() {
        return 9;
    }
}
