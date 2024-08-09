/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;

public class SkullHandler
implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
    private static final int SKULL_START = 5447;

    @Override
    public CompoundTag transform(UserConnection userConnection, int n, CompoundTag compoundTag) {
        int n2 = n - 5447;
        int n3 = n2 % 20;
        byte by = (byte)Math.floor((float)n2 / 20.0f);
        compoundTag.put("SkullType", new ByteTag(by));
        if (n3 < 4) {
            return compoundTag;
        }
        compoundTag.put("Rot", new ByteTag((byte)(n3 - 4 & 0xFF)));
        return compoundTag;
    }
}

