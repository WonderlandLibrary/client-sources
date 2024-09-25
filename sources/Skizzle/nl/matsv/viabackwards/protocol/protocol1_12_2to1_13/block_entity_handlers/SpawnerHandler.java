/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.data.EntityNameRewrites;
import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;

public class SpawnerHandler
implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
    @Override
    public CompoundTag transform(UserConnection user, int blockId, CompoundTag tag) {
        CompoundTag data;
        Object idTag;
        Object dataTag = tag.get("SpawnData");
        if (dataTag instanceof CompoundTag && (idTag = (data = (CompoundTag)dataTag).get("id")) instanceof StringTag) {
            StringTag s = (StringTag)idTag;
            s.setValue(EntityNameRewrites.rewrite(s.getValue()));
        }
        return tag;
    }
}

