package dev.luvbeeq.baritone.api.pathing.movement;

import dev.luvbeeq.baritone.api.utils.BetterBlockPos;
import net.minecraft.util.math.BlockPos;

/**
 * @author Brady
 * @since 10/8/2018
 */
public interface IMovement {

    double getCost();

    MovementStatus update();

    /**
     * Resets the current state status to {@link MovementStatus#PREPPING}
     */
    void reset();

    /**
     * Resets the cache for special break, place, and walk into blocks
     */
    void resetBlockCache();

    /**
     * @return Whether or not it is safe to cancel the current movement state
     */
    boolean safeToCancel();

    boolean calculatedWhileLoaded();

    BetterBlockPos getSrc();

    BetterBlockPos getDest();

    BlockPos getDirection();
}
