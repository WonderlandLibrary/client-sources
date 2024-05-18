package us.dev.direkt.command.handler.handling.handlers;

import us.dev.direkt.command.handler.CommandManager;
import us.dev.direkt.command.handler.handling.parameter.CommandParameter;
import us.dev.direkt.command.handler.annotations.strings.Equals;
import us.dev.direkt.command.handler.annotations.strings.Length;
import us.dev.direkt.command.handler.exceptions.ArgumentParseException;
import us.dev.direkt.command.handler.handling.ArgumentHandler;

import java.util.regex.Pattern;

/**
 * @author Foundry
 */
public final class StringArgumentHandler implements ArgumentHandler<String> {
    @Override
    public String parse(CommandManager commandManager, CommandParameter parameter, String input) throws ArgumentParseException {
        if (input.length() == 0) {
            return null;
        }
        Equals equals = parameter.getAnnotation(Equals.class);
        if (equals != null) {
            for (String string : equals.value()) {
                if (!(equals.regex() ? Pattern.compile(string).matcher(input).matches() : string.equalsIgnoreCase(input))) continue;
                return input;
            }
            throw new ArgumentParseException(String.format("'%s' doesn't match %s (regex=%s)", input, String.format("['%s']", String.join("'/'", equals.value())), equals.regex()));
        }
        Length length = parameter.getAnnotation(Length.class);
        if (length != null) {
            double min = Math.min(length.min(), length.max());
            double max = Math.max(length.min(), length.max());
            if (min < (double)input.length() || max > (double)input.length()) {
                throw new ArgumentParseException(String.format("'%s' is either too long or too short. (%s-%s)", input, min, max));
            }
        }
        return input;
    }

    @Override
    public String getSyntax(CommandParameter parameter) {
        Equals equals = parameter.getAnnotation(Equals.class);
        if (equals != null) {
            return parameter.getLabel() + " - " + String.format("String['%s', regex=%s]", String.join("'/'", equals.value()), equals.regex());
        }
        Length length = parameter.getAnnotation(Length.class);
        if (length != null) {
            return parameter.getLabel() + " - " + String.format("String[%s-%s]", Math.min(length.min(), length.max()), Math.max(length.min(), length.max()));
        }
        return parameter.getLabel() + " - " + "String";
    }

    @Override
    public Class[] getHandledTypes() {
        return new Class[] {String.class};
    }
}

