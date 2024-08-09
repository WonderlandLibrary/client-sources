/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.packets;

import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.Protocol1_14To1_14_1;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import java.util.List;

public class EntityPackets1_14_1
extends LegacyEntityRewriter<ClientboundPackets1_14, Protocol1_14To1_14_1> {
    public EntityPackets1_14_1(Protocol1_14To1_14_1 protocol1_14To1_14_1) {
        super(protocol1_14To1_14_1);
    }

    @Override
    protected void registerPackets() {
        this.registerTracker(ClientboundPackets1_14.SPAWN_EXPERIENCE_ORB, Entity1_14Types.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_14.SPAWN_GLOBAL_ENTITY, Entity1_14Types.LIGHTNING_BOLT);
        this.registerTracker(ClientboundPackets1_14.SPAWN_PAINTING, Entity1_14Types.PAINTING);
        this.registerTracker(ClientboundPackets1_14.SPAWN_PLAYER, Entity1_14Types.PLAYER);
        this.registerTracker(ClientboundPackets1_14.JOIN_GAME, Entity1_14Types.PLAYER, Type.INT);
        this.registerRemoveEntities(ClientboundPackets1_14.DESTROY_ENTITIES);
        ((Protocol1_14To1_14_1)this.protocol).registerClientbound(ClientboundPackets1_14.SPAWN_ENTITY, new PacketHandlers(this){
            final EntityPackets1_14_1 this$0;
            {
                this.this$0 = entityPackets1_14_1;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(EntityPackets1_14_1.access$000(this.this$0));
            }
        });
        ((Protocol1_14To1_14_1)this.protocol).registerClientbound(ClientboundPackets1_14.SPAWN_MOB, new PacketHandlers(this){
            final EntityPackets1_14_1 this$0;
            {
                this.this$0 = entityPackets1_14_1;
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
                this.map(Types1_14.METADATA_LIST);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                int n2 = packetWrapper.get(Type.VAR_INT, 1);
                this.this$0.tracker(packetWrapper.user()).addEntity(n, Entity1_14Types.getTypeFromId(n2));
                List<Metadata> list = packetWrapper.get(Types1_14.METADATA_LIST, 0);
                this.this$0.handleMetadata(n, list, packetWrapper.user());
            }
        });
        this.registerMetadataRewriter(ClientboundPackets1_14.ENTITY_METADATA, Types1_14.METADATA_LIST);
    }

    @Override
    protected void registerRewrites() {
        this.filter().type(Entity1_14Types.VILLAGER).cancel(15);
        this.filter().type(Entity1_14Types.VILLAGER).index(16).toIndex(15);
        this.filter().type(Entity1_14Types.WANDERING_TRADER).cancel(15);
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_14Types.getTypeFromId(n);
    }

    static PacketHandler access$000(EntityPackets1_14_1 entityPackets1_14_1) {
        return entityPackets1_14_1.getTrackerHandler();
    }
}

