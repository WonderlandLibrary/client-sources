/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CClientStatusPacket
implements IPacket<IServerPlayNetHandler> {
    private State status;

    public CClientStatusPacket() {
    }

    public CClientStatusPacket(State state) {
        this.status = state;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.status = packetBuffer.readEnumValue(State.class);
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.status);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processClientStatus(this);
    }

    public State getStatus() {
        return this.status;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }

    public static enum State {
        PERFORM_RESPAWN,
        REQUEST_STATS;

    }
}

