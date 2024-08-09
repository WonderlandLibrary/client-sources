/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SSetPassengersPacket
implements IPacket<IClientPlayNetHandler> {
    private int entityId;
    private int[] passengerIds;

    public SSetPassengersPacket() {
    }

    public SSetPassengersPacket(Entity entity2) {
        this.entityId = entity2.getEntityId();
        List<Entity> list = entity2.getPassengers();
        this.passengerIds = new int[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            this.passengerIds[i] = list.get(i).getEntityId();
        }
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarInt();
        this.passengerIds = packetBuffer.readVarIntArray();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityId);
        packetBuffer.writeVarIntArray(this.passengerIds);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleSetPassengers(this);
    }

    public int[] getPassengerIds() {
        return this.passengerIds;
    }

    public int getEntityId() {
        return this.entityId;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

