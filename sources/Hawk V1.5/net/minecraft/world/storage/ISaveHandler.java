package net.minecraft.world.storage;

import java.io.File;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;

public interface ISaveHandler {
   IChunkLoader getChunkLoader(WorldProvider var1);

   void saveWorldInfo(WorldInfo var1);

   void flush();

   File getWorldDirectory();

   String getWorldDirectoryName();

   void checkSessionLock() throws MinecraftException;

   void saveWorldInfoWithPlayer(WorldInfo var1, NBTTagCompound var2);

   WorldInfo loadWorldInfo();

   IPlayerFileData getPlayerNBTManager();

   File getMapFileFromName(String var1);
}
