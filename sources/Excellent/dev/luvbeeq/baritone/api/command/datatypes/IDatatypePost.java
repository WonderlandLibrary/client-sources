package dev.luvbeeq.baritone.api.command.datatypes;

import dev.luvbeeq.baritone.api.command.exception.CommandException;

import java.util.function.Function;

/**
 * An {@link IDatatype} which acts as a {@link Function}, in essence. The only difference
 * is that it requires an {@link IDatatypeContext} to be provided due to the expectation that
 * implementations of {@link IDatatype} are singletons.
 */
public interface IDatatypePost<T, O> extends IDatatype {

    /**
     * Takes the expected input and transforms it based on the value held by {@code original}. If {@code original}
     * is null, it is expected that the implementation of this method has a case to handle it, such that a
     * {@link NullPointerException} will never be thrown as a result.
     *
     * @param ctx      The datatype context
     * @param original The transformable value
     * @return The transformed value
     */
    T apply(IDatatypeContext ctx, O original) throws CommandException;
}
