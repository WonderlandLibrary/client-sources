package us.dev.api.factory;

import us.dev.api.factory.exceptions.FactoryException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * @author Foundry
 */
public final class ClassFactory {
    public static <K> K create(final Class<K> objectClass, final Object... arguments) {
        Constructor<K> constructor;
        try {
            constructor = objectClass.getDeclaredConstructor(Arrays.stream(arguments).map(Object::getClass).toArray(Class[]::new));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new FactoryException("Constructor not found! - Cannot create class.");
        }
        if (!constructor.isAccessible()) {
            constructor.setAccessible(true);
        }
        try {
            return constructor.newInstance(arguments);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new FactoryException("An error occurred while instantiating the class!");
        }
    }
}
