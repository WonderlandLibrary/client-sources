/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.opennbt.tag;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import us.myles.viaversion.libs.opennbt.tag.TagCreateException;
import us.myles.viaversion.libs.opennbt.tag.TagRegisterException;
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

public class TagRegistry {
    private static final Map<Integer, Class<? extends Tag>> idToTag = new HashMap<Integer, Class<? extends Tag>>();
    private static final Map<Class<? extends Tag>, Integer> tagToId = new HashMap<Class<? extends Tag>, Integer>();

    public static void register(int id, Class<? extends Tag> tag) throws TagRegisterException {
        if (idToTag.containsKey(id)) {
            throw new TagRegisterException("Tag ID \"" + id + "\" is already in use.");
        }
        if (tagToId.containsKey(tag)) {
            throw new TagRegisterException("Tag \"" + tag.getSimpleName() + "\" is already registered.");
        }
        idToTag.put(id, tag);
        tagToId.put(tag, id);
    }

    public static void unregister(int id) {
        tagToId.remove(TagRegistry.getClassFor(id));
        idToTag.remove(id);
    }

    public static Class<? extends Tag> getClassFor(int id) {
        if (!idToTag.containsKey(id)) {
            return null;
        }
        return idToTag.get(id);
    }

    public static int getIdFor(Class<? extends Tag> clazz) {
        if (!tagToId.containsKey(clazz)) {
            return -1;
        }
        return tagToId.get(clazz);
    }

    public static Tag createInstance(int id, String tagName) throws TagCreateException {
        Class<? extends Tag> clazz = idToTag.get(id);
        if (clazz == null) {
            throw new TagCreateException("Could not find tag with ID \"" + id + "\".");
        }
        try {
            Constructor<? extends Tag> constructor = clazz.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            return constructor.newInstance(tagName);
        }
        catch (Exception e) {
            throw new TagCreateException("Failed to create instance of tag \"" + clazz.getSimpleName() + "\".", e);
        }
    }

    static {
        TagRegistry.register(1, ByteTag.class);
        TagRegistry.register(2, ShortTag.class);
        TagRegistry.register(3, IntTag.class);
        TagRegistry.register(4, LongTag.class);
        TagRegistry.register(5, FloatTag.class);
        TagRegistry.register(6, DoubleTag.class);
        TagRegistry.register(7, ByteArrayTag.class);
        TagRegistry.register(8, StringTag.class);
        TagRegistry.register(9, ListTag.class);
        TagRegistry.register(10, CompoundTag.class);
        TagRegistry.register(11, IntArrayTag.class);
        TagRegistry.register(12, LongArrayTag.class);
        TagRegistry.register(60, DoubleArrayTag.class);
        TagRegistry.register(61, FloatArrayTag.class);
        TagRegistry.register(65, ShortArrayTag.class);
    }
}

