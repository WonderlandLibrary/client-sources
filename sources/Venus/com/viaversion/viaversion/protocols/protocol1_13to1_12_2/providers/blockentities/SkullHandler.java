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

public class SkullHandler
implements BlockEntityProvider.BlockEntityHandler {
    private static final int SKULL_WALL_START = 5447;
    private static final int SKULL_END = 5566;

    @Override
    public int transform(UserConnection userConnection, CompoundTag compoundTag) {
        Position position;
        BlockStorage blockStorage = userConnection.get(BlockStorage.class);
        if (!blockStorage.contains(position = new Position((int)this.getLong((NumberTag)compoundTag.get("x")), (short)this.getLong((NumberTag)compoundTag.get("y")), (int)this.getLong((NumberTag)compoundTag.get("z"))))) {
            Via.getPlatform().getLogger().warning("Received an head update packet, but there is no head! O_o " + compoundTag);
            return 1;
        }
        int n = blockStorage.get(position).getOriginal();
        if (n >= 5447 && n <= 5566) {
            Object t;
            Object t2 = compoundTag.get("SkullType");
            if (t2 instanceof NumberTag) {
                n += ((NumberTag)t2).asInt() * 20;
            }
            if ((t = compoundTag.get("Rot")) instanceof NumberTag) {
                n += ((NumberTag)t).asInt();
            }
        } else {
            Via.getPlatform().getLogger().warning("Why does this block have the skull block entity? " + compoundTag);
            return 1;
        }
        return n;
    }

    private long getLong(NumberTag numberTag) {
        return numberTag.asLong();
    }
}

