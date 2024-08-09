/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.Validate;

public class SPlaySoundEffectPacket
implements IPacket<IClientPlayNetHandler> {
    private SoundEvent sound;
    private SoundCategory category;
    private int posX;
    private int posY;
    private int posZ;
    private float soundVolume;
    private float soundPitch;

    public SPlaySoundEffectPacket() {
    }

    public SPlaySoundEffectPacket(SoundEvent soundEvent, SoundCategory soundCategory, double d, double d2, double d3, float f, float f2) {
        Validate.notNull(soundEvent, "sound", new Object[0]);
        this.sound = soundEvent;
        this.category = soundCategory;
        this.posX = (int)(d * 8.0);
        this.posY = (int)(d2 * 8.0);
        this.posZ = (int)(d3 * 8.0);
        this.soundVolume = f;
        this.soundPitch = f2;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.sound = (SoundEvent)Registry.SOUND_EVENT.getByValue(packetBuffer.readVarInt());
        this.category = packetBuffer.readEnumValue(SoundCategory.class);
        this.posX = packetBuffer.readInt();
        this.posY = packetBuffer.readInt();
        this.posZ = packetBuffer.readInt();
        this.soundVolume = packetBuffer.readFloat();
        this.soundPitch = packetBuffer.readFloat();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(Registry.SOUND_EVENT.getId(this.sound));
        packetBuffer.writeEnumValue(this.category);
        packetBuffer.writeInt(this.posX);
        packetBuffer.writeInt(this.posY);
        packetBuffer.writeInt(this.posZ);
        packetBuffer.writeFloat(this.soundVolume);
        packetBuffer.writeFloat(this.soundPitch);
    }

    public SoundEvent getSound() {
        return this.sound;
    }

    public SoundCategory getCategory() {
        return this.category;
    }

    public double getX() {
        return (float)this.posX / 8.0f;
    }

    public double getY() {
        return (float)this.posY / 8.0f;
    }

    public double getZ() {
        return (float)this.posZ / 8.0f;
    }

    public float getVolume() {
        return this.soundVolume;
    }

    public float getPitch() {
        return this.soundPitch;
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleSoundEffect(this);
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

