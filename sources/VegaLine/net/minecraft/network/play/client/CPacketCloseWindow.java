/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketCloseWindow
implements Packet<INetHandlerPlayServer> {
    public int windowId;

    public CPacketCloseWindow() {
    }

    public CPacketCloseWindow(int windowIdIn) {
        this.windowId = windowIdIn;
    }

    @Override
    public void processPacket(INetHandlerPlayServer handler) {
        handler.processCloseWindow(this);
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.windowId = buf.readByte();
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeByte(this.windowId);
    }
}

