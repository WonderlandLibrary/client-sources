/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.IntTag;

public class BedHandler
implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
    @Override
    public CompoundTag transform(UserConnection user, int blockId, CompoundTag tag) {
        int offset = blockId - 748;
        int color = offset >> 4;
        tag.put(new IntTag("color", color));
        return tag;
    }
}

