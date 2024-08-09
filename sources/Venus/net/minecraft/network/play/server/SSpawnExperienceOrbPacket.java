/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SSpawnExperienceOrbPacket
implements IPacket<IClientPlayNetHandler> {
    private int entityID;
    private double posX;
    private double posY;
    private double posZ;
    private int xpValue;

    public SSpawnExperienceOrbPacket() {
    }

    public SSpawnExperienceOrbPacket(ExperienceOrbEntity experienceOrbEntity) {
        this.entityID = experienceOrbEntity.getEntityId();
        this.posX = experienceOrbEntity.getPosX();
        this.posY = experienceOrbEntity.getPosY();
        this.posZ = experienceOrbEntity.getPosZ();
        this.xpValue = experienceOrbEntity.getXpValue();
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarInt();
        this.posX = packetBuffer.readDouble();
        this.posY = packetBuffer.readDouble();
        this.posZ = packetBuffer.readDouble();
        this.xpValue = packetBuffer.readShort();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityID);
        packetBuffer.writeDouble(this.posX);
        packetBuffer.writeDouble(this.posY);
        packetBuffer.writeDouble(this.posZ);
        packetBuffer.writeShort(this.xpValue);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleSpawnExperienceOrb(this);
    }

    public int getEntityID() {
        return this.entityID;
    }

    public double getX() {
        return this.posX;
    }

    public double getY() {
        return this.posY;
    }

    public double getZ() {
        return this.posZ;
    }

    public int getXPValue() {
        return this.xpValue;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

