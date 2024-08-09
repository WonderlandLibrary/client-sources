/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockStorage;

public class BedHandler
implements BlockEntityProvider.BlockEntityHandler {
    @Override
    public int transform(UserConnection userConnection, CompoundTag compoundTag) {
        Position position;
        BlockStorage blockStorage = userConnection.get(BlockStorage.class);
        if (!blockStorage.contains(position = new Position((int)this.getLong((NumberTag)compoundTag.get("x")), (short)this.getLong((NumberTag)compoundTag.get("y")), (int)this.getLong((NumberTag)compoundTag.get("z"))))) {
            Via.getPlatform().getLogger().warning("Received an bed color update packet, but there is no bed! O_o " + compoundTag);
            return 1;
        }
        int n = blockStorage.get(position).getOriginal() - 972 + 748;
        Object t = compoundTag.get("color");
        if (t instanceof NumberTag) {
            n += ((NumberTag)t).asInt() * 16;
        }
        return n;
    }

    private long getLong(NumberTag numberTag) {
        return numberTag.asLong();
    }
}

