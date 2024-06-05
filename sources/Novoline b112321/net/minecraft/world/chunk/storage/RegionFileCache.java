package net.minecraft.world.chunk.storage;

import com.google.common.collect.Maps;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import net.minecraft.world.chunk.storage.RegionFile;

public class RegionFileCache {
   private static final Map regionsByFilename = Maps.newHashMap();

   public static synchronized RegionFile createOrLoadRegionFile(File var0, int var1, int var2) {
      File var3 = new File(var0, "region");
      File var4 = new File(var3, "r." + (var1 >> 5) + "." + (var2 >> 5) + ".mca");
      RegionFile var5 = (RegionFile)regionsByFilename.get(var4);
      return var5;
   }

   public static synchronized void clearRegionFileReferences() {
      for(RegionFile var1 : regionsByFilename.values()) {
         RegionFile var10000 = var1;

         try {
            var10000.close();
         } catch (IOException var3) {
            var3.printStackTrace();
         }
      }

      regionsByFilename.clear();
   }

   public static DataInputStream getChunkInputStream(File var0, int var1, int var2) {
      RegionFile var3 = createOrLoadRegionFile(var0, var1, var2);
      return var3.c(var1 & 31, var2 & 31);
   }

   public static DataOutputStream getChunkOutputStream(File var0, int var1, int var2) {
      RegionFile var3 = createOrLoadRegionFile(var0, var1, var2);
      return var3.getChunkDataOutputStream(var1 & 31, var2 & 31);
   }
}
