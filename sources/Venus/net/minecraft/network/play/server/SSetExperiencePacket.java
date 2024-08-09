/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SSetExperiencePacket
implements IPacket<IClientPlayNetHandler> {
    private float experienceBar;
    private int totalExperience;
    private int level;

    public SSetExperiencePacket() {
    }

    public SSetExperiencePacket(float f, int n, int n2) {
        this.experienceBar = f;
        this.totalExperience = n;
        this.level = n2;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.experienceBar = packetBuffer.readFloat();
        this.level = packetBuffer.readVarInt();
        this.totalExperience = packetBuffer.readVarInt();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat(this.experienceBar);
        packetBuffer.writeVarInt(this.level);
        packetBuffer.writeVarInt(this.totalExperience);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleSetExperience(this);
    }

    public float getExperienceBar() {
        return this.experienceBar;
    }

    public int getTotalExperience() {
        return this.totalExperience;
    }

    public int getLevel() {
        return this.level;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

