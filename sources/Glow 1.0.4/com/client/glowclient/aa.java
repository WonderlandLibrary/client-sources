package com.client.glowclient;

import mcp.*;
import javax.annotation.*;
import net.minecraft.world.gen.structure.template.*;
import java.io.*;
import net.minecraft.nbt.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.world.storage.*;
import net.minecraft.world.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class aA implements ISaveHandler
{
    public TemplateManager getStructureTemplateManager() {
        return null;
    }
    
    public File getMapFileFromName(final String s) {
        return null;
    }
    
    public void saveWorldInfoWithPlayer(final WorldInfo worldInfo, final NBTTagCompound nbtTagCompound) {
    }
    
    public IChunkLoader getChunkLoader(final WorldProvider worldProvider) {
        return null;
    }
    
    public aA() {
        super();
    }
    
    public IPlayerFileData getPlayerNBTManager() {
        return null;
    }
    
    public WorldInfo loadWorldInfo() {
        return null;
    }
    
    public void flush() {
    }
    
    public void saveWorldInfo(final WorldInfo worldInfo) {
    }
    
    public void checkSessionLock() throws MinecraftException {
    }
    
    public File getWorldDirectory() {
        return null;
    }
}
