/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.entities.storage.EntityObjectData;
import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viabackwards.api.rewriters.EntityRewriterBase;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.ObjectType;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class LegacyEntityRewriter<C extends ClientboundPacketType, T extends BackwardsProtocol<C, ?, ?, ?>>
extends EntityRewriterBase<C, T> {
    private final Map<ObjectType, EntityData> objectTypes = new HashMap<ObjectType, EntityData>();

    protected LegacyEntityRewriter(T t) {
        this(t, MetaType1_9.String, MetaType1_9.Boolean);
    }

    protected LegacyEntityRewriter(T t, MetaType metaType, MetaType metaType2) {
        super(t, metaType, 2, metaType2, 3);
    }

    protected EntityObjectData mapObjectType(ObjectType objectType, ObjectType objectType2, int n) {
        EntityObjectData entityObjectData = new EntityObjectData((BackwardsProtocol)this.protocol, objectType.getType().name(), objectType.getId(), objectType2.getId(), n);
        this.objectTypes.put(objectType, entityObjectData);
        return entityObjectData;
    }

    protected @Nullable EntityData getObjectData(ObjectType objectType) {
        return this.objectTypes.get(objectType);
    }

    protected void registerRespawn(C c) {
        ((BackwardsProtocol)this.protocol).registerClientbound(c, new PacketHandlers(this){
            final LegacyEntityRewriter this$0;
            {
                this.this$0 = legacyEntityRewriter;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                clientWorld.setEnvironment(packetWrapper.get(Type.INT, 0));
            }
        });
    }

    protected void registerJoinGame(C c, EntityType entityType) {
        ((BackwardsProtocol)this.protocol).registerClientbound(c, new PacketHandlers(this, entityType){
            final EntityType val$playerType;
            final LegacyEntityRewriter this$0;
            {
                this.this$0 = legacyEntityRewriter;
                this.val$playerType = entityType;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(arg_0 -> this.lambda$register$0(this.val$playerType, arg_0));
            }

            private void lambda$register$0(EntityType entityType, PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                clientWorld.setEnvironment(packetWrapper.get(Type.INT, 1));
                this.this$0.addTrackedEntity(packetWrapper, packetWrapper.get(Type.INT, 0), entityType);
            }
        });
    }

    @Override
    public void registerMetadataRewriter(C c, Type<List<Metadata>> type, Type<List<Metadata>> type2) {
        ((BackwardsProtocol)this.protocol).registerClientbound(c, new PacketHandlers(this, type, type2){
            final Type val$oldMetaType;
            final Type val$newMetaType;
            final LegacyEntityRewriter this$0;
            {
                this.this$0 = legacyEntityRewriter;
                this.val$oldMetaType = type;
                this.val$newMetaType = type2;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                if (this.val$oldMetaType != null) {
                    this.map(this.val$oldMetaType, this.val$newMetaType);
                } else {
                    this.map(this.val$newMetaType);
                }
                this.handler(arg_0 -> this.lambda$register$0(this.val$newMetaType, arg_0));
            }

            private void lambda$register$0(Type type, PacketWrapper packetWrapper) throws Exception {
                List list = (List)packetWrapper.get(type, 0);
                this.this$0.handleMetadata(packetWrapper.get(Type.VAR_INT, 0), list, packetWrapper.user());
            }
        });
    }

    @Override
    public void registerMetadataRewriter(C c, Type<List<Metadata>> type) {
        this.registerMetadataRewriter(c, null, type);
    }

    protected PacketHandler getMobSpawnRewriter(Type<List<Metadata>> type) {
        return arg_0 -> this.lambda$getMobSpawnRewriter$0(type, arg_0);
    }

    protected PacketHandler getObjectTrackerHandler() {
        return this::lambda$getObjectTrackerHandler$1;
    }

    protected PacketHandler getTrackerAndMetaHandler(Type<List<Metadata>> type, EntityType entityType) {
        return arg_0 -> this.lambda$getTrackerAndMetaHandler$2(entityType, type, arg_0);
    }

    protected PacketHandler getObjectRewriter(Function<Byte, ObjectType> function) {
        return arg_0 -> this.lambda$getObjectRewriter$3(function, arg_0);
    }

    protected EntityType getObjectTypeFromId(int n) {
        return this.typeFromId(n);
    }

    @Deprecated
    protected void addTrackedEntity(PacketWrapper packetWrapper, int n, EntityType entityType) throws Exception {
        this.tracker(packetWrapper.user()).addEntity(n, entityType);
    }

    private void lambda$getObjectRewriter$3(Function function, PacketWrapper packetWrapper) throws Exception {
        ObjectType objectType = (ObjectType)function.apply(packetWrapper.get(Type.BYTE, 0));
        if (objectType == null) {
            ViaBackwards.getPlatform().getLogger().warning("Could not find Entity Type" + packetWrapper.get(Type.BYTE, 0));
            return;
        }
        EntityData entityData = this.getObjectData(objectType);
        if (entityData != null) {
            packetWrapper.set(Type.BYTE, 0, (byte)entityData.replacementId());
            if (entityData.objectData() != -1) {
                packetWrapper.set(Type.INT, 0, entityData.objectData());
            }
        }
    }

    private void lambda$getTrackerAndMetaHandler$2(EntityType entityType, Type type, PacketWrapper packetWrapper) throws Exception {
        this.addTrackedEntity(packetWrapper, packetWrapper.get(Type.VAR_INT, 0), entityType);
        List list = (List)packetWrapper.get(type, 0);
        this.handleMetadata(packetWrapper.get(Type.VAR_INT, 0), list, packetWrapper.user());
    }

    private void lambda$getObjectTrackerHandler$1(PacketWrapper packetWrapper) throws Exception {
        this.addTrackedEntity(packetWrapper, packetWrapper.get(Type.VAR_INT, 0), this.getObjectTypeFromId(packetWrapper.get(Type.BYTE, 0).byteValue()));
    }

    private void lambda$getMobSpawnRewriter$0(Type type, PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.get(Type.VAR_INT, 0);
        EntityType entityType = this.tracker(packetWrapper.user()).entityType(n);
        List list = (List)packetWrapper.get(type, 0);
        this.handleMetadata(n, list, packetWrapper.user());
        EntityData entityData = this.entityDataForType(entityType);
        if (entityData != null) {
            packetWrapper.set(Type.VAR_INT, 1, entityData.replacementId());
            if (entityData.hasBaseMeta()) {
                entityData.defaultMeta().createMeta(new WrappedMetadata(list));
            }
        }
    }
}

