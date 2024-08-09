/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SDestroyEntitiesPacket
implements IPacket<IClientPlayNetHandler> {
    private int[] entityIDs;

    public SDestroyEntitiesPacket() {
    }

    public SDestroyEntitiesPacket(int ... nArray) {
        this.entityIDs = nArray;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityIDs = new int[packetBuffer.readVarInt()];
        for (int i = 0; i < this.entityIDs.length; ++i) {
            this.entityIDs[i] = packetBuffer.readVarInt();
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityIDs.length);
        for (int n : this.entityIDs) {
            packetBuffer.writeVarInt(n);
        }
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleDestroyEntities(this);
    }

    public int[] getEntityIDs() {
        return this.entityIDs;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

