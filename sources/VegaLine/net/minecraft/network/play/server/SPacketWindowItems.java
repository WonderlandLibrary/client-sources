/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.NonNullList;

public class SPacketWindowItems
implements Packet<INetHandlerPlayClient> {
    private int windowId;
    private List<ItemStack> itemStacks;

    public SPacketWindowItems() {
    }

    public SPacketWindowItems(int p_i47317_1_, NonNullList<ItemStack> p_i47317_2_) {
        this.windowId = p_i47317_1_;
        this.itemStacks = NonNullList.func_191197_a(p_i47317_2_.size(), ItemStack.field_190927_a);
        for (int i = 0; i < this.itemStacks.size(); ++i) {
            ItemStack itemstack = p_i47317_2_.get(i);
            this.itemStacks.set(i, itemstack.copy());
        }
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.windowId = buf.readUnsignedByte();
        int i = buf.readShort();
        this.itemStacks = NonNullList.func_191197_a(i, ItemStack.field_190927_a);
        for (int j = 0; j < i; ++j) {
            this.itemStacks.set(j, buf.readItemStackFromBuffer());
        }
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeByte(this.windowId);
        buf.writeShort(this.itemStacks.size());
        for (ItemStack itemstack : this.itemStacks) {
            buf.writeItemStackToBuffer(itemstack);
        }
    }

    @Override
    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleWindowItems(this);
    }

    public int getWindowId() {
        return this.windowId;
    }

    public List<ItemStack> getItemStacks() {
        return this.itemStacks;
    }
}

