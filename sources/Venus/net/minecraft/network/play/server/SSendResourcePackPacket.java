/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SSendResourcePackPacket
implements IPacket<IClientPlayNetHandler> {
    private String url;
    private String hash;

    public SSendResourcePackPacket() {
    }

    public SSendResourcePackPacket(String string, String string2) {
        this.url = string;
        this.hash = string2;
        if (string2.length() > 40) {
            throw new IllegalArgumentException("Hash is too long (max 40, was " + string2.length() + ")");
        }
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.url = packetBuffer.readString(Short.MAX_VALUE);
        this.hash = packetBuffer.readString(40);
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.url);
        packetBuffer.writeString(this.hash);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleResourcePack(this);
    }

    public String getURL() {
        return this.url;
    }

    public String getHash() {
        return this.hash;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

