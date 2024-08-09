/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.login.server;

import java.io.IOException;
import net.minecraft.client.network.login.IClientLoginNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class SCustomPayloadLoginPacket
implements IPacket<IClientLoginNetHandler> {
    private int transaction;
    private ResourceLocation channel;
    private PacketBuffer payload;

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.transaction = packetBuffer.readVarInt();
        this.channel = packetBuffer.readResourceLocation();
        int n = packetBuffer.readableBytes();
        if (n < 0 || n > 0x100000) {
            throw new IOException("Payload may not be larger than 1048576 bytes");
        }
        this.payload = new PacketBuffer(packetBuffer.readBytes(n));
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.transaction);
        packetBuffer.writeResourceLocation(this.channel);
        packetBuffer.writeBytes(this.payload.copy());
    }

    @Override
    public void processPacket(IClientLoginNetHandler iClientLoginNetHandler) {
        iClientLoginNetHandler.handleCustomPayloadLogin(this);
    }

    public int getTransaction() {
        return this.transaction;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientLoginNetHandler)iNetHandler);
    }
}

