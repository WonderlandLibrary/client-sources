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

public class SEntityStatusPacket
implements IPacket<IClientPlayNetHandler> {
    public int entityId;
    private byte logicOpcode;

    public SEntityStatusPacket() {
    }

    public SEntityStatusPacket(Entity entity2, byte by) {
        this.entityId = entity2.getEntityId();
        this.logicOpcode = by;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readInt();
        this.logicOpcode = packetBuffer.readByte();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.entityId);
        packetBuffer.writeByte(this.logicOpcode);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleEntityStatus(this);
    }

    public Entity getEntity(World world) {
        return world.getEntityByID(this.entityId);
    }

    public byte getOpCode() {
        return this.logicOpcode;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

