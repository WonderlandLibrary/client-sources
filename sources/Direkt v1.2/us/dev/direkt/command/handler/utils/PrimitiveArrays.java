package us.dev.direkt.command.handler.utils;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Foundry
 */
public enum PrimitiveArrays {
    BYTE(byte[].class, Byte[].class),
    SHORT(short[].class, Short[].class),
    INTEGER(int[].class, Integer[].class),
    LONG(long[].class, Long[].class),
    FLOAT(float[].class, Float[].class),
    DOUBLE(double[].class, Double[].class),
    BOOLEAN(boolean[].class, Boolean[].class),
    CHAR(char[].class, Character[].class);

    private static final Map<Class, Class> UNWRAPPED_TO_WRAPPED;
    private static final Map<Class, Class> WRAPPED_TO_UNWRAPPED;
    private final Class unwrapped;
    private final Class wrapped;

    static {
        UNWRAPPED_TO_WRAPPED = new HashMap<>();
        WRAPPED_TO_UNWRAPPED = new HashMap<>();
        PrimitiveArrays[] values;
        for (int i = 0; i < (values = values()).length; i++) {
            final PrimitiveArrays primitive = values[i];
            PrimitiveArrays.UNWRAPPED_TO_WRAPPED.put(primitive.unwrapped, primitive.wrapped);
            PrimitiveArrays.WRAPPED_TO_UNWRAPPED.put(primitive.wrapped, primitive.unwrapped);
        }
    }

    PrimitiveArrays(Class unwrapped, Class wrapped) {
        this.unwrapped = unwrapped;
        this.wrapped = wrapped;
    }

    public static Class wrap(Class unwrapped) {
        if (unwrapped.isArray()) {
            final Class wrapped = PrimitiveArrays.UNWRAPPED_TO_WRAPPED.get(unwrapped);
            return (wrapped != null) ? wrapped : unwrapped;
        } else {
            throw new RuntimeException("class \"" + unwrapped.getSimpleName() + "\" is not an array type");
        }
    }

    public static Class unwrap(Class wrapped) {
        final Class unwrapped = PrimitiveArrays.WRAPPED_TO_UNWRAPPED.get(wrapped);
        return (unwrapped != null) ? unwrapped : wrapped;
    }

    public static Object[] toObjects(Object val){
        if (val instanceof Object[])
            return (Object[] )val;
        int arrayLength = Array.getLength(val);
        Object[] outputArray = new Object[arrayLength];
        for (int i = 0; i < arrayLength; ++i) {
            outputArray[i] = Array.get(val, i);
        }
        return outputArray;
    }

    public static Object toPrimitives(Object[] val){
        Object outputArray = Array.newInstance(val.getClass().getComponentType(), val.length);
        for (int i = 0; i < val.length; ++i) {
            Array.set(outputArray, i, val[i]);
        }
        return outputArray;
    }
}

