/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import java.util.List;

public class EntityPackets1_13_1
extends LegacyEntityRewriter<ClientboundPackets1_13, Protocol1_13To1_13_1> {
    public EntityPackets1_13_1(Protocol1_13To1_13_1 protocol1_13To1_13_1) {
        super(protocol1_13To1_13_1);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_13To1_13_1)this.protocol).registerClientbound(ClientboundPackets1_13.SPAWN_ENTITY, new PacketHandlers(this){
            final EntityPackets1_13_1 this$0;
            {
                this.this$0 = entityPackets1_13_1;
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
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                byte by = packetWrapper.get(Type.BYTE, 0);
                Entity1_13Types.EntityType entityType = Entity1_13Types.getTypeFromId(by, true);
                if (entityType == null) {
                    ViaBackwards.getPlatform().getLogger().warning("Could not find 1.13 entity type " + by);
                    return;
                }
                if (entityType.is((EntityType)Entity1_13Types.EntityType.FALLING_BLOCK)) {
                    int n2 = packetWrapper.get(Type.INT, 0);
                    packetWrapper.set(Type.INT, 0, ((Protocol1_13To1_13_1)EntityPackets1_13_1.access$000(this.this$0)).getMappingData().getNewBlockStateId(n2));
                }
                this.this$0.tracker(packetWrapper.user()).addEntity(n, entityType);
            }
        });
        this.registerTracker(ClientboundPackets1_13.SPAWN_EXPERIENCE_ORB, Entity1_13Types.EntityType.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_13.SPAWN_GLOBAL_ENTITY, Entity1_13Types.EntityType.LIGHTNING_BOLT);
        ((Protocol1_13To1_13_1)this.protocol).registerClientbound(ClientboundPackets1_13.SPAWN_MOB, new PacketHandlers(this){
            final EntityPackets1_13_1 this$0;
            {
                this.this$0 = entityPackets1_13_1;
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
                this.map(Types1_13.METADATA_LIST);
                this.handler(EntityPackets1_13_1.access$100(this.this$0));
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                List<Metadata> list = packetWrapper.get(Types1_13.METADATA_LIST, 0);
                this.this$0.handleMetadata(packetWrapper.get(Type.VAR_INT, 0), list, packetWrapper.user());
            }
        });
        ((Protocol1_13To1_13_1)this.protocol).registerClientbound(ClientboundPackets1_13.SPAWN_PLAYER, new PacketHandlers(this){
            final EntityPackets1_13_1 this$0;
            {
                this.this$0 = entityPackets1_13_1;
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
                this.map(Types1_13.METADATA_LIST);
                this.handler(EntityPackets1_13_1.access$200(this.this$0, Types1_13.METADATA_LIST, Entity1_13Types.EntityType.PLAYER));
            }
        });
        this.registerTracker(ClientboundPackets1_13.SPAWN_PAINTING, Entity1_13Types.EntityType.PAINTING);
        this.registerJoinGame(ClientboundPackets1_13.JOIN_GAME, Entity1_13Types.EntityType.PLAYER);
        this.registerRespawn(ClientboundPackets1_13.RESPAWN);
        this.registerRemoveEntities(ClientboundPackets1_13.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_13.ENTITY_METADATA, Types1_13.METADATA_LIST);
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler(this::lambda$registerRewrites$0);
        this.filter().filterFamily(Entity1_13Types.EntityType.ABSTRACT_ARROW).cancel(7);
        this.filter().type(Entity1_13Types.EntityType.SPECTRAL_ARROW).index(8).toIndex(7);
        this.filter().type(Entity1_13Types.EntityType.TRIDENT).index(8).toIndex(7);
        this.filter().filterFamily(Entity1_13Types.EntityType.MINECART_ABSTRACT).index(9).handler(this::lambda$registerRewrites$1);
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_13Types.getTypeFromId(n, false);
    }

    @Override
    protected EntityType getObjectTypeFromId(int n) {
        return Entity1_13Types.getTypeFromId(n, true);
    }

    private void lambda$registerRewrites$1(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = (Integer)metadata.getValue();
        metadata.setValue(((Protocol1_13To1_13_1)this.protocol).getMappingData().getNewBlockStateId(n));
    }

    private void lambda$registerRewrites$0(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        if (metadata.metaType() == Types1_13.META_TYPES.itemType) {
            ((Protocol1_13To1_13_1)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
        } else if (metadata.metaType() == Types1_13.META_TYPES.blockStateType) {
            int n = (Integer)metadata.getValue();
            metadata.setValue(((Protocol1_13To1_13_1)this.protocol).getMappingData().getNewBlockStateId(n));
        } else if (metadata.metaType() == Types1_13.META_TYPES.particleType) {
            this.rewriteParticle((Particle)metadata.getValue());
        } else if (metadata.metaType() == Types1_13.META_TYPES.optionalComponentType || metadata.metaType() == Types1_13.META_TYPES.componentType) {
            JsonElement jsonElement = (JsonElement)metadata.value();
            ((Protocol1_13To1_13_1)this.protocol).translatableRewriter().processText(jsonElement);
        }
    }

    static Protocol access$000(EntityPackets1_13_1 entityPackets1_13_1) {
        return entityPackets1_13_1.protocol;
    }

    static PacketHandler access$100(EntityPackets1_13_1 entityPackets1_13_1) {
        return entityPackets1_13_1.getTrackerHandler();
    }

    static PacketHandler access$200(EntityPackets1_13_1 entityPackets1_13_1, Type type, EntityType entityType) {
        return entityPackets1_13_1.getTrackerAndMetaHandler(type, entityType);
    }
}

