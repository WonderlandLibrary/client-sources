/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CEditBookPacket
implements IPacket<IServerPlayNetHandler> {
    private ItemStack stack;
    private boolean updateAll;
    private int field_244707_c;

    public CEditBookPacket() {
    }

    public CEditBookPacket(ItemStack itemStack, boolean bl, int n) {
        this.stack = itemStack.copy();
        this.updateAll = bl;
        this.field_244707_c = n;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.stack = packetBuffer.readItemStack();
        this.updateAll = packetBuffer.readBoolean();
        this.field_244707_c = packetBuffer.readVarInt();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeItemStack(this.stack);
        packetBuffer.writeBoolean(this.updateAll);
        packetBuffer.writeVarInt(this.field_244707_c);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processEditBook(this);
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public boolean shouldUpdateAll() {
        return this.updateAll;
    }

    public int func_244708_d() {
        return this.field_244707_c;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

