/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.reflect.MemberUtils;

public class FieldUtils {
    public static Field getField(Class<?> clazz, String string) {
        Field field = FieldUtils.getField(clazz, string, false);
        MemberUtils.setAccessibleWorkaround(field);
        return field;
    }

    public static Field getField(Class<?> clazz, String string, boolean bl) {
        AnnotatedElement annotatedElement;
        Validate.isTrue(clazz != null, "The class must not be null", new Object[0]);
        Validate.isTrue(StringUtils.isNotBlank(string), "The field name must not be blank/empty", new Object[0]);
        for (annotatedElement = clazz; annotatedElement != null; annotatedElement = annotatedElement.getSuperclass()) {
            try {
                Field field = annotatedElement.getDeclaredField(string);
                if (!Modifier.isPublic(field.getModifiers())) {
                    if (!bl) continue;
                    field.setAccessible(false);
                }
                return field;
            } catch (NoSuchFieldException noSuchFieldException) {
                // empty catch block
            }
        }
        annotatedElement = null;
        for (Class clazz2 : ClassUtils.getAllInterfaces(clazz)) {
            try {
                Field field = clazz2.getField(string);
                Validate.isTrue(annotatedElement == null, "Reference to field %s is ambiguous relative to %s; a matching field exists on two or more implemented interfaces.", string, clazz);
                annotatedElement = field;
            } catch (NoSuchFieldException noSuchFieldException) {}
        }
        return annotatedElement;
    }

    public static Field getDeclaredField(Class<?> clazz, String string) {
        return FieldUtils.getDeclaredField(clazz, string, false);
    }

    public static Field getDeclaredField(Class<?> clazz, String string, boolean bl) {
        Validate.isTrue(clazz != null, "The class must not be null", new Object[0]);
        Validate.isTrue(StringUtils.isNotBlank(string), "The field name must not be blank/empty", new Object[0]);
        try {
            Field field = clazz.getDeclaredField(string);
            if (!MemberUtils.isAccessible(field)) {
                if (bl) {
                    field.setAccessible(false);
                } else {
                    return null;
                }
            }
            return field;
        } catch (NoSuchFieldException noSuchFieldException) {
            return null;
        }
    }

    public static Field[] getAllFields(Class<?> clazz) {
        List<Field> list = FieldUtils.getAllFieldsList(clazz);
        return list.toArray(new Field[list.size()]);
    }

    public static List<Field> getAllFieldsList(Class<?> clazz) {
        Validate.isTrue(clazz != null, "The class must not be null", new Object[0]);
        ArrayList<Field> arrayList = new ArrayList<Field>();
        for (Class<?> clazz2 = clazz; clazz2 != null; clazz2 = clazz2.getSuperclass()) {
            Field[] fieldArray;
            for (Field field : fieldArray = clazz2.getDeclaredFields()) {
                arrayList.add(field);
            }
        }
        return arrayList;
    }

    public static Field[] getFieldsWithAnnotation(Class<?> clazz, Class<? extends Annotation> clazz2) {
        List<Field> list = FieldUtils.getFieldsListWithAnnotation(clazz, clazz2);
        return list.toArray(new Field[list.size()]);
    }

    public static List<Field> getFieldsListWithAnnotation(Class<?> clazz, Class<? extends Annotation> clazz2) {
        Validate.isTrue(clazz2 != null, "The annotation class must not be null", new Object[0]);
        List<Field> list = FieldUtils.getAllFieldsList(clazz);
        ArrayList<Field> arrayList = new ArrayList<Field>();
        for (Field field : list) {
            if (field.getAnnotation(clazz2) == null) continue;
            arrayList.add(field);
        }
        return arrayList;
    }

    public static Object readStaticField(Field field) throws IllegalAccessException {
        return FieldUtils.readStaticField(field, false);
    }

    public static Object readStaticField(Field field, boolean bl) throws IllegalAccessException {
        Validate.isTrue(field != null, "The field must not be null", new Object[0]);
        Validate.isTrue(Modifier.isStatic(field.getModifiers()), "The field '%s' is not static", field.getName());
        return FieldUtils.readField(field, null, bl);
    }

    public static Object readStaticField(Class<?> clazz, String string) throws IllegalAccessException {
        return FieldUtils.readStaticField(clazz, string, false);
    }

    public static Object readStaticField(Class<?> clazz, String string, boolean bl) throws IllegalAccessException {
        Field field = FieldUtils.getField(clazz, string, bl);
        Validate.isTrue(field != null, "Cannot locate field '%s' on %s", string, clazz);
        return FieldUtils.readStaticField(field, false);
    }

    public static Object readDeclaredStaticField(Class<?> clazz, String string) throws IllegalAccessException {
        return FieldUtils.readDeclaredStaticField(clazz, string, false);
    }

    public static Object readDeclaredStaticField(Class<?> clazz, String string, boolean bl) throws IllegalAccessException {
        Field field = FieldUtils.getDeclaredField(clazz, string, bl);
        Validate.isTrue(field != null, "Cannot locate declared field %s.%s", clazz.getName(), string);
        return FieldUtils.readStaticField(field, false);
    }

    public static Object readField(Field field, Object object) throws IllegalAccessException {
        return FieldUtils.readField(field, object, false);
    }

    public static Object readField(Field field, Object object, boolean bl) throws IllegalAccessException {
        Validate.isTrue(field != null, "The field must not be null", new Object[0]);
        if (bl && !field.isAccessible()) {
            field.setAccessible(false);
        } else {
            MemberUtils.setAccessibleWorkaround(field);
        }
        return field.get(object);
    }

