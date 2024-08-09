/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.EntityRewriterBase;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;

public abstract class EntityRewriter<C extends ClientboundPacketType, T extends BackwardsProtocol<C, ?, ?, ?>>
extends EntityRewriterBase<C, T> {
    protected EntityRewriter(T t) {
        this(t, Types1_14.META_TYPES.optionalComponentType, Types1_14.META_TYPES.booleanType);
    }

    protected EntityRewriter(T t, MetaType metaType, MetaType metaType2) {
        super(t, metaType, 2, metaType2, 3);
    }

    @Override
    public void registerTrackerWithData(C c, EntityType entityType) {
        ((BackwardsProtocol)this.protocol).registerClientbound(c, new PacketHandlers(this, entityType){
            final EntityType val$fallingBlockType;
            final EntityRewriter this$0;
            {
                this.this$0 = entityRewriter;
                this.val$fallingBlockType = entityType;
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
                this.map(Type.INT);
                this.handler(this.this$0.getSpawnTrackerWithDataHandler(this.val$fallingBlockType));
            }
        });
    }

    @Override
    public void registerTrackerWithData1_19(C c, EntityType entityType) {
        ((BackwardsProtocol)this.protocol).registerClientbound(c, new PacketHandlers(this, entityType){
            final EntityType val$fallingBlockType;
            final EntityRewriter this$0;
            {
                this.this$0 = entityRewriter;
                this.val$fallingBlockType = entityType;
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
                this.map(Type.VAR_INT);
                this.handler(this.this$0.getSpawnTrackerWithDataHandler1_19(this.val$fallingBlockType));
            }
        });
    }

    public PacketHandler getSpawnTrackerWithDataHandler(EntityType entityType) {
        return arg_0 -> this.lambda$getSpawnTrackerWithDataHandler$0(entityType, arg_0);
    }

    public PacketHandler getSpawnTrackerWithDataHandler1_19(EntityType entityType) {
        return arg_0 -> this.lambda$getSpawnTrackerWithDataHandler1_19$1(entityType, arg_0);
    }

    public void registerSpawnTracker(C c) {
        ((BackwardsProtocol)this.protocol).registerClientbound(c, new PacketHandlers(this){
            final EntityRewriter this$0;
            {
                this.this$0 = entityRewriter;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                this.this$0.trackAndMapEntity(packetWrapper);
            }
        });
    }

    public PacketHandler worldTrackerHandlerByKey() {
        return this::lambda$worldTrackerHandlerByKey$2;
    }

    protected EntityType trackAndMapEntity(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.get(Type.VAR_INT, 1);
        EntityType entityType = this.typeFromId(n);
        this.tracker(packetWrapper.user()).addEntity(packetWrapper.get(Type.VAR_INT, 0), entityType);
        int n2 = this.newEntityId(entityType.getId());
        if (n != n2) {
            packetWrapper.set(Type.VAR_INT, 1, n2);
        }
        return entityType;
    }

    private void lambda$worldTrackerHandlerByKey$2(PacketWrapper packetWrapper) throws Exception {
        Object e = this.tracker(packetWrapper.user());
        String string = packetWrapper.get(Type.STRING, 1);
        if (e.currentWorld() != null && !e.currentWorld().equals(string)) {
            e.clearEntities();
            e.trackClientEntity();
        }
        e.setCurrentWorld(string);
    }

    private void lambda$getSpawnTrackerWithDataHandler1_19$1(EntityType entityType, PacketWrapper packetWrapper) throws Exception {
        EntityType entityType2 = this.trackAndMapEntity(packetWrapper);
        if (entityType2 == entityType) {
            int n = packetWrapper.get(Type.VAR_INT, 2);
            packetWrapper.set(Type.VAR_INT, 2, ((BackwardsProtocol)this.protocol).getMappingData().getNewBlockStateId(n));
        }
    }

    private void lambda$getSpawnTrackerWithDataHandler$0(EntityType entityType, PacketWrapper packetWrapper) throws Exception {
        EntityType entityType2 = this.trackAndMapEntity(packetWrapper);
        if (entityType2 == entityType) {
            int n = packetWrapper.get(Type.INT, 0);
            packetWrapper.set(Type.INT, 0, ((BackwardsProtocol)this.protocol).getMappingData().getNewBlockStateId(n));
        }
    }
}

