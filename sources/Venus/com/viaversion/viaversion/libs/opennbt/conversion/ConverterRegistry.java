/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.conversion;

import com.viaversion.viaversion.libs.opennbt.conversion.ConversionException;
import com.viaversion.viaversion.libs.opennbt.conversion.ConverterRegisterException;
import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.ByteArrayTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.ByteTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.CompoundTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.DoubleTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.FloatTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.IntArrayTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.IntTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.ListTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.LongArrayTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.LongTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.ShortTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.StringTagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConverterRegistry {
    private static final Map<Class<? extends Tag>, TagConverter<? extends Tag, ?>> tagToConverter = new HashMap();
    private static final Map<Class<?>, TagConverter<? extends Tag, ?>> typeToConverter = new HashMap();

    public static <T extends Tag, V> void register(Class<T> clazz, Class<V> clazz2, TagConverter<T, V> tagConverter) throws ConverterRegisterException {
        if (tagToConverter.containsKey(clazz)) {
            throw new ConverterRegisterException("Type conversion to tag " + clazz.getName() + " is already registered.");
        }
        if (typeToConverter.containsKey(clazz2)) {
            throw new ConverterRegisterException("Tag conversion to type " + clazz2.getName() + " is already registered.");
        }
        tagToConverter.put(clazz, tagConverter);
        typeToConverter.put(clazz2, tagConverter);
    }

    public static <T extends Tag, V> void unregister(Class<T> clazz, Class<V> clazz2) {
        tagToConverter.remove(clazz);
        typeToConverter.remove(clazz2);
    }

    public static <T extends Tag, V> V convertToValue(T t) throws ConversionException {
        if (t == null || t.getValue() == null) {
            return null;
        }
        if (!tagToConverter.containsKey(t.getClass())) {
            throw new ConversionException("Tag type " + t.getClass().getName() + " has no converter.");
        }
        TagConverter<Tag, ?> tagConverter = tagToConverter.get(t.getClass());
        return (V)tagConverter.convert(t);
    }

    public static <V, T extends Tag> T convertToTag(V v) throws ConversionException {
        if (v == null) {
            return null;
        }
        TagConverter<Tag, ?> tagConverter = typeToConverter.get(v.getClass());
        if (tagConverter == null) {
            for (Class<?> clazz : ConverterRegistry.getAllClasses(v.getClass())) {
                if (!typeToConverter.containsKey(clazz)) continue;
                try {
                    tagConverter = typeToConverter.get(clazz);
                    break;
                } catch (ClassCastException classCastException) {
                }
            }
        }
        if (tagConverter == null) {
            throw new ConversionException("Value type " + v.getClass().getName() + " has no converter.");
        }
        return (T)tagConverter.convert(v);
    }

    private static Set<Class<?>> getAllClasses(Class<?> clazz) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Class<?> clazz2 = clazz; clazz2 != null; clazz2 = clazz2.getSuperclass()) {
            linkedHashSet.add(clazz2);
            linkedHashSet.addAll(ConverterRegistry.getAllSuperInterfaces(clazz2));
        }
        if (linkedHashSet.contains(Serializable.class)) {
            linkedHashSet.remove(Serializable.class);
            linkedHashSet.add(Serializable.class);
        }
        return linkedHashSet;
    }

    private static Set<Class<?>> getAllSuperInterfaces(Class<?> clazz) {
        HashSet hashSet = new HashSet();
        for (Class<?> clazz2 : clazz.getInterfaces()) {
            hashSet.add(clazz2);
            hashSet.addAll(ConverterRegistry.getAllSuperInterfaces(clazz2));
        }
        return hashSet;
    }

    static {
        ConverterRegistry.register(ByteTag.class, Byte.class, new ByteTagConverter());
        ConverterRegistry.register(ShortTag.class, Short.class, new ShortTagConverter());
        ConverterRegistry.register(IntTag.class, Integer.class, new IntTagConverter());
        ConverterRegistry.register(LongTag.class, Long.class, new LongTagConverter());
        ConverterRegistry.register(FloatTag.class, Float.class, new FloatTagConverter());
        ConverterRegistry.register(DoubleTag.class, Double.class, new DoubleTagConverter());
        ConverterRegistry.register(ByteArrayTag.class, byte[].class, new ByteArrayTagConverter());
        ConverterRegistry.register(StringTag.class, String.class, new StringTagConverter());
        ConverterRegistry.register(ListTag.class, List.class, new ListTagConverter());
        ConverterRegistry.register(CompoundTag.class, Map.class, new CompoundTagConverter());
        ConverterRegistry.register(IntArrayTag.class, int[].class, new IntArrayTagConverter());
        ConverterRegistry.register(LongArrayTag.class, long[].class, new LongArrayTagConverter());
    }
}

