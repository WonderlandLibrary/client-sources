package net.minecraft.world.chunk.storage;

import java.io.*;

class ChunkBuffer extends ByteArrayOutputStream
{
    private int chunkX;
    private int chunkZ;
    
    public ChunkBuffer(final int p_i2000_2_, final int p_i2000_3_) {
        super(8096);
        this.chunkX = p_i2000_2_;
        this.chunkZ = p_i2000_3_;
    }
    
    @Override
    public void close() throws IOException {
        RegionFile.this.write(this.chunkX, this.chunkZ, this.buf, this.count);
    }
}
