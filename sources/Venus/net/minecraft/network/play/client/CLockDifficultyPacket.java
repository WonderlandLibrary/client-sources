/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CLockDifficultyPacket
implements IPacket<IServerPlayNetHandler> {
    private boolean field_218777_a;

    public CLockDifficultyPacket() {
    }

    public CLockDifficultyPacket(boolean bl) {
        this.field_218777_a = bl;
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.func_217261_a(this);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.field_218777_a = packetBuffer.readBoolean();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBoolean(this.field_218777_a);
    }

    public boolean func_218776_b() {
        return this.field_218777_a;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

