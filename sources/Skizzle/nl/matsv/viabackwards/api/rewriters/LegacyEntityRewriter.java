/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package nl.matsv.viabackwards.api.rewriters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import nl.matsv.viabackwards.ViaBackwards;
import nl.matsv.viabackwards.api.BackwardsProtocol;
import nl.matsv.viabackwards.api.entities.storage.EntityData;
import nl.matsv.viabackwards.api.entities.storage.EntityObjectData;
import nl.matsv.viabackwards.api.entities.storage.MetaStorage;
import nl.matsv.viabackwards.api.rewriters.EntityRewriterBase;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.entities.EntityType;
import us.myles.ViaVersion.api.entities.ObjectType;
import us.myles.ViaVersion.api.minecraft.metadata.MetaType;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_9;
import us.myles.ViaVersion.api.protocol.ClientboundPacketType;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;

public abstract class LegacyEntityRewriter<T extends BackwardsProtocol>
extends EntityRewriterBase<T> {
    private final Map<ObjectType, EntityData> objectTypes = new HashMap<ObjectType, EntityData>();

    protected LegacyEntityRewriter(T protocol) {
        this(protocol, MetaType1_9.String, MetaType1_9.Boolean);
    }

    protected LegacyEntityRewriter(T protocol, MetaType displayType, MetaType displayVisibilityType) {
        super(protocol, displayType, 2, displayVisibilityType, 3);
    }

    protected EntityObjectData mapObjectType(ObjectType oldObjectType, ObjectType replacement, int data) {
        EntityObjectData entData = new EntityObjectData(oldObjectType.getId(), true, replacement.getId(), data);
        this.objectTypes.put(oldObjectType, entData);
        return entData;
    }

    @Nullable
    protected EntityData getObjectData(ObjectType type) {
        return this.objectTypes.get(type);
    }

    protected void registerRespawn(ClientboundPacketType packetType) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(wrapper -> {
                    ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
                    clientWorld.setEnvironment(wrapper.get(Type.INT, 0));
                });
            }
        });
    }

    protected void registerJoinGame(ClientboundPacketType packetType, final EntityType playerType) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(wrapper -> {
                    ClientWorld clientChunks = wrapper.user().get(ClientWorld.class);
                    clientChunks.setEnvironment(wrapper.get(Type.INT, 1));
                    LegacyEntityRewriter.this.getEntityTracker(wrapper.user()).trackEntityType(wrapper.get(Type.INT, 0), playerType);
                });
            }
        });
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
                    List metadata = (List)wrapper.get(newMetaType, 0);
                    wrapper.set(newMetaType, 0, LegacyEntityRewriter.this.handleMeta(wrapper.user(), wrapper.get(Type.VAR_INT, 0), new MetaStorage(metadata)).getMetaDataList());
                });
            }
        });
    }

    protected void registerMetadataRewriter(ClientboundPacketType packetType, Type<List<Metadata>> metaType) {
        this.registerMetadataRewriter(packetType, null, metaType);
    }

    protected PacketHandler getMobSpawnRewriter(Type<List<Metadata>> metaType) {
        return wrapper -> {
            int entityId = wrapper.get(Type.VAR_INT, 0);
            EntityType type = this.getEntityType(wrapper.user(), entityId);
            MetaStorage storage = new MetaStorage((List)wrapper.get(metaType, 0));
            this.handleMeta(wrapper.user(), entityId, storage);
            EntityData entityData = this.getEntityData(type);
            if (entityData != null) {
                wrapper.set(Type.VAR_INT, 1, entityData.getReplacementId());
                if (entityData.hasBaseMeta()) {
                    entityData.getDefaultMeta().createMeta(storage);
                }
            }
            wrapper.set(metaType, 0, storage.getMetaDataList());
        };
    }

    protected PacketHandler getObjectTrackerHandler() {
        return wrapper -> this.addTrackedEntity(wrapper, wrapper.get(Type.VAR_INT, 0), this.getObjectTypeFromId(wrapper.get(Type.BYTE, 0).byteValue()));
    }

    protected PacketHandler getTrackerAndMetaHandler(Type<List<Metadata>> metaType, EntityType entityType) {
        return wrapper -> {
            this.addTrackedEntity(wrapper, wrapper.get(Type.VAR_INT, 0), entityType);
            List<Metadata> metaDataList = this.handleMeta(wrapper.user(), wrapper.get(Type.VAR_INT, 0), new MetaStorage((List)wrapper.get(metaType, 0))).getMetaDataList();
            wrapper.set(metaType, 0, metaDataList);
        };
    }

    protected PacketHandler getObjectRewriter(Function<Byte, ObjectType> objectGetter) {
        return wrapper -> {
            ObjectType type = (ObjectType)objectGetter.apply(wrapper.get(Type.BYTE, 0));
            if (type == null) {
                ViaBackwards.getPlatform().getLogger().warning("Could not find Entity Type" + wrapper.get(Type.BYTE, 0));
                return;
            }
            EntityData data = this.getObjectData(type);
            if (data != null) {
                wrapper.set(Type.BYTE, 0, (byte)data.getReplacementId());
                if (data.getObjectData() != -1) {
                    wrapper.set(Type.INT, 0, data.getObjectData());
                }
            }
        };
    }

    protected EntityType getObjectTypeFromId(int typeId) {
        return this.getTypeFromId(typeId);
    }
}

