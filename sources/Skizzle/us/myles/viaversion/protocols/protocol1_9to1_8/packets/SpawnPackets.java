/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_9to1_8.packets;

import java.util.ArrayList;
import java.util.List;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.entities.Entity1_10Types;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_9;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.remapper.ValueCreator;
import us.myles.ViaVersion.api.remapper.ValueTransformer;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.version.Types1_8;
import us.myles.ViaVersion.api.type.types.version.Types1_9;
import us.myles.ViaVersion.protocols.protocol1_8.ClientboundPackets1_8;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.ItemRewriter;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.metadata.MetadataRewriter1_9To1_8;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;

public class SpawnPackets {
    public static final ValueTransformer<Integer, Double> toNewDouble = new ValueTransformer<Integer, Double>(Type.DOUBLE){

        @Override
        public Double transform(PacketWrapper wrapper, Integer inputValue) {
            return (double)inputValue.intValue() / 32.0;
        }
    };

    public static void register(final Protocol1_9To1_8 protocol) {
        protocol.registerOutgoing(ClientboundPackets1_8.SPAWN_ENTITY, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper wrapper) throws Exception {
                        int entityID = wrapper.get(Type.VAR_INT, 0);
                        EntityTracker1_9 tracker = wrapper.user().get(EntityTracker1_9.class);
                        wrapper.write(Type.UUID, tracker.getEntityUUID(entityID));
                    }
                });
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityID = wrapper.get(Type.VAR_INT, 0);
                        byte typeID = wrapper.get(Type.BYTE, 0);
                        EntityTracker1_9 tracker = wrapper.user().get(EntityTracker1_9.class);
                        tracker.addEntity(entityID, Entity1_10Types.getTypeFromId(typeID, true));
                        tracker.sendMetadataBuffer(entityID);
                    }
                });
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper wrapper) throws Exception {
                        int data = wrapper.get(Type.INT, 0);
                        short vX = 0;
                        short vY = 0;
                        short vZ = 0;
                        if (data > 0) {
                            vX = wrapper.read(Type.SHORT);
                            vY = wrapper.read(Type.SHORT);
                            vZ = wrapper.read(Type.SHORT);
                        }
                        wrapper.write(Type.SHORT, vX);
                        wrapper.write(Type.SHORT, vY);
                        wrapper.write(Type.SHORT, vZ);
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        final int entityID = wrapper.get(Type.VAR_INT, 0);
                        final int data = wrapper.get(Type.INT, 0);
                        byte typeID = wrapper.get(Type.BYTE, 0);
                        if (Entity1_10Types.getTypeFromId(typeID, true) == Entity1_10Types.EntityType.SPLASH_POTION) {
                            PacketWrapper metaPacket = wrapper.create(57, new ValueCreator(){

                                @Override
                                public void write(PacketWrapper wrapper) throws Exception {
                                    wrapper.write(Type.VAR_INT, entityID);
                                    ArrayList<Metadata> meta = new ArrayList<Metadata>();
                                    Item item = new Item(373, 1, (short)data, null);
                                    ItemRewriter.toClient(item);
                                    Metadata potion = new Metadata(5, MetaType1_9.Slot, item);
                                    meta.add(potion);
                                    wrapper.write(Types1_9.METADATA_LIST, meta);
                                }
                            });
                            metaPacket.send(Protocol1_9To1_8.class);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.SPAWN_EXPERIENCE_ORB, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityID = wrapper.get(Type.VAR_INT, 0);
                        EntityTracker1_9 tracker = wrapper.user().get(EntityTracker1_9.class);
                        tracker.addEntity(entityID, Entity1_10Types.EntityType.EXPERIENCE_ORB);
                        tracker.sendMetadataBuffer(entityID);
                    }
                });
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
                this.map(Type.SHORT);
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.SPAWN_GLOBAL_ENTITY, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityID = wrapper.get(Type.VAR_INT, 0);
                        EntityTracker1_9 tracker = wrapper.user().get(EntityTracker1_9.class);
                        tracker.addEntity(entityID, Entity1_10Types.EntityType.LIGHTNING);
                        tracker.sendMetadataBuffer(entityID);
                    }
                });
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.SPAWN_MOB, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper wrapper) throws Exception {
                        int entityID = wrapper.get(Type.VAR_INT, 0);
                        EntityTracker1_9 tracker = wrapper.user().get(EntityTracker1_9.class);
                        wrapper.write(Type.UUID, tracker.getEntityUUID(entityID));
                    }
                });
                this.map(Type.UNSIGNED_BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityID = wrapper.get(Type.VAR_INT, 0);
                        short typeID = wrapper.get(Type.UNSIGNED_BYTE, 0);
                        EntityTracker1_9 tracker = wrapper.user().get(EntityTracker1_9.class);
                        tracker.addEntity(entityID, Entity1_10Types.getTypeFromId(typeID, false));
                        tracker.sendMetadataBuffer(entityID);
                    }
                });
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        List<Metadata> metadataList = wrapper.get(Types1_9.METADATA_LIST, 0);
                        int entityId = wrapper.get(Type.VAR_INT, 0);
                        EntityTracker1_9 tracker = wrapper.user().get(EntityTracker1_9.class);
                        if (tracker.hasEntity(entityId)) {
                            protocol.get(MetadataRewriter1_9To1_8.class).handleMetadata(entityId, metadataList, wrapper.user());
                        } else {
                            Via.getPlatform().getLogger().warning("Unable to find entity for metadata, entity ID: " + entityId);
                            metadataList.clear();
                        }
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        List<Metadata> metadataList = wrapper.get(Types1_9.METADATA_LIST, 0);
                        int entityID = wrapper.get(Type.VAR_INT, 0);
                        EntityTracker1_9 tracker = wrapper.user().get(EntityTracker1_9.class);
                        tracker.handleMetadata(entityID, metadataList);
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.SPAWN_PAINTING, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityID = wrapper.get(Type.VAR_INT, 0);
                        EntityTracker1_9 tracker = wrapper.user().get(EntityTracker1_9.class);
                        tracker.addEntity(entityID, Entity1_10Types.EntityType.PAINTING);
                        tracker.sendMetadataBuffer(entityID);
                    }
                });
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper wrapper) throws Exception {
                        int entityID = wrapper.get(Type.VAR_INT, 0);
                        EntityTracker1_9 tracker = wrapper.user().get(EntityTracker1_9.class);
                        wrapper.write(Type.UUID, tracker.getEntityUUID(entityID));
                    }
                });
                this.map(Type.STRING);
                this.map(Type.POSITION);
                this.map(Type.BYTE);
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.SPAWN_PLAYER, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityID = wrapper.get(Type.VAR_INT, 0);
                        EntityTracker1_9 tracker = wrapper.user().get(EntityTracker1_9.class);
                        tracker.addEntity(entityID, Entity1_10Types.EntityType.PLAYER);
                        tracker.sendMetadataBuffer(entityID);
                    }
                });
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        short item = wrapper.read(Type.SHORT);
                        if (item != 0) {
                            PacketWrapper packet = new PacketWrapper(60, null, wrapper.user());
                            packet.write(Type.VAR_INT, wrapper.get(Type.VAR_INT, 0));
                            packet.write(Type.VAR_INT, 0);
                            packet.write(Type.ITEM, new Item(item, 1, 0, null));
                            try {
                                packet.send(Protocol1_9To1_8.class, true, true);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                this.map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        List<Metadata> metadataList = wrapper.get(Types1_9.METADATA_LIST, 0);
                        int entityId = wrapper.get(Type.VAR_INT, 0);
                        EntityTracker1_9 tracker = wrapper.user().get(EntityTracker1_9.class);
                        if (tracker.hasEntity(entityId)) {
                            protocol.get(MetadataRewriter1_9To1_8.class).handleMetadata(entityId, metadataList, wrapper.user());
                        } else {
                            Via.getPlatform().getLogger().warning("Unable to find entity for metadata, entity ID: " + entityId);
                            metadataList.clear();
                        }
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        List<Metadata> metadataList = wrapper.get(Types1_9.METADATA_LIST, 0);
                        int entityID = wrapper.get(Type.VAR_INT, 0);
                        EntityTracker1_9 tracker = wrapper.user().get(EntityTracker1_9.class);
                        tracker.handleMetadata(entityID, metadataList);
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.DESTROY_ENTITIES, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT_ARRAY_PRIMITIVE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int[] entities;
                        for (int entity : entities = wrapper.get(Type.VAR_INT_ARRAY_PRIMITIVE, 0)) {
                            wrapper.user().get(EntityTracker1_9.class).removeEntity(entity);
                        }
                    }
                });
            }
        });
    }
}

