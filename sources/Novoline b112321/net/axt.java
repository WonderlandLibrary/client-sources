package net;

import java.io.File;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class axt {
   public static WorldInfo d(ISaveHandler var0) {
      return var0.loadWorldInfo();
   }

   public static void a(ISaveHandler var0, WorldInfo var1) {
      var0.saveWorldInfo(var1);
   }

   public static File a(ISaveHandler var0, String var1) {
      return var0.getMapFileFromName(var1);
   }

   public static IChunkLoader a(ISaveHandler var0, WorldProvider var1) {
      return var0.getChunkLoader(var1);
   }

   public static void a(ISaveHandler var0, WorldInfo var1, NBTTagCompound var2) {
      var0.saveWorldInfoWithPlayer(var1, var2);
   }

   public static IPlayerFileData b(ISaveHandler var0) {
      return var0.getPlayerNBTManager();
   }

   public static File e(ISaveHandler var0) {
      return var0.getWorldDirectory();
   }

   public static String c(ISaveHandler var0) {
      return var0.getWorldDirectoryName();
   }

   public static void a(ISaveHandler var0) {
      var0.flush();
   }
}
