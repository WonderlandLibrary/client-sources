package net.minecraft.world.chunk.storage;

import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import java.io.*;
import com.google.common.collect.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import java.util.*;
import net.minecraft.world.storage.*;
import net.minecraft.client.*;
import org.apache.commons.lang3.*;

public class AnvilSaveConverter extends SaveFormatOld
{
    private static final Logger logger;
    private static final String[] I;
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    private void convertFile(final File file, final Iterable<File> iterable, final WorldChunkManager worldChunkManager, int n, final int n2, final IProgressUpdate progressUpdate) {
        final Iterator<File> iterator = iterable.iterator();
        "".length();
        if (!true) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.convertChunks(file, iterator.next(), worldChunkManager, n, n2, progressUpdate);
            ++n;
            progressUpdate.setLoadingProgress((int)Math.round(100.0 * n / n2));
        }
    }
    
    @Override
    public boolean isOldMapFormat(final String s) {
        final WorldInfo worldInfo = this.getWorldInfo(s);
        if (worldInfo != null && worldInfo.getSaveVersion() != this.getSaveVersion()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public AnvilSaveConverter(final File file) {
        super(file);
    }
    
    private void addRegionFilesToCollection(final File file, final Collection<File> collection) {
        final File[] listFiles = new File(file, AnvilSaveConverter.I[0xAF ^ 0xBC]).listFiles(new FilenameFilter(this) {
            final AnvilSaveConverter this$0;
            private static final String[] I;
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("L\b\u0004$", "begVn");
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
                    if (3 <= -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public boolean accept(final File file, final String s) {
                return s.endsWith(AnvilSaveConverter$1.I["".length()]);
            }
            
            static {
                I();
            }
        });
        if (listFiles != null) {
            Collections.addAll(collection, listFiles);
        }
    }
    
    @Override
    public String getName() {
        return AnvilSaveConverter.I["".length()];
    }
    
    @Override
    public void flushCache() {
        RegionFileCache.clearRegionFileReferences();
    }
    
    private static void I() {
        (I = new String[0x39 ^ 0x2D])["".length()] = I("$\u0004\u001b\r5", "ejmdY");
        AnvilSaveConverter.I[" ".length()] = I(":8#\u0004/\nv6\tc\u001d3#\u0002c\u0000$b\u0007 \f31\u0015c\t9.\u0002&\u001dv5\u000e&\u001d3b\u0001\"\u00023b\u0011,\u001d:&\u0015c\u000e$'F0\u000e '\u0002b", "oVBfC");
        AnvilSaveConverter.I["  ".length()] = I("\u0007\u0011\u0003|\\", "CXNQm");
        AnvilSaveConverter.I["   ".length()] = I("1\f:[", "uEwjO");
        AnvilSaveConverter.I[0x3D ^ 0x39] = I("\u0002\u0010\u0011\u000f 8\u001d\u0017A(>\u001f\u0014\u0004<\"]^O", "QspaN");
        AnvilSaveConverter.I[0xC ^ 0x9] = I("\"(.&$V$5)>\u00135).'\u0018g9(=\u00183z.;V", "vGZGH");
        AnvilSaveConverter.I[0xAC ^ 0xAA] = I("\u0001\u0016\u0000\u0005>\u001d", "ssglQ");
        AnvilSaveConverter.I[0x24 ^ 0x23] = I("\u001a/\u0016<\n\u0006", "hJqUe");
        AnvilSaveConverter.I[0x37 ^ 0x3F] = I("\u0005\u001c$\u000e\u0007\u0019", "wyCgh");
        AnvilSaveConverter.I[0x6B ^ 0x62] = I("\u000288\u0017!2v-\u001am4$<\u001492v5\u0010;2:w\u0011,#\t4\u0016?w48\u0016&\"&", "WVYuM");
        AnvilSaveConverter.I[0xAF ^ 0xA5] = I("/\r\u0003!(m\f\u00140", "ChuDD");
        AnvilSaveConverter.I[0xAC ^ 0xA7] = I("\u0000\b8\u0001!0F-\fm6\u0014<\u000290F5\u0006;0\nw\u0007,!94\u0000?u\u00048\u0000& \u0016", "UfYcM");
        AnvilSaveConverter.I[0xD ^ 0x1] = I("\u000e#!\u0012$L\"6\u0003\u0017\u000f%%", "bFWwH");
        AnvilSaveConverter.I[0x3A ^ 0x37] = I("\"\u00160:<\u0012X%7p\u0014\n49$\u0012X==&\u0012\u0014\u007f<1\u0003'<;\"W\u001a0;;\u0002\b", "wxQXP");
        AnvilSaveConverter.I[0x37 ^ 0x39] = I("f\u0001\u0001#", "HlbQv");
        AnvilSaveConverter.I[0xC8 ^ 0xC7] = I("W\u0006&#", "ykEBQ");
        AnvilSaveConverter.I[0xB1 ^ 0xA1] = I("%&9%\f\u0007g$&I\u0005\"$*\u0001C.>9\u001c\u0017g#=\u001b\u0006&=", "cGPIi");
        AnvilSaveConverter.I[0x54 ^ 0x45] = I(":?\u001d\u0015%", "vZkpI");
        AnvilSaveConverter.I[0x64 ^ 0x76] = I("\u00167\u0015\u0002&", "ZRcgJ");
        AnvilSaveConverter.I[0x69 ^ 0x7A] = I("\u0016$\u0001\u0003\u0017\n", "dAfjx");
    }
    
    private void convertChunks(final File file, final File file2, final WorldChunkManager worldChunkManager, final int n, final int n2, final IProgressUpdate progressUpdate) {
        try {
            final String name = file2.getName();
            final RegionFile regionFile = new RegionFile(file2);
            final RegionFile regionFile2 = new RegionFile(new File(file, String.valueOf(name.substring("".length(), name.length() - AnvilSaveConverter.I[0x88 ^ 0x86].length())) + AnvilSaveConverter.I[0x37 ^ 0x38]));
            int i = "".length();
            "".length();
            if (4 == 0) {
                throw null;
            }
            while (i < (0x2F ^ 0xF)) {
                int j = "".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                while (j < (0x57 ^ 0x77)) {
                    if (regionFile.isChunkSaved(i, j) && !regionFile2.isChunkSaved(i, j)) {
                        final DataInputStream chunkDataInputStream = regionFile.getChunkDataInputStream(i, j);
                        if (chunkDataInputStream == null) {
                            AnvilSaveConverter.logger.warn(AnvilSaveConverter.I[0x88 ^ 0x98]);
                            "".length();
                            if (3 == 1) {
                                throw null;
                            }
                        }
                        else {
                            final NBTTagCompound read = CompressedStreamTools.read(chunkDataInputStream);
                            chunkDataInputStream.close();
                            final ChunkLoader.AnvilConverterData load = ChunkLoader.load(read.getCompoundTag(AnvilSaveConverter.I[0x31 ^ 0x20]));
                            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                            nbtTagCompound.setTag(AnvilSaveConverter.I[0x6C ^ 0x7E], nbtTagCompound2);
                            ChunkLoader.convertToAnvilFormat(load, nbtTagCompound2, worldChunkManager);
                            final DataOutputStream chunkDataOutputStream = regionFile2.getChunkDataOutputStream(i, j);
                            CompressedStreamTools.write(nbtTagCompound, chunkDataOutputStream);
                            chunkDataOutputStream.close();
                        }
                    }
                    ++j;
                }
                final int n3 = (int)Math.round(100.0 * (n * (627 + 357 - 6 + 46)) / (n2 * (563 + 557 - 1040 + 944)));
                final int loadingProgress = (int)Math.round(100.0 * ((i + " ".length()) * (0x72 ^ 0x52) + n * (678 + 400 - 301 + 247)) / (n2 * (47 + 753 - 264 + 488)));
                if (loadingProgress > n3) {
                    progressUpdate.setLoadingProgress(loadingProgress);
                }
                ++i;
            }
            regionFile.close();
            regionFile2.close();
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    protected int getSaveVersion() {
        return 7191 + 6710 - 6386 + 11618;
    }
    
    @Override
    public ISaveHandler getSaveLoader(final String s, final boolean b) {
        return new AnvilSaveHandler(this.savesDirectory, s, b);
    }
    
    @Override
    public boolean convertMapFormat(final String s, final IProgressUpdate progressUpdate) {
        progressUpdate.setLoadingProgress("".length());
        final ArrayList arrayList = Lists.newArrayList();
        final ArrayList arrayList2 = Lists.newArrayList();
        final ArrayList arrayList3 = Lists.newArrayList();
        final File file = new File(this.savesDirectory, s);
        final File file2 = new File(file, AnvilSaveConverter.I["  ".length()]);
        final File file3 = new File(file, AnvilSaveConverter.I["   ".length()]);
        AnvilSaveConverter.logger.info(AnvilSaveConverter.I[0x91 ^ 0x95]);
        this.addRegionFilesToCollection(file, arrayList);
        if (file2.exists()) {
            this.addRegionFilesToCollection(file2, arrayList2);
        }
        if (file3.exists()) {
            this.addRegionFilesToCollection(file3, arrayList3);
        }
        final int n = arrayList.size() + arrayList2.size() + arrayList3.size();
        AnvilSaveConverter.logger.info(AnvilSaveConverter.I[0x5F ^ 0x5A] + n);
        final WorldInfo worldInfo = this.getWorldInfo(s);
        WorldChunkManager worldChunkManager;
        if (worldInfo.getTerrainType() == WorldType.FLAT) {
            worldChunkManager = new WorldChunkManagerHell(BiomeGenBase.plains, 0.5f);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            worldChunkManager = new WorldChunkManager(worldInfo.getSeed(), worldInfo.getTerrainType(), worldInfo.getGeneratorOptions());
        }
        this.convertFile(new File(file, AnvilSaveConverter.I[0x9B ^ 0x9D]), arrayList, worldChunkManager, "".length(), n, progressUpdate);
        this.convertFile(new File(file2, AnvilSaveConverter.I[0x63 ^ 0x64]), arrayList2, new WorldChunkManagerHell(BiomeGenBase.hell, 0.0f), arrayList.size(), n, progressUpdate);
        this.convertFile(new File(file3, AnvilSaveConverter.I[0x36 ^ 0x3E]), arrayList3, new WorldChunkManagerHell(BiomeGenBase.sky, 0.0f), arrayList.size() + arrayList2.size(), n, progressUpdate);
        worldInfo.setSaveVersion(9513 + 750 + 2774 + 6096);
        if (worldInfo.getTerrainType() == WorldType.DEFAULT_1_1) {
            worldInfo.setTerrainType(WorldType.DEFAULT);
        }
        this.createFile(s);
        this.getSaveLoader(s, "".length() != 0).saveWorldInfo(worldInfo);
        return " ".length() != 0;
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public List<SaveFormatComparator> getSaveList() throws AnvilConverterException {
        if (this.savesDirectory == null || !this.savesDirectory.exists() || !this.savesDirectory.isDirectory()) {
            throw new AnvilConverterException(AnvilSaveConverter.I[" ".length()]);
        }
        final ArrayList arrayList = Lists.newArrayList();
        final File[] listFiles;
        final int length = (listFiles = this.savesDirectory.listFiles()).length;
        int i = "".length();
        "".length();
        if (4 < 3) {
            throw null;
        }
        while (i < length) {
            final File file = listFiles[i];
            if (file.isDirectory()) {
                final String name = file.getName();
                final WorldInfo worldInfo = this.getWorldInfo(name);
                if (worldInfo != null && (worldInfo.getSaveVersion() == 894 + 6723 - 2773 + 14288 || worldInfo.getSaveVersion() == 12839 + 10708 - 11307 + 6893)) {
                    int n;
                    if (worldInfo.getSaveVersion() != this.getSaveVersion()) {
                        n = " ".length();
                        "".length();
                        if (3 <= 0) {
                            throw null;
                        }
                    }
                    else {
                        n = "".length();
                    }
                    final int n2 = n;
                    String worldName = worldInfo.getWorldName();
                    if (StringUtils.isEmpty((CharSequence)worldName)) {
                        worldName = name;
                    }
                    arrayList.add(new SaveFormatComparator(name, worldName, worldInfo.getLastTimePlayed(), 0L, worldInfo.getGameType(), (boolean)(n2 != 0), worldInfo.isHardcoreModeEnabled(), worldInfo.areCommandsAllowed()));
                }
            }
            ++i;
        }
        return (List<SaveFormatComparator>)arrayList;
    }
    
    @Override
    public boolean func_154334_a(final String s) {
        final WorldInfo worldInfo = this.getWorldInfo(s);
        if (worldInfo != null && worldInfo.getSaveVersion() == 7399 + 18295 - 18057 + 11495) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private void createFile(final String s) {
        final File file = new File(this.savesDirectory, s);
        if (!file.exists()) {
            AnvilSaveConverter.logger.warn(AnvilSaveConverter.I[0x33 ^ 0x3A]);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            final File file2 = new File(file, AnvilSaveConverter.I[0x4F ^ 0x45]);
            if (!file2.exists()) {
                AnvilSaveConverter.logger.warn(AnvilSaveConverter.I[0x82 ^ 0x89]);
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            else if (!file2.renameTo(new File(file, AnvilSaveConverter.I[0x3B ^ 0x37]))) {
                AnvilSaveConverter.logger.warn(AnvilSaveConverter.I[0x61 ^ 0x6C]);
            }
        }
    }
}
