/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketResourcePackSend
implements Packet<INetHandlerPlayClient> {
    private String url;
    private String hash;

    public SPacketResourcePackSend() {
    }

    public SPacketResourcePackSend(String urlIn, String hashIn) {
        this.url = urlIn;
        this.hash = hashIn;
        if (hashIn.length() > 40) {
            throw new IllegalArgumentException("Hash is too long (max 40, was " + hashIn.length() + ")");
        }
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.url = buf.readStringFromBuffer(Short.MAX_VALUE);
        this.hash = buf.readStringFromBuffer(40);
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeString(this.url);
        buf.writeString(this.hash);
    }

    @Override
    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleResourcePack(this);
    }

    public String getURL() {
        return this.url;
    }

    public String getHash() {
        return this.hash;
    }
}

