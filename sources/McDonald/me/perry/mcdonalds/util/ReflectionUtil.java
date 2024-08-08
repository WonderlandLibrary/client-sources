// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.util;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.AccessibleObject;
import java.util.Objects;

public class ReflectionUtil
{
    public static <F, T extends F> void copyOf(final F from, final T to, final boolean ignoreFinal) throws NoSuchFieldException, IllegalAccessException {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        final Class<?> clazz = from.getClass();
        for (final Field field : clazz.getDeclaredFields()) {
            makePublic(field);
            if (!isStatic(field)) {
                if (!ignoreFinal || !isFinal(field)) {
                    makeMutable(field);
                    field.set(to, field.get(from));
                }
            }
        }
    }
    
    public static <F, T extends F> void copyOf(final F from, final T to) throws NoSuchFieldException, IllegalAccessException {
        copyOf(from, to, false);
    }
    
    public static boolean isStatic(final Member instance) {
        return (instance.getModifiers() & 0x8) != 0x0;
    }
    
    public static boolean isFinal(final Member instance) {
        return (instance.getModifiers() & 0x10) != 0x0;
    }
    
    public static void makeAccessible(final AccessibleObject instance, final boolean accessible) {
        Objects.requireNonNull(instance);
        instance.setAccessible(accessible);
    }
    
    public static void makePublic(final AccessibleObject instance) {
        makeAccessible(instance, true);
    }
    
    public static void makePrivate(final AccessibleObject instance) {
        makeAccessible(instance, false);
    }
    
    public static void makeMutable(final Member instance) throws NoSuchFieldException, IllegalAccessException {
        Objects.requireNonNull(instance);
        final Field modifiers = Field.class.getDeclaredField("modifiers");
        makePublic(modifiers);
        modifiers.setInt(instance, instance.getModifiers() & 0xFFFFFFEF);
    }
    
    public static void makeImmutable(final Member instance) throws NoSuchFieldException, IllegalAccessException {
        Objects.requireNonNull(instance);
        final Field modifiers = Field.class.getDeclaredField("modifiers");
        makePublic(modifiers);
        modifiers.setInt(instance, instance.getModifiers() & 0x10);
    }
}
