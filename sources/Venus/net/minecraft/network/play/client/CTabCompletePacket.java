/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CTabCompletePacket
implements IPacket<IServerPlayNetHandler> {
    private int transactionId;
    private String command;

    public CTabCompletePacket() {
    }

    public CTabCompletePacket(int n, String string) {
        this.transactionId = n;
        this.command = string;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.transactionId = packetBuffer.readVarInt();
        this.command = packetBuffer.readString(32500);
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.transactionId);
        packetBuffer.writeString(this.command, 32500);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processTabComplete(this);
    }

    public int getTransactionId() {
        return this.transactionId;
    }

    public String getCommand() {
        return this.command;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

