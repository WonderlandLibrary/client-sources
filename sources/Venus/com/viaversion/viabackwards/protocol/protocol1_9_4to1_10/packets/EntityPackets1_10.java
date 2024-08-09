/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets;

import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10;
import com.viaversion.viabackwards.utils.Block;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_11Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_12Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.ObjectType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class EntityPackets1_10
extends LegacyEntityRewriter<ClientboundPackets1_9_3, Protocol1_9_4To1_10> {
    public EntityPackets1_10(Protocol1_9_4To1_10 protocol1_9_4To1_10) {
        super(protocol1_9_4To1_10);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_9_4To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketHandlers(this){
            final EntityPackets1_10 this$0;
            {
                this.this$0 = entityPackets1_10;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.handler(EntityPackets1_10.access$000(this.this$0));
                this.handler(EntityPackets1_10.access$100(this.this$0, 1::lambda$register$0));
                this.handler(this::lambda$register$1);
            }

            private void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                Optional<Entity1_12Types.ObjectType> optional = Entity1_12Types.ObjectType.findById(packetWrapper.get(Type.BYTE, 0).byteValue());
                if (optional.isPresent() && optional.get() == Entity1_12Types.ObjectType.FALLING_BLOCK) {
                    int n = packetWrapper.get(Type.INT, 0);
                    int n2 = n & 0xFFF;
                    int n3 = n >> 12 & 0xF;
                    Block block = ((Protocol1_9_4To1_10)EntityPackets1_10.access$200(this.this$0)).getItemRewriter().handleBlock(n2, n3);
                    if (block == null) {
                        return;
                    }
                    packetWrapper.set(Type.INT, 0, block.getId() | block.getData() << 12);
                }
            }

            private static ObjectType lambda$register$0(Byte by) {
                return Entity1_11Types.ObjectType.findById(by.byteValue()).orElse(null);
            }
        });
        this.registerTracker(ClientboundPackets1_9_3.SPAWN_EXPERIENCE_ORB, Entity1_10Types.EntityType.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_9_3.SPAWN_GLOBAL_ENTITY, Entity1_10Types.EntityType.WEATHER);
        ((Protocol1_9_4To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketHandlers(this){
            final EntityPackets1_10 this$0;
            {
                this.this$0 = entityPackets1_10;
            }

            @Override
            public void register() {
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
                this.handler(EntityPackets1_10.access$300(this.this$0, Type.UNSIGNED_BYTE, 0));
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityType entityType = this.this$0.tracker(packetWrapper.user()).entityType(n);
                List<Metadata> list = packetWrapper.get(Types1_9.METADATA_LIST, 0);
                this.this$0.handleMetadata(packetWrapper.get(Type.VAR_INT, 0), list, packetWrapper.user());
                EntityData entityData = EntityPackets1_10.access$400(this.this$0, entityType);
                if (entityData != null) {
                    WrappedMetadata wrappedMetadata = new WrappedMetadata(list);
                    packetWrapper.set(Type.UNSIGNED_BYTE, 0, (short)entityData.replacementId());
                    if (entityData.hasBaseMeta()) {
                        entityData.defaultMeta().createMeta(wrappedMetadata);
                    }
                }
            }
        });
        this.registerTracker(ClientboundPackets1_9_3.SPAWN_PAINTING, Entity1_10Types.EntityType.PAINTING);
        this.registerJoinGame(ClientboundPackets1_9_3.JOIN_GAME, Entity1_10Types.EntityType.PLAYER);
        this.registerRespawn(ClientboundPackets1_9_3.RESPAWN);
        ((Protocol1_9_4To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_PLAYER, new PacketHandlers(this){
            final EntityPackets1_10 this$0;
            {
                this.this$0 = entityPackets1_10;
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
                this.map(Types1_9.METADATA_LIST);
                this.handler(EntityPackets1_10.access$500(this.this$0, Types1_9.METADATA_LIST, Entity1_11Types.EntityType.PLAYER));
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
    }

    @Override
    protected void registerRewrites() {
        this.mapEntityTypeWithData(Entity1_10Types.EntityType.POLAR_BEAR, Entity1_10Types.EntityType.SHEEP).plainName();
        this.filter().type(Entity1_10Types.EntityType.POLAR_BEAR).index(13).handler(EntityPackets1_10::lambda$registerRewrites$0);
        this.filter().type(Entity1_10Types.EntityType.ZOMBIE).index(13).handler(EntityPackets1_10::lambda$registerRewrites$1);
        this.filter().type(Entity1_10Types.EntityType.SKELETON).index(12).handler(EntityPackets1_10::lambda$registerRewrites$2);
        this.filter().removeIndex(5);
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_10Types.getTypeFromId(n, false);
    }

    @Override
    protected EntityType getObjectTypeFromId(int n) {
        return Entity1_10Types.getTypeFromId(n, true);
    }

    private static void lambda$registerRewrites$2(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        if ((Integer)metadata.getValue() == 2) {
            metadata.setValue(0);
        }
    }

    private static void lambda$registerRewrites$1(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        if ((Integer)metadata.getValue() == 6) {
            metadata.setValue(0);
        }
    }

    private static void lambda$registerRewrites$0(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        boolean bl = (Boolean)metadata.getValue();
        metadata.setTypeAndValue(MetaType1_9.Byte, bl ? (byte)14 : 0);
    }

    static PacketHandler access$000(EntityPackets1_10 entityPackets1_10) {
        return entityPackets1_10.getObjectTrackerHandler();
    }

    static PacketHandler access$100(EntityPackets1_10 entityPackets1_10, Function function) {
        return entityPackets1_10.getObjectRewriter(function);
    }

    static Protocol access$200(EntityPackets1_10 entityPackets1_10) {
        return entityPackets1_10.protocol;
    }

    static PacketHandler access$300(EntityPackets1_10 entityPackets1_10, Type type, int n) {
        return entityPackets1_10.getTrackerHandler(type, n);
    }

    static EntityData access$400(EntityPackets1_10 entityPackets1_10, EntityType entityType) {
        return entityPackets1_10.entityDataForType(entityType);
    }

    static PacketHandler access$500(EntityPackets1_10 entityPackets1_10, Type type, EntityType entityType) {
        return entityPackets1_10.getTrackerAndMetaHandler(type, entityType);
    }
}

