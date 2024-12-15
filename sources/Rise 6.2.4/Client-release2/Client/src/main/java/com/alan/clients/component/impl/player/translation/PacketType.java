package com.alan.clients.component.impl.player.translation;

import lombok.Getter;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.*;

import java.util.Map;
import java.util.function.BiFunction;

public enum PacketType {
    SPAWN_MOB(S0FPacketSpawnMob.class, (packet, entityPositions) -> {
        S0FPacketSpawnMob spawnMobPacket = (S0FPacketSpawnMob) packet;
        int entityId = spawnMobPacket.getEntityID();
        double posX = spawnMobPacket.getX() / 32.0;
        double posY = spawnMobPacket.getY() / 32.0;
        double posZ = spawnMobPacket.getZ() / 32.0;
        float yaw = (spawnMobPacket.getYaw() * 360) / 256.0F;
        float pitch = (spawnMobPacket.getPitch() * 360) / 256.0F;

        Position newPosition = new Position(posX, posY, posZ, yaw, pitch);
        entityPositions.put(entityId, newPosition);
        return entityPositions;
    }),

    ENTITY_TELEPORT(S18PacketEntityTeleport.class, (packet, entityPositions) -> {
        S18PacketEntityTeleport teleportPacket = (S18PacketEntityTeleport) packet;
        int entityId = teleportPacket.getEntityId();
        double posX = teleportPacket.getX() / 32.0;
        double posY = teleportPacket.getY() / 32.0;
        double posZ = teleportPacket.getZ() / 32.0;
        float yaw = (teleportPacket.getYaw() * 360) / 256.0F;
        float pitch = (teleportPacket.getPitch() * 360) / 256.0F;

        Position newPosition = new Position(posX, posY, posZ, yaw, pitch);
        entityPositions.put(entityId, newPosition);
        return entityPositions;
    }),

    ENTITY_REL_MOVE(S14PacketEntity.S15PacketEntityRelMove.class, (packet, entityPositions) -> {
        S14PacketEntity.S15PacketEntityRelMove relMovePacket = (S14PacketEntity.S15PacketEntityRelMove) packet;
        int entityId = relMovePacket.entityId;

        Position currentPosition = entityPositions.get(entityId);

        if (currentPosition != null) {
            double newPosX = currentPosition.posX + relMovePacket.getPosX() / 32.0;
            double newPosY = currentPosition.posY + relMovePacket.getPosY() / 32.0;
            double newPosZ = currentPosition.posZ + relMovePacket.getPosZ() / 32.0;
            float yaw = currentPosition.yaw;
            float pitch = currentPosition.pitch;

            Position newPosition = new Position(newPosX, newPosY, newPosZ, yaw, pitch);
            entityPositions.put(entityId, newPosition);
            return entityPositions;
        }

        return null;
    }),

    ENTITY_LOOK_MOVE(S14PacketEntity.S17PacketEntityLookMove.class, (packet, entityPositions) -> {
        S14PacketEntity.S17PacketEntityLookMove lookMovePacket = (S14PacketEntity.S17PacketEntityLookMove) packet;
        int entityId = lookMovePacket.getEntityId();
        double deltaX = lookMovePacket.getPosX() / 32.0;
        double deltaY = lookMovePacket.getPosY() / 32.0;
        double deltaZ = lookMovePacket.getPosZ() / 32.0;
        float yaw = (lookMovePacket.getYaw() * 360) / 256.0F;
        float pitch = (lookMovePacket.getPitch() * 360) / 256.0F;

        Position currentPosition = entityPositions.get(entityId);

        if (currentPosition != null) {
            double newPosX = currentPosition.posX + deltaX;
            double newPosY = currentPosition.posY + deltaY;
            double newPosZ = currentPosition.posZ + deltaZ;

            // Ensure yaw and pitch are updated
            Position newPosition = new Position(newPosX, newPosY, newPosZ, yaw, pitch);
            entityPositions.put(entityId, newPosition);
            return entityPositions;
        }

        return null;
    }),

    ENTITY_LOOK(S14PacketEntity.S16PacketEntityLook.class, (packet, entityPositions) -> {
        S14PacketEntity.S16PacketEntityLook lookPacket = (S14PacketEntity.S16PacketEntityLook) packet;
        int entityId = lookPacket.getEntityId();
        float yaw = (lookPacket.getYaw() * 360) / 256.0F;
        float pitch = (lookPacket.getPitch() * 360) / 256.0F;

        Position currentPosition = entityPositions.get(entityId);

        if (currentPosition != null) {
            Position newPosition = new Position(currentPosition.posX, currentPosition.posY, currentPosition.posZ, yaw, pitch);
            entityPositions.put(entityId, newPosition);
            return entityPositions;
        }

        return null;
    }),

    DESTROY_ENTITY(S13PacketDestroyEntities.class, (packet, entityPositions) -> {
        S13PacketDestroyEntities destroyEntitiesPacket = (S13PacketDestroyEntities) packet;
        int[] entityIds = destroyEntitiesPacket.getEntityIDs();

        for (int entityId : entityIds) {
            entityPositions.remove(entityId);
        }

        return entityPositions;
    }),

    ENTITY_HEAD_LOOK(S19PacketEntityHeadLook.class, (packet, entityPositions) -> {
        S19PacketEntityHeadLook headLookPacket = (S19PacketEntityHeadLook) packet;
        int entityId = headLookPacket.getEntityId();
        float headYaw = (headLookPacket.getYaw() * 360) / 256.0F;

        Position currentPosition = entityPositions.get(entityId);

        if (currentPosition != null) {
            Position newPosition = new Position(currentPosition.posX, currentPosition.posY, currentPosition.posZ, headYaw, currentPosition.pitch);
            entityPositions.put(entityId, newPosition);
            return entityPositions;
        }

        return null;
    });

    @Getter
    private final Class<? extends Packet<?>> packetClass;
    private final BiFunction<Packet<?>, Map<Integer, Position>, Map<Integer, Position>> handler;

    PacketType(Class<? extends Packet<?>> packetClass, BiFunction<Packet<?>, Map<Integer, Position>, Map<Integer, Position>> handler) {
        this.packetClass = packetClass;
        this.handler = handler;
    }

    public Map<Integer, Position> handle(Packet<?> packet, Map<Integer, Position> positions) {
        return handler.apply(packet, positions);
    }
}
