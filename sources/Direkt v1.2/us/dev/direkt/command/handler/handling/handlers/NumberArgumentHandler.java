package us.dev.direkt.command.handler.handling.handlers;

import us.dev.direkt.command.handler.CommandManager;
import us.dev.direkt.command.handler.handling.parameter.CommandParameter;
import us.dev.direkt.command.handler.annotations.numeric.IntClamp;
import us.dev.direkt.command.handler.annotations.numeric.RealClamp;
import us.dev.direkt.command.handler.exceptions.ArgumentParseException;
import us.dev.direkt.command.handler.handling.ArgumentHandler;
import us.dev.direkt.command.handler.utils.Primitives;

import java.util.regex.Pattern;

/**
 * @author Foundry
 */
public final class NumberArgumentHandler<T extends Number> implements ArgumentHandler<T> {
    private static final Pattern hexValidator = Pattern.compile("^(-|\\+)?(0x|0X|#)[a-fA-F0-9]+$"); //require hex prefix

    @Override
    public final T parse(CommandManager commandManager, CommandParameter parameter, String input) throws ArgumentParseException {
        Class<? extends Number> numberClass; T resultNumber = parseNumber((numberClass = Primitives.wrap(parameter.getType())), input);

        if (numberClass == Float.class || numberClass == Double.class) {
            final RealClamp realClamp = parameter.getAnnotation(RealClamp.class);
            if (realClamp != null) {
                double min = Math.min(realClamp.min(), realClamp.max());
                double max = Math.max(realClamp.min(), realClamp.max());
                if (!Double.isNaN(min) && resultNumber.doubleValue() < min) {
                    resultNumber = (numberClass == Float.class ? (T) Float.valueOf((float) min) : (T) Double.valueOf(min));
                } else if (!Double.isNaN(max) && resultNumber.doubleValue() > max) {
                    resultNumber = (numberClass == Float.class ? (T) Float.valueOf((float) max) : (T) Double.valueOf(max));
                }
            }
        } else {
            final IntClamp intClamp = parameter.getAnnotation(IntClamp.class);
            if (intClamp != null) {
                Long min, max, typeMinimum, typeMaximum;
                if (resultNumber.longValue() < (min = Math.min(intClamp.min(), intClamp.max()))) {
                    resultNumber = parseNumber(numberClass, min.toString());
                } else if (resultNumber.longValue() > (max = (Math.max(intClamp.min(), intClamp.max())))) {
                    resultNumber = parseNumber(numberClass, max.toString());
                }
            }
        }
        return resultNumber;
    }

    @Override
    public final String getSyntax(CommandParameter parameter) {
        Class<? extends Number> numberClass; String suffix, prefix = (numberClass = Primitives.wrap(parameter.getType())).getSimpleName();
        if (numberClass == Float.class || numberClass == Double.class) {
            RealClamp realClamp = parameter.getAnnotation(RealClamp.class);
            if (realClamp == null) {
                return parameter.getLabel() + " - " + prefix;
            }
            double min = Math.min(realClamp.min(), realClamp.max());
            double max = Math.max(realClamp.min(), realClamp.max());
            suffix = !Double.isNaN(min) && !Double.isNaN(max) ? String.format("%s-%s", min, max) : (!Double.isNaN(min) ? ">" + min : (!Double.isNaN(max) ? "<" + max : "?"));
        } else {
            IntClamp intClamp = parameter.getAnnotation(IntClamp.class);
            if (intClamp == null) {
                return parameter.getLabel() + " - " + prefix;
            }
            long min = Math.min(intClamp.min(), intClamp.max());
            long max = Math.max(intClamp.min(), intClamp.max());
            suffix = Long.MIN_VALUE != min && Long.MAX_VALUE != max ? String.format("%s-%s", min, max) : (Long.MIN_VALUE != min ? ">" + min : (Long.MAX_VALUE != max ? "<" + max : "?"));
        }
        return parameter.getLabel() + " - " + String.format("%s[%s]", prefix, suffix);
    }

    @Override
    public Class[] getHandledTypes() {
        return new Class[] {Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class};
    }

    private T parseNumber(Class numberClass, String input) throws ArgumentParseException {
        Number resultNumber;
        try {
            if (numberClass == Byte.class) resultNumber = Byte.decode(input);
            else if (numberClass == Short.class) resultNumber = Short.decode(input);
            else if (numberClass == Integer.class) resultNumber = Integer.decode(input);
            else if (numberClass == Long.class) resultNumber = Long.decode(input);
            else if (numberClass == Float.class) resultNumber = hexValidator.matcher(input).matches() ? Integer.decode(input).floatValue() : Float.valueOf(input);
            else resultNumber = hexValidator.matcher(input).matches() ? Long.decode(input).doubleValue() : Double.valueOf(input);
        } catch (NumberFormatException e) {
            throw new ArgumentParseException(String.format("'%s' cannot be parsed to a %s: %s", input, numberClass.getSimpleName(), e.getMessage()));
        }
        return (T) resultNumber;
    }
}

