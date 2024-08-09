/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Debug$Renderer
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Debug.Renderer(text="\"CompoundBinaryTag[length=\" + this.tags.size() + \"]\"", childrenArray="this.tags.entrySet().toArray()", hasChildren="!this.tags.isEmpty()")
final class CompoundBinaryTagImpl
extends AbstractBinaryTag
implements CompoundBinaryTag {
    static final CompoundBinaryTag EMPTY = new CompoundBinaryTagImpl(Collections.emptyMap());
    private final Map<String, BinaryTag> tags;
    private final int hashCode;

    CompoundBinaryTagImpl(Map<String, BinaryTag> map) {
        this.tags = Collections.unmodifiableMap(map);
        this.hashCode = map.hashCode();
    }

    public boolean contains(@NotNull String string, @NotNull BinaryTagType<?> binaryTagType) {
        @Nullable BinaryTag binaryTag = this.tags.get(string);
        return binaryTag != null && binaryTagType.test(binaryTag.type());
    }

    @Override
    @NotNull
    public Set<String> keySet() {
        return Collections.unmodifiableSet(this.tags.keySet());
    }

    @Override
    @Nullable
    public BinaryTag get(String string) {
        return this.tags.get(string);
    }

    @Override
    @NotNull
    public CompoundBinaryTag put(@NotNull String string, @NotNull BinaryTag binaryTag) {
        return this.edit(arg_0 -> CompoundBinaryTagImpl.lambda$put$0(string, binaryTag, arg_0));
    }

    @Override
    @NotNull
    public CompoundBinaryTag put(@NotNull CompoundBinaryTag compoundBinaryTag) {
        return this.edit(arg_0 -> CompoundBinaryTagImpl.lambda$put$1(compoundBinaryTag, arg_0));
    }

    @Override
    @NotNull
    public CompoundBinaryTag put(@NotNull Map<String, ? extends BinaryTag> map) {
        return this.edit(arg_0 -> CompoundBinaryTagImpl.lambda$put$2(map, arg_0));
    }

    @Override
    @NotNull
    public CompoundBinaryTag remove(@NotNull String string, @Nullable Consumer<? super BinaryTag> consumer) {
        if (!this.tags.containsKey(string)) {
            return this;
        }
        return this.edit(arg_0 -> CompoundBinaryTagImpl.lambda$remove$3(string, consumer, arg_0));
    }

    @Override
    public byte getByte(@NotNull String string, byte by) {
        if (this.contains(string, BinaryTagTypes.BYTE)) {
            return ((NumberBinaryTag)this.tags.get(string)).byteValue();
        }
        return by;
    }

    @Override
    public short getShort(@NotNull String string, short s) {
        if (this.contains(string, BinaryTagTypes.SHORT)) {
            return ((NumberBinaryTag)this.tags.get(string)).shortValue();
        }
        return s;
    }

    @Override
    public int getInt(@NotNull String string, int n) {
        if (this.contains(string, BinaryTagTypes.INT)) {
            return ((NumberBinaryTag)this.tags.get(string)).intValue();
        }
        return n;
    }

    @Override
    public long getLong(@NotNull String string, long l) {
        if (this.contains(string, BinaryTagTypes.LONG)) {
            return ((NumberBinaryTag)this.tags.get(string)).longValue();
        }
        return l;
    }

    @Override
    public float getFloat(@NotNull String string, float f) {
        if (this.contains(string, BinaryTagTypes.FLOAT)) {
            return ((NumberBinaryTag)this.tags.get(string)).floatValue();
        }
        return f;
    }

    @Override
    public double getDouble(@NotNull String string, double d) {
        if (this.contains(string, BinaryTagTypes.DOUBLE)) {
            return ((NumberBinaryTag)this.tags.get(string)).doubleValue();
        }
        return d;
    }

    @Override
    public byte @NotNull [] getByteArray(@NotNull String string) {
        if (this.contains(string, BinaryTagTypes.BYTE_ARRAY)) {
            return ((ByteArrayBinaryTag)this.tags.get(string)).value();
        }
        return new byte[0];
    }

    @Override
    public byte @NotNull [] getByteArray(@NotNull String string, byte @NotNull [] byArray) {
        if (this.contains(string, BinaryTagTypes.BYTE_ARRAY)) {
            return ((ByteArrayBinaryTag)this.tags.get(string)).value();
        }
        return byArray;
    }

    @Override
    @NotNull
    public String getString(@NotNull String string, @NotNull String string2) {
        if (this.contains(string, BinaryTagTypes.STRING)) {
            return ((StringBinaryTag)this.tags.get(string)).value();
        }
        return string2;
    }

    @Override
    @NotNull
    public ListBinaryTag getList(@NotNull String string, @NotNull ListBinaryTag listBinaryTag) {
        if (this.contains(string, BinaryTagTypes.LIST)) {
            return (ListBinaryTag)this.tags.get(string);
        }
        return listBinaryTag;
    }

    @Override
    @NotNull
    public ListBinaryTag getList(@NotNull String string, @NotNull BinaryTagType<? extends BinaryTag> binaryTagType, @NotNull ListBinaryTag listBinaryTag) {
        ListBinaryTag listBinaryTag2;
        if (this.contains(string, BinaryTagTypes.LIST) && binaryTagType.test((listBinaryTag2 = (ListBinaryTag)this.tags.get(string)).elementType())) {
            return listBinaryTag2;
        }
        return listBinaryTag;
    }

    @Override
    @NotNull
    public CompoundBinaryTag getCompound(@NotNull String string, @NotNull CompoundBinaryTag compoundBinaryTag) {
        if (this.contains(string, BinaryTagTypes.COMPOUND)) {
            return (CompoundBinaryTag)this.tags.get(string);
        }
        return compoundBinaryTag;
    }

    @Override
    public int @NotNull [] getIntArray(@NotNull String string) {
        if (this.contains(string, BinaryTagTypes.INT_ARRAY)) {
            return ((IntArrayBinaryTag)this.tags.get(string)).value();
        }
        return new int[0];
    }

    @Override
    public int @NotNull [] getIntArray(@NotNull String string, int @NotNull [] nArray) {
        if (this.contains(string, BinaryTagTypes.INT_ARRAY)) {
            return ((IntArrayBinaryTag)this.tags.get(string)).value();
        }
        return nArray;
    }

    @Override
    public long @NotNull [] getLongArray(@NotNull String string) {
        if (this.contains(string, BinaryTagTypes.LONG_ARRAY)) {
            return ((LongArrayBinaryTag)this.tags.get(string)).value();
        }
        return new long[0];
    }

    @Override
    public long @NotNull [] getLongArray(@NotNull String string, long @NotNull [] lArray) {
        if (this.contains(string, BinaryTagTypes.LONG_ARRAY)) {
            return ((LongArrayBinaryTag)this.tags.get(string)).value();
        }
        return lArray;
    }

    private CompoundBinaryTag edit(Consumer<Map<String, BinaryTag>> consumer) {
        HashMap<String, BinaryTag> hashMap = new HashMap<String, BinaryTag>(this.tags);
        consumer.accept(hashMap);
        return new CompoundBinaryTagImpl(hashMap);
    }

    public boolean equals(Object object) {
        return this == object || object instanceof CompoundBinaryTagImpl && this.tags.equals(((CompoundBinaryTagImpl)object).tags);
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
    public void forEach(@NotNull Consumer<? super Map.Entry<String, ? extends BinaryTag>> consumer) {
        this.tags.entrySet().forEach(Objects.requireNonNull(consumer, "action"));
    }

    @Override
    @NotNull
    public Object remove(@NotNull String string, @Nullable Consumer consumer) {
        return this.remove(string, consumer);
    }

    @Override
    @NotNull
    public Object put(@NotNull Map map) {
        return this.put(map);
    }

    @Override
    @NotNull
    public Object put(@NotNull CompoundBinaryTag compoundBinaryTag) {
        return this.put(compoundBinaryTag);
    }

    @Override
    @NotNull
    public Object put(@NotNull String string, @NotNull BinaryTag binaryTag) {
        return this.put(string, binaryTag);
    }

    private static void lambda$remove$3(String string, Consumer consumer, Map map) {
        BinaryTag binaryTag = (BinaryTag)map.remove(string);
        if (consumer != null) {
            consumer.accept(binaryTag);
        }
    }

    private static void lambda$put$2(Map map, Map map2) {
        map2.putAll(map);
    }

    private static void lambda$put$1(CompoundBinaryTag compoundBinaryTag, Map map) {
        for (String string : compoundBinaryTag.keySet()) {
            map.put(string, compoundBinaryTag.get(string));
        }
    }

    private static void lambda$put$0(String string, BinaryTag binaryTag, Map map) {
        map.put(string, binaryTag);
    }
}

