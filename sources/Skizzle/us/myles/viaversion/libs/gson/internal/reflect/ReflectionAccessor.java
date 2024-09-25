/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.gson.internal.reflect;

import java.lang.reflect.AccessibleObject;
import us.myles.viaversion.libs.gson.internal.JavaVersion;
import us.myles.viaversion.libs.gson.internal.reflect.PreJava9ReflectionAccessor;
import us.myles.viaversion.libs.gson.internal.reflect.UnsafeReflectionAccessor;

public abstract class ReflectionAccessor {
    private static final ReflectionAccessor instance = JavaVersion.getMajorJavaVersion() < 9 ? new PreJava9ReflectionAccessor() : new UnsafeReflectionAccessor();

    public abstract void makeAccessible(AccessibleObject var1);

    public static ReflectionAccessor getInstance() {
        return instance;
    }
}

