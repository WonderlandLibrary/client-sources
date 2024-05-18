package net.minecraft.src;

import java.io.*;

class RegionFileChunkBuffer extends ByteArrayOutputStream
{
    private int chunkX;
    private int chunkZ;
    final RegionFile regionFile;
    
    public RegionFileChunkBuffer(final RegionFile par1RegionFile, final int par2, final int par3) {
        super(8096);
        this.regionFile = par1RegionFile;
        this.chunkX = par2;
        this.chunkZ = par3;
    }
    
    @Override
    public void close() throws IOException {
        this.regionFile.write(this.chunkX, this.chunkZ, this.buf, this.count);
    }
}
