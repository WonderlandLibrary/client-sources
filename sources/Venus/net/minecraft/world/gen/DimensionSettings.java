/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen;

import com.google.common.collect.Maps;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKeyCodec;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.NoiseSettings;
import net.minecraft.world.gen.settings.ScalingSettings;
import net.minecraft.world.gen.settings.SlideSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

public final class DimensionSettings {
    public static final Codec<DimensionSettings> field_236097_a_ = RecordCodecBuilder.create(DimensionSettings::lambda$static$0);
    public static final Codec<Supplier<DimensionSettings>> field_236098_b_ = RegistryKeyCodec.create(Registry.NOISE_SETTINGS_KEY, field_236097_a_);
    private final DimensionStructuresSettings structures;
    private final NoiseSettings noise;
    private final BlockState defaultBlock;
    private final BlockState defaultFluid;
    private final int field_236103_g_;
    private final int field_236104_h_;
    private final int field_236105_i_;
    private final boolean field_236106_j_;
    public static final RegistryKey<DimensionSettings> field_242734_c = RegistryKey.getOrCreateKey(Registry.NOISE_SETTINGS_KEY, new ResourceLocation("overworld"));
    public static final RegistryKey<DimensionSettings> field_242735_d = RegistryKey.getOrCreateKey(Registry.NOISE_SETTINGS_KEY, new ResourceLocation("amplified"));
    public static final RegistryKey<DimensionSettings> field_242736_e = RegistryKey.getOrCreateKey(Registry.NOISE_SETTINGS_KEY, new ResourceLocation("nether"));
    public static final RegistryKey<DimensionSettings> field_242737_f = RegistryKey.getOrCreateKey(Registry.NOISE_SETTINGS_KEY, new ResourceLocation("end"));
    public static final RegistryKey<DimensionSettings> field_242738_g = RegistryKey.getOrCreateKey(Registry.NOISE_SETTINGS_KEY, new ResourceLocation("caves"));
    public static final RegistryKey<DimensionSettings> field_242739_h = RegistryKey.getOrCreateKey(Registry.NOISE_SETTINGS_KEY, new ResourceLocation("floating_islands"));
    private static final DimensionSettings field_242740_q = DimensionSettings.func_242745_a(field_242734_c, DimensionSettings.func_242743_a(new DimensionStructuresSettings(true), false, field_242734_c.getLocation()));

    private DimensionSettings(DimensionStructuresSettings dimensionStructuresSettings, NoiseSettings noiseSettings, BlockState blockState, BlockState blockState2, int n, int n2, int n3, boolean bl) {
        this.structures = dimensionStructuresSettings;
        this.noise = noiseSettings;
        this.defaultBlock = blockState;
        this.defaultFluid = blockState2;
        this.field_236103_g_ = n;
        this.field_236104_h_ = n2;
        this.field_236105_i_ = n3;
        this.field_236106_j_ = bl;
    }

    public DimensionStructuresSettings getStructures() {
        return this.structures;
    }

    public NoiseSettings getNoise() {
        return this.noise;
    }

    public BlockState getDefaultBlock() {
        return this.defaultBlock;
    }

    public BlockState getDefaultFluid() {
        return this.defaultFluid;
    }

    public int func_236117_e_() {
        return this.field_236103_g_;
    }

    public int func_236118_f_() {
        return this.field_236104_h_;
    }

    public int func_236119_g_() {
        return this.field_236105_i_;
    }

    @Deprecated
    protected boolean func_236120_h_() {
        return this.field_236106_j_;
    }

    public boolean func_242744_a(RegistryKey<DimensionSettings> registryKey) {
        return Objects.equals(this, WorldGenRegistries.NOISE_SETTINGS.getValueForKey(registryKey));
    }

    private static DimensionSettings func_242745_a(RegistryKey<DimensionSettings> registryKey, DimensionSettings dimensionSettings) {
        WorldGenRegistries.register(WorldGenRegistries.NOISE_SETTINGS, registryKey.getLocation(), dimensionSettings);
        return dimensionSettings;
    }

    public static DimensionSettings func_242746_i() {
        return field_242740_q;
    }

