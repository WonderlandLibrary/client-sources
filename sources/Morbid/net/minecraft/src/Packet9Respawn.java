package net.minecraft.src;

import java.io.*;

public class Packet9Respawn extends Packet
{
    public int respawnDimension;
    public int difficulty;
    public int worldHeight;
    public EnumGameType gameType;
    public WorldType terrainType;
    
    public Packet9Respawn() {
    }
    
    public Packet9Respawn(final int par1, final byte par2, final WorldType par3WorldType, final int par4, final EnumGameType par5EnumGameType) {
        this.respawnDimension = par1;
        this.difficulty = par2;
        this.worldHeight = par4;
        this.gameType = par5EnumGameType;
        this.terrainType = par3WorldType;
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleRespawn(this);
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.respawnDimension = par1DataInputStream.readInt();
        this.difficulty = par1DataInputStream.readByte();
        this.gameType = EnumGameType.getByID(par1DataInputStream.readByte());
        this.worldHeight = par1DataInputStream.readShort();
        final String var2 = Packet.readString(par1DataInputStream, 16);
        this.terrainType = WorldType.parseWorldType(var2);
        if (this.terrainType == null) {
            this.terrainType = WorldType.DEFAULT;
        }
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.respawnDimension);
        par1DataOutputStream.writeByte(this.difficulty);
        par1DataOutputStream.writeByte(this.gameType.getID());
        par1DataOutputStream.writeShort(this.worldHeight);
        Packet.writeString(this.terrainType.getWorldTypeName(), par1DataOutputStream);
    }
    
    @Override
    public int getPacketSize() {
        return 8 + ((this.terrainType == null) ? 0 : this.terrainType.getWorldTypeName().length());
    }
}
