/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.item.Item;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SCooldownPacket
implements IPacket<IClientPlayNetHandler> {
    private Item item;
    private int ticks;

    public SCooldownPacket() {
    }

    public SCooldownPacket(Item item, int n) {
        this.item = item;
        this.ticks = n;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.item = Item.getItemById(packetBuffer.readVarInt());
        this.ticks = packetBuffer.readVarInt();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(Item.getIdFromItem(this.item));
        packetBuffer.writeVarInt(this.ticks);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleCooldown(this);
    }

    public Item getItem() {
        return this.item;
    }

    public int getTicks() {
        return this.ticks;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

