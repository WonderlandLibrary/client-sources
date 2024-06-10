 package me.vene.skilled.utilities;

 import java.lang.reflect.Field; 

public class ReflectionUtil
{
    public static Object getFieldValue(final String field_, final Object object, final Class<?> clazz) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(field_);
            final boolean access = field.isAccessible();
            if (!access) {
                field.setAccessible(true);
            }
            final Object obj = field.get(object);
            if (!access) {
                field.setAccessible(false);
            }
            return obj;
        }
        catch (Exception ex) {
            return null;
        }
    }
}
