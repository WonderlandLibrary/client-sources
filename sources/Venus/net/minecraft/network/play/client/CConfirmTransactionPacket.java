/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CConfirmTransactionPacket
implements IPacket<IServerPlayNetHandler> {
    private int windowId;
    private short uid;
    private boolean accepted;

    public CConfirmTransactionPacket() {
    }

    public CConfirmTransactionPacket(int n, short s, boolean bl) {
        this.windowId = n;
        this.uid = s;
        this.accepted = bl;
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processConfirmTransaction(this);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readByte();
        this.uid = packetBuffer.readShort();
        this.accepted = packetBuffer.readByte() != 0;
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.uid);
        packetBuffer.writeByte(this.accepted ? 1 : 0);
    }

    public int getWindowId() {
        return this.windowId;
    }

    public short getUid() {
        return this.uid;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

