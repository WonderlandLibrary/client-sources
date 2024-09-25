/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.opennbt.conversion;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import us.myles.viaversion.libs.opennbt.conversion.ConversionException;
import us.myles.viaversion.libs.opennbt.conversion.ConverterRegisterException;
import us.myles.viaversion.libs.opennbt.conversion.TagConverter;
import us.myles.viaversion.libs.opennbt.conversion.builtin.ByteArrayTagConverter;
import us.myles.viaversion.libs.opennbt.conversion.builtin.ByteTagConverter;
import us.myles.viaversion.libs.opennbt.conversion.builtin.CompoundTagConverter;
import us.myles.viaversion.libs.opennbt.conversion.builtin.DoubleTagConverter;
import us.myles.viaversion.libs.opennbt.conversion.builtin.FloatTagConverter;
import us.myles.viaversion.libs.opennbt.conversion.builtin.IntArrayTagConverter;
import us.myles.viaversion.libs.opennbt.conversion.builtin.IntTagConverter;
import us.myles.viaversion.libs.opennbt.conversion.builtin.ListTagConverter;
import us.myles.viaversion.libs.opennbt.conversion.builtin.LongArrayTagConverter;
import us.myles.viaversion.libs.opennbt.conversion.builtin.LongTagConverter;
import us.myles.viaversion.libs.opennbt.conversion.builtin.ShortTagConverter;
import us.myles.viaversion.libs.opennbt.conversion.builtin.StringTagConverter;
import us.myles.viaversion.libs.opennbt.conversion.builtin.custom.DoubleArrayTagConverter;
import us.myles.viaversion.libs.opennbt.conversion.builtin.custom.FloatArrayTagConverter;
import us.myles.viaversion.libs.opennbt.conversion.builtin.custom.ShortArrayTagConverter;
import us.myles.viaversion.libs.opennbt.tag.builtin.ByteArrayTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ByteTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.DoubleTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.FloatTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.IntTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ListTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.LongTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ShortTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;
import us.myles.viaversion.libs.opennbt.tag.builtin.custom.DoubleArrayTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.custom.FloatArrayTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.custom.ShortArrayTag;

public class ConverterRegistry {
    private static final Map<Class<? extends Tag>, TagConverter<? extends Tag, ?>> tagToConverter = new HashMap();
    private static final Map<Class<?>, TagConverter<? extends Tag, ?>> typeToConverter = new HashMap();

    public static <T extends Tag, V> void register(Class<T> tag, Class<V> type, TagConverter<T, V> converter) throws ConverterRegisterException {
        if (tagToConverter.containsKey(tag)) {
            throw new ConverterRegisterException("Type conversion to tag " + tag.getName() + " is already registered.");
        }
        if (typeToConverter.containsKey(type)) {
            throw new ConverterRegisterException("Tag conversion to type " + type.getName() + " is already registered.");
        }
        tagToConverter.put(tag, converter);
        typeToConverter.put(type, converter);
    }

    public static <T extends Tag, V> void unregister(Class<T> tag, Class<V> type) {
        tagToConverter.remove(tag);
        typeToConverter.remove(type);
    }

    public static <T extends Tag, V> V convertToValue(T tag) throws ConversionException {
        if (tag == null || tag.getValue() == null) {
            return null;
        }
        if (!tagToConverter.containsKey(tag.getClass())) {
            throw new ConversionException("Tag type " + tag.getClass().getName() + " has no converter.");
        }
        TagConverter<Tag, ?> converter = tagToConverter.get(tag.getClass());
        return (V)converter.convert(tag);
    }

    public static <V, T extends Tag> T convertToTag(String name, V value) throws ConversionException {
        if (value == null) {
            return null;
        }
        TagConverter<Tag, ?> converter = typeToConverter.get(value.getClass());
        if (converter == null) {
            for (Class<?> clazz : ConverterRegistry.getAllClasses(value.getClass())) {
                if (!typeToConverter.containsKey(clazz)) continue;
                try {
                    converter = typeToConverter.get(clazz);
                    break;
                }
                catch (ClassCastException classCastException) {
                }
            }
        }
        if (converter == null) {
            throw new ConversionException("Value type " + value.getClass().getName() + " has no converter.");
        }
        return (T)converter.convert(name, value);
    }

    private static Set<Class<?>> getAllClasses(Class<?> clazz) {
        LinkedHashSet ret = new LinkedHashSet();
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            ret.add(c);
            ret.addAll(ConverterRegistry.getAllSuperInterfaces(c));
        }
        if (ret.contains(Serializable.class)) {
            ret.remove(Serializable.class);
            ret.add(Serializable.class);
        }
        return ret;
    }

    private static Set<Class<?>> getAllSuperInterfaces(Class<?> clazz) {
        HashSet ret = new HashSet();
        for (Class<?> c : clazz.getInterfaces()) {
            ret.add(c);
            ret.addAll(ConverterRegistry.getAllSuperInterfaces(c));
        }
        return ret;
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
        ConverterRegistry.register(DoubleArrayTag.class, double[].class, new DoubleArrayTagConverter());
        ConverterRegistry.register(FloatArrayTag.class, float[].class, new FloatArrayTagConverter());
        ConverterRegistry.register(ShortArrayTag.class, short[].class, new ShortArrayTagConverter());
    }
}

