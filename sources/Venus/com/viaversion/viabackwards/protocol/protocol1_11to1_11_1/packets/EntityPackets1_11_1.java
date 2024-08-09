/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.packets;

import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.Protocol1_11To1_11_1;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_11Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.ObjectType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import java.util.function.Function;

public class EntityPackets1_11_1
extends LegacyEntityRewriter<ClientboundPackets1_9_3, Protocol1_11To1_11_1> {
    public EntityPackets1_11_1(Protocol1_11To1_11_1 protocol1_11To1_11_1) {
        super(protocol1_11To1_11_1);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_11To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketHandlers(this){
            final EntityPackets1_11_1 this$0;
            {
                this.this$0 = entityPackets1_11_1;
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
                this.handler(EntityPackets1_11_1.access$000(this.this$0));
                this.handler(EntityPackets1_11_1.access$100(this.this$0, 1::lambda$register$0));
            }

            private static ObjectType lambda$register$0(Byte by) {
                return Entity1_11Types.ObjectType.findById(by.byteValue()).orElse(null);
            }
        });
        this.registerTracker(ClientboundPackets1_9_3.SPAWN_EXPERIENCE_ORB, Entity1_11Types.EntityType.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_9_3.SPAWN_GLOBAL_ENTITY, Entity1_11Types.EntityType.WEATHER);
        ((Protocol1_11To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketHandlers(this){
            final EntityPackets1_11_1 this$0;
            {
                this.this$0 = entityPackets1_11_1;
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
                this.map(Types1_9.METADATA_LIST);
                this.handler(EntityPackets1_11_1.access$200(this.this$0));
                this.handler(EntityPackets1_11_1.access$300(this.this$0, Types1_9.METADATA_LIST));
            }
        });
        this.registerTracker(ClientboundPackets1_9_3.SPAWN_PAINTING, Entity1_11Types.EntityType.PAINTING);
        this.registerJoinGame(ClientboundPackets1_9_3.JOIN_GAME, Entity1_11Types.EntityType.PLAYER);
        this.registerRespawn(ClientboundPackets1_9_3.RESPAWN);
        ((Protocol1_11To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_PLAYER, new PacketHandlers(this){
            final EntityPackets1_11_1 this$0;
            {
                this.this$0 = entityPackets1_11_1;
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
                this.handler(EntityPackets1_11_1.access$400(this.this$0, Types1_9.METADATA_LIST, Entity1_11Types.EntityType.PLAYER));
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
    }

    @Override
    protected void registerRewrites() {
        this.filter().type(Entity1_11Types.EntityType.FIREWORK).cancel(7);
        this.filter().type(Entity1_11Types.EntityType.PIG).cancel(14);
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_11Types.getTypeFromId(n, false);
    }

    @Override
    protected EntityType getObjectTypeFromId(int n) {
        return Entity1_11Types.getTypeFromId(n, true);
    }

    static PacketHandler access$000(EntityPackets1_11_1 entityPackets1_11_1) {
        return entityPackets1_11_1.getObjectTrackerHandler();
    }

    static PacketHandler access$100(EntityPackets1_11_1 entityPackets1_11_1, Function function) {
        return entityPackets1_11_1.getObjectRewriter(function);
    }

    static PacketHandler access$200(EntityPackets1_11_1 entityPackets1_11_1) {
        return entityPackets1_11_1.getTrackerHandler();
    }

    static PacketHandler access$300(EntityPackets1_11_1 entityPackets1_11_1, Type type) {
        return entityPackets1_11_1.getMobSpawnRewriter(type);
    }

    static PacketHandler access$400(EntityPackets1_11_1 entityPackets1_11_1, Type type, EntityType entityType) {
        return entityPackets1_11_1.getTrackerAndMetaHandler(type, entityType);
    }
}

