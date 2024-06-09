package us.dev.direkt.command.handler.handling;

import us.dev.direkt.command.handler.CommandManager;
import us.dev.direkt.command.handler.handling.parameter.CommandParameter;
import us.dev.direkt.command.handler.exceptions.ArgumentParseException;

/**
 * @author Foundry
 */
public interface ArgumentHandler<T> {
    T parse(CommandManager commandManager, CommandParameter parameter, String input) throws ArgumentParseException;

    String getSyntax(CommandParameter parameter);

    Class[] getHandledTypes();
}

