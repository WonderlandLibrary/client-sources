package net.minecraft.src;

import java.util.*;
import java.util.zip.*;
import java.io.*;

public class Packet56MapChunks extends Packet
{
    private int[] chunkPostX;
    private int[] chunkPosZ;
    public int[] field_73590_a;
    public int[] field_73588_b;
    private byte[] chunkDataBuffer;
    private byte[][] field_73584_f;
    private int dataLength;
    private boolean skyLightSent;
    private static byte[] chunkDataNotCompressed;
    
    static {
        Packet56MapChunks.chunkDataNotCompressed = new byte[0];
    }
    
    public Packet56MapChunks() {
    }
    
    public Packet56MapChunks(final List par1List) {
        final int var2 = par1List.size();
        this.chunkPostX = new int[var2];
        this.chunkPosZ = new int[var2];
        this.field_73590_a = new int[var2];
        this.field_73588_b = new int[var2];
        this.field_73584_f = new byte[var2][];
        this.skyLightSent = (!par1List.isEmpty() && !par1List.get(0).worldObj.provider.hasNoSky);
        int var3 = 0;
        for (int var4 = 0; var4 < var2; ++var4) {
            final Chunk var5 = par1List.get(var4);
            final Packet51MapChunkData var6 = Packet51MapChunk.getMapChunkData(var5, true, 65535);
            if (Packet56MapChunks.chunkDataNotCompressed.length < var3 + var6.compressedData.length) {
                final byte[] var7 = new byte[var3 + var6.compressedData.length];
                System.arraycopy(Packet56MapChunks.chunkDataNotCompressed, 0, var7, 0, Packet56MapChunks.chunkDataNotCompressed.length);
                Packet56MapChunks.chunkDataNotCompressed = var7;
            }
            System.arraycopy(var6.compressedData, 0, Packet56MapChunks.chunkDataNotCompressed, var3, var6.compressedData.length);
            var3 += var6.compressedData.length;
            this.chunkPostX[var4] = var5.xPosition;
            this.chunkPosZ[var4] = var5.zPosition;
            this.field_73590_a[var4] = var6.chunkExistFlag;
            this.field_73588_b[var4] = var6.chunkHasAddSectionFlag;
            this.field_73584_f[var4] = var6.compressedData;
        }
        final Deflater var8 = new Deflater(-1);
        try {
            var8.setInput(Packet56MapChunks.chunkDataNotCompressed, 0, var3);
            var8.finish();
            this.chunkDataBuffer = new byte[var3];
            this.dataLength = var8.deflate(this.chunkDataBuffer);
        }
        finally {
            var8.end();
        }
        var8.end();
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        final short var2 = par1DataInputStream.readShort();
        this.dataLength = par1DataInputStream.readInt();
        this.skyLightSent = par1DataInputStream.readBoolean();
        this.chunkPostX = new int[var2];
        this.chunkPosZ = new int[var2];
        this.field_73590_a = new int[var2];
        this.field_73588_b = new int[var2];
        this.field_73584_f = new byte[var2][];
        if (Packet56MapChunks.chunkDataNotCompressed.length < this.dataLength) {
            Packet56MapChunks.chunkDataNotCompressed = new byte[this.dataLength];
        }
        par1DataInputStream.readFully(Packet56MapChunks.chunkDataNotCompressed, 0, this.dataLength);
        final byte[] var3 = new byte[196864 * var2];
        final Inflater var4 = new Inflater();
        var4.setInput(Packet56MapChunks.chunkDataNotCompressed, 0, this.dataLength);
        try {
            var4.inflate(var3);
        }
        catch (DataFormatException var10) {
            throw new IOException("Bad compressed data format");
        }
        finally {
            var4.end();
        }
        var4.end();
        int var5 = 0;
        for (int var6 = 0; var6 < var2; ++var6) {
            this.chunkPostX[var6] = par1DataInputStream.readInt();
            this.chunkPosZ[var6] = par1DataInputStream.readInt();
            this.field_73590_a[var6] = par1DataInputStream.readShort();
            this.field_73588_b[var6] = par1DataInputStream.readShort();
            int var7 = 0;
            int var8 = 0;
            for (int var9 = 0; var9 < 16; ++var9) {
                var7 += (this.field_73590_a[var6] >> var9 & 0x1);
                var8 += (this.field_73588_b[var6] >> var9 & 0x1);
            }
            int var9 = 8192 * var7 + 256;
            var9 += 2048 * var8;
            if (this.skyLightSent) {
                var9 += 2048 * var7;
            }
            System.arraycopy(var3, var5, this.field_73584_f[var6] = new byte[var9], 0, var9);
            var5 += var9;
        }
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeShort(this.chunkPostX.length);
        par1DataOutputStream.writeInt(this.dataLength);
        par1DataOutputStream.writeBoolean(this.skyLightSent);
        par1DataOutputStream.write(this.chunkDataBuffer, 0, this.dataLength);
        for (int var2 = 0; var2 < this.chunkPostX.length; ++var2) {
            par1DataOutputStream.writeInt(this.chunkPostX[var2]);
            par1DataOutputStream.writeInt(this.chunkPosZ[var2]);
            par1DataOutputStream.writeShort((short)(this.field_73590_a[var2] & 0xFFFF));
            par1DataOutputStream.writeShort((short)(this.field_73588_b[var2] & 0xFFFF));
        }
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleMapChunks(this);
    }
    
    @Override
    public int getPacketSize() {
        return 6 + this.dataLength + 12 * this.getNumberOfChunkInPacket();
    }
    
    public int getChunkPosX(final int par1) {
        return this.chunkPostX[par1];
    }
    
    public int getChunkPosZ(final int par1) {
        return this.chunkPosZ[par1];
    }
    
    public int getNumberOfChunkInPacket() {
        return this.chunkPostX.length;
    }
    
    public byte[] getChunkCompressedData(final int par1) {
        return this.field_73584_f[par1];
    }
}
