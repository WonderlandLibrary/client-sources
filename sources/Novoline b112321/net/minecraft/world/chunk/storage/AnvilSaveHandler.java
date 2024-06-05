package net.minecraft.world.chunk.storage;

import java.io.File;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.chunk.storage.RegionFileCache;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.ThreadedFileIOBase;
import net.minecraft.world.storage.WorldInfo;

public class AnvilSaveHandler extends SaveHandler {
   public AnvilSaveHandler(File var1, String var2, boolean var3) {
      super(var1, var2, var3);
   }

   public IChunkLoader getChunkLoader(WorldProvider var1) {
      File var2 = this.getWorldDirectory();
      if(var1 instanceof WorldProviderHell) {
         File var4 = new File(var2, "DIM-1");
         var4.mkdirs();
         return new AnvilChunkLoader(var4);
      } else if(var1 instanceof WorldProviderEnd) {
         File var3 = new File(var2, "DIM1");
         var3.mkdirs();
         return new AnvilChunkLoader(var3);
      } else {
         return new AnvilChunkLoader(var2);
      }
   }

   public void saveWorldInfoWithPlayer(WorldInfo var1, NBTTagCompound var2) {
      var1.setSaveVersion(19133);
      super.saveWorldInfoWithPlayer(var1, var2);
   }

   public void flush() {
      try {
         ThreadedFileIOBase.getThreadedIOInstance().waitForFinish();
      } catch (InterruptedException var2) {
         var2.printStackTrace();
      }

      RegionFileCache.clearRegionFileReferences();
   }
}
