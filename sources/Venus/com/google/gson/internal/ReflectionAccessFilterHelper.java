/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson.internal;

import com.google.gson.ReflectionAccessFilter;
import com.google.gson.internal.JavaVersion;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

public class ReflectionAccessFilterHelper {
    private ReflectionAccessFilterHelper() {
    }

    public static boolean isJavaType(Class<?> clazz) {
        return ReflectionAccessFilterHelper.isJavaType(clazz.getName());
    }

    private static boolean isJavaType(String string) {
        return string.startsWith("java.") || string.startsWith("javax.");
    }

    public static boolean isAndroidType(Class<?> clazz) {
        return ReflectionAccessFilterHelper.isAndroidType(clazz.getName());
    }

    private static boolean isAndroidType(String string) {
        return string.startsWith("android.") || string.startsWith("androidx.") || ReflectionAccessFilterHelper.isJavaType(string);
    }

    public static boolean isAnyPlatformType(Class<?> clazz) {
        String string = clazz.getName();
        return ReflectionAccessFilterHelper.isAndroidType(string) || string.startsWith("kotlin.") || string.startsWith("kotlinx.") || string.startsWith("scala.");
    }

    public static ReflectionAccessFilter.FilterResult getFilterResult(List<ReflectionAccessFilter> list, Class<?> clazz) {
        for (ReflectionAccessFilter reflectionAccessFilter : list) {
            ReflectionAccessFilter.FilterResult filterResult = reflectionAccessFilter.check(clazz);
            if (filterResult == ReflectionAccessFilter.FilterResult.INDECISIVE) continue;
            return filterResult;
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

        AccessChecker(1 var1_1) {
            this();
        }

        static {
            AccessChecker accessChecker = null;
            if (JavaVersion.isJava9OrLater()) {
                try {
                    Method method = AccessibleObject.class.getDeclaredMethod("canAccess", Object.class);
                    accessChecker = new AccessChecker(method){
                        final Method val$canAccessMethod;
                        {
                            this.val$canAccessMethod = method;
                            super(null);
                        }

                        @Override
                        public boolean canAccess(AccessibleObject accessibleObject, Object object) {
                            try {
                                return (Boolean)this.val$canAccessMethod.invoke(accessibleObject, object);
                            } catch (Exception exception) {
                                throw new RuntimeException("Failed invoking canAccess", exception);
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
                        return false;
                    }
                };
            }
            INSTANCE = accessChecker;
        }
    }
}

