package dev.luvbeeq.baritone.api.behavior.look;

import dev.luvbeeq.baritone.api.utils.Rotation;

/**
 * @author Brady
 */
public interface ITickableAimProcessor extends IAimProcessor {

    /**
     * Advances the internal state of this aim processor by a single tick.
     */
    void tick();

    /**
     * Calls {@link #tick()} the specified number of times.
     *
     * @param ticks The number of calls
     */
    void advance(int ticks);

    /**
     * Returns the actual rotation as provided by {@link #peekRotation(Rotation)}, and then automatically advances the
     * internal state by one {@link #tick() tick}.
     *
     * @param rotation The desired rotation to set
     * @return The actual rotation
     */
    Rotation nextRotation(Rotation rotation);
}
