/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.AltCallingConvention;
import com.sun.jna.Function;
import com.sun.jna.InvocationMapper;
import com.sun.jna.NativeLibrary;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public interface Library {
    public static final String OPTION_TYPE_MAPPER = "type-mapper";
    public static final String OPTION_FUNCTION_MAPPER = "function-mapper";
    public static final String OPTION_INVOCATION_MAPPER = "invocation-mapper";
    public static final String OPTION_STRUCTURE_ALIGNMENT = "structure-alignment";
    public static final String OPTION_STRING_ENCODING = "string-encoding";
    public static final String OPTION_ALLOW_OBJECTS = "allow-objects";
    public static final String OPTION_CALLING_CONVENTION = "calling-convention";
    public static final String OPTION_OPEN_FLAGS = "open-flags";
    public static final String OPTION_CLASSLOADER = "classloader";

    public static class Handler
    implements InvocationHandler {
        static final Method OBJECT_TOSTRING;
        static final Method OBJECT_HASHCODE;
        static final Method OBJECT_EQUALS;
        private final NativeLibrary nativeLibrary;
        private final Class<?> interfaceClass;
        private final Map<String, Object> options;
        private final InvocationMapper invocationMapper;
        private final Map<Method, FunctionInfo> functions = new WeakHashMap<Method, FunctionInfo>();

        public Handler(String string, Class<?> clazz, Map<String, ?> map) {
            int n;
            if (string != null && "".equals(string.trim())) {
                throw new IllegalArgumentException("Invalid library name \"" + string + "\"");
            }
            if (!clazz.isInterface()) {
                throw new IllegalArgumentException(string + " does not implement an interface: " + clazz.getName());
            }
            this.interfaceClass = clazz;
            this.options = new HashMap(map);
            int n2 = n = AltCallingConvention.class.isAssignableFrom(clazz) ? 63 : 0;
            if (this.options.get(Library.OPTION_CALLING_CONVENTION) == null) {
                this.options.put(Library.OPTION_CALLING_CONVENTION, n);
            }
            if (this.options.get(Library.OPTION_CLASSLOADER) == null) {
                this.options.put(Library.OPTION_CLASSLOADER, clazz.getClassLoader());
            }
            this.nativeLibrary = NativeLibrary.getInstance(string, this.options);
            this.invocationMapper = (InvocationMapper)this.options.get(Library.OPTION_INVOCATION_MAPPER);
        }

        public NativeLibrary getNativeLibrary() {
            return this.nativeLibrary;
        }

        public String getLibraryName() {
            return this.nativeLibrary.getName();
        }

        public Class<?> getInterfaceClass() {
            return this.interfaceClass;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object invoke(Object object, Method method, Object[] objectArray) throws Throwable {
            if (OBJECT_TOSTRING.equals(method)) {
                return "Proxy interface to " + this.nativeLibrary;
            }
            if (OBJECT_HASHCODE.equals(method)) {
                return this.hashCode();
            }
            if (OBJECT_EQUALS.equals(method)) {
                Object object2 = objectArray[0];
                if (object2 != null && Proxy.isProxyClass(object2.getClass())) {
                    return Function.valueOf(Proxy.getInvocationHandler(object2) == this);
                }
                return Boolean.FALSE;
            }
            FunctionInfo functionInfo = this.functions.get(method);
            if (functionInfo == null) {
                Map<Method, FunctionInfo> map = this.functions;
                synchronized (map) {
                    functionInfo = this.functions.get(method);
                    if (functionInfo == null) {
                        boolean bl = Function.isVarArgs(method);
                        InvocationHandler invocationHandler = null;
                        if (this.invocationMapper != null) {
                            invocationHandler = this.invocationMapper.getInvocationHandler(this.nativeLibrary, method);
                        }
                        Function function = null;
                        Class<?>[] classArray = null;
                        HashMap<String, Object> hashMap = null;
                        if (invocationHandler == null) {
                            function = this.nativeLibrary.getFunction(method.getName(), method);
                            classArray = method.getParameterTypes();
                            hashMap = new HashMap<String, Object>(this.options);
                            hashMap.put("invoking-method", method);
                        }
                        functionInfo = new FunctionInfo(invocationHandler, function, classArray, bl, hashMap);
                        this.functions.put(method, functionInfo);
                    }
                }
            }
            if (functionInfo.isVarArgs) {
                objectArray = Function.concatenateVarArgs(objectArray);
            }
            if (functionInfo.handler != null) {
                return functionInfo.handler.invoke(object, method, objectArray);
            }
            return functionInfo.function.invoke(method, functionInfo.parameterTypes, method.getReturnType(), objectArray, functionInfo.options);
        }

        static {
            try {
                OBJECT_TOSTRING = Object.class.getMethod("toString", new Class[0]);
                OBJECT_HASHCODE = Object.class.getMethod("hashCode", new Class[0]);
                OBJECT_EQUALS = Object.class.getMethod("equals", Object.class);
            } catch (Exception exception) {
                throw new Error("Error retrieving Object.toString() method");
            }
        }

        private static final class FunctionInfo {
            final InvocationHandler handler;
            final Function function;
            final boolean isVarArgs;
            final Map<String, ?> options;
            final Class<?>[] parameterTypes;

            FunctionInfo(InvocationHandler invocationHandler, Function function, Class<?>[] classArray, boolean bl, Map<String, ?> map) {
                this.handler = invocationHandler;
                this.function = function;
                this.isVarArgs = bl;
                this.options = map;
                this.parameterTypes = classArray;
            }
        }
    }
}

