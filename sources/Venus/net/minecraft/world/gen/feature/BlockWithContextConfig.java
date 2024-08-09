/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BlockWithContextConfig
implements IFeatureConfig {
    public static final Codec<BlockWithContextConfig> field_236636_a_ = RecordCodecBuilder.create(BlockWithContextConfig::lambda$static$4);
    public final BlockState toPlace;
    public final List<BlockState> placeOn;
    public final List<BlockState> placeIn;
    public final List<BlockState> placeUnder;

    public BlockWithContextConfig(BlockState blockState, List<BlockState> list, List<BlockState> list2, List<BlockState> list3) {
        this.toPlace = blockState;
        this.placeOn = list;
        this.placeIn = list2;
        this.placeUnder = list3;
    }

    private static App lambda$static$4(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)BlockState.CODEC.fieldOf("to_place")).forGetter(BlockWithContextConfig::lambda$static$0), ((MapCodec)BlockState.CODEC.listOf().fieldOf("place_on")).forGetter(BlockWithContextConfig::lambda$static$1), ((MapCodec)BlockState.CODEC.listOf().fieldOf("place_in")).forGetter(BlockWithContextConfig::lambda$static$2), ((MapCodec)BlockState.CODEC.listOf().fieldOf("place_under")).forGetter(BlockWithContextConfig::lambda$static$3)).apply(instance, BlockWithContextConfig::new);
    }

    private static List lambda$static$3(BlockWithContextConfig blockWithContextConfig) {
        return blockWithContextConfig.placeUnder;
    }

    private static List lambda$static$2(BlockWithContextConfig blockWithContextConfig) {
        return blockWithContextConfig.placeIn;
    }

    private static List lambda$static$1(BlockWithContextConfig blockWithContextConfig) {
        return blockWithContextConfig.placeOn;
    }

    private static BlockState lambda$static$0(BlockWithContextConfig blockWithContextConfig) {
        return blockWithContextConfig.toPlace;
    }
}

