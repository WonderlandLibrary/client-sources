/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model.multipart;

import java.util.function.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;

@FunctionalInterface
public interface ICondition {
    public static final ICondition TRUE = ICondition::lambda$static$1;
    public static final ICondition FALSE = ICondition::lambda$static$3;

    public Predicate<BlockState> getPredicate(StateContainer<Block, BlockState> var1);

    private static Predicate lambda$static$3(StateContainer stateContainer) {
        return ICondition::lambda$static$2;
    }

    private static boolean lambda$static$2(BlockState blockState) {
        return true;
    }

    private static Predicate lambda$static$1(StateContainer stateContainer) {
        return ICondition::lambda$static$0;
    }

    private static boolean lambda$static$0(BlockState blockState) {
        return false;
    }
}

