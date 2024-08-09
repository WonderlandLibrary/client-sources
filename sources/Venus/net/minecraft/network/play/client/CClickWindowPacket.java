/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CClickWindowPacket
implements IPacket<IServerPlayNetHandler> {
    private int windowId;
    private int slotId;
    private int packedClickData;
    private short actionNumber;
    private ItemStack clickedItem = ItemStack.EMPTY;
    private ClickType mode;

    public CClickWindowPacket() {
    }

    public CClickWindowPacket(int n, int n2, int n3, ClickType clickType, ItemStack itemStack, short s) {
        this.windowId = n;
        this.slotId = n2;
        this.packedClickData = n3;
        this.clickedItem = itemStack.copy();
        this.actionNumber = s;
        this.mode = clickType;
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processClickWindow(this);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readByte();
        this.slotId = packetBuffer.readShort();
        this.packedClickData = packetBuffer.readByte();
        this.actionNumber = packetBuffer.readShort();
        this.mode = packetBuffer.readEnumValue(ClickType.class);
        this.clickedItem = packetBuffer.readItemStack();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.slotId);
        packetBuffer.writeByte(this.packedClickData);
        packetBuffer.writeShort(this.actionNumber);
        packetBuffer.writeEnumValue(this.mode);
        packetBuffer.writeItemStack(this.clickedItem);
    }

    public int getWindowId() {
        return this.windowId;
    }

    public int getSlotId() {
        return this.slotId;
    }

    public int getUsedButton() {
        return this.packedClickData;
    }

    public short getActionNumber() {
        return this.actionNumber;
    }

    public ItemStack getClickedItem() {
        return this.clickedItem;
    }

    public ClickType getClickType() {
        return this.mode;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

