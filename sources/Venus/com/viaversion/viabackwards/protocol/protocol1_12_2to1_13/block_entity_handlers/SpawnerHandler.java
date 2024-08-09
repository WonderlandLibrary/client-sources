/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.EntityNameRewrites;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;

public class SpawnerHandler
implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
    @Override
    public CompoundTag transform(UserConnection userConnection, int n, CompoundTag compoundTag) {
        CompoundTag compoundTag2;
        Object t;
        Object t2 = compoundTag.get("SpawnData");
        if (t2 instanceof CompoundTag && (t = (compoundTag2 = (CompoundTag)t2).get("id")) instanceof StringTag) {
            StringTag stringTag = (StringTag)t;
            stringTag.setValue(EntityNameRewrites.rewrite(stringTag.getValue()));
        }
        return compoundTag;
    }
}

