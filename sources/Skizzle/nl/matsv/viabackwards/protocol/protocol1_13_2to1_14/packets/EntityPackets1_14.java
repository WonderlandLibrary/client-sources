/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_13_2to1_14.packets;

import nl.matsv.viabackwards.ViaBackwards;
import nl.matsv.viabackwards.api.entities.meta.MetaHandler;
import nl.matsv.viabackwards.api.entities.storage.EntityData;
import nl.matsv.viabackwards.api.entities.storage.EntityPositionHandler;
import nl.matsv.viabackwards.api.entities.storage.EntityTracker;
import nl.matsv.viabackwards.api.exceptions.RemovedValueException;
import nl.matsv.viabackwards.api.rewriters.LegacyEntityRewriter;
import nl.matsv.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import nl.matsv.viabackwards.protocol.protocol1_13_2to1_14.storage.ChunkLightStorage;
import nl.matsv.viabackwards.protocol.protocol1_13_2to1_14.storage.EntityPositionStorage1_14;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.entities.Entity1_13Types;
import us.myles.ViaVersion.api.entities.Entity1_14Types;
import us.myles.ViaVersion.api.entities.EntityType;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.api.minecraft.VillagerData;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.metadata.MetaType;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_13_2;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.Particle;
import us.myles.ViaVersion.api.type.types.version.Types1_13_2;
import us.myles.ViaVersion.api.type.types.version.Types1_14;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;

