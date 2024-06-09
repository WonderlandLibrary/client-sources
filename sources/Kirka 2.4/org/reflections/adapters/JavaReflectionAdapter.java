/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.adapters;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import org.reflections.ReflectionUtils;
import org.reflections.adapters.MetadataAdapter;
import org.reflections.util.Utils;
import org.reflections.vfs.Vfs;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class JavaReflectionAdapter
implements MetadataAdapter<Class, Field, Member> {
    @Override
    public List<Field> getFields(Class cls) {
        return Lists.newArrayList((Object[])cls.getDeclaredFields());
    }

    @Override
    public List<Member> getMethods(Class cls) {
        ArrayList methods = Lists.newArrayList();
        methods.addAll(Arrays.asList(cls.getDeclaredMethods()));
        methods.addAll(Arrays.asList(cls.getDeclaredConstructors()));
        return methods;
    }

    @Override
    public String getMethodName(Member method) {
        return method instanceof Method ? method.getName() : (method instanceof Constructor ? "<init>" : null);
    }

    @Override
    public List<String> getParameterNames(Member member) {
        Class<?>[] parameterTypes;
        ArrayList result = Lists.newArrayList();
        Object object = member instanceof Method ? ((Method)member).getParameterTypes() : (parameterTypes = member instanceof Constructor ? ((Constructor)member).getParameterTypes() : null);
        if (parameterTypes != null) {
            for (Class<?> paramType : parameterTypes) {
                String name = JavaReflectionAdapter.getName(paramType);
                result.add(name);
            }
        }
        return result;
    }

    @Override
    public List<String> getClassAnnotationNames(Class aClass) {
        return this.getAnnotationNames(aClass.getDeclaredAnnotations());
    }

    @Override
    public List<String> getFieldAnnotationNames(Field field) {
        return this.getAnnotationNames(field.getDeclaredAnnotations());
    }

    @Override
    public List<String> getMethodAnnotationNames(Member method) {
        Annotation[] annotations = method instanceof Method ? ((Method)method).getDeclaredAnnotations() : (method instanceof Constructor ? ((Constructor)method).getDeclaredAnnotations() : null);
        return this.getAnnotationNames(annotations);
    }

    @Override
    public List<String> getParameterAnnotationNames(Member method, int parameterIndex) {
        Annotation[][] annotations = method instanceof Method ? ((Method)method).getParameterAnnotations() : (method instanceof Constructor ? ((Constructor)method).getParameterAnnotations() : (Annotation[][])null);
        return this.getAnnotationNames(annotations != null ? annotations[parameterIndex] : null);
    }

    @Override
    public String getReturnTypeName(Member method) {
        return ((Method)method).getReturnType().getName();
    }

    @Override
    public String getFieldName(Field field) {
        return field.getName();
    }

    @Override
    public Class getOfCreateClassObject(Vfs.File file) throws Exception {
        return this.getOfCreateClassObject(file, null);
    }

    public Class getOfCreateClassObject(Vfs.File file, @Nullable ClassLoader ... loaders) throws Exception {
        String name = file.getRelativePath().replace("/", ".").replace(".class", "");
        return ReflectionUtils.forName(name, loaders);
    }

    @Override
    public String getMethodModifier(Member method) {
        return Modifier.toString(method.getModifiers());
    }

    @Override
    public String getMethodKey(Class cls, Member method) {
        return this.getMethodName(method) + "(" + Joiner.on((String)", ").join(this.getParameterNames(method)) + ")";
    }

    @Override
    public String getMethodFullKey(Class cls, Member method) {
        return this.getClassName(cls) + "." + this.getMethodKey(cls, method);
    }

    @Override
    public boolean isPublic(Object o) {
        Integer mod = o instanceof Class ? ((Class)o).getModifiers() : (o instanceof Member ? Integer.valueOf(((Member)o).getModifiers()) : null).intValue();
        return mod != null && Modifier.isPublic(mod);
    }

    @Override
    public String getClassName(Class cls) {
        return cls.getName();
    }

    @Override
    public String getSuperclassName(Class cls) {
        Class superclass = cls.getSuperclass();
        return superclass != null ? superclass.getName() : "";
    }

    @Override
    public List<String> getInterfacesNames(Class cls) {
        Class<?>[] classes = cls.getInterfaces();
        ArrayList<String> names = new ArrayList<String>(classes != null ? classes.length : 0);
        if (classes != null) {
            for (Class<?> cls1 : classes) {
                names.add(cls1.getName());
            }
        }
        return names;
    }

    @Override
    public boolean acceptsInput(String file) {
        return file.endsWith(".class");
    }

    private List<String> getAnnotationNames(Annotation[] annotations) {
        ArrayList<String> names = new ArrayList<String>(annotations.length);
        for (Annotation annotation : annotations) {
            names.add(annotation.annotationType().getName());
        }
        return names;
    }

    public static String getName(Class type) {
        if (type.isArray()) {
            try {
                Class<?> cl = type;
                int dim = 0;
                while (cl.isArray()) {
                    ++dim;
                    cl = cl.getComponentType();
                }
                return cl.getName() + Utils.repeat("[]", dim);
            }
            catch (Throwable cl) {
                // empty catch block
            }
        }
        return type.getName();
    }
}

