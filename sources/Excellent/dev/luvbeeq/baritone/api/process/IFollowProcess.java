package dev.luvbeeq.baritone.api.process;

import net.minecraft.entity.Entity;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author Brady
 * @since 9/23/2018
 */
public interface IFollowProcess extends IBaritoneProcess {

    /**
     * Set the follow target to any entities matching this predicate
     *
     * @param filter the predicate
     */
    void follow(Predicate<Entity> filter);

    /**
     * @return The entities that are currently being followed. null if not currently following, empty if nothing matches the predicate
     */
    List<Entity> following();

    Predicate<Entity> currentFilter();

    /**
     * Cancels the follow behavior, this will clear the current follow target.
     */
    default void cancel() {
        onLostControl();
    }
}
