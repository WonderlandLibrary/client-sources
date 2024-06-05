package net.minecraft.src;

import java.io.*;

public class Packet25EntityPainting extends Packet
{
    public int entityId;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public int direction;
    public String title;
    
    public Packet25EntityPainting() {
    }
    
    public Packet25EntityPainting(final EntityPainting par1EntityPainting) {
        this.entityId = par1EntityPainting.entityId;
        this.xPosition = par1EntityPainting.xPosition;
        this.yPosition = par1EntityPainting.yPosition;
        this.zPosition = par1EntityPainting.zPosition;
        this.direction = par1EntityPainting.hangingDirection;
        this.title = par1EntityPainting.art.title;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.entityId = par1DataInputStream.readInt();
        this.title = Packet.readString(par1DataInputStream, EnumArt.maxArtTitleLength);
        this.xPosition = par1DataInputStream.readInt();
        this.yPosition = par1DataInputStream.readInt();
        this.zPosition = par1DataInputStream.readInt();
        this.direction = par1DataInputStream.readInt();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.entityId);
        Packet.writeString(this.title, par1DataOutputStream);
        par1DataOutputStream.writeInt(this.xPosition);
        par1DataOutputStream.writeInt(this.yPosition);
        par1DataOutputStream.writeInt(this.zPosition);
        par1DataOutputStream.writeInt(this.direction);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleEntityPainting(this);
    }
    
    @Override
    public int getPacketSize() {
        return 24;
    }
}
