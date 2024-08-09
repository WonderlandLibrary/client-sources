package dev.luvbeeq.baritone.api.behavior.look;

import dev.luvbeeq.baritone.api.utils.Rotation;

/**
 * @author Brady
 */
public interface IAimProcessor {

    /**
     * Returns the actual rotation that will be used when the desired rotation is requested. The returned rotation
     * always reflects what would happen in the upcoming tick. In other words, it is a pure function, and no internal
     * state changes. If simulation of the rotation states beyond the next tick is required, then a
     * {@link IAimProcessor#fork fork} should be created.
     *
     * @param desired The desired rotation to set
     * @return The actual rotation
     */
    Rotation peekRotation(Rotation desired);

    /**
     * Returns a copy of this {@link IAimProcessor} which has its own internal state and is manually tickable.
     *
     * @return The forked processor
     * @see ITickableAimProcessor
     */
    ITickableAimProcessor fork();
}
