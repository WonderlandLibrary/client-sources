/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CRenameItemPacket
implements IPacket<IServerPlayNetHandler> {
    private String name;

    public CRenameItemPacket() {
    }

    public CRenameItemPacket(String string) {
        this.name = string;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.name = packetBuffer.readString(Short.MAX_VALUE);
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.name);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processRenameItem(this);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

