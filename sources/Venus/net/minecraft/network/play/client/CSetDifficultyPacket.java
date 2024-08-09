/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.world.Difficulty;

public class CSetDifficultyPacket
implements IPacket<IServerPlayNetHandler> {
    private Difficulty field_218774_a;

    public CSetDifficultyPacket() {
    }

    public CSetDifficultyPacket(Difficulty difficulty) {
        this.field_218774_a = difficulty;
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.func_217263_a(this);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.field_218774_a = Difficulty.byId(packetBuffer.readUnsignedByte());
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.field_218774_a.getId());
    }

    public Difficulty func_218773_b() {
        return this.field_218774_a;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

