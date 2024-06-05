/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist.runtime;

public class Desc {
    public static boolean useContextClassLoader = false;
    private static final ThreadLocal<Boolean> USE_CONTEXT_CLASS_LOADER_LOCALLY = new ThreadLocal<Boolean>(){

        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    public static void setUseContextClassLoaderLocally() {
        USE_CONTEXT_CLASS_LOADER_LOCALLY.set(true);
    }

    public static void resetUseContextClassLoaderLocally() {
        USE_CONTEXT_CLASS_LOADER_LOCALLY.remove();
    }

    private static Class<?> getClassObject(String name) throws ClassNotFoundException {
        if (useContextClassLoader) return Class.forName(name, true, Thread.currentThread().getContextClassLoader());
        if (USE_CONTEXT_CLASS_LOADER_LOCALLY.get() == false) return Class.forName(name);
        return Class.forName(name, true, Thread.currentThread().getContextClassLoader());
    }

    public static Class<?> getClazz(String name) {
        try {
            return Desc.getClassObject(name);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("$class: internal error, could not find class '" + name + "' (Desc.useContextClassLoader: " + Boolean.toString(useContextClassLoader) + ")", e);
        }
    }

    public static Class<?>[] getParams(String desc) {
        if (desc.charAt(0) == '(') return Desc.getType(desc, desc.length(), 1, 0);
        throw new RuntimeException("$sig: internal error");
    }

    public static Class<?> getType(String desc) {
        Class<?>[] result = Desc.getType(desc, desc.length(), 0, 0);
        if (result == null) throw new RuntimeException("$type: internal error");
        if (result.length == 1) return result[0];
        throw new RuntimeException("$type: internal error");
    }

    private static Class<?>[] getType(String desc, int descLen, int start, int num) {
        Class<Object> clazz;
        if (start >= descLen) {
            return new Class[num];
        }
        char c = desc.charAt(start);
        switch (c) {
            case 'Z': {
                clazz = Boolean.TYPE;
                break;
            }
            case 'C': {
                clazz = Character.TYPE;
                break;
            }
            case 'B': {
                clazz = Byte.TYPE;
                break;
            }
            case 'S': {
                clazz = Short.TYPE;
                break;
            }
            case 'I': {
                clazz = Integer.TYPE;
                break;
            }
            case 'J': {
                clazz = Long.TYPE;
                break;
            }
            case 'F': {
                clazz = Float.TYPE;
                break;
            }
            case 'D': {
                clazz = Double.TYPE;
                break;
            }
            case 'V': {
                clazz = Void.TYPE;
                break;
            }
            case 'L': 
            case '[': {
                return Desc.getClassType(desc, descLen, start, num);
            }
            default: {
                return new Class[num];
            }
        }
        Class<?>[] result = Desc.getType(desc, descLen, start + 1, num + 1);
        result[num] = clazz;
        return result;
    }

    private static Class<?>[] getClassType(String desc, int descLen, int start, int num) {
        int end = start;
        while (desc.charAt(end) == '[') {
            ++end;
        }
        if (desc.charAt(end) == 'L' && (end = desc.indexOf(59, end)) < 0) {
            throw new IndexOutOfBoundsException("bad descriptor");
        }
        String cname = desc.charAt(start) == 'L' ? desc.substring(start + 1, end) : desc.substring(start, end + 1);
        Class<?>[] result = Desc.getType(desc, descLen, end + 1, num + 1);
        try {
            result[num] = Desc.getClassObject(cname.replace('/', '.'));
            return result;
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

