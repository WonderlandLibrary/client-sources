package us.dev.direkt.command.handler.exceptions;

import us.dev.direkt.command.handler.utils.Primitives;

/**
 * @author Foundry
 */
public final class UnsupportedParameterException extends RuntimeException {
    public UnsupportedParameterException(String message) {
        super(message);
    }

    public UnsupportedParameterException(Class<?> type) {
        super(String.format("%s is not a supported parameter type.", Primitives.wrap(type).getCanonicalName()));
    }
}

