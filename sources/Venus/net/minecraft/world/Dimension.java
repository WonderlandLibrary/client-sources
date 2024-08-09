/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.biome.provider.NetherBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;

public final class Dimension {
    public static final Codec<Dimension> CODEC = RecordCodecBuilder.create(Dimension::lambda$static$0);
    public static final RegistryKey<Dimension> OVERWORLD = RegistryKey.getOrCreateKey(Registry.DIMENSION_KEY, new ResourceLocation("overworld"));
    public static final RegistryKey<Dimension> THE_NETHER = RegistryKey.getOrCreateKey(Registry.DIMENSION_KEY, new ResourceLocation("the_nether"));
    public static final RegistryKey<Dimension> THE_END = RegistryKey.getOrCreateKey(Registry.DIMENSION_KEY, new ResourceLocation("the_end"));
    private static final LinkedHashSet<RegistryKey<Dimension>> DIMENSION_KEYS = Sets.newLinkedHashSet(ImmutableList.of(OVERWORLD, THE_NETHER, THE_END));
    private final Supplier<DimensionType> dimensionTypeSupplier;
    private final ChunkGenerator chunkGenerator;

    public Dimension(Supplier<DimensionType> supplier, ChunkGenerator chunkGenerator) {
        this.dimensionTypeSupplier = supplier;
        this.chunkGenerator = chunkGenerator;
    }

    public Supplier<DimensionType> getDimensionTypeSupplier() {
        return this.dimensionTypeSupplier;
    }

    public DimensionType getDimensionType() {
        return this.dimensionTypeSupplier.get();
    }

    public ChunkGenerator getChunkGenerator() {
        return this.chunkGenerator;
    }

    public static SimpleRegistry<Dimension> func_236062_a_(SimpleRegistry<Dimension> simpleRegistry) {
        Object object;
        SimpleRegistry<Dimension> simpleRegistry2 = new SimpleRegistry<Dimension>(Registry.DIMENSION_KEY, Lifecycle.experimental());
        for (RegistryKey object2 : DIMENSION_KEYS) {
            object = simpleRegistry.getValueForKey(object2);
            if (object == null) continue;
            simpleRegistry2.register(object2, object, simpleRegistry.getLifecycleByRegistry((Dimension)object));
        }
        for (Map.Entry entry : simpleRegistry.getEntries()) {
            object = (RegistryKey)entry.getKey();
            if (DIMENSION_KEYS.contains(object)) continue;
            simpleRegistry2.register((RegistryKey<Dimension>)object, (Dimension)entry.getValue(), simpleRegistry.getLifecycleByRegistry((Dimension)entry.getValue()));
        }
        return simpleRegistry2;
    }

    public static boolean func_236060_a_(long l, SimpleRegistry<Dimension> simpleRegistry) {
        ArrayList<Map.Entry<RegistryKey<Dimension>, Dimension>> arrayList = Lists.newArrayList(simpleRegistry.getEntries());
        if (arrayList.size() != DIMENSION_KEYS.size()) {
            return true;
        }
        Map.Entry entry = (Map.Entry)arrayList.get(0);
        Map.Entry entry2 = (Map.Entry)arrayList.get(1);
        Map.Entry entry3 = (Map.Entry)arrayList.get(2);
        if (entry.getKey() == OVERWORLD && entry2.getKey() == THE_NETHER && entry3.getKey() == THE_END) {
            if (!((Dimension)entry.getValue()).getDimensionType().isSame(DimensionType.OVERWORLD_TYPE) && ((Dimension)entry.getValue()).getDimensionType() != DimensionType.OVERWORLD_CAVES_TYPE) {
                return true;
            }
            if (!((Dimension)entry2.getValue()).getDimensionType().isSame(DimensionType.NETHER_TYPE)) {
                return true;
            }
            if (!((Dimension)entry3.getValue()).getDimensionType().isSame(DimensionType.END_TYPE)) {
                return true;
            }
            if (((Dimension)entry2.getValue()).getChunkGenerator() instanceof NoiseChunkGenerator && ((Dimension)entry3.getValue()).getChunkGenerator() instanceof NoiseChunkGenerator) {
                NoiseChunkGenerator noiseChunkGenerator = (NoiseChunkGenerator)((Dimension)entry2.getValue()).getChunkGenerator();
                NoiseChunkGenerator noiseChunkGenerator2 = (NoiseChunkGenerator)((Dimension)entry3.getValue()).getChunkGenerator();
                if (!noiseChunkGenerator.func_236088_a_(l, DimensionSettings.field_242736_e)) {
                    return true;
                }
                if (!noiseChunkGenerator2.func_236088_a_(l, DimensionSettings.field_242737_f)) {
                    return true;
                }
                if (!(noiseChunkGenerator.getBiomeProvider() instanceof NetherBiomeProvider)) {
                    return true;
                }
                NetherBiomeProvider netherBiomeProvider = (NetherBiomeProvider)noiseChunkGenerator.getBiomeProvider();
                if (!netherBiomeProvider.isDefaultPreset(l)) {
                    return true;
                }
                if (!(noiseChunkGenerator2.getBiomeProvider() instanceof EndBiomeProvider)) {
                    return true;
                }
                EndBiomeProvider endBiomeProvider = (EndBiomeProvider)noiseChunkGenerator2.getBiomeProvider();
                return endBiomeProvider.areProvidersEqual(l);
            }
            return true;
        }
        return true;
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)DimensionType.DIMENSION_TYPE_CODEC.fieldOf("type")).forGetter(Dimension::getDimensionTypeSupplier), ((MapCodec)ChunkGenerator.field_235948_a_.fieldOf("generator")).forGetter(Dimension::getChunkGenerator)).apply(instance, instance.stable(Dimension::new));
    }
}

