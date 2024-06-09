package org.spongepowered.asm.util;

import java.lang.annotation.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.*;

public final class Annotations
{
    private Annotations() {
        super();
    }
    
    public static void setVisible(final FieldNode fieldNode, final Class<? extends Annotation> clazz, final Object... array) {
        fieldNode.visibleAnnotations = add(fieldNode.visibleAnnotations, createNode(Type.getDescriptor(clazz), array));
    }
    
    public static void setInvisible(final FieldNode fieldNode, final Class<? extends Annotation> clazz, final Object... array) {
        fieldNode.invisibleAnnotations = add(fieldNode.invisibleAnnotations, createNode(Type.getDescriptor(clazz), array));
    }
    
    public static void setVisible(final MethodNode methodNode, final Class<? extends Annotation> clazz, final Object... array) {
        methodNode.visibleAnnotations = add(methodNode.visibleAnnotations, createNode(Type.getDescriptor(clazz), array));
    }
    
    public static void setInvisible(final MethodNode methodNode, final Class<? extends Annotation> clazz, final Object... array) {
        methodNode.invisibleAnnotations = add(methodNode.invisibleAnnotations, createNode(Type.getDescriptor(clazz), array));
    }
    
    private static AnnotationNode createNode(final String s, final Object... array) {
        final AnnotationNode annotationNode = new AnnotationNode(s);
        for (int i = 0; i < array.length - 1; i += 2) {
            if (!(array[i] instanceof String)) {
                throw new IllegalArgumentException("Annotation keys must be strings, found " + array[i].getClass().getSimpleName() + " with " + array[i].toString() + " at index " + i + " creating " + s);
            }
            annotationNode.visit((String)array[i], array[i + 1]);
        }
        return annotationNode;
    }
    
    private static List<AnnotationNode> add(List<AnnotationNode> list, final AnnotationNode annotationNode) {
        if (list == null) {
            list = new ArrayList<AnnotationNode>(1);
        }
        else {
            list.remove(get(list, annotationNode.desc));
        }
        list.add(annotationNode);
        return list;
    }
    
    public static AnnotationNode getVisible(final FieldNode fieldNode, final Class<? extends Annotation> clazz) {
        return get(fieldNode.visibleAnnotations, Type.getDescriptor(clazz));
    }
    
    public static AnnotationNode getInvisible(final FieldNode fieldNode, final Class<? extends Annotation> clazz) {
        return get(fieldNode.invisibleAnnotations, Type.getDescriptor(clazz));
    }
    
    public static AnnotationNode getVisible(final MethodNode methodNode, final Class<? extends Annotation> clazz) {
        return get(methodNode.visibleAnnotations, Type.getDescriptor(clazz));
    }
    
    public static AnnotationNode getInvisible(final MethodNode methodNode, final Class<? extends Annotation> clazz) {
        return get(methodNode.invisibleAnnotations, Type.getDescriptor(clazz));
    }
    
    public static AnnotationNode getSingleVisible(final MethodNode methodNode, final Class<? extends Annotation>... array) {
        return getSingle(methodNode.visibleAnnotations, array);
    }
    
    public static AnnotationNode getSingleInvisible(final MethodNode methodNode, final Class<? extends Annotation>... array) {
        return getSingle(methodNode.invisibleAnnotations, array);
    }
    
    public static AnnotationNode getVisible(final ClassNode classNode, final Class<? extends Annotation> clazz) {
        return get(classNode.visibleAnnotations, Type.getDescriptor(clazz));
    }
    
    public static AnnotationNode getInvisible(final ClassNode classNode, final Class<? extends Annotation> clazz) {
        return get(classNode.invisibleAnnotations, Type.getDescriptor(clazz));
    }
    
    public static AnnotationNode getVisibleParameter(final MethodNode methodNode, final Class<? extends Annotation> clazz, final int n) {
        return getParameter(methodNode.visibleParameterAnnotations, Type.getDescriptor(clazz), n);
    }
    
    public static AnnotationNode getInvisibleParameter(final MethodNode methodNode, final Class<? extends Annotation> clazz, final int n) {
        return getParameter(methodNode.invisibleParameterAnnotations, Type.getDescriptor(clazz), n);
    }
    
