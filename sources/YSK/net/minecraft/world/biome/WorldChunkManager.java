package net.minecraft.world.biome;

import net.minecraft.world.gen.layer.*;
import net.minecraft.crash.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.util.*;

public class WorldChunkManager
{
    private String field_180301_f;
    private BiomeCache biomeCache;
    private List<BiomeGenBase> biomesToSpawnIn;
    private static final String[] I;
    private GenLayer genBiomes;
    private GenLayer biomeIndexLayer;
    
    public float[] getRainfall(float[] array, final int n, final int n2, final int n3, final int n4) {
        IntCache.resetIntCache();
        if (array == null || array.length < n3 * n4) {
            array = new float[n3 * n4];
        }
        final int[] ints = this.biomeIndexLayer.getInts(n, n2, n3, n4);
        int i = "".length();
        "".length();
        if (1 == 4) {
            throw null;
        }
        while (i < n3 * n4) {
            try {
                float n5 = BiomeGenBase.getBiomeFromBiomeList(ints[i], BiomeGenBase.field_180279_ad).getIntRainfall() / 65536.0f;
                if (n5 > 1.0f) {
                    n5 = 1.0f;
                }
                array[i] = n5;
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            catch (Throwable t) {
                final CrashReport crashReport = CrashReport.makeCrashReport(t, WorldChunkManager.I[" ".length()]);
                final CrashReportCategory category = crashReport.makeCategory(WorldChunkManager.I["  ".length()]);
                category.addCrashSection(WorldChunkManager.I["   ".length()], i);
                category.addCrashSection(WorldChunkManager.I[0x6 ^ 0x2], array.length);
                category.addCrashSection(WorldChunkManager.I[0xC2 ^ 0xC7], n);
                category.addCrashSection(WorldChunkManager.I[0x18 ^ 0x1E], n2);
                category.addCrashSection(WorldChunkManager.I[0x5F ^ 0x58], n3);
                category.addCrashSection(WorldChunkManager.I[0x20 ^ 0x28], n4);
                throw new ReportedException(crashReport);
            }
            ++i;
        }
        return array;
    }
    
    public BiomeGenBase[] loadBlockGeneratorData(final BiomeGenBase[] array, final int n, final int n2, final int n3, final int n4) {
        return this.getBiomeGenAt(array, n, n2, n3, n4, " ".length() != 0);
    }
    
    public WorldChunkManager(final long n, final WorldType worldType, final String field_180301_f) {
        this();
        this.field_180301_f = field_180301_f;
        final GenLayer[] initializeAllBiomeGenerators = GenLayer.initializeAllBiomeGenerators(n, worldType, field_180301_f);
        this.genBiomes = initializeAllBiomeGenerators["".length()];
        this.biomeIndexLayer = initializeAllBiomeGenerators[" ".length()];
    }
    
    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] array, final int n, final int n2, final int n3, final int n4, final boolean b) {
        IntCache.resetIntCache();
        if (array == null || array.length < n3 * n4) {
            array = new BiomeGenBase[n3 * n4];
        }
        if (b && n3 == (0x80 ^ 0x90) && n4 == (0x90 ^ 0x80) && (n & (0xBA ^ 0xB5)) == 0x0 && (n2 & (0x1 ^ 0xE)) == 0x0) {
            System.arraycopy(this.biomeCache.getCachedBiomes(n, n2), "".length(), array, "".length(), n3 * n4);
            return array;
        }
        final int[] ints = this.biomeIndexLayer.getInts(n, n2, n3, n4);
        int i = "".length();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (i < n3 * n4) {
            array[i] = BiomeGenBase.getBiomeFromBiomeList(ints[i], BiomeGenBase.field_180279_ad);
            ++i;
        }
        return array;
    }
    
    public void cleanupCache() {
        this.biomeCache.cleanupCache();
    }
    
    public boolean areBiomesViable(final int n, final int n2, final int n3, final List<BiomeGenBase> list) {
        IntCache.resetIntCache();
        final int n4 = n - n3 >> "  ".length();
        final int n5 = n2 - n3 >> "  ".length();
        final int n6 = n + n3 >> "  ".length();
        final int n7 = n2 + n3 >> "  ".length();
        final int n8 = n6 - n4 + " ".length();
        final int n9 = n7 - n5 + " ".length();
        final int[] ints = this.genBiomes.getInts(n4, n5, n8, n9);
        try {
            int i = "".length();
            "".length();
            if (3 <= 0) {
                throw null;
            }
            while (i < n8 * n9) {
                if (!list.contains(BiomeGenBase.getBiome(ints[i]))) {
                    return "".length() != 0;
                }
                ++i;
            }
            return " ".length() != 0;
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, WorldChunkManager.I[0x46 ^ 0x56]);
            final CrashReportCategory category = crashReport.makeCategory(WorldChunkManager.I[0x6B ^ 0x7A]);
            category.addCrashSection(WorldChunkManager.I[0x3D ^ 0x2F], this.genBiomes.toString());
            category.addCrashSection(WorldChunkManager.I[0x29 ^ 0x3A], n);
            category.addCrashSection(WorldChunkManager.I[0xD1 ^ 0xC5], n2);
            category.addCrashSection(WorldChunkManager.I[0x57 ^ 0x42], n3);
            category.addCrashSection(WorldChunkManager.I[0x81 ^ 0x97], list);
            throw new ReportedException(crashReport);
        }
    }
    
    public WorldChunkManager(final World world) {
        this(world.getSeed(), world.getWorldInfo().getTerrainType(), world.getWorldInfo().getGeneratorOptions());
    }
    
    public float getTemperatureAtHeight(final float n, final int n2) {
        return n;
    }
    
    protected WorldChunkManager() {
        this.biomeCache = new BiomeCache(this);
        this.field_180301_f = WorldChunkManager.I["".length()];
        (this.biomesToSpawnIn = (List<BiomeGenBase>)Lists.newArrayList()).add(BiomeGenBase.forest);
        this.biomesToSpawnIn.add(BiomeGenBase.plains);
        this.biomesToSpawnIn.add(BiomeGenBase.taiga);
        this.biomesToSpawnIn.add(BiomeGenBase.taigaHills);
        this.biomesToSpawnIn.add(BiomeGenBase.forestHills);
        this.biomesToSpawnIn.add(BiomeGenBase.jungle);
        this.biomesToSpawnIn.add(BiomeGenBase.jungleHills);
    }
    
    public BlockPos findBiomePosition(final int n, final int n2, final int n3, final List<BiomeGenBase> list, final Random random) {
        IntCache.resetIntCache();
        final int n4 = n - n3 >> "  ".length();
        final int n5 = n2 - n3 >> "  ".length();
        final int n6 = n + n3 >> "  ".length();
        final int n7 = n2 + n3 >> "  ".length();
        final int n8 = n6 - n4 + " ".length();
        final int n9 = n7 - n5 + " ".length();
        final int[] ints = this.genBiomes.getInts(n4, n5, n8, n9);
        BlockPos blockPos = null;
        int length = "".length();
        int i = "".length();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (i < n8 * n9) {
            final int n10 = n4 + i % n8 << "  ".length();
            final int n11 = n5 + i / n8 << "  ".length();
            if (list.contains(BiomeGenBase.getBiome(ints[i])) && (blockPos == null || random.nextInt(length + " ".length()) == 0)) {
                blockPos = new BlockPos(n10, "".length(), n11);
                ++length;
            }
            ++i;
        }
        return blockPos;
    }
    
    public BiomeGenBase getBiomeGenerator(final BlockPos blockPos, final BiomeGenBase biomeGenBase) {
        return this.biomeCache.func_180284_a(blockPos.getX(), blockPos.getZ(), biomeGenBase);
    }
    
    static {
        I();
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
            if (3 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] array, final int n, final int n2, final int n3, final int n4) {
        IntCache.resetIntCache();
        if (array == null || array.length < n3 * n4) {
            array = new BiomeGenBase[n3 * n4];
        }
        final int[] ints = this.genBiomes.getInts(n, n2, n3, n4);
        try {
            int i = "".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
            while (i < n3 * n4) {
                array[i] = BiomeGenBase.getBiomeFromBiomeList(ints[i], BiomeGenBase.field_180279_ad);
                ++i;
            }
            return array;
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, WorldChunkManager.I[0x89 ^ 0x80]);
            final CrashReportCategory category = crashReport.makeCategory(WorldChunkManager.I[0x12 ^ 0x18]);
            category.addCrashSection(WorldChunkManager.I[0x38 ^ 0x33], array.length);
            category.addCrashSection(WorldChunkManager.I[0xAB ^ 0xA7], n);
            category.addCrashSection(WorldChunkManager.I[0xCD ^ 0xC0], n2);
            category.addCrashSection(WorldChunkManager.I[0x87 ^ 0x89], n3);
            category.addCrashSection(WorldChunkManager.I[0x16 ^ 0x19], n4);
            throw new ReportedException(crashReport);
        }
    }
    
    public List<BiomeGenBase> getBiomesToSpawnIn() {
        return this.biomesToSpawnIn;
    }
    
    private static void I() {
        (I = new String[0x55 ^ 0x42])["".length()] = I("", "MOxUS");
        WorldChunkManager.I[" ".length()] = I("-)\u0006;\u0014\r#P\u0018\u0011\u000b*\u0015z\u0011\u0000", "dGpZx");
        WorldChunkManager.I["  ".length()] = I("\u0014!;?41\" \u0013>?-'", "PNLQR");
        WorldChunkManager.I["   ".length()] = I("\b\u00119 \u0003J\u00112", "jxVMf");
        WorldChunkManager.I[0xC2 ^ 0xC6] = I("\u0010 \u0004*5\u0015#\u001f7\b)o\u0000-)\u0011", "tOsDS");
        WorldChunkManager.I[0x3 ^ 0x6] = I("\u000e", "vVFNm");
        WorldChunkManager.I[0xA0 ^ 0xA6] = I("\u0012", "hAmwK");
        WorldChunkManager.I[0x4F ^ 0x48] = I("\u001b", "llUnp");
        WorldChunkManager.I[0x3C ^ 0x34] = I("0", "XRMxs");
        WorldChunkManager.I[0xB0 ^ 0xB9] = I("\r!\u000f\u0007;-+Y$>+\"\u001cF> ", "DOyfW");
        WorldChunkManager.I[0x63 ^ 0x69] = I("\u0013\u000e\u001d2?.\u0002\u000f2:.\f\u0001", "AojpV");
        WorldChunkManager.I[0xCA ^ 0xC1] = I(":\u0018\u001a\u0017<+*(Z*1\u000b\u0010", "XquzY");
        WorldChunkManager.I[0x62 ^ 0x6E] = I("\u0012", "jTKjd");
        WorldChunkManager.I[0x70 ^ 0x7D] = I(")", "SuCSK");
        WorldChunkManager.I[0x1E ^ 0x10] = I("\u000f", "xWGNV");
        WorldChunkManager.I[0x7B ^ 0x74] = I("2", "ZCHbT");
        WorldChunkManager.I[0x69 ^ 0x79] = I(":\u0001,&+\u001a\u000bz\u0005.\u001c\u0002?g.\u0017", "soZGG");
        WorldChunkManager.I[0x17 ^ 0x6] = I("\u0018\u0018\u00142>", "TymWL");
        WorldChunkManager.I[0x40 ^ 0x52] = I(";%\u0015\u0004\u001c", "wDlan");
        WorldChunkManager.I[0xA8 ^ 0xBB] = I("\u0011", "ixCRv");
        WorldChunkManager.I[0xAD ^ 0xB9] = I("\f", "vbyIP");
        WorldChunkManager.I[0x59 ^ 0x4C] = I("\u001d\u0004\u0012\u000e\u001c\u001c", "oevgi");
        WorldChunkManager.I[0x1 ^ 0x17] = I("\u0011.\u001f\u001d.\u0015&", "pBsrY");
    }
    
    public BiomeGenBase getBiomeGenerator(final BlockPos blockPos) {
        return this.getBiomeGenerator(blockPos, null);
    }
}
