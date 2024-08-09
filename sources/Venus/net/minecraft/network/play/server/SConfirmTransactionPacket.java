/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SConfirmTransactionPacket
implements IPacket<IClientPlayNetHandler> {
    private int windowId;
    private short actionNumber;
    private boolean accepted;

    public SConfirmTransactionPacket() {
    }

    public SConfirmTransactionPacket(int n, short s, boolean bl) {
        this.windowId = n;
        this.actionNumber = s;
        this.accepted = bl;
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleConfirmTransaction(this);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readUnsignedByte();
        this.actionNumber = packetBuffer.readShort();
        this.accepted = packetBuffer.readBoolean();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.actionNumber);
        packetBuffer.writeBoolean(this.accepted);
    }

    public int getWindowId() {
        return this.windowId;
    }

    public short getActionNumber() {
        return this.actionNumber;
    }

    public boolean wasAccepted() {
        return this.accepted;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

