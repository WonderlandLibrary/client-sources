/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;

import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data.EntityTypeMapping;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data.ImmediateRespawn;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import java.util.ArrayList;

public class EntityPackets1_15
extends EntityRewriter<ClientboundPackets1_15, Protocol1_14_4To1_15> {
    public EntityPackets1_15(Protocol1_14_4To1_15 protocol1_14_4To1_15) {
        super(protocol1_14_4To1_15);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.UPDATE_HEALTH, EntityPackets1_15::lambda$registerPackets$0);
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.GAME_EVENT, new PacketHandlers(this){
            final EntityPackets1_15 this$0;
            {
                this.this$0 = entityPackets1_15;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.FLOAT);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.get(Type.UNSIGNED_BYTE, 0) == 11) {
                    packetWrapper.user().get(ImmediateRespawn.class).setImmediateRespawn(packetWrapper.get(Type.FLOAT, 0).floatValue() == 1.0f);
                }
            }
        });
        this.registerTrackerWithData(ClientboundPackets1_15.SPAWN_ENTITY, Entity1_15Types.FALLING_BLOCK);
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.SPAWN_MOB, new PacketHandlers(this){
            final EntityPackets1_15 this$0;
            {
                this.this$0 = entityPackets1_15;
            }

            @Override
            public void register() {
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
                this.handler(2::lambda$register$0);
                this.handler(this::lambda$register$1);
            }

            private void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 1);
                EntityType entityType = Entity1_15Types.getTypeFromId(n);
                this.this$0.tracker(packetWrapper.user()).addEntity(packetWrapper.get(Type.VAR_INT, 0), entityType);
                packetWrapper.set(Type.VAR_INT, 1, EntityTypeMapping.getOldEntityId(n));
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Types1_14.METADATA_LIST, new ArrayList());
            }
        });
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.RESPAWN, new PacketHandlers(this){
            final EntityPackets1_15 this$0;
            {
                this.this$0 = entityPackets1_15;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map((Type)Type.LONG, Type.NOTHING);
            }
        });
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.JOIN_GAME, new PacketHandlers(this){
            final EntityPackets1_15 this$0;
            {
                this.this$0 = entityPackets1_15;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.map((Type)Type.LONG, Type.NOTHING);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.handler(EntityPackets1_15.access$000(this.this$0, Entity1_15Types.PLAYER, Type.INT));
                this.handler(4::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                boolean bl = packetWrapper.read(Type.BOOLEAN) == false;
                packetWrapper.user().get(ImmediateRespawn.class).setImmediateRespawn(bl);
            }
        });
        this.registerTracker(ClientboundPackets1_15.SPAWN_EXPERIENCE_ORB, Entity1_15Types.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY, Entity1_15Types.LIGHTNING_BOLT);
        this.registerTracker(ClientboundPackets1_15.SPAWN_PAINTING, Entity1_15Types.PAINTING);
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.SPAWN_PLAYER, new PacketHandlers(this){
            final EntityPackets1_15 this$0;
            {
                this.this$0 = entityPackets1_15;
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
                this.handler(5::lambda$register$0);
                this.handler(EntityPackets1_15.access$100(this.this$0, Entity1_15Types.PLAYER, Type.VAR_INT));
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Types1_14.METADATA_LIST, new ArrayList());
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_15.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_15.ENTITY_METADATA, Types1_14.METADATA_LIST);
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.ENTITY_PROPERTIES, new PacketHandlers(this){
            final EntityPackets1_15 this$0;
            {
                this.this$0 = entityPackets1_15;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n;
                int n2 = packetWrapper.get(Type.VAR_INT, 0);
                EntityType entityType = this.this$0.tracker(packetWrapper.user()).entityType(n2);
                if (entityType != Entity1_15Types.BEE) {
                    return;
                }
                int n3 = n = packetWrapper.get(Type.INT, 0).intValue();
                for (int i = 0; i < n; ++i) {
                    int n4;
                    int n5;
                    String string = packetWrapper.read(Type.STRING);
                    if (string.equals("generic.flyingSpeed")) {
                        --n3;
                        packetWrapper.read(Type.DOUBLE);
                        n5 = packetWrapper.read(Type.VAR_INT);
                        for (n4 = 0; n4 < n5; ++n4) {
                            packetWrapper.read(Type.UUID);
                            packetWrapper.read(Type.DOUBLE);
                            packetWrapper.read(Type.BYTE);
                        }
                        continue;
                    }
                    packetWrapper.write(Type.STRING, string);
                    packetWrapper.passthrough(Type.DOUBLE);
                    n5 = packetWrapper.passthrough(Type.VAR_INT);
                    for (n4 = 0; n4 < n5; ++n4) {
                        packetWrapper.passthrough(Type.UUID);
                        packetWrapper.passthrough(Type.DOUBLE);
                        packetWrapper.passthrough(Type.BYTE);
                    }
                }
                if (n3 != n) {
                    packetWrapper.set(Type.INT, 0, n3);
                }
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.registerMetaTypeHandler(Types1_14.META_TYPES.itemType, Types1_14.META_TYPES.blockStateType, null, Types1_14.META_TYPES.particleType, Types1_14.META_TYPES.componentType, Types1_14.META_TYPES.optionalComponentType);
        this.filter().filterFamily(Entity1_15Types.LIVINGENTITY).removeIndex(12);
        this.filter().type(Entity1_15Types.BEE).cancel(15);
        this.filter().type(Entity1_15Types.BEE).cancel(16);
        this.mapEntityTypeWithData(Entity1_15Types.BEE, Entity1_15Types.PUFFERFISH).jsonName().spawnMetadata(EntityPackets1_15::lambda$registerRewrites$1);
        this.filter().type(Entity1_15Types.ENDERMAN).cancel(16);
        this.filter().type(Entity1_15Types.TRIDENT).cancel(10);
        this.filter().type(Entity1_15Types.WOLF).addIndex(17);
        this.filter().type(Entity1_15Types.WOLF).index(8).handler(EntityPackets1_15::lambda$registerRewrites$2);
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_15Types.getTypeFromId(n);
    }

    @Override
    public int newEntityId(int n) {
        return EntityTypeMapping.getOldEntityId(n);
    }

    private static void lambda$registerRewrites$2(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        metaHandlerEvent.createExtraMeta(new Metadata(17, Types1_14.META_TYPES.floatType, metaHandlerEvent.meta().value()));
    }

    private static void lambda$registerRewrites$1(WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(new Metadata(14, Types1_14.META_TYPES.booleanType, false));
        wrappedMetadata.add(new Metadata(15, Types1_14.META_TYPES.varIntType, 2));
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        float f = packetWrapper.passthrough(Type.FLOAT).floatValue();
        if (f > 0.0f) {
            return;
        }
        if (!packetWrapper.user().get(ImmediateRespawn.class).isImmediateRespawn()) {
            return;
        }
        PacketWrapper packetWrapper2 = packetWrapper.create(ServerboundPackets1_14.CLIENT_STATUS);
        packetWrapper2.write(Type.VAR_INT, 0);
        packetWrapper2.sendToServer(Protocol1_14_4To1_15.class);
    }

    static PacketHandler access$000(EntityPackets1_15 entityPackets1_15, EntityType entityType, Type type) {
        return entityPackets1_15.getTrackerHandler(entityType, type);
    }

    static PacketHandler access$100(EntityPackets1_15 entityPackets1_15, EntityType entityType, Type type) {
        return entityPackets1_15.getTrackerHandler(entityType, type);
    }
}

