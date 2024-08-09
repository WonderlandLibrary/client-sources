/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class SWorldSpawnChangedPacket
implements IPacket<IClientPlayNetHandler> {
    private BlockPos field_240831_a_;
    private float field_244312_b;

    public SWorldSpawnChangedPacket() {
    }

    public SWorldSpawnChangedPacket(BlockPos blockPos, float f) {
        this.field_240831_a_ = blockPos;
        this.field_244312_b = f;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.field_240831_a_ = packetBuffer.readBlockPos();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.field_240831_a_);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.func_230488_a_(this);
    }

    public BlockPos func_240832_b_() {
        return this.field_240831_a_;
    }

    public float func_244313_c() {
        return this.field_244312_b;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

