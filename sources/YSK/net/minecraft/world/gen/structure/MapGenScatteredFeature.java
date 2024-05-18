package net.minecraft.world.gen.structure;

import net.minecraft.world.biome.*;
import com.google.common.collect.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;

public class MapGenScatteredFeature extends MapGenStructure
{
    private static final List<BiomeGenBase> biomelist;
    private int minDistanceBetweenScatteredFeatures;
    private static final String[] I;
    private int maxDistanceBetweenScatteredFeatures;
    private List<BiomeGenBase.SpawnListEntry> scatteredFeatureSpawnList;
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0003&$\u0004\u0010\t,2", "gOWpq");
        MapGenScatteredFeature.I[" ".length()] = I("'\u0012\u000e#5\u0016", "swcSY");
    }
    
    public boolean func_175798_a(final BlockPos blockPos) {
        final StructureStart func_175797_c = this.func_175797_c(blockPos);
        if (func_175797_c != null && func_175797_c instanceof Start && !func_175797_c.components.isEmpty()) {
            return func_175797_c.components.getFirst() instanceof ComponentScatteredFeaturePieces.SwampHut;
        }
        return "".length() != 0;
    }
    
    @Override
    protected StructureStart getStructureStart(final int n, final int n2) {
        return new Start(this.worldObj, this.rand, n, n2);
    }
    
    static {
        I();
        final BiomeGenBase[] array = new BiomeGenBase[0x68 ^ 0x6D];
        array["".length()] = BiomeGenBase.desert;
        array[" ".length()] = BiomeGenBase.desertHills;
        array["  ".length()] = BiomeGenBase.jungle;
        array["   ".length()] = BiomeGenBase.jungleHills;
        array[0x4C ^ 0x48] = BiomeGenBase.swampland;
        biomelist = Arrays.asList(array);
    }
    
    public MapGenScatteredFeature() {
        this.scatteredFeatureSpawnList = (List<BiomeGenBase.SpawnListEntry>)Lists.newArrayList();
        this.maxDistanceBetweenScatteredFeatures = (0x61 ^ 0x41);
        this.minDistanceBetweenScatteredFeatures = (0x14 ^ 0x1C);
        this.scatteredFeatureSpawnList.add(new BiomeGenBase.SpawnListEntry(EntityWitch.class, " ".length(), " ".length(), " ".length()));
    }
    
    public MapGenScatteredFeature(final Map<String, String> map) {
        this();
        final Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<String, String> entry = iterator.next();
            if (entry.getKey().equals(MapGenScatteredFeature.I["".length()])) {
                this.maxDistanceBetweenScatteredFeatures = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.maxDistanceBetweenScatteredFeatures, this.minDistanceBetweenScatteredFeatures + " ".length());
            }
        }
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
            if (4 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public List<BiomeGenBase.SpawnListEntry> getScatteredFeatureSpawnList() {
        return this.scatteredFeatureSpawnList;
    }
    
    @Override
    public String getStructureName() {
        return MapGenScatteredFeature.I[" ".length()];
    }
    
    @Override
    protected boolean canSpawnStructureAtCoords(int n, int n2) {
        final int n3 = n;
        final int n4 = n2;
        if (n < 0) {
            n -= this.maxDistanceBetweenScatteredFeatures - " ".length();
        }
        if (n2 < 0) {
            n2 -= this.maxDistanceBetweenScatteredFeatures - " ".length();
        }
        final int n5 = n / this.maxDistanceBetweenScatteredFeatures;
        final int n6 = n2 / this.maxDistanceBetweenScatteredFeatures;
        final Random setRandomSeed = this.worldObj.setRandomSeed(n5, n6, 9789828 + 2415933 - 6035967 + 8187823);
        final int n7 = n5 * this.maxDistanceBetweenScatteredFeatures;
        final int n8 = n6 * this.maxDistanceBetweenScatteredFeatures;
        final int n9 = n7 + setRandomSeed.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
        final int n10 = n8 + setRandomSeed.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
        if (n3 == n9 && n4 == n10) {
            final BiomeGenBase biomeGenerator = this.worldObj.getWorldChunkManager().getBiomeGenerator(new BlockPos(n3 * (0x91 ^ 0x81) + (0x1 ^ 0x9), "".length(), n4 * (0x29 ^ 0x39) + (0x1D ^ 0x15)));
            if (biomeGenerator == null) {
                return "".length() != 0;
            }
            final Iterator<BiomeGenBase> iterator = MapGenScatteredFeature.biomelist.iterator();
            "".length();
            if (4 < 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                if (biomeGenerator == iterator.next()) {
                    return " ".length() != 0;
                }
            }
        }
        return "".length() != 0;
    }
    
    public static class Start extends StructureStart
    {
        public Start(final World world, final Random random, final int n, final int n2) {
            super(n, n2);
            final BiomeGenBase biomeGenForCoords = world.getBiomeGenForCoords(new BlockPos(n * (0x1E ^ 0xE) + (0x38 ^ 0x30), "".length(), n2 * (0x43 ^ 0x53) + (0x26 ^ 0x2E)));
            if (biomeGenForCoords != BiomeGenBase.jungle && biomeGenForCoords != BiomeGenBase.jungleHills) {
                if (biomeGenForCoords == BiomeGenBase.swampland) {
                    this.components.add(new ComponentScatteredFeaturePieces.SwampHut(random, n * (0x8 ^ 0x18), n2 * (0x75 ^ 0x65)));
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else if (biomeGenForCoords == BiomeGenBase.desert || biomeGenForCoords == BiomeGenBase.desertHills) {
                    this.components.add(new ComponentScatteredFeaturePieces.DesertPyramid(random, n * (0x2E ^ 0x3E), n2 * (0x32 ^ 0x22)));
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                }
            }
            else {
                this.components.add(new ComponentScatteredFeaturePieces.JunglePyramid(random, n * (0x1F ^ 0xF), n2 * (0xB1 ^ 0xA1)));
            }
            this.updateBoundingBox();
        }
        
        public Start() {
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
                if (3 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
