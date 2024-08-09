/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.util.math.BlockPos;

public class CUpdateSignPacket
implements IPacket<IServerPlayNetHandler> {
    private BlockPos pos;
    private String[] lines;

    public CUpdateSignPacket() {
    }

    public CUpdateSignPacket(BlockPos blockPos, String string, String string2, String string3, String string4) {
        this.pos = blockPos;
        this.lines = new String[]{string, string2, string3, string4};
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.pos = packetBuffer.readBlockPos();
        this.lines = new String[4];
        for (int i = 0; i < 4; ++i) {
            this.lines[i] = packetBuffer.readString(384);
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.pos);
        for (int i = 0; i < 4; ++i) {
            packetBuffer.writeString(this.lines[i]);
        }
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processUpdateSign(this);
    }

    public BlockPos getPosition() {
        return this.pos;
    }

    public String[] getLines() {
        return this.lines;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

