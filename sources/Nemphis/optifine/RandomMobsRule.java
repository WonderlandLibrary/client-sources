/*
 * Decompiled with CFR 0_118.
 */
package optifine;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import optifine.Config;
import optifine.Matches;
import optifine.MathUtils;
import optifine.RandomMobs;
import optifine.RangeListInt;

public class RandomMobsRule {
    private ResourceLocation baseResLoc = null;
    private int[] skins = null;
    private ResourceLocation[] resourceLocations = null;
    private int[] weights = null;
    private BiomeGenBase[] biomes = null;
    private RangeListInt heights = null;
    public int[] sumWeights = null;
    public int sumAllWeights = 1;

    public RandomMobsRule(ResourceLocation baseResLoc, int[] skins, int[] weights, BiomeGenBase[] biomes, RangeListInt heights) {
        this.baseResLoc = baseResLoc;
        this.skins = skins;
        this.weights = weights;
        this.biomes = biomes;
        this.heights = heights;
    }

    public boolean isValid(String path) {
        int i;
        this.resourceLocations = new ResourceLocation[this.skins.length];
        ResourceLocation locMcp = RandomMobs.getMcpatcherLocation(this.baseResLoc);
        if (locMcp == null) {
            Config.warn("Invalid path: " + this.baseResLoc.getResourcePath());
            return false;
        }
        int sum = 0;
        while (sum < this.resourceLocations.length) {
            i = this.skins[sum];
            if (i <= 1) {
                this.resourceLocations[sum] = this.baseResLoc;
            } else {
                ResourceLocation i1 = RandomMobs.getLocationIndexed(locMcp, i);
                if (i1 == null) {
                    Config.warn("Invalid path: " + this.baseResLoc.getResourcePath());
                    return false;
                }
                if (!Config.hasResource(i1)) {
                    Config.warn("Texture not found: " + i1.getResourcePath());
                    return false;
                }
                this.resourceLocations[sum] = i1;
            }
            ++sum;
        }
        if (this.weights != null) {
            int[] var6;
            if (this.weights.length > this.resourceLocations.length) {
                Config.warn("More weights defined than skins, trimming weights: " + path);
                var6 = new int[this.resourceLocations.length];
                System.arraycopy(this.weights, 0, var6, 0, var6.length);
                this.weights = var6;
            }
            if (this.weights.length < this.resourceLocations.length) {
                Config.warn("Less weights defined than skins, expanding weights: " + path);
                var6 = new int[this.resourceLocations.length];
                System.arraycopy(this.weights, 0, var6, 0, this.weights.length);
                i = MathUtils.getAverage(this.weights);
                int var7 = this.weights.length;
                while (var7 < var6.length) {
                    var6[var7] = i;
                    ++var7;
                }
                this.weights = var6;
            }
            this.sumWeights = new int[this.weights.length];
            sum = 0;
            i = 0;
            while (i < this.weights.length) {
                if (this.weights[i] < 0) {
                    Config.warn("Invalid weight: " + this.weights[i]);
                    return false;
                }
                this.sumWeights[i] = sum += this.weights[i];
                ++i;
            }
            this.sumAllWeights = sum;
            if (this.sumAllWeights <= 0) {
                Config.warn("Invalid sum of all weights: " + sum);
                this.sumAllWeights = 1;
            }
        }
        return true;
    }

    public boolean matches(EntityLiving el) {
        return !Matches.biome(el.spawnBiome, this.biomes) ? false : (this.heights != null && el.spawnPosition != null ? this.heights.isInRange(el.spawnPosition.getY()) : true);
    }

    public ResourceLocation getTextureLocation(ResourceLocation loc, int randomId) {
        int index = 0;
        if (this.weights == null) {
            index = randomId % this.resourceLocations.length;
        } else {
            int randWeight = randomId % this.sumAllWeights;
            int i = 0;
            while (i < this.sumWeights.length) {
                if (this.sumWeights[i] > randWeight) {
                    index = i;
                    break;
                }
                ++i;
            }
        }
        return this.resourceLocations[index];
    }
}

