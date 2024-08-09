/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.event;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.reflect.MethodUtils;

public class EventUtils {
    public static <L> void addEventListener(Object object, Class<L> clazz, L l) {
        try {
            MethodUtils.invokeMethod(object, "add" + clazz.getSimpleName(), l);
        } catch (NoSuchMethodException noSuchMethodException) {
            throw new IllegalArgumentException("Class " + object.getClass().getName() + " does not have a public add" + clazz.getSimpleName() + " method which takes a parameter of type " + clazz.getName() + ".");
        } catch (IllegalAccessException illegalAccessException) {
            throw new IllegalArgumentException("Class " + object.getClass().getName() + " does not have an accessible add" + clazz.getSimpleName() + " method which takes a parameter of type " + clazz.getName() + ".");
        } catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException("Unable to add listener.", invocationTargetException.getCause());
        }
    }

    public static <L> void bindEventsToMethod(Object object, String string, Object object2, Class<L> clazz, String ... stringArray) {
        L l = clazz.cast(Proxy.newProxyInstance(object.getClass().getClassLoader(), new Class[]{clazz}, new EventBindingInvocationHandler(object, string, stringArray)));
        EventUtils.addEventListener(object2, clazz, l);
    }

    private static class EventBindingInvocationHandler
    implements InvocationHandler {
        private final Object target;
        private final String methodName;
        private final Set<String> eventTypes;

        EventBindingInvocationHandler(Object object, String string, String[] stringArray) {
            this.target = object;
            this.methodName = string;
            this.eventTypes = new HashSet<String>(Arrays.asList(stringArray));
        }

        @Override
        public Object invoke(Object object, Method method, Object[] objectArray) throws Throwable {
            if (this.eventTypes.isEmpty() || this.eventTypes.contains(method.getName())) {
                if (this.hasMatchingParametersMethod(method)) {
                    return MethodUtils.invokeMethod(this.target, this.methodName, objectArray);
                }
                return MethodUtils.invokeMethod(this.target, this.methodName);
            }
            return null;
        }

        private boolean hasMatchingParametersMethod(Method method) {
            return MethodUtils.getAccessibleMethod(this.target.getClass(), this.methodName, method.getParameterTypes()) != null;
        }
    }
}

