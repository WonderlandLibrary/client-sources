package net.minecraft.src;

import java.util.*;
import java.io.*;
import java.util.zip.*;

public class RegionFile
{
    private static final byte[] emptySector;
    private final File fileName;
    private RandomAccessFile dataFile;
    private final int[] offsets;
    private final int[] chunkTimestamps;
    private ArrayList sectorFree;
    private int sizeDelta;
    private long lastModified;
    
    static {
        emptySector = new byte[4096];
    }
    
    public RegionFile(final File par1File) {
        this.offsets = new int[1024];
        this.chunkTimestamps = new int[1024];
        this.lastModified = 0L;
        this.fileName = par1File;
        this.sizeDelta = 0;
        try {
            if (par1File.exists()) {
                this.lastModified = par1File.lastModified();
            }
            this.dataFile = new RandomAccessFile(par1File, "rw");
            if (this.dataFile.length() < 4096L) {
                for (int var2 = 0; var2 < 1024; ++var2) {
                    this.dataFile.writeInt(0);
                }
                for (int var2 = 0; var2 < 1024; ++var2) {
                    this.dataFile.writeInt(0);
                }
                this.sizeDelta += 8192;
            }
            if ((this.dataFile.length() & 0xFFFL) != 0x0L) {
                for (int var2 = 0; var2 < (this.dataFile.length() & 0xFFFL); ++var2) {
                    this.dataFile.write(0);
                }
            }
            int var2 = (int)this.dataFile.length() / 4096;
            this.sectorFree = new ArrayList(var2);
            for (int var3 = 0; var3 < var2; ++var3) {
                this.sectorFree.add(true);
            }
            this.sectorFree.set(0, false);
            this.sectorFree.set(1, false);
            this.dataFile.seek(0L);
            for (int var3 = 0; var3 < 1024; ++var3) {
                final int var4 = this.dataFile.readInt();
                this.offsets[var3] = var4;
                if (var4 != 0 && (var4 >> 8) + (var4 & 0xFF) <= this.sectorFree.size()) {
                    for (int var5 = 0; var5 < (var4 & 0xFF); ++var5) {
                        this.sectorFree.set((var4 >> 8) + var5, false);
                    }
                }
            }
            for (int var3 = 0; var3 < 1024; ++var3) {
                final int var4 = this.dataFile.readInt();
                this.chunkTimestamps[var3] = var4;
            }
        }
        catch (IOException var6) {
            var6.printStackTrace();
        }
    }
    
    public synchronized DataInputStream getChunkDataInputStream(final int par1, final int par2) {
        if (this.outOfBounds(par1, par2)) {
            return null;
        }
        try {
            final int var3 = this.getOffset(par1, par2);
            if (var3 == 0) {
                return null;
            }
            final int var4 = var3 >> 8;
            final int var5 = var3 & 0xFF;
            if (var4 + var5 > this.sectorFree.size()) {
                return null;
            }
            this.dataFile.seek(var4 * 4096);
            final int var6 = this.dataFile.readInt();
            if (var6 > 4096 * var5) {
                return null;
            }
            if (var6 <= 0) {
                return null;
            }
            final byte var7 = this.dataFile.readByte();
            if (var7 == 1) {
                final byte[] var8 = new byte[var6 - 1];
                this.dataFile.read(var8);
                return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(var8))));
            }
            if (var7 == 2) {
                final byte[] var8 = new byte[var6 - 1];
                this.dataFile.read(var8);
                return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(var8))));
            }
            return null;
        }
        catch (IOException var9) {
            return null;
        }
    }
    
    public DataOutputStream getChunkDataOutputStream(final int par1, final int par2) {
        DataOutputStream dataOutputStream;
        if (this.outOfBounds(par1, par2)) {
            dataOutputStream = null;
        }
        else {
            final DeflaterOutputStream deflaterOutputStream;
            dataOutputStream = new DataOutputStream(deflaterOutputStream);
            deflaterOutputStream = new DeflaterOutputStream(new RegionFileChunkBuffer(this, par1, par2));
        }
        return dataOutputStream;
    }
    
    protected synchronized void write(final int par1, final int par2, final byte[] par3ArrayOfByte, final int par4) {
        try {
            final int var5 = this.getOffset(par1, par2);
            int var6 = var5 >> 8;
            final int var7 = var5 & 0xFF;
            final int var8 = (par4 + 5) / 4096 + 1;
            if (var8 >= 256) {
                return;
            }
            if (var6 != 0 && var7 == var8) {
                this.write(var6, par3ArrayOfByte, par4);
            }
            else {
                for (int var9 = 0; var9 < var7; ++var9) {
                    this.sectorFree.set(var6 + var9, true);
                }
                int var9 = this.sectorFree.indexOf(true);
                int var10 = 0;
                if (var9 != -1) {
                    for (int var11 = var9; var11 < this.sectorFree.size(); ++var11) {
                        if (var10 != 0) {
                            if (this.sectorFree.get(var11)) {
                                ++var10;
                            }
                            else {
                                var10 = 0;
                            }
                        }
                        else if (this.sectorFree.get(var11)) {
                            var9 = var11;
                            var10 = 1;
                        }
                        if (var10 >= var8) {
                            break;
                        }
                    }
                }
                if (var10 >= var8) {
                    var6 = var9;
                    this.setOffset(par1, par2, var9 << 8 | var8);
                    for (int var11 = 0; var11 < var8; ++var11) {
                        this.sectorFree.set(var6 + var11, false);
                    }
                    this.write(var6, par3ArrayOfByte, par4);
                }
                else {
                    this.dataFile.seek(this.dataFile.length());
                    var6 = this.sectorFree.size();
                    for (int var11 = 0; var11 < var8; ++var11) {
                        this.dataFile.write(RegionFile.emptySector);
                        this.sectorFree.add(false);
                    }
                    this.sizeDelta += 4096 * var8;
                    this.write(var6, par3ArrayOfByte, par4);
                    this.setOffset(par1, par2, var6 << 8 | var8);
                }
            }
            this.setChunkTimestamp(par1, par2, (int)(System.currentTimeMillis() / 1000L));
        }
        catch (IOException var12) {
            var12.printStackTrace();
        }
    }
    
    private void write(final int par1, final byte[] par2ArrayOfByte, final int par3) throws IOException {
        this.dataFile.seek(par1 * 4096);
        this.dataFile.writeInt(par3 + 1);
        this.dataFile.writeByte(2);
        this.dataFile.write(par2ArrayOfByte, 0, par3);
    }
    
    private boolean outOfBounds(final int par1, final int par2) {
        return par1 < 0 || par1 >= 32 || par2 < 0 || par2 >= 32;
    }
    
    private int getOffset(final int par1, final int par2) {
        return this.offsets[par1 + par2 * 32];
    }
    
    public boolean isChunkSaved(final int par1, final int par2) {
        return this.getOffset(par1, par2) != 0;
    }
    
    private void setOffset(final int par1, final int par2, final int par3) throws IOException {
        this.offsets[par1 + par2 * 32] = par3;
        this.dataFile.seek((par1 + par2 * 32) * 4);
        this.dataFile.writeInt(par3);
    }
    
    private void setChunkTimestamp(final int par1, final int par2, final int par3) throws IOException {
        this.chunkTimestamps[par1 + par2 * 32] = par3;
        this.dataFile.seek(4096 + (par1 + par2 * 32) * 4);
        this.dataFile.writeInt(par3);
    }
    
    public void close() throws IOException {
        if (this.dataFile != null) {
            this.dataFile.close();
        }
    }
}
