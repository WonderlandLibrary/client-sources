/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist.util.proxy;

import com.viaversion.viaversion.libs.javassist.util.proxy.MethodHandler;
import com.viaversion.viaversion.libs.javassist.util.proxy.Proxy;
import com.viaversion.viaversion.libs.javassist.util.proxy.ProxyFactory;
import com.viaversion.viaversion.libs.javassist.util.proxy.ProxyObject;
import com.viaversion.viaversion.libs.javassist.util.proxy.SecurityActions;
import com.viaversion.viaversion.libs.javassist.util.proxy.SerializedProxy;
import java.io.InvalidClassException;
import java.io.Serializable;
import java.lang.reflect.Method;

public class RuntimeSupport {
    public static MethodHandler default_interceptor = new DefaultMethodHandler();

    public static void find2Methods(Class<?> clazz, String superMethod, String thisMethod, int index, String desc, Method[] methods) {
        methods[index + 1] = thisMethod == null ? null : RuntimeSupport.findMethod(clazz, thisMethod, desc);
        methods[index] = RuntimeSupport.findSuperClassMethod(clazz, superMethod, desc);
    }

    @Deprecated
    public static void find2Methods(Object self, String superMethod, String thisMethod, int index, String desc, Method[] methods) {
        methods[index + 1] = thisMethod == null ? null : RuntimeSupport.findMethod(self, thisMethod, desc);
        methods[index] = RuntimeSupport.findSuperMethod(self, superMethod, desc);
    }

    @Deprecated
    public static Method findMethod(Object self, String name, String desc) {
        Method m = RuntimeSupport.findMethod2(self.getClass(), name, desc);
        if (m != null) return m;
        RuntimeSupport.error(self.getClass(), name, desc);
        return m;
    }

    public static Method findMethod(Class<?> clazz, String name, String desc) {
        Method m = RuntimeSupport.findMethod2(clazz, name, desc);
        if (m != null) return m;
        RuntimeSupport.error(clazz, name, desc);
        return m;
    }

    public static Method findSuperMethod(Object self, String name, String desc) {
        Class<?> clazz = self.getClass();
        return RuntimeSupport.findSuperClassMethod(clazz, name, desc);
    }

    public static Method findSuperClassMethod(Class<?> clazz, String name, String desc) {
        Method m = RuntimeSupport.findSuperMethod2(clazz.getSuperclass(), name, desc);
        if (m == null) {
            m = RuntimeSupport.searchInterfaces(clazz, name, desc);
        }
        if (m != null) return m;
        RuntimeSupport.error(clazz, name, desc);
        return m;
    }

    private static void error(Class<?> clazz, String name, String desc) {
        throw new RuntimeException("not found " + name + ":" + desc + " in " + clazz.getName());
    }

    private static Method findSuperMethod2(Class<?> clazz, String name, String desc) {
        Method m = RuntimeSupport.findMethod2(clazz, name, desc);
        if (m != null) {
            return m;
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass == null) return RuntimeSupport.searchInterfaces(clazz, name, desc);
        m = RuntimeSupport.findSuperMethod2(superClass, name, desc);
        if (m == null) return RuntimeSupport.searchInterfaces(clazz, name, desc);
        return m;
    }

    private static Method searchInterfaces(Class<?> clazz, String name, String desc) {
        Method m = null;
        Class<?>[] interfaces = clazz.getInterfaces();
        int i = 0;
        while (i < interfaces.length) {
            m = RuntimeSupport.findSuperMethod2(interfaces[i], name, desc);
            if (m != null) {
                return m;
            }
            ++i;
        }
        return m;
    }

    private static Method findMethod2(Class<?> clazz, String name, String desc) {
        Method[] methods = SecurityActions.getDeclaredMethods(clazz);
        int n = methods.length;
        int i = 0;
        while (i < n) {
            if (methods[i].getName().equals(name) && RuntimeSupport.makeDescriptor(methods[i]).equals(desc)) {
                return methods[i];
            }
            ++i;
        }
        return null;
    }

    public static String makeDescriptor(Method m) {
        Class<?>[] params = m.getParameterTypes();
        return RuntimeSupport.makeDescriptor(params, m.getReturnType());
    }

    public static String makeDescriptor(Class<?>[] params, Class<?> retType) {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append('(');
        int i = 0;
        while (true) {
            if (i >= params.length) {
                sbuf.append(')');
                if (retType == null) return sbuf.toString();
                RuntimeSupport.makeDesc(sbuf, retType);
                return sbuf.toString();
            }
            RuntimeSupport.makeDesc(sbuf, params[i]);
            ++i;
        }
    }

    public static String makeDescriptor(String params, Class<?> retType) {
        StringBuffer sbuf = new StringBuffer(params);
        RuntimeSupport.makeDesc(sbuf, retType);
        return sbuf.toString();
    }

    private static void makeDesc(StringBuffer sbuf, Class<?> type) {
        if (type.isArray()) {
            sbuf.append('[');
            RuntimeSupport.makeDesc(sbuf, type.getComponentType());
            return;
        }
        if (!type.isPrimitive()) {
            sbuf.append('L').append(type.getName().replace('.', '/')).append(';');
            return;
        }
        if (type == Void.TYPE) {
            sbuf.append('V');
            return;
        }
        if (type == Integer.TYPE) {
            sbuf.append('I');
            return;
        }
        if (type == Byte.TYPE) {
            sbuf.append('B');
            return;
        }
        if (type == Long.TYPE) {
            sbuf.append('J');
            return;
        }
        if (type == Double.TYPE) {
            sbuf.append('D');
            return;
        }
        if (type == Float.TYPE) {
            sbuf.append('F');
            return;
        }
        if (type == Character.TYPE) {
            sbuf.append('C');
            return;
        }
        if (type == Short.TYPE) {
            sbuf.append('S');
            return;
        }
        if (type != Boolean.TYPE) throw new RuntimeException("bad type: " + type.getName());
        sbuf.append('Z');
    }

    public static SerializedProxy makeSerializedProxy(Object proxy) throws InvalidClassException {
        Class<?> clazz = proxy.getClass();
        MethodHandler methodHandler = null;
        if (proxy instanceof ProxyObject) {
            methodHandler = ((ProxyObject)proxy).getHandler();
            return new SerializedProxy(clazz, ProxyFactory.getFilterSignature(clazz), methodHandler);
        }
        if (!(proxy instanceof Proxy)) return new SerializedProxy(clazz, ProxyFactory.getFilterSignature(clazz), methodHandler);
        methodHandler = ProxyFactory.getHandler((Proxy)proxy);
        return new SerializedProxy(clazz, ProxyFactory.getFilterSignature(clazz), methodHandler);
    }

    static class DefaultMethodHandler
    implements MethodHandler,
    Serializable {
        private static final long serialVersionUID = 1L;

        DefaultMethodHandler() {
        }

        @Override
        public Object invoke(Object self, Method m, Method proceed, Object[] args) throws Exception {
            return proceed.invoke(self, args);
        }
    }
}

