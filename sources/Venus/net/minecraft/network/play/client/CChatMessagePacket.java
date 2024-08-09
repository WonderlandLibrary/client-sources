/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CChatMessagePacket
implements IPacket<IServerPlayNetHandler> {
    private String message;

    public CChatMessagePacket() {
    }

    public CChatMessagePacket(String string) {
        if (string.length() > 256) {
            string = string.substring(0, 256);
        }
        this.message = string;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.message = packetBuffer.readString(256);
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.message);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processChatMessage(this);
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

