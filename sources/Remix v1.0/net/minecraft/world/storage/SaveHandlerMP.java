package net.minecraft.world.storage;

import net.minecraft.world.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.nbt.*;
import java.io.*;

public class SaveHandlerMP implements ISaveHandler
{
    @Override
    public WorldInfo loadWorldInfo() {
        return null;
    }
    
    @Override
    public void checkSessionLock() throws MinecraftException {
    }
    
    @Override
    public IChunkLoader getChunkLoader(final WorldProvider p_75763_1_) {
        return null;
    }
    
    @Override
    public void saveWorldInfoWithPlayer(final WorldInfo p_75755_1_, final NBTTagCompound p_75755_2_) {
    }
    
    @Override
    public void saveWorldInfo(final WorldInfo p_75761_1_) {
    }
    
    @Override
    public IPlayerFileData getPlayerNBTManager() {
        return null;
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public File getMapFileFromName(final String p_75758_1_) {
        return null;
    }
    
    @Override
    public String getWorldDirectoryName() {
        return "none";
    }
    
    @Override
    public File getWorldDirectory() {
        return null;
    }
}
