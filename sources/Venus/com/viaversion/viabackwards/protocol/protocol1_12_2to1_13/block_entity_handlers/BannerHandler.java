/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public class BannerHandler
implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
    private static final int WALL_BANNER_START = 7110;
    private static final int WALL_BANNER_STOP = 7173;
    private static final int BANNER_START = 6854;
    private static final int BANNER_STOP = 7109;

    @Override
    public CompoundTag transform(UserConnection userConnection, int n, CompoundTag compoundTag) {
        int n2;
        if (n >= 6854 && n <= 7109) {
            n2 = n - 6854 >> 4;
            compoundTag.put("Base", new IntTag(15 - n2));
        } else if (n >= 7110 && n <= 7173) {
            n2 = n - 7110 >> 2;
            compoundTag.put("Base", new IntTag(15 - n2));
        } else {
            ViaBackwards.getPlatform().getLogger().warning("Why does this block have the banner block entity? :(" + compoundTag);
        }
        Object t = compoundTag.get("Patterns");
        if (t instanceof ListTag) {
            for (Tag tag : (ListTag)t) {
                if (!(tag instanceof CompoundTag)) continue;
                IntTag intTag = (IntTag)((CompoundTag)tag).get("Color");
                intTag.setValue(15 - intTag.asInt());
            }
        }
        return compoundTag;
    }
}

