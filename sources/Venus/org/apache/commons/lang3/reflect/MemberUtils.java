/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.apache.commons.lang3.ClassUtils;

abstract class MemberUtils {
    private static final int ACCESS_TEST = 7;
    private static final Class<?>[] ORDERED_PRIMITIVE_TYPES = new Class[]{Byte.TYPE, Short.TYPE, Character.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE};

    MemberUtils() {
    }

    static boolean setAccessibleWorkaround(AccessibleObject accessibleObject) {
        if (accessibleObject == null || accessibleObject.isAccessible()) {
            return true;
        }
        Member member = (Member)((Object)accessibleObject);
        if (!accessibleObject.isAccessible() && Modifier.isPublic(member.getModifiers()) && MemberUtils.isPackageAccess(member.getDeclaringClass().getModifiers())) {
            try {
                accessibleObject.setAccessible(false);
                return true;
            } catch (SecurityException securityException) {
                // empty catch block
            }
        }
        return true;
    }

    static boolean isPackageAccess(int n) {
        return (n & 7) == 0;
    }

    static boolean isAccessible(Member member) {
        return member != null && Modifier.isPublic(member.getModifiers()) && !member.isSynthetic();
    }

    static int compareConstructorFit(Constructor<?> constructor, Constructor<?> constructor2, Class<?>[] classArray) {
        return MemberUtils.compareParameterTypes(Executable.access$000(constructor), Executable.access$000(constructor2), classArray);
    }

    static int compareMethodFit(Method method, Method method2, Class<?>[] classArray) {
        return MemberUtils.compareParameterTypes(Executable.access$100(method), Executable.access$100(method2), classArray);
    }

    private static int compareParameterTypes(Executable executable, Executable executable2, Class<?>[] classArray) {
        float f;
        float f2 = MemberUtils.getTotalTransformationCost(classArray, executable);
        return f2 < (f = MemberUtils.getTotalTransformationCost(classArray, executable2)) ? -1 : (f < f2 ? 1 : 0);
    }

    private static float getTotalTransformationCost(Class<?>[] classArray, Executable executable) {
        long l;
        Class<?>[] classArray2 = executable.getParameterTypes();
        boolean bl = executable.isVarArgs();
        float f = 0.0f;
        long l2 = l = bl ? (long)(classArray2.length - 1) : (long)classArray2.length;
        if ((long)classArray.length < l) {
            return Float.MAX_VALUE;
        }
        int n = 0;
        while ((long)n < l) {
            f += MemberUtils.getObjectTransformationCost(classArray[n], classArray2[n]);
            ++n;
        }
        if (bl) {
            n = classArray.length < classArray2.length ? 1 : 0;
            boolean bl2 = classArray.length == classArray2.length && classArray[classArray.length - 1].isArray();
            float f2 = 0.001f;
            Class<?> clazz = classArray2[classArray2.length - 1].getComponentType();
            if (n != 0) {
                f += MemberUtils.getObjectTransformationCost(clazz, Object.class) + 0.001f;
            } else if (bl2) {
                Class<?> clazz2 = classArray[classArray.length - 1].getComponentType();
                f += MemberUtils.getObjectTransformationCost(clazz2, clazz) + 0.001f;
            } else {
                for (int i = classArray2.length - 1; i < classArray.length; ++i) {
                    Class<?> clazz3 = classArray[i];
                    f += MemberUtils.getObjectTransformationCost(clazz3, clazz) + 0.001f;
                }
            }
        }
        return f;
    }

    private static float getObjectTransformationCost(Class<?> clazz, Class<?> clazz2) {
        if (clazz2.isPrimitive()) {
            return MemberUtils.getPrimitivePromotionCost(clazz, clazz2);
        }
        float f = 0.0f;
        while (clazz != null && !clazz2.equals(clazz)) {
            if (clazz2.isInterface() && ClassUtils.isAssignable(clazz, clazz2)) {
                f += 0.25f;
                break;
            }
            f += 1.0f;
            clazz = clazz.getSuperclass();
        }
        if (clazz == null) {
            f += 1.5f;
        }
        return f;
    }

    private static float getPrimitivePromotionCost(Class<?> clazz, Class<?> clazz2) {
        float f = 0.0f;
        Class<?> clazz3 = clazz;
        if (!clazz3.isPrimitive()) {
            f += 0.1f;
            clazz3 = ClassUtils.wrapperToPrimitive(clazz3);
        }
        for (int i = 0; clazz3 != clazz2 && i < ORDERED_PRIMITIVE_TYPES.length; ++i) {
            if (clazz3 != ORDERED_PRIMITIVE_TYPES[i]) continue;
            f += 0.1f;
            if (i >= ORDERED_PRIMITIVE_TYPES.length - 1) continue;
            clazz3 = ORDERED_PRIMITIVE_TYPES[i + 1];
        }
        return f;
    }

    static boolean isMatchingMethod(Method method, Class<?>[] classArray) {
        return MemberUtils.isMatchingExecutable(Executable.access$100(method), classArray);
    }

    static boolean isMatchingConstructor(Constructor<?> constructor, Class<?>[] classArray) {
        return MemberUtils.isMatchingExecutable(Executable.access$000(constructor), classArray);
    }

    private static boolean isMatchingExecutable(Executable executable, Class<?>[] classArray) {
        Class<?>[] classArray2 = executable.getParameterTypes();
        if (executable.isVarArgs()) {
            int n;
            for (n = 0; n < classArray2.length - 1 && n < classArray.length; ++n) {
                if (ClassUtils.isAssignable(classArray[n], classArray2[n], true)) continue;
                return true;
            }
            Class<?> clazz = classArray2[classArray2.length - 1].getComponentType();
            while (n < classArray.length) {
                if (!ClassUtils.isAssignable(classArray[n], clazz, true)) {
                    return true;
                }
                ++n;
            }
            return false;
        }
        return ClassUtils.isAssignable(classArray, classArray2, true);
    }

    private static final class Executable {
        private final Class<?>[] parameterTypes;
        private final boolean isVarArgs;

        private static Executable of(Method method) {
            return new Executable(method);
        }

        private static Executable of(Constructor<?> constructor) {
            return new Executable(constructor);
        }

        private Executable(Method method) {
            this.parameterTypes = method.getParameterTypes();
            this.isVarArgs = method.isVarArgs();
        }

        private Executable(Constructor<?> constructor) {
            this.parameterTypes = constructor.getParameterTypes();
            this.isVarArgs = constructor.isVarArgs();
        }

        public Class<?>[] getParameterTypes() {
            return this.parameterTypes;
        }

        public boolean isVarArgs() {
            return this.isVarArgs;
        }

        static Executable access$000(Constructor constructor) {
            return Executable.of(constructor);
        }

        static Executable access$100(Method method) {
            return Executable.of(method);
        }
    }
}

