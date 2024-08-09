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

public class SPlaySoundEventPacket
implements IPacket<IClientPlayNetHandler> {
    private int soundType;
    private BlockPos soundPos;
    private int soundData;
    private boolean serverWide;

    public SPlaySoundEventPacket() {
    }

    public SPlaySoundEventPacket(int n, BlockPos blockPos, int n2, boolean bl) {
        this.soundType = n;
        this.soundPos = blockPos.toImmutable();
        this.soundData = n2;
        this.serverWide = bl;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.soundType = packetBuffer.readInt();
        this.soundPos = packetBuffer.readBlockPos();
        this.soundData = packetBuffer.readInt();
        this.serverWide = packetBuffer.readBoolean();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.soundType);
        packetBuffer.writeBlockPos(this.soundPos);
        packetBuffer.writeInt(this.soundData);
        packetBuffer.writeBoolean(this.serverWide);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleEffect(this);
    }

    public boolean isSoundServerwide() {
        return this.serverWide;
    }

    public int getSoundType() {
        return this.soundType;
    }

    public int getSoundData() {
        return this.soundData;
    }

    public BlockPos getSoundPos() {
        return this.soundPos;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

