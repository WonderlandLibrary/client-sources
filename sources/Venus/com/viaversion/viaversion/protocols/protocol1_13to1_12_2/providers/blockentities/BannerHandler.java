/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockStorage;
import java.util.Iterator;

public class BannerHandler
implements BlockEntityProvider.BlockEntityHandler {
    private static final int WALL_BANNER_START = 7110;
    private static final int WALL_BANNER_STOP = 7173;
    private static final int BANNER_START = 6854;
    private static final int BANNER_STOP = 7109;

    @Override
    public int transform(UserConnection userConnection, CompoundTag compoundTag) {
        Iterator<Tag> iterator2;
        Position position;
        BlockStorage blockStorage = userConnection.get(BlockStorage.class);
        if (!blockStorage.contains(position = new Position((int)this.getLong((NumberTag)compoundTag.get("x")), (short)this.getLong((NumberTag)compoundTag.get("y")), (int)this.getLong((NumberTag)compoundTag.get("z"))))) {
            Via.getPlatform().getLogger().warning("Received an banner color update packet, but there is no banner! O_o " + compoundTag);
            return 1;
        }
        int n = blockStorage.get(position).getOriginal();
        Object t = compoundTag.get("Base");
        int n2 = 0;
        if (t instanceof NumberTag) {
            n2 = ((NumberTag)t).asInt();
        }
        if (n >= 6854 && n <= 7109) {
            n += (15 - n2) * 16;
        } else if (n >= 7110 && n <= 7173) {
            n += (15 - n2) * 4;
        } else {
            Via.getPlatform().getLogger().warning("Why does this block have the banner block entity? :(" + compoundTag);
        }
        Object t2 = compoundTag.get("Patterns");
        if (t2 instanceof ListTag) {
            iterator2 = ((ListTag)t2).iterator();
            while (iterator2.hasNext()) {
                Object t3;
                Tag tag = iterator2.next();
                if (!(tag instanceof CompoundTag) || !((t3 = ((CompoundTag)tag).get("Color")) instanceof IntTag)) continue;
                ((IntTag)t3).setValue(15 - (Integer)((Tag)t3).getValue());
            }
        }
        if ((iterator2 = compoundTag.get("CustomName")) instanceof StringTag) {
            ((StringTag)((Object)iterator2)).setValue(ChatRewriter.legacyTextToJsonString(((StringTag)((Object)iterator2)).getValue()));
        }
        return n;
    }

    private long getLong(NumberTag numberTag) {
        return numberTag.asLong();
    }
}

