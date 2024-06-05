package net.minecraft.world.chunk.storage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import net.minecraft.world.chunk.storage.RegionFile;

class RegionFile$ChunkBuffer extends ByteArrayOutputStream {
   private int chunkX;
   private int chunkZ;
   final RegionFile this$0;

   public RegionFile$ChunkBuffer(RegionFile var1, int var2, int var3) {
      super(8096);
      this.this$0 = var1;
      this.chunkX = var2;
      this.chunkZ = var3;
   }

   public void close() throws IOException {
      this.this$0.write(this.chunkX, this.chunkZ, this.buf, this.count);
   }
}
