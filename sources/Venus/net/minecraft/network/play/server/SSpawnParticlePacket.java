/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.registry.Registry;

public class SSpawnParticlePacket
implements IPacket<IClientPlayNetHandler> {
    private double xCoord;
    private double yCoord;
    private double zCoord;
    private float xOffset;
    private float yOffset;
    private float zOffset;
    private float particleSpeed;
    private int particleCount;
    private boolean longDistance;
    private IParticleData particle;

    public SSpawnParticlePacket() {
    }

    public <T extends IParticleData> SSpawnParticlePacket(T t, boolean bl, double d, double d2, double d3, float f, float f2, float f3, float f4, int n) {
        this.particle = t;
        this.longDistance = bl;
        this.xCoord = d;
        this.yCoord = d2;
        this.zCoord = d3;
        this.xOffset = f;
        this.yOffset = f2;
        this.zOffset = f3;
        this.particleSpeed = f4;
        this.particleCount = n;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        ParticleType particleType = (ParticleType)Registry.PARTICLE_TYPE.getByValue(packetBuffer.readInt());
        if (particleType == null) {
            particleType = ParticleTypes.BARRIER;
        }
        this.longDistance = packetBuffer.readBoolean();
        this.xCoord = packetBuffer.readDouble();
        this.yCoord = packetBuffer.readDouble();
        this.zCoord = packetBuffer.readDouble();
        this.xOffset = packetBuffer.readFloat();
        this.yOffset = packetBuffer.readFloat();
        this.zOffset = packetBuffer.readFloat();
        this.particleSpeed = packetBuffer.readFloat();
        this.particleCount = packetBuffer.readInt();
        this.particle = this.readParticle(packetBuffer, particleType);
    }

    private <T extends IParticleData> T readParticle(PacketBuffer packetBuffer, ParticleType<T> particleType) {
        return particleType.getDeserializer().read(particleType, packetBuffer);
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(Registry.PARTICLE_TYPE.getId(this.particle.getType()));
        packetBuffer.writeBoolean(this.longDistance);
        packetBuffer.writeDouble(this.xCoord);
        packetBuffer.writeDouble(this.yCoord);
        packetBuffer.writeDouble(this.zCoord);
        packetBuffer.writeFloat(this.xOffset);
        packetBuffer.writeFloat(this.yOffset);
        packetBuffer.writeFloat(this.zOffset);
        packetBuffer.writeFloat(this.particleSpeed);
        packetBuffer.writeInt(this.particleCount);
        this.particle.write(packetBuffer);
    }

    public boolean isLongDistance() {
        return this.longDistance;
    }

    public double getXCoordinate() {
        return this.xCoord;
    }

    public double getYCoordinate() {
        return this.yCoord;
    }

    public double getZCoordinate() {
        return this.zCoord;
    }

    public float getXOffset() {
        return this.xOffset;
    }

    public float getYOffset() {
        return this.yOffset;
    }

    public float getZOffset() {
        return this.zOffset;
    }

    public float getParticleSpeed() {
        return this.particleSpeed;
    }

    public int getParticleCount() {
        return this.particleCount;
    }

    public IParticleData getParticle() {
        return this.particle;
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleParticles(this);
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