    public static Object readField(Object object, String string) throws IllegalAccessException {
        return FieldUtils.readField(object, string, false);
    }

    public static Object readField(Object object, String string, boolean bl) throws IllegalAccessException {
        Validate.isTrue(object != null, "target object must not be null", new Object[0]);
        Class<?> clazz = object.getClass();
        Field field = FieldUtils.getField(clazz, string, bl);
        Validate.isTrue(field != null, "Cannot locate field %s on %s", string, clazz);
        return FieldUtils.readField(field, object, false);
    }

    public static Object readDeclaredField(Object object, String string) throws IllegalAccessException {
        return FieldUtils.readDeclaredField(object, string, false);
    }

    public static Object readDeclaredField(Object object, String string, boolean bl) throws IllegalAccessException {
        Validate.isTrue(object != null, "target object must not be null", new Object[0]);
        Class<?> clazz = object.getClass();
        Field field = FieldUtils.getDeclaredField(clazz, string, bl);
        Validate.isTrue(field != null, "Cannot locate declared field %s.%s", clazz, string);
        return FieldUtils.readField(field, object, false);
    }

    public static void writeStaticField(Field field, Object object) throws IllegalAccessException {
        FieldUtils.writeStaticField(field, object, false);
    }

    public static void writeStaticField(Field field, Object object, boolean bl) throws IllegalAccessException {
        Validate.isTrue(field != null, "The field must not be null", new Object[0]);
        Validate.isTrue(Modifier.isStatic(field.getModifiers()), "The field %s.%s is not static", field.getDeclaringClass().getName(), field.getName());
        FieldUtils.writeField(field, null, object, bl);
    }

    public static void writeStaticField(Class<?> clazz, String string, Object object) throws IllegalAccessException {
        FieldUtils.writeStaticField(clazz, string, object, false);
    }

    public static void writeStaticField(Class<?> clazz, String string, Object object, boolean bl) throws IllegalAccessException {
        Field field = FieldUtils.getField(clazz, string, bl);
        Validate.isTrue(field != null, "Cannot locate field %s on %s", string, clazz);
        FieldUtils.writeStaticField(field, object, false);
    }

    public static void writeDeclaredStaticField(Class<?> clazz, String string, Object object) throws IllegalAccessException {
        FieldUtils.writeDeclaredStaticField(clazz, string, object, false);
    }

    public static void writeDeclaredStaticField(Class<?> clazz, String string, Object object, boolean bl) throws IllegalAccessException {
        Field field = FieldUtils.getDeclaredField(clazz, string, bl);
        Validate.isTrue(field != null, "Cannot locate declared field %s.%s", clazz.getName(), string);
        FieldUtils.writeField(field, null, object, false);
    }

    public static void writeField(Field field, Object object, Object object2) throws IllegalAccessException {
        FieldUtils.writeField(field, object, object2, false);
    }

    public static void writeField(Field field, Object object, Object object2, boolean bl) throws IllegalAccessException {
        Validate.isTrue(field != null, "The field must not be null", new Object[0]);
        if (bl && !field.isAccessible()) {
            field.setAccessible(false);
        } else {
            MemberUtils.setAccessibleWorkaround(field);
        }
        field.set(object, object2);
    }

    public static void removeFinalModifier(Field field) {
        FieldUtils.removeFinalModifier(field, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void removeFinalModifier(Field field, boolean bl) {
        block8: {
            Validate.isTrue(field != null, "The field must not be null", new Object[0]);
            try {
                boolean bl2;
                if (!Modifier.isFinal(field.getModifiers())) break block8;
                Field field2 = Field.class.getDeclaredField("modifiers");
                boolean bl3 = bl2 = bl && !field2.isAccessible();
                if (bl2) {
                    field2.setAccessible(false);
                }
                try {
                    field2.setInt(field, field.getModifiers() & 0xFFFFFFEF);
                } finally {
                    if (bl2) {
                        field2.setAccessible(true);
                    }
                }
            } catch (NoSuchFieldException noSuchFieldException) {
            } catch (IllegalAccessException illegalAccessException) {
                // empty catch block
            }
        }
    }

    public static void writeField(Object object, String string, Object object2) throws IllegalAccessException {
        FieldUtils.writeField(object, string, object2, false);
    }

    public static void writeField(Object object, String string, Object object2, boolean bl) throws IllegalAccessException {
        Validate.isTrue(object != null, "target object must not be null", new Object[0]);
        Class<?> clazz = object.getClass();
        Field field = FieldUtils.getField(clazz, string, bl);
        Validate.isTrue(field != null, "Cannot locate declared field %s.%s", clazz.getName(), string);
        FieldUtils.writeField(field, object, object2, false);
    }

    public static void writeDeclaredField(Object object, String string, Object object2) throws IllegalAccessException {
        FieldUtils.writeDeclaredField(object, string, object2, false);
    }

    public static void writeDeclaredField(Object object, String string, Object object2, boolean bl) throws IllegalAccessException {
        Validate.isTrue(object != null, "target object must not be null", new Object[0]);
        Class<?> clazz = object.getClass();
        Field field = FieldUtils.getDeclaredField(clazz, string, bl);
        Validate.isTrue(field != null, "Cannot locate declared field %s.%s", clazz.getName(), string);
        FieldUtils.writeField(field, object, object2, false);
    }
}

