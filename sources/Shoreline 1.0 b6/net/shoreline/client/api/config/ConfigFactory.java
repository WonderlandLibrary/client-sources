package net.shoreline.client.api.config;

import net.shoreline.client.Shoreline;

import java.lang.reflect.Field;

/**
 * @author linus
 * @see ConfigContainer
 * @since 1.0
 */
public class ConfigFactory {
    // The object to grab from
    protected final Object configObj;

    /**
     * @param configObj
     */
    public ConfigFactory(Object configObj) {
        this.configObj = configObj;
    }

    /**
     * Creates and returns a new {@link Config} instance from a {@link Field}
     * using {@link java.lang.reflect} lib.
     *
     * @param f The config field
     * @return The created config
     * @throws RuntimeException if the field is not a Config type or reflect
     *                          could not access the field
     */
    public Config<?> build(Field f) {
        f.setAccessible(true);
        // attempt to extract object from field
        try {
            return (Config<?>) f.get(configObj);
        }
        // field getter error
        catch (IllegalArgumentException | IllegalAccessException e) {
            Shoreline.error("Failed to build config from field {}!", f.getName());
            e.printStackTrace();
        }
        // failed config creation
        throw new RuntimeException("Invalid field!");
    }
}
