/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.opennbt.tag;

import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.opennbt.tag.TagCreateException;
import com.viaversion.viaversion.libs.opennbt.tag.TagRegisterException;
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
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

public final class TagRegistry {
    private static final int HIGHEST_ID = 12;
    private static final Class<? extends Tag>[] idToTag = new Class[13];
    private static final Supplier<? extends Tag>[] instanceSuppliers = new Supplier[13];
    private static final Object2IntMap<Class<? extends Tag>> tagToId = new Object2IntOpenHashMap<Class<? extends Tag>>();

    public static void register(int id, Class<? extends Tag> tag, Supplier<? extends Tag> supplier) throws TagRegisterException {
        if (id < 0 || id > 12) {
            throw new TagRegisterException("Tag ID must be between 0 and 12");
        }
        if (idToTag[id] != null) {
            throw new TagRegisterException("Tag ID \"" + id + "\" is already in use.");
        }
        if (tagToId.containsKey(tag)) {
            throw new TagRegisterException("Tag \"" + tag.getSimpleName() + "\" is already registered.");
        }
        TagRegistry.instanceSuppliers[id] = supplier;
        TagRegistry.idToTag[id] = tag;
        tagToId.put(tag, id);
    }

    public static void unregister(int id) {
        tagToId.removeInt(TagRegistry.getClassFor(id));
        TagRegistry.idToTag[id] = null;
        TagRegistry.instanceSuppliers[id] = null;
    }

    @Nullable
    public static Class<? extends Tag> getClassFor(int id) {
        return id >= 0 && id < idToTag.length ? idToTag[id] : null;
    }

    public static int getIdFor(Class<? extends Tag> clazz) {
        return tagToId.getInt(clazz);
    }

    public static Tag createInstance(int id) throws TagCreateException {
        Supplier<? extends Tag> supplier;
        Supplier<? extends Tag> supplier2 = supplier = id > 0 && id < instanceSuppliers.length ? instanceSuppliers[id] : null;
        if (supplier == null) {
            throw new TagCreateException("Could not find tag with ID \"" + id + "\".");
        }
        return (Tag)supplier.get();
    }

    static {
        tagToId.defaultReturnValue(-1);
        TagRegistry.register(1, ByteTag.class, ByteTag::new);
        TagRegistry.register(2, ShortTag.class, ShortTag::new);
        TagRegistry.register(3, IntTag.class, IntTag::new);
        TagRegistry.register(4, LongTag.class, LongTag::new);
        TagRegistry.register(5, FloatTag.class, FloatTag::new);
        TagRegistry.register(6, DoubleTag.class, DoubleTag::new);
        TagRegistry.register(7, ByteArrayTag.class, ByteArrayTag::new);
        TagRegistry.register(8, StringTag.class, StringTag::new);
        TagRegistry.register(9, ListTag.class, ListTag::new);
        TagRegistry.register(10, CompoundTag.class, CompoundTag::new);
        TagRegistry.register(11, IntArrayTag.class, IntArrayTag::new);
        TagRegistry.register(12, LongArrayTag.class, LongArrayTag::new);
    }
}

