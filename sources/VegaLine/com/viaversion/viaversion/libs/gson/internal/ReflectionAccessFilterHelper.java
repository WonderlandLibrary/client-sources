/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson.internal;

import com.viaversion.viaversion.libs.gson.ReflectionAccessFilter;
import com.viaversion.viaversion.libs.gson.internal.JavaVersion;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

public class ReflectionAccessFilterHelper {
    private ReflectionAccessFilterHelper() {
    }

    public static boolean isJavaType(Class<?> c) {
        return ReflectionAccessFilterHelper.isJavaType(c.getName());
    }

    private static boolean isJavaType(String className) {
        return className.startsWith("java.") || className.startsWith("javax.");
    }

    public static boolean isAndroidType(Class<?> c) {
        return ReflectionAccessFilterHelper.isAndroidType(c.getName());
    }

    private static boolean isAndroidType(String className) {
        return className.startsWith("android.") || className.startsWith("androidx.") || ReflectionAccessFilterHelper.isJavaType(className);
    }

    public static boolean isAnyPlatformType(Class<?> c) {
        String className = c.getName();
        return ReflectionAccessFilterHelper.isAndroidType(className) || className.startsWith("kotlin.") || className.startsWith("kotlinx.") || className.startsWith("scala.");
    }

    public static ReflectionAccessFilter.FilterResult getFilterResult(List<ReflectionAccessFilter> reflectionFilters, Class<?> c) {
        for (ReflectionAccessFilter filter : reflectionFilters) {
            ReflectionAccessFilter.FilterResult result = filter.check(c);
            if (result == ReflectionAccessFilter.FilterResult.INDECISIVE) continue;
            return result;
        }
        return ReflectionAccessFilter.FilterResult.ALLOW;
    }

    public static boolean canAccess(AccessibleObject accessibleObject, Object object) {
        return AccessChecker.INSTANCE.canAccess(accessibleObject, object);
    }

    private static abstract class AccessChecker {
        public static final AccessChecker INSTANCE;

        private AccessChecker() {
        }

        public abstract boolean canAccess(AccessibleObject var1, Object var2);

        static {
            AccessChecker accessChecker = null;
            if (JavaVersion.isJava9OrLater()) {
                try {
                    final Method canAccessMethod = AccessibleObject.class.getDeclaredMethod("canAccess", Object.class);
                    accessChecker = new AccessChecker(){

                        @Override
                        public boolean canAccess(AccessibleObject accessibleObject, Object object) {
                            try {
                                return (Boolean)canAccessMethod.invoke(accessibleObject, object);
                            } catch (Exception e) {
                                throw new RuntimeException("Failed invoking canAccess", e);
                            }
                        }
                    };
                } catch (NoSuchMethodException noSuchMethodException) {
                    // empty catch block
                }
            }
            if (accessChecker == null) {
                accessChecker = new AccessChecker(){

                    @Override
                    public boolean canAccess(AccessibleObject accessibleObject, Object object) {
                        return true;
                    }
                };
            }
            INSTANCE = accessChecker;
        }
    }
}

