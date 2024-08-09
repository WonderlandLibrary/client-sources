/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorClass;
import net.optifine.reflect.ReflectorField;

public class ReflectorRaw {
    private ReflectorRaw() {
    }

    public static Field getField(Class clazz, Class clazz2) {
        try {
            Field[] fieldArray = clazz.getDeclaredFields();
            for (int i = 0; i < fieldArray.length; ++i) {
                Field field = fieldArray[i];
                if (field.getType() != clazz2) continue;
                field.setAccessible(false);
                return field;
            }
            return null;
        } catch (Exception exception) {
            return null;
        }
    }

    public static Field[] getFields(Class clazz, Class clazz2) {
        try {
            Field[] fieldArray = clazz.getDeclaredFields();
            return ReflectorRaw.getFields(fieldArray, clazz2);
        } catch (Exception exception) {
            return null;
        }
    }

    public static Field[] getFields(Field[] fieldArray, Class clazz) {
        try {
            ArrayList<Field> arrayList = new ArrayList<Field>();
            for (int i = 0; i < fieldArray.length; ++i) {
                Field field = fieldArray[i];
                if (field.getType() != clazz) continue;
                field.setAccessible(false);
                arrayList.add(field);
            }
            return arrayList.toArray(new Field[arrayList.size()]);
        } catch (Exception exception) {
            return null;
        }
    }

    public static Field[] getFieldsAfter(Class clazz, Field field, Class clazz2) {
        try {
            Field[] fieldArray = clazz.getDeclaredFields();
            List<Field> list = Arrays.asList(fieldArray);
            int n = list.indexOf(field);
            if (n < 0) {
                return new Field[0];
            }
            List<Field> list2 = list.subList(n + 1, list.size());
            Field[] fieldArray2 = list2.toArray(new Field[list2.size()]);
            return ReflectorRaw.getFields(fieldArray2, clazz2);
        } catch (Exception exception) {
            return null;
        }
    }

    public static Field[] getFields(Object object, Field[] fieldArray, Class clazz, Object object2) {
        try {
            ArrayList<Field> arrayList = new ArrayList<Field>();
            for (int i = 0; i < fieldArray.length; ++i) {
                Field field = fieldArray[i];
                if (field.getType() != clazz) continue;
                boolean bl = Modifier.isStatic(field.getModifiers());
                if (object == null && !bl || object != null && bl) continue;
                field.setAccessible(false);
                Object object3 = field.get(object);
                if (object3 == object2) {
                    arrayList.add(field);
                    continue;
                }
                if (object3 == null || object2 == null || !object3.equals(object2)) continue;
                arrayList.add(field);
            }
            return arrayList.toArray(new Field[arrayList.size()]);
        } catch (Exception exception) {
            return null;
        }
    }

    public static Field getField(Class clazz, Class clazz2, int n) {
        Field[] fieldArray = ReflectorRaw.getFields(clazz, clazz2);
        return n >= 0 && n < fieldArray.length ? fieldArray[n] : null;
    }

    public static Field getFieldAfter(Class clazz, Field field, Class clazz2, int n) {
        Field[] fieldArray = ReflectorRaw.getFieldsAfter(clazz, field, clazz2);
        return n >= 0 && n < fieldArray.length ? fieldArray[n] : null;
    }

    public static Object getFieldValue(Object object, Class clazz, Class clazz2) {
        ReflectorField reflectorField = ReflectorRaw.getReflectorField(clazz, clazz2);
        if (reflectorField == null) {
            return null;
        }
        return !reflectorField.exists() ? null : Reflector.getFieldValue(object, reflectorField);
    }

    public static Object getFieldValue(Object object, Class clazz, Class clazz2, int n) {
        ReflectorField reflectorField = ReflectorRaw.getReflectorField(clazz, clazz2, n);
        if (reflectorField == null) {
            return null;
        }
        return !reflectorField.exists() ? null : Reflector.getFieldValue(object, reflectorField);
    }

    public static boolean setFieldValue(Object object, Class clazz, Class clazz2, Object object2) {
        ReflectorField reflectorField = ReflectorRaw.getReflectorField(clazz, clazz2);
        if (reflectorField == null) {
            return true;
        }
        return !reflectorField.exists() ? false : Reflector.setFieldValue(object, reflectorField, object2);
    }

    public static boolean setFieldValue(Object object, Class clazz, Class clazz2, int n, Object object2) {
        ReflectorField reflectorField = ReflectorRaw.getReflectorField(clazz, clazz2, n);
        if (reflectorField == null) {
            return true;
        }
        return !reflectorField.exists() ? false : Reflector.setFieldValue(object, reflectorField, object2);
    }

    public static ReflectorField getReflectorField(Class clazz, Class clazz2) {
        Field field = ReflectorRaw.getField(clazz, clazz2);
        if (field == null) {
            return null;
        }
        ReflectorClass reflectorClass = new ReflectorClass(clazz);
        return new ReflectorField(reflectorClass, field.getName());
    }

    public static ReflectorField getReflectorField(Class clazz, Class clazz2, int n) {
        Field field = ReflectorRaw.getField(clazz, clazz2, n);
        if (field == null) {
            return null;
        }
        ReflectorClass reflectorClass = new ReflectorClass(clazz);
        return new ReflectorField(reflectorClass, field.getName());
    }
}

