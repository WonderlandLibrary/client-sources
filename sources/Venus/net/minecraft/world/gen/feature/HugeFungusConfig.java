/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class HugeFungusConfig
implements IFeatureConfig {
    public static final Codec<HugeFungusConfig> field_236298_a_ = RecordCodecBuilder.create(HugeFungusConfig::lambda$static$5);
    public static final HugeFungusConfig field_236299_b_ = new HugeFungusConfig(Blocks.CRIMSON_NYLIUM.getDefaultState(), Blocks.CRIMSON_STEM.getDefaultState(), Blocks.NETHER_WART_BLOCK.getDefaultState(), Blocks.SHROOMLIGHT.getDefaultState(), true);
    public static final HugeFungusConfig field_236300_c_;
    public static final HugeFungusConfig field_236301_d_;
    public static final HugeFungusConfig field_236302_e_;
    public final BlockState field_236303_f_;
    public final BlockState field_236304_g_;
    public final BlockState field_236305_h_;
    public final BlockState field_236306_i_;
    public final boolean field_236307_j_;

    public HugeFungusConfig(BlockState blockState, BlockState blockState2, BlockState blockState3, BlockState blockState4, boolean bl) {
        this.field_236303_f_ = blockState;
        this.field_236304_g_ = blockState2;
        this.field_236305_h_ = blockState3;
        this.field_236306_i_ = blockState4;
        this.field_236307_j_ = bl;
    }

    private static App lambda$static$5(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)BlockState.CODEC.fieldOf("valid_base_block")).forGetter(HugeFungusConfig::lambda$static$0), ((MapCodec)BlockState.CODEC.fieldOf("stem_state")).forGetter(HugeFungusConfig::lambda$static$1), ((MapCodec)BlockState.CODEC.fieldOf("hat_state")).forGetter(HugeFungusConfig::lambda$static$2), ((MapCodec)BlockState.CODEC.fieldOf("decor_state")).forGetter(HugeFungusConfig::lambda$static$3), ((MapCodec)Codec.BOOL.fieldOf("planted")).orElse(false).forGetter(HugeFungusConfig::lambda$static$4)).apply(instance, HugeFungusConfig::new);
    }

    private static Boolean lambda$static$4(HugeFungusConfig hugeFungusConfig) {
        return hugeFungusConfig.field_236307_j_;
    }

    private static BlockState lambda$static$3(HugeFungusConfig hugeFungusConfig) {
        return hugeFungusConfig.field_236306_i_;
    }

    private static BlockState lambda$static$2(HugeFungusConfig hugeFungusConfig) {
        return hugeFungusConfig.field_236305_h_;
    }

    private static BlockState lambda$static$1(HugeFungusConfig hugeFungusConfig) {
        return hugeFungusConfig.field_236304_g_;
    }

    private static BlockState lambda$static$0(HugeFungusConfig hugeFungusConfig) {
        return hugeFungusConfig.field_236303_f_;
    }

    static {
        field_236301_d_ = new HugeFungusConfig(Blocks.WARPED_NYLIUM.getDefaultState(), Blocks.WARPED_STEM.getDefaultState(), Blocks.WARPED_WART_BLOCK.getDefaultState(), Blocks.SHROOMLIGHT.getDefaultState(), true);
        field_236300_c_ = new HugeFungusConfig(HugeFungusConfig.field_236299_b_.field_236303_f_, HugeFungusConfig.field_236299_b_.field_236304_g_, HugeFungusConfig.field_236299_b_.field_236305_h_, HugeFungusConfig.field_236299_b_.field_236306_i_, false);
        field_236302_e_ = new HugeFungusConfig(HugeFungusConfig.field_236301_d_.field_236303_f_, HugeFungusConfig.field_236301_d_.field_236304_g_, HugeFungusConfig.field_236301_d_.field_236305_h_, HugeFungusConfig.field_236301_d_.field_236306_i_, false);
    }
}

