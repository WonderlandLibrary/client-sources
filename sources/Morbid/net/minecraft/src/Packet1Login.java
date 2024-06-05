package net.minecraft.src;

import java.io.*;

public class Packet1Login extends Packet
{
    public int clientEntityId;
    public WorldType terrainType;
    public boolean hardcoreMode;
    public EnumGameType gameType;
    public int dimension;
    public byte difficultySetting;
    public byte worldHeight;
    public byte maxPlayers;
    
    public Packet1Login() {
        this.clientEntityId = 0;
    }
    
    public Packet1Login(final int par1, final WorldType par2WorldType, final EnumGameType par3EnumGameType, final boolean par4, final int par5, final int par6, final int par7, final int par8) {
        this.clientEntityId = 0;
        this.clientEntityId = par1;
        this.terrainType = par2WorldType;
        this.dimension = par5;
        this.difficultySetting = (byte)par6;
        this.gameType = par3EnumGameType;
        this.worldHeight = (byte)par7;
        this.maxPlayers = (byte)par8;
        this.hardcoreMode = par4;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.clientEntityId = par1DataInputStream.readInt();
        final String var2 = Packet.readString(par1DataInputStream, 16);
        this.terrainType = WorldType.parseWorldType(var2);
        if (this.terrainType == null) {
            this.terrainType = WorldType.DEFAULT;
        }
        final byte var3 = par1DataInputStream.readByte();
        this.hardcoreMode = ((var3 & 0x8) == 0x8);
        final int var4 = var3 & 0xFFFFFFF7;
        this.gameType = EnumGameType.getByID(var4);
        this.dimension = par1DataInputStream.readByte();
        this.difficultySetting = par1DataInputStream.readByte();
        this.worldHeight = par1DataInputStream.readByte();
        this.maxPlayers = par1DataInputStream.readByte();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.clientEntityId);
        Packet.writeString((this.terrainType == null) ? "" : this.terrainType.getWorldTypeName(), par1DataOutputStream);
        int var2 = this.gameType.getID();
        if (this.hardcoreMode) {
            var2 |= 0x8;
        }
        par1DataOutputStream.writeByte(var2);
        par1DataOutputStream.writeByte(this.dimension);
        par1DataOutputStream.writeByte(this.difficultySetting);
        par1DataOutputStream.writeByte(this.worldHeight);
        par1DataOutputStream.writeByte(this.maxPlayers);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleLogin(this);
    }
    
    @Override
    public int getPacketSize() {
        int var1 = 0;
        if (this.terrainType != null) {
            var1 = this.terrainType.getWorldTypeName().length();
        }
        return 6 + 2 * var1 + 4 + 4 + 1 + 1 + 1;
    }
}
