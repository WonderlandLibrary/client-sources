/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.adapters;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.ClassFile;
import javassist.bytecode.Descriptor;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.ParameterAnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;
import org.reflections.ReflectionsException;
import org.reflections.adapters.MetadataAdapter;
import org.reflections.util.Utils;
import org.reflections.vfs.Vfs;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class JavassistAdapter
implements MetadataAdapter<ClassFile, FieldInfo, MethodInfo> {
    public static boolean includeInvisibleTag = true;

    @Override
    public List<FieldInfo> getFields(ClassFile cls) {
        return cls.getFields();
    }

    @Override
    public List<MethodInfo> getMethods(ClassFile cls) {
        return cls.getMethods();
    }

    @Override
    public String getMethodName(MethodInfo method) {
        return method.getName();
    }

    @Override
    public List<String> getParameterNames(MethodInfo method) {
        String descriptor = method.getDescriptor();
        descriptor = descriptor.substring(descriptor.indexOf("(") + 1, descriptor.lastIndexOf(")"));
        return this.splitDescriptorToTypeNames(descriptor);
    }

    @Override
    public List<String> getClassAnnotationNames(ClassFile aClass) {
        return this.getAnnotationNames((AnnotationsAttribute)aClass.getAttribute("RuntimeVisibleAnnotations"), includeInvisibleTag ? (AnnotationsAttribute)aClass.getAttribute("RuntimeInvisibleAnnotations") : null);
    }

    @Override
    public List<String> getFieldAnnotationNames(FieldInfo field) {
        return this.getAnnotationNames((AnnotationsAttribute)field.getAttribute("RuntimeVisibleAnnotations"), includeInvisibleTag ? (AnnotationsAttribute)field.getAttribute("RuntimeInvisibleAnnotations") : null);
    }

    @Override
    public List<String> getMethodAnnotationNames(MethodInfo method) {
        return this.getAnnotationNames((AnnotationsAttribute)method.getAttribute("RuntimeVisibleAnnotations"), includeInvisibleTag ? (AnnotationsAttribute)method.getAttribute("RuntimeInvisibleAnnotations") : null);
    }

    @Override
    public List<String> getParameterAnnotationNames(MethodInfo method, int parameterIndex) {
        ArrayList result = Lists.newArrayList();
        ArrayList parameterAnnotationsAttributes = Lists.newArrayList((Object[])new ParameterAnnotationsAttribute[]{(ParameterAnnotationsAttribute)method.getAttribute("RuntimeVisibleParameterAnnotations"), (ParameterAnnotationsAttribute)method.getAttribute("RuntimeInvisibleParameterAnnotations")});
        if (parameterAnnotationsAttributes != null) {
            for (ParameterAnnotationsAttribute parameterAnnotationsAttribute : parameterAnnotationsAttributes) {
                Annotation[][] annotations;
                if (parameterAnnotationsAttribute == null || parameterIndex >= (annotations = parameterAnnotationsAttribute.getAnnotations()).length) continue;
                Annotation[] annotation = annotations[parameterIndex];
                result.addAll(this.getAnnotationNames(annotation));
            }
        }
        return result;
    }

    @Override
    public String getReturnTypeName(MethodInfo method) {
        String descriptor = method.getDescriptor();
        descriptor = descriptor.substring(descriptor.lastIndexOf(")") + 1);
        return this.splitDescriptorToTypeNames(descriptor).get(0);
    }

    @Override
    public String getFieldName(FieldInfo field) {
        return field.getName();
    }

    @Override
    public ClassFile getOfCreateClassObject(Vfs.File file) {
        InputStream inputStream = null;
        try {
            inputStream = file.openInputStream();
            DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
            ClassFile classFile = new ClassFile(dis);
            return classFile;
        }
        catch (IOException e) {
            throw new ReflectionsException("could not create class file from " + file.getName(), e);
        }
        finally {
            Utils.close(inputStream);
        }
    }

    @Override
    public String getMethodModifier(MethodInfo method) {
        int accessFlags = method.getAccessFlags();
        return AccessFlag.isPrivate(accessFlags) ? "private" : (AccessFlag.isProtected(accessFlags) ? "protected" : (this.isPublic(accessFlags) ? "public" : ""));
    }

    @Override
    public String getMethodKey(ClassFile cls, MethodInfo method) {
        return this.getMethodName(method) + "(" + Joiner.on((String)", ").join(this.getParameterNames(method)) + ")";
    }

    @Override
    public String getMethodFullKey(ClassFile cls, MethodInfo method) {
        return this.getClassName(cls) + "." + this.getMethodKey(cls, method);
    }

    @Override
    public boolean isPublic(Object o) {
        Integer accessFlags = o instanceof ClassFile ? ((ClassFile)o).getAccessFlags() : (o instanceof FieldInfo ? ((FieldInfo)o).getAccessFlags() : (o instanceof MethodInfo ? Integer.valueOf(((MethodInfo)o).getAccessFlags()) : null).intValue());
        return accessFlags != null && AccessFlag.isPublic(accessFlags);
    }

    @Override
    public String getClassName(ClassFile cls) {
        return cls.getName();
    }

    @Override
    public String getSuperclassName(ClassFile cls) {
        return cls.getSuperclass();
    }

    @Override
    public List<String> getInterfacesNames(ClassFile cls) {
        return Arrays.asList(cls.getInterfaces());
    }

    @Override
    public boolean acceptsInput(String file) {
        return file.endsWith(".class");
    }

    private List<String> getAnnotationNames(AnnotationsAttribute ... annotationsAttributes) {
        ArrayList result = Lists.newArrayList();
        if (annotationsAttributes != null) {
            for (AnnotationsAttribute annotationsAttribute : annotationsAttributes) {
                if (annotationsAttribute == null) continue;
                for (Annotation annotation : annotationsAttribute.getAnnotations()) {
                    result.add(annotation.getTypeName());
                }
            }
        }
        return result;
    }

    private List<String> getAnnotationNames(Annotation[] annotations) {
        ArrayList result = Lists.newArrayList();
        for (Annotation annotation : annotations) {
            result.add(annotation.getTypeName());
        }
        return result;
    }

    private List<String> splitDescriptorToTypeNames(String descriptors) {
        ArrayList result = Lists.newArrayList();
        if (descriptors != null && descriptors.length() != 0) {
            ArrayList indices = Lists.newArrayList();
            Descriptor.Iterator iterator = new Descriptor.Iterator(descriptors);
            while (iterator.hasNext()) {
                indices.add(iterator.next());
            }
            indices.add(descriptors.length());
            for (int i = 0; i < indices.size() - 1; ++i) {
                String s1 = Descriptor.toString(descriptors.substring((Integer)indices.get(i), (Integer)indices.get(i + 1)));
                result.add(s1);
            }
        }
        return result;
    }
}

