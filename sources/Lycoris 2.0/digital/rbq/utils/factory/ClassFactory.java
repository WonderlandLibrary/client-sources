/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.utils.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import digital.rbq.utils.factory.exception.FactoryException;

public final class ClassFactory {
    public static <K> K create(Class<K> objectClass, Object ... arguments) {
        Constructor<K> constructor;
        try {
            ArrayList list = new ArrayList();
            for (Object argument : arguments) {
                Class<?> aClass = argument.getClass();
                list.add(aClass);
            }
            constructor = objectClass.getDeclaredConstructor((Class<?>[]) list.toArray(new Class[0]));
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new FactoryException("Constructor not found! - Cannot create class.");
        }
        if (!constructor.isAccessible()) {
            constructor.setAccessible(true);
        }
        try {
            return constructor.newInstance(arguments);
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            throw new FactoryException("An error occurred while instantiating the class!");
        }
    }
}

