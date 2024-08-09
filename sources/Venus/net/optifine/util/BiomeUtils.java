/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraft.world.biome.Biomes;
import net.optifine.config.BiomeId;
import net.optifine.override.ChunkCacheOF;

public class BiomeUtils {
    private static Registry<Biome> biomeRegistry = BiomeUtils.getBiomeRegistry(Minecraft.getInstance().world);
    public static Biome PLAINS = BiomeUtils.getBiomeSafe(biomeRegistry, Biomes.PLAINS, BiomeUtils::lambda$static$0);
    public static Biome SWAMP = BiomeUtils.getBiomeSafe(biomeRegistry, Biomes.SWAMP, BiomeUtils::lambda$static$1);
    public static Biome SWAMP_HILLS = BiomeUtils.getBiomeSafe(biomeRegistry, Biomes.SWAMP_HILLS, BiomeUtils::lambda$static$2);

    public static void onWorldChanged(World world) {
        biomeRegistry = BiomeUtils.getBiomeRegistry(world);
        PLAINS = BiomeUtils.getBiomeSafe(biomeRegistry, Biomes.PLAINS, BiomeUtils::lambda$onWorldChanged$3);
        SWAMP = BiomeUtils.getBiomeSafe(biomeRegistry, Biomes.SWAMP, BiomeUtils::lambda$onWorldChanged$4);
        SWAMP_HILLS = BiomeUtils.getBiomeSafe(biomeRegistry, Biomes.SWAMP_HILLS, BiomeUtils::lambda$onWorldChanged$5);
    }

    private static Biome getBiomeSafe(Registry<Biome> registry, RegistryKey<Biome> registryKey, Supplier<Biome> supplier) {
        Biome biome = registry.getValueForKey(registryKey);
        if (biome == null) {
            biome = supplier.get();
        }
        return biome;
    }

    public static Registry<Biome> getBiomeRegistry(World world) {
        return world != null ? world.func_241828_r().getRegistry(Registry.BIOME_KEY) : WorldGenRegistries.BIOME;
    }

    public static Registry<Biome> getBiomeRegistry() {
        return biomeRegistry;
    }

    public static ResourceLocation getLocation(Biome biome) {
        return BiomeUtils.getBiomeRegistry().getKey(biome);
    }

    public static int getId(Biome biome) {
        return BiomeUtils.getBiomeRegistry().getId(biome);
    }

    public static int getId(ResourceLocation resourceLocation) {
        Biome biome = BiomeUtils.getBiome(resourceLocation);
        return BiomeUtils.getBiomeRegistry().getId(biome);
    }

    public static BiomeId getBiomeId(ResourceLocation resourceLocation) {
        return BiomeId.make(resourceLocation);
    }

    public static Biome getBiome(ResourceLocation resourceLocation) {
        return BiomeUtils.getBiomeRegistry().getOrDefault(resourceLocation);
    }

    public static Set<ResourceLocation> getLocations() {
        return BiomeUtils.getBiomeRegistry().keySet();
    }

    public static List<Biome> getBiomes() {
        return Lists.newArrayList(biomeRegistry);
    }

    public static List<BiomeId> getBiomeIds() {
        return BiomeUtils.getBiomeIds(BiomeUtils.getLocations());
    }

    public static List<BiomeId> getBiomeIds(Collection<ResourceLocation> collection) {
        ArrayList<BiomeId> arrayList = new ArrayList<BiomeId>();
        for (ResourceLocation resourceLocation : collection) {
            BiomeId biomeId = BiomeId.make(resourceLocation);
            if (biomeId == null) continue;
            arrayList.add(biomeId);
        }
        return arrayList;
    }

    public static Biome getBiome(IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos) {
        Biome biome = PLAINS;
        if (iBlockDisplayReader instanceof ChunkCacheOF) {
            biome = ((ChunkCacheOF)iBlockDisplayReader).getBiome(blockPos);
        } else if (iBlockDisplayReader instanceof IWorldReader) {
            biome = ((IWorldReader)iBlockDisplayReader).getBiome(blockPos);
        }
        return biome;
    }

    private static Biome lambda$onWorldChanged$5() {
        return BiomeMaker.makeGenericSwampBiome(-0.1f, 0.3f, true);
    }

    private static Biome lambda$onWorldChanged$4() {
        return BiomeMaker.makeGenericSwampBiome(-0.2f, 0.1f, false);
    }

    private static Biome lambda$onWorldChanged$3() {
        return BiomeMaker.makePlainsBiome(false);
    }

    private static Biome lambda$static$2() {
        return BiomeMaker.makeGenericSwampBiome(-0.1f, 0.3f, true);
    }

    private static Biome lambda$static$1() {
        return BiomeMaker.makeGenericSwampBiome(-0.2f, 0.1f, false);
    }

    private static Biome lambda$static$0() {
        return BiomeMaker.makePlainsBiome(false);
    }
}