    private static DimensionSettings func_242742_a(DimensionStructuresSettings dimensionStructuresSettings, BlockState blockState, BlockState blockState2, ResourceLocation resourceLocation, boolean bl, boolean bl2) {
        return new DimensionSettings(dimensionStructuresSettings, new NoiseSettings(128, new ScalingSettings(2.0, 1.0, 80.0, 160.0), new SlideSettings(-3000, 64, -46), new SlideSettings(-30, 7, 1), 2, 1, 0.0, 0.0, true, false, bl2, false), blockState, blockState2, -10, -10, 0, bl);
    }

    private static DimensionSettings func_242741_a(DimensionStructuresSettings dimensionStructuresSettings, BlockState blockState, BlockState blockState2, ResourceLocation resourceLocation) {
        HashMap<Structure<?>, StructureSeparationSettings> hashMap = Maps.newHashMap(DimensionStructuresSettings.field_236191_b_);
        hashMap.put(Structure.field_236372_h_, new StructureSeparationSettings(25, 10, 34222645));
        return new DimensionSettings(new DimensionStructuresSettings(Optional.ofNullable(dimensionStructuresSettings.func_236199_b_()), hashMap), new NoiseSettings(128, new ScalingSettings(1.0, 3.0, 80.0, 60.0), new SlideSettings(120, 3, 0), new SlideSettings(320, 4, -1), 1, 2, 0.0, 0.019921875, false, false, false, false), blockState, blockState2, 0, 0, 32, false);
    }

    private static DimensionSettings func_242743_a(DimensionStructuresSettings dimensionStructuresSettings, boolean bl, ResourceLocation resourceLocation) {
        double d = 0.9999999814507745;
        return new DimensionSettings(dimensionStructuresSettings, new NoiseSettings(256, new ScalingSettings(0.9999999814507745, 0.9999999814507745, 80.0, 160.0), new SlideSettings(-10, 3, 0), new SlideSettings(-30, 0, 0), 1, 2, 1.0, -0.46875, true, true, false, bl), Blocks.STONE.getDefaultState(), Blocks.WATER.getDefaultState(), -10, 0, 63, false);
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)DimensionStructuresSettings.field_236190_a_.fieldOf("structures")).forGetter(DimensionSettings::getStructures), ((MapCodec)NoiseSettings.field_236156_a_.fieldOf("noise")).forGetter(DimensionSettings::getNoise), ((MapCodec)BlockState.CODEC.fieldOf("default_block")).forGetter(DimensionSettings::getDefaultBlock), ((MapCodec)BlockState.CODEC.fieldOf("default_fluid")).forGetter(DimensionSettings::getDefaultFluid), ((MapCodec)Codec.intRange(-20, 276).fieldOf("bedrock_roof_position")).forGetter(DimensionSettings::func_236117_e_), ((MapCodec)Codec.intRange(-20, 276).fieldOf("bedrock_floor_position")).forGetter(DimensionSettings::func_236118_f_), ((MapCodec)Codec.intRange(0, 255).fieldOf("sea_level")).forGetter(DimensionSettings::func_236119_g_), ((MapCodec)Codec.BOOL.fieldOf("disable_mob_generation")).forGetter(DimensionSettings::func_236120_h_)).apply(instance, DimensionSettings::new);
    }

    static {
        DimensionSettings.func_242745_a(field_242735_d, DimensionSettings.func_242743_a(new DimensionStructuresSettings(true), true, field_242735_d.getLocation()));
        DimensionSettings.func_242745_a(field_242736_e, DimensionSettings.func_242741_a(new DimensionStructuresSettings(false), Blocks.NETHERRACK.getDefaultState(), Blocks.LAVA.getDefaultState(), field_242736_e.getLocation()));
        DimensionSettings.func_242745_a(field_242737_f, DimensionSettings.func_242742_a(new DimensionStructuresSettings(false), Blocks.END_STONE.getDefaultState(), Blocks.AIR.getDefaultState(), field_242737_f.getLocation(), true, true));
        DimensionSettings.func_242745_a(field_242738_g, DimensionSettings.func_242741_a(new DimensionStructuresSettings(true), Blocks.STONE.getDefaultState(), Blocks.WATER.getDefaultState(), field_242738_g.getLocation()));
        DimensionSettings.func_242745_a(field_242739_h, DimensionSettings.func_242742_a(new DimensionStructuresSettings(true), Blocks.STONE.getDefaultState(), Blocks.WATER.getDefaultState(), field_242739_h.getLocation(), false, false));
    }
}

