package net.minecraft.world.chunk.storage;

import com.google.common.collect.*;
import java.io.*;
import java.util.*;

public class RegionFileCache
{
    private static final String[] I;
    private static final Map<File, RegionFile> regionsByFilename;
    
    public static DataOutputStream getChunkOutputStream(final File file, final int n, final int n2) {
        return createOrLoadRegionFile(file, n, n2).getChunkDataOutputStream(n & (0x43 ^ 0x5C), n2 & (0x92 ^ 0x8D));
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
            if (3 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static synchronized RegionFile createOrLoadRegionFile(final File file, final int n, final int n2) {
        final File file2 = new File(file, RegionFileCache.I["".length()]);
        final File file3 = new File(file2, RegionFileCache.I[" ".length()] + (n >> (0x3C ^ 0x39)) + RegionFileCache.I["  ".length()] + (n2 >> (0x9B ^ 0x9E)) + RegionFileCache.I["   ".length()]);
        final RegionFile regionFile = RegionFileCache.regionsByFilename.get(file3);
        if (regionFile != null) {
            return regionFile;
        }
        if (!file2.exists()) {
            file2.mkdirs();
        }
        if (RegionFileCache.regionsByFilename.size() >= 6 + 219 + 19 + 12) {
            clearRegionFileReferences();
        }
        final RegionFile regionFile2 = new RegionFile(file3);
        RegionFileCache.regionsByFilename.put(file3, regionFile2);
        return regionFile2;
    }
    
    static {
        I();
        regionsByFilename = Maps.newHashMap();
    }
    
    private static void I() {
        (I = new String[0x9B ^ 0x9F])["".length()] = I(">+\u0017 \t\"", "LNpIf");
        RegionFileCache.I[" ".length()] = I("\u001dY", "owlpu");
        RegionFileCache.I["  ".length()] = I("A", "ouTfl");
        RegionFileCache.I["   ".length()] = I("y\u0018\u0007\t", "Wudhy");
    }
    
    public static DataInputStream getChunkInputStream(final File file, final int n, final int n2) {
        return createOrLoadRegionFile(file, n, n2).getChunkDataInputStream(n & (0xBB ^ 0xA4), n2 & (0x2E ^ 0x31));
    }
    
    public static synchronized void clearRegionFileReferences() {
        final Iterator<RegionFile> iterator = RegionFileCache.regionsByFilename.values().iterator();
        "".length();
        if (0 == -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final RegionFile regionFile = iterator.next();
            try {
                if (regionFile == null) {
                    continue;
                }
                regionFile.close();
                "".length();
                if (3 == 1) {
                    throw null;
                }
                continue;
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        RegionFileCache.regionsByFilename.clear();
    }
}
