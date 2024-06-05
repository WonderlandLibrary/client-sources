package net.minecraft.world.chunk.storage;

import com.google.common.collect.Maps;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class RegionFileCache {
   private static final String __OBFID = "CL_00000383";
   private static final Map regionsByFilename = Maps.newHashMap();

   public static DataOutputStream getChunkOutputStream(File var0, int var1, int var2) {
      RegionFile var3 = createOrLoadRegionFile(var0, var1, var2);
      return var3.getChunkDataOutputStream(var1 & 31, var2 & 31);
   }

   public static synchronized void clearRegionFileReferences() {
      Iterator var0 = regionsByFilename.values().iterator();

      while(var0.hasNext()) {
         RegionFile var1 = (RegionFile)var0.next();

         try {
            if (var1 != null) {
               var1.close();
            }
         } catch (IOException var3) {
            var3.printStackTrace();
         }
      }

      regionsByFilename.clear();
   }

   public static synchronized RegionFile createOrLoadRegionFile(File var0, int var1, int var2) {
      File var3 = new File(var0, "region");
      File var4 = new File(var3, String.valueOf((new StringBuilder("r.")).append(var1 >> 5).append(".").append(var2 >> 5).append(".mca")));
      RegionFile var5 = (RegionFile)regionsByFilename.get(var4);
      if (var5 != null) {
         return var5;
      } else {
         if (!var3.exists()) {
            var3.mkdirs();
         }

         if (regionsByFilename.size() >= 256) {
            clearRegionFileReferences();
         }

         RegionFile var6 = new RegionFile(var4);
         regionsByFilename.put(var4, var6);
         return var6;
      }
   }

   public static DataInputStream getChunkInputStream(File var0, int var1, int var2) {
      RegionFile var3 = createOrLoadRegionFile(var0, var1, var2);
      return var3.getChunkDataInputStream(var1 & 31, var2 & 31);
   }
}
