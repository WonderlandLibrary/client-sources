package net.minecraft.src;

import java.io.*;

public class Packet52MultiBlockChange extends Packet
{
    public int xPosition;
    public int zPosition;
    public byte[] metadataArray;
    public int size;
    private static byte[] field_73449_e;
    
    static {
        Packet52MultiBlockChange.field_73449_e = new byte[0];
    }
    
    public Packet52MultiBlockChange() {
        this.isChunkDataPacket = true;
    }
    
    public Packet52MultiBlockChange(final int par1, final int par2, final short[] par3ArrayOfShort, final int par4, final World par5World) {
        this.isChunkDataPacket = true;
        this.xPosition = par1;
        this.zPosition = par2;
        this.size = par4;
        final int var6 = 4 * par4;
        final Chunk var7 = par5World.getChunkFromChunkCoords(par1, par2);
        try {
            if (par4 >= 64) {
                this.field_98193_m.logInfo("ChunkTilesUpdatePacket compress " + par4);
                if (Packet52MultiBlockChange.field_73449_e.length < var6) {
                    Packet52MultiBlockChange.field_73449_e = new byte[var6];
                }
            }
            else {
                final ByteArrayOutputStream var8 = new ByteArrayOutputStream(var6);
                final DataOutputStream var9 = new DataOutputStream(var8);
                for (int var10 = 0; var10 < par4; ++var10) {
                    final int var11 = par3ArrayOfShort[var10] >> 12 & 0xF;
                    final int var12 = par3ArrayOfShort[var10] >> 8 & 0xF;
                    final int var13 = par3ArrayOfShort[var10] & 0xFF;
                    var9.writeShort(par3ArrayOfShort[var10]);
                    var9.writeShort((short)((var7.getBlockID(var11, var13, var12) & 0xFFF) << 4 | (var7.getBlockMetadata(var11, var13, var12) & 0xF)));
                }
                this.metadataArray = var8.toByteArray();
                if (this.metadataArray.length != var6) {
                    throw new RuntimeException("Expected length " + var6 + " doesn't match received length " + this.metadataArray.length);
                }
            }
        }
        catch (IOException var14) {
            this.field_98193_m.logSevereException("Couldn't create chunk packet", var14);
            this.metadataArray = null;
        }
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.xPosition = par1DataInputStream.readInt();
        this.zPosition = par1DataInputStream.readInt();
        this.size = (par1DataInputStream.readShort() & 0xFFFF);
        final int var2 = par1DataInputStream.readInt();
        if (var2 > 0) {
            par1DataInputStream.readFully(this.metadataArray = new byte[var2]);
        }
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.xPosition);
        par1DataOutputStream.writeInt(this.zPosition);
        par1DataOutputStream.writeShort((short)this.size);
        if (this.metadataArray != null) {
            par1DataOutputStream.writeInt(this.metadataArray.length);
            par1DataOutputStream.write(this.metadataArray);
        }
        else {
            par1DataOutputStream.writeInt(0);
        }
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleMultiBlockChange(this);
    }
    
    @Override
    public int getPacketSize() {
        return 10 + this.size * 4;
    }
}
