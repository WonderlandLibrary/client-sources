/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CSteerBoatPacket
implements IPacket<IServerPlayNetHandler> {
    private boolean left;
    private boolean right;

    public CSteerBoatPacket() {
    }

    public CSteerBoatPacket(boolean bl, boolean bl2) {
        this.left = bl;
        this.right = bl2;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.left = packetBuffer.readBoolean();
        this.right = packetBuffer.readBoolean();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBoolean(this.left);
        packetBuffer.writeBoolean(this.right);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processSteerBoat(this);
    }

    public boolean getLeft() {
        return this.left;
    }

    public boolean getRight() {
        return this.right;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

