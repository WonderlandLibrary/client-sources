/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.rewriters;

import com.google.common.base.Preconditions;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.Int2IntMapMappings;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.data.entity.TrackedEntity;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class EntityRewriterBase<C extends ClientboundPacketType, T extends BackwardsProtocol<C, ?, ?, ?>>
extends EntityRewriter<C, T> {
    private final Int2ObjectMap<EntityData> entityDataMappings = new Int2ObjectOpenHashMap<EntityData>();
    private final MetaType displayNameMetaType;
    private final MetaType displayVisibilityMetaType;
    private final int displayNameIndex;
    private final int displayVisibilityIndex;

    EntityRewriterBase(T t, MetaType metaType, int n, MetaType metaType2, int n2) {
        super(t, false);
        this.displayNameMetaType = metaType;
        this.displayNameIndex = n;
        this.displayVisibilityMetaType = metaType2;
        this.displayVisibilityIndex = n2;
    }

    @Override
    public void handleMetadata(int n, List<Metadata> list, UserConnection userConnection) {
        TrackedEntity trackedEntity = this.tracker(userConnection).entity(n);
        boolean bl = trackedEntity == null || !trackedEntity.hasSentMetadata();
        super.handleMetadata(n, list, userConnection);
        if (trackedEntity == null) {
            return;
        }
        EntityData entityData = this.entityDataForType(trackedEntity.entityType());
        if (entityData != null && entityData.mobName() != null) {
            Metadata metadata = this.getMeta(this.displayNameIndex, list);
            if (bl) {
                if (metadata == null) {
                    list.add(new Metadata(this.displayNameIndex, this.displayNameMetaType, entityData.mobName()));
                    this.addDisplayVisibilityMeta(list);
                } else if (metadata.getValue() == null || metadata.getValue().toString().isEmpty()) {
                    metadata.setValue(entityData.mobName());
                    this.addDisplayVisibilityMeta(list);
                }
            } else if (metadata != null && (metadata.getValue() == null || metadata.getValue().toString().isEmpty())) {
                metadata.setValue(entityData.mobName());
                this.addDisplayVisibilityMeta(list);
            }
        }
        if (entityData != null && entityData.hasBaseMeta() && bl) {
            entityData.defaultMeta().createMeta(new WrappedMetadata(list));
        }
    }

    private void addDisplayVisibilityMeta(List<Metadata> list) {
        if (ViaBackwards.getConfig().alwaysShowOriginalMobName()) {
            this.removeMeta(this.displayVisibilityIndex, list);
            list.add(new Metadata(this.displayVisibilityIndex, this.displayVisibilityMetaType, true));
        }
    }

    protected @Nullable Metadata getMeta(int n, List<Metadata> list) {
        for (Metadata metadata : list) {
            if (metadata.id() != n) continue;
            return metadata;
        }
        return null;
    }

    protected void removeMeta(int n, List<Metadata> list) {
        list.removeIf(arg_0 -> EntityRewriterBase.lambda$removeMeta$0(n, arg_0));
    }

    protected boolean hasData(EntityType entityType) {
        return this.entityDataMappings.containsKey(entityType.getId());
    }

    protected @Nullable EntityData entityDataForType(EntityType entityType) {
        return (EntityData)this.entityDataMappings.get(entityType.getId());
    }

    protected @Nullable StoredEntityData storedEntityData(MetaHandlerEvent metaHandlerEvent) {
        return this.tracker(metaHandlerEvent.user()).entityData(metaHandlerEvent.entityId());
    }

    protected EntityData mapEntityTypeWithData(EntityType entityType, EntityType entityType2) {
        Preconditions.checkArgument(entityType.getClass() == entityType2.getClass(), "Both entity types need to be of the same class");
        int n = this.newEntityId(entityType2.getId());
        EntityData entityData = new EntityData((BackwardsProtocol)this.protocol, entityType, n);
        this.mapEntityType(entityType.getId(), n);
        this.entityDataMappings.put(entityType.getId(), entityData);
        return entityData;
    }

    @Override
    public <E extends Enum<E>> void mapTypes(EntityType[] entityTypeArray, Class<E> clazz) {
        if (this.typeMappings == null) {
            this.typeMappings = Int2IntMapMappings.of();
        }
        for (EntityType entityType : entityTypeArray) {
            try {
                E e = Enum.valueOf(clazz, entityType.name());
                this.typeMappings.setNewId(entityType.getId(), ((EntityType)e).getId());
            } catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
    }

    public void registerMetaTypeHandler(@Nullable MetaType metaType, @Nullable MetaType metaType2, @Nullable MetaType metaType3, @Nullable MetaType metaType4, @Nullable MetaType metaType5, @Nullable MetaType metaType6) {
        this.filter().handler((arg_0, arg_1) -> this.lambda$registerMetaTypeHandler$1(metaType, metaType2, metaType3, metaType4, metaType6, metaType5, arg_0, arg_1));
    }

    protected PacketHandler getTrackerHandler(Type<? extends Number> type, int n) {
        return arg_0 -> this.lambda$getTrackerHandler$2(type, n, arg_0);
    }

    protected PacketHandler getTrackerHandler() {
        return this.getTrackerHandler(Type.VAR_INT, 1);
    }

    protected PacketHandler getTrackerHandler(EntityType entityType, Type<? extends Number> type) {
        return arg_0 -> this.lambda$getTrackerHandler$3(type, entityType, arg_0);
    }

    protected PacketHandler getDimensionHandler(int n) {
        return arg_0 -> EntityRewriterBase.lambda$getDimensionHandler$4(n, arg_0);
    }

    private static void lambda$getDimensionHandler$4(int n, PacketWrapper packetWrapper) throws Exception {
        ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
        int n2 = packetWrapper.get(Type.INT, n);
        clientWorld.setEnvironment(n2);
    }

    private void lambda$getTrackerHandler$3(Type type, EntityType entityType, PacketWrapper packetWrapper) throws Exception {
        this.tracker(packetWrapper.user()).addEntity(((Number)packetWrapper.get(type, 0)).intValue(), entityType);
    }

    private void lambda$getTrackerHandler$2(Type type, int n, PacketWrapper packetWrapper) throws Exception {
        Number number = (Number)packetWrapper.get(type, n);
        this.tracker(packetWrapper.user()).addEntity(packetWrapper.get(Type.VAR_INT, 0), this.typeFromId(number.intValue()));
    }

    private void lambda$registerMetaTypeHandler$1(MetaType metaType, MetaType metaType2, MetaType metaType3, MetaType metaType4, MetaType metaType5, MetaType metaType6, MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        JsonElement jsonElement;
        MetaType metaType7 = metadata.metaType();
        if (metaType7 == metaType) {
            ((BackwardsProtocol)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.value());
        } else if (metaType7 == metaType2) {
            int n = (Integer)metadata.value();
            metadata.setValue(((BackwardsProtocol)this.protocol).getMappingData().getNewBlockStateId(n));
        } else if (metaType7 == metaType3) {
            int n = (Integer)metadata.value();
            if (n != 0) {
                metadata.setValue(((BackwardsProtocol)this.protocol).getMappingData().getNewBlockStateId(n));
            }
        } else if (metaType7 == metaType4) {
            this.rewriteParticle((Particle)metadata.value());
        } else if ((metaType7 == metaType5 || metaType7 == metaType6) && (jsonElement = (JsonElement)metadata.value()) != null) {
            ((BackwardsProtocol)this.protocol).getTranslatableRewriter().processText(jsonElement);
        }
    }

    private static boolean lambda$removeMeta$0(int n, Metadata metadata) {
        return metadata.id() == n;
    }
}

