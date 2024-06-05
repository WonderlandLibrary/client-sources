package net.minecraft.src;

import java.util.*;
import java.io.*;

public class RegionFileCache
{
    private static final Map regionsByFilename;
    
    static {
        regionsByFilename = new HashMap();
    }
    
    public static synchronized RegionFile createOrLoadRegionFile(final File par0File, final int par1, final int par2) {
        final File var3 = new File(par0File, "region");
        final File var4 = new File(var3, "r." + (par1 >> 5) + "." + (par2 >> 5) + ".mca");
        final RegionFile var5 = RegionFileCache.regionsByFilename.get(var4);
        if (var5 != null) {
            return var5;
        }
        if (!var3.exists()) {
            var3.mkdirs();
        }
        if (RegionFileCache.regionsByFilename.size() >= 256) {
            clearRegionFileReferences();
        }
        final RegionFile var6 = new RegionFile(var4);
        RegionFileCache.regionsByFilename.put(var4, var6);
        return var6;
    }
    
    public static synchronized void clearRegionFileReferences() {
        for (final RegionFile var2 : RegionFileCache.regionsByFilename.values()) {
            try {
                if (var2 == null) {
                    continue;
                }
                var2.close();
            }
            catch (IOException var3) {
                var3.printStackTrace();
            }
        }
        RegionFileCache.regionsByFilename.clear();
    }
    
    public static DataInputStream getChunkInputStream(final File par0File, final int par1, final int par2) {
        final RegionFile var3 = createOrLoadRegionFile(par0File, par1, par2);
        return var3.getChunkDataInputStream(par1 & 0x1F, par2 & 0x1F);
    }
    
    public static DataOutputStream getChunkOutputStream(final File par0File, final int par1, final int par2) {
        final RegionFile var3 = createOrLoadRegionFile(par0File, par1, par2);
        return var3.getChunkDataOutputStream(par1 & 0x1F, par2 & 0x1F);
    }
}
