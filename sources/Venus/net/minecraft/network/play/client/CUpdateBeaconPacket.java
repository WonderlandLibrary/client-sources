/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CUpdateBeaconPacket
implements IPacket<IServerPlayNetHandler> {
    private int primaryEffect;
    private int secondaryEffect;

    public CUpdateBeaconPacket() {
    }

    public CUpdateBeaconPacket(int n, int n2) {
        this.primaryEffect = n;
        this.secondaryEffect = n2;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.primaryEffect = packetBuffer.readVarInt();
        this.secondaryEffect = packetBuffer.readVarInt();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.primaryEffect);
        packetBuffer.writeVarInt(this.secondaryEffect);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processUpdateBeacon(this);
    }

    public int getPrimaryEffect() {
        return this.primaryEffect;
    }

    public int getSecondaryEffect() {
        return this.secondaryEffect;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

