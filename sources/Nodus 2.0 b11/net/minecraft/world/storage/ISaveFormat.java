package net.minecraft.world.storage;

import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.util.IProgressUpdate;

public abstract interface ISaveFormat
{
  public abstract ISaveHandler getSaveLoader(String paramString, boolean paramBoolean);
  
  public abstract List getSaveList()
    throws AnvilConverterException;
  
  public abstract void flushCache();
  
  public abstract WorldInfo getWorldInfo(String paramString);
  
  public abstract boolean deleteWorldDirectory(String paramString);
  
  public abstract void renameWorld(String paramString1, String paramString2);
  
  public abstract boolean isOldMapFormat(String paramString);
  
  public abstract boolean convertMapFormat(String paramString, IProgressUpdate paramIProgressUpdate);
  
  public abstract boolean canLoadWorld(String paramString);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.storage.ISaveFormat
 * JD-Core Version:    0.7.0.1
 */