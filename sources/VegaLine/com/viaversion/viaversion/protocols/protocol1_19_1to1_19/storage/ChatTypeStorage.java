/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19_1to1_19.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.Protocol1_19To1_18_2;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ChatTypeStorage
implements StorableObject {
    private final Int2ObjectMap<CompoundTag> chatTypes = new Int2ObjectOpenHashMap<CompoundTag>();

    public @Nullable CompoundTag chatType(int id) {
        return this.chatTypes.isEmpty() ? Protocol1_19To1_18_2.MAPPINGS.chatType(id) : (CompoundTag)this.chatTypes.get(id);
    }

    public void addChatType(int id, CompoundTag chatType) {
        this.chatTypes.put(id, chatType);
    }

    public void clear() {
        this.chatTypes.clear();
    }

    @Override
    public boolean clearOnServerSwitch() {
        return false;
    }
}

