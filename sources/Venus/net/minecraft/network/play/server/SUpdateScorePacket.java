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
import net.minecraft.scoreboard.ServerScoreboard;

public class SUpdateScorePacket
implements IPacket<IClientPlayNetHandler> {
    private String name = "";
    @Nullable
    private String objective;
    private int value;
    private ServerScoreboard.Action action;

    public SUpdateScorePacket() {
    }

    public SUpdateScorePacket(ServerScoreboard.Action action, @Nullable String string, String string2, int n) {
        if (action != ServerScoreboard.Action.REMOVE && string == null) {
            throw new IllegalArgumentException("Need an objective name");
        }
        this.name = string2;
        this.objective = string;
        this.value = n;
        this.action = action;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.name = packetBuffer.readString(40);
        this.action = packetBuffer.readEnumValue(ServerScoreboard.Action.class);
        String string = packetBuffer.readString(16);
        String string2 = this.objective = Objects.equals(string, "") ? null : string;
        if (this.action != ServerScoreboard.Action.REMOVE) {
            this.value = packetBuffer.readVarInt();
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.name);
        packetBuffer.writeEnumValue(this.action);
        packetBuffer.writeString(this.objective == null ? "" : this.objective);
        if (this.action != ServerScoreboard.Action.REMOVE) {
            packetBuffer.writeVarInt(this.value);
        }
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleUpdateScore(this);
    }

    public String getPlayerName() {
        return this.name;
    }

    @Nullable
    public String getObjectiveName() {
        return this.objective;
    }

    public int getScoreValue() {
        return this.value;
    }

    public ServerScoreboard.Action getAction() {
        return this.action;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

