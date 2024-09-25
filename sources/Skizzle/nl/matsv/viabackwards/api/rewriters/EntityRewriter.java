/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.api.rewriters;

import java.util.List;
import nl.matsv.viabackwards.api.BackwardsProtocol;
import nl.matsv.viabackwards.api.entities.storage.EntityData;
import nl.matsv.viabackwards.api.entities.storage.MetaStorage;
import nl.matsv.viabackwards.api.rewriters.EntityRewriterBase;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.entities.EntityType;
import us.myles.ViaVersion.api.minecraft.metadata.MetaType;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_14;
import us.myles.ViaVersion.api.protocol.ClientboundPacketType;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;

public abstract class EntityRewriter<T extends BackwardsProtocol>
extends EntityRewriterBase<T> {
    protected EntityRewriter(T protocol) {
        this(protocol, MetaType1_14.OptChat, MetaType1_14.Boolean);
    }

    protected EntityRewriter(T protocol, MetaType displayType, MetaType displayVisibilityType) {
        super(protocol, displayType, 2, displayVisibilityType, 3);
    }

    public void registerSpawnTrackerWithData(ClientboundPacketType packetType, final EntityType fallingBlockType) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

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
                this.map(Type.INT);
                this.handler(wrapper -> {
                    EntityType entityType = EntityRewriter.this.setOldEntityId(wrapper);
                    if (entityType == fallingBlockType) {
                        int blockState = wrapper.get(Type.INT, 0);
                        wrapper.set(Type.INT, 0, EntityRewriter.this.protocol.getMappingData().getNewBlockStateId(blockState));
                    }
                });
            }
        });
    }

    public void registerSpawnTracker(ClientboundPacketType packetType) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> EntityRewriter.this.setOldEntityId(wrapper));
            }
        });
    }

    private EntityType setOldEntityId(PacketWrapper wrapper) throws Exception {
        int typeId = wrapper.get(Type.VAR_INT, 1);
        EntityType entityType = this.getTypeFromId(typeId);
        this.addTrackedEntity(wrapper, wrapper.get(Type.VAR_INT, 0), entityType);
        int oldTypeId = this.getOldEntityId(entityType.getId());
        if (typeId != oldTypeId) {
            wrapper.set(Type.VAR_INT, 1, oldTypeId);
        }
        return entityType;
    }

    protected void registerMetadataRewriter(ClientboundPacketType packetType, final Type<List<Metadata>> oldMetaType, final Type<List<Metadata>> newMetaType) {
        ((Protocol)this.getProtocol()).registerOutgoing((ClientboundPacketType)packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                if (oldMetaType != null) {
                    this.map(oldMetaType, newMetaType);
                } else {
                    this.map(newMetaType);
                }
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Type.VAR_INT, 0);
                    EntityType type = EntityRewriter.this.getEntityType(wrapper.user(), entityId);
                    MetaStorage storage = new MetaStorage((List)wrapper.get(newMetaType, 0));
                    EntityRewriter.this.handleMeta(wrapper.user(), entityId, storage);
                    EntityData entityData = EntityRewriter.this.getEntityData(type);
                    if (entityData != null && entityData.hasBaseMeta()) {
                        entityData.getDefaultMeta().createMeta(storage);
                    }
                    wrapper.set(newMetaType, 0, storage.getMetaDataList());
                });
            }
        });
    }

    protected void registerMetadataRewriter(ClientboundPacketType packetType, Type<List<Metadata>> metaType) {
        this.registerMetadataRewriter(packetType, null, metaType);
    }
}

