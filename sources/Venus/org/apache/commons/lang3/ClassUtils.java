/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.JavaVersion;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.mutable.MutableObject;

public class ClassUtils {
    public static final char PACKAGE_SEPARATOR_CHAR = '.';
    public static final String PACKAGE_SEPARATOR;
    public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
    public static final String INNER_CLASS_SEPARATOR;
    private static final Map<String, Class<?>> namePrimitiveMap;
    private static final Map<Class<?>, Class<?>> primitiveWrapperMap;
    private static final Map<Class<?>, Class<?>> wrapperPrimitiveMap;
    private static final Map<String, String> abbreviationMap;
    private static final Map<String, String> reverseAbbreviationMap;

    public static String getShortClassName(Object object, String string) {
        if (object == null) {
            return string;
        }
        return ClassUtils.getShortClassName(object.getClass());
    }

    public static String getShortClassName(Class<?> clazz) {
        if (clazz == null) {
            return "";
        }
        return ClassUtils.getShortClassName(clazz.getName());
    }

    public static String getShortClassName(String string) {
        int n;
        if (StringUtils.isEmpty(string)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (string.startsWith("[")) {
            while (string.charAt(0) == '[') {
                string = string.substring(1);
                stringBuilder.append("[]");
            }
            if (string.charAt(0) == 'L' && string.charAt(string.length() - 1) == ';') {
                string = string.substring(1, string.length() - 1);
            }
            if (reverseAbbreviationMap.containsKey(string)) {
                string = reverseAbbreviationMap.get(string);
            }
        }
        int n2 = string.indexOf(36, (n = string.lastIndexOf(46)) == -1 ? 0 : n + 1);
        String string2 = string.substring(n + 1);
        if (n2 != -1) {
            string2 = string2.replace('$', '.');
        }
        return string2 + stringBuilder;
    }

    public static String getSimpleName(Class<?> clazz) {
        if (clazz == null) {
            return "";
        }
        return clazz.getSimpleName();
    }

    public static String getSimpleName(Object object, String string) {
        if (object == null) {
            return string;
        }
        return ClassUtils.getSimpleName(object.getClass());
    }

    public static String getPackageName(Object object, String string) {
        if (object == null) {
            return string;
        }
        return ClassUtils.getPackageName(object.getClass());
    }

    public static String getPackageName(Class<?> clazz) {
        if (clazz == null) {
            return "";
        }
        return ClassUtils.getPackageName(clazz.getName());
    }

    public static String getPackageName(String string) {
        int n;
        if (StringUtils.isEmpty(string)) {
            return "";
        }
        while (string.charAt(0) == '[') {
            string = string.substring(1);
        }
        if (string.charAt(0) == 'L' && string.charAt(string.length() - 1) == ';') {
            string = string.substring(1);
        }
        if ((n = string.lastIndexOf(46)) == -1) {
            return "";
        }
        return string.substring(0, n);
    }

    public static String getAbbreviatedName(Class<?> clazz, int n) {
        if (clazz == null) {
            return "";
        }
        return ClassUtils.getAbbreviatedName(clazz.getName(), n);
    }

    public static String getAbbreviatedName(String string, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("len must be > 0");
        }
        if (string == null) {
            return "";
        }
        int n2 = n;
        int n3 = StringUtils.countMatches((CharSequence)string, '.');
        Object[] objectArray = new String[n3 + 1];
        int n4 = string.length() - 1;
        for (int i = n3; i >= 0; --i) {
            int n5 = string.lastIndexOf(46, n4);
            String string2 = string.substring(n5 + 1, n4 + 1);
            n2 -= string2.length();
            if (i > 0) {
                --n2;
            }
            objectArray[i] = i == n3 ? string2 : (n2 > 0 ? string2 : string2.substring(0, 1));
            n4 = n5 - 1;
        }
        return StringUtils.join(objectArray, '.');
    }

