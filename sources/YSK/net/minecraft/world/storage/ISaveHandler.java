package net.minecraft.world.storage;

import java.io.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.storage.*;

public interface ISaveHandler
{
    File getMapFileFromName(final String p0);
    
    void saveWorldInfoWithPlayer(final WorldInfo p0, final NBTTagCompound p1);
    
    WorldInfo loadWorldInfo();
    
    String getWorldDirectoryName();
    
    void checkSessionLock() throws MinecraftException;
    
    IChunkLoader getChunkLoader(final WorldProvider p0);
    
    File getWorldDirectory();
    
    void flush();
    
    IPlayerFileData getPlayerNBTManager();
    
    void saveWorldInfo(final WorldInfo p0);
}
