package dev.luvbeeq.baritone.api.schematic.mask;

import dev.luvbeeq.baritone.api.schematic.mask.operator.BinaryOperatorMask;
import dev.luvbeeq.baritone.api.schematic.mask.operator.NotMask;
import dev.luvbeeq.baritone.api.utils.BooleanBinaryOperators;
import net.minecraft.block.BlockState;

/**
 * A mask that is context-free. In other words, it doesn't require the current block state to determine if a relative
 * position is a part of the mask.
 *
 * @author Brady
 */
public interface StaticMask extends Mask {

    /**
     * Determines if a given relative coordinate is included in this mask, without the need for the current block state.
     *
     * @param x The relative x position of the block
     * @param y The relative y position of the block
     * @param z The relative z position of the block
     * @return Whether the given position is included in this mask
     */
    boolean partOfMask(int x, int y, int z);

    /**
     * Implements the parent {@link Mask#partOfMask partOfMask function} by calling the static function
     * provided in this functional interface without needing the {@link BlockState} argument. This {@code default}
     * implementation should <b><u>NOT</u></b> be overriden.
     *
     * @param x            The relative x position of the block
     * @param y            The relative y position of the block
     * @param z            The relative z position of the block
     * @param currentState The current state of that block in the world, may be {@code null}
     * @return Whether the given position is included in this mask
     */
    @Override
    default boolean partOfMask(int x, int y, int z, BlockState currentState) {
        return this.partOfMask(x, y, z);
    }

    @Override
    default StaticMask not() {
        return new NotMask.Static(this);
    }

    default StaticMask union(StaticMask other) {
        return new BinaryOperatorMask.Static(this, other, BooleanBinaryOperators.OR);
    }

    default StaticMask intersection(StaticMask other) {
        return new BinaryOperatorMask.Static(this, other, BooleanBinaryOperators.AND);
    }

    default StaticMask xor(StaticMask other) {
        return new BinaryOperatorMask.Static(this, other, BooleanBinaryOperators.XOR);
    }

    /**
     * Returns a pre-computed mask using {@code this} function, with the specified size parameters.
     */
    default StaticMask compute() {
        return new PreComputedMask(this);
    }
}
