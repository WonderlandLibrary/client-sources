/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.util;

import java.lang.reflect.Method;
import java.util.Stack;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PerformanceSensitive;

public final class ReflectionUtil {
    static final int JDK_7u25_OFFSET;
    private static final Logger LOGGER;
    private static final boolean SUN_REFLECTION_SUPPORTED;
    private static final Method GET_CALLER_CLASS;
    private static final PrivateSecurityManager SECURITY_MANAGER;

    private ReflectionUtil() {
    }

    public static boolean supportsFastReflection() {
        return SUN_REFLECTION_SUPPORTED;
    }

    @PerformanceSensitive
    public static Class<?> getCallerClass(int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException(Integer.toString(n));
        }
        if (ReflectionUtil.supportsFastReflection()) {
            try {
                return (Class)GET_CALLER_CLASS.invoke(null, n + 1 + JDK_7u25_OFFSET);
            } catch (Exception exception) {
                LOGGER.error("Error in ReflectionUtil.getCallerClass({}).", (Object)n, (Object)exception);
                return null;
            }
        }
        StackTraceElement stackTraceElement = ReflectionUtil.getEquivalentStackTraceElement(n + 1);
        try {
            return LoaderUtil.loadClass(stackTraceElement.getClassName());
        } catch (ClassNotFoundException classNotFoundException) {
            LOGGER.error("Could not find class in ReflectionUtil.getCallerClass({}).", (Object)n, (Object)classNotFoundException);
            return null;
        }
    }

    static StackTraceElement getEquivalentStackTraceElement(int n) {
        StackTraceElement[] stackTraceElementArray = new Throwable().getStackTrace();
        int n2 = 0;
        for (StackTraceElement stackTraceElement : stackTraceElementArray) {
            if (!ReflectionUtil.isValid(stackTraceElement)) continue;
            if (n2 == n) {
                return stackTraceElement;
            }
            ++n2;
        }
        LOGGER.error("Could not find an appropriate StackTraceElement at index {}", (Object)n);
        throw new IndexOutOfBoundsException(Integer.toString(n));
    }

    private static boolean isValid(StackTraceElement stackTraceElement) {
        if (stackTraceElement.isNativeMethod()) {
            return true;
        }
        String string = stackTraceElement.getClassName();
        if (string.startsWith("sun.reflect.")) {
            return true;
        }
        String string2 = stackTraceElement.getMethodName();
        if (string.startsWith("java.lang.reflect.") && (string2.equals("invoke") || string2.equals("newInstance"))) {
            return true;
        }
        if (string.startsWith("jdk.internal.reflect.")) {
            return true;
        }
        if (string.equals("java.lang.Class") && string2.equals("newInstance")) {
            return true;
        }
        return string.equals("java.lang.invoke.MethodHandle") && string2.startsWith("invoke");
    }

    @PerformanceSensitive
    public static Class<?> getCallerClass(String string) {
        return ReflectionUtil.getCallerClass(string, "");
    }

    @PerformanceSensitive
    public static Class<?> getCallerClass(String string, String string2) {
        if (ReflectionUtil.supportsFastReflection()) {
            Class<?> clazz;
            boolean bl = false;
            int n = 2;
            while (null != (clazz = ReflectionUtil.getCallerClass(n))) {
                if (string.equals(clazz.getName())) {
                    bl = true;
                } else if (bl && clazz.getName().startsWith(string2)) {
                    return clazz;
                }
                ++n;
            }
            return null;
        }
        if (SECURITY_MANAGER != null) {
            return SECURITY_MANAGER.getCallerClass(string, string2);
        }
        try {
            return LoaderUtil.loadClass(ReflectionUtil.getCallerClassName(string, string2, new Throwable().getStackTrace()));
        } catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }

    @PerformanceSensitive
    public static Class<?> getCallerClass(Class<?> clazz) {
        if (ReflectionUtil.supportsFastReflection()) {
            Class<?> clazz2;
            boolean bl = false;
            int n = 2;
            while (null != (clazz2 = ReflectionUtil.getCallerClass(n))) {
                if (clazz.equals(clazz2)) {
                    bl = true;
                } else if (bl) {
                    return clazz2;
                }
                ++n;
            }
            return Object.class;
        }
        if (SECURITY_MANAGER != null) {
            return SECURITY_MANAGER.getCallerClass(clazz);
        }
        try {
            return LoaderUtil.loadClass(ReflectionUtil.getCallerClassName(clazz.getName(), "", new Throwable().getStackTrace()));
        } catch (ClassNotFoundException classNotFoundException) {
            return Object.class;
        }
    }

    private static String getCallerClassName(String string, String string2, StackTraceElement ... stackTraceElementArray) {
        boolean bl = false;
        for (StackTraceElement stackTraceElement : stackTraceElementArray) {
            String string3 = stackTraceElement.getClassName();
            if (string3.equals(string)) {
                bl = true;
                continue;
            }
            if (!bl || !string3.startsWith(string2)) continue;
            return string3;
        }
        return Object.class.getName();
    }

    @PerformanceSensitive
    public static Stack<Class<?>> getCurrentStackTrace() {
        if (SECURITY_MANAGER != null) {
            Class<?>[] classArray = SECURITY_MANAGER.getClassContext();
            Stack stack = new Stack();
            stack.ensureCapacity(classArray.length);
            for (Class<?> clazz : classArray) {
                stack.push(clazz);
            }
            return stack;
        }
        if (ReflectionUtil.supportsFastReflection()) {
            Class<?> clazz;
            Stack stack = new Stack();
            int n = 1;
            while (null != (clazz = ReflectionUtil.getCallerClass(n))) {
                stack.push(clazz);
                ++n;
            }
            return stack;
        }
        return new Stack();
    }

    static {
        Object object;
        Method method;
        Object object2;
        LOGGER = StatusLogger.getLogger();
        int n = 0;
        try {
            object2 = LoaderUtil.loadClass("sun.reflect.Reflection");
            method = ((Class)object2).getDeclaredMethod("getCallerClass", Integer.TYPE);
            object = method.invoke(null, 0);
            Object object3 = method.invoke(null, 0);
            if (object == null || object != object2) {
                LOGGER.warn("Unexpected return value from Reflection.getCallerClass(): {}", object3);
                method = null;
                n = -1;
            } else {
                object = method.invoke(null, 1);
                if (object == object2) {
                    LOGGER.warn("You are using Java 1.7.0_25 which has a broken implementation of Reflection.getCallerClass.");
                    LOGGER.warn("You should upgrade to at least Java 1.7.0_40 or later.");
                    LOGGER.debug("Using stack depth compensation offset of 1 due to Java 7u25.");
                    n = 1;
                }
            }
        } catch (Exception | LinkageError throwable) {
            LOGGER.info("sun.reflect.Reflection.getCallerClass is not supported. ReflectionUtil.getCallerClass will be much slower due to this.", throwable);
            method = null;
            n = -1;
        }
        SUN_REFLECTION_SUPPORTED = method != null;
        GET_CALLER_CLASS = method;
        JDK_7u25_OFFSET = n;
        try {
            object = System.getSecurityManager();
            if (object != null) {
                ((SecurityManager)object).checkPermission(new RuntimePermission("createSecurityManager"));
            }
            object2 = new PrivateSecurityManager();
        } catch (SecurityException securityException) {
            LOGGER.debug("Not allowed to create SecurityManager. Falling back to slowest ReflectionUtil implementation.");
            object2 = null;
        }
        SECURITY_MANAGER = object2;
    }

    static final class PrivateSecurityManager
    extends SecurityManager {
        PrivateSecurityManager() {
        }

        protected Class<?>[] getClassContext() {
            return super.getClassContext();
        }

        protected Class<?> getCallerClass(String string, String string2) {
            boolean bl = false;
            for (Class<?> clazz : this.getClassContext()) {
                if (string.equals(clazz.getName())) {
                    bl = true;
                    continue;
                }
                if (!bl || !clazz.getName().startsWith(string2)) continue;
                return clazz;
            }
            return null;
        }

        protected Class<?> getCallerClass(Class<?> clazz) {
            boolean bl = false;
            for (Class<?> clazz2 : this.getClassContext()) {
                if (clazz.equals(clazz2)) {
                    bl = true;
                    continue;
                }
                if (!bl) continue;
                return clazz2;
            }
            return Object.class;
        }
    }
}

