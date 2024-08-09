package dev.luvbeeq.baritone.api.command.argparser;

import dev.luvbeeq.baritone.api.command.argument.ICommandArgument;
import dev.luvbeeq.baritone.api.command.exception.CommandInvalidTypeException;
import dev.luvbeeq.baritone.api.command.registry.Registry;

/**
 * Used to retrieve {@link IArgParser} instances from the registry, by their target class.
 * It can be assumed that a {@link IArgParser} exists for {@link Integer}, {@link Long},
 * {@link Float}, {@link Double} and {@link Boolean}.
 *
 * @author Brady
 * @since 10/4/2019
 */
public interface IArgParserManager {

    /**
     * @param type The type trying to be parsed
     * @return A parser that can parse arguments into this class, if found.
     */
    <T> IArgParser.Stateless<T> getParserStateless(Class<T> type);

    /**
     * @param type The type trying to be parsed
     * @return A parser that can parse arguments into this class, if found.
     */
    <T, S> IArgParser.Stated<T, S> getParserStated(Class<T> type, Class<S> stateKlass);

    /**
     * Attempt to parse the specified argument with a stateless {@link IArgParser} that outputs the specified class.
     *
     * @param type The type to try and parse the argument into.
     * @param arg  The argument to parse.
     * @return An instance of the specified class.
     * @throws CommandInvalidTypeException If the parsing failed
     */
    <T> T parseStateless(Class<T> type, ICommandArgument arg) throws CommandInvalidTypeException;

    /**
     * Attempt to parse the specified argument with a stated {@link IArgParser} that outputs the specified class.
     *
     * @param type  The type to try and parse the argument into.
     * @param arg   The argument to parse.
     * @param state The state to pass to the {@link IArgParser.Stated}.
     * @return An instance of the specified class.
     * @throws CommandInvalidTypeException If the parsing failed
     * @see IArgParser.Stated
     */
    <T, S> T parseStated(Class<T> type, Class<S> stateKlass, ICommandArgument arg, S state) throws CommandInvalidTypeException;

    Registry<IArgParser> getRegistry();
}