    public static List<Class<?>> getAllSuperclasses(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Class<?> clazz2 = clazz.getSuperclass(); clazz2 != null; clazz2 = clazz2.getSuperclass()) {
            arrayList.add(clazz2);
        }
        return arrayList;
    }

    public static List<Class<?>> getAllInterfaces(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        ClassUtils.getAllInterfaces(clazz, linkedHashSet);
        return new ArrayList(linkedHashSet);
    }

    private static void getAllInterfaces(Class<?> clazz, HashSet<Class<?>> hashSet) {
        while (clazz != null) {
            Class<?>[] classArray;
            for (Class<?> clazz2 : classArray = clazz.getInterfaces()) {
                if (!hashSet.add(clazz2)) continue;
                ClassUtils.getAllInterfaces(clazz2, hashSet);
            }
            clazz = clazz.getSuperclass();
        }
    }

    public static List<Class<?>> convertClassNamesToClasses(List<String> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (String string : list) {
            try {
                arrayList.add(Class.forName(string));
            } catch (Exception exception) {
                arrayList.add(null);
            }
        }
        return arrayList;
    }

    public static List<String> convertClassesToClassNames(List<Class<?>> list) {
        if (list == null) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<String>(list.size());
        for (Class<?> clazz : list) {
            if (clazz == null) {
                arrayList.add(null);
                continue;
            }
            arrayList.add(clazz.getName());
        }
        return arrayList;
    }

    public static boolean isAssignable(Class<?>[] classArray, Class<?> ... classArray2) {
        return ClassUtils.isAssignable(classArray, classArray2, SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_5));
    }

    public static boolean isAssignable(Class<?>[] classArray, Class<?>[] classArray2, boolean bl) {
        if (!ArrayUtils.isSameLength(classArray, classArray2)) {
            return true;
        }
        if (classArray == null) {
            classArray = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        if (classArray2 == null) {
            classArray2 = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        for (int i = 0; i < classArray.length; ++i) {
            if (ClassUtils.isAssignable(classArray[i], classArray2[i], bl)) continue;
            return true;
        }
        return false;
    }

    public static boolean isPrimitiveOrWrapper(Class<?> clazz) {
        if (clazz == null) {
            return true;
        }
        return clazz.isPrimitive() || ClassUtils.isPrimitiveWrapper(clazz);
    }

    public static boolean isPrimitiveWrapper(Class<?> clazz) {
        return wrapperPrimitiveMap.containsKey(clazz);
    }

    public static boolean isAssignable(Class<?> clazz, Class<?> clazz2) {
        return ClassUtils.isAssignable(clazz, clazz2, SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_5));
    }

    public static boolean isAssignable(Class<?> clazz, Class<?> clazz2, boolean bl) {
        if (clazz2 == null) {
            return true;
        }
        if (clazz == null) {
            return !clazz2.isPrimitive();
        }
        if (bl) {
            if (clazz.isPrimitive() && !clazz2.isPrimitive() && (clazz = ClassUtils.primitiveToWrapper(clazz)) == null) {
                return true;
            }
            if (clazz2.isPrimitive() && !clazz.isPrimitive() && (clazz = ClassUtils.wrapperToPrimitive(clazz)) == null) {
                return true;
            }
        }
        if (clazz.equals(clazz2)) {
            return false;
        }
        if (clazz.isPrimitive()) {
            if (!clazz2.isPrimitive()) {
                return true;
            }
            if (Integer.TYPE.equals(clazz)) {
                return Long.TYPE.equals(clazz2) || Float.TYPE.equals(clazz2) || Double.TYPE.equals(clazz2);
            }
            if (Long.TYPE.equals(clazz)) {
                return Float.TYPE.equals(clazz2) || Double.TYPE.equals(clazz2);
            }
            if (Boolean.TYPE.equals(clazz)) {
                return true;
            }
            if (Double.TYPE.equals(clazz)) {
                return true;
            }
            if (Float.TYPE.equals(clazz)) {
                return Double.TYPE.equals(clazz2);
            }
            if (Character.TYPE.equals(clazz)) {
                return Integer.TYPE.equals(clazz2) || Long.TYPE.equals(clazz2) || Float.TYPE.equals(clazz2) || Double.TYPE.equals(clazz2);
            }
            if (Short.TYPE.equals(clazz)) {
                return Integer.TYPE.equals(clazz2) || Long.TYPE.equals(clazz2) || Float.TYPE.equals(clazz2) || Double.TYPE.equals(clazz2);
            }
            if (Byte.TYPE.equals(clazz)) {
                return Short.TYPE.equals(clazz2) || Integer.TYPE.equals(clazz2) || Long.TYPE.equals(clazz2) || Float.TYPE.equals(clazz2) || Double.TYPE.equals(clazz2);
            }
            return true;
        }
        return clazz2.isAssignableFrom(clazz);
    }

    public static Class<?> primitiveToWrapper(Class<?> clazz) {
        Class<?> clazz2 = clazz;
        if (clazz != null && clazz.isPrimitive()) {
            clazz2 = primitiveWrapperMap.get(clazz);
        }
        return clazz2;
    }

    public static Class<?>[] primitivesToWrappers(Class<?> ... classArray) {
        if (classArray == null) {
            return null;
        }
        if (classArray.length == 0) {
            return classArray;
        }
        Class[] classArray2 = new Class[classArray.length];
        for (int i = 0; i < classArray.length; ++i) {
            classArray2[i] = ClassUtils.primitiveToWrapper(classArray[i]);
        }
        return classArray2;
    }

    public static Class<?> wrapperToPrimitive(Class<?> clazz) {
        return wrapperPrimitiveMap.get(clazz);
    }

    public static Class<?>[] wrappersToPrimitives(Class<?> ... classArray) {
        if (classArray == null) {
            return null;
        }
        if (classArray.length == 0) {
            return classArray;
        }
        Class[] classArray2 = new Class[classArray.length];
        for (int i = 0; i < classArray.length; ++i) {
            classArray2[i] = ClassUtils.wrapperToPrimitive(classArray[i]);
        }
        return classArray2;
    }

    public static boolean isInnerClass(Class<?> clazz) {
        return clazz != null && clazz.getEnclosingClass() != null;
    }

    public static Class<?> getClass(ClassLoader classLoader, String string, boolean bl) throws ClassNotFoundException {
        try {
            Class<?> clazz = namePrimitiveMap.containsKey(string) ? namePrimitiveMap.get(string) : Class.forName(ClassUtils.toCanonicalName(string), bl, classLoader);
            return clazz;
        } catch (ClassNotFoundException classNotFoundException) {
            int n = string.lastIndexOf(46);
            if (n != -1) {
                try {
                    return ClassUtils.getClass(classLoader, string.substring(0, n) + '$' + string.substring(n + 1), bl);
                } catch (ClassNotFoundException classNotFoundException2) {
                    // empty catch block
                }
            }
            throw classNotFoundException;
        }
    }

    public static Class<?> getClass(ClassLoader classLoader, String string) throws ClassNotFoundException {
        return ClassUtils.getClass(classLoader, string, true);
    }

    public static Class<?> getClass(String string) throws ClassNotFoundException {
        return ClassUtils.getClass(string, true);
    }

    public static Class<?> getClass(String string, boolean bl) throws ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ClassLoader classLoader2 = classLoader == null ? ClassUtils.class.getClassLoader() : classLoader;
        return ClassUtils.getClass(classLoader2, string, bl);
    }

    public static Method getPublicMethod(Class<?> clazz, String string, Class<?> ... classArray) throws SecurityException, NoSuchMethodException {
        Method method = clazz.getMethod(string, classArray);
        if (Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
            return method;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(ClassUtils.getAllInterfaces(clazz));
        arrayList.addAll(ClassUtils.getAllSuperclasses(clazz));
        for (Class clazz2 : arrayList) {
            Method method2;
            if (!Modifier.isPublic(clazz2.getModifiers())) continue;
            try {
                method2 = clazz2.getMethod(string, classArray);
            } catch (NoSuchMethodException noSuchMethodException) {
                continue;
            }
            if (!Modifier.isPublic(method2.getDeclaringClass().getModifiers())) continue;
            return method2;
        }
        throw new NoSuchMethodException("Can't find a public method for " + string + " " + ArrayUtils.toString(classArray));
    }

    private static String toCanonicalName(String string) {
        if ((string = StringUtils.deleteWhitespace(string)) == null) {
            throw new NullPointerException("className must not be null.");
        }
        if (string.endsWith("[]")) {
            StringBuilder stringBuilder = new StringBuilder();
            while (string.endsWith("[]")) {
                string = string.substring(0, string.length() - 2);
                stringBuilder.append("[");
            }
            String string2 = abbreviationMap.get(string);
            if (string2 != null) {
                stringBuilder.append(string2);
            } else {
                stringBuilder.append("L").append(string).append(";");
            }
            string = stringBuilder.toString();
        }
        return string;
    }

    public static Class<?>[] toClass(Object ... objectArray) {
        if (objectArray == null) {
            return null;
        }
        if (objectArray.length == 0) {
            return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        Class[] classArray = new Class[objectArray.length];
        for (int i = 0; i < objectArray.length; ++i) {
            classArray[i] = objectArray[i] == null ? null : objectArray[i].getClass();
        }
        return classArray;
    }

    public static String getShortCanonicalName(Object object, String string) {
        if (object == null) {
            return string;
        }
        return ClassUtils.getShortCanonicalName(object.getClass().getName());
    }

    public static String getShortCanonicalName(Class<?> clazz) {
        if (clazz == null) {
            return "";
        }
        return ClassUtils.getShortCanonicalName(clazz.getName());
    }

    public static String getShortCanonicalName(String string) {
        return ClassUtils.getShortClassName(ClassUtils.getCanonicalName(string));
    }

    public static String getPackageCanonicalName(Object object, String string) {
        if (object == null) {
            return string;
        }
        return ClassUtils.getPackageCanonicalName(object.getClass().getName());
    }

    public static String getPackageCanonicalName(Class<?> clazz) {
        if (clazz == null) {
            return "";
        }
        return ClassUtils.getPackageCanonicalName(clazz.getName());
    }

    public static String getPackageCanonicalName(String string) {
        return ClassUtils.getPackageName(ClassUtils.getCanonicalName(string));
    }

    private static String getCanonicalName(String string) {
        if ((string = StringUtils.deleteWhitespace(string)) == null) {
            return null;
        }
        int n = 0;
        while (string.startsWith("[")) {
            ++n;
            string = string.substring(1);
        }
        if (n < 1) {
            return string;
        }
        if (string.startsWith("L")) {
            string = string.substring(1, string.endsWith(";") ? string.length() - 1 : string.length());
        } else if (string.length() > 0) {
            string = reverseAbbreviationMap.get(string.substring(0, 1));
        }
        StringBuilder stringBuilder = new StringBuilder(string);
        for (int i = 0; i < n; ++i) {
            stringBuilder.append("[]");
        }
        return stringBuilder.toString();
    }

    public static Iterable<Class<?>> hierarchy(Class<?> clazz) {
        return ClassUtils.hierarchy(clazz, Interfaces.EXCLUDE);
    }

    public static Iterable<Class<?>> hierarchy(Class<?> clazz, Interfaces interfaces) {
        Iterable iterable = new Iterable<Class<?>>(clazz){
            final Class val$type;
            {
                this.val$type = clazz;
            }

            @Override
            public Iterator<Class<?>> iterator() {
                MutableObject<Class> mutableObject = new MutableObject<Class>(this.val$type);
                return new Iterator<Class<?>>(this, mutableObject){
                    final MutableObject val$next;
                    final 1 this$0;
                    {
                        this.this$0 = var1_1;
                        this.val$next = mutableObject;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.val$next.getValue() != null;
                    }

                    @Override
                    public Class<?> next() {
                        Class clazz = (Class)this.val$next.getValue();
                        this.val$next.setValue(clazz.getSuperclass());
                        return clazz;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public Object next() {
                        return this.next();
                    }
                };
            }
        };
        if (interfaces != Interfaces.INCLUDE) {
            return iterable;
        }
        return new Iterable<Class<?>>(iterable){
            final Iterable val$classes;
            {
                this.val$classes = iterable;
            }

            @Override
            public Iterator<Class<?>> iterator() {
                HashSet hashSet = new HashSet();
                Iterator iterator2 = this.val$classes.iterator();
                return new Iterator<Class<?>>(this, iterator2, hashSet){
                    Iterator<Class<?>> interfaces;
                    final Iterator val$wrapped;
                    final Set val$seenInterfaces;
                    final 2 this$0;
                    {
                        this.this$0 = var1_1;
                        this.val$wrapped = iterator2;
                        this.val$seenInterfaces = set;
                        this.interfaces = Collections.emptySet().iterator();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.interfaces.hasNext() || this.val$wrapped.hasNext();
                    }

                    @Override
                    public Class<?> next() {
                        if (this.interfaces.hasNext()) {
                            Class<?> clazz = this.interfaces.next();
                            this.val$seenInterfaces.add(clazz);
                            return clazz;
                        }
                        Class clazz = (Class)this.val$wrapped.next();
                        LinkedHashSet linkedHashSet = new LinkedHashSet();
                        this.walkInterfaces(linkedHashSet, clazz);
                        this.interfaces = linkedHashSet.iterator();
                        return clazz;
                    }

                    private void walkInterfaces(Set<Class<?>> set, Class<?> clazz) {
                        for (Class<?> clazz2 : clazz.getInterfaces()) {
                            if (!this.val$seenInterfaces.contains(clazz2)) {
                                set.add(clazz2);
                            }
                            this.walkInterfaces(set, clazz2);
                        }
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public Object next() {
                        return this.next();
                    }
                };
            }
        };
    }

    static {
        Map.Entry<Class<?>, Class<?>> entry22;
        PACKAGE_SEPARATOR = String.valueOf('.');
        INNER_CLASS_SEPARATOR = String.valueOf('$');
        namePrimitiveMap = new HashMap();
        namePrimitiveMap.put("boolean", Boolean.TYPE);
        namePrimitiveMap.put("byte", Byte.TYPE);
        namePrimitiveMap.put("char", Character.TYPE);
        namePrimitiveMap.put("short", Short.TYPE);
        namePrimitiveMap.put("int", Integer.TYPE);
        namePrimitiveMap.put("long", Long.TYPE);
        namePrimitiveMap.put("double", Double.TYPE);
        namePrimitiveMap.put("float", Float.TYPE);
        namePrimitiveMap.put("void", Void.TYPE);
        primitiveWrapperMap = new HashMap();
        primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
        primitiveWrapperMap.put(Byte.TYPE, Byte.class);
        primitiveWrapperMap.put(Character.TYPE, Character.class);
        primitiveWrapperMap.put(Short.TYPE, Short.class);
        primitiveWrapperMap.put(Integer.TYPE, Integer.class);
        primitiveWrapperMap.put(Long.TYPE, Long.class);
        primitiveWrapperMap.put(Double.TYPE, Double.class);
        primitiveWrapperMap.put(Float.TYPE, Float.class);
        primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
        wrapperPrimitiveMap = new HashMap();
        for (Map.Entry<Class<?>, Class<?>> entry22 : primitiveWrapperMap.entrySet()) {
            Class<?> object;
            Class<?> clazz = entry22.getKey();
            if (clazz.equals(object = entry22.getValue())) continue;
            wrapperPrimitiveMap.put(object, clazz);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("int", "I");
        hashMap.put("boolean", "Z");
        hashMap.put("float", "F");
        hashMap.put("long", "J");
        hashMap.put("short", "S");
        hashMap.put("byte", "B");
        hashMap.put("double", "D");
        hashMap.put("char", "C");
        entry22 = new HashMap();
        for (Map.Entry entry : hashMap.entrySet()) {
            entry22.put((Class<?>)entry.getValue(), (Class<?>)entry.getKey());
        }
        abbreviationMap = Collections.unmodifiableMap(hashMap);
        reverseAbbreviationMap = Collections.unmodifiableMap(entry22);
    }

    public static enum Interfaces {
        INCLUDE,
        EXCLUDE;

    }
}

