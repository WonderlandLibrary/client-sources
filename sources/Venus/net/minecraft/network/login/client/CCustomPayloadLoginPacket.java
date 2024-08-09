/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.login.client;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.IServerLoginNetHandler;

public class CCustomPayloadLoginPacket
implements IPacket<IServerLoginNetHandler> {
    private int transaction;
    private PacketBuffer payload;

    public CCustomPayloadLoginPacket() {
    }

    public CCustomPayloadLoginPacket(int n, @Nullable PacketBuffer packetBuffer) {
        this.transaction = n;
        this.payload = packetBuffer;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.transaction = packetBuffer.readVarInt();
        if (packetBuffer.readBoolean()) {
            int n = packetBuffer.readableBytes();
            if (n < 0 || n > 0x100000) {
                throw new IOException("Payload may not be larger than 1048576 bytes");
            }
            this.payload = new PacketBuffer(packetBuffer.readBytes(n));
        } else {
            this.payload = null;
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.transaction);
        if (this.payload != null) {
            packetBuffer.writeBoolean(false);
            packetBuffer.writeBytes(this.payload.copy());
        } else {
            packetBuffer.writeBoolean(true);
        }
    }

    @Override
    public void processPacket(IServerLoginNetHandler iServerLoginNetHandler) {
        iServerLoginNetHandler.processCustomPayloadLogin(this);
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerLoginNetHandler)iNetHandler);
    }
}

