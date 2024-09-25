/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_14_4to1_15.packets;

import java.util.ArrayList;
import nl.matsv.viabackwards.api.exceptions.RemovedValueException;
import nl.matsv.viabackwards.api.rewriters.EntityRewriter;
import nl.matsv.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
import nl.matsv.viabackwards.protocol.protocol1_14_4to1_15.data.EntityTypeMapping;
import nl.matsv.viabackwards.protocol.protocol1_14_4to1_15.data.ImmediateRespawn;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.entities.Entity1_15Types;
import us.myles.ViaVersion.api.entities.EntityType;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.metadata.MetaType;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_14;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.Particle;
import us.myles.ViaVersion.api.type.types.version.Types1_14;
import us.myles.ViaVersion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;

public class EntityPackets1_15
extends EntityRewriter<Protocol1_14_4To1_15> {
    public EntityPackets1_15(Protocol1_14_4To1_15 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_14_4To1_15)this.protocol).registerOutgoing(ClientboundPackets1_15.UPDATE_HEALTH, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    float health = wrapper.passthrough(Type.FLOAT).floatValue();
                    if (health > 0.0f) {
                        return;
                    }
                    if (!wrapper.user().get(ImmediateRespawn.class).isImmediateRespawn()) {
                        return;
                    }
                    PacketWrapper statusPacket = wrapper.create(4);
                    statusPacket.write(Type.VAR_INT, 0);
                    statusPacket.sendToServer(Protocol1_14_4To1_15.class);
                });
            }
        });
        ((Protocol1_14_4To1_15)this.protocol).registerOutgoing(ClientboundPackets1_15.GAME_EVENT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.FLOAT);
                this.handler(wrapper -> {
                    if (wrapper.get(Type.UNSIGNED_BYTE, 0) == 11) {
                        wrapper.user().get(ImmediateRespawn.class).setImmediateRespawn(wrapper.get(Type.FLOAT, 0).floatValue() == 1.0f);
                    }
                });
            }
        });
        this.registerSpawnTrackerWithData(ClientboundPackets1_15.SPAWN_ENTITY, Entity1_15Types.EntityType.FALLING_BLOCK);
        ((Protocol1_14_4To1_15)this.protocol).registerOutgoing(ClientboundPackets1_15.SPAWN_MOB, new PacketRemapper(){

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
                this.create(wrapper -> wrapper.write(Types1_14.METADATA_LIST, new ArrayList()));
                this.handler(wrapper -> {
                    int type = wrapper.get(Type.VAR_INT, 1);
                    Entity1_15Types.EntityType entityType = Entity1_15Types.getTypeFromId(type);
                    EntityPackets1_15.this.addTrackedEntity(wrapper, wrapper.get(Type.VAR_INT, 0), entityType);
                    wrapper.set(Type.VAR_INT, 1, EntityTypeMapping.getOldEntityId(type));
                });
            }
        });
        ((Protocol1_14_4To1_15)this.protocol).registerOutgoing(ClientboundPackets1_15.RESPAWN, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.LONG, Type.NOTHING);
            }
        });
        ((Protocol1_14_4To1_15)this.protocol).registerOutgoing(ClientboundPackets1_15.JOIN_GAME, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.map(Type.LONG, Type.NOTHING);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.handler(EntityPackets1_15.this.getTrackerHandler(Entity1_15Types.EntityType.PLAYER, Type.INT));
                this.handler(wrapper -> wrapper.user().get(ImmediateRespawn.class).setImmediateRespawn(wrapper.read(Type.BOOLEAN)));
            }
        });
        this.registerExtraTracker(ClientboundPackets1_15.SPAWN_EXPERIENCE_ORB, Entity1_15Types.EntityType.EXPERIENCE_ORB);
        this.registerExtraTracker(ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY, Entity1_15Types.EntityType.LIGHTNING_BOLT);
        this.registerExtraTracker(ClientboundPackets1_15.SPAWN_PAINTING, Entity1_15Types.EntityType.PAINTING);
        ((Protocol1_14_4To1_15)this.protocol).registerOutgoing(ClientboundPackets1_15.SPAWN_PLAYER, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.create(wrapper -> wrapper.write(Types1_14.METADATA_LIST, new ArrayList()));
                this.handler(EntityPackets1_15.this.getTrackerHandler(Entity1_15Types.EntityType.PLAYER, Type.VAR_INT));
            }
        });
        this.registerEntityDestroy(ClientboundPackets1_15.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_15.ENTITY_METADATA, Types1_14.METADATA_LIST);
        ((Protocol1_14_4To1_15)this.protocol).registerOutgoing(ClientboundPackets1_15.ENTITY_PROPERTIES, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.handler(wrapper -> {
                    int size;
                    int entityId = wrapper.get(Type.VAR_INT, 0);
                    EntityType entityType = EntityPackets1_15.this.getEntityType(wrapper.user(), entityId);
                    if (entityType != Entity1_15Types.EntityType.BEE) {
                        return;
                    }
                    int newSize = size = wrapper.get(Type.INT, 0).intValue();
                    for (int i = 0; i < size; ++i) {
                        int j;
                        int modSize;
                        String key = wrapper.read(Type.STRING);
                        if (key.equals("generic.flyingSpeed")) {
                            --newSize;
                            wrapper.read(Type.DOUBLE);
                            modSize = wrapper.read(Type.VAR_INT);
                            for (j = 0; j < modSize; ++j) {
                                wrapper.read(Type.UUID);
                                wrapper.read(Type.DOUBLE);
                                wrapper.read(Type.BYTE);
                            }
                            continue;
                        }
                        wrapper.write(Type.STRING, key);
                        wrapper.passthrough(Type.DOUBLE);
                        modSize = wrapper.passthrough(Type.VAR_INT);
                        for (j = 0; j < modSize; ++j) {
                            wrapper.passthrough(Type.UUID);
                            wrapper.passthrough(Type.DOUBLE);
                            wrapper.passthrough(Type.BYTE);
                        }
                    }
                    if (newSize != size) {
                        wrapper.set(Type.INT, 0, newSize);
                    }
                });
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.registerMetaHandler().handle(e -> {
            Metadata meta = e.getData();
            MetaType type = meta.getMetaType();
            if (type == MetaType1_14.Slot) {
                Item item = (Item)meta.getValue();
                meta.setValue(((Protocol1_14_4To1_15)this.protocol).getBlockItemPackets().handleItemToClient(item));
            } else if (type == MetaType1_14.BlockID) {
                int blockstate = (Integer)meta.getValue();
                meta.setValue(((Protocol1_14_4To1_15)this.protocol).getMappingData().getNewBlockStateId(blockstate));
            } else if (type == MetaType1_14.PARTICLE) {
                this.rewriteParticle((Particle)meta.getValue());
            }
            return meta;
        });
        this.registerMetaHandler().filter((EntityType)Entity1_15Types.EntityType.LIVINGENTITY, true).handle(e -> {
            int index = e.getIndex();
            if (index == 12) {
                throw RemovedValueException.EX;
            }
            if (index > 12) {
                e.getData().setId(index - 1);
            }
            return e.getData();
        });
        this.registerMetaHandler().filter((EntityType)Entity1_15Types.EntityType.BEE, 15).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_15Types.EntityType.BEE, 16).removed();
        this.mapEntity(Entity1_15Types.EntityType.BEE, Entity1_15Types.EntityType.PUFFERFISH).jsonName("Bee").spawnMetadata(storage -> {
            storage.add(new Metadata(14, MetaType1_14.Boolean, false));
            storage.add(new Metadata(15, MetaType1_14.VarInt, 2));
        });
        this.registerMetaHandler().filter((EntityType)Entity1_15Types.EntityType.ENDERMAN, 16).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_15Types.EntityType.TRIDENT, 10).removed();
        this.registerMetaHandler().filter(Entity1_15Types.EntityType.WOLF).handle(e -> {
            int index = e.getIndex();
            if (index >= 17) {
                e.getData().setId(index + 1);
            }
            return e.getData();
        });
    }

    @Override
    protected EntityType getTypeFromId(int typeId) {
        return Entity1_15Types.getTypeFromId(typeId);
    }

    @Override
    public int getOldEntityId(int newId) {
        return EntityTypeMapping.getOldEntityId(newId);
    }
}

