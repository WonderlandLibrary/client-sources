package net.minecraft.client.renderer.model.multipart;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;

import java.util.function.Predicate;

@FunctionalInterface
public interface ICondition
{
    ICondition TRUE = (container) ->
    {
        return (state) -> {
            return true;
        };
    };
    ICondition FALSE = (container) ->
    {
        return (state) -> {
            return false;
        };
    };

    Predicate<BlockState> getPredicate(StateContainer<Block, BlockState> p_getPredicate_1_);
}
