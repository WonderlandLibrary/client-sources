/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.entities.storage.EntityPositionHandler;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.ChunkLightStorage;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.DifficultyStorage;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.EntityPositionStorage1_14;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.VillagerData;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.meta.MetaHandler;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;

public class EntityPackets1_14
extends LegacyEntityRewriter<ClientboundPackets1_14, Protocol1_13_2To1_14> {
    private EntityPositionHandler positionHandler;

    public EntityPackets1_14(Protocol1_13_2To1_14 protocol1_13_2To1_14) {
        super(protocol1_13_2To1_14, Types1_13_2.META_TYPES.optionalComponentType, Types1_13_2.META_TYPES.booleanType);
    }

    @Override
    protected void addTrackedEntity(PacketWrapper packetWrapper, int n, EntityType entityType) throws Exception {
        super.addTrackedEntity(packetWrapper, n, entityType);
        if (entityType == Entity1_14Types.PAINTING) {
            Position position = packetWrapper.get(Type.POSITION, 0);
            this.positionHandler.cacheEntityPosition(packetWrapper, position.x(), position.y(), position.z(), true, true);
        } else if (packetWrapper.getId() != ClientboundPackets1_14.JOIN_GAME.getId()) {
            this.positionHandler.cacheEntityPosition(packetWrapper, true, true);
        }
    }

    @Override
    protected void registerPackets() {
        this.positionHandler = new EntityPositionHandler(this, EntityPositionStorage1_14.class, EntityPositionStorage1_14::new);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.ENTITY_STATUS, this::lambda$registerPackets$0);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.ENTITY_TELEPORT, new PacketHandlers(this){
            final EntityPackets1_14 this$0;
            {
                this.this$0 = entityPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                EntityPackets1_14.access$000(this.this$0).cacheEntityPosition(packetWrapper, false, true);
            }
        });
        PacketHandlers packetHandlers = new PacketHandlers(this){
            final EntityPackets1_14 this$0;
            {
                this.this$0 = entityPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                double d = (double)packetWrapper.get(Type.SHORT, 0).shortValue() / 4096.0;
                double d2 = (double)packetWrapper.get(Type.SHORT, 1).shortValue() / 4096.0;
                double d3 = (double)packetWrapper.get(Type.SHORT, 2).shortValue() / 4096.0;
                EntityPackets1_14.access$000(this.this$0).cacheEntityPosition(packetWrapper, d, d2, d3, false, false);
            }
        };
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.ENTITY_POSITION, packetHandlers);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.ENTITY_POSITION_AND_ROTATION, packetHandlers);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.SPAWN_ENTITY, new PacketHandlers(this){
            final EntityPackets1_14 this$0;
            {
                this.this$0 = entityPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map((Type)Type.VAR_INT, Type.BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(EntityPackets1_14.access$100(this.this$0));
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n;
                Entity1_13Types.ObjectType objectType;
                byte by = packetWrapper.get(Type.BYTE, 0);
                int n2 = this.this$0.newEntityId(by);
                Entity1_13Types.EntityType entityType = Entity1_13Types.getTypeFromId(n2, false);
                if (entityType.isOrHasParent(Entity1_13Types.EntityType.MINECART_ABSTRACT)) {
                    objectType = Entity1_13Types.ObjectType.MINECART;
                    n = 0;
                    switch (11.$SwitchMap$com$viaversion$viaversion$api$minecraft$entities$Entity1_13Types$EntityType[entityType.ordinal()]) {
                        case 1: {
                            n = 1;
                            break;
                        }
                        case 2: {
                            n = 2;
                            break;
                        }
                        case 3: {
                            n = 3;
                            break;
                        }
                        case 4: {
                            n = 4;
                            break;
                        }
                        case 5: {
                            n = 5;
                            break;
                        }
                        case 6: {
                            n = 6;
                        }
                    }
                    if (n != 0) {
                        packetWrapper.set(Type.INT, 0, n);
                    }
                } else {
                    objectType = Entity1_13Types.ObjectType.fromEntityType(entityType).orElse(null);
                }
                if (objectType == null) {
                    return;
                }
                packetWrapper.set(Type.BYTE, 0, (byte)objectType.getId());
                n = packetWrapper.get(Type.INT, 0);
                if (objectType == Entity1_13Types.ObjectType.FALLING_BLOCK) {
                    int n3 = packetWrapper.get(Type.INT, 0);
                    int n4 = ((Protocol1_13_2To1_14)EntityPackets1_14.access$200(this.this$0)).getMappingData().getNewBlockStateId(n3);
                    packetWrapper.set(Type.INT, 0, n4);
                } else if (entityType.isOrHasParent(Entity1_13Types.EntityType.ABSTRACT_ARROW)) {
                    packetWrapper.set(Type.INT, 0, n + 1);
                }
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.SPAWN_MOB, new PacketHandlers(this){
            final EntityPackets1_14 this$0;
            {
                this.this$0 = entityPackets1_14;
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
                this.map(Types1_14.METADATA_LIST, Types1_13_2.METADATA_LIST);
                this.handler(this::lambda$register$0);
                this.handler(EntityPackets1_14.access$300(this.this$0, Types1_13_2.METADATA_LIST));
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 1);
                EntityType entityType = Entity1_14Types.getTypeFromId(n);
                this.this$0.addTrackedEntity(packetWrapper, packetWrapper.get(Type.VAR_INT, 0), entityType);
                int n2 = this.this$0.newEntityId(n);
                if (n2 == -1) {
                    EntityData entityData = EntityPackets1_14.access$400(this.this$0, entityType);
                    if (entityData == null) {
                        ViaBackwards.getPlatform().getLogger().warning("Could not find 1.13.2 entity type for 1.14 entity type " + n + "/" + entityType);
                        packetWrapper.cancel();
                    } else {
                        packetWrapper.set(Type.VAR_INT, 1, entityData.replacementId());
                    }
                } else {
                    packetWrapper.set(Type.VAR_INT, 1, n2);
                }
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.SPAWN_EXPERIENCE_ORB, new PacketHandlers(this){
            final EntityPackets1_14 this$0;
            {
                this.this$0 = entityPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                this.this$0.addTrackedEntity(packetWrapper, packetWrapper.get(Type.VAR_INT, 0), Entity1_14Types.EXPERIENCE_ORB);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.SPAWN_GLOBAL_ENTITY, new PacketHandlers(this){
            final EntityPackets1_14 this$0;
            {
                this.this$0 = entityPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                this.this$0.addTrackedEntity(packetWrapper, packetWrapper.get(Type.VAR_INT, 0), Entity1_14Types.LIGHTNING_BOLT);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.SPAWN_PAINTING, new PacketHandlers(this){
            final EntityPackets1_14 this$0;
            {
                this.this$0 = entityPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.BYTE);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                this.this$0.addTrackedEntity(packetWrapper, packetWrapper.get(Type.VAR_INT, 0), Entity1_14Types.PAINTING);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.SPAWN_PLAYER, new PacketHandlers(this){
            final EntityPackets1_14 this$0;
            {
                this.this$0 = entityPackets1_14;
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
                this.map(Types1_14.METADATA_LIST, Types1_13_2.METADATA_LIST);
                this.handler(EntityPackets1_14.access$500(this.this$0, Types1_13_2.METADATA_LIST, Entity1_14Types.PLAYER));
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                EntityPackets1_14.access$000(this.this$0).cacheEntityPosition(packetWrapper, true, true);
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_14.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_14.ENTITY_METADATA, Types1_14.METADATA_LIST, Types1_13_2.METADATA_LIST);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.JOIN_GAME, new PacketHandlers(this){
            final EntityPackets1_14 this$0;
            {
                this.this$0 = entityPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(EntityPackets1_14.access$600(this.this$0, Entity1_14Types.PLAYER, Type.INT));
                this.handler(EntityPackets1_14.access$700(this.this$0, 1));
                this.handler(9::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                short s = packetWrapper.user().get(DifficultyStorage.class).getDifficulty();
                packetWrapper.write(Type.UNSIGNED_BYTE, s);
                packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.read(Type.VAR_INT);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.RESPAWN, new PacketHandlers(this){
            final EntityPackets1_14 this$0;
            {
                this.this$0 = entityPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.handler(10::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                int n = packetWrapper.get(Type.INT, 0);
                clientWorld.setEnvironment(n);
                short s = packetWrapper.user().get(DifficultyStorage.class).getDifficulty();
                packetWrapper.write(Type.UNSIGNED_BYTE, s);
                packetWrapper.user().get(ChunkLightStorage.class).clear();
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.mapTypes(Entity1_14Types.values(), Entity1_13Types.EntityType.class);
        this.mapEntityTypeWithData(Entity1_14Types.CAT, Entity1_14Types.OCELOT).jsonName();
        this.mapEntityTypeWithData(Entity1_14Types.TRADER_LLAMA, Entity1_14Types.LLAMA).jsonName();
        this.mapEntityTypeWithData(Entity1_14Types.FOX, Entity1_14Types.WOLF).jsonName();
        this.mapEntityTypeWithData(Entity1_14Types.PANDA, Entity1_14Types.POLAR_BEAR).jsonName();
        this.mapEntityTypeWithData(Entity1_14Types.PILLAGER, Entity1_14Types.VILLAGER).jsonName();
        this.mapEntityTypeWithData(Entity1_14Types.WANDERING_TRADER, Entity1_14Types.VILLAGER).jsonName();
        this.mapEntityTypeWithData(Entity1_14Types.RAVAGER, Entity1_14Types.COW).jsonName();
        this.filter().handler(EntityPackets1_14::lambda$registerRewrites$1);
        this.registerMetaTypeHandler(Types1_13_2.META_TYPES.itemType, Types1_13_2.META_TYPES.blockStateType, null, null, Types1_13_2.META_TYPES.componentType, Types1_13_2.META_TYPES.optionalComponentType);
        this.filter().type(Entity1_14Types.PILLAGER).cancel(15);
        this.filter().type(Entity1_14Types.FOX).cancel(15);
        this.filter().type(Entity1_14Types.FOX).cancel(16);
        this.filter().type(Entity1_14Types.FOX).cancel(17);
        this.filter().type(Entity1_14Types.FOX).cancel(18);
        this.filter().type(Entity1_14Types.PANDA).cancel(15);
        this.filter().type(Entity1_14Types.PANDA).cancel(16);
        this.filter().type(Entity1_14Types.PANDA).cancel(17);
        this.filter().type(Entity1_14Types.PANDA).cancel(18);
        this.filter().type(Entity1_14Types.PANDA).cancel(19);
        this.filter().type(Entity1_14Types.PANDA).cancel(20);
        this.filter().type(Entity1_14Types.CAT).cancel(18);
        this.filter().type(Entity1_14Types.CAT).cancel(19);
        this.filter().type(Entity1_14Types.CAT).cancel(20);
        this.filter().handler(EntityPackets1_14::lambda$registerRewrites$2);
        this.filter().type(Entity1_14Types.AREA_EFFECT_CLOUD).index(10).handler(this::lambda$registerRewrites$3);
        this.filter().type(Entity1_14Types.FIREWORK_ROCKET).index(8).handler(EntityPackets1_14::lambda$registerRewrites$4);
        this.filter().filterFamily(Entity1_14Types.ABSTRACT_ARROW).removeIndex(9);
        this.filter().type(Entity1_14Types.VILLAGER).cancel(15);
        MetaHandler metaHandler = this::lambda$registerRewrites$5;
        this.filter().type(Entity1_14Types.ZOMBIE_VILLAGER).index(18).handler(metaHandler);
        this.filter().type(Entity1_14Types.VILLAGER).index(16).handler(metaHandler);
        this.filter().filterFamily(Entity1_14Types.ABSTRACT_SKELETON).index(13).handler(EntityPackets1_14::lambda$registerRewrites$6);
        this.filter().filterFamily(Entity1_14Types.ZOMBIE).index(13).handler(EntityPackets1_14::lambda$registerRewrites$7);
        this.filter().filterFamily(Entity1_14Types.ZOMBIE).addIndex(16);
        this.filter().filterFamily(Entity1_14Types.LIVINGENTITY).handler(EntityPackets1_14::lambda$registerRewrites$8);
        this.filter().removeIndex(6);
        this.filter().type(Entity1_14Types.OCELOT).index(13).handler(EntityPackets1_14::lambda$registerRewrites$9);
        this.filter().type(Entity1_14Types.CAT).handler(EntityPackets1_14::lambda$registerRewrites$10);
        this.filter().handler(EntityPackets1_14::lambda$registerRewrites$11);
    }

    public int villagerDataToProfession(VillagerData villagerData) {
        switch (villagerData.profession()) {
            case 1: 
            case 10: 
            case 13: 
            case 14: {
                return 0;
            }
            case 2: 
            case 8: {
                return 1;
            }
            case 3: 
            case 9: {
                return 0;
            }
            case 4: {
                return 1;
            }
            case 5: 
            case 6: 
            case 7: 
            case 12: {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_14Types.getTypeFromId(n);
    }

    private static void lambda$registerRewrites$11(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        if (metadata.metaType().typeId() > 15) {
            throw new IllegalArgumentException("Unhandled metadata: " + metadata);
        }
    }

    private static void lambda$registerRewrites$10(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        if (metaHandlerEvent.index() == 15) {
            metadata.setValue(1);
        } else if (metaHandlerEvent.index() == 13) {
            metadata.setValue((byte)((Byte)metadata.getValue() & 4));
        }
    }

    private static void lambda$registerRewrites$9(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        metaHandlerEvent.setIndex(15);
        metadata.setTypeAndValue(Types1_13_2.META_TYPES.varIntType, 0);
    }

    private static void lambda$registerRewrites$8(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = metaHandlerEvent.index();
        if (n == 12) {
            Position position = (Position)metadata.getValue();
            if (position != null) {
                PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_13.USE_BED, null, metaHandlerEvent.user());
                packetWrapper.write(Type.VAR_INT, metaHandlerEvent.entityId());
                packetWrapper.write(Type.POSITION, position);
                try {
                    packetWrapper.scheduleSend(Protocol1_13_2To1_14.class);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            metaHandlerEvent.cancel();
        } else if (n > 12) {
            metaHandlerEvent.setIndex(n - 1);
        }
    }

    private static void lambda$registerRewrites$7(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        byte by = (Byte)metadata.getValue();
        if ((by & 4) != 0) {
            metaHandlerEvent.createExtraMeta(new Metadata(16, Types1_13_2.META_TYPES.booleanType, true));
        }
    }

    private static void lambda$registerRewrites$6(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        byte by = (Byte)metadata.getValue();
        if ((by & 4) != 0) {
            metaHandlerEvent.createExtraMeta(new Metadata(14, Types1_13_2.META_TYPES.booleanType, true));
        }
    }

    private void lambda$registerRewrites$5(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        VillagerData villagerData = (VillagerData)metadata.getValue();
        metadata.setTypeAndValue(Types1_13_2.META_TYPES.varIntType, this.villagerDataToProfession(villagerData));
        if (metadata.id() == 16) {
            metaHandlerEvent.setIndex(15);
        }
    }

    private static void lambda$registerRewrites$4(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        metadata.setMetaType(Types1_13_2.META_TYPES.varIntType);
        Integer n = (Integer)metadata.getValue();
        if (n == null) {
            metadata.setValue(0);
        }
    }

    private void lambda$registerRewrites$3(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        this.rewriteParticle((Particle)metadata.getValue());
    }

    private static void lambda$registerRewrites$2(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        EntityType entityType = metaHandlerEvent.entityType();
        if (entityType == null) {
            return;
        }
        if (entityType.isOrHasParent(Entity1_14Types.ABSTRACT_ILLAGER_BASE) || entityType == Entity1_14Types.RAVAGER || entityType == Entity1_14Types.WITCH) {
            int n = metaHandlerEvent.index();
            if (n == 14) {
                metaHandlerEvent.cancel();
            } else if (n > 14) {
                metaHandlerEvent.setIndex(n - 1);
            }
        }
    }

    private static void lambda$registerRewrites$1(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = metadata.metaType().typeId();
        if (n <= 15) {
            metadata.setMetaType(Types1_13_2.META_TYPES.byId(n));
        }
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.INT);
        byte by = packetWrapper.passthrough(Type.BYTE);
        if (by != 3) {
            return;
        }
        Object e = this.tracker(packetWrapper.user());
        EntityType entityType = e.entityType(n);
        if (entityType != Entity1_14Types.PLAYER) {
            return;
        }
        for (int i = 0; i <= 5; ++i) {
            PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_13.ENTITY_EQUIPMENT);
            packetWrapper2.write(Type.VAR_INT, n);
            packetWrapper2.write(Type.VAR_INT, i);
            packetWrapper2.write(Type.FLAT_VAR_INT_ITEM, null);
            packetWrapper2.send(Protocol1_13_2To1_14.class);
        }
    }

    static EntityPositionHandler access$000(EntityPackets1_14 entityPackets1_14) {
        return entityPackets1_14.positionHandler;
    }

    static PacketHandler access$100(EntityPackets1_14 entityPackets1_14) {
        return entityPackets1_14.getObjectTrackerHandler();
    }

    static Protocol access$200(EntityPackets1_14 entityPackets1_14) {
        return entityPackets1_14.protocol;
    }

    static PacketHandler access$300(EntityPackets1_14 entityPackets1_14, Type type) {
        return entityPackets1_14.getMobSpawnRewriter(type);
    }

    static EntityData access$400(EntityPackets1_14 entityPackets1_14, EntityType entityType) {
        return entityPackets1_14.entityDataForType(entityType);
    }

    static PacketHandler access$500(EntityPackets1_14 entityPackets1_14, Type type, EntityType entityType) {
        return entityPackets1_14.getTrackerAndMetaHandler(type, entityType);
    }

    static PacketHandler access$600(EntityPackets1_14 entityPackets1_14, EntityType entityType, Type type) {
        return entityPackets1_14.getTrackerHandler(entityType, type);
    }

    static PacketHandler access$700(EntityPackets1_14 entityPackets1_14, int n) {
        return entityPackets1_14.getDimensionHandler(n);
    }
}

