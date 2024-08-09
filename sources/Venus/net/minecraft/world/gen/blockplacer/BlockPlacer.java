/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.blockplacer;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;

public abstract class BlockPlacer {
    public static final Codec<BlockPlacer> CODEC = Registry.BLOCK_PLACER_TYPE.dispatch(BlockPlacer::getBlockPlacerType, BlockPlacerType::getCodec);

    public abstract void place(IWorld var1, BlockPos var2, BlockState var3, Random var4);

    protected abstract BlockPlacerType<?> getBlockPlacerType();
}

