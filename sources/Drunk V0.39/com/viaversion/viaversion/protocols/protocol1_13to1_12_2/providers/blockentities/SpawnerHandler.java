/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.EntityNameRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;

public class SpawnerHandler
implements BlockEntityProvider.BlockEntityHandler {
    @Override
    public int transform(UserConnection user, CompoundTag tag) {
        if (!tag.contains("SpawnData")) return -1;
        if (!(tag.get("SpawnData") instanceof CompoundTag)) return -1;
        CompoundTag data = (CompoundTag)tag.get("SpawnData");
        if (!data.contains("id")) return -1;
        if (!(data.get("id") instanceof StringTag)) return -1;
        StringTag s = (StringTag)data.get("id");
        s.setValue(EntityNameRewriter.rewrite(s.getValue()));
        return -1;
    }
}

