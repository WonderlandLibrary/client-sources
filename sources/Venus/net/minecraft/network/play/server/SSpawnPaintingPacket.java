/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class SSpawnPaintingPacket
implements IPacket<IClientPlayNetHandler> {
    private int entityID;
    private UUID uniqueId;
    private BlockPos position;
    private Direction facing;
    private int title;

    public SSpawnPaintingPacket() {
    }

    public SSpawnPaintingPacket(PaintingEntity paintingEntity) {
        this.entityID = paintingEntity.getEntityId();
        this.uniqueId = paintingEntity.getUniqueID();
        this.position = paintingEntity.getHangingPosition();
        this.facing = paintingEntity.getHorizontalFacing();
        this.title = Registry.MOTIVE.getId(paintingEntity.art);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarInt();
        this.uniqueId = packetBuffer.readUniqueId();
        this.title = packetBuffer.readVarInt();
        this.position = packetBuffer.readBlockPos();
        this.facing = Direction.byHorizontalIndex(packetBuffer.readUnsignedByte());
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityID);
        packetBuffer.writeUniqueId(this.uniqueId);
        packetBuffer.writeVarInt(this.title);
        packetBuffer.writeBlockPos(this.position);
        packetBuffer.writeByte(this.facing.getHorizontalIndex());
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleSpawnPainting(this);
    }

    public int getEntityID() {
        return this.entityID;
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public Direction getFacing() {
        return this.facing;
    }

    public PaintingType getType() {
        return Registry.MOTIVE.getByValue(this.title);
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

