/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.ArmorStandReplacement;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.EndermiteReplacement;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.EntityReplacement1_7to1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.GuardianReplacement;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.RabbitReplacement;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.items.ReplacementRegistry1_7_6_10to1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.metadata.MetadataRewriter;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viarewind.replacement.Replacement;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import java.util.List;
import java.util.UUID;

public class SpawnPackets {
    public static void register(Protocol1_7_6_10To1_8 protocol) {
        protocol.registerClientbound(ClientboundPackets1_8.SPAWN_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(packetWrapper -> {
                    UUID uuid = packetWrapper.read(Type.UUID);
                    packetWrapper.write(Type.STRING, uuid.toString());
                    GameProfileStorage gameProfileStorage = packetWrapper.user().get(GameProfileStorage.class);
                    GameProfileStorage.GameProfile gameProfile = gameProfileStorage.get(uuid);
                    if (gameProfile == null) {
                        packetWrapper.write(Type.STRING, "");
                        packetWrapper.write(Type.VAR_INT, 0);
                    } else {
                        packetWrapper.write(Type.STRING, gameProfile.name.length() > 16 ? gameProfile.name.substring(0, 16) : gameProfile.name);
                        packetWrapper.write(Type.VAR_INT, gameProfile.properties.size());
                        for (GameProfileStorage.Property property : gameProfile.properties) {
                            packetWrapper.write(Type.STRING, property.name);
                            packetWrapper.write(Type.STRING, property.value);
                            packetWrapper.write(Type.STRING, property.signature == null ? "" : property.signature);
                        }
                    }
                    EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                    if (gameProfile != null && gameProfile.gamemode == 3) {
                        int entityId = packetWrapper.get(Type.VAR_INT, 0);
                        for (short i = 0; i < 5; i = (short)(i + 1)) {
                            PacketWrapper equipmentPacket = PacketWrapper.create(ClientboundPackets1_7.ENTITY_EQUIPMENT, packetWrapper.user());
                            equipmentPacket.write(Type.INT, entityId);
                            equipmentPacket.write(Type.SHORT, i);
                            equipmentPacket.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, i == 4 ? gameProfile.getSkull() : null);
                            PacketUtil.sendPacket(equipmentPacket, Protocol1_7_6_10To1_8.class);
                        }
                    }
                    tracker.addPlayer(packetWrapper.get(Type.VAR_INT, 0), uuid);
                });
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
                this.handler(packetWrapper -> {
                    List<Metadata> metadata = packetWrapper.get(Types1_7_6_10.METADATA_LIST, 0);
                    MetadataRewriter.transform(Entity1_10Types.EntityType.PLAYER, metadata);
                });
                this.handler(packetWrapper -> {
                    int entityId = packetWrapper.get(Type.VAR_INT, 0);
                    EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(entityId, Entity1_10Types.EntityType.PLAYER);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.SPAWN_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.handler(packetWrapper -> {
                    EntityTracker tracker;
                    int entityId = packetWrapper.get(Type.VAR_INT, 0);
                    byte typeId = packetWrapper.get(Type.BYTE, 0);
                    int x = packetWrapper.get(Type.INT, 0);
                    int y = packetWrapper.get(Type.INT, 1);
                    int z = packetWrapper.get(Type.INT, 2);
                    byte pitch = packetWrapper.get(Type.BYTE, 1);
                    byte yaw = packetWrapper.get(Type.BYTE, 2);
                    if (typeId == 71) {
                        switch (yaw) {
                            case -128: {
                                z += 32;
                                yaw = 0;
                                break;
                            }
                            case -64: {
                                x -= 32;
                                yaw = -64;
                                break;
                            }
                            case 0: {
                                z -= 32;
                                yaw = -128;
                                break;
                            }
                            case 64: {
                                x += 32;
                                yaw = 64;
                            }
                        }
                    } else if (typeId == 78) {
                        packetWrapper.cancel();
                        tracker = packetWrapper.user().get(EntityTracker.class);
                        ArmorStandReplacement armorStand = new ArmorStandReplacement(entityId, packetWrapper.user());
                        armorStand.setLocation((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                        armorStand.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                        armorStand.setHeadYaw((float)yaw * 360.0f / 256.0f);
                        tracker.addEntityReplacement(armorStand);
                    } else if (typeId == 10) {
                        y += 12;
                    }
                    packetWrapper.set(Type.BYTE, 0, typeId);
                    packetWrapper.set(Type.INT, 0, x);
                    packetWrapper.set(Type.INT, 1, y);
                    packetWrapper.set(Type.INT, 2, z);
                    packetWrapper.set(Type.BYTE, 1, pitch);
                    packetWrapper.set(Type.BYTE, 2, yaw);
                    tracker = packetWrapper.user().get(EntityTracker.class);
                    Entity1_10Types.EntityType type2 = Entity1_10Types.getTypeFromId(typeId, true);
                    tracker.getClientEntityTypes().put(entityId, type2);
                    tracker.sendMetadataBuffer(entityId);
                    int data = packetWrapper.get(Type.INT, 3);
                    if (type2 != null && type2.isOrHasParent(Entity1_10Types.EntityType.FALLING_BLOCK)) {
                        int blockId = data & 0xFFF;
                        int blockData = data >> 12 & 0xF;
                        Replacement replace = ReplacementRegistry1_7_6_10to1_8.getReplacement(blockId, blockData);
                        if (replace != null) {
                            blockId = replace.getId();
                            blockData = replace.replaceData(blockData);
                        }
                        data = blockId | blockData << 16;
                        packetWrapper.set(Type.INT, 3, data);
                    }
                    if (data > 0) {
                        packetWrapper.passthrough(Type.SHORT);
                        packetWrapper.passthrough(Type.SHORT);
                        packetWrapper.passthrough(Type.SHORT);
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.SPAWN_MOB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
                this.handler(packetWrapper -> {
                    int entityId = packetWrapper.get(Type.VAR_INT, 0);
                    short typeId = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                    int x = packetWrapper.get(Type.INT, 0);
                    int y = packetWrapper.get(Type.INT, 1);
                    int z = packetWrapper.get(Type.INT, 2);
                    byte pitch = packetWrapper.get(Type.BYTE, 1);
                    byte yaw = packetWrapper.get(Type.BYTE, 0);
                    byte headYaw = packetWrapper.get(Type.BYTE, 2);
                    if (typeId == 30 || typeId == 68 || typeId == 67 || typeId == 101) {
                        packetWrapper.cancel();
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        EntityReplacement1_7to1_8 replacement = null;
                        if (typeId == 30) {
                            replacement = new ArmorStandReplacement(entityId, packetWrapper.user());
                        } else if (typeId == 68) {
                            replacement = new GuardianReplacement(entityId, packetWrapper.user());
                        } else if (typeId == 67) {
                            replacement = new EndermiteReplacement(entityId, packetWrapper.user());
                        } else if (typeId == 101) {
                            replacement = new RabbitReplacement(entityId, packetWrapper.user());
                        }
                        replacement.setLocation((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                        replacement.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                        replacement.setHeadYaw((float)headYaw * 360.0f / 256.0f);
                        tracker.addEntityReplacement(replacement);
                    } else if (typeId == 255 || typeId == -1) {
                        packetWrapper.cancel();
                    }
                });
                this.handler(packetWrapper -> {
                    int entityId = packetWrapper.get(Type.VAR_INT, 0);
                    short typeId = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                    EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(entityId, Entity1_10Types.getTypeFromId(typeId, false));
                    tracker.sendMetadataBuffer(entityId);
                });
                this.handler(wrapper -> {
                    List<Metadata> metadataList = wrapper.get(Types1_7_6_10.METADATA_LIST, 0);
                    int entityId = wrapper.get(Type.VAR_INT, 0);
                    EntityTracker tracker = wrapper.user().get(EntityTracker.class);
                    if (tracker.getEntityReplacement(entityId) != null) {
                        tracker.getEntityReplacement(entityId).updateMetadata(metadataList);
                    } else if (tracker.getClientEntityTypes().containsKey(entityId)) {
                        MetadataRewriter.transform(tracker.getClientEntityTypes().get(entityId), metadataList);
                    } else {
                        wrapper.cancel();
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.SPAWN_PAINTING, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.STRING);
                this.handler(packetWrapper -> {
                    Position position = packetWrapper.read(Type.POSITION);
                    packetWrapper.write(Type.INT, position.x());
                    packetWrapper.write(Type.INT, position.y());
                    packetWrapper.write(Type.INT, position.z());
                });
                this.map((Type)Type.UNSIGNED_BYTE, Type.INT);
                this.handler(packetWrapper -> {
                    int entityId = packetWrapper.get(Type.VAR_INT, 0);
                    EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(entityId, Entity1_10Types.EntityType.PAINTING);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.SPAWN_EXPERIENCE_ORB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.SHORT);
                this.handler(packetWrapper -> {
                    int entityId = packetWrapper.get(Type.VAR_INT, 0);
                    EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(entityId, Entity1_10Types.EntityType.EXPERIENCE_ORB);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.SPAWN_GLOBAL_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.handler(packetWrapper -> {
                    int entityId = packetWrapper.get(Type.VAR_INT, 0);
                    EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(entityId, Entity1_10Types.EntityType.LIGHTNING);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
    }
}

