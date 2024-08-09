/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.util.ResourceLocation;

public class CCustomPayloadPacket
implements IPacket<IServerPlayNetHandler> {
    public static final ResourceLocation BRAND = new ResourceLocation("brand");
    private ResourceLocation channel;
    private PacketBuffer data;

    public CCustomPayloadPacket() {
    }

    public CCustomPayloadPacket(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
        this.channel = resourceLocation;
        this.data = packetBuffer;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.channel = packetBuffer.readResourceLocation();
        int n = packetBuffer.readableBytes();
        if (n < 0 || n > Short.MAX_VALUE) {
            throw new IOException("Payload may not be larger than 32767 bytes");
        }
        this.data = new PacketBuffer(packetBuffer.readBytes(n));
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeResourceLocation(this.channel);
        packetBuffer.writeBytes(this.data);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processCustomPayload(this);
        if (this.data != null) {
            this.data.release();
        }
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

