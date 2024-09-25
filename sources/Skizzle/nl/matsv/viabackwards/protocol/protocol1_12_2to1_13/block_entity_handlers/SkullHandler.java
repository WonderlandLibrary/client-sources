/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.viaversion.libs.opennbt.tag.builtin.ByteTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;

public class SkullHandler
implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
    private static final int SKULL_START = 5447;

    @Override
    public CompoundTag transform(UserConnection user, int blockId, CompoundTag tag) {
        int diff = blockId - 5447;
        int pos = diff % 20;
        byte type = (byte)Math.floor((float)diff / 20.0f);
        tag.put(new ByteTag("SkullType", type));
        if (pos < 4) {
            return tag;
        }
        tag.put(new ByteTag("Rot", (byte)(pos - 4 & 0xFF)));
        return tag;
    }
}

