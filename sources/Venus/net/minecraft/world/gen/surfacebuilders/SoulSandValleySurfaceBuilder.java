/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.surfacebuilders;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.ValleySurfaceBuilder;

public class SoulSandValleySurfaceBuilder
extends ValleySurfaceBuilder {
    private static final BlockState field_237180_a_ = Blocks.SOUL_SAND.getDefaultState();
    private static final BlockState field_237181_b_ = Blocks.SOUL_SOIL.getDefaultState();
    private static final BlockState field_237182_c_ = Blocks.GRAVEL.getDefaultState();
    private static final ImmutableList<BlockState> field_237183_d_ = ImmutableList.of(field_237180_a_, field_237181_b_);

    public SoulSandValleySurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    protected ImmutableList<BlockState> func_230387_a_() {
        return field_237183_d_;
    }

    @Override
    protected ImmutableList<BlockState> func_230388_b_() {
        return field_237183_d_;
    }

    @Override
    protected BlockState func_230389_c_() {
        return field_237182_c_;
    }
}

