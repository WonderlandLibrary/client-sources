package us.dev.dvent.internal.util;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author Foundry
 */
public final class FieldFactory {
    public static <K> K create(Object parent, Field field, final Class<K> fieldClass) {
        if (!Objects.requireNonNull(field, "Field cannot be null").isAccessible())
            field.setAccessible(true);
        try {
            return Objects.requireNonNull(fieldClass, "Field type class cannot be null").cast(field.get(parent));
        } catch (IllegalAccessException e) {
            throw new FactoryException("Illegal to access field " + field.getName() + " in object " + parent.getClass().getSimpleName() + ": " + e);
        } catch (ClassCastException e) {
            throw new FactoryException("Illegal to cast field " + field.getName() + " to type " + fieldClass.getSimpleName() + ": " + e);
        } catch (Exception e) {
            throw new FactoryException("Exception retrieving object from field " + field.getName() + ": " + e);
        }
    }

    private static class FactoryException extends RuntimeException {
        private static final long serialVersionUID = 1;

        FactoryException(String message) {
            super(message);
        }
    }
}
