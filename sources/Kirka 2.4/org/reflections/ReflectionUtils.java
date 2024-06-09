/*
 * Decompiled with CFR 0.143.
 */
package org.reflections;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.Utils;
import org.slf4j.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class ReflectionUtils {
    public static boolean includeObject = false;
    private static List<String> primitiveNames;
    private static List<Class> primitiveTypes;
    private static List<String> primitiveDescriptors;

    public static Set<Class<?>> getAllSuperTypes(Class<?> type, Predicate<? super Class<?>> ... predicates) {
        LinkedHashSet result = Sets.newLinkedHashSet();
        if (type != null && (includeObject || !type.equals(Object.class))) {
            result.add(type);
            result.addAll(ReflectionUtils.getAllSuperTypes(type.getSuperclass(), new Predicate[0]));
            for (Class<?> ifc : type.getInterfaces()) {
                result.addAll(ReflectionUtils.getAllSuperTypes(ifc, new Predicate[0]));
            }
        }
        return ReflectionUtils.filter(result, predicates);
    }

    public static Set<Method> getAllMethods(Class<?> type, Predicate<? super Method> ... predicates) {
        HashSet result = Sets.newHashSet();
        for (Class<?> t : ReflectionUtils.getAllSuperTypes(type, new Predicate[0])) {
            result.addAll(ReflectionUtils.getMethods(t, predicates));
        }
        return result;
    }

    public static Set<Method> getMethods(Class<?> t, Predicate<? super Method> ... predicates) {
        return ReflectionUtils.filter(t.isInterface() ? t.getMethods() : t.getDeclaredMethods(), predicates);
    }

    public static Set<Constructor> getAllConstructors(Class<?> type, Predicate<? super Constructor> ... predicates) {
        HashSet result = Sets.newHashSet();
        for (Class<?> t : ReflectionUtils.getAllSuperTypes(type, new Predicate[0])) {
            result.addAll(ReflectionUtils.getConstructors(t, predicates));
        }
        return result;
    }

    public static Set<Constructor> getConstructors(Class<?> t, Predicate<? super Constructor> ... predicates) {
        return ReflectionUtils.filter(t.getDeclaredConstructors(), predicates);
    }

    public static Set<Field> getAllFields(Class<?> type, Predicate<? super Field> ... predicates) {
        HashSet result = Sets.newHashSet();
        for (Class<?> t : ReflectionUtils.getAllSuperTypes(type, new Predicate[0])) {
            result.addAll(ReflectionUtils.getFields(t, predicates));
        }
        return result;
    }

    public static Set<Field> getFields(Class<?> type, Predicate<? super Field> ... predicates) {
        return ReflectionUtils.filter(type.getDeclaredFields(), predicates);
    }

    public static <T extends AnnotatedElement> Set<Annotation> getAllAnnotations(T type, Predicate<Annotation> ... predicates) {
        HashSet result = Sets.newHashSet();
        if (type instanceof Class) {
            for (Class<?> t : ReflectionUtils.getAllSuperTypes((Class)type, new Predicate[0])) {
                result.addAll(ReflectionUtils.getAnnotations(t, predicates));
            }
        } else {
            result.addAll(ReflectionUtils.getAnnotations(type, predicates));
        }
        return result;
    }

    public static <T extends AnnotatedElement> Set<Annotation> getAnnotations(T type, Predicate<Annotation> ... predicates) {
        return ReflectionUtils.filter(type.getDeclaredAnnotations(), predicates);
    }

    public static <T extends AnnotatedElement> Set<T> getAll(Set<T> elements, Predicate<? super T> ... predicates) {
        return Utils.isEmpty(predicates) ? elements : Sets.newHashSet((Iterable)Iterables.filter(elements, (Predicate)Predicates.and(predicates)));
    }

    public static <T extends Member> Predicate<T> withName(final String name) {
        return new Predicate<T>(){

            public boolean apply(@Nullable T input) {
                return input != null && input.getName().equals(name);
            }
        };
    }

    public static <T extends Member> Predicate<T> withPrefix(final String prefix) {
        return new Predicate<T>(){

            public boolean apply(@Nullable T input) {
                return input != null && input.getName().startsWith(prefix);
            }
        };
    }

    public static <T extends AnnotatedElement> Predicate<T> withPattern(final String regex) {
        return new Predicate<T>(){

            public boolean apply(@Nullable T input) {
                return Pattern.matches(regex, input.toString());
            }
        };
    }

    public static <T extends AnnotatedElement> Predicate<T> withAnnotation(final Class<? extends Annotation> annotation) {
        return new Predicate<T>(){

            public boolean apply(@Nullable T input) {
                return input != null && input.isAnnotationPresent(annotation);
            }
        };
    }

    public static <T extends AnnotatedElement> Predicate<T> withAnnotations(final Class<? extends Annotation> ... annotations) {
        return new Predicate<T>(){

            public boolean apply(@Nullable T input) {
                return input != null && Arrays.equals(annotations, ReflectionUtils.annotationTypes(input.getAnnotations()));
            }
        };
    }

    public static <T extends AnnotatedElement> Predicate<T> withAnnotation(final Annotation annotation) {
        return new Predicate<T>(){

            public boolean apply(@Nullable T input) {
                return input != null && input.isAnnotationPresent(annotation.annotationType()) && ReflectionUtils.areAnnotationMembersMatching(input.getAnnotation(annotation.annotationType()), annotation);
            }
        };
    }

    public static <T extends AnnotatedElement> Predicate<T> withAnnotations(final Annotation ... annotations) {
        return new Predicate<T>(){

            public boolean apply(@Nullable T input) {
                Annotation[] inputAnnotations;
                if (input != null && (inputAnnotations = input.getAnnotations()).length == annotations.length) {
                    for (int i = 0; i < inputAnnotations.length; ++i) {
                        if (ReflectionUtils.areAnnotationMembersMatching(inputAnnotations[i], annotations[i])) continue;
                        return false;
                    }
                }
                return true;
            }
        };
    }

    public static Predicate<Member> withParameters(final Class<?> ... types) {
        return new Predicate<Member>(){

            public boolean apply(@Nullable Member input) {
                return Arrays.equals(ReflectionUtils.parameterTypes(input), types);
            }
        };
    }

    public static Predicate<Member> withParametersAssignableTo(final Class ... types) {
        return new Predicate<Member>(){

            public boolean apply(@Nullable Member input) {
                Class[] parameterTypes;
                if (input != null && (parameterTypes = ReflectionUtils.parameterTypes(input)).length == types.length) {
                    for (int i = 0; i < parameterTypes.length; ++i) {
                        if (parameterTypes[i].isAssignableFrom(types[i]) && (parameterTypes[i] != Object.class || types[i] == Object.class)) continue;
                        return false;
                    }
                    return true;
                }
                return false;
            }
        };
    }

    public static Predicate<Member> withParametersCount(final int count) {
        return new Predicate<Member>(){

            public boolean apply(@Nullable Member input) {
                return input != null && ReflectionUtils.parameterTypes(input).length == count;
            }
        };
    }

    public static Predicate<Member> withAnyParameterAnnotation(final Class<? extends Annotation> annotationClass) {
        return new Predicate<Member>(){

            public boolean apply(@Nullable Member input) {
                return input != null && Iterables.any((Iterable)ReflectionUtils.annotationTypes(ReflectionUtils.parameterAnnotations(input)), (Predicate)new Predicate<Class<? extends Annotation>>(){

                    public boolean apply(@Nullable Class<? extends Annotation> input) {
                        return input.equals((Object)annotationClass);
                    }
                });
            }

        };
    }

    public static Predicate<Member> withAnyParameterAnnotation(final Annotation annotation) {
        return new Predicate<Member>(){

            public boolean apply(@Nullable Member input) {
                return input != null && Iterables.any((Iterable)ReflectionUtils.parameterAnnotations(input), (Predicate)new Predicate<Annotation>(){

                    public boolean apply(@Nullable Annotation input) {
                        return ReflectionUtils.areAnnotationMembersMatching(annotation, input);
                    }
                });
            }

        };
    }

    public static <T> Predicate<Field> withType(final Class<T> type) {
        return new Predicate<Field>(){

            public boolean apply(@Nullable Field input) {
                return input != null && input.getType().equals((Object)type);
            }
        };
    }

    public static <T> Predicate<Field> withTypeAssignableTo(final Class<T> type) {
        return new Predicate<Field>(){

            public boolean apply(@Nullable Field input) {
                return input != null && type.isAssignableFrom(input.getType());
            }
        };
    }

    public static <T> Predicate<Method> withReturnType(final Class<T> type) {
        return new Predicate<Method>(){

            public boolean apply(@Nullable Method input) {
                return input != null && input.getReturnType().equals((Object)type);
            }
        };
    }

    public static <T> Predicate<Method> withReturnTypeAssignableTo(final Class<T> type) {
        return new Predicate<Method>(){

            public boolean apply(@Nullable Method input) {
                return input != null && type.isAssignableFrom(input.getReturnType());
            }
        };
    }

    public static <T extends Member> Predicate<T> withModifier(final int mod) {
        return new Predicate<T>(){

            public boolean apply(@Nullable T input) {
                return input != null && (input.getModifiers() & mod) != 0;
            }
        };
    }

    public static Predicate<Class<?>> withClassModifier(final int mod) {
        return new Predicate<Class<?>>(){

            public boolean apply(@Nullable Class<?> input) {
                return input != null && (input.getModifiers() & mod) != 0;
            }
        };
    }

    public static Class<?> forName(String typeName, ClassLoader ... classLoaders) {
        String type;
        if (ReflectionUtils.getPrimitiveNames().contains(typeName)) {
            return ReflectionUtils.getPrimitiveTypes().get(ReflectionUtils.getPrimitiveNames().indexOf(typeName));
        }
        if (typeName.contains("[")) {
            int i = typeName.indexOf("[");
            type = typeName.substring(0, i);
            String array = typeName.substring(i).replace("]", "");
            type = ReflectionUtils.getPrimitiveNames().contains(type) ? ReflectionUtils.getPrimitiveDescriptors().get(ReflectionUtils.getPrimitiveNames().indexOf(type)) : "L" + type + ";";
            type = (String)array + type;
        } else {
            type = typeName;
        }
        ArrayList reflectionsExceptions = Lists.newArrayList();
        for (ClassLoader classLoader : ClasspathHelper.classLoaders(classLoaders)) {
            if (type.contains("[")) {
                try {
                    return Class.forName(type, false, classLoader);
                }
                catch (Throwable e) {
                    reflectionsExceptions.add(new ReflectionsException("could not get type for name " + typeName, e));
                }
            }
            try {
                return classLoader.loadClass(type);
            }
            catch (Throwable e) {
                reflectionsExceptions.add(new ReflectionsException("could not get type for name " + typeName, e));
            }
        }
        if (Reflections.log != null) {
            for (ReflectionsException reflectionsException : reflectionsExceptions) {
                Reflections.log.warn("could not get type for name " + typeName + " from any class loader", (Throwable)reflectionsException);
            }
        }
        return null;
    }

    public static <T> List<Class<? extends T>> forNames(Iterable<String> classes, ClassLoader ... classLoaders) {
        ArrayList<Class<T>> result = new ArrayList<Class<T>>();
        for (String className : classes) {
            Class<?> type = ReflectionUtils.forName(className, classLoaders);
            if (type == null) continue;
            result.add(type);
        }
        return result;
    }

    private static Class[] parameterTypes(Member member) {
        return member != null ? (member.getClass() == Method.class ? ((Method)member).getParameterTypes() : (member.getClass() == Constructor.class ? ((Constructor)member).getParameterTypes() : null)) : null;
    }

    private static Set<Annotation> parameterAnnotations(Member member) {
        Annotation[][] annotations;
        HashSet result = Sets.newHashSet();
        for (Annotation[] annotation : annotations = member instanceof Method ? ((Method)member).getParameterAnnotations() : (member instanceof Constructor ? ((Constructor)member).getParameterAnnotations() : (Annotation[][])null)) {
            Collections.addAll(result, annotation);
        }
        return result;
    }

    private static Set<Class<? extends Annotation>> annotationTypes(Iterable<Annotation> annotations) {
        HashSet result = Sets.newHashSet();
        for (Annotation annotation : annotations) {
            result.add(annotation.annotationType());
        }
        return result;
    }

    private static Class<? extends Annotation>[] annotationTypes(Annotation[] annotations) {
        Class[] result = new Class[annotations.length];
        for (int i = 0; i < annotations.length; ++i) {
            result[i] = annotations[i].annotationType();
        }
        return result;
    }

    private static void initPrimitives() {
        if (primitiveNames == null) {
            primitiveNames = Lists.newArrayList((Object[])new String[]{"boolean", "char", "byte", "short", "int", "long", "float", "double", "void"});
            primitiveTypes = Lists.newArrayList((Object[])new Class[]{Boolean.TYPE, Character.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE});
            primitiveDescriptors = Lists.newArrayList((Object[])new String[]{"Z", "C", "B", "S", "I", "J", "F", "D", "V"});
        }
    }

    private static List<String> getPrimitiveNames() {
        ReflectionUtils.initPrimitives();
        return primitiveNames;
    }

    private static List<Class> getPrimitiveTypes() {
        ReflectionUtils.initPrimitives();
        return primitiveTypes;
    }

    private static List<String> getPrimitiveDescriptors() {
        ReflectionUtils.initPrimitives();
        return primitiveDescriptors;
    }

    static <T> Set<T> filter(T[] elements, Predicate<? super T> ... predicates) {
        return Utils.isEmpty(predicates) ? Sets.newHashSet((Object[])elements) : Sets.newHashSet((Iterable)Iterables.filter(Arrays.asList(elements), (Predicate)Predicates.and(predicates)));
    }

    static <T> Set<T> filter(Iterable<T> elements, Predicate<? super T> ... predicates) {
        return Utils.isEmpty(predicates) ? Sets.newHashSet(elements) : Sets.newHashSet((Iterable)Iterables.filter(elements, (Predicate)Predicates.and(predicates)));
    }

    private static boolean areAnnotationMembersMatching(Annotation annotation1, Annotation annotation2) {
        if (annotation2 != null && annotation1.annotationType() == annotation2.annotationType()) {
            for (Method method : annotation1.annotationType().getDeclaredMethods()) {
                try {
                    if (method.invoke(annotation1, new Object[0]).equals(method.invoke(annotation2, new Object[0]))) continue;
                    return false;
                }
                catch (Exception e) {
                    throw new ReflectionsException(String.format("could not invoke method %s on annotation %s", method.getName(), annotation1.annotationType()), e);
                }
            }
            return true;
        }
        return false;
    }

}

