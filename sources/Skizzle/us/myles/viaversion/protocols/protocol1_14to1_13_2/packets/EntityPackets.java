/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_14to1_13_2.packets;

import java.util.LinkedList;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.entities.Entity1_13Types;
import us.myles.ViaVersion.api.entities.Entity1_14Types;
import us.myles.ViaVersion.api.entities.EntityType;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_14;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.version.Types1_13_2;
import us.myles.ViaVersion.api.type.types.version.Types1_14;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.metadata.MetadataRewriter1_14To1_13_2;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;

public class EntityPackets {
    public static void register(final Protocol1_14To1_13_2 protocol) {
        final MetadataRewriter1_14To1_13_2 metadataRewriter = protocol.get(MetadataRewriter1_14To1_13_2.class);
        protocol.registerOutgoing(ClientboundPackets1_13.SPAWN_ENTITY, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.BYTE, Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityId = wrapper.get(Type.VAR_INT, 0);
                        int typeId = wrapper.get(Type.VAR_INT, 1);
                        Entity1_13Types.EntityType type1_13 = Entity1_13Types.getTypeFromId(typeId, true);
                        Entity1_14Types.EntityType type1_14 = Entity1_14Types.getTypeFromId(typeId = metadataRewriter.getNewEntityId(type1_13.getId()));
                        if (type1_14 != null) {
                            int data = wrapper.get(Type.INT, 0);
                            if (type1_14.is((EntityType)Entity1_14Types.EntityType.FALLING_BLOCK)) {
                                wrapper.set(Type.INT, 0, protocol.getMappingData().getNewBlockStateId(data));
                            } else if (type1_14.is((EntityType)Entity1_14Types.EntityType.MINECART)) {
                                switch (data) {
                                    case 1: {
                                        typeId = Entity1_14Types.EntityType.CHEST_MINECART.getId();
                                        break;
                                    }
                                    case 2: {
                                        typeId = Entity1_14Types.EntityType.FURNACE_MINECART.getId();
                                        break;
                                    }
                                    case 3: {
                                        typeId = Entity1_14Types.EntityType.TNT_MINECART.getId();
                                        break;
                                    }
                                    case 4: {
                                        typeId = Entity1_14Types.EntityType.SPAWNER_MINECART.getId();
                                        break;
                                    }
                                    case 5: {
                                        typeId = Entity1_14Types.EntityType.HOPPER_MINECART.getId();
                                        break;
                                    }
                                    case 6: {
                                        typeId = Entity1_14Types.EntityType.COMMAND_BLOCK_MINECART.getId();
                                    }
                                }
                            } else if (type1_14.is((EntityType)Entity1_14Types.EntityType.ITEM) && data > 0 || type1_14.isOrHasParent(Entity1_14Types.EntityType.ABSTRACT_ARROW)) {
                                if (type1_14.isOrHasParent(Entity1_14Types.EntityType.ABSTRACT_ARROW)) {
                                    wrapper.set(Type.INT, 0, data - 1);
                                }
                                PacketWrapper velocity = wrapper.create(69);
                                velocity.write(Type.VAR_INT, entityId);
                                velocity.write(Type.SHORT, wrapper.get(Type.SHORT, 0));
                                velocity.write(Type.SHORT, wrapper.get(Type.SHORT, 1));
                                velocity.write(Type.SHORT, wrapper.get(Type.SHORT, 2));
                                velocity.send(Protocol1_14To1_13_2.class);
                            }
                            wrapper.user().get(EntityTracker1_14.class).addEntity(entityId, type1_14);
                        }
                        wrapper.set(Type.VAR_INT, 1, typeId);
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_13.SPAWN_MOB, new PacketRemapper(){

            @Override
            public void registerMap() {
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
                this.handler(metadataRewriter.getTrackerAndRewriter(Types1_14.METADATA_LIST));
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_13.SPAWN_PAINTING, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.map(Type.POSITION, Type.POSITION1_14);
                this.map(Type.BYTE);
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_13.SPAWN_PLAYER, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_13_2.METADATA_LIST, Types1_14.METADATA_LIST);
                this.handler(metadataRewriter.getTrackerAndRewriter(Types1_14.METADATA_LIST, Entity1_14Types.EntityType.PLAYER));
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_13.ENTITY_ANIMATION, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        short animation = wrapper.passthrough(Type.UNSIGNED_BYTE);
                        if (animation == 2) {
                            EntityTracker1_14 tracker = wrapper.user().get(EntityTracker1_14.class);
                            int entityId = wrapper.get(Type.VAR_INT, 0);
                            tracker.setSleeping(entityId, false);
                            PacketWrapper metadataPacket = wrapper.create(67);
                            metadataPacket.write(Type.VAR_INT, entityId);
                            LinkedList<Metadata> metadataList = new LinkedList<Metadata>();
                            if (tracker.getClientEntityId() != entityId) {
                                metadataList.add(new Metadata(6, MetaType1_14.Pose, MetadataRewriter1_14To1_13_2.recalculatePlayerPose(entityId, tracker)));
                            }
                            metadataList.add(new Metadata(12, MetaType1_14.OptPosition, null));
                            metadataPacket.write(Types1_14.METADATA_LIST, metadataList);
                            metadataPacket.send(Protocol1_14To1_13_2.class);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_13.USE_BED, ClientboundPackets1_14.ENTITY_METADATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        EntityTracker1_14 tracker = wrapper.user().get(EntityTracker1_14.class);
                        int entityId = wrapper.get(Type.VAR_INT, 0);
                        tracker.setSleeping(entityId, true);
                        Position position = wrapper.read(Type.POSITION);
                        LinkedList<Metadata> metadataList = new LinkedList<Metadata>();
                        metadataList.add(new Metadata(12, MetaType1_14.OptPosition, position));
                        if (tracker.getClientEntityId() != entityId) {
                            metadataList.add(new Metadata(6, MetaType1_14.Pose, MetadataRewriter1_14To1_13_2.recalculatePlayerPose(entityId, tracker)));
                        }
                        wrapper.write(Types1_14.METADATA_LIST, metadataList);
                    }
                });
            }
        });
        metadataRewriter.registerEntityDestroy(ClientboundPackets1_13.DESTROY_ENTITIES);
        metadataRewriter.registerMetadataRewriter(ClientboundPackets1_13.ENTITY_METADATA, Types1_13_2.METADATA_LIST, Types1_14.METADATA_LIST);
    }
}

