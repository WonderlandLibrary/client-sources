/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;

public class SPlaySoundPacket
implements IPacket<IClientPlayNetHandler> {
    private ResourceLocation soundName;
    private SoundCategory category;
    private int x;
    private int y = Integer.MAX_VALUE;
    private int z;
    private float volume;
    private float pitch;

    public SPlaySoundPacket() {
    }

    public SPlaySoundPacket(ResourceLocation resourceLocation, SoundCategory soundCategory, Vector3d vector3d, float f, float f2) {
        this.soundName = resourceLocation;
        this.category = soundCategory;
        this.x = (int)(vector3d.x * 8.0);
        this.y = (int)(vector3d.y * 8.0);
        this.z = (int)(vector3d.z * 8.0);
        this.volume = f;
        this.pitch = f2;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.soundName = packetBuffer.readResourceLocation();
        this.category = packetBuffer.readEnumValue(SoundCategory.class);
        this.x = packetBuffer.readInt();
        this.y = packetBuffer.readInt();
        this.z = packetBuffer.readInt();
        this.volume = packetBuffer.readFloat();
        this.pitch = packetBuffer.readFloat();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeResourceLocation(this.soundName);
        packetBuffer.writeEnumValue(this.category);
        packetBuffer.writeInt(this.x);
        packetBuffer.writeInt(this.y);
        packetBuffer.writeInt(this.z);
        packetBuffer.writeFloat(this.volume);
        packetBuffer.writeFloat(this.pitch);
    }

    public ResourceLocation getSoundName() {
        return this.soundName;
    }

    public SoundCategory getCategory() {
        return this.category;
    }

    public double getX() {
        return (float)this.x / 8.0f;
    }

    public double getY() {
        return (float)this.y / 8.0f;
    }

    public double getZ() {
        return (float)this.z / 8.0f;
    }

    public float getVolume() {
        return this.volume;
    }

    public float getPitch() {
        return this.pitch;
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleCustomSound(this);
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

