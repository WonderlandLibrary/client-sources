/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Converter;
import com.google.common.base.Optional;
import com.google.common.base.Platform;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class Enums {
    @GwtIncompatible
    private static final Map<Class<? extends Enum<?>>, Map<String, WeakReference<? extends Enum<?>>>> enumConstantCache = new WeakHashMap();

    private Enums() {
    }

    @GwtIncompatible
    public static Field getField(Enum<?> enum_) {
        Class<?> clazz = enum_.getDeclaringClass();
        try {
            return clazz.getDeclaredField(enum_.name());
        } catch (NoSuchFieldException noSuchFieldException) {
            throw new AssertionError((Object)noSuchFieldException);
        }
    }

    public static <T extends Enum<T>> Optional<T> getIfPresent(Class<T> clazz, String string) {
        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(string);
        return Platform.getEnumIfPresent(clazz, string);
    }

    @GwtIncompatible
    private static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> populateCache(Class<T> clazz) {
        HashMap hashMap = new HashMap();
        for (Enum enum_ : EnumSet.allOf(clazz)) {
            hashMap.put(enum_.name(), new WeakReference<Enum>(enum_));
        }
        enumConstantCache.put(clazz, hashMap);
        return hashMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @GwtIncompatible
    static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> getEnumConstants(Class<T> clazz) {
        Map<Class<? extends Enum<?>>, Map<String, WeakReference<? extends Enum<?>>>> map = enumConstantCache;
        synchronized (map) {
            Map<String, WeakReference<Enum>> map2 = enumConstantCache.get(clazz);
            if (map2 == null) {
                map2 = Enums.populateCache(clazz);
            }
            return map2;
        }
    }

    public static <T extends Enum<T>> Converter<String, T> stringConverter(Class<T> clazz) {
        return new StringConverter<T>(clazz);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class StringConverter<T extends Enum<T>>
    extends Converter<String, T>
    implements Serializable {
        private final Class<T> enumClass;
        private static final long serialVersionUID = 0L;

        StringConverter(Class<T> clazz) {
            this.enumClass = Preconditions.checkNotNull(clazz);
        }

        @Override
        protected T doForward(String string) {
            return Enum.valueOf(this.enumClass, string);
        }

        @Override
        protected String doBackward(T t) {
            return ((Enum)t).name();
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof StringConverter) {
                StringConverter stringConverter = (StringConverter)object;
                return this.enumClass.equals(stringConverter.enumClass);
            }
            return true;
        }

        public int hashCode() {
            return this.enumClass.hashCode();
        }

        public String toString() {
            return "Enums.stringConverter(" + this.enumClass.getName() + ".class)";
        }

        @Override
        protected Object doBackward(Object object) {
            return this.doBackward((T)((Enum)object));
        }

        @Override
        protected Object doForward(Object object) {
            return this.doForward((String)object);
        }
    }
}

