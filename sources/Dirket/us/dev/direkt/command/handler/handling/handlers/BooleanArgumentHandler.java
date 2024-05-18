package us.dev.direkt.command.handler.handling.handlers;

import us.dev.direkt.command.handler.CommandManager;
import us.dev.direkt.command.handler.handling.parameter.CommandParameter;
import us.dev.direkt.command.handler.exceptions.ArgumentParseException;
import us.dev.direkt.command.handler.handling.ArgumentHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Foundry
 */
public final class BooleanArgumentHandler implements ArgumentHandler<Boolean> {
    private static final Map<String, Boolean> VALUES = new HashMap<>();

    @Override
    public Boolean parse(CommandManager commandManager, CommandParameter parameter, String input) throws ArgumentParseException {
        Boolean value = VALUES.get(input.toLowerCase());
        if (value == null) {
            throw new ArgumentParseException(String.format("'%s' cannot be parsed to a boolean.", input));
        }
        return value;
    }

    @Override
    public String getSyntax(CommandParameter parameter) {
        return parameter.getLabel() + " - " + "Boolean";
    }

    @Override
    public Class[] getHandledTypes() {
        return new Class[] {Boolean.class};
    }

    static {
        Arrays.asList("true", "1", "enabled", "on").forEach(x -> VALUES.put(x, true));
        Arrays.asList("false", "0", "disabled", "off").forEach(x -> VALUES.put(x, false));
    }

}

