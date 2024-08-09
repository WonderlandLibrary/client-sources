package dev.luvbeeq.baritone.api.command.datatypes;

import dev.luvbeeq.baritone.api.command.argparser.IArgParser;
import dev.luvbeeq.baritone.api.command.argument.IArgConsumer;
import dev.luvbeeq.baritone.api.command.exception.CommandException;

import java.util.stream.Stream;

/**
 * An {@link IDatatype} is similar to an {@link IArgParser} in the sense that it is capable of consuming an argument
 * to transform it into a usable form as the code desires.
 * <p>
 * A fundamental difference is that an {@link IDatatype} is capable of consuming multiple arguments. For example,
 * {@link RelativeBlockPos} is an {@link IDatatypePost} which requires at least 3 {@link RelativeCoordinate} arguments
 * to be specified.
 * <p>
 * Another difference is that an {@link IDatatype} can be tab-completed, providing comprehensive auto completion
 * that can substitute based on existing input or provide possibilities for the next piece of input.
 *
 * @see IDatatypeContext
 * @see IDatatypeFor
 * @see IDatatypePost
 */
public interface IDatatype {

    /**
     * Attempts to complete missing or partial input provided through the {@link IArgConsumer}} provided by
     * {@link IDatatypeContext#getConsumer()} in order to aide the user in executing commands.
     * <p>
     * One benefit over datatypes over {@link IArgParser}s is that instead of each command trying to guess what values
     * the datatype will accept, or simply not tab completing at all, datatypes that support tab completion can provide
     * accurate information using the same methods used to parse arguments in the first place.
     *
     * @param ctx The argument consumer to tab complete
     * @return A stream representing the strings that can be tab completed. DO NOT INCLUDE SPACES IN ANY STRINGS.
     * @see IArgConsumer#tabCompleteDatatype(IDatatype)
     */
    Stream<String> tabComplete(IDatatypeContext ctx) throws CommandException;
}
