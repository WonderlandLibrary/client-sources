/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.Difficulty;

public class SServerDifficultyPacket
implements IPacket<IClientPlayNetHandler> {
    private Difficulty difficulty;
    private boolean difficultyLocked;

    public SServerDifficultyPacket() {
    }

    public SServerDifficultyPacket(Difficulty difficulty, boolean bl) {
        this.difficulty = difficulty;
        this.difficultyLocked = bl;
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleServerDifficulty(this);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.difficulty = Difficulty.byId(packetBuffer.readUnsignedByte());
        this.difficultyLocked = packetBuffer.readBoolean();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.difficulty.getId());
        packetBuffer.writeBoolean(this.difficultyLocked);
    }

    public boolean isDifficultyLocked() {
        return this.difficultyLocked;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

