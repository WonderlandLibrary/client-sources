/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model.multipart;

import com.google.common.collect.Streams;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.multipart.ICondition;
import net.minecraft.state.StateContainer;

public class OrCondition
implements ICondition {
    private final Iterable<? extends ICondition> conditions;

    public OrCondition(Iterable<? extends ICondition> iterable) {
        this.conditions = iterable;
    }

    @Override
    public Predicate<BlockState> getPredicate(StateContainer<Block, BlockState> stateContainer) {
        List list = Streams.stream(this.conditions).map(arg_0 -> OrCondition.lambda$getPredicate$0(stateContainer, arg_0)).collect(Collectors.toList());
        return arg_0 -> OrCondition.lambda$getPredicate$2(list, arg_0);
    }

    private static boolean lambda$getPredicate$2(List list, BlockState blockState) {
        return list.stream().anyMatch(arg_0 -> OrCondition.lambda$getPredicate$1(blockState, arg_0));
    }

    private static boolean lambda$getPredicate$1(BlockState blockState, Predicate predicate) {
        return predicate.test(blockState);
    }

    private static Predicate lambda$getPredicate$0(StateContainer stateContainer, ICondition iCondition) {
        return iCondition.getPredicate(stateContainer);
    }
}

