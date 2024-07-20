/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketResourcePackStatus
implements Packet<INetHandlerPlayServer> {
    private Action action;

    public CPacketResourcePackStatus() {
    }

    public CPacketResourcePackStatus(Action p_i47156_1_) {
        this.action = p_i47156_1_;
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.action = buf.readEnumValue(Action.class);
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeEnumValue(this.action);
    }

    @Override
    public void processPacket(INetHandlerPlayServer handler) {
        handler.handleResourcePackStatus(this);
    }

    public static enum Action {
        SUCCESSFULLY_LOADED,
        DECLINED,
        FAILED_DOWNLOAD,
        ACCEPTED;

    }
}

