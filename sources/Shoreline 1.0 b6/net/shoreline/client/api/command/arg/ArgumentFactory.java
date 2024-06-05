package net.shoreline.client.api.command.arg;

import net.shoreline.client.Shoreline;

import java.lang.reflect.Field;

/**
 * @author linus
 * @since 1.0
 */
public class ArgumentFactory {
    //
    private final Object argObj;

    /**
     * @param argObj
     */
    public ArgumentFactory(Object argObj) {
        this.argObj = argObj;
    }

    /**
     * Creates and returns a new {@link Argument} instance from a {@link Field}
     * using {@link java.lang.reflect} lib.
     *
     * @param f The arg field
     * @return The created config
     * @throws RuntimeException if the field is not a Config type or reflect
     *                          could not access the field
     */
    public Argument<?> build(Field f) {
        f.setAccessible(true);
        // attempt to extract object from field
        try {
            final Argument<?> arg = (Argument<?>) f.get(argObj);
            if (arg != null) {
                arg.setOptional(f.isAnnotationPresent(OptionalArgument.class));
            }
            return arg;
        }
        // field getter error
        catch (IllegalArgumentException | IllegalAccessException e) {
            Shoreline.error("Failed to build argument from field {}!", f.getName());
            e.printStackTrace();
        }
        // failed arg creation
        throw new RuntimeException("Invalid field!");
    }
}
