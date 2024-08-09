/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.metadata.MetadataRewriter1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import java.util.LinkedList;

public class EntityPackets {
    public static void register(Protocol1_14To1_13_2 protocol1_14To1_13_2) {
        MetadataRewriter1_14To1_13_2 metadataRewriter1_14To1_13_2 = protocol1_14To1_13_2.get(MetadataRewriter1_14To1_13_2.class);
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.SPAWN_ENTITY, new PacketHandlers(metadataRewriter1_14To1_13_2, protocol1_14To1_13_2){
            final MetadataRewriter1_14To1_13_2 val$metadataRewriter;
            final Protocol1_14To1_13_2 val$protocol;
            {
                this.val$metadataRewriter = metadataRewriter1_14To1_13_2;
                this.val$protocol = protocol1_14To1_13_2;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map((Type)Type.BYTE, Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(arg_0 -> 1.lambda$register$0(this.val$metadataRewriter, this.val$protocol, arg_0));
            }

            private static void lambda$register$0(MetadataRewriter1_14To1_13_2 metadataRewriter1_14To1_13_2, Protocol1_14To1_13_2 protocol1_14To1_13_2, PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                int n2 = packetWrapper.get(Type.VAR_INT, 1);
                Entity1_13Types.EntityType entityType = Entity1_13Types.getTypeFromId(n2, true);
                EntityType entityType2 = Entity1_14Types.getTypeFromId(n2 = metadataRewriter1_14To1_13_2.newEntityId(entityType.getId()));
                if (entityType2 != null) {
                    int n3 = packetWrapper.get(Type.INT, 0);
                    if (entityType2.is((EntityType)Entity1_14Types.FALLING_BLOCK)) {
                        packetWrapper.set(Type.INT, 0, protocol1_14To1_13_2.getMappingData().getNewBlockStateId(n3));
                    } else if (entityType2.is((EntityType)Entity1_14Types.MINECART)) {
                        switch (n3) {
                            case 1: {
                                n2 = Entity1_14Types.CHEST_MINECART.getId();
                                break;
                            }
                            case 2: {
                                n2 = Entity1_14Types.FURNACE_MINECART.getId();
                                break;
                            }
                            case 3: {
                                n2 = Entity1_14Types.TNT_MINECART.getId();
                                break;
                            }
                            case 4: {
                                n2 = Entity1_14Types.SPAWNER_MINECART.getId();
                                break;
                            }
                            case 5: {
                                n2 = Entity1_14Types.HOPPER_MINECART.getId();
                                break;
                            }
                            case 6: {
                                n2 = Entity1_14Types.COMMAND_BLOCK_MINECART.getId();
                            }
                        }
                    } else if (entityType2.is((EntityType)Entity1_14Types.ITEM) && n3 > 0 || entityType2.isOrHasParent(Entity1_14Types.ABSTRACT_ARROW)) {
                        if (entityType2.isOrHasParent(Entity1_14Types.ABSTRACT_ARROW)) {
                            packetWrapper.set(Type.INT, 0, n3 - 1);
                        }
                        PacketWrapper packetWrapper2 = packetWrapper.create(69);
                        packetWrapper2.write(Type.VAR_INT, n);
                        packetWrapper2.write(Type.SHORT, packetWrapper.get(Type.SHORT, 0));
                        packetWrapper2.write(Type.SHORT, packetWrapper.get(Type.SHORT, 1));
                        packetWrapper2.write(Type.SHORT, packetWrapper.get(Type.SHORT, 2));
                        packetWrapper2.scheduleSend(Protocol1_14To1_13_2.class);
                    }
                    packetWrapper.user().getEntityTracker(Protocol1_14To1_13_2.class).addEntity(n, entityType2);
                }
                packetWrapper.set(Type.VAR_INT, 1, n2);
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.SPAWN_MOB, new PacketHandlers(metadataRewriter1_14To1_13_2){
            final MetadataRewriter1_14To1_13_2 val$metadataRewriter;
            {
                this.val$metadataRewriter = metadataRewriter1_14To1_13_2;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_13_2.METADATA_LIST, Types1_14.METADATA_LIST);
                this.handler(this.val$metadataRewriter.trackerAndRewriterHandler(Types1_14.METADATA_LIST));
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.SPAWN_PAINTING, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.map(Type.POSITION, Type.POSITION1_14);
                this.map(Type.BYTE);
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.SPAWN_PLAYER, new PacketHandlers(metadataRewriter1_14To1_13_2){
            final MetadataRewriter1_14To1_13_2 val$metadataRewriter;
            {
                this.val$metadataRewriter = metadataRewriter1_14To1_13_2;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_13_2.METADATA_LIST, Types1_14.METADATA_LIST);
                this.handler(this.val$metadataRewriter.trackerAndRewriterHandler(Types1_14.METADATA_LIST, Entity1_14Types.PLAYER));
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.ENTITY_ANIMATION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(5::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                short s = packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                if (s == 2) {
                    EntityTracker1_14 entityTracker1_14 = (EntityTracker1_14)packetWrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                    int n = packetWrapper.get(Type.VAR_INT, 0);
                    entityTracker1_14.setSleeping(n, true);
                    PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_14.ENTITY_METADATA);
                    packetWrapper2.write(Type.VAR_INT, n);
                    LinkedList<Metadata> linkedList = new LinkedList<Metadata>();
                    if (entityTracker1_14.clientEntityId() != n) {
                        linkedList.add(new Metadata(6, Types1_14.META_TYPES.poseType, MetadataRewriter1_14To1_13_2.recalculatePlayerPose(n, entityTracker1_14)));
                    }
                    linkedList.add(new Metadata(12, Types1_14.META_TYPES.optionalPositionType, null));
                    packetWrapper2.write(Types1_14.METADATA_LIST, linkedList);
                    packetWrapper2.scheduleSend(Protocol1_14To1_13_2.class);
                }
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.JOIN_GAME, new PacketHandlers(metadataRewriter1_14To1_13_2, protocol1_14To1_13_2){
            final MetadataRewriter1_14To1_13_2 val$metadataRewriter;
            final Protocol1_14To1_13_2 val$protocol;
            {
                this.val$metadataRewriter = metadataRewriter1_14To1_13_2;
                this.val$protocol = protocol1_14To1_13_2;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(6::lambda$register$0);
                this.handler(this.val$metadataRewriter.playerTrackerHandler());
                this.handler(arg_0 -> 6.lambda$register$1(this.val$protocol, arg_0));
                this.handler(6::lambda$register$2);
            }

            private static void lambda$register$2(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.send(Protocol1_14To1_13_2.class);
                packetWrapper.cancel();
                WorldPackets.sendViewDistancePacket(packetWrapper.user());
            }

            private static void lambda$register$1(Protocol1_14To1_13_2 protocol1_14To1_13_2, PacketWrapper packetWrapper) throws Exception {
                short s = packetWrapper.read(Type.UNSIGNED_BYTE);
                PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_14.SERVER_DIFFICULTY);
                packetWrapper2.write(Type.UNSIGNED_BYTE, s);
                packetWrapper2.write(Type.BOOLEAN, false);
                packetWrapper2.scheduleSend(protocol1_14To1_13_2.getClass());
                packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.write(Type.VAR_INT, 64);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                int n = packetWrapper.get(Type.INT, 1);
                clientWorld.setEnvironment(n);
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.USE_BED, ClientboundPackets1_14.ENTITY_METADATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(7::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                EntityTracker1_14 entityTracker1_14 = (EntityTracker1_14)packetWrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                int n = packetWrapper.get(Type.VAR_INT, 0);
                entityTracker1_14.setSleeping(n, false);
                Position position = packetWrapper.read(Type.POSITION);
                LinkedList<Metadata> linkedList = new LinkedList<Metadata>();
                linkedList.add(new Metadata(12, Types1_14.META_TYPES.optionalPositionType, position));
                if (entityTracker1_14.clientEntityId() != n) {
                    linkedList.add(new Metadata(6, Types1_14.META_TYPES.poseType, MetadataRewriter1_14To1_13_2.recalculatePlayerPose(n, entityTracker1_14)));
                }
                packetWrapper.write(Types1_14.METADATA_LIST, linkedList);
            }
        });
        metadataRewriter1_14To1_13_2.registerRemoveEntities(ClientboundPackets1_13.DESTROY_ENTITIES);
        metadataRewriter1_14To1_13_2.registerMetadataRewriter(ClientboundPackets1_13.ENTITY_METADATA, Types1_13_2.METADATA_LIST, Types1_14.METADATA_LIST);
    }
}

