package net.minecraft.world.chunk.storage;

import net.minecraft.nbt.*;
import java.io.*;
import net.minecraft.world.storage.*;
import net.minecraft.world.*;

public class AnvilSaveHandler extends SaveHandler
{
    private static final String[] I;
    
    static {
        I();
    }
    
    @Override
    public void saveWorldInfoWithPlayer(final WorldInfo worldInfo, final NBTTagCompound nbtTagCompound) {
        worldInfo.setSaveVersion(12526 + 16163 - 9653 + 97);
        super.saveWorldInfoWithPlayer(worldInfo, nbtTagCompound);
    }
    
    public AnvilSaveHandler(final File file, final String s, final boolean b) {
        super(file, s, b);
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
            if (1 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u001d-\u001dz_", "YdPWn");
        AnvilSaveHandler.I[" ".length()] = I(")\u0013\u001aE", "mZWtr");
    }
    
    @Override
    public void flush() {
        try {
            ThreadedFileIOBase.getThreadedIOInstance().waitForFinish();
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        RegionFileCache.clearRegionFileReferences();
    }
    
    @Override
    public IChunkLoader getChunkLoader(final WorldProvider worldProvider) {
        final File worldDirectory = this.getWorldDirectory();
        if (worldProvider instanceof WorldProviderHell) {
            final File file = new File(worldDirectory, AnvilSaveHandler.I["".length()]);
            file.mkdirs();
            return new AnvilChunkLoader(file);
        }
        if (worldProvider instanceof WorldProviderEnd) {
            final File file2 = new File(worldDirectory, AnvilSaveHandler.I[" ".length()]);
            file2.mkdirs();
            return new AnvilChunkLoader(file2);
        }
        return new AnvilChunkLoader(worldDirectory);
    }
}
