/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.text.ITextComponent;

public class SScoreboardObjectivePacket
implements IPacket<IClientPlayNetHandler> {
    private String objectiveName;
    private ITextComponent displayName;
    private ScoreCriteria.RenderType renderType;
    private int action;

    public SScoreboardObjectivePacket() {
    }

    public SScoreboardObjectivePacket(ScoreObjective scoreObjective, int n) {
        this.objectiveName = scoreObjective.getName();
        this.displayName = scoreObjective.getDisplayName();
        this.renderType = scoreObjective.getRenderType();
        this.action = n;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.objectiveName = packetBuffer.readString(16);
        this.action = packetBuffer.readByte();
        if (this.action == 0 || this.action == 2) {
            this.displayName = packetBuffer.readTextComponent();
            this.renderType = packetBuffer.readEnumValue(ScoreCriteria.RenderType.class);
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.objectiveName);
        packetBuffer.writeByte(this.action);
        if (this.action == 0 || this.action == 2) {
            packetBuffer.writeTextComponent(this.displayName);
            packetBuffer.writeEnumValue(this.renderType);
        }
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleScoreboardObjective(this);
    }

    public String getObjectiveName() {
        return this.objectiveName;
    }

    public ITextComponent getDisplayName() {
        return this.displayName;
    }

    public int getAction() {
        return this.action;
    }

    public ScoreCriteria.RenderType getRenderType() {
        return this.renderType;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

