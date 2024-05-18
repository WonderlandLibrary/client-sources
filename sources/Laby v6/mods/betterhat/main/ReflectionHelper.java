package mods.betterhat.main;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionHelper
{
    public static Field findField(Class<?> clazz, String... fieldNames)
    {
        Exception exception = null;

        for (String s : fieldNames)
        {
            try
            {
                Field field = clazz.getDeclaredField(s);
                field.setAccessible(true);
                return field;
            }
            catch (Exception exception1)
            {
                exception = exception1;
            }
        }

        throw new ReflectionHelper.UnableToFindFieldException(fieldNames, exception);
    }

    public static <T, E> T getPrivateValue(Class <? super E > classToAccess, E instance, int fieldIndex)
    {
        try
        {
            Field field = classToAccess.getDeclaredFields()[fieldIndex];
            field.setAccessible(true);
            return (T)field.get(instance);
        }
        catch (Exception exception)
        {
            throw new ReflectionHelper.UnableToAccessFieldException(new String[0], exception);
        }
    }

    public static <T, E> T getPrivateValue(Class <? super E > classToAccess, E instance, String... fieldNames)
    {
        try
        {
            return (T)findField(classToAccess, fieldNames).get(instance);
        }
        catch (Exception exception)
        {
            throw new ReflectionHelper.UnableToAccessFieldException(fieldNames, exception);
        }
    }

    public static <T, E> void setPrivateValue(Class <? super T > classToAccess, T instance, E value, int fieldIndex)
    {
        try
        {
            Field field = classToAccess.getDeclaredFields()[fieldIndex];
            field.setAccessible(true);
            field.set(instance, value);
        }
        catch (Exception exception)
        {
            throw new ReflectionHelper.UnableToAccessFieldException(new String[0], exception);
        }
    }

    public static <T, E> void setPrivateValue(Class <? super T > classToAccess, T instance, E value, String... fieldNames)
    {
        try
        {
            findField(classToAccess, fieldNames).set(instance, value);
        }
        catch (Exception exception)
        {
            throw new ReflectionHelper.UnableToAccessFieldException(fieldNames, exception);
        }
    }

    public static Class <? super Object > getClass(ClassLoader loader, String... classNames)
    {
        Exception exception = null;

        for (String s : classNames)
        {
            try
            {
                return (Class<? super Object>) Class.forName(s, false, loader);
            }
            catch (Exception exception1)
            {
                exception = exception1;
            }
        }

        throw new ReflectionHelper.UnableToFindClassException(classNames, exception);
    }

    public static <E> Method findMethod(Class <? super E > clazz, E instance, String[] methodNames, Class<?>... methodTypes)
    {
        Exception exception = null;

        for (String s : methodNames)
        {
            try
            {
                Method method = clazz.getDeclaredMethod(s, methodTypes);
                method.setAccessible(true);
                return method;
            }
            catch (Exception exception1)
            {
                exception = exception1;
            }
        }

        throw new ReflectionHelper.UnableToFindMethodException(methodNames, exception);
    }

    public static class UnableToAccessFieldException extends RuntimeException
    {
        private static final long serialVersionUID = 1L;

        public UnableToAccessFieldException(String[] fieldNames, Exception e)
        {
            super((Throwable)e);
        }
    }

    public static class UnableToFindClassException extends RuntimeException
    {
        private static final long serialVersionUID = 1L;

        public UnableToFindClassException(String[] classNames, Exception err)
        {
            super((Throwable)err);
        }
    }

    public static class UnableToFindFieldException extends RuntimeException
    {
        private static final long serialVersionUID = 1L;

        public UnableToFindFieldException(String[] fieldNameList, Exception e)
        {
            super((Throwable)e);
        }
    }

    public static class UnableToFindMethodException extends RuntimeException
    {
        private static final long serialVersionUID = 1L;

        public UnableToFindMethodException(String[] methodNames, Exception failed)
        {
            super((Throwable)failed);
        }
    }
}
