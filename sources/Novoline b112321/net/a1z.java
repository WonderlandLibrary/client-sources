package net;

import java.io.File;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class a1z implements ISaveHandler {
   public WorldInfo loadWorldInfo() {
      return null;
   }

   public void checkSessionLock() {
   }

   public IChunkLoader getChunkLoader(WorldProvider var1) {
      return null;
   }

   public void saveWorldInfoWithPlayer(WorldInfo var1, NBTTagCompound var2) {
   }

   public void saveWorldInfo(WorldInfo var1) {
   }

   public IPlayerFileData getPlayerNBTManager() {
      return null;
   }

   public void flush() {
   }

   public File getWorldDirectory() {
      return null;
   }

   public File getMapFileFromName(String var1) {
      return null;
   }

   public String getWorldDirectoryName() {
      return null;
   }
}
