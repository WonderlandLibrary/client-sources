/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_9_4to1_10.packets;

import java.util.Optional;
import nl.matsv.viabackwards.api.entities.storage.EntityData;
import nl.matsv.viabackwards.api.entities.storage.MetaStorage;
import nl.matsv.viabackwards.api.exceptions.RemovedValueException;
import nl.matsv.viabackwards.api.rewriters.LegacyEntityRewriter;
import nl.matsv.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10;
import nl.matsv.viabackwards.utils.Block;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.entities.Entity1_10Types;
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

public class EntityPackets1_10
extends LegacyEntityRewriter<Protocol1_9_4To1_10> {
    public EntityPackets1_10(Protocol1_9_4To1_10 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_9_4To1_10)this.protocol).registerOutgoing(ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketRemapper(){

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
                this.handler(EntityPackets1_10.this.getObjectTrackerHandler());
                this.handler(EntityPackets1_10.this.getObjectRewriter(id -> Entity1_11Types.ObjectType.findById(id.byteValue()).orElse(null)));
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Optional<Entity1_12Types.ObjectType> type = Entity1_12Types.ObjectType.findById(wrapper.get(Type.BYTE, 0).byteValue());
                        if (type.isPresent() && type.get() == Entity1_12Types.ObjectType.FALLING_BLOCK) {
                            int objectData = wrapper.get(Type.INT, 0);
                            int objType = objectData & 0xFFF;
                            int data = objectData >> 12 & 0xF;
                            Block block = ((Protocol1_9_4To1_10)EntityPackets1_10.this.getProtocol()).getBlockItemPackets().handleBlock(objType, data);
                            if (block == null) {
                                return;
                            }
                            wrapper.set(Type.INT, 0, block.getId() | block.getData() << 12);
                        }
                    }
                });
            }
        });
        this.registerExtraTracker(ClientboundPackets1_9_3.SPAWN_EXPERIENCE_ORB, Entity1_10Types.EntityType.EXPERIENCE_ORB);
        this.registerExtraTracker(ClientboundPackets1_9_3.SPAWN_GLOBAL_ENTITY, Entity1_10Types.EntityType.WEATHER);
        ((Protocol1_9_4To1_10)this.protocol).registerOutgoing(ClientboundPackets1_9_3.SPAWN_MOB, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.UNSIGNED_BYTE);
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
                this.handler(EntityPackets1_10.this.getTrackerHandler(Type.UNSIGNED_BYTE, 0));
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityId = wrapper.get(Type.VAR_INT, 0);
                        EntityType type = EntityPackets1_10.this.getEntityType(wrapper.user(), entityId);
                        MetaStorage storage = new MetaStorage(wrapper.get(Types1_9.METADATA_LIST, 0));
                        EntityPackets1_10.this.handleMeta(wrapper.user(), wrapper.get(Type.VAR_INT, 0), storage);
                        EntityData entityData = EntityPackets1_10.this.getEntityData(type);
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
        this.registerExtraTracker(ClientboundPackets1_9_3.SPAWN_PAINTING, Entity1_10Types.EntityType.PAINTING);
        this.registerJoinGame(ClientboundPackets1_9_3.JOIN_GAME, Entity1_10Types.EntityType.PLAYER);
        this.registerRespawn(ClientboundPackets1_9_3.RESPAWN);
        ((Protocol1_9_4To1_10)this.protocol).registerOutgoing(ClientboundPackets1_9_3.SPAWN_PLAYER, new PacketRemapper(){

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
                this.handler(EntityPackets1_10.this.getTrackerAndMetaHandler(Types1_9.METADATA_LIST, Entity1_11Types.EntityType.PLAYER));
            }
        });
        this.registerEntityDestroy(ClientboundPackets1_9_3.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
    }

    @Override
    protected void registerRewrites() {
        this.mapEntity(Entity1_10Types.EntityType.POLAR_BEAR, Entity1_10Types.EntityType.SHEEP).mobName("Polar Bear");
        this.registerMetaHandler().filter((EntityType)Entity1_10Types.EntityType.POLAR_BEAR, 13).handle(e -> {
            Metadata data = e.getData();
            boolean b = (Boolean)data.getValue();
            data.setMetaType(MetaType1_9.Byte);
            data.setValue(b ? (byte)14 : (byte)0);
            return data;
        });
        this.registerMetaHandler().filter((EntityType)Entity1_10Types.EntityType.ZOMBIE, 13).handle(e -> {
            Metadata data = e.getData();
            if ((Integer)data.getValue() == 6) {
                data.setValue(0);
            }
            return data;
        });
        this.registerMetaHandler().filter((EntityType)Entity1_10Types.EntityType.SKELETON, 12).handle(e -> {
            Metadata data = e.getData();
            if ((Integer)data.getValue() == 2) {
                data.setValue(0);
            }
            return data;
        });
        this.registerMetaHandler().handle(e -> {
            Metadata data = e.getData();
            if (data.getId() == 5) {
                throw RemovedValueException.EX;
            }
            if (data.getId() >= 5) {
                data.setId(data.getId() - 1);
            }
            return data;
        });
    }

    @Override
    protected EntityType getTypeFromId(int typeId) {
        return Entity1_10Types.getTypeFromId(typeId, false);
    }

    @Override
    protected EntityType getObjectTypeFromId(int typeId) {
        return Entity1_10Types.getTypeFromId(typeId, true);
    }
}

