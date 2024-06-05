package net.minecraft.world.chunk.storage;

import com.google.common.collect.Lists;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import net.minecraft.world.chunk.storage.RegionFile$ChunkBuffer;

public class RegionFile {
   private static final byte[] emptySector = new byte[4096];
   private final File fileName;
   private RandomAccessFile dataFile;
   private final int[] offsets = new int[1024];
   private final int[] chunkTimestamps = new int[1024];
   private List sectorFree;
   private int sizeDelta;
   private long lastModified;

   public RegionFile(File var1) {
      this.fileName = var1;
      this.sizeDelta = 0;

      try {
         if(var1.exists()) {
            this.lastModified = var1.lastModified();
         }

         this.dataFile = new RandomAccessFile(var1, "rw");
         if(this.dataFile.length() < 4096L) {
            for(int var2 = 0; var2 < 1024; ++var2) {
               this.dataFile.writeInt(0);
            }

            for(int var7 = 0; var7 < 1024; ++var7) {
               this.dataFile.writeInt(0);
            }

            this.sizeDelta += 8192;
         }

         if((this.dataFile.length() & 4095L) != 0L) {
            for(int var8 = 0; (long)var8 < (this.dataFile.length() & 4095L); ++var8) {
               this.dataFile.write(0);
            }
         }

         int var9 = (int)this.dataFile.length() / 4096;
         this.sectorFree = Lists.newArrayListWithCapacity(var9);

         for(int var3 = 0; var3 < var9; ++var3) {
            this.sectorFree.add(Boolean.TRUE);
         }

         this.sectorFree.set(0, Boolean.FALSE);
         this.sectorFree.set(1, Boolean.FALSE);
         this.dataFile.seek(0L);

         for(int var10 = 0; var10 < 1024; ++var10) {
            int var4 = this.dataFile.readInt();
            this.offsets[var10] = var4;
            if((var4 >> 8) + (var4 & 255) <= this.sectorFree.size()) {
               for(int var5 = 0; var5 < (var4 & 255); ++var5) {
                  this.sectorFree.set((var4 >> 8) + var5, Boolean.FALSE);
               }
            }
         }

         for(int var11 = 0; var11 < 1024; ++var11) {
            int var12 = this.dataFile.readInt();
            this.chunkTimestamps[var11] = var12;
         }
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   public synchronized DataInputStream c(int var1, int var2) {
      if(this.outOfBounds(var1, var2)) {
         return null;
      } else {
         RegionFile var10000 = this;
         int var10001 = var1;
         int var10002 = var2;

         try {
            int var3 = var10000.getOffset(var10001, var10002);
            return null;
         } catch (IOException var4) {
            return null;
         }
      }
   }

   public DataOutputStream getChunkDataOutputStream(int var1, int var2) {
      return this.outOfBounds(var1, var2)?null:new DataOutputStream(new DeflaterOutputStream(new RegionFile$ChunkBuffer(this, var1, var2)));
   }

   protected synchronized void write(int param1, int param2, byte[] param3, int param4) {
      // $FF: Couldn't be decompiled
   }

   private void write(int var1, byte[] var2, int var3) throws IOException {
      this.dataFile.seek((long)(var1 * 4096));
      this.dataFile.writeInt(var3 + 1);
      this.dataFile.writeByte(2);
      this.dataFile.write(var2, 0, var3);
   }

   private boolean outOfBounds(int var1, int var2) {
      return var1 >= 32 || var2 >= 32;
   }

   private int getOffset(int var1, int var2) {
      return this.offsets[var1 + var2 * 32];
   }

   public boolean isChunkSaved(int var1, int var2) {
      return this.getOffset(var1, var2) != 0;
   }

   private void setOffset(int var1, int var2, int var3) throws IOException {
      this.offsets[var1 + var2 * 32] = var3;
      this.dataFile.seek((long)((var1 + var2 * 32) * 4));
      this.dataFile.writeInt(var3);
   }

   private void setChunkTimestamp(int var1, int var2, int var3) throws IOException {
      this.chunkTimestamps[var1 + var2 * 32] = var3;
      this.dataFile.seek((long)(4096 + (var1 + var2 * 32) * 4));
      this.dataFile.writeInt(var3);
   }

   public void close() throws IOException {
      if(this.dataFile != null) {
         this.dataFile.close();
      }

   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
