package us.dev.direkt.command.handler.handling.handlers;

import us.dev.direkt.command.handler.CommandManager;
import us.dev.direkt.command.handler.handling.parameter.CommandParameter;
import us.dev.direkt.command.handler.exceptions.ArgumentParseException;
import us.dev.direkt.command.handler.handling.ArgumentHandler;

import java.util.Arrays;

/**
 * @author Foundry
 */
public final class EnumArgumentHandler<T extends Enum> implements ArgumentHandler<T> {

    @Override
    public T parse(CommandManager commandManager, CommandParameter parameter, String input) throws ArgumentParseException {
        Enum[] enumConstants = (Enum[]) parameter.getType().getEnumConstants();
        for (Enum e : enumConstants) {
            if (e.toString().equalsIgnoreCase(input) || e.name().equalsIgnoreCase(input)) return (T) e;
        }
        throw new ArgumentParseException("argument \"" + input + "\" not a value in "
                + parameter.getType().getSimpleName() + " " + Arrays.toString(parameter.getType().getEnumConstants()));
    }

    @Override
    public String getSyntax(CommandParameter parameter) {
        return parameter.getLabel() + " - " + "One of " + Arrays.toString(parameter.getType().getEnumConstants());
    }

    @Override
    public Class[] getHandledTypes() {
        return new Class[] {Enum.class};
    }

}

