/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;

public class CPlayerTryUseItemOnBlockPacket
implements IPacket<IServerPlayNetHandler> {
    private BlockRayTraceResult field_218795_a;
    private Hand hand;

    public CPlayerTryUseItemOnBlockPacket() {
    }

    public CPlayerTryUseItemOnBlockPacket(Hand hand, BlockRayTraceResult blockRayTraceResult) {
        this.hand = hand;
        this.field_218795_a = blockRayTraceResult;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.hand = packetBuffer.readEnumValue(Hand.class);
        this.field_218795_a = packetBuffer.readBlockRay();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.hand);
        packetBuffer.writeBlockRay(this.field_218795_a);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processTryUseItemOnBlock(this);
    }

    public Hand getHand() {
        return this.hand;
    }

    public BlockRayTraceResult func_218794_c() {
        return this.field_218795_a;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

