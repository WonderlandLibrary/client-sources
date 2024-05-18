package us.dev.direkt.command.handler.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Foundry
 */
public enum Primitives {
    BYTE(Byte.TYPE, Byte.class),
    SHORT(Short.TYPE, Short.class),
    INTEGER(Integer.TYPE, Integer.class),
    LONG(Long.TYPE, Long.class),
    FLOAT(Float.TYPE, Float.class),
    DOUBLE(Double.TYPE, Double.class),
    BOOLEAN(Boolean.TYPE, Boolean.class),
    CHAR(Character.TYPE, Character.class);

    private static final Map<Class, Class> UNWRAPPED_TO_WRAPPED;
    private static final Map<Class, Class> WRAPPED_TO_UNWRAPPED;
    private final Class unwrapped;
    private final Class wrapped;

    static {
        UNWRAPPED_TO_WRAPPED = new HashMap<>();
        WRAPPED_TO_UNWRAPPED = new HashMap<>();
        Primitives[] values;
        for (int i = 0; i < (values = values()).length; i++) {
            final Primitives primitive = values[i];
            Primitives.UNWRAPPED_TO_WRAPPED.put(primitive.unwrapped, primitive.wrapped);
            Primitives.WRAPPED_TO_UNWRAPPED.put(primitive.wrapped, primitive.unwrapped);
        }
    }

    Primitives(Class unwrapped, Class wrapped) {
        this.unwrapped = unwrapped;
        this.wrapped = wrapped;
    }

    public static Class wrap(Class unwrapped) {
        final Class wrapped = Primitives.UNWRAPPED_TO_WRAPPED.get(unwrapped);
        return (wrapped != null) ? wrapped : unwrapped;
    }

    public static Class unwrap(Class wrapped) {
        final Class unwrapped = Primitives.WRAPPED_TO_UNWRAPPED.get(wrapped);
        return (unwrapped != null) ? unwrapped : wrapped;
    }
}

