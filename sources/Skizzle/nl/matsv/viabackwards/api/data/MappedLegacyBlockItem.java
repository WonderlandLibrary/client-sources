/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package nl.matsv.viabackwards.api.data;

import nl.matsv.viabackwards.utils.Block;
import org.jetbrains.annotations.Nullable;
import us.myles.viaversion.libs.bungeecordchat.api.ChatColor;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;

public class MappedLegacyBlockItem {
    private final int id;
    private final short data;
    private final String name;
    private final Block block;
    private BlockEntityHandler blockEntityHandler;

    public MappedLegacyBlockItem(int id, short data, @Nullable String name, boolean block) {
        this.id = id;
        this.data = data;
        this.name = name != null ? ChatColor.RESET + name : null;
        this.block = block ? new Block(id, data) : null;
    }

    public int getId() {
        return this.id;
    }

    public short getData() {
        return this.data;
    }

    public String getName() {
        return this.name;
    }

    public boolean isBlock() {
        return this.block != null;
    }

    public Block getBlock() {
        return this.block;
    }

    public boolean hasBlockEntityHandler() {
        return this.blockEntityHandler != null;
    }

    @Nullable
    public BlockEntityHandler getBlockEntityHandler() {
        return this.blockEntityHandler;
    }

    public void setBlockEntityHandler(@Nullable BlockEntityHandler blockEntityHandler) {
        this.blockEntityHandler = blockEntityHandler;
    }

    @FunctionalInterface
    public static interface BlockEntityHandler {
        public CompoundTag handleOrNewCompoundTag(int var1, CompoundTag var2);
    }
}

