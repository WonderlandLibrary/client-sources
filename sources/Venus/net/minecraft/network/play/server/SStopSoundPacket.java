/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

public class SStopSoundPacket
implements IPacket<IClientPlayNetHandler> {
    private ResourceLocation name;
    private SoundCategory category;

    public SStopSoundPacket() {
    }

    public SStopSoundPacket(@Nullable ResourceLocation resourceLocation, @Nullable SoundCategory soundCategory) {
        this.name = resourceLocation;
        this.category = soundCategory;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        byte by = packetBuffer.readByte();
        if ((by & 1) > 0) {
            this.category = packetBuffer.readEnumValue(SoundCategory.class);
        }
        if ((by & 2) > 0) {
            this.name = packetBuffer.readResourceLocation();
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        if (this.category != null) {
            if (this.name != null) {
                packetBuffer.writeByte(3);
                packetBuffer.writeEnumValue(this.category);
                packetBuffer.writeResourceLocation(this.name);
            } else {
                packetBuffer.writeByte(1);
                packetBuffer.writeEnumValue(this.category);
            }
        } else if (this.name != null) {
            packetBuffer.writeByte(2);
            packetBuffer.writeResourceLocation(this.name);
        } else {
            packetBuffer.writeByte(0);
        }
    }

    @Nullable
    public ResourceLocation getName() {
        return this.name;
    }

    @Nullable
    public SoundCategory getCategory() {
        return this.category;
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleStopSound(this);
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

