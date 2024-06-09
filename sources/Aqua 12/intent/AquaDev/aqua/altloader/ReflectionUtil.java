// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.altloader;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

public class ReflectionUtil
{
    public static Object getFieldByClass(final Object o, final Class<?> searchingClazz) {
        return getFieldByClass(o.getClass(), o, searchingClazz);
    }
    
    public static Object getFieldByClass(final Class<?> parentClass, final Object o, final Class<?> searchingClazz) {
        AccessibleObject field = null;
        for (final Field f : parentClass.getDeclaredFields()) {
            if (f.getType().equals(searchingClazz)) {
                field = f;
                break;
            }
        }
        if (field == null) {
            return null;
        }
        try {
            final boolean isAccessible = field.isAccessible();
            ((Field)field).setAccessible(true);
            final Object toReturn = ((Field)field).get(o);
            ((Field)field).setAccessible(isAccessible);
            return toReturn;
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void setFieldByClass(final Object parentObject, final Object newObject) {
        AccessibleObject field = null;
        for (final Field f : parentObject.getClass().getDeclaredFields()) {
            if (f.getType().isInstance(newObject)) {
                field = f;
                break;
            }
        }
        if (field == null) {
            return;
        }
        try {
            final boolean accessible = field.isAccessible();
            ((Field)field).setAccessible(true);
            ((Field)field).set(parentObject, newObject);
            ((Field)field).setAccessible(accessible);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    public static void setStaticField(final Class clazz, final String fieldName, final Object value) {
        try {
            final Field staticField = clazz.getDeclaredField(fieldName);
            staticField.setAccessible(true);
            final Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(staticField, staticField.getModifiers() & 0xFFFFFFEF);
            staticField.set(null, value);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
    }
}
