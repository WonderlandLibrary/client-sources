/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagTypes;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTagImpl;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundTagBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundTagSetter;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ListBinaryTag;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CompoundBinaryTag
extends BinaryTag,
CompoundTagSetter<CompoundBinaryTag>,
Iterable<Map.Entry<String, ? extends BinaryTag>> {
    @NotNull
    public static CompoundBinaryTag empty() {
        return CompoundBinaryTagImpl.EMPTY;
    }

    @NotNull
    public static CompoundBinaryTag from(@NotNull Map<String, ? extends BinaryTag> map) {
        if (map.isEmpty()) {
            return CompoundBinaryTag.empty();
        }
        return new CompoundBinaryTagImpl(new HashMap<String, BinaryTag>(map));
    }

    @NotNull
    public static Builder builder() {
        return new CompoundTagBuilder();
    }

    @NotNull
    default public BinaryTagType<CompoundBinaryTag> type() {
        return BinaryTagTypes.COMPOUND;
    }

    @NotNull
    public Set<String> keySet();

    @Nullable
    public BinaryTag get(String var1);

    default public boolean getBoolean(@NotNull String string) {
        return this.getBoolean(string, false);
    }

    default public boolean getBoolean(@NotNull String string, boolean bl) {
        return this.getByte(string) != 0 || bl;
    }

    default public byte getByte(@NotNull String string) {
        return this.getByte(string, (byte)0);
    }

    public byte getByte(@NotNull String var1, byte var2);

    default public short getShort(@NotNull String string) {
        return this.getShort(string, (short)0);
    }

    public short getShort(@NotNull String var1, short var2);

    default public int getInt(@NotNull String string) {
        return this.getInt(string, 0);
    }

    public int getInt(@NotNull String var1, int var2);

    default public long getLong(@NotNull String string) {
        return this.getLong(string, 0L);
    }

    public long getLong(@NotNull String var1, long var2);

    default public float getFloat(@NotNull String string) {
        return this.getFloat(string, 0.0f);
    }

    public float getFloat(@NotNull String var1, float var2);

    default public double getDouble(@NotNull String string) {
        return this.getDouble(string, 0.0);
    }

    public double getDouble(@NotNull String var1, double var2);

    public byte @NotNull [] getByteArray(@NotNull String var1);

    public byte @NotNull [] getByteArray(@NotNull String var1, byte @NotNull [] var2);

    @NotNull
    default public String getString(@NotNull String string) {
        return this.getString(string, "");
    }

    @NotNull
    public String getString(@NotNull String var1, @NotNull String var2);

    @NotNull
    default public ListBinaryTag getList(@NotNull String string) {
        return this.getList(string, ListBinaryTag.empty());
    }

    @NotNull
    public ListBinaryTag getList(@NotNull String var1, @NotNull ListBinaryTag var2);

    @NotNull
    default public ListBinaryTag getList(@NotNull String string, @NotNull BinaryTagType<? extends BinaryTag> binaryTagType) {
        return this.getList(string, binaryTagType, ListBinaryTag.empty());
    }

    @NotNull
    public ListBinaryTag getList(@NotNull String var1, @NotNull BinaryTagType<? extends BinaryTag> var2, @NotNull ListBinaryTag var3);

    @NotNull
    default public CompoundBinaryTag getCompound(@NotNull String string) {
        return this.getCompound(string, CompoundBinaryTag.empty());
    }

    @NotNull
    public CompoundBinaryTag getCompound(@NotNull String var1, @NotNull CompoundBinaryTag var2);

    public int @NotNull [] getIntArray(@NotNull String var1);

    public int @NotNull [] getIntArray(@NotNull String var1, int @NotNull [] var2);

    public long @NotNull [] getLongArray(@NotNull String var1);

    public long @NotNull [] getLongArray(@NotNull String var1, long @NotNull [] var2);

    public static interface Builder
    extends CompoundTagSetter<Builder> {
        @NotNull
        public CompoundBinaryTag build();
    }
}

