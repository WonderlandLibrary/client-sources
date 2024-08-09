/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage;

import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.Protocol1_18_2To1_19;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class DimensionRegistryStorage
implements StorableObject {
    private final Map<String, CompoundTag> dimensions = new HashMap<String, CompoundTag>();
    private final Int2ObjectMap<CompoundTag> chatTypes = new Int2ObjectOpenHashMap<CompoundTag>();

    public @Nullable CompoundTag dimension(String string) {
        CompoundTag compoundTag = this.dimensions.get(string);
        return compoundTag != null ? compoundTag.clone() : null;
    }

    public void addDimension(String string, CompoundTag compoundTag) {
        this.dimensions.put(string, compoundTag);
    }

    public @Nullable CompoundTag chatType(int n) {
        return this.chatTypes.isEmpty() ? Protocol1_18_2To1_19.MAPPINGS.chatType(n) : (CompoundTag)this.chatTypes.get(n);
    }

    public void addChatType(int n, CompoundTag compoundTag) {
        this.chatTypes.put(n, compoundTag);
    }

    public void clear() {
        this.dimensions.clear();
        this.chatTypes.clear();
    }

    @Override
    public boolean clearOnServerSwitch() {
        return true;
    }
}

