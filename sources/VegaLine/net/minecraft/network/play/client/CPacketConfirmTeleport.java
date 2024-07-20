/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketConfirmTeleport
implements Packet<INetHandlerPlayServer> {
    private int telportId;

    public CPacketConfirmTeleport() {
    }

    public CPacketConfirmTeleport(int teleportIdIn) {
        this.telportId = teleportIdIn;
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.telportId = buf.readVarIntFromBuffer();
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeVarIntToBuffer(this.telportId);
    }

    @Override
    public void processPacket(INetHandlerPlayServer handler) {
        handler.processConfirmTeleport(this);
    }

    public int getTeleportId() {
        return this.telportId;
    }
}

