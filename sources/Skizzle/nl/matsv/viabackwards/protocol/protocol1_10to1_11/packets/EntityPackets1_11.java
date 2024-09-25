/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_10to1_11.packets;

import java.util.Optional;
import nl.matsv.viabackwards.api.entities.storage.EntityData;
import nl.matsv.viabackwards.api.entities.storage.MetaStorage;
import nl.matsv.viabackwards.api.exceptions.RemovedValueException;
import nl.matsv.viabackwards.api.rewriters.LegacyEntityRewriter;
import nl.matsv.viabackwards.protocol.protocol1_10to1_11.PotionSplashHandler;
import nl.matsv.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
import nl.matsv.viabackwards.protocol.protocol1_10to1_11.storage.ChestedHorseStorage;
import nl.matsv.viabackwards.utils.Block;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.entities.Entity1_11Types;
import us.myles.ViaVersion.api.entities.Entity1_12Types;
import us.myles.ViaVersion.api.entities.EntityType;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_9;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.version.Types1_9;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;

public class EntityPackets1_11
extends LegacyEntityRewriter<Protocol1_10To1_11> {
    public EntityPackets1_11(Protocol1_10To1_11 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_10To1_11)this.protocol).registerOutgoing(ClientboundPackets1_9_3.EFFECT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.handler(wrapper -> {
                    int type = wrapper.get(Type.INT, 0);
                    if (type == 2002 || type == 2007) {
                        int mappedData;
                        if (type == 2007) {
                            wrapper.set(Type.INT, 0, 2002);
                        }
                        if ((mappedData = PotionSplashHandler.getOldData(wrapper.get(Type.INT, 1))) != -1) {
                            wrapper.set(Type.INT, 1, mappedData);
                        }
                    }
                });
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerOutgoing(ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.handler(EntityPackets1_11.this.getObjectTrackerHandler());
                this.handler(EntityPackets1_11.this.getObjectRewriter(id -> Entity1_11Types.ObjectType.findById(id.byteValue()).orElse(null)));
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Optional<Entity1_12Types.ObjectType> type = Entity1_12Types.ObjectType.findById(wrapper.get(Type.BYTE, 0).byteValue());
                        if (type.isPresent() && type.get() == Entity1_12Types.ObjectType.FALLING_BLOCK) {
                            int objectData = wrapper.get(Type.INT, 0);
                            int objType = objectData & 0xFFF;
                            int data = objectData >> 12 & 0xF;
                            Block block = ((Protocol1_10To1_11)EntityPackets1_11.this.getProtocol()).getBlockItemPackets().handleBlock(objType, data);
                            if (block == null) {
                                return;
                            }
                            wrapper.set(Type.INT, 0, block.getId() | block.getData() << 12);
                        }
                    }
                });
            }
        });
        this.registerExtraTracker(ClientboundPackets1_9_3.SPAWN_EXPERIENCE_ORB, Entity1_11Types.EntityType.EXPERIENCE_ORB);
        this.registerExtraTracker(ClientboundPackets1_9_3.SPAWN_GLOBAL_ENTITY, Entity1_11Types.EntityType.WEATHER);
        ((Protocol1_10To1_11)this.protocol).registerOutgoing(ClientboundPackets1_9_3.SPAWN_MOB, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map((Type)Type.VAR_INT, Type.UNSIGNED_BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_9.METADATA_LIST);
                this.handler(EntityPackets1_11.this.getTrackerHandler(Type.UNSIGNED_BYTE, 0));
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityId = wrapper.get(Type.VAR_INT, 0);
                        EntityType type = EntityPackets1_11.this.getEntityType(wrapper.user(), entityId);
                        MetaStorage storage = new MetaStorage(wrapper.get(Types1_9.METADATA_LIST, 0));
                        EntityPackets1_11.this.handleMeta(wrapper.user(), wrapper.get(Type.VAR_INT, 0), storage);
                        EntityData entityData = EntityPackets1_11.this.getEntityData(type);
                        if (entityData != null) {
                            wrapper.set(Type.UNSIGNED_BYTE, 0, (short)entityData.getReplacementId());
                            if (entityData.hasBaseMeta()) {
                                entityData.getDefaultMeta().createMeta(storage);
                            }
                        }
                        wrapper.set(Types1_9.METADATA_LIST, 0, storage.getMetaDataList());
                    }
                });
            }
        });
        this.registerExtraTracker(ClientboundPackets1_9_3.SPAWN_PAINTING, Entity1_11Types.EntityType.PAINTING);
        this.registerJoinGame(ClientboundPackets1_9_3.JOIN_GAME, Entity1_11Types.EntityType.PLAYER);
        this.registerRespawn(ClientboundPackets1_9_3.RESPAWN);
        ((Protocol1_10To1_11)this.protocol).registerOutgoing(ClientboundPackets1_9_3.SPAWN_PLAYER, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_9.METADATA_LIST);
                this.handler(EntityPackets1_11.this.getTrackerAndMetaHandler(Types1_9.METADATA_LIST, Entity1_11Types.EntityType.PLAYER));
            }
        });
        this.registerEntityDestroy(ClientboundPackets1_9_3.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
        ((Protocol1_10To1_11)this.protocol).registerOutgoing(ClientboundPackets1_9_3.ENTITY_STATUS, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        byte b = wrapper.get(Type.BYTE, 0);
                        if (b == 35) {
                            wrapper.clearPacket();
                            wrapper.setId(30);
                            wrapper.write(Type.UNSIGNED_BYTE, (short)10);
                            wrapper.write(Type.FLOAT, Float.valueOf(0.0f));
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.mapEntity(Entity1_11Types.EntityType.ELDER_GUARDIAN, Entity1_11Types.EntityType.GUARDIAN);
        this.mapEntity(Entity1_11Types.EntityType.WITHER_SKELETON, Entity1_11Types.EntityType.SKELETON).mobName("Wither Skeleton").spawnMetadata(storage -> storage.add(this.getSkeletonTypeMeta(1)));
        this.mapEntity(Entity1_11Types.EntityType.STRAY, Entity1_11Types.EntityType.SKELETON).mobName("Stray").spawnMetadata(storage -> storage.add(this.getSkeletonTypeMeta(2)));
        this.mapEntity(Entity1_11Types.EntityType.HUSK, Entity1_11Types.EntityType.ZOMBIE).mobName("Husk").spawnMetadata(storage -> this.handleZombieType(storage, 6));
        this.mapEntity(Entity1_11Types.EntityType.ZOMBIE_VILLAGER, Entity1_11Types.EntityType.ZOMBIE).spawnMetadata(storage -> this.handleZombieType(storage, 1));
        this.mapEntity(Entity1_11Types.EntityType.HORSE, Entity1_11Types.EntityType.HORSE).spawnMetadata(storage -> storage.add(this.getHorseMetaType(0)));
        this.mapEntity(Entity1_11Types.EntityType.DONKEY, Entity1_11Types.EntityType.HORSE).spawnMetadata(storage -> storage.add(this.getHorseMetaType(1)));
        this.mapEntity(Entity1_11Types.EntityType.MULE, Entity1_11Types.EntityType.HORSE).spawnMetadata(storage -> storage.add(this.getHorseMetaType(2)));
        this.mapEntity(Entity1_11Types.EntityType.SKELETON_HORSE, Entity1_11Types.EntityType.HORSE).spawnMetadata(storage -> storage.add(this.getHorseMetaType(4)));
        this.mapEntity(Entity1_11Types.EntityType.ZOMBIE_HORSE, Entity1_11Types.EntityType.HORSE).spawnMetadata(storage -> storage.add(this.getHorseMetaType(3)));
        this.mapEntity(Entity1_11Types.EntityType.EVOCATION_FANGS, Entity1_11Types.EntityType.SHULKER);
        this.mapEntity(Entity1_11Types.EntityType.EVOCATION_ILLAGER, Entity1_11Types.EntityType.VILLAGER).mobName("Evoker");
        this.mapEntity(Entity1_11Types.EntityType.VEX, Entity1_11Types.EntityType.BAT).mobName("Vex");
        this.mapEntity(Entity1_11Types.EntityType.VINDICATION_ILLAGER, Entity1_11Types.EntityType.VILLAGER).mobName("Vindicator").spawnMetadata(storage -> storage.add(new Metadata(13, MetaType1_9.VarInt, 4)));
        this.mapEntity(Entity1_11Types.EntityType.LIAMA, Entity1_11Types.EntityType.HORSE).mobName("Llama").spawnMetadata(storage -> storage.add(this.getHorseMetaType(1)));
        this.mapEntity(Entity1_11Types.EntityType.LIAMA_SPIT, Entity1_11Types.EntityType.SNOWBALL);
        this.mapObjectType(Entity1_11Types.ObjectType.LIAMA_SPIT, Entity1_11Types.ObjectType.SNOWBALL, -1);
        this.mapObjectType(Entity1_11Types.ObjectType.EVOCATION_FANGS, Entity1_11Types.ObjectType.FALLING_BLOCK, 4294);
        this.registerMetaHandler().filter(Entity1_11Types.EntityType.GUARDIAN, true, 12).handle(e -> {
            int bitmask;
            Metadata data = e.getData();
            boolean b = (Boolean)data.getValue();
            int n = bitmask = b ? 2 : 0;
            if (e.getEntity().getType().is((EntityType)Entity1_11Types.EntityType.ELDER_GUARDIAN)) {
                bitmask |= 4;
            }
            data.setMetaType(MetaType1_9.Byte);
            data.setValue((byte)bitmask);
            return data;
        });
        this.registerMetaHandler().filter(Entity1_11Types.EntityType.ABSTRACT_SKELETON, true, 12).handleIndexChange(13);
        this.registerMetaHandler().filter((EntityType)Entity1_11Types.EntityType.ZOMBIE, true).handle(e -> {
            Metadata data = e.getData();
            switch (data.getId()) {
                case 13: {
                    throw RemovedValueException.EX;
                }
                case 14: {
                    data.setId(15);
                    break;
                }
                case 15: {
                    data.setId(14);
                    break;
                }
                case 16: {
                    data.setId(13);
                    data.setValue(1 + (Integer)data.getValue());
                }
            }
            return data;
        });
        this.registerMetaHandler().filter((EntityType)Entity1_11Types.EntityType.EVOCATION_ILLAGER, 12).handle(e -> {
            Metadata data = e.getData();
            data.setId(13);
            data.setMetaType(MetaType1_9.VarInt);
            data.setValue(((Byte)data.getValue()).intValue());
            return data;
        });
        this.registerMetaHandler().filter((EntityType)Entity1_11Types.EntityType.VEX, 12).handle(e -> {
            Metadata data = e.getData();
            data.setValue((byte)0);
            return data;
        });
        this.registerMetaHandler().filter((EntityType)Entity1_11Types.EntityType.VINDICATION_ILLAGER, 12).handle(e -> {
            Metadata data = e.getData();
            data.setId(13);
            data.setMetaType(MetaType1_9.VarInt);
            data.setValue(((Number)data.getValue()).intValue() == 1 ? 2 : 4);
            return data;
        });
        this.registerMetaHandler().filter(Entity1_11Types.EntityType.ABSTRACT_HORSE, true, 13).handle(e -> {
            Metadata data = e.getData();
            byte b = (Byte)data.getValue();
            if (e.getEntity().has(ChestedHorseStorage.class) && e.getEntity().get(ChestedHorseStorage.class).isChested()) {
                b = (byte)(b | 8);
                data.setValue(b);
            }
            return data;
        });
        this.registerMetaHandler().filter((EntityType)Entity1_11Types.EntityType.CHESTED_HORSE, true).handle(e -> {
            if (!e.getEntity().has(ChestedHorseStorage.class)) {
                e.getEntity().put(new ChestedHorseStorage());
            }
            return e.getData();
        });
        this.registerMetaHandler().filter((EntityType)Entity1_11Types.EntityType.HORSE, 16).handleIndexChange(17);
        this.registerMetaHandler().filter(Entity1_11Types.EntityType.CHESTED_HORSE, true, 15).handle(e -> {
            ChestedHorseStorage storage = e.getEntity().get(ChestedHorseStorage.class);
            boolean b = (Boolean)e.getData().getValue();
            storage.setChested(b);
            throw RemovedValueException.EX;
        });
        this.registerMetaHandler().filter(Entity1_11Types.EntityType.LIAMA).handle(e -> {
            Metadata data = e.getData();
            ChestedHorseStorage storage = e.getEntity().get(ChestedHorseStorage.class);
            int index = e.getIndex();
            switch (index) {
                case 16: {
                    storage.setLiamaStrength((Integer)data.getValue());
                    throw RemovedValueException.EX;
                }
                case 17: {
                    storage.setLiamaCarpetColor((Integer)data.getValue());
                    throw RemovedValueException.EX;
                }
                case 18: {
                    storage.setLiamaVariant((Integer)data.getValue());
                    throw RemovedValueException.EX;
                }
            }
            return e.getData();
        });
        this.registerMetaHandler().filter(Entity1_11Types.EntityType.ABSTRACT_HORSE, true, 14).handleIndexChange(16);
        this.registerMetaHandler().filter((EntityType)Entity1_11Types.EntityType.VILLAGER, 13).handle(e -> {
            Metadata data = e.getData();
            if ((Integer)data.getValue() == 5) {
                data.setValue(0);
            }
            return data;
        });
        this.registerMetaHandler().filter((EntityType)Entity1_11Types.EntityType.SHULKER, 15).removed();
    }

    private Metadata getSkeletonTypeMeta(int type) {
        return new Metadata(12, MetaType1_9.VarInt, type);
    }

    private Metadata getZombieTypeMeta(int type) {
        return new Metadata(13, MetaType1_9.VarInt, type);
    }

    private void handleZombieType(MetaStorage storage, int type) {
        Metadata meta = storage.get(13);
        if (meta == null) {
            storage.add(this.getZombieTypeMeta(type));
        }
    }

    private Metadata getHorseMetaType(int type) {
        return new Metadata(14, MetaType1_9.VarInt, type);
    }

    @Override
    protected EntityType getTypeFromId(int typeId) {
        return Entity1_11Types.getTypeFromId(typeId, false);
    }

    @Override
    protected EntityType getObjectTypeFromId(int typeId) {
        return Entity1_11Types.getTypeFromId(typeId, true);
    }
}

