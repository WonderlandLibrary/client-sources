/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.blockstateprovider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;

public class SimpleBlockStateProvider
extends BlockStateProvider {
    public static final Codec<SimpleBlockStateProvider> CODEC = ((MapCodec)BlockState.CODEC.fieldOf("state")).xmap(SimpleBlockStateProvider::new, SimpleBlockStateProvider::lambda$static$0).codec();
    private final BlockState state;

    public SimpleBlockStateProvider(BlockState blockState) {
        this.state = blockState;
    }

    @Override
    protected BlockStateProviderType<?> getProviderType() {
        return BlockStateProviderType.SIMPLE_STATE_PROVIDER;
    }

    @Override
    public BlockState getBlockState(Random random2, BlockPos blockPos) {
        return this.state;
    }

    private static BlockState lambda$static$0(SimpleBlockStateProvider simpleBlockStateProvider) {
        return simpleBlockStateProvider.state;
    }
}

