/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SCollectItemPacket
implements IPacket<IClientPlayNetHandler> {
    private int collectedItemEntityId;
    private int entityId;
    private int collectedQuantity;

    public SCollectItemPacket() {
    }

    public SCollectItemPacket(int n, int n2, int n3) {
        this.collectedItemEntityId = n;
        this.entityId = n2;
        this.collectedQuantity = n3;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.collectedItemEntityId = packetBuffer.readVarInt();
        this.entityId = packetBuffer.readVarInt();
        this.collectedQuantity = packetBuffer.readVarInt();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.collectedItemEntityId);
        packetBuffer.writeVarInt(this.entityId);
        packetBuffer.writeVarInt(this.collectedQuantity);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleCollectItem(this);
    }

    public int getCollectedItemEntityID() {
        return this.collectedItemEntityId;
    }

    public int getEntityID() {
        return this.entityId;
    }

    public int getAmount() {
        return this.collectedQuantity;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

