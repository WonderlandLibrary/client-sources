/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.entities.storage.EntityPositionHandler;
import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.EntityTypeMapping;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.PaintingMapping;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.PlayerPositionStorage1_13;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_12;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_12;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import java.util.Optional;

public class EntityPackets1_13
extends LegacyEntityRewriter<ClientboundPackets1_13, Protocol1_12_2To1_13> {
    public EntityPackets1_13(Protocol1_12_2To1_13 protocol1_12_2To1_13) {
        super(protocol1_12_2To1_13);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.PLAYER_POSITION, new PacketHandlers(this){
            final EntityPackets1_13 this$0;
            {
                this.this$0 = entityPackets1_13;
            }

            @Override
            public void register() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BYTE);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (!ViaBackwards.getConfig().isFix1_13FacePlayer()) {
                    return;
                }
                PlayerPositionStorage1_13 playerPositionStorage1_13 = packetWrapper.user().get(PlayerPositionStorage1_13.class);
                byte by = packetWrapper.get(Type.BYTE, 0);
                playerPositionStorage1_13.setX(EntityPackets1_13.access$000(by, 0, playerPositionStorage1_13.getX(), packetWrapper.get(Type.DOUBLE, 0)));
                playerPositionStorage1_13.setY(EntityPackets1_13.access$000(by, 1, playerPositionStorage1_13.getY(), packetWrapper.get(Type.DOUBLE, 1)));
                playerPositionStorage1_13.setZ(EntityPackets1_13.access$000(by, 2, playerPositionStorage1_13.getZ(), packetWrapper.get(Type.DOUBLE, 2)));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.SPAWN_ENTITY, new PacketHandlers(this){
            final EntityPackets1_13 this$0;
            {
                this.this$0 = entityPackets1_13;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.handler(EntityPackets1_13.access$100(this.this$0));
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Optional<Entity1_13Types.ObjectType> optional = Entity1_13Types.ObjectType.findById(packetWrapper.get(Type.BYTE, 0).byteValue());
                if (!optional.isPresent()) {
                    return;
                }
                Entity1_13Types.ObjectType objectType = optional.get();
                if (objectType == Entity1_13Types.ObjectType.FALLING_BLOCK) {
                    int n = packetWrapper.get(Type.INT, 0);
                    int n2 = Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(n);
                    n2 = n2 >> 4 & 0xFFF | (n2 & 0xF) << 12;
                    packetWrapper.set(Type.INT, 0, n2);
                } else if (objectType == Entity1_13Types.ObjectType.ITEM_FRAME) {
                    int n = packetWrapper.get(Type.INT, 0);
                    switch (n) {
                        case 3: {
                            n = 0;
                            break;
                        }
                        case 4: {
                            n = 1;
                            break;
                        }
                        case 5: {
                            n = 3;
                        }
                    }
                    packetWrapper.set(Type.INT, 0, n);
                } else if (objectType == Entity1_13Types.ObjectType.TRIDENT) {
                    packetWrapper.set(Type.BYTE, 0, (byte)Entity1_13Types.ObjectType.TIPPED_ARROW.getId());
                }
            }
        });
        this.registerTracker(ClientboundPackets1_13.SPAWN_EXPERIENCE_ORB, Entity1_13Types.EntityType.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_13.SPAWN_GLOBAL_ENTITY, Entity1_13Types.EntityType.LIGHTNING_BOLT);
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.SPAWN_MOB, new PacketHandlers(this){
            final EntityPackets1_13 this$0;
            {
                this.this$0 = entityPackets1_13;
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
                this.map(Types1_13.METADATA_LIST, Types1_12.METADATA_LIST);
                this.handler(this::lambda$register$0);
                this.handler(EntityPackets1_13.access$200(this.this$0, Types1_12.METADATA_LIST));
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 1);
                Entity1_13Types.EntityType entityType = Entity1_13Types.getTypeFromId(n, false);
                this.this$0.tracker(packetWrapper.user()).addEntity(packetWrapper.get(Type.VAR_INT, 0), entityType);
                int n2 = EntityTypeMapping.getOldId(n);
                if (n2 == -1) {
                    if (!EntityPackets1_13.access$300(this.this$0, entityType)) {
                        ViaBackwards.getPlatform().getLogger().warning("Could not find 1.12 entity type for 1.13 entity type " + n + "/" + entityType);
                    }
                } else {
                    packetWrapper.set(Type.VAR_INT, 1, n2);
                }
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.SPAWN_PLAYER, new PacketHandlers(this){
            final EntityPackets1_13 this$0;
            {
                this.this$0 = entityPackets1_13;
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
                this.map(Types1_13.METADATA_LIST, Types1_12.METADATA_LIST);
                this.handler(EntityPackets1_13.access$400(this.this$0, Types1_12.METADATA_LIST, Entity1_13Types.EntityType.PLAYER));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.SPAWN_PAINTING, new PacketHandlers(this){
            final EntityPackets1_13 this$0;
            {
                this.this$0 = entityPackets1_13;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.handler(EntityPackets1_13.access$500(this.this$0, Entity1_13Types.EntityType.PAINTING, Type.VAR_INT));
                this.handler(5::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.VAR_INT);
                String string = PaintingMapping.getStringId(n);
                packetWrapper.write(Type.STRING, string);
            }
        });
        this.registerJoinGame(ClientboundPackets1_13.JOIN_GAME, Entity1_13Types.EntityType.PLAYER);
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.RESPAWN, new PacketHandlers(this){
            final EntityPackets1_13 this$0;
            {
                this.this$0 = entityPackets1_13;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.handler(EntityPackets1_13.access$600(this.this$0, 0));
                this.handler(6::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.user().get(BackwardsBlockStorage.class).clear();
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_13.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_13.ENTITY_METADATA, Types1_13.METADATA_LIST, Types1_12.METADATA_LIST);
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.FACE_PLAYER, null, EntityPackets1_13::lambda$registerPackets$0);
        if (ViaBackwards.getConfig().isFix1_13FacePlayer()) {
            PacketHandlers packetHandlers = new PacketHandlers(this){
                final EntityPackets1_13 this$0;
                {
                    this.this$0 = entityPackets1_13;
                }

                @Override
                public void register() {
                    this.map(Type.DOUBLE);
                    this.map(Type.DOUBLE);
                    this.map(Type.DOUBLE);
                    this.handler(7::lambda$register$0);
                }

                private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                    packetWrapper.user().get(PlayerPositionStorage1_13.class).setCoordinates(packetWrapper, true);
                }
            };
            ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_12_1.PLAYER_POSITION, packetHandlers);
            ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_12_1.PLAYER_POSITION_AND_ROTATION, packetHandlers);
            ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_12_1.VEHICLE_MOVE, packetHandlers);
        }
    }

    @Override
    protected void registerRewrites() {
        this.mapEntityTypeWithData(Entity1_13Types.EntityType.DROWNED, Entity1_13Types.EntityType.ZOMBIE_VILLAGER).plainName();
        this.mapEntityTypeWithData(Entity1_13Types.EntityType.COD, Entity1_13Types.EntityType.SQUID).plainName();
        this.mapEntityTypeWithData(Entity1_13Types.EntityType.SALMON, Entity1_13Types.EntityType.SQUID).plainName();
        this.mapEntityTypeWithData(Entity1_13Types.EntityType.PUFFERFISH, Entity1_13Types.EntityType.SQUID).plainName();
        this.mapEntityTypeWithData(Entity1_13Types.EntityType.TROPICAL_FISH, Entity1_13Types.EntityType.SQUID).plainName();
        this.mapEntityTypeWithData(Entity1_13Types.EntityType.PHANTOM, Entity1_13Types.EntityType.PARROT).plainName().spawnMetadata(EntityPackets1_13::lambda$registerRewrites$1);
        this.mapEntityTypeWithData(Entity1_13Types.EntityType.DOLPHIN, Entity1_13Types.EntityType.SQUID).plainName();
        this.mapEntityTypeWithData(Entity1_13Types.EntityType.TURTLE, Entity1_13Types.EntityType.OCELOT).plainName();
        this.filter().handler(this::lambda$registerRewrites$2);
        this.filter().filterFamily(Entity1_13Types.EntityType.ZOMBIE).removeIndex(15);
        this.filter().type(Entity1_13Types.EntityType.TURTLE).cancel(13);
        this.filter().type(Entity1_13Types.EntityType.TURTLE).cancel(14);
        this.filter().type(Entity1_13Types.EntityType.TURTLE).cancel(15);
        this.filter().type(Entity1_13Types.EntityType.TURTLE).cancel(16);
        this.filter().type(Entity1_13Types.EntityType.TURTLE).cancel(17);
        this.filter().type(Entity1_13Types.EntityType.TURTLE).cancel(18);
        this.filter().filterFamily(Entity1_13Types.EntityType.ABSTRACT_FISHES).cancel(12);
        this.filter().filterFamily(Entity1_13Types.EntityType.ABSTRACT_FISHES).cancel(13);
        this.filter().type(Entity1_13Types.EntityType.PHANTOM).cancel(12);
        this.filter().type(Entity1_13Types.EntityType.BOAT).cancel(12);
        this.filter().type(Entity1_13Types.EntityType.TRIDENT).cancel(7);
        this.filter().type(Entity1_13Types.EntityType.WOLF).index(17).handler(EntityPackets1_13::lambda$registerRewrites$3);
        this.filter().type(Entity1_13Types.EntityType.AREA_EFFECT_CLOUD).index(9).handler(this::lambda$registerRewrites$4);
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_13Types.getTypeFromId(n, false);
    }

    @Override
    protected EntityType getObjectTypeFromId(int n) {
        return Entity1_13Types.getTypeFromId(n, true);
    }

    @Override
    public int newEntityId(int n) {
        return EntityTypeMapping.getOldId(n);
    }

    private static double toSet(int n, int n2, double d, double d2) {
        return (n & 1 << n2) != 0 ? d + d2 : d2;
    }

    private void lambda$registerRewrites$4(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        Particle particle = (Particle)metadata.getValue();
        ParticleMapping.ParticleData particleData = ParticleMapping.getMapping(particle.getId());
        int n = 0;
        int n2 = 0;
        int[] nArray = particleData.rewriteMeta((Protocol1_12_2To1_13)this.protocol, particle.getArguments());
        if (nArray != null && nArray.length != 0) {
            if (particleData.getHandler().isBlockHandler() && nArray[0] == 0) {
                nArray[0] = 102;
            }
            n = nArray[0];
            n2 = nArray.length == 2 ? nArray[1] : 0;
        }
        metaHandlerEvent.createExtraMeta(new Metadata(9, MetaType1_12.VarInt, particleData.getHistoryId()));
        metaHandlerEvent.createExtraMeta(new Metadata(10, MetaType1_12.VarInt, n));
        metaHandlerEvent.createExtraMeta(new Metadata(11, MetaType1_12.VarInt, n2));
        metaHandlerEvent.cancel();
    }

    private static void lambda$registerRewrites$3(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        metadata.setValue(15 - (Integer)metadata.getValue());
    }

    private void lambda$registerRewrites$2(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = metadata.metaType().typeId();
        if (n == 4) {
            JsonElement jsonElement = (JsonElement)metadata.value();
            ((Protocol1_12_2To1_13)this.protocol).translatableRewriter().processText(jsonElement);
            metadata.setMetaType(MetaType1_12.Chat);
        } else if (n == 5) {
            JsonElement jsonElement = (JsonElement)metadata.value();
            metadata.setTypeAndValue(MetaType1_12.String, ((Protocol1_12_2To1_13)this.protocol).jsonToLegacy(jsonElement));
        } else if (n == 6) {
            Item item = (Item)metadata.getValue();
            metadata.setTypeAndValue(MetaType1_12.Slot, ((Protocol1_12_2To1_13)this.protocol).getItemRewriter().handleItemToClient(item));
        } else if (n == 15) {
            metaHandlerEvent.cancel();
        } else {
            metadata.setMetaType(MetaType1_12.byId(n > 5 ? n - 1 : n));
        }
    }

    private static void lambda$registerRewrites$1(WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(new Metadata(15, MetaType1_12.VarInt, 3));
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.cancel();
        if (!ViaBackwards.getConfig().isFix1_13FacePlayer()) {
            return;
        }
        int n = packetWrapper.read(Type.VAR_INT);
        double d = packetWrapper.read(Type.DOUBLE);
        double d2 = packetWrapper.read(Type.DOUBLE);
        double d3 = packetWrapper.read(Type.DOUBLE);
        PlayerPositionStorage1_13 playerPositionStorage1_13 = packetWrapper.user().get(PlayerPositionStorage1_13.class);
        PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_12_1.PLAYER_POSITION);
        packetWrapper2.write(Type.DOUBLE, 0.0);
        packetWrapper2.write(Type.DOUBLE, 0.0);
        packetWrapper2.write(Type.DOUBLE, 0.0);
        EntityPositionHandler.writeFacingDegrees(packetWrapper2, playerPositionStorage1_13.getX(), n == 1 ? playerPositionStorage1_13.getY() + 1.62 : playerPositionStorage1_13.getY(), playerPositionStorage1_13.getZ(), d, d2, d3);
        packetWrapper2.write(Type.BYTE, (byte)7);
        packetWrapper2.write(Type.VAR_INT, -1);
        packetWrapper2.send(Protocol1_12_2To1_13.class);
    }

    static double access$000(int n, int n2, double d, double d2) {
        return EntityPackets1_13.toSet(n, n2, d, d2);
    }

    static PacketHandler access$100(EntityPackets1_13 entityPackets1_13) {
        return entityPackets1_13.getObjectTrackerHandler();
    }

    static PacketHandler access$200(EntityPackets1_13 entityPackets1_13, Type type) {
        return entityPackets1_13.getMobSpawnRewriter(type);
    }

    static boolean access$300(EntityPackets1_13 entityPackets1_13, EntityType entityType) {
        return entityPackets1_13.hasData(entityType);
    }

    static PacketHandler access$400(EntityPackets1_13 entityPackets1_13, Type type, EntityType entityType) {
        return entityPackets1_13.getTrackerAndMetaHandler(type, entityType);
    }

    static PacketHandler access$500(EntityPackets1_13 entityPackets1_13, EntityType entityType, Type type) {
        return entityPackets1_13.getTrackerHandler(entityType, type);
    }

    static PacketHandler access$600(EntityPackets1_13 entityPackets1_13, int n) {
        return entityPackets1_13.getDimensionHandler(n);
    }
}

