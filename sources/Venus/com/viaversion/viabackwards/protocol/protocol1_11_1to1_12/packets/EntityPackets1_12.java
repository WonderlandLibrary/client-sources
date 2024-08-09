/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.ParrotStorage;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.ShoulderTracker;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.ChatPackets1_12;
import com.viaversion.viabackwards.utils.Block;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_12Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.ObjectType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_12;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_12;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import java.util.Optional;
import java.util.function.Function;

public class EntityPackets1_12
extends LegacyEntityRewriter<ClientboundPackets1_12, Protocol1_11_1To1_12> {
    public EntityPackets1_12(Protocol1_11_1To1_12 protocol1_11_1To1_12) {
        super(protocol1_11_1To1_12);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.SPAWN_ENTITY, new PacketHandlers(this){
            final EntityPackets1_12 this$0;
            {
                this.this$0 = entityPackets1_12;
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
                this.handler(EntityPackets1_12.access$000(this.this$0));
                this.handler(EntityPackets1_12.access$100(this.this$0, 1::lambda$register$0));
                this.handler(this::lambda$register$1);
            }

            private void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                Optional<Entity1_12Types.ObjectType> optional = Entity1_12Types.ObjectType.findById(packetWrapper.get(Type.BYTE, 0).byteValue());
                if (optional.isPresent() && optional.get() == Entity1_12Types.ObjectType.FALLING_BLOCK) {
                    int n = packetWrapper.get(Type.INT, 0);
                    int n2 = n & 0xFFF;
                    int n3 = n >> 12 & 0xF;
                    Block block = ((Protocol1_11_1To1_12)EntityPackets1_12.access$200(this.this$0)).getItemRewriter().handleBlock(n2, n3);
                    if (block == null) {
                        return;
                    }
                    packetWrapper.set(Type.INT, 0, block.getId() | block.getData() << 12);
                }
            }

            private static ObjectType lambda$register$0(Byte by) {
                return Entity1_12Types.ObjectType.findById(by.byteValue()).orElse(null);
            }
        });
        this.registerTracker(ClientboundPackets1_12.SPAWN_EXPERIENCE_ORB, Entity1_12Types.EntityType.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_12.SPAWN_GLOBAL_ENTITY, Entity1_12Types.EntityType.WEATHER);
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.SPAWN_MOB, new PacketHandlers(this){
            final EntityPackets1_12 this$0;
            {
                this.this$0 = entityPackets1_12;
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
                this.map(Types1_12.METADATA_LIST);
                this.handler(EntityPackets1_12.access$300(this.this$0));
                this.handler(EntityPackets1_12.access$400(this.this$0, Types1_12.METADATA_LIST));
            }
        });
        this.registerTracker(ClientboundPackets1_12.SPAWN_PAINTING, Entity1_12Types.EntityType.PAINTING);
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.SPAWN_PLAYER, new PacketHandlers(this){
            final EntityPackets1_12 this$0;
            {
                this.this$0 = entityPackets1_12;
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
                this.map(Types1_12.METADATA_LIST);
                this.handler(EntityPackets1_12.access$500(this.this$0, Types1_12.METADATA_LIST, Entity1_12Types.EntityType.PLAYER));
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.JOIN_GAME, new PacketHandlers(this){
            final EntityPackets1_12 this$0;
            {
                this.this$0 = entityPackets1_12;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(EntityPackets1_12.access$600(this.this$0, Entity1_12Types.EntityType.PLAYER, Type.INT));
                this.handler(EntityPackets1_12.access$700(this.this$0, 1));
                this.handler(4::lambda$register$0);
                this.handler(4::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                PacketWrapper packetWrapper2 = PacketWrapper.create(ClientboundPackets1_9_3.STATISTICS, packetWrapper.user());
                packetWrapper2.write(Type.VAR_INT, 1);
                packetWrapper2.write(Type.STRING, "achievement.openInventory");
                packetWrapper2.write(Type.VAR_INT, 1);
                packetWrapper2.scheduleSend(Protocol1_11_1To1_12.class);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ShoulderTracker shoulderTracker = packetWrapper.user().get(ShoulderTracker.class);
                shoulderTracker.setEntityId(packetWrapper.get(Type.INT, 0));
            }
        });
        this.registerRespawn(ClientboundPackets1_12.RESPAWN);
        this.registerRemoveEntities(ClientboundPackets1_12.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_12.ENTITY_METADATA, Types1_12.METADATA_LIST);
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.ENTITY_PROPERTIES, new PacketHandlers(this){
            final EntityPackets1_12 this$0;
            {
                this.this$0 = entityPackets1_12;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.handler(5::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n;
                int n2 = n = packetWrapper.get(Type.INT, 0).intValue();
                for (int i = 0; i < n; ++i) {
                    int n3;
                    int n4;
                    String string = packetWrapper.read(Type.STRING);
                    if (string.equals("generic.flyingSpeed")) {
                        --n2;
                        packetWrapper.read(Type.DOUBLE);
                        n4 = packetWrapper.read(Type.VAR_INT);
                        for (n3 = 0; n3 < n4; ++n3) {
                            packetWrapper.read(Type.UUID);
                            packetWrapper.read(Type.DOUBLE);
                            packetWrapper.read(Type.BYTE);
                        }
                        continue;
                    }
                    packetWrapper.write(Type.STRING, string);
                    packetWrapper.passthrough(Type.DOUBLE);
                    n4 = packetWrapper.passthrough(Type.VAR_INT);
                    for (n3 = 0; n3 < n4; ++n3) {
                        packetWrapper.passthrough(Type.UUID);
                        packetWrapper.passthrough(Type.DOUBLE);
                        packetWrapper.passthrough(Type.BYTE);
                    }
                }
                if (n2 != n) {
                    packetWrapper.set(Type.INT, 0, n2);
                }
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.mapEntityTypeWithData(Entity1_12Types.EntityType.PARROT, Entity1_12Types.EntityType.BAT).plainName().spawnMetadata(EntityPackets1_12::lambda$registerRewrites$0);
        this.mapEntityTypeWithData(Entity1_12Types.EntityType.ILLUSION_ILLAGER, Entity1_12Types.EntityType.EVOCATION_ILLAGER).plainName();
        this.filter().handler(EntityPackets1_12::lambda$registerRewrites$1);
        this.filter().filterFamily(Entity1_12Types.EntityType.EVOCATION_ILLAGER).cancel(12);
        this.filter().filterFamily(Entity1_12Types.EntityType.EVOCATION_ILLAGER).index(13).toIndex(12);
        this.filter().type(Entity1_12Types.EntityType.ILLUSION_ILLAGER).index(0).handler(EntityPackets1_12::lambda$registerRewrites$2);
        this.filter().filterFamily(Entity1_12Types.EntityType.PARROT).handler(this::lambda$registerRewrites$3);
        this.filter().type(Entity1_12Types.EntityType.PARROT).cancel(12);
        this.filter().type(Entity1_12Types.EntityType.PARROT).index(13).handler(this::lambda$registerRewrites$4);
        this.filter().type(Entity1_12Types.EntityType.PARROT).cancel(14);
        this.filter().type(Entity1_12Types.EntityType.PARROT).cancel(15);
        this.filter().type(Entity1_12Types.EntityType.PLAYER).index(15).handler(EntityPackets1_12::lambda$registerRewrites$5);
        this.filter().type(Entity1_12Types.EntityType.PLAYER).index(16).handler(EntityPackets1_12::lambda$registerRewrites$6);
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_12Types.getTypeFromId(n, false);
    }

    @Override
    protected EntityType getObjectTypeFromId(int n) {
        return Entity1_12Types.getTypeFromId(n, true);
    }

    private static void lambda$registerRewrites$6(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        CompoundTag compoundTag = (CompoundTag)metaHandlerEvent.meta().getValue();
        ShoulderTracker shoulderTracker = metaHandlerEvent.user().get(ShoulderTracker.class);
        if (compoundTag.isEmpty() && shoulderTracker.getRightShoulder() != null) {
            shoulderTracker.setRightShoulder(null);
            shoulderTracker.update();
        } else if (compoundTag.contains("id") && metaHandlerEvent.entityId() == shoulderTracker.getEntityId()) {
            String string = (String)((Tag)compoundTag.get("id")).getValue();
            if (shoulderTracker.getRightShoulder() == null || !shoulderTracker.getRightShoulder().equals(string)) {
                shoulderTracker.setRightShoulder(string);
                shoulderTracker.update();
            }
        }
        metaHandlerEvent.cancel();
    }

    private static void lambda$registerRewrites$5(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        CompoundTag compoundTag = (CompoundTag)metadata.getValue();
        ShoulderTracker shoulderTracker = metaHandlerEvent.user().get(ShoulderTracker.class);
        if (compoundTag.isEmpty() && shoulderTracker.getLeftShoulder() != null) {
            shoulderTracker.setLeftShoulder(null);
            shoulderTracker.update();
        } else if (compoundTag.contains("id") && metaHandlerEvent.entityId() == shoulderTracker.getEntityId()) {
            String string = (String)((Tag)compoundTag.get("id")).getValue();
            if (shoulderTracker.getLeftShoulder() == null || !shoulderTracker.getLeftShoulder().equals(string)) {
                shoulderTracker.setLeftShoulder(string);
                shoulderTracker.update();
            }
        }
        metaHandlerEvent.cancel();
    }

    private void lambda$registerRewrites$4(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        boolean bl;
        StoredEntityData storedEntityData = this.storedEntityData(metaHandlerEvent);
        ParrotStorage parrotStorage = storedEntityData.get(ParrotStorage.class);
        boolean bl2 = ((Byte)metadata.getValue() & 1) == 1;
        boolean bl3 = bl = ((Byte)metadata.getValue() & 4) == 4;
        if (parrotStorage.isTamed() || bl) {
            // empty if block
        }
        parrotStorage.setTamed(bl);
        if (bl2) {
            metaHandlerEvent.setIndex(12);
            metadata.setValue((byte)1);
            parrotStorage.setSitting(false);
        } else if (parrotStorage.isSitting()) {
            metaHandlerEvent.setIndex(12);
            metadata.setValue((byte)0);
            parrotStorage.setSitting(true);
        } else {
            metaHandlerEvent.cancel();
        }
    }

    private void lambda$registerRewrites$3(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        StoredEntityData storedEntityData = this.storedEntityData(metaHandlerEvent);
        if (!storedEntityData.has(ParrotStorage.class)) {
            storedEntityData.put(new ParrotStorage());
        }
    }

    private static void lambda$registerRewrites$2(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        byte by = (Byte)metadata.getValue();
        if ((by & 0x20) == 32) {
            by = (byte)(by & 0xFFFFFFDF);
        }
        metadata.setValue(by);
    }

    private static void lambda$registerRewrites$1(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        if (metadata.metaType() == MetaType1_12.Chat) {
            ChatPackets1_12.COMPONENT_REWRITER.processText((JsonElement)metadata.getValue());
        }
    }

    private static void lambda$registerRewrites$0(WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(new Metadata(12, MetaType1_12.Byte, (byte)0));
    }

    static PacketHandler access$000(EntityPackets1_12 entityPackets1_12) {
        return entityPackets1_12.getObjectTrackerHandler();
    }

    static PacketHandler access$100(EntityPackets1_12 entityPackets1_12, Function function) {
        return entityPackets1_12.getObjectRewriter(function);
    }

    static Protocol access$200(EntityPackets1_12 entityPackets1_12) {
        return entityPackets1_12.protocol;
    }

    static PacketHandler access$300(EntityPackets1_12 entityPackets1_12) {
        return entityPackets1_12.getTrackerHandler();
    }

    static PacketHandler access$400(EntityPackets1_12 entityPackets1_12, Type type) {
        return entityPackets1_12.getMobSpawnRewriter(type);
    }

    static PacketHandler access$500(EntityPackets1_12 entityPackets1_12, Type type, EntityType entityType) {
        return entityPackets1_12.getTrackerAndMetaHandler(type, entityType);
    }

    static PacketHandler access$600(EntityPackets1_12 entityPackets1_12, EntityType entityType, Type type) {
        return entityPackets1_12.getTrackerHandler(entityType, type);
    }

    static PacketHandler access$700(EntityPackets1_12 entityPackets1_12, int n) {
        return entityPackets1_12.getDimensionHandler(n);
    }
}

