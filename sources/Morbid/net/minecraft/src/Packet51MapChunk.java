package net.minecraft.src;

import java.util.zip.*;
import java.io.*;

public class Packet51MapChunk extends Packet
{
    public int xCh;
    public int zCh;
    public int yChMin;
    public int yChMax;
    private byte[] chunkData;
    private byte[] compressedChunkData;
    public boolean includeInitialize;
    private int tempLength;
    private static byte[] temp;
    
    static {
        Packet51MapChunk.temp = new byte[196864];
    }
    
    public Packet51MapChunk() {
        this.isChunkDataPacket = true;
    }
    
    public Packet51MapChunk(final Chunk par1Chunk, final boolean par2, final int par3) {
        this.isChunkDataPacket = true;
        this.xCh = par1Chunk.xPosition;
        this.zCh = par1Chunk.zPosition;
        this.includeInitialize = par2;
        final Packet51MapChunkData var4 = getMapChunkData(par1Chunk, par2, par3);
        final Deflater var5 = new Deflater(-1);
        this.yChMax = var4.chunkHasAddSectionFlag;
        this.yChMin = var4.chunkExistFlag;
        try {
            this.compressedChunkData = var4.compressedData;
            var5.setInput(var4.compressedData, 0, var4.compressedData.length);
            var5.finish();
            this.chunkData = new byte[var4.compressedData.length];
            this.tempLength = var5.deflate(this.chunkData);
        }
        finally {
            var5.end();
        }
        var5.end();
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.xCh = par1DataInputStream.readInt();
        this.zCh = par1DataInputStream.readInt();
        this.includeInitialize = par1DataInputStream.readBoolean();
        this.yChMin = par1DataInputStream.readShort();
        this.yChMax = par1DataInputStream.readShort();
        this.tempLength = par1DataInputStream.readInt();
        if (Packet51MapChunk.temp.length < this.tempLength) {
            Packet51MapChunk.temp = new byte[this.tempLength];
        }
        par1DataInputStream.readFully(Packet51MapChunk.temp, 0, this.tempLength);
        int var2 = 0;
        for (int var3 = 0; var3 < 16; ++var3) {
            var2 += (this.yChMin >> var3 & 0x1);
        }
        int var3 = 12288 * var2;
        if (this.includeInitialize) {
            var3 += 256;
        }
        this.compressedChunkData = new byte[var3];
        final Inflater var4 = new Inflater();
        var4.setInput(Packet51MapChunk.temp, 0, this.tempLength);
        try {
            var4.inflate(this.compressedChunkData);
        }
        catch (DataFormatException var5) {
            throw new IOException("Bad compressed data format");
        }
        finally {
            var4.end();
        }
        var4.end();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.xCh);
        par1DataOutputStream.writeInt(this.zCh);
        par1DataOutputStream.writeBoolean(this.includeInitialize);
        par1DataOutputStream.writeShort((short)(this.yChMin & 0xFFFF));
        par1DataOutputStream.writeShort((short)(this.yChMax & 0xFFFF));
        par1DataOutputStream.writeInt(this.tempLength);
        par1DataOutputStream.write(this.chunkData, 0, this.tempLength);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleMapChunk(this);
    }
    
    @Override
    public int getPacketSize() {
        return 17 + this.tempLength;
    }
    
    public byte[] getCompressedChunkData() {
        return this.compressedChunkData;
    }
    
    public static Packet51MapChunkData getMapChunkData(final Chunk par0Chunk, final boolean par1, final int par2) {
        int var3 = 0;
        final ExtendedBlockStorage[] var4 = par0Chunk.getBlockStorageArray();
        int var5 = 0;
        final Packet51MapChunkData var6 = new Packet51MapChunkData();
        final byte[] var7 = Packet51MapChunk.temp;
        if (par1) {
            par0Chunk.sendUpdates = true;
        }
        for (int var8 = 0; var8 < var4.length; ++var8) {
            if (var4[var8] != null && (!par1 || !var4[var8].isEmpty()) && (par2 & 1 << var8) != 0x0) {
                final Packet51MapChunkData packet51MapChunkData = var6;
                packet51MapChunkData.chunkExistFlag |= 1 << var8;
                if (var4[var8].getBlockMSBArray() != null) {
                    final Packet51MapChunkData packet51MapChunkData2 = var6;
                    packet51MapChunkData2.chunkHasAddSectionFlag |= 1 << var8;
                    ++var5;
                }
            }
        }
        for (int var8 = 0; var8 < var4.length; ++var8) {
            if (var4[var8] != null && (!par1 || !var4[var8].isEmpty()) && (par2 & 1 << var8) != 0x0) {
                final byte[] var9 = var4[var8].getBlockLSBArray();
                System.arraycopy(var9, 0, var7, var3, var9.length);
                var3 += var9.length;
            }
        }
        for (int var8 = 0; var8 < var4.length; ++var8) {
            if (var4[var8] != null && (!par1 || !var4[var8].isEmpty()) && (par2 & 1 << var8) != 0x0) {
                final NibbleArray var10 = var4[var8].getMetadataArray();
                System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
                var3 += var10.data.length;
            }
        }
        for (int var8 = 0; var8 < var4.length; ++var8) {
            if (var4[var8] != null && (!par1 || !var4[var8].isEmpty()) && (par2 & 1 << var8) != 0x0) {
                final NibbleArray var10 = var4[var8].getBlocklightArray();
                System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
                var3 += var10.data.length;
            }
        }
        if (!par0Chunk.worldObj.provider.hasNoSky) {
            for (int var8 = 0; var8 < var4.length; ++var8) {
                if (var4[var8] != null && (!par1 || !var4[var8].isEmpty()) && (par2 & 1 << var8) != 0x0) {
                    final NibbleArray var10 = var4[var8].getSkylightArray();
                    System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
                    var3 += var10.data.length;
                }
            }
        }
        if (var5 > 0) {
            for (int var8 = 0; var8 < var4.length; ++var8) {
                if (var4[var8] != null && (!par1 || !var4[var8].isEmpty()) && var4[var8].getBlockMSBArray() != null && (par2 & 1 << var8) != 0x0) {
                    final NibbleArray var10 = var4[var8].getBlockMSBArray();
                    System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
                    var3 += var10.data.length;
                }
            }
        }
        if (par1) {
            final byte[] var11 = par0Chunk.getBiomeArray();
            System.arraycopy(var11, 0, var7, var3, var11.length);
            var3 += var11.length;
        }
        System.arraycopy(var7, 0, var6.compressedData = new byte[var3], 0, var3);
        return var6;
    }
}
