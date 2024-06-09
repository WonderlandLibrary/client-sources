package com.client.glowclient;

import java.util.*;
import java.lang.reflect.*;
import com.google.common.annotations.*;
import com.google.common.collect.*;
import net.minecraft.inventory.*;
import net.minecraft.client.gui.inventory.*;

public class Hd
{
    @VisibleForTesting
    public static final Map<Class<?>, Map<Class<?>, Field>> b;
    
    public static <T, K> void D(final K k, final Class<K> clazz, final Class<T> clazz2, final T t) {
        try {
            D(clazz, (Class<?>)clazz2).set(k, t);
        }
        catch (Exception ex) {
            throw new RuntimeException(new StringBuilder().insert(0, "WorldDownloader: Couldn't set Field of type \"").append(clazz2).append("\" from object \"").append(k).append("\" to ").append(t).append("!").toString(), ex);
        }
    }
    
    public static <T, K> T D(final K k, final Class<K> clazz, final Class<T> clazz2) {
        try {
            return clazz2.cast(D(clazz, (Class<?>)clazz2).get(k));
        }
        catch (Exception ex) {
            throw new RuntimeException(new StringBuilder().insert(0, "WorldDownloader: Couldn't get Field of type \"").append(clazz2).append("\" from object \"").append(k).append("\" !").toString(), ex);
        }
    }
    
    @Deprecated
    public static <T> T M(Object o, Class<?> clazz, final Class<T> clazz2) {
        clazz = clazz;
        if (o instanceof Class) {
            o = null;
        }
        return (T)D(o, clazz, (Class<Object>)clazz2);
    }
    
    static {
        b = Maps.newHashMap();
    }
    
    public static <T> T D(Object o, final Class<T> clazz) {
        Label_0025: {
            if (o instanceof Class) {
                final Class clazz2 = (Class)o;
                o = null;
                final Class<?> clazz3 = (Class<?>)clazz2;
                break Label_0025;
            }
            final Class<?> class1 = o.getClass();
            try {
                final Class<?> clazz3 = class1;
                return clazz.cast(D(clazz3, (Class<?>)clazz).get(o));
            }
            catch (Exception ex) {
                throw new RuntimeException(new StringBuilder().insert(0, "WorldDownloader: Couldn't get Field of type \"").append(clazz).append("\" from object \"").append(o).append("\" !").toString(), ex);
            }
        }
    }
    
    public static Field D(final Class<?> clazz, final Class<?> clazz2) {
        final Map<Class<?>, Field> map;
        if (Hd.b.containsKey(clazz) && (map = Hd.b.get(clazz)).containsKey(clazz2)) {
            return map.get(clazz2);
        }
        final Field[] declaredFields = clazz.getDeclaredFields();
        final int length = declaredFields.length;
        int n;
        int i = n = 0;
        while (i < length) {
            final Field field;
            if ((field = declaredFields[n]).getType().equals(clazz2)) {
                try {
                    field.setAccessible(true);
                    if (!Hd.b.containsKey(clazz)) {
                        Hd.b.put(clazz, (Map<Class<?>, Field>)Maps.newHashMap());
                    }
                    Hd.b.get(clazz).put(clazz2, field);
                    return field;
                }
                catch (Exception ex) {
                    throw new RuntimeException(new StringBuilder().insert(0, "WorldDownloader: Couldn't get private Field of type \"").append(clazz2).append("\" from class \"").append(clazz).append("\" !").toString(), ex);
                }
            }
            i = ++n;
        }
        throw new RuntimeException(new StringBuilder().insert(0, "WorldDownloader: Couldn't find any Field of type \"").append(clazz2).append("\" from class \"").append(clazz).append("\" !").toString());
    }
    
    @Deprecated
    public static <T> T M(final Object o, final Class<T> clazz) {
        return (T)D(o, (Class<Object>)clazz);
    }
    
    @Deprecated
    public static <T> void D(final Object o, final Class<T> clazz, final Object o2) {
        M(o, (Class<Object>)clazz, o2);
    }
    
    public static boolean M(final Class<? extends Container> clazz) {
        try {
            return GuiContainerCreative.class.equals(clazz.getEnclosingClass());
        }
        catch (Exception ex) {
            throw new RuntimeException(new StringBuilder().insert(0, "WorldDownloader: Couldn't check if \"").append(clazz).append("\" was the creative inventory!").toString(), ex);
        }
    }
    
    public Hd() {
        super();
    }
    
    public static <T> void M(Object o, final Class<T> clazz, final T t) {
        Label_0025: {
            if (o instanceof Class) {
                final Class clazz2 = (Class)o;
                o = null;
                final Class<?> clazz3 = (Class<?>)clazz2;
                break Label_0025;
            }
            final Class<?> class1 = o.getClass();
            try {
                final Class<?> clazz3 = class1;
                D(clazz3, (Class<?>)clazz).set(o, t);
            }
            catch (Exception ex) {
                throw new RuntimeException(new StringBuilder().insert(0, "WorldDownloader: Couldn't set Field of type \"").append(clazz).append("\" from object \"").append(o).append("\" to ").append(t).append("!").toString(), ex);
            }
        }
    }
    
    @Deprecated
    public static Field M(final Class<?> clazz, final Class<?> clazz2) {
        return D(clazz, clazz2);
    }
    
    @Deprecated
    public static void M(Object o, Class<?> clazz, Class<?> clazz2, final Object o2) {
        clazz = clazz;
        clazz2 = clazz2;
        if (o instanceof Class) {
            o = null;
        }
        D(o, clazz, clazz2, o2);
    }
}
