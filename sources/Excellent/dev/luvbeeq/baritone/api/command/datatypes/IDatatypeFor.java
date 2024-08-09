package dev.luvbeeq.baritone.api.command.datatypes;

import dev.luvbeeq.baritone.api.command.exception.CommandException;

import java.util.function.Supplier;

/**
 * An {@link IDatatype} which acts as a {@link Supplier}, in essence. The only difference
 * is that it requires an {@link IDatatypeContext} to be provided due to the expectation that
 * implementations of {@link IDatatype} are singletons.
 */
public interface IDatatypeFor<T> extends IDatatype {

    /**
     * Consumes the desired amount of arguments from the specified {@link IDatatypeContext}, and
     * then returns the parsed value. This method will more than likely return a {@link IllegalArgumentException}
     * if the expected input does not conform to a parseable value. As far as a {@link CommandException} being
     * thrown is concerned, see the note below for specifics.
     *
     * @param ctx The context
     * @return The parsed data-type
     * @throws CommandException If there was an issue parsing using another type or arguments could not be polled.
     * @see IDatatypeContext
     */
    T get(IDatatypeContext ctx) throws CommandException;
}
