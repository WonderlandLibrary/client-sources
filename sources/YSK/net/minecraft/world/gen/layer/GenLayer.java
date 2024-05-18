package net.minecraft.world.gen.layer;

import net.minecraft.world.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.biome.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;

public abstract class GenLayer
{
    private long chunkSeed;
    private static final String[] I;
    protected long baseSeed;
    private long worldGenSeed;
    protected GenLayer parent;
    
    public GenLayer(final long baseSeed) {
        this.baseSeed = baseSeed;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += baseSeed;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += baseSeed;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += baseSeed;
    }
    
    public static GenLayer[] initializeAllBiomeGenerators(final long n, final WorldType worldType, final String s) {
        final GenLayer magnify = GenLayerZoom.magnify(1000L, new GenLayerDeepOcean(4L, new GenLayerAddMushroomIsland(5L, new GenLayerAddIsland(4L, new GenLayerZoom(2003L, new GenLayerZoom(2002L, new GenLayerEdge(3L, new GenLayerEdge(2L, new GenLayerEdge(2L, new GenLayerAddIsland(3L, new GenLayerAddSnow(2L, new GenLayerRemoveTooMuchOcean(2L, new GenLayerAddIsland(70L, new GenLayerAddIsland(50L, new GenLayerAddIsland(2L, new GenLayerZoom(2001L, new GenLayerAddIsland(1L, new GenLayerFuzzyZoom(2000L, new GenLayerIsland(1L)))))))))), GenLayerEdge.Mode.COOL_WARM), GenLayerEdge.Mode.HEAT_ICE), GenLayerEdge.Mode.SPECIAL)))))), "".length());
        int riverSize;
        int biomeSize = riverSize = (0x4F ^ 0x4B);
        if (worldType == WorldType.CUSTOMIZED && s.length() > 0) {
            final ChunkProviderSettings func_177864_b = ChunkProviderSettings.Factory.jsonToFactory(s).func_177864_b();
            biomeSize = func_177864_b.biomeSize;
            riverSize = func_177864_b.riverSize;
        }
        if (worldType == WorldType.LARGE_BIOMES) {
            biomeSize = (0x6C ^ 0x6A);
        }
        final GenLayerRiverInit genLayerRiverInit = new GenLayerRiverInit(100L, GenLayerZoom.magnify(1000L, magnify, "".length()));
        final GenLayerHills genLayerHills = new GenLayerHills(1000L, new GenLayerBiomeEdge(1000L, GenLayerZoom.magnify(1000L, new GenLayerBiome(200L, magnify, worldType, s), "  ".length())), GenLayerZoom.magnify(1000L, genLayerRiverInit, "  ".length()));
        final GenLayerSmooth genLayerSmooth = new GenLayerSmooth(1000L, new GenLayerRiver(1L, GenLayerZoom.magnify(1000L, GenLayerZoom.magnify(1000L, genLayerRiverInit, "  ".length()), riverSize)));
        GenLayer genLayer = new GenLayerRareBiome(1001L, genLayerHills);
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < biomeSize) {
            genLayer = new GenLayerZoom(443 + 871 - 407 + 93 + i, genLayer);
            if (i == 0) {
                genLayer = new GenLayerAddIsland(3L, genLayer);
            }
            if (i == " ".length() || biomeSize == " ".length()) {
                genLayer = new GenLayerShore(1000L, genLayer);
            }
            ++i;
        }
        final GenLayerRiverMix genLayerRiverMix = new GenLayerRiverMix(100L, new GenLayerSmooth(1000L, genLayer), genLayerSmooth);
        final GenLayerVoronoiZoom genLayerVoronoiZoom = new GenLayerVoronoiZoom(10L, genLayerRiverMix);
        genLayerRiverMix.initWorldGenSeed(n);
        genLayerVoronoiZoom.initWorldGenSeed(n);
        final GenLayer[] array = new GenLayer["   ".length()];
        array["".length()] = genLayerRiverMix;
        array[" ".length()] = genLayerVoronoiZoom;
        array["  ".length()] = genLayerRiverMix;
        return array;
    }
    
    private static void I() {
        (I = new String[0x23 ^ 0x25])["".length()] = I("\b\u001a\u0001\u0015\u00139\u001c\u0002\u0002R)\u001c\u0003\b\u00178", "Kuler");
        GenLayer.I[" ".length()] = I("\u0014\u001a\n57%S\u0007=;8\u0014E;=;\u0003\u0004*72", "VseXR");
        GenLayer.I["  ".length()] = I("\u0013\u0011)\u001c-q9f8\f", "QxFqH");
        GenLayer.I["   ".length()] = I("(%%(\u0003J\u000ej\f\"", "jLJEf");
        GenLayer.I[0x7C ^ 0x78] = I("/\u0010\u001a\u001c*M8", "myuqO");
        GenLayer.I[0x1B ^ 0x1E] = I(":8\u000e>(X\u0013", "xQaSM");
    }
    
    protected static boolean isBiomeOceanic(final int n) {
        if (n != BiomeGenBase.ocean.biomeID && n != BiomeGenBase.deepOcean.biomeID && n != BiomeGenBase.frozenOcean.biomeID) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    protected int nextInt(final int n) {
        int n2 = (int)((this.chunkSeed >> (0xA0 ^ 0xB8)) % n);
        if (n2 < 0) {
            n2 += n;
        }
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += this.worldGenSeed;
        return n2;
    }
    
    public abstract int[] getInts(final int p0, final int p1, final int p2, final int p3);
    
    public void initChunkSeed(final long n, final long n2) {
        this.chunkSeed = this.worldGenSeed;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += n;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += n2;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += n;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += n2;
    }
    
    static {
        I();
    }
    
    protected int selectModeOrRandom(final int n, final int n2, final int n3, final int n4) {
        int selectRandom;
        if (n2 == n3 && n3 == n4) {
            selectRandom = n2;
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else if (n == n2 && n == n3) {
            selectRandom = n;
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        else if (n == n2 && n == n4) {
            selectRandom = n;
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else if (n == n3 && n == n4) {
            selectRandom = n;
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else if (n == n2 && n3 != n4) {
            selectRandom = n;
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else if (n == n3 && n2 != n4) {
            selectRandom = n;
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        else if (n == n4 && n2 != n3) {
            selectRandom = n;
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (n2 == n3 && n != n4) {
            selectRandom = n2;
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else if (n2 == n4 && n != n3) {
            selectRandom = n2;
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else if (n3 == n4 && n != n2) {
            selectRandom = n3;
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            final int[] array = new int[0x45 ^ 0x41];
            array["".length()] = n;
            array[" ".length()] = n2;
            array["  ".length()] = n3;
            array["   ".length()] = n4;
            selectRandom = this.selectRandom(array);
        }
        return selectRandom;
    }
    
    public void initWorldGenSeed(final long worldGenSeed) {
        this.worldGenSeed = worldGenSeed;
        if (this.parent != null) {
            this.parent.initWorldGenSeed(worldGenSeed);
        }
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
    }
    
    protected int selectRandom(final int... array) {
        return array[this.nextInt(array.length)];
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected static boolean biomesEqualOrMesaPlateau(final int n, final int n2) {
        if (n == n2) {
            return " ".length() != 0;
        }
        if (n != BiomeGenBase.mesaPlateau_F.biomeID && n != BiomeGenBase.mesaPlateau.biomeID) {
            final BiomeGenBase biome = BiomeGenBase.getBiome(n);
            final BiomeGenBase biome2 = BiomeGenBase.getBiome(n2);
            try {
                int n3;
                if (biome != null && biome2 != null) {
                    n3 = (biome.isEqualTo(biome2) ? 1 : 0);
                    "".length();
                    if (2 == 3) {
                        throw null;
                    }
                }
                else {
                    n3 = "".length();
                }
                return n3 != 0;
            }
            catch (Throwable t) {
                final CrashReport crashReport = CrashReport.makeCrashReport(t, GenLayer.I["".length()]);
                final CrashReportCategory category = crashReport.makeCategory(GenLayer.I[" ".length()]);
                category.addCrashSection(GenLayer.I["  ".length()], n);
                category.addCrashSection(GenLayer.I["   ".length()], n2);
                category.addCrashSectionCallable(GenLayer.I[0x6A ^ 0x6E], new Callable<String>(biome) {
                    private final BiomeGenBase val$biomegenbase;
                    
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
                            if (0 >= 3) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(this.val$biomegenbase);
                    }
                    
                    @Override
                    public Object call() throws Exception {
                        return this.call();
                    }
                });
                category.addCrashSectionCallable(GenLayer.I[0x5F ^ 0x5A], new Callable<String>(biome2) {
                    private final BiomeGenBase val$biomegenbase1;
                    
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
                            if (2 != 2) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(this.val$biomegenbase1);
                    }
                    
                    @Override
                    public Object call() throws Exception {
                        return this.call();
                    }
                });
                throw new ReportedException(crashReport);
            }
        }
        if (n2 != BiomeGenBase.mesaPlateau_F.biomeID && n2 != BiomeGenBase.mesaPlateau.biomeID) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
}
