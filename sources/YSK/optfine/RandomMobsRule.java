package optfine;

import net.minecraft.util.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.*;

public class RandomMobsRule
{
    private ResourceLocation baseResLoc;
    public int[] sumWeights;
    private ResourceLocation[] resourceLocations;
    private RangeListInt heights;
    private static final String[] I;
    private int[] weights;
    public int sumAllWeights;
    private int[] skins;
    private BiomeGenBase[] biomes;
    
    public boolean matches(final EntityLiving entityLiving) {
        if (this.biomes != null) {
            final BiomeGenBase spawnBiome = entityLiving.spawnBiome;
            int n = "".length();
            int i = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (i < this.biomes.length) {
                if (this.biomes[i] == spawnBiome) {
                    n = " ".length();
                    "".length();
                    if (-1 == 3) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
            if (n == 0) {
                return "".length() != 0;
            }
        }
        int n2;
        if (this.heights != null && entityLiving.spawnPosition != null) {
            n2 = (this.heights.isInRange(entityLiving.spawnPosition.getY()) ? 1 : 0);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n2 = " ".length();
        }
        return n2 != 0;
    }
    
    public RandomMobsRule(final ResourceLocation baseResLoc, final int[] skins, final int[] weights, final BiomeGenBase[] biomes, final RangeListInt heights) {
        this.baseResLoc = null;
        this.skins = null;
        this.resourceLocations = null;
        this.weights = null;
        this.biomes = null;
        this.heights = null;
        this.sumWeights = null;
        this.sumAllWeights = " ".length();
        this.baseResLoc = baseResLoc;
        this.skins = skins;
        this.weights = weights;
        this.biomes = biomes;
        this.heights = heights;
    }
    
    static {
        I();
    }
    
    public ResourceLocation getTextureLocation(final ResourceLocation resourceLocation, final int n) {
        int length = "".length();
        if (this.weights == null) {
            length = n % this.resourceLocations.length;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            final int n2 = n % this.sumAllWeights;
            int i = "".length();
            "".length();
            if (4 == 2) {
                throw null;
            }
            while (i < this.sumWeights.length) {
                if (this.sumWeights[i] > n2) {
                    length = i;
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        return this.resourceLocations[length];
    }
    
    public boolean isValid(final String s) {
        this.resourceLocations = new ResourceLocation[this.skins.length];
        final ResourceLocation mcpatcherLocation = RandomMobs.getMcpatcherLocation(this.baseResLoc);
        if (mcpatcherLocation == null) {
            Config.warn(RandomMobsRule.I["".length()] + this.baseResLoc.getResourcePath());
            return "".length() != 0;
        }
        int i = "".length();
        "".length();
        if (-1 == 0) {
            throw null;
        }
        while (i < this.resourceLocations.length) {
            final int n = this.skins[i];
            if (n <= " ".length()) {
                this.resourceLocations[i] = this.baseResLoc;
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            else {
                final ResourceLocation locationIndexed = RandomMobs.getLocationIndexed(mcpatcherLocation, n);
                if (locationIndexed == null) {
                    Config.warn(RandomMobsRule.I[" ".length()] + this.baseResLoc.getResourcePath());
                    return "".length() != 0;
                }
                if (!Config.hasResource(locationIndexed)) {
                    Config.warn(RandomMobsRule.I["  ".length()] + locationIndexed.getResourcePath());
                    return "".length() != 0;
                }
                this.resourceLocations[i] = locationIndexed;
            }
            ++i;
        }
        if (this.weights != null) {
            if (this.weights.length > this.resourceLocations.length) {
                Config.warn(RandomMobsRule.I["   ".length()] + s);
                final int[] weights = new int[this.resourceLocations.length];
                System.arraycopy(this.weights, "".length(), weights, "".length(), weights.length);
                this.weights = weights;
            }
            if (this.weights.length < this.resourceLocations.length) {
                Config.warn(RandomMobsRule.I[0xC1 ^ 0xC5] + s);
                final int[] weights2 = new int[this.resourceLocations.length];
                System.arraycopy(this.weights, "".length(), weights2, "".length(), this.weights.length);
                final int average = ConnectedUtils.getAverage(this.weights);
                int j = this.weights.length;
                "".length();
                if (3 <= 2) {
                    throw null;
                }
                while (j < weights2.length) {
                    weights2[j] = average;
                    ++j;
                }
                this.weights = weights2;
            }
            this.sumWeights = new int[this.weights.length];
            int length = "".length();
            int k = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (k < this.weights.length) {
                if (this.weights[k] < 0) {
                    Config.warn(RandomMobsRule.I[0x96 ^ 0x93] + this.weights[k]);
                    return "".length() != 0;
                }
                length += this.weights[k];
                this.sumWeights[k] = length;
                ++k;
            }
            this.sumAllWeights = length;
            if (this.sumAllWeights <= 0) {
                Config.warn(RandomMobsRule.I[0x2F ^ 0x29] + length);
                this.sumAllWeights = " ".length();
            }
        }
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x2 ^ 0x5])["".length()] = I(",\u000b3\u0014\u0019\f\u0001e\u0005\u0014\u0011\r\u007fU", "eeEuu");
        RandomMobsRule.I[" ".length()] = I("\u0005>.#=%4x2088bb", "LPXBQ");
        RandomMobsRule.I["  ".length()] = I(" !=\f\u0012\u0006!e\u0016\b\u0000d#\u0017\u0012\u001a \u007fX", "tDExg");
        RandomMobsRule.I["   ".length()] = I("\u0006?;(z<5 *2?#i)?-9'(>k$!,4k#\"$48|i9(\"=$$4,p>(3,8=>`k", "KPIMZ");
        RandomMobsRule.I[0x80 ^ 0x84] = I("\u0000=\u0018\u0003K;=\u0002\u0017\u00038+K\u0014\u000e*1\u0005\u0015\u000fl,\u0003\u0011\u0005l+\u0000\u0019\u0005?tK\u0015\u0013<9\u0005\u0014\u0002\"?K\u0007\u000e%?\u0003\u0004\u0018vx", "LXkpk");
        RandomMobsRule.I[0x6E ^ 0x6B] = I("\u0010\u001d>\u0000-0\u0017h\u0016$0\u0014 \u0015{y", "YsHaA");
        RandomMobsRule.I[0xA6 ^ 0xA0] = I("+<\u0011\u0005\u0005\u000b6G\u0017\u001c\u000fr\b\u0002I\u0003>\u000bD\u001e\u0007;\u0000\f\u001d\u0011hG", "bRgdi");
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
            if (3 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
