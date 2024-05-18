/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.util;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class Utils {
    public static String repeat(String string, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; ++i) {
            sb.append(string);
        }
        return sb.toString();
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }

    public static File prepareFile(String filename) {
        File file = new File(filename);
        File parent = file.getAbsoluteFile().getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        return file;
    }

    public static Member getMemberFromDescriptor(String descriptor, ClassLoader ... classLoaders) throws ReflectionsException {
        int p0 = descriptor.lastIndexOf(40);
        String memberKey = p0 != -1 ? descriptor.substring(0, p0) : descriptor;
        String methodParameters = p0 != -1 ? descriptor.substring(p0 + 1, descriptor.lastIndexOf(41)) : "";
        int p1 = Math.max(memberKey.lastIndexOf(46), memberKey.lastIndexOf("$"));
        String className = memberKey.substring(memberKey.lastIndexOf(32) + 1, p1);
        String memberName = memberKey.substring(p1 + 1);
        Class[] parameterTypes = null;
        if (!Utils.isEmpty(methodParameters)) {
            String[] parameterNames = methodParameters.split(",");
            ArrayList result = new ArrayList(parameterNames.length);
            for (String name : parameterNames) {
                result.add(ReflectionUtils.forName(name.trim(), classLoaders));
            }
            parameterTypes = result.toArray(new Class[result.size()]);
        }
        for (Class<?> aClass = ReflectionUtils.forName((String)className, (ClassLoader[])classLoaders); aClass != null; aClass = aClass.getSuperclass()) {
            try {
                if (!descriptor.contains("(")) {
                    return aClass.isInterface() ? aClass.getField(memberName) : aClass.getDeclaredField(memberName);
                }
                if (Utils.isConstructor(descriptor)) {
                    return aClass.isInterface() ? aClass.getConstructor(parameterTypes) : aClass.getDeclaredConstructor(parameterTypes);
                }
                return aClass.isInterface() ? aClass.getMethod(memberName, parameterTypes) : aClass.getDeclaredMethod(memberName, parameterTypes);
            }
            catch (Exception e) {
                continue;
            }
        }
        throw new ReflectionsException("Can't resolve member named " + memberName + " for class " + className);
    }

    public static Set<Method> getMethodsFromDescriptors(Iterable<String> annotatedWith, ClassLoader ... classLoaders) {
        HashSet result = Sets.newHashSet();
        for (String annotated : annotatedWith) {
            Method member;
            if (Utils.isConstructor(annotated) || (member = (Method)Utils.getMemberFromDescriptor(annotated, classLoaders)) == null) continue;
            result.add(member);
        }
        return result;
    }

    public static Set<Constructor> getConstructorsFromDescriptors(Iterable<String> annotatedWith, ClassLoader ... classLoaders) {
        HashSet result = Sets.newHashSet();
        for (String annotated : annotatedWith) {
            Constructor member;
            if (!Utils.isConstructor(annotated) || (member = (Constructor)Utils.getMemberFromDescriptor(annotated, classLoaders)) == null) continue;
            result.add(member);
        }
        return result;
    }

    public static Set<Member> getMembersFromDescriptors(Iterable<String> values, ClassLoader ... classLoaders) {
        HashSet result = Sets.newHashSet();
        for (String value : values) {
            try {
                result.add(Utils.getMemberFromDescriptor(value, classLoaders));
            }
            catch (ReflectionsException e) {
                throw new ReflectionsException("Can't resolve member named " + value, e);
            }
        }
        return result;
    }

    public static Field getFieldFromString(String field, ClassLoader ... classLoaders) {
        String className = field.substring(0, field.lastIndexOf(46));
        String fieldName = field.substring(field.lastIndexOf(46) + 1);
        try {
            return ReflectionUtils.forName(className, classLoaders).getDeclaredField(fieldName);
        }
        catch (NoSuchFieldException e) {
            throw new ReflectionsException("Can't resolve field named " + fieldName, e);
        }
    }

    public static void close(InputStream closeable) {
        block3 : {
            try {
                if (closeable != null) {
                    closeable.close();
                }
            }
            catch (IOException e) {
                if (Reflections.log == null) break block3;
                Reflections.log.warn("Could not close InputStream", (Throwable)e);
            }
        }
    }

    @Nullable
    public static Logger findLogger(Class<?> aClass) {
        try {
            Class.forName("org.slf4j.impl.StaticLoggerBinder");
            return LoggerFactory.getLogger(aClass);
        }
        catch (Throwable e) {
            return null;
        }
    }

    public static boolean isConstructor(String fqn) {
        return fqn.contains("init>");
    }

    public static String name(Class type) {
        if (!type.isArray()) {
            return type.getName();
        }
        int dim = 0;
        while (type.isArray()) {
            ++dim;
            type = type.getComponentType();
        }
        return type.getName() + Utils.repeat("[]", dim);
    }

    public static List<String> names(Iterable<Class<?>> types) {
        ArrayList<String> result = new ArrayList<String>();
        for (Class<?> type : types) {
            result.add(Utils.name(type));
        }
        return result;
    }

    public static List<String> names(Class<?> ... types) {
        return Utils.names(Arrays.asList(types));
    }

    public static String name(Constructor constructor) {
        return constructor.getName() + "." + "<init>" + "(" + Joiner.on((String)",").join(Utils.names(constructor.getParameterTypes())) + ")";
    }

    public static String name(Method method) {
        return method.getDeclaringClass().getName() + "." + method.getName() + "(" + Joiner.on((String)", ").join(Utils.names(method.getParameterTypes())) + ")";
    }

    public static String name(Field field) {
        return field.getDeclaringClass().getName() + "." + field.getName();
    }
}

