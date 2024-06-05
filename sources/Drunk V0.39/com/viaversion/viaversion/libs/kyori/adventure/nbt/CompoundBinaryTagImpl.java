/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.AbstractBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagTypes;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ListBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.StringBinaryTag;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Debug.Renderer(text="\"CompoundBinaryTag[length=\" + this.tags.size() + \"]\"", childrenArray="this.tags.entrySet().toArray()", hasChildren="!this.tags.isEmpty()")
final class CompoundBinaryTagImpl
extends AbstractBinaryTag
implements CompoundBinaryTag {
    static final CompoundBinaryTag EMPTY = new CompoundBinaryTagImpl(Collections.<String, BinaryTag>emptyMap());
    private final Map<String, BinaryTag> tags;
    private final int hashCode;

    CompoundBinaryTagImpl(Map<String, BinaryTag> tags) {
        this.tags = Collections.unmodifiableMap(tags);
        this.hashCode = tags.hashCode();
    }

    public boolean contains(@NotNull String key, @NotNull BinaryTagType<?> type) {
        @Nullable BinaryTag tag = this.tags.get(key);
        if (tag == null) return false;
        if (!type.test(tag.type())) return false;
        return true;
    }

    @Override
    @NotNull
    public Set<String> keySet() {
        return Collections.unmodifiableSet(this.tags.keySet());
    }

    @Override
    @Nullable
    public BinaryTag get(String key) {
        return this.tags.get(key);
    }

    @Override
    @NotNull
    public CompoundBinaryTag put(@NotNull String key, @NotNull BinaryTag tag) {
        return this.edit(map -> map.put(key, tag));
    }

    @Override
    @NotNull
    public CompoundBinaryTag put(@NotNull CompoundBinaryTag tag) {
        return this.edit(map -> {
            Iterator<String> iterator = tag.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                map.put(key, tag.get(key));
            }
        });
    }

    @Override
    @NotNull
    public CompoundBinaryTag put(@NotNull Map<String, ? extends BinaryTag> tags) {
        return this.edit(map -> map.putAll(tags));
    }

    @Override
    @NotNull
    public CompoundBinaryTag remove(@NotNull String key, @Nullable Consumer<? super BinaryTag> removed) {
        if (this.tags.containsKey(key)) return this.edit(map -> {
            BinaryTag tag = (BinaryTag)map.remove(key);
            if (removed == null) return;
            removed.accept(tag);
        });
        return this;
    }

    @Override
    public byte getByte(@NotNull String key, byte defaultValue) {
        if (!this.contains(key, BinaryTagTypes.BYTE)) return defaultValue;
        return ((NumberBinaryTag)this.tags.get(key)).byteValue();
    }

    @Override
    public short getShort(@NotNull String key, short defaultValue) {
        if (!this.contains(key, BinaryTagTypes.SHORT)) return defaultValue;
        return ((NumberBinaryTag)this.tags.get(key)).shortValue();
    }

    @Override
    public int getInt(@NotNull String key, int defaultValue) {
        if (!this.contains(key, BinaryTagTypes.INT)) return defaultValue;
        return ((NumberBinaryTag)this.tags.get(key)).intValue();
    }

    @Override
    public long getLong(@NotNull String key, long defaultValue) {
        if (!this.contains(key, BinaryTagTypes.LONG)) return defaultValue;
        return ((NumberBinaryTag)this.tags.get(key)).longValue();
    }

    @Override
    public float getFloat(@NotNull String key, float defaultValue) {
        if (!this.contains(key, BinaryTagTypes.FLOAT)) return defaultValue;
        return ((NumberBinaryTag)this.tags.get(key)).floatValue();
    }

    @Override
    public double getDouble(@NotNull String key, double defaultValue) {
        if (!this.contains(key, BinaryTagTypes.DOUBLE)) return defaultValue;
        return ((NumberBinaryTag)this.tags.get(key)).doubleValue();
    }

    @Override
    public byte @NotNull [] getByteArray(@NotNull String key) {
        if (!this.contains(key, BinaryTagTypes.BYTE_ARRAY)) return new byte[0];
        return ((ByteArrayBinaryTag)this.tags.get(key)).value();
    }

    @Override
    public byte @NotNull [] getByteArray(@NotNull String key, byte @NotNull [] defaultValue) {
        if (!this.contains(key, BinaryTagTypes.BYTE_ARRAY)) return defaultValue;
        return ((ByteArrayBinaryTag)this.tags.get(key)).value();
    }

    @Override
    @NotNull
    public String getString(@NotNull String key, @NotNull String defaultValue) {
        if (!this.contains(key, BinaryTagTypes.STRING)) return defaultValue;
        return ((StringBinaryTag)this.tags.get(key)).value();
    }

    @Override
    @NotNull
    public ListBinaryTag getList(@NotNull String key, @NotNull ListBinaryTag defaultValue) {
        if (!this.contains(key, BinaryTagTypes.LIST)) return defaultValue;
        return (ListBinaryTag)this.tags.get(key);
    }

    @Override
    @NotNull
    public ListBinaryTag getList(@NotNull String key, @NotNull BinaryTagType<? extends BinaryTag> expectedType, @NotNull ListBinaryTag defaultValue) {
        if (!this.contains(key, BinaryTagTypes.LIST)) return defaultValue;
        ListBinaryTag tag = (ListBinaryTag)this.tags.get(key);
        if (!expectedType.test(tag.elementType())) return defaultValue;
        return tag;
    }

    @Override
    @NotNull
    public CompoundBinaryTag getCompound(@NotNull String key, @NotNull CompoundBinaryTag defaultValue) {
        if (!this.contains(key, BinaryTagTypes.COMPOUND)) return defaultValue;
        return (CompoundBinaryTag)this.tags.get(key);
    }

    @Override
    public int @NotNull [] getIntArray(@NotNull String key) {
        if (!this.contains(key, BinaryTagTypes.INT_ARRAY)) return new int[0];
        return ((IntArrayBinaryTag)this.tags.get(key)).value();
    }

    @Override
    public int @NotNull [] getIntArray(@NotNull String key, int @NotNull [] defaultValue) {
        if (!this.contains(key, BinaryTagTypes.INT_ARRAY)) return defaultValue;
        return ((IntArrayBinaryTag)this.tags.get(key)).value();
    }

    @Override
    public long @NotNull [] getLongArray(@NotNull String key) {
        if (!this.contains(key, BinaryTagTypes.LONG_ARRAY)) return new long[0];
        return ((LongArrayBinaryTag)this.tags.get(key)).value();
    }

    @Override
    public long @NotNull [] getLongArray(@NotNull String key, long @NotNull [] defaultValue) {
        if (!this.contains(key, BinaryTagTypes.LONG_ARRAY)) return defaultValue;
        return ((LongArrayBinaryTag)this.tags.get(key)).value();
    }

    private CompoundBinaryTag edit(Consumer<Map<String, BinaryTag>> consumer) {
        HashMap<String, BinaryTag> tags = new HashMap<String, BinaryTag>(this.tags);
        consumer.accept(tags);
        return new CompoundBinaryTagImpl(tags);
    }

    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof CompoundBinaryTagImpl)) return false;
        if (!this.tags.equals(((CompoundBinaryTagImpl)that).tags)) return false;
        return true;
    }

    public int hashCode() {
        return this.hashCode;
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("tags", this.tags));
    }

    @Override
    @NotNull
    public Iterator<Map.Entry<String, ? extends BinaryTag>> iterator() {
        return this.tags.entrySet().iterator();
    }

    @Override
    public void forEach(@NotNull Consumer<? super Map.Entry<String, ? extends BinaryTag>> action) {
        this.tags.entrySet().forEach(Objects.requireNonNull(action, "action"));
    }
}

