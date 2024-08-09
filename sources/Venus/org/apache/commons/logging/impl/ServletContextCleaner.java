/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContextEvent
 *  javax.servlet.ServletContextListener
 */
package org.apache.commons.logging.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.LogFactory;

public class ServletContextCleaner
implements ServletContextListener {
    private static final Class[] RELEASE_SIGNATURE = new Class[]{class$java$lang$ClassLoader == null ? (class$java$lang$ClassLoader = ServletContextCleaner.class$("java.lang.ClassLoader")) : class$java$lang$ClassLoader};
    static Class class$java$lang$ClassLoader;

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Object[] objectArray = new Object[]{classLoader};
        ClassLoader classLoader2 = classLoader;
        while (classLoader2 != null) {
            try {
                Class<?> clazz = classLoader2.loadClass("org.apache.commons.logging.LogFactory");
                Method method = clazz.getMethod("release", RELEASE_SIGNATURE);
                method.invoke(null, objectArray);
                classLoader2 = clazz.getClassLoader().getParent();
            } catch (ClassNotFoundException classNotFoundException) {
                classLoader2 = null;
            } catch (NoSuchMethodException noSuchMethodException) {
                System.err.println("LogFactory instance found which does not support release method!");
                classLoader2 = null;
            } catch (IllegalAccessException illegalAccessException) {
                System.err.println("LogFactory instance found which is not accessable!");
                classLoader2 = null;
            } catch (InvocationTargetException invocationTargetException) {
                System.err.println("LogFactory instance release method failed!");
                classLoader2 = null;
            }
        }
        LogFactory.release(classLoader);
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }

    static Class class$(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }
}

