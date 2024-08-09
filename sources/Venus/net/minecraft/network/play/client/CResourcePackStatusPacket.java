/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CResourcePackStatusPacket
implements IPacket<IServerPlayNetHandler> {
    private Action action;

    public CResourcePackStatusPacket() {
    }

    public CResourcePackStatusPacket(Action action) {
        this.action = action;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.action = packetBuffer.readEnumValue(Action.class);
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.action);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.handleResourcePackStatus(this);
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }

    public static enum Action {
        SUCCESSFULLY_LOADED,
        DECLINED,
        FAILED_DOWNLOAD,
        ACCEPTED;

    }
}

