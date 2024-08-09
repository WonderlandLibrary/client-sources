/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;

public class SWindowItemsPacket
implements IPacket<IClientPlayNetHandler> {
    private int windowId;
    private List<ItemStack> itemStacks;

    public SWindowItemsPacket() {
    }

    public SWindowItemsPacket(int n, NonNullList<ItemStack> nonNullList) {
        this.windowId = n;
        this.itemStacks = NonNullList.withSize(nonNullList.size(), ItemStack.EMPTY);
        for (int i = 0; i < this.itemStacks.size(); ++i) {
            this.itemStacks.set(i, nonNullList.get(i).copy());
        }
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readUnsignedByte();
        int n = packetBuffer.readShort();
        this.itemStacks = NonNullList.withSize(n, ItemStack.EMPTY);
        for (int i = 0; i < n; ++i) {
            this.itemStacks.set(i, packetBuffer.readItemStack());
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.itemStacks.size());
        for (ItemStack itemStack : this.itemStacks) {
            packetBuffer.writeItemStack(itemStack);
        }
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleWindowItems(this);
    }

    public int getWindowId() {
        return this.windowId;
    }

    public List<ItemStack> getItemStacks() {
        return this.itemStacks;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

