/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_13to1_13_1.packets;

import nl.matsv.viabackwards.ViaBackwards;
import nl.matsv.viabackwards.api.entities.storage.MetaStorage;
import nl.matsv.viabackwards.api.rewriters.LegacyEntityRewriter;
import nl.matsv.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
import nl.matsv.viabackwards.protocol.protocol1_13to1_13_1.packets.InventoryPackets1_13_1;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.entities.Entity1_13Types;
import us.myles.ViaVersion.api.entities.EntityType;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_13;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.Particle;
import us.myles.ViaVersion.api.type.types.version.Types1_13;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;

public class EntityPackets1_13_1
extends LegacyEntityRewriter<Protocol1_13To1_13_1> {
    public EntityPackets1_13_1(Protocol1_13To1_13_1 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_13To1_13_1)this.protocol).registerOutgoing(ClientboundPackets1_13.SPAWN_ENTITY, new PacketRemapper(){

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
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityId = wrapper.get(Type.VAR_INT, 0);
                        byte type = wrapper.get(Type.BYTE, 0);
                        Entity1_13Types.EntityType entType = Entity1_13Types.getTypeFromId(type, true);
                        if (entType == null) {
                            ViaBackwards.getPlatform().getLogger().warning("Could not find 1.13 entity type " + type);
                            return;
                        }
                        if (entType.is((EntityType)Entity1_13Types.EntityType.FALLING_BLOCK)) {
                            int data = wrapper.get(Type.INT, 0);
                            wrapper.set(Type.INT, 0, ((Protocol1_13To1_13_1)EntityPackets1_13_1.this.protocol).getMappingData().getNewBlockStateId(data));
                        }
                        EntityPackets1_13_1.this.addTrackedEntity(wrapper, entityId, entType);
                    }
                });
            }
        });
        this.registerExtraTracker(ClientboundPackets1_13.SPAWN_EXPERIENCE_ORB, Entity1_13Types.EntityType.EXPERIENCE_ORB);
        this.registerExtraTracker(ClientboundPackets1_13.SPAWN_GLOBAL_ENTITY, Entity1_13Types.EntityType.LIGHTNING_BOLT);
        ((Protocol1_13To1_13_1)this.protocol).registerOutgoing(ClientboundPackets1_13.SPAWN_MOB, new PacketRemapper(){

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
                this.map(Types1_13.METADATA_LIST);
                this.handler(EntityPackets1_13_1.this.getTrackerHandler());
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        MetaStorage storage = new MetaStorage(wrapper.get(Types1_13.METADATA_LIST, 0));
                        EntityPackets1_13_1.this.handleMeta(wrapper.user(), wrapper.get(Type.VAR_INT, 0), storage);
                        wrapper.set(Types1_13.METADATA_LIST, 0, storage.getMetaDataList());
                    }
                });
            }
        });
        ((Protocol1_13To1_13_1)this.protocol).registerOutgoing(ClientboundPackets1_13.SPAWN_PLAYER, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_13.METADATA_LIST);
                this.handler(EntityPackets1_13_1.this.getTrackerAndMetaHandler(Types1_13.METADATA_LIST, Entity1_13Types.EntityType.PLAYER));
            }
        });
        this.registerExtraTracker(ClientboundPackets1_13.SPAWN_PAINTING, Entity1_13Types.EntityType.PAINTING);
        this.registerJoinGame(ClientboundPackets1_13.JOIN_GAME, Entity1_13Types.EntityType.PLAYER);
        this.registerRespawn(ClientboundPackets1_13.RESPAWN);
        this.registerEntityDestroy(ClientboundPackets1_13.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_13.ENTITY_METADATA, Types1_13.METADATA_LIST);
    }

    @Override
    protected void registerRewrites() {
        this.registerMetaHandler().handle(e -> {
            Metadata meta = e.getData();
            if (meta.getMetaType() == MetaType1_13.Slot) {
                InventoryPackets1_13_1.toClient((Item)meta.getValue());
            } else if (meta.getMetaType() == MetaType1_13.BlockID) {
                int data = (Integer)meta.getValue();
                meta.setValue(((Protocol1_13To1_13_1)this.protocol).getMappingData().getNewBlockStateId(data));
            } else if (meta.getMetaType() == MetaType1_13.PARTICLE) {
                this.rewriteParticle((Particle)meta.getValue());
            }
            return meta;
        });
        this.registerMetaHandler().filter(Entity1_13Types.EntityType.ABSTRACT_ARROW, true, 7).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_13Types.EntityType.SPECTRAL_ARROW, 8).handleIndexChange(7);
        this.registerMetaHandler().filter((EntityType)Entity1_13Types.EntityType.TRIDENT, 8).handleIndexChange(7);
        this.registerMetaHandler().filter(Entity1_13Types.EntityType.MINECART_ABSTRACT, true, 9).handle(e -> {
            Metadata meta = e.getData();
            int data = (Integer)meta.getValue();
            meta.setValue(((Protocol1_13To1_13_1)this.protocol).getMappingData().getNewBlockStateId(data));
            return meta;
        });
    }

    @Override
    protected EntityType getTypeFromId(int typeId) {
        return Entity1_13Types.getTypeFromId(typeId, false);
    }

    @Override
    protected EntityType getObjectTypeFromId(int typeId) {
        return Entity1_13Types.getTypeFromId(typeId, true);
    }
}

