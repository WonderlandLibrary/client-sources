/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.multipart.ICondition;

public class ConditionAnd
implements ICondition {
    private final Iterable<ICondition> conditions;

    public ConditionAnd(Iterable<ICondition> conditionsIn) {
        this.conditions = conditionsIn;
    }

    @Override
    public Predicate<IBlockState> getPredicate(final BlockStateContainer blockState) {
        return Predicates.and(Iterables.transform(this.conditions, new Function<ICondition, Predicate<IBlockState>>(){

            @Override
            @Nullable
            public Predicate<IBlockState> apply(@Nullable ICondition p_apply_1_) {
                return p_apply_1_ == null ? null : p_apply_1_.getPredicate(blockState);
            }
        }));
    }
}

