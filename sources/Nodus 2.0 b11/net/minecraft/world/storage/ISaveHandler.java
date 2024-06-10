package net.minecraft.world.storage;

import java.io.File;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;

public abstract interface ISaveHandler
{
  public abstract WorldInfo loadWorldInfo();
  
  public abstract void checkSessionLock()
    throws MinecraftException;
  
  public abstract IChunkLoader getChunkLoader(WorldProvider paramWorldProvider);
  
  public abstract void saveWorldInfoWithPlayer(WorldInfo paramWorldInfo, NBTTagCompound paramNBTTagCompound);
  
  public abstract void saveWorldInfo(WorldInfo paramWorldInfo);
  
  public abstract IPlayerFileData getSaveHandler();
  
  public abstract void flush();
  
  public abstract File getWorldDirectory();
  
  public abstract File getMapFileFromName(String paramString);
  
  public abstract String getWorldDirectoryName();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.storage.ISaveHandler
 * JD-Core Version:    0.7.0.1
 */