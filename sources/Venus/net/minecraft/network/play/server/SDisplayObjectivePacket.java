/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.scoreboard.ScoreObjective;

public class SDisplayObjectivePacket
implements IPacket<IClientPlayNetHandler> {
    private int position;
    private String scoreName;

    public SDisplayObjectivePacket() {
    }

    public SDisplayObjectivePacket(int n, @Nullable ScoreObjective scoreObjective) {
        this.position = n;
        this.scoreName = scoreObjective == null ? "" : scoreObjective.getName();
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.position = packetBuffer.readByte();
        this.scoreName = packetBuffer.readString(16);
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.position);
        packetBuffer.writeString(this.scoreName);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleDisplayObjective(this);
    }

    public int getPosition() {
        return this.position;
    }

    @Nullable
    public String getName() {
        return Objects.equals(this.scoreName, "") ? null : this.scoreName;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

