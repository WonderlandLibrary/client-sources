/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTagImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class CompoundTagBuilder
implements CompoundBinaryTag.Builder {
    @Nullable
    private Map<String, BinaryTag> tags;

    CompoundTagBuilder() {
    }

    private Map<String, BinaryTag> tags() {
        if (this.tags == null) {
            this.tags = new HashMap<String, BinaryTag>();
        }
        return this.tags;
    }

    @Override
    public @NotNull CompoundBinaryTag.Builder put(@NotNull String string, @NotNull BinaryTag binaryTag) {
        this.tags().put(string, binaryTag);
        return this;
    }

    @Override
    public @NotNull CompoundBinaryTag.Builder put(@NotNull CompoundBinaryTag compoundBinaryTag) {
        Map<String, BinaryTag> map = this.tags();
        for (String string : compoundBinaryTag.keySet()) {
            map.put(string, compoundBinaryTag.get(string));
        }
        return this;
    }

    @Override
    public @NotNull CompoundBinaryTag.Builder put(@NotNull Map<String, ? extends BinaryTag> map) {
        this.tags().putAll(map);
        return this;
    }

    @Override
    public @NotNull CompoundBinaryTag.Builder remove(@NotNull String string, @Nullable Consumer<? super BinaryTag> consumer) {
        if (this.tags != null) {
            BinaryTag binaryTag = this.tags.remove(string);
            if (consumer != null) {
                consumer.accept(binaryTag);
            }
        }
        return this;
    }

    @Override
    @NotNull
    public CompoundBinaryTag build() {
        if (this.tags == null) {
            return CompoundBinaryTag.empty();
        }
        return new CompoundBinaryTagImpl(new HashMap<String, BinaryTag>(this.tags));
    }

    @Override
    public @NotNull Object remove(@NotNull String string, @Nullable Consumer consumer) {
        return this.remove(string, consumer);
    }

    @Override
    public @NotNull Object put(@NotNull Map map) {
        return this.put(map);
    }

    @Override
    public @NotNull Object put(@NotNull CompoundBinaryTag compoundBinaryTag) {
        return this.put(compoundBinaryTag);
    }

    @Override
    public @NotNull Object put(@NotNull String string, @NotNull BinaryTag binaryTag) {
        return this.put(string, binaryTag);
    }
}

