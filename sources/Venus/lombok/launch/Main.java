/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package lombok.launch;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import lombok.launch.ShadowClassLoader;

class Main {
    private static ShadowClassLoader classLoader;

    Main() {
    }

    static synchronized ClassLoader getShadowClassLoader() {
        if (classLoader == null) {
            classLoader = new ShadowClassLoader(Main.class.getClassLoader(), "lombok", null, Arrays.asList(new String[0]), Arrays.asList("lombok.patcher.Symbols"));
        }
        return classLoader;
    }

    static synchronized void prependClassLoader(ClassLoader classLoader) {
        Main.getShadowClassLoader();
        Main.classLoader.prependParent(classLoader);
    }

    public static void main(String[] stringArray) throws Throwable {
        ClassLoader classLoader = Main.getShadowClassLoader();
        Class<?> clazz = classLoader.loadClass("lombok.core.Main");
        try {
            clazz.getMethod("main", String[].class).invoke(null, new Object[]{stringArray});
        } catch (InvocationTargetException invocationTargetException) {
            throw invocationTargetException.getCause();
        }
    }
}

