/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.status.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.IServerStatusNetHandler;

public class CServerQueryPacket
implements IPacket<IServerStatusNetHandler> {
    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
    }

    @Override
    public void processPacket(IServerStatusNetHandler iServerStatusNetHandler) {
        iServerStatusNetHandler.processServerQuery(this);
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerStatusNetHandler)iNetHandler);
    }
}

