package dev.luvbeeq.baritone.api.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Brady
 * @since 4/20/2019
 */
public final class TypeUtils {

    private TypeUtils() {
    }

    /**
     * Resolves the "base type" for the specified type. For example, if the specified
     * type is {@code List<String>}, then {@code List.class} will be returned. If the
     * specified type is already a class, then it is directly returned.
     *
     * @param type The type to resolve
     * @return The base class
     */
    public static Class<?> resolveBaseClass(Type type) {
        return type instanceof Class ? (Class<?>) type
                : type instanceof ParameterizedType ? (Class<?>) ((ParameterizedType) type).getRawType()
                : null;
    }
}