    public static AnnotationNode getParameter(final List<AnnotationNode>[] array, final String s, final int n) {
        if (array == null || n < 0 || n >= array.length) {
            return null;
        }
        return get(array[n], s);
    }
    
    public static AnnotationNode get(final List<AnnotationNode> list, final String s) {
        if (list == null) {
            return null;
        }
        for (final AnnotationNode annotationNode : list) {
            if (s.equals(annotationNode.desc)) {
                return annotationNode;
            }
        }
        return null;
    }
    
    private static AnnotationNode getSingle(final List<AnnotationNode> list, final Class<? extends Annotation>[] array) {
        final ArrayList<Object> fromList = new ArrayList<Object>();
        for (int length = array.length, i = 0; i < length; ++i) {
            final AnnotationNode value = get(list, Type.getDescriptor(array[i]));
            if (value != null) {
                fromList.add(value);
            }
        }
        final int size = fromList.size();
        if (size > 1) {
            throw new IllegalArgumentException("Conflicting annotations found: " + Lists.transform((List<Object>)fromList, (Function<? super Object, ?>)new Function<AnnotationNode, String>() {
                Annotations$1() {
                    super();
                }
                
                @Override
                public String apply(final AnnotationNode annotationNode) {
                    return annotationNode.desc;
                }
                
                @Override
                public Object apply(final Object o) {
                    return this.apply((AnnotationNode)o);
                }
            }));
        }
        return (size == 0) ? null : fromList.get(0);
    }
    
    public static <T> T getValue(final AnnotationNode annotationNode) {
        return getValue(annotationNode, "value");
    }
    
    public static <T> T getValue(final AnnotationNode annotationNode, final String s, final T t) {
        final T value = getValue(annotationNode, s);
        return (value != null) ? value : t;
    }
    
    public static <T> T getValue(final AnnotationNode annotationNode, final String s, final Class<?> reference) {
        Preconditions.checkNotNull(reference, "annotationClass cannot be null");
        Object o = getValue(annotationNode, s);
        if (o == null) {
            try {
                o = reference.getDeclaredMethod(s, (Class<?>[])new Class[0]).getDefaultValue();
            }
            catch (NoSuchMethodException ex) {}
        }
        return (T)o;
    }
    
    public static <T> T getValue(final AnnotationNode annotationNode, final String s) {
        int n = 0;
        if (annotationNode == null || annotationNode.values == null) {
            return null;
        }
        for (final T next : annotationNode.values) {
            if (n != 0) {
                return next;
            }
            if (!next.equals(s)) {
                continue;
            }
            n = 1;
        }
        return null;
    }
    
    public static <T extends Enum<T>> T getValue(final AnnotationNode annotationNode, final String s, final Class<T> clazz, final T t) {
        final String[] array = getValue(annotationNode, s);
        if (array == null) {
            return t;
        }
        return toEnumValue(clazz, array);
    }
    
    public static <T> List<T> getValue(final AnnotationNode annotationNode, final String s, final boolean b) {
        final List<T> value = (List<T>)getValue(annotationNode, s);
        if (value instanceof List) {
            return value;
        }
        if (value != null) {
            final ArrayList<List<T>> list = (ArrayList<List<T>>)new ArrayList<T>();
            list.add((T)value);
            return (List<T>)list;
        }
        return Collections.emptyList();
    }
    
    public static <T extends Enum<T>> List<T> getValue(final AnnotationNode annotationNode, final String s, final boolean b, final Class<T> clazz) {
        final String[] value = getValue(annotationNode, s);
        if (value instanceof List) {
            final ListIterator<String[]> listIterator = ((List<String[]>)(Object)value).listIterator();
            while (listIterator.hasNext()) {
                listIterator.set((String[])(Object)toEnumValue(clazz, listIterator.next()));
            }
            return (List<T>)(Object)value;
        }
        if (value instanceof String[]) {
            final ArrayList<T> list = new ArrayList<T>();
            list.add(toEnumValue(clazz, value));
            return list;
        }
        return Collections.emptyList();
    }
    
    private static <T extends Enum<T>> T toEnumValue(final Class<T> clazz, final String[] array) {
        if (!clazz.getName().equals(Type.getType(array[0]).getClassName())) {
            throw new IllegalArgumentException("The supplied enum class does not match the stored enum value");
        }
        return Enum.valueOf(clazz, array[1]);
    }
}
