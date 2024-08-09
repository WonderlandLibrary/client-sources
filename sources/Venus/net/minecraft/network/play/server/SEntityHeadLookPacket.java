/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class SEntityHeadLookPacket
implements IPacket<IClientPlayNetHandler> {
    private int entityId;
    private byte yaw;

    public SEntityHeadLookPacket() {
    }

    public SEntityHeadLookPacket(Entity entity2, byte by) {
        this.entityId = entity2.getEntityId();
        this.yaw = by;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarInt();
        this.yaw = packetBuffer.readByte();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityId);
        packetBuffer.writeByte(this.yaw);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleEntityHeadLook(this);
    }

    public Entity getEntity(World world) {
        return world.getEntityByID(this.entityId);
    }

    public byte getYaw() {
        return this.yaw;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

