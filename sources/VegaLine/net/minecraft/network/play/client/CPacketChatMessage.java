/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketChatMessage
implements Packet<INetHandlerPlayServer> {
    private String message;

    public CPacketChatMessage() {
    }

    public CPacketChatMessage(String messageIn) {
        if (messageIn.length() > 256) {
            messageIn = messageIn.substring(0, 256);
        }
        this.message = messageIn;
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.message = buf.readStringFromBuffer(256);
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeString(this.message);
    }

    @Override
    public void processPacket(INetHandlerPlayServer handler) {
        handler.processChatMessage(this);
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String massage) {
        this.message = massage;
    }

    public void appendMessage(String append) {
        this.message = this.message + append;
    }
}

