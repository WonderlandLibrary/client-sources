/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist;

public class Modifier {
    public static final int PUBLIC = 1;
    public static final int PRIVATE = 2;
    public static final int PROTECTED = 4;
    public static final int STATIC = 8;
    public static final int FINAL = 16;
    public static final int SYNCHRONIZED = 32;
    public static final int VOLATILE = 64;
    public static final int VARARGS = 128;
    public static final int TRANSIENT = 128;
    public static final int NATIVE = 256;
    public static final int INTERFACE = 512;
    public static final int ABSTRACT = 1024;
    public static final int STRICT = 2048;
    public static final int ANNOTATION = 8192;
    public static final int ENUM = 16384;

    public static boolean isPublic(int mod) {
        if ((mod & 1) == 0) return false;
        return true;
    }

    public static boolean isPrivate(int mod) {
        if ((mod & 2) == 0) return false;
        return true;
    }

    public static boolean isProtected(int mod) {
        if ((mod & 4) == 0) return false;
        return true;
    }

    public static boolean isPackage(int mod) {
        if ((mod & 7) != 0) return false;
        return true;
    }

    public static boolean isStatic(int mod) {
        if ((mod & 8) == 0) return false;
        return true;
    }

    public static boolean isFinal(int mod) {
        if ((mod & 0x10) == 0) return false;
        return true;
    }

    public static boolean isSynchronized(int mod) {
        if ((mod & 0x20) == 0) return false;
        return true;
    }

    public static boolean isVolatile(int mod) {
        if ((mod & 0x40) == 0) return false;
        return true;
    }

    public static boolean isTransient(int mod) {
        if ((mod & 0x80) == 0) return false;
        return true;
    }

    public static boolean isNative(int mod) {
        if ((mod & 0x100) == 0) return false;
        return true;
    }

    public static boolean isInterface(int mod) {
        if ((mod & 0x200) == 0) return false;
        return true;
    }

    public static boolean isAnnotation(int mod) {
        if ((mod & 0x2000) == 0) return false;
        return true;
    }

    public static boolean isEnum(int mod) {
        if ((mod & 0x4000) == 0) return false;
        return true;
    }

    public static boolean isAbstract(int mod) {
        if ((mod & 0x400) == 0) return false;
        return true;
    }

    public static boolean isStrict(int mod) {
        if ((mod & 0x800) == 0) return false;
        return true;
    }

    public static boolean isVarArgs(int mod) {
        if ((mod & 0x80) == 0) return false;
        return true;
    }

    public static int setPublic(int mod) {
        return mod & 0xFFFFFFF9 | 1;
    }

    public static int setProtected(int mod) {
        return mod & 0xFFFFFFFC | 4;
    }

    public static int setPrivate(int mod) {
        return mod & 0xFFFFFFFA | 2;
    }

    public static int setPackage(int mod) {
        return mod & 0xFFFFFFF8;
    }

    public static int clear(int mod, int clearBit) {
        return mod & ~clearBit;
    }

    public static String toString(int mod) {
        return java.lang.reflect.Modifier.toString(mod);
    }
}

