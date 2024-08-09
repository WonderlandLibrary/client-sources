/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.reflect;

import com.google.common.annotations.Beta;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import javax.annotation.Nullable;

@Beta
public abstract class AbstractInvocationHandler
implements InvocationHandler {
    private static final Object[] NO_ARGS = new Object[0];

    @Override
    public final Object invoke(Object object, Method method, @Nullable Object[] objectArray) throws Throwable {
        if (objectArray == null) {
            objectArray = NO_ARGS;
        }
        if (objectArray.length == 0 && method.getName().equals("hashCode")) {
            return this.hashCode();
        }
        if (objectArray.length == 1 && method.getName().equals("equals") && method.getParameterTypes()[0] == Object.class) {
            Object object2 = objectArray[0];
            if (object2 == null) {
                return false;
            }
            if (object == object2) {
                return true;
            }
            return AbstractInvocationHandler.isProxyOfSameInterfaces(object2, object.getClass()) && this.equals(Proxy.getInvocationHandler(object2));
        }
        if (objectArray.length == 0 && method.getName().equals("toString")) {
            return this.toString();
        }
        return this.handleInvocation(object, method, objectArray);
    }

    protected abstract Object handleInvocation(Object var1, Method var2, Object[] var3) throws Throwable;

    public boolean equals(Object object) {
        return super.equals(object);
    }

    public int hashCode() {
        return super.hashCode();
    }

    public String toString() {
        return super.toString();
    }

    private static boolean isProxyOfSameInterfaces(Object object, Class<?> clazz) {
        return clazz.isInstance(object) || Proxy.isProxyClass(object.getClass()) && Arrays.equals(object.getClass().getInterfaces(), clazz.getInterfaces());
    }
}

