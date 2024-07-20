/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.items.ItemRewriter;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.metadata.MetadataRewriter;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viarewind.replacement.EntityReplacement;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import java.util.List;
import java.util.UUID;

public class EntityPackets {
    public static void register(Protocol1_7_6_10To1_8 protocol) {
        protocol.registerClientbound(ClientboundPackets1_8.ENTITY_EQUIPMENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.SHORT);
                this.map(Type.ITEM, Types1_7_6_10.COMPRESSED_NBT_ITEM);
                this.handler(packetWrapper -> {
                    Item item = packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
                    ItemRewriter.toClient(item);
                    packetWrapper.set(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0, item);
                });
                this.handler(packetWrapper -> {
                    short limit;
                    EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                    int id = packetWrapper.get(Type.INT, 0);
                    short s = limit = tracker.getPlayerId() == id ? (short)3 : (short)4;
                    if (packetWrapper.get(Type.SHORT, 0) > limit) {
                        packetWrapper.cancel();
                    }
                });
                this.handler(packetWrapper -> {
                    short slot = packetWrapper.get(Type.SHORT, 0);
                    if (packetWrapper.isCancelled()) {
                        return;
                    }
                    EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                    UUID uuid = tracker.getPlayerUUID(packetWrapper.get(Type.INT, 0));
                    if (uuid == null) {
                        return;
                    }
                    Item item = packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
                    tracker.setPlayerEquipment(uuid, item, slot);
                    GameProfileStorage storage = packetWrapper.user().get(GameProfileStorage.class);
                    GameProfileStorage.GameProfile profile = storage.get(uuid);
                    if (profile != null && profile.gamemode == 3) {
                        packetWrapper.cancel();
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.USE_BED, new PacketHandlers(){

            @Override
            public void register() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.handler(packetWrapper -> {
                    Position position = packetWrapper.read(Type.POSITION);
                    packetWrapper.write(Type.INT, position.x());
                    packetWrapper.write(Type.UNSIGNED_BYTE, (short)position.y());
                    packetWrapper.write(Type.INT, position.z());
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.COLLECT_ITEM, new PacketHandlers(){

            @Override
            public void register() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map((Type)Type.VAR_INT, Type.INT);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.ENTITY_VELOCITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.DESTROY_ENTITIES, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(packetWrapper -> {
                    int[] entityIds = packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                    EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                    for (int entityId : entityIds) {
                        tracker.removeEntity(entityId);
                    }
                    List<List<Integer>> parts = Lists.partition(Ints.asList(entityIds), 127);
                    for (int i = 0; i < parts.size() - 1; ++i) {
                        PacketWrapper destroy = PacketWrapper.create(ClientboundPackets1_7.DESTROY_ENTITIES, packetWrapper.user());
                        destroy.write(Types1_7_6_10.INT_ARRAY, parts.get(i).stream().mapToInt(Integer::intValue).toArray());
                        PacketUtil.sendPacket(destroy, Protocol1_7_6_10To1_8.class);
                    }
                    packetWrapper.write(Types1_7_6_10.INT_ARRAY, parts.get(parts.size() - 1).stream().mapToInt(Integer::intValue).toArray());
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.ENTITY_MOVEMENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map((Type)Type.VAR_INT, Type.INT);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.ENTITY_POSITION, new PacketHandlers(){

            @Override
            public void register() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map((Type)Type.BOOLEAN, Type.NOTHING);
                this.handler(packetWrapper -> {
                    int entityId = packetWrapper.get(Type.INT, 0);
                    EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        byte x = packetWrapper.get(Type.BYTE, 0);
                        byte y = packetWrapper.get(Type.BYTE, 1);
                        byte z = packetWrapper.get(Type.BYTE, 2);
                        replacement.relMove((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.ENTITY_ROTATION, new PacketHandlers(){

            @Override
            public void register() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map((Type)Type.BOOLEAN, Type.NOTHING);
                this.handler(packetWrapper -> {
                    int entityId = packetWrapper.get(Type.INT, 0);
                    EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        byte yaw = packetWrapper.get(Type.BYTE, 0);
                        byte pitch = packetWrapper.get(Type.BYTE, 1);
                        replacement.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.ENTITY_POSITION_AND_ROTATION, new PacketHandlers(){

            @Override
            public void register() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map((Type)Type.BOOLEAN, Type.NOTHING);
                this.handler(packetWrapper -> {
                    int entityId = packetWrapper.get(Type.INT, 0);
                    EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        byte x = packetWrapper.get(Type.BYTE, 0);
                        byte y = packetWrapper.get(Type.BYTE, 1);
                        byte z = packetWrapper.get(Type.BYTE, 2);
                        byte yaw = packetWrapper.get(Type.BYTE, 3);
                        byte pitch = packetWrapper.get(Type.BYTE, 4);
                        replacement.relMove((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                        replacement.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.ENTITY_TELEPORT, new PacketHandlers(){

            @Override
            public void register() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map((Type)Type.BOOLEAN, Type.NOTHING);
                this.handler(packetWrapper -> {
                    int entityId = packetWrapper.get(Type.INT, 0);
                    EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                    Entity1_10Types.EntityType type2 = tracker.getClientEntityTypes().get(entityId);
                    if (type2 == Entity1_10Types.EntityType.MINECART_ABSTRACT) {
                        int y = packetWrapper.get(Type.INT, 2);
                        packetWrapper.set(Type.INT, 2, y += 12);
                    }
                });
                this.handler(packetWrapper -> {
                    int entityId = packetWrapper.get(Type.INT, 0);
                    EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        int x = packetWrapper.get(Type.INT, 1);
                        int y = packetWrapper.get(Type.INT, 2);
                        int z = packetWrapper.get(Type.INT, 3);
                        byte yaw = packetWrapper.get(Type.BYTE, 0);
                        byte pitch = packetWrapper.get(Type.BYTE, 1);
                        replacement.setLocation((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                        replacement.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.ENTITY_HEAD_LOOK, new PacketHandlers(){

            @Override
            public void register() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.BYTE);
                this.handler(packetWrapper -> {
                    int entityId = packetWrapper.get(Type.INT, 0);
                    EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        byte yaw = packetWrapper.get(Type.BYTE, 0);
                        replacement.setHeadYaw((float)yaw * 360.0f / 256.0f);
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.ATTACH_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.handler(packetWrapper -> {
                    boolean leash = packetWrapper.get(Type.BOOLEAN, 0);
                    if (leash) {
                        return;
                    }
                    int passenger = packetWrapper.get(Type.INT, 0);
                    int vehicle = packetWrapper.get(Type.INT, 1);
                    EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                    tracker.setPassenger(vehicle, passenger);
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.ENTITY_METADATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
                this.handler(wrapper -> {
                    List<Metadata> metadataList = wrapper.get(Types1_7_6_10.METADATA_LIST, 0);
                    int entityId = wrapper.get(Type.INT, 0);
                    EntityTracker tracker = wrapper.user().get(EntityTracker.class);
                    if (tracker.getClientEntityTypes().containsKey(entityId)) {
                        EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                        if (replacement != null) {
                            wrapper.cancel();
                            replacement.updateMetadata(metadataList);
                        } else {
                            MetadataRewriter.transform(tracker.getClientEntityTypes().get(entityId), metadataList);
                            if (metadataList.isEmpty()) {
                                wrapper.cancel();
                            }
                        }
                    } else {
                        tracker.addMetadataToBuffer(entityId, metadataList);
                        wrapper.cancel();
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.ENTITY_EFFECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map((Type)Type.VAR_INT, Type.SHORT);
                this.map((Type)Type.BYTE, Type.NOTHING);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.REMOVE_ENTITY_EFFECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.BYTE);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.ENTITY_PROPERTIES, new PacketHandlers(){

            @Override
            public void register() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.handler(packetWrapper -> {
                    int entityId = packetWrapper.get(Type.INT, 0);
                    EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                    if (tracker.getEntityReplacement(entityId) != null) {
                        packetWrapper.cancel();
                        return;
                    }
                    int amount = packetWrapper.passthrough(Type.INT);
                    for (int i = 0; i < amount; ++i) {
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.DOUBLE);
                        int modifierlength = packetWrapper.read(Type.VAR_INT);
                        packetWrapper.write(Type.SHORT, (short)modifierlength);
                        for (int j = 0; j < modifierlength; ++j) {
                            packetWrapper.passthrough(Type.UUID);
                            packetWrapper.passthrough(Type.DOUBLE);
                            packetWrapper.passthrough(Type.BYTE);
                        }
                    }
                });
            }
        });
        protocol.cancelClientbound(ClientboundPackets1_8.UPDATE_ENTITY_NBT);
    }
}