public class EntityPackets1_14
extends LegacyEntityRewriter<Protocol1_13_2To1_14> {
    private EntityPositionHandler positionHandler;

    public EntityPackets1_14(Protocol1_13_2To1_14 protocol) {
        super(protocol, MetaType1_13_2.OptChat, MetaType1_13_2.Boolean);
    }

    @Override
    protected void addTrackedEntity(PacketWrapper wrapper, int entityId, EntityType type) throws Exception {
        super.addTrackedEntity(wrapper, entityId, type);
        if (type == Entity1_14Types.EntityType.PAINTING) {
            Position position = wrapper.get(Type.POSITION, 0);
            this.positionHandler.cacheEntityPosition(wrapper, position.getX(), position.getY(), position.getZ(), true, false);
        } else if (wrapper.getId() != 37) {
            this.positionHandler.cacheEntityPosition(wrapper, true, false);
        }
    }

    @Override
    protected void registerPackets() {
        this.positionHandler = new EntityPositionHandler(this, EntityPositionStorage1_14.class, EntityPositionStorage1_14::new);
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.ENTITY_STATUS, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    int entityId = wrapper.passthrough(Type.INT);
                    byte status = wrapper.passthrough(Type.BYTE);
                    if (status != 3) {
                        return;
                    }
                    EntityTracker.ProtocolEntityTracker tracker = EntityPackets1_14.this.getEntityTracker(wrapper.user());
                    EntityType entityType = tracker.getEntityType(entityId);
                    if (entityType != Entity1_14Types.EntityType.PLAYER) {
                        return;
                    }
                    for (int i = 0; i <= 5; ++i) {
                        PacketWrapper equipmentPacket = wrapper.create(66);
                        equipmentPacket.write(Type.VAR_INT, entityId);
                        equipmentPacket.write(Type.VAR_INT, i);
                        equipmentPacket.write(Type.FLAT_VAR_INT_ITEM, null);
                        equipmentPacket.send(Protocol1_13_2To1_14.class, true, true);
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.ENTITY_TELEPORT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.handler(wrapper -> EntityPackets1_14.this.positionHandler.cacheEntityPosition(wrapper, false, false));
            }
        });
        PacketRemapper relativeMoveHandler = new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        double x = (double)wrapper.get(Type.SHORT, 0).shortValue() / 4096.0;
                        double y = (double)wrapper.get(Type.SHORT, 1).shortValue() / 4096.0;
                        double z = (double)wrapper.get(Type.SHORT, 2).shortValue() / 4096.0;
                        EntityPackets1_14.this.positionHandler.cacheEntityPosition(wrapper, x, y, z, false, true);
                    }
                });
            }
        };
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.ENTITY_POSITION, relativeMoveHandler);
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.ENTITY_POSITION_AND_ROTATION, relativeMoveHandler);
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.SPAWN_ENTITY, new PacketRemapper(){

            @Override
            public void registerMap() {
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
                this.handler(EntityPackets1_14.this.getObjectTrackerHandler());
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int data;
                        Entity1_13Types.ObjectType objectType;
                        byte id = wrapper.get(Type.BYTE, 0);
                        int mappedId = EntityPackets1_14.this.getOldEntityId(id);
                        Entity1_13Types.EntityType entityType = Entity1_13Types.getTypeFromId(mappedId, false);
                        if (entityType.isOrHasParent(Entity1_13Types.EntityType.MINECART_ABSTRACT)) {
                            objectType = Entity1_13Types.ObjectType.MINECART;
                            data = 0;
                            switch (entityType) {
                                case CHEST_MINECART: {
                                    data = 1;
                                    break;
                                }
                                case FURNACE_MINECART: {
                                    data = 2;
                                    break;
                                }
                                case TNT_MINECART: {
                                    data = 3;
                                    break;
                                }
                                case SPAWNER_MINECART: {
                                    data = 4;
                                    break;
                                }
                                case HOPPER_MINECART: {
                                    data = 5;
                                    break;
                                }
                                case COMMAND_BLOCK_MINECART: {
                                    data = 6;
                                }
                            }
                            if (data != 0) {
                                wrapper.set(Type.INT, 0, data);
                            }
                        } else {
                            objectType = Entity1_13Types.ObjectType.fromEntityType(entityType).orElse(null);
                        }
                        if (objectType == null) {
                            return;
                        }
                        wrapper.set(Type.BYTE, 0, (byte)objectType.getId());
                        data = wrapper.get(Type.INT, 0);
                        if (objectType == Entity1_13Types.ObjectType.FALLING_BLOCK) {
                            int blockState = wrapper.get(Type.INT, 0);
                            int combined = ((Protocol1_13_2To1_14)EntityPackets1_14.this.protocol).getMappingData().getNewBlockStateId(blockState);
                            wrapper.set(Type.INT, 0, combined);
                        } else if (entityType.isOrHasParent(Entity1_13Types.EntityType.ABSTRACT_ARROW)) {
                            wrapper.set(Type.INT, 0, data + 1);
                        }
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.SPAWN_MOB, new PacketRemapper(){

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
                this.map(Types1_14.METADATA_LIST, Types1_13_2.METADATA_LIST);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int type = wrapper.get(Type.VAR_INT, 1);
                        Entity1_14Types.EntityType entityType = Entity1_14Types.getTypeFromId(type);
                        EntityPackets1_14.this.addTrackedEntity(wrapper, wrapper.get(Type.VAR_INT, 0), entityType);
                        int oldId = EntityPackets1_14.this.typeMapping.get(type);
                        if (oldId == -1) {
                            EntityData entityData = EntityPackets1_14.this.getEntityData(entityType);
                            if (entityData == null) {
                                ViaBackwards.getPlatform().getLogger().warning("Could not find 1.13.2 entity type for 1.14 entity type " + type + "/" + entityType);
                                wrapper.cancel();
                            } else {
                                wrapper.set(Type.VAR_INT, 1, entityData.getReplacementId());
                            }
                        } else {
                            wrapper.set(Type.VAR_INT, 1, oldId);
                        }
                    }
                });
                this.handler(EntityPackets1_14.this.getMobSpawnRewriter(Types1_13_2.METADATA_LIST));
            }
        });
        ((Protocol1_13_2To1_14)this.getProtocol()).registerOutgoing(ClientboundPackets1_14.SPAWN_EXPERIENCE_ORB, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.handler(wrapper -> EntityPackets1_14.this.addTrackedEntity(wrapper, wrapper.get(Type.VAR_INT, 0), Entity1_14Types.EntityType.EXPERIENCE_ORB));
            }
        });
        ((Protocol1_13_2To1_14)this.getProtocol()).registerOutgoing(ClientboundPackets1_14.SPAWN_GLOBAL_ENTITY, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.handler(wrapper -> EntityPackets1_14.this.addTrackedEntity(wrapper, wrapper.get(Type.VAR_INT, 0), Entity1_14Types.EntityType.LIGHTNING_BOLT));
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.SPAWN_PAINTING, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.BYTE);
                this.handler(wrapper -> EntityPackets1_14.this.addTrackedEntity(wrapper, wrapper.get(Type.VAR_INT, 0), Entity1_14Types.EntityType.PAINTING));
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.SPAWN_PLAYER, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_14.METADATA_LIST, Types1_13_2.METADATA_LIST);
                this.handler(EntityPackets1_14.this.getTrackerAndMetaHandler(Types1_13_2.METADATA_LIST, Entity1_14Types.EntityType.PLAYER));
                this.handler(wrapper -> EntityPackets1_14.this.positionHandler.cacheEntityPosition(wrapper, true, false));
            }
        });
        this.registerEntityDestroy(ClientboundPackets1_14.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_14.ENTITY_METADATA, Types1_14.METADATA_LIST, Types1_13_2.METADATA_LIST);
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.JOIN_GAME, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(EntityPackets1_14.this.getTrackerHandler(Entity1_14Types.EntityType.PLAYER, Type.INT));
                this.handler(EntityPackets1_14.this.getDimensionHandler(1));
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.write(Type.UNSIGNED_BYTE, (short)0);
                        wrapper.passthrough(Type.UNSIGNED_BYTE);
                        wrapper.passthrough(Type.STRING);
                        wrapper.read(Type.VAR_INT);
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.RESPAWN, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
                        int dimensionId = wrapper.get(Type.INT, 0);
                        clientWorld.setEnvironment(dimensionId);
                        wrapper.write(Type.UNSIGNED_BYTE, (short)0);
                        wrapper.user().get(ChunkLightStorage.class).clear();
                    }
                });
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.mapTypes(Entity1_14Types.EntityType.values(), Entity1_13Types.EntityType.class);
        this.mapEntity(Entity1_14Types.EntityType.CAT, Entity1_14Types.EntityType.OCELOT).jsonName("Cat");
        this.mapEntity(Entity1_14Types.EntityType.TRADER_LLAMA, Entity1_14Types.EntityType.LLAMA).jsonName("Trader Llama");
        this.mapEntity(Entity1_14Types.EntityType.FOX, Entity1_14Types.EntityType.WOLF).jsonName("Fox");
        this.mapEntity(Entity1_14Types.EntityType.PANDA, Entity1_14Types.EntityType.POLAR_BEAR).jsonName("Panda");
        this.mapEntity(Entity1_14Types.EntityType.PILLAGER, Entity1_14Types.EntityType.VILLAGER).jsonName("Pillager");
        this.mapEntity(Entity1_14Types.EntityType.WANDERING_TRADER, Entity1_14Types.EntityType.VILLAGER).jsonName("Wandering Trader");
        this.mapEntity(Entity1_14Types.EntityType.RAVAGER, Entity1_14Types.EntityType.COW).jsonName("Ravager");
        this.registerMetaHandler().handle(e -> {
            MetaType type;
            Metadata meta = e.getData();
            int typeId = meta.getMetaType().getTypeID();
            if (typeId <= 15) {
                meta.setMetaType(MetaType1_13_2.byId(typeId));
            }
            if ((type = meta.getMetaType()) == MetaType1_13_2.Slot) {
                Item item = (Item)meta.getValue();
                meta.setValue(((Protocol1_13_2To1_14)this.getProtocol()).getBlockItemPackets().handleItemToClient(item));
            } else if (type == MetaType1_13_2.BlockID) {
                int blockstate = (Integer)meta.getValue();
                meta.setValue(((Protocol1_13_2To1_14)this.protocol).getMappingData().getNewBlockStateId(blockstate));
            }
            return meta;
        });
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.PILLAGER, 15).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.FOX, 15).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.FOX, 16).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.FOX, 17).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.FOX, 18).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.PANDA, 15).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.PANDA, 16).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.PANDA, 17).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.PANDA, 18).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.PANDA, 19).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.PANDA, 20).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.CAT, 18).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.CAT, 19).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.CAT, 20).removed();
        this.registerMetaHandler().handle(e -> {
            EntityType type = e.getEntity().getType();
            Metadata meta = e.getData();
            if (type.isOrHasParent(Entity1_14Types.EntityType.ABSTRACT_ILLAGER_BASE) || type == Entity1_14Types.EntityType.RAVAGER || type == Entity1_14Types.EntityType.WITCH) {
                int index = e.getIndex();
                if (index == 14) {
                    throw RemovedValueException.EX;
                }
                if (index > 14) {
                    meta.setId(index - 1);
                }
            }
            return meta;
        });
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.AREA_EFFECT_CLOUD, 10).handle(e -> {
            Metadata meta = e.getData();
            this.rewriteParticle((Particle)meta.getValue());
            return meta;
        });
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.FIREWORK_ROCKET, 8).handle(e -> {
            Metadata meta = e.getData();
            meta.setMetaType(MetaType1_13_2.VarInt);
            Integer value = (Integer)meta.getValue();
            if (value == null) {
                meta.setValue(0);
            }
            return meta;
        });
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.ABSTRACT_ARROW, true).handle(e -> {
            Metadata meta = e.getData();
            int index = e.getIndex();
            if (index == 9) {
                throw RemovedValueException.EX;
            }
            if (index > 9) {
                meta.setId(index - 1);
            }
            return meta;
        });
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.VILLAGER, 15).removed();
        MetaHandler villagerDataHandler = e -> {
            Metadata meta = e.getData();
            VillagerData villagerData = (VillagerData)meta.getValue();
            meta.setValue(this.villagerDataToProfession(villagerData));
            meta.setMetaType(MetaType1_13_2.VarInt);
            if (meta.getId() == 16) {
                meta.setId(15);
            }
            return meta;
        };
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.ZOMBIE_VILLAGER, 18).handle(villagerDataHandler);
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.VILLAGER, 16).handle(villagerDataHandler);
        this.registerMetaHandler().filter(Entity1_14Types.EntityType.ABSTRACT_SKELETON, true, 13).handle(e -> {
            byte value = (Byte)e.getData().getValue();
            if ((value & 4) != 0) {
                e.createMeta(new Metadata(14, MetaType1_13_2.Boolean, true));
            }
            return e.getData();
        });
        this.registerMetaHandler().filter(Entity1_14Types.EntityType.ZOMBIE, true, 13).handle(e -> {
            byte value = (Byte)e.getData().getValue();
            if ((value & 4) != 0) {
                e.createMeta(new Metadata(16, MetaType1_13_2.Boolean, true));
            }
            return e.getData();
        });
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.ZOMBIE, true).handle(e -> {
            Metadata meta = e.getData();
            int index = e.getIndex();
            if (index >= 16) {
                meta.setId(index + 1);
            }
            return meta;
        });
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.LIVINGENTITY, true).handle(e -> {
            Metadata meta = e.getData();
            int index = e.getIndex();
            if (index == 12) {
                Position position = (Position)meta.getValue();
                if (position != null) {
                    PacketWrapper wrapper = new PacketWrapper(51, null, e.getUser());
                    wrapper.write(Type.VAR_INT, e.getEntity().getEntityId());
                    wrapper.write(Type.POSITION, position);
                    try {
                        wrapper.send(Protocol1_13_2To1_14.class);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                throw RemovedValueException.EX;
            }
            if (index > 12) {
                meta.setId(index - 1);
            }
            return meta;
        });
        this.registerMetaHandler().handle(e -> {
            Metadata meta = e.getData();
            int index = e.getIndex();
            if (index == 6) {
                throw RemovedValueException.EX;
            }
            if (index > 6) {
                meta.setId(index - 1);
            }
            return meta;
        });
        this.registerMetaHandler().handle(e -> {
            Metadata meta = e.getData();
            int typeId = meta.getMetaType().getTypeID();
            if (typeId > 15) {
                ViaBackwards.getPlatform().getLogger().warning("New 1.14 metadata was not handled: " + meta + " entity: " + e.getEntity().getType());
                return null;
            }
            return meta;
        });
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.OCELOT, 13).handle(e -> {
            Metadata meta = e.getData();
            meta.setId(15);
            meta.setMetaType(MetaType1_13_2.VarInt);
            meta.setValue(0);
            return meta;
        });
        this.registerMetaHandler().filter(Entity1_14Types.EntityType.CAT).handle(e -> {
            Metadata meta = e.getData();
            if (meta.getId() == 15) {
                meta.setValue(1);
            } else if (meta.getId() == 13) {
                meta.setValue((byte)((Byte)meta.getValue() & 4));
            }
            return meta;
        });
    }

    public int villagerDataToProfession(VillagerData data) {
        switch (data.getProfession()) {
            case 1: 
            case 10: 
            case 13: 
            case 14: {
                return 3;
            }
            case 2: 
            case 8: {
                return 4;
            }
            case 3: 
            case 9: {
                return 1;
            }
            case 4: {
                return 2;
            }
            case 5: 
            case 6: 
            case 7: 
            case 12: {
                return 0;
            }
        }
        return 5;
    }

    @Override
    protected EntityType getTypeFromId(int typeId) {
        return Entity1_14Types.getTypeFromId(typeId);
    }
}

