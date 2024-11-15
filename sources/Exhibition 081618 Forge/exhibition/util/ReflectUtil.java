package exhibition.util;

import net.minecraftforge.fml.relauncher.*;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.*;

public class ReflectUtil
{
	
    public static MethodHandles.Lookup lookup;
    
    public void setField(final String string, final String obfName, final Object instance, final Object newValue, final boolean isFinal) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        final Field strField = ReflectionHelper.findField((Class)instance.getClass(), new String[] { string, obfName });
        strField.setAccessible(true);
        if (isFinal) {
            final Field modField = Field.class.getDeclaredField("modifiers");
            modField.setAccessible(true);
            modField.setInt(strField, strField.getModifiers() & 0xFFFFFFEF);
        }
        strField.set(instance, newValue);
    }
    
    public void setField(final String string, final String obfName, final Class targetClass, final Object instance, final Object newValue, final boolean isFinal) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        final Field strField = ReflectionHelper.findField(targetClass, new String[] { string, obfName });
        strField.setAccessible(true);
        if (isFinal) {
            final Field modField = Field.class.getDeclaredField("modifiers");
            modField.setAccessible(true);
            modField.setInt(strField, strField.getModifiers() & 0xFFFFFFEF);
        }
        strField.set(instance, newValue);
    }
    
    public Object getField(final String field, final String obfName, final Object instance) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Field fField = ReflectionHelper.findField((Class)instance.getClass(), new String[] { field, obfName });
        fField.setAccessible(true);
        return fField.get(instance);
    }
    
    public Object getField(final String field, final String obfName, final Class clazz) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Field fField = ReflectionHelper.findField(clazz, new String[] { field, obfName });
        fField.setAccessible(true);
        return fField.get(clazz);
    }
    
    public static Field getField(Class class_, String... arrstring) {
        for (Field field : class_.getDeclaredFields()) {
            field.setAccessible(true);
            for (String string : arrstring) {
                if (!field.getName().equals(string)) {
                    continue;
                }
                return field;
            }
        }
        return null;
    }
    
    public static MethodHandles.Lookup lookup() {
        return lookup;
    }
    
    public Object invoke(final Object target, final String methodName, final String obfName, final Class[] methodArgs, final Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Class clazz = target.getClass();
        final Method method = ReflectionHelper.findMethod(clazz, target, new String[] { methodName, obfName }, methodArgs);
        method.setAccessible(true);
        return method.invoke(target, args);
    }
}
