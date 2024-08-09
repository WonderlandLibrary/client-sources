package dev.luvbeeq.baritone.api.command.exception;

import dev.luvbeeq.baritone.api.command.ICommand;
import dev.luvbeeq.baritone.api.command.argument.ICommandArgument;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

import static dev.luvbeeq.baritone.api.utils.Helper.HELPER;

/**
 * The base for a Baritone Command Exception, checked or unchecked. Provides a
 * {@link #handle(ICommand, List)} method that is used to provide useful output
 * to the user for diagnosing issues that may have occurred during execution.
 * <p>
 * Anything implementing this interface should be assignable to {@link Exception}.
 *
 * @author Brady
 * @since 9/20/2019
 */
public interface ICommandException {

    /**
     * @return The exception details
     * @see Exception#getMessage()
     */
    String getMessage();

    /**
     * Called when this exception is thrown, to handle the exception.
     *
     * @param command The command that threw it.
     * @param args    The arguments the command was called with.
     */
    default void handle(ICommand command, List<ICommandArgument> args) {
        HELPER.logDirect(this.getMessage(), TextFormatting.RED);
    }
}
