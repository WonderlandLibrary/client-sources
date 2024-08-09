/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;

public class SPlayEntityEffectPacket
implements IPacket<IClientPlayNetHandler> {
    private int entityId;
    private byte effectId;
    private byte amplifier;
    private int duration;
    private byte flags;

    public SPlayEntityEffectPacket() {
    }

    public SPlayEntityEffectPacket(int n, EffectInstance effectInstance) {
        this.entityId = n;
        this.effectId = (byte)(Effect.getId(effectInstance.getPotion()) & 0xFF);
        this.amplifier = (byte)(effectInstance.getAmplifier() & 0xFF);
        this.duration = effectInstance.getDuration() > Short.MAX_VALUE ? Short.MAX_VALUE : effectInstance.getDuration();
        this.flags = 0;
        if (effectInstance.isAmbient()) {
            this.flags = (byte)(this.flags | 1);
        }
        if (effectInstance.doesShowParticles()) {
            this.flags = (byte)(this.flags | 2);
        }
        if (effectInstance.isShowIcon()) {
            this.flags = (byte)(this.flags | 4);
        }
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarInt();
        this.effectId = packetBuffer.readByte();
        this.amplifier = packetBuffer.readByte();
        this.duration = packetBuffer.readVarInt();
        this.flags = packetBuffer.readByte();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityId);
        packetBuffer.writeByte(this.effectId);
        packetBuffer.writeByte(this.amplifier);
        packetBuffer.writeVarInt(this.duration);
        packetBuffer.writeByte(this.flags);
    }

    public boolean isMaxDuration() {
        return this.duration == Short.MAX_VALUE;
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleEntityEffect(this);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public byte getEffectId() {
        return this.effectId;
    }

    public byte getAmplifier() {
        return this.amplifier;
    }

    public int getDuration() {
        return this.duration;
    }

    public boolean doesShowParticles() {
        return (this.flags & 2) == 2;
    }

    public boolean getIsAmbient() {
        return (this.flags & 1) == 1;
    }

    public boolean shouldShowIcon() {
        return (this.flags & 4) == 4;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

