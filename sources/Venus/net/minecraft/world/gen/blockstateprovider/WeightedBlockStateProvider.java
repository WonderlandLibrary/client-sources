/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.blockstateprovider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.WeightedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;

public class WeightedBlockStateProvider
extends BlockStateProvider {
    public static final Codec<WeightedBlockStateProvider> CODEC = ((MapCodec)WeightedList.func_234002_a_(BlockState.CODEC).comapFlatMap(WeightedBlockStateProvider::encode, WeightedBlockStateProvider::lambda$static$0).fieldOf("entries")).codec();
    private final WeightedList<BlockState> weightedStates;

    private static DataResult<WeightedBlockStateProvider> encode(WeightedList<BlockState> weightedList) {
        return weightedList.func_234005_b_() ? DataResult.error("WeightedStateProvider with no states") : DataResult.success(new WeightedBlockStateProvider(weightedList));
    }

    private WeightedBlockStateProvider(WeightedList<BlockState> weightedList) {
        this.weightedStates = weightedList;
    }

    @Override
    protected BlockStateProviderType<?> getProviderType() {
        return BlockStateProviderType.WEIGHTED_STATE_PROVIDER;
    }

    public WeightedBlockStateProvider() {
        this(new WeightedList<BlockState>());
    }

    public WeightedBlockStateProvider addWeightedBlockstate(BlockState blockState, int n) {
        this.weightedStates.func_226313_a_(blockState, n);
        return this;
    }

    @Override
    public BlockState getBlockState(Random random2, BlockPos blockPos) {
        return this.weightedStates.func_226318_b_(random2);
    }

    private static WeightedList lambda$static$0(WeightedBlockStateProvider weightedBlockStateProvider) {
        return weightedBlockStateProvider.weightedStates;
    }
}

