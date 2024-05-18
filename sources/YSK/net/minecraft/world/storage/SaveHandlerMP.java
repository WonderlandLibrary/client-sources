package net.minecraft.world.storage;

import net.minecraft.world.chunk.storage.*;
import java.io.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;

public class SaveHandlerMP implements ISaveHandler
{
    private static final String[] I;
    
    @Override
    public void saveWorldInfo(final WorldInfo worldInfo) {
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u001a\u0003+\u0002", "tlEgI");
    }
    
    @Override
    public IChunkLoader getChunkLoader(final WorldProvider worldProvider) {
        return null;
    }
    
    @Override
    public WorldInfo loadWorldInfo() {
        return null;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public File getWorldDirectory() {
        return null;
    }
    
    static {
        I();
    }
    
    @Override
    public IPlayerFileData getPlayerNBTManager() {
        return null;
    }
    
    @Override
    public void saveWorldInfoWithPlayer(final WorldInfo worldInfo, final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    public String getWorldDirectoryName() {
        return SaveHandlerMP.I["".length()];
    }
    
    @Override
    public File getMapFileFromName(final String s) {
        return null;
    }
    
    @Override
    public void checkSessionLock() throws MinecraftException {
    }
    
    @Override
    public void flush() {
    }
}
