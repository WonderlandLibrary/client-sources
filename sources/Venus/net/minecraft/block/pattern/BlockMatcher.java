/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block.pattern;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class BlockMatcher
implements Predicate<BlockState> {
    private final Block block;

    public BlockMatcher(Block block) {
        this.block = block;
    }

    public static BlockMatcher forBlock(Block block) {
        return new BlockMatcher(block);
    }

    @Override
    public boolean test(@Nullable BlockState blockState) {
        return blockState != null && blockState.isIn(this.block);
    }

    @Override
    public boolean test(@Nullable Object object) {
        return this.test((BlockState)object);
    }
}

