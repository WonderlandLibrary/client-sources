/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.reflect.MemberUtils;
import org.apache.commons.lang3.reflect.TypeUtils;

public class MethodUtils {
    public static Object invokeMethod(Object object, String string) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return MethodUtils.invokeMethod(object, string, ArrayUtils.EMPTY_OBJECT_ARRAY, null);
    }

    public static Object invokeMethod(Object object, boolean bl, String string) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return MethodUtils.invokeMethod(object, bl, string, ArrayUtils.EMPTY_OBJECT_ARRAY, null);
    }

    public static Object invokeMethod(Object object, String string, Object ... objectArray) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        objectArray = ArrayUtils.nullToEmpty(objectArray);
        Class<?>[] classArray = ClassUtils.toClass(objectArray);
        return MethodUtils.invokeMethod(object, string, objectArray, classArray);
    }

    public static Object invokeMethod(Object object, boolean bl, String string, Object ... objectArray) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        objectArray = ArrayUtils.nullToEmpty(objectArray);
        Class<?>[] classArray = ClassUtils.toClass(objectArray);
        return MethodUtils.invokeMethod(object, bl, string, objectArray, classArray);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Object invokeMethod(Object object, boolean bl, String string, Object[] objectArray, Class<?>[] classArray) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        classArray = ArrayUtils.nullToEmpty(classArray);
        objectArray = ArrayUtils.nullToEmpty(objectArray);
        AccessibleObject accessibleObject = null;
        boolean bl2 = false;
        Object object2 = null;
        try {
            String string2;
            if (bl) {
                string2 = "No such method: ";
                accessibleObject = MethodUtils.getMatchingMethod(object.getClass(), string, classArray);
                if (accessibleObject != null && !(bl2 = accessibleObject.isAccessible())) {
                    accessibleObject.setAccessible(false);
                }
            } else {
                string2 = "No such accessible method: ";
                accessibleObject = MethodUtils.getMatchingAccessibleMethod(object.getClass(), string, classArray);
            }
            if (accessibleObject == null) {
                throw new NoSuchMethodException(string2 + string + "() on object: " + object.getClass().getName());
            }
            objectArray = MethodUtils.toVarArgs((Method)accessibleObject, objectArray);
            object2 = ((Method)accessibleObject).invoke(object, objectArray);
        } finally {
            if (accessibleObject != null && bl && accessibleObject.isAccessible() != bl2) {
                accessibleObject.setAccessible(bl2);
            }
        }
        return object2;
    }

    public static Object invokeMethod(Object object, String string, Object[] objectArray, Class<?>[] classArray) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return MethodUtils.invokeMethod(object, false, string, objectArray, classArray);
    }

    public static Object invokeExactMethod(Object object, String string) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return MethodUtils.invokeExactMethod(object, string, ArrayUtils.EMPTY_OBJECT_ARRAY, null);
    }

    public static Object invokeExactMethod(Object object, String string, Object ... objectArray) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        objectArray = ArrayUtils.nullToEmpty(objectArray);
        Class<?>[] classArray = ClassUtils.toClass(objectArray);
        return MethodUtils.invokeExactMethod(object, string, objectArray, classArray);
    }

    public static Object invokeExactMethod(Object object, String string, Object[] objectArray, Class<?>[] classArray) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        objectArray = ArrayUtils.nullToEmpty(objectArray);
        classArray = ArrayUtils.nullToEmpty(classArray);
        Method method = MethodUtils.getAccessibleMethod(object.getClass(), string, classArray);
        if (method == null) {
            throw new NoSuchMethodException("No such accessible method: " + string + "() on object: " + object.getClass().getName());
        }
        return method.invoke(object, objectArray);
    }

    public static Object invokeExactStaticMethod(Class<?> clazz, String string, Object[] objectArray, Class<?>[] classArray) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        objectArray = ArrayUtils.nullToEmpty(objectArray);
        Method method = MethodUtils.getAccessibleMethod(clazz, string, classArray = ArrayUtils.nullToEmpty(classArray));
        if (method == null) {
            throw new NoSuchMethodException("No such accessible method: " + string + "() on class: " + clazz.getName());
        }
        return method.invoke(null, objectArray);
    }

    public static Object invokeStaticMethod(Class<?> clazz, String string, Object ... objectArray) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        objectArray = ArrayUtils.nullToEmpty(objectArray);
        Class<?>[] classArray = ClassUtils.toClass(objectArray);
        return MethodUtils.invokeStaticMethod(clazz, string, objectArray, classArray);
    }

    public static Object invokeStaticMethod(Class<?> clazz, String string, Object[] objectArray, Class<?>[] classArray) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        objectArray = ArrayUtils.nullToEmpty(objectArray);
        Method method = MethodUtils.getMatchingAccessibleMethod(clazz, string, classArray = ArrayUtils.nullToEmpty(classArray));
        if (method == null) {
            throw new NoSuchMethodException("No such accessible method: " + string + "() on class: " + clazz.getName());
        }
        objectArray = MethodUtils.toVarArgs(method, objectArray);
        return method.invoke(null, objectArray);
    }

    private static Object[] toVarArgs(Method method, Object[] objectArray) {
        if (method.isVarArgs()) {
            Class<?>[] classArray = method.getParameterTypes();
            objectArray = MethodUtils.getVarArgs(objectArray, classArray);
        }
        return objectArray;
    }

    static Object[] getVarArgs(Object[] objectArray, Class<?>[] classArray) {
        if (objectArray.length == classArray.length && objectArray[objectArray.length - 1].getClass().equals(classArray[classArray.length - 1])) {
            return objectArray;
        }
        Object[] objectArray2 = new Object[classArray.length];
        System.arraycopy(objectArray, 0, objectArray2, 0, classArray.length - 1);
        Class<?> clazz = classArray[classArray.length - 1].getComponentType();
        int n = objectArray.length - classArray.length + 1;
        Object object = Array.newInstance(ClassUtils.primitiveToWrapper(clazz), n);
        System.arraycopy(objectArray, classArray.length - 1, object, 0, n);
        if (clazz.isPrimitive()) {
            object = ArrayUtils.toPrimitive(object);
        }
        objectArray2[classArray.length - 1] = object;
        return objectArray2;
    }

    public static Object invokeExactStaticMethod(Class<?> clazz, String string, Object ... objectArray) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        objectArray = ArrayUtils.nullToEmpty(objectArray);
        Class<?>[] classArray = ClassUtils.toClass(objectArray);
        return MethodUtils.invokeExactStaticMethod(clazz, string, objectArray, classArray);
    }

    public static Method getAccessibleMethod(Class<?> clazz, String string, Class<?> ... classArray) {
        try {
            return MethodUtils.getAccessibleMethod(clazz.getMethod(string, classArray));
        } catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
    }

    public static Method getAccessibleMethod(Method method) {
        Class<?>[] classArray;
        if (!MemberUtils.isAccessible(method)) {
            return null;
        }
        Class<?> clazz = method.getDeclaringClass();
        if (Modifier.isPublic(clazz.getModifiers())) {
            return method;
        }
        String string = method.getName();
        if ((method = MethodUtils.getAccessibleMethodFromInterfaceNest(clazz, string, classArray = method.getParameterTypes())) == null) {
            method = MethodUtils.getAccessibleMethodFromSuperclass(clazz, string, classArray);
        }
        return method;
    }

    private static Method getAccessibleMethodFromSuperclass(Class<?> clazz, String string, Class<?> ... classArray) {
        for (Class<?> clazz2 = clazz.getSuperclass(); clazz2 != null; clazz2 = clazz2.getSuperclass()) {
            if (!Modifier.isPublic(clazz2.getModifiers())) continue;
            try {
                return clazz2.getMethod(string, classArray);
            } catch (NoSuchMethodException noSuchMethodException) {
                return null;
            }
        }
        return null;
    }

    private static Method getAccessibleMethodFromInterfaceNest(Class<?> clazz, String string, Class<?> ... classArray) {
        while (clazz != null) {
            Class<?>[] classArray2 = clazz.getInterfaces();
            for (int i = 0; i < classArray2.length; ++i) {
                if (!Modifier.isPublic(classArray2[i].getModifiers())) continue;
                try {
                    return classArray2[i].getDeclaredMethod(string, classArray);
                } catch (NoSuchMethodException noSuchMethodException) {
                    Method method = MethodUtils.getAccessibleMethodFromInterfaceNest(classArray2[i], string, classArray);
                    if (method == null) continue;
                    return method;
                }
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    public static Method getMatchingAccessibleMethod(Class<?> clazz, String string, Class<?> ... classArray) {
        try {
            Method method = clazz.getMethod(string, classArray);
            MemberUtils.setAccessibleWorkaround(method);
            return method;
        } catch (NoSuchMethodException noSuchMethodException) {
            Method[] methodArray;
            Method method = null;
            for (Method method2 : methodArray = clazz.getMethods()) {
                Method method3;
                if (!method2.getName().equals(string) || !MemberUtils.isMatchingMethod(method2, classArray) || (method3 = MethodUtils.getAccessibleMethod(method2)) == null || method != null && MemberUtils.compareMethodFit(method3, method, classArray) >= 0) continue;
                method = method3;
            }
            if (method != null) {
                MemberUtils.setAccessibleWorkaround(method);
            }
            return method;
        }
    }

    public static Method getMatchingMethod(Class<?> clazz, String string, Class<?> ... classArray) {
        Validate.notNull(clazz, "Null class not allowed.", new Object[0]);
        Validate.notEmpty(string, "Null or blank methodName not allowed.", new Object[0]);
        Method[] methodArray = clazz.getDeclaredMethods();
        List<Class<?>> list = ClassUtils.getAllSuperclasses(clazz);
        for (Class<?> methodArray2 : list) {
            methodArray = ArrayUtils.addAll(methodArray, methodArray2.getDeclaredMethods());
        }
        Object object = null;
        for (Method method : methodArray) {
            if (string.equals(method.getName()) && ArrayUtils.isEquals(classArray, method.getParameterTypes())) {
                return method;
            }
            if (!string.equals(method.getName()) || !ClassUtils.isAssignable(classArray, method.getParameterTypes(), true)) continue;
            if (object == null) {
                object = method;
                continue;
            }
            if (MethodUtils.distance(classArray, method.getParameterTypes()) >= MethodUtils.distance(classArray, ((Method)object).getParameterTypes())) continue;
            object = method;
        }
        return object;
    }

    private static int distance(Class<?>[] classArray, Class<?>[] classArray2) {
        int n = 0;
        if (!ClassUtils.isAssignable(classArray, classArray2, true)) {
            return 1;
        }
        for (int i = 0; i < classArray.length; ++i) {
            if (classArray[i].equals(classArray2[i])) continue;
            if (ClassUtils.isAssignable(classArray[i], classArray2[i], true) && !ClassUtils.isAssignable(classArray[i], classArray2[i], false)) {
                ++n;
                continue;
            }
            n += 2;
        }
        return n;
    }

    public static Set<Method> getOverrideHierarchy(Method method, ClassUtils.Interfaces interfaces) {
        Validate.notNull(method);
        LinkedHashSet<Method> linkedHashSet = new LinkedHashSet<Method>();
        linkedHashSet.add(method);
        Object[] objectArray = method.getParameterTypes();
        Class<?> clazz = method.getDeclaringClass();
        Iterator<Class<?>> iterator2 = ClassUtils.hierarchy(clazz, interfaces).iterator();
        iterator2.next();
        block0: while (iterator2.hasNext()) {
            Class<?> clazz2 = iterator2.next();
            Method method2 = MethodUtils.getMatchingAccessibleMethod(clazz2, method.getName(), objectArray);
            if (method2 == null) continue;
            if (Arrays.equals(method2.getParameterTypes(), objectArray)) {
                linkedHashSet.add(method2);
                continue;
            }
            Map<TypeVariable<?>, Type> map = TypeUtils.getTypeArguments(clazz, method2.getDeclaringClass());
            for (int i = 0; i < objectArray.length; ++i) {
                Type type;
                Type type2 = TypeUtils.unrollVariables(map, method.getGenericParameterTypes()[i]);
                if (!TypeUtils.equals(type2, type = TypeUtils.unrollVariables(map, method2.getGenericParameterTypes()[i]))) continue block0;
            }
            linkedHashSet.add(method2);
        }
        return linkedHashSet;
    }

    public static Method[] getMethodsWithAnnotation(Class<?> clazz, Class<? extends Annotation> clazz2) {
        List<Method> list = MethodUtils.getMethodsListWithAnnotation(clazz, clazz2);
        return list.toArray(new Method[list.size()]);
    }

    public static List<Method> getMethodsListWithAnnotation(Class<?> clazz, Class<? extends Annotation> clazz2) {
        Validate.isTrue(clazz != null, "The class must not be null", new Object[0]);
        Validate.isTrue(clazz2 != null, "The annotation class must not be null", new Object[0]);
        Method[] methodArray = clazz.getMethods();
        ArrayList<Method> arrayList = new ArrayList<Method>();
        for (Method method : methodArray) {
            if (method.getAnnotation(clazz2) == null) continue;
            arrayList.add(method);
        }
        return arrayList;
    }
}

