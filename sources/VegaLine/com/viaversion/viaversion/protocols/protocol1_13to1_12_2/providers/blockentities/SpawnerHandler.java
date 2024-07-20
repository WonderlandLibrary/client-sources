/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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
        Object id;
        Object data = tag.get("SpawnData");
        if (data instanceof CompoundTag && (id = ((CompoundTag)data).get("id")) instanceof StringTag) {
            ((StringTag)id).setValue(EntityNameRewriter.rewrite(((StringTag)id).getValue()));
        }
        return -1;
    }
}

