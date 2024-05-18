package net.minecraft.src;

import java.io.*;

public interface ISaveHandler
{
    WorldInfo loadWorldInfo();
    
    void checkSessionLock() throws MinecraftException;
    
    IChunkLoader getChunkLoader(final WorldProvider p0);
    
    void saveWorldInfoWithPlayer(final WorldInfo p0, final NBTTagCompound p1);
    
    void saveWorldInfo(final WorldInfo p0);
    
    IPlayerFileData getSaveHandler();
    
    void flush();
    
    File getMapFileFromName(final String p0);
    
    String getWorldDirectoryName();
}
