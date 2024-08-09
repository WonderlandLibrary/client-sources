/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.PotionSplashHandler;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.ChestedHorseStorage;
import com.viaversion.viabackwards.utils.Block;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
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

public class EntityPackets1_11
extends LegacyEntityRewriter<ClientboundPackets1_9_3, Protocol1_10To1_11> {
    public EntityPackets1_11(Protocol1_10To1_11 protocol1_10To1_11) {
        super(protocol1_10To1_11);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.EFFECT, new PacketHandlers(this){
            final EntityPackets1_11 this$0;
            {
                this.this$0 = entityPackets1_11;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.INT, 0);
                if (n == 2002 || n == 2007) {
                    int n2;
                    if (n == 2007) {
                        packetWrapper.set(Type.INT, 0, 2002);
                    }
                    if ((n2 = PotionSplashHandler.getOldData(packetWrapper.get(Type.INT, 1))) != -1) {
                        packetWrapper.set(Type.INT, 1, n2);
                    }
                }
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketHandlers(this){
            final EntityPackets1_11 this$0;
            {
                this.this$0 = entityPackets1_11;
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
                this.handler(EntityPackets1_11.access$000(this.this$0));
                this.handler(EntityPackets1_11.access$100(this.this$0, 2::lambda$register$0));
                this.handler(this::lambda$register$1);
            }

            private void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                Optional<Entity1_12Types.ObjectType> optional = Entity1_12Types.ObjectType.findById(packetWrapper.get(Type.BYTE, 0).byteValue());
                if (optional.isPresent() && optional.get() == Entity1_12Types.ObjectType.FALLING_BLOCK) {
                    int n = packetWrapper.get(Type.INT, 0);
                    int n2 = n & 0xFFF;
                    int n3 = n >> 12 & 0xF;
                    Block block = ((Protocol1_10To1_11)EntityPackets1_11.access$200(this.this$0)).getItemRewriter().handleBlock(n2, n3);
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
        this.registerTracker(ClientboundPackets1_9_3.SPAWN_EXPERIENCE_ORB, Entity1_11Types.EntityType.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_9_3.SPAWN_GLOBAL_ENTITY, Entity1_11Types.EntityType.WEATHER);
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketHandlers(this){
            final EntityPackets1_11 this$0;
            {
                this.this$0 = entityPackets1_11;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map((Type)Type.VAR_INT, Type.UNSIGNED_BYTE);
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
                this.handler(EntityPackets1_11.access$300(this.this$0, Type.UNSIGNED_BYTE, 0));
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityType entityType = this.this$0.tracker(packetWrapper.user()).entityType(n);
                List<Metadata> list = packetWrapper.get(Types1_9.METADATA_LIST, 0);
                this.this$0.handleMetadata(packetWrapper.get(Type.VAR_INT, 0), list, packetWrapper.user());
                EntityData entityData = EntityPackets1_11.access$400(this.this$0, entityType);
                if (entityData != null) {
                    packetWrapper.set(Type.UNSIGNED_BYTE, 0, (short)entityData.replacementId());
                    if (entityData.hasBaseMeta()) {
                        entityData.defaultMeta().createMeta(new WrappedMetadata(list));
                    }
                }
                if (list.isEmpty()) {
                    list.add(new Metadata(0, MetaType1_9.Byte, (byte)0));
                }
            }
        });
        this.registerTracker(ClientboundPackets1_9_3.SPAWN_PAINTING, Entity1_11Types.EntityType.PAINTING);
        this.registerJoinGame(ClientboundPackets1_9_3.JOIN_GAME, Entity1_11Types.EntityType.PLAYER);
        this.registerRespawn(ClientboundPackets1_9_3.RESPAWN);
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_PLAYER, new PacketHandlers(this){
            final EntityPackets1_11 this$0;
            {
                this.this$0 = entityPackets1_11;
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
                this.handler(EntityPackets1_11.access$500(this.this$0, Types1_9.METADATA_LIST, Entity1_11Types.EntityType.PLAYER));
                this.handler(4::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                List<Metadata> list = packetWrapper.get(Types1_9.METADATA_LIST, 0);
                if (list.isEmpty()) {
                    list.add(new Metadata(0, MetaType1_9.Byte, (byte)0));
                }
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.ENTITY_STATUS, new PacketHandlers(this){
            final EntityPackets1_11 this$0;
            {
                this.this$0 = entityPackets1_11;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.BYTE);
                this.handler(5::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                byte by = packetWrapper.get(Type.BYTE, 0);
                if (by == 35) {
                    packetWrapper.clearPacket();
                    packetWrapper.setPacketType(ClientboundPackets1_9_3.GAME_EVENT);
                    packetWrapper.write(Type.UNSIGNED_BYTE, (short)10);
                    packetWrapper.write(Type.FLOAT, Float.valueOf(0.0f));
                }
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.ELDER_GUARDIAN, Entity1_11Types.EntityType.GUARDIAN);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.WITHER_SKELETON, Entity1_11Types.EntityType.SKELETON).spawnMetadata(this::lambda$registerRewrites$0);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.STRAY, Entity1_11Types.EntityType.SKELETON).plainName().spawnMetadata(this::lambda$registerRewrites$1);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.HUSK, Entity1_11Types.EntityType.ZOMBIE).plainName().spawnMetadata(this::lambda$registerRewrites$2);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.ZOMBIE_VILLAGER, Entity1_11Types.EntityType.ZOMBIE).spawnMetadata(this::lambda$registerRewrites$3);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.HORSE, Entity1_11Types.EntityType.HORSE).spawnMetadata(this::lambda$registerRewrites$4);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.DONKEY, Entity1_11Types.EntityType.HORSE).spawnMetadata(this::lambda$registerRewrites$5);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.MULE, Entity1_11Types.EntityType.HORSE).spawnMetadata(this::lambda$registerRewrites$6);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.SKELETON_HORSE, Entity1_11Types.EntityType.HORSE).spawnMetadata(this::lambda$registerRewrites$7);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.ZOMBIE_HORSE, Entity1_11Types.EntityType.HORSE).spawnMetadata(this::lambda$registerRewrites$8);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.EVOCATION_FANGS, Entity1_11Types.EntityType.SHULKER);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.EVOCATION_ILLAGER, Entity1_11Types.EntityType.VILLAGER).plainName();
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.VEX, Entity1_11Types.EntityType.BAT).plainName();
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.VINDICATION_ILLAGER, Entity1_11Types.EntityType.VILLAGER).plainName().spawnMetadata(EntityPackets1_11::lambda$registerRewrites$9);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.LIAMA, Entity1_11Types.EntityType.HORSE).plainName().spawnMetadata(this::lambda$registerRewrites$10);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.LIAMA_SPIT, Entity1_11Types.EntityType.SNOWBALL);
        this.mapObjectType(Entity1_11Types.ObjectType.LIAMA_SPIT, Entity1_11Types.ObjectType.SNOWBALL, -1);
        this.mapObjectType(Entity1_11Types.ObjectType.EVOCATION_FANGS, Entity1_11Types.ObjectType.FALLING_BLOCK, 4294);
        this.filter().filterFamily(Entity1_11Types.EntityType.GUARDIAN).index(12).handler(EntityPackets1_11::lambda$registerRewrites$11);
        this.filter().filterFamily(Entity1_11Types.EntityType.ABSTRACT_SKELETON).index(12).toIndex(13);
        this.filter().filterFamily(Entity1_11Types.EntityType.ZOMBIE).handler(EntityPackets1_11::lambda$registerRewrites$12);
        this.filter().type(Entity1_11Types.EntityType.EVOCATION_ILLAGER).index(12).handler(EntityPackets1_11::lambda$registerRewrites$13);
        this.filter().type(Entity1_11Types.EntityType.VEX).index(12).handler(EntityPackets1_11::lambda$registerRewrites$14);
        this.filter().type(Entity1_11Types.EntityType.VINDICATION_ILLAGER).index(12).handler(EntityPackets1_11::lambda$registerRewrites$15);
        this.filter().filterFamily(Entity1_11Types.EntityType.ABSTRACT_HORSE).index(13).handler(this::lambda$registerRewrites$16);
        this.filter().filterFamily(Entity1_11Types.EntityType.CHESTED_HORSE).handler(this::lambda$registerRewrites$17);
        this.filter().type(Entity1_11Types.EntityType.HORSE).index(16).toIndex(17);
        this.filter().filterFamily(Entity1_11Types.EntityType.CHESTED_HORSE).index(15).handler(this::lambda$registerRewrites$18);
        this.filter().type(Entity1_11Types.EntityType.LIAMA).handler(this::lambda$registerRewrites$19);
        this.filter().filterFamily(Entity1_11Types.EntityType.ABSTRACT_HORSE).index(14).toIndex(16);
        this.filter().type(Entity1_11Types.EntityType.VILLAGER).index(13).handler(EntityPackets1_11::lambda$registerRewrites$20);
        this.filter().type(Entity1_11Types.EntityType.SHULKER).cancel(15);
    }

    private Metadata getSkeletonTypeMeta(int n) {
        return new Metadata(12, MetaType1_9.VarInt, n);
    }

    private Metadata getZombieTypeMeta(int n) {
        return new Metadata(13, MetaType1_9.VarInt, n);
    }

    private void handleZombieType(WrappedMetadata wrappedMetadata, int n) {
        Metadata metadata = wrappedMetadata.get(13);
        if (metadata == null) {
            wrappedMetadata.add(this.getZombieTypeMeta(n));
        }
    }

    private Metadata getHorseMetaType(int n) {
        return new Metadata(14, MetaType1_9.VarInt, n);
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_11Types.getTypeFromId(n, false);
    }

    @Override
    protected EntityType getObjectTypeFromId(int n) {
        return Entity1_11Types.getTypeFromId(n, true);
    }

    private static void lambda$registerRewrites$20(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        if ((Integer)metadata.getValue() == 5) {
            metadata.setValue(0);
        }
    }

    private void lambda$registerRewrites$19(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        StoredEntityData storedEntityData = this.storedEntityData(metaHandlerEvent);
        ChestedHorseStorage chestedHorseStorage = storedEntityData.get(ChestedHorseStorage.class);
        int n = metaHandlerEvent.index();
        switch (n) {
            case 16: {
                chestedHorseStorage.setLiamaStrength((Integer)metadata.getValue());
                metaHandlerEvent.cancel();
                break;
            }
            case 17: {
                chestedHorseStorage.setLiamaCarpetColor((Integer)metadata.getValue());
                metaHandlerEvent.cancel();
                break;
            }
            case 18: {
                chestedHorseStorage.setLiamaVariant((Integer)metadata.getValue());
                metaHandlerEvent.cancel();
            }
        }
    }

    private void lambda$registerRewrites$18(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        StoredEntityData storedEntityData = this.storedEntityData(metaHandlerEvent);
        ChestedHorseStorage chestedHorseStorage = storedEntityData.get(ChestedHorseStorage.class);
        boolean bl = (Boolean)metadata.getValue();
        chestedHorseStorage.setChested(bl);
        metaHandlerEvent.cancel();
    }

    private void lambda$registerRewrites$17(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        StoredEntityData storedEntityData = this.storedEntityData(metaHandlerEvent);
        if (!storedEntityData.has(ChestedHorseStorage.class)) {
            storedEntityData.put(new ChestedHorseStorage());
        }
    }

    private void lambda$registerRewrites$16(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        StoredEntityData storedEntityData = this.storedEntityData(metaHandlerEvent);
        byte by = (Byte)metadata.getValue();
        if (storedEntityData.has(ChestedHorseStorage.class) && storedEntityData.get(ChestedHorseStorage.class).isChested()) {
            by = (byte)(by | 8);
            metadata.setValue(by);
        }
    }

    private static void lambda$registerRewrites$15(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        metaHandlerEvent.setIndex(13);
        metadata.setTypeAndValue(MetaType1_9.VarInt, ((Number)metadata.getValue()).intValue() == 1 ? 2 : 4);
    }

    private static void lambda$registerRewrites$14(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        metadata.setValue((byte)0);
    }

    private static void lambda$registerRewrites$13(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        metaHandlerEvent.setIndex(13);
        metadata.setTypeAndValue(MetaType1_9.VarInt, ((Byte)metadata.getValue()).intValue());
    }

    private static void lambda$registerRewrites$12(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        switch (metadata.id()) {
            case 13: {
                metaHandlerEvent.cancel();
                return;
            }
            case 14: {
                metaHandlerEvent.setIndex(15);
                break;
            }
            case 15: {
                metaHandlerEvent.setIndex(14);
                break;
            }
            case 16: {
                metaHandlerEvent.setIndex(13);
                metadata.setValue(1 + (Integer)metadata.getValue());
            }
        }
    }

    private static void lambda$registerRewrites$11(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n;
        boolean bl = (Boolean)metadata.getValue();
        int n2 = n = bl ? 2 : 0;
        if (metaHandlerEvent.entityType() == Entity1_11Types.EntityType.ELDER_GUARDIAN) {
            n |= 4;
        }
        metadata.setTypeAndValue(MetaType1_9.Byte, (byte)n);
    }

    private void lambda$registerRewrites$10(WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(this.getHorseMetaType(1));
    }

    private static void lambda$registerRewrites$9(WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(new Metadata(13, MetaType1_9.VarInt, 4));
    }

    private void lambda$registerRewrites$8(WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(this.getHorseMetaType(3));
    }

    private void lambda$registerRewrites$7(WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(this.getHorseMetaType(4));
    }

    private void lambda$registerRewrites$6(WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(this.getHorseMetaType(2));
    }

    private void lambda$registerRewrites$5(WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(this.getHorseMetaType(1));
    }

    private void lambda$registerRewrites$4(WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(this.getHorseMetaType(0));
    }

    private void lambda$registerRewrites$3(WrappedMetadata wrappedMetadata) {
        this.handleZombieType(wrappedMetadata, 1);
    }

    private void lambda$registerRewrites$2(WrappedMetadata wrappedMetadata) {
        this.handleZombieType(wrappedMetadata, 6);
    }

    private void lambda$registerRewrites$1(WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(this.getSkeletonTypeMeta(2));
    }

    private void lambda$registerRewrites$0(WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(this.getSkeletonTypeMeta(1));
    }

    static PacketHandler access$000(EntityPackets1_11 entityPackets1_11) {
        return entityPackets1_11.getObjectTrackerHandler();
    }

    static PacketHandler access$100(EntityPackets1_11 entityPackets1_11, Function function) {
        return entityPackets1_11.getObjectRewriter(function);
    }

    static Protocol access$200(EntityPackets1_11 entityPackets1_11) {
        return entityPackets1_11.protocol;
    }

    static PacketHandler access$300(EntityPackets1_11 entityPackets1_11, Type type, int n) {
        return entityPackets1_11.getTrackerHandler(type, n);
    }

    static EntityData access$400(EntityPackets1_11 entityPackets1_11, EntityType entityType) {
        return entityPackets1_11.entityDataForType(entityType);
    }

    static PacketHandler access$500(EntityPackets1_11 entityPackets1_11, Type type, EntityType entityType) {
        return entityPackets1_11.getTrackerAndMetaHandler(type, entityType);
    }
}

