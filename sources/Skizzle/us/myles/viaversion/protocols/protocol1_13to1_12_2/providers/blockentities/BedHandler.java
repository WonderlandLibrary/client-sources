/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_13to1_12_2.providers.blockentities;

import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.storage.BlockStorage;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class BedHandler
implements BlockEntityProvider.BlockEntityHandler {
    @Override
    public int transform(UserConnection user, CompoundTag tag) {
        Position position;
        BlockStorage storage = user.get(BlockStorage.class);
        if (!storage.contains(position = new Position((int)this.getLong((Tag)tag.get("x")), (short)this.getLong((Tag)tag.get("y")), (int)this.getLong((Tag)tag.get("z"))))) {
            Via.getPlatform().getLogger().warning("Received an bed color update packet, but there is no bed! O_o " + tag);
            return -1;
        }
        int blockId = storage.get(position).getOriginal() - 972 + 748;
        Object color = tag.get("color");
        if (color != null) {
            blockId += ((Number)((Tag)color).getValue()).intValue() * 16;
        }
        return blockId;
    }

    private long getLong(Tag tag) {
        return ((Integer)tag.getValue()).longValue();
    }
}

