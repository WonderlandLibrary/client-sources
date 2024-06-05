package net.minecraft.src;

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
    public IChunkLoader getChunkLoader(final WorldProvider par1WorldProvider) {
        return null;
    }
    
    @Override
    public void saveWorldInfoWithPlayer(final WorldInfo par1WorldInfo, final NBTTagCompound par2NBTTagCompound) {
    }
    
    @Override
    public void saveWorldInfo(final WorldInfo par1WorldInfo) {
    }
    
    @Override
    public IPlayerFileData getSaveHandler() {
        return null;
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public File getMapFileFromName(final String par1Str) {
        return null;
    }
    
    @Override
    public String getWorldDirectoryName() {
        return "none";
    }
}
