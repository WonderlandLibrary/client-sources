/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SQueryNBTResponsePacket
implements IPacket<IClientPlayNetHandler> {
    private int transactionId;
    @Nullable
    private CompoundNBT tag;

    public SQueryNBTResponsePacket() {
    }

    public SQueryNBTResponsePacket(int n, @Nullable CompoundNBT compoundNBT) {
        this.transactionId = n;
        this.tag = compoundNBT;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.transactionId = packetBuffer.readVarInt();
        this.tag = packetBuffer.readCompoundTag();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.transactionId);
        packetBuffer.writeCompoundTag(this.tag);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleNBTQueryResponse(this);
    }

    public int getTransactionId() {
        return this.transactionId;
    }

    @Nullable
    public CompoundNBT getTag() {
        return this.tag;
    }

    @Override
    public boolean shouldSkipErrors() {
        return false;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

