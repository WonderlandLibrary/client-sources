package dev.luvbeeq.baritone.api.command.datatypes;

import dev.luvbeeq.baritone.api.IBaritone;
import dev.luvbeeq.baritone.api.command.argument.IArgConsumer;

/**
 * Provides an {@link IDatatype} with contextual information so
 * that it can perform the desired operation on the target level.
 *
 * @author Brady
 * @see IDatatype
 * @since 9/26/2019
 */
public interface IDatatypeContext {

    /**
     * Provides the {@link IBaritone} instance that is associated with the action relating to datatype handling.
     *
     * @return The context {@link IBaritone} instance.
     */
    IBaritone getBaritone();

    /**
     * Provides the {@link IArgConsumer}} to fetch input information from.
     *
     * @return The context {@link IArgConsumer}}.
     */
    IArgConsumer getConsumer();
}
