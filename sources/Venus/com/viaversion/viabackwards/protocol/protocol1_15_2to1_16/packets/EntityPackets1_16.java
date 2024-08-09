/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.WorldNameTracker;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.storage.WolfDataMaskStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import com.viaversion.viaversion.util.Key;

public class EntityPackets1_16
extends EntityRewriter<ClientboundPackets1_16, Protocol1_15_2To1_16> {
    private final ValueTransformer<String, Integer> dimensionTransformer = new ValueTransformer<String, Integer>(this, Type.STRING, (Type)Type.INT){
        final EntityPackets1_16 this$0;
        {
            this.this$0 = entityPackets1_16;
            super(type, type2);
        }

        @Override
        public Integer transform(PacketWrapper packetWrapper, String string) {
            switch (string) {
                case "minecraft:the_nether": {
                    return -1;
                }
                default: {
                    return 0;
                }
                case "minecraft:the_end": 
            }
            return 1;
        }

        @Override
        public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
            return this.transform(packetWrapper, (String)object);
        }
    };

    public EntityPackets1_16(Protocol1_15_2To1_16 protocol1_15_2To1_16) {
        super(protocol1_15_2To1_16);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.SPAWN_ENTITY, new PacketHandlers(this){
            final EntityPackets1_16 this$0;
            {
                this.this$0 = entityPackets1_16;
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
                this.handler(this::lambda$register$0);
                this.handler(this.this$0.getSpawnTrackerWithDataHandler(Entity1_16Types.FALLING_BLOCK));
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                EntityType entityType = this.this$0.typeFromId(packetWrapper.get(Type.VAR_INT, 1));
                if (entityType == Entity1_16Types.LIGHTNING_BOLT) {
                    packetWrapper.cancel();
                    PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY);
                    packetWrapper2.write(Type.VAR_INT, packetWrapper.get(Type.VAR_INT, 0));
                    packetWrapper2.write(Type.BYTE, (byte)1);
                    packetWrapper2.write(Type.DOUBLE, packetWrapper.get(Type.DOUBLE, 0));
                    packetWrapper2.write(Type.DOUBLE, packetWrapper.get(Type.DOUBLE, 1));
                    packetWrapper2.write(Type.DOUBLE, packetWrapper.get(Type.DOUBLE, 2));
                    packetWrapper2.send(Protocol1_15_2To1_16.class);
                }
            }
        });
        this.registerSpawnTracker(ClientboundPackets1_16.SPAWN_MOB);
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.RESPAWN, new PacketHandlers(this){
            final EntityPackets1_16 this$0;
            {
                this.this$0 = entityPackets1_16;
            }

            @Override
            public void register() {
                this.map(EntityPackets1_16.access$000(this.this$0));
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                WorldNameTracker worldNameTracker = packetWrapper.user().get(WorldNameTracker.class);
                String string = packetWrapper.read(Type.STRING);
                packetWrapper.passthrough(Type.LONG);
                packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                packetWrapper.read(Type.BYTE);
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                int n = packetWrapper.get(Type.INT, 0);
                if (clientWorld.getEnvironment() != null && n == clientWorld.getEnvironment().id() && (packetWrapper.user().isClientSide() || Via.getPlatform().isProxy() || packetWrapper.user().getProtocolInfo().getProtocolVersion() <= ProtocolVersion.v1_12_2.getVersion() || !string.equals(worldNameTracker.getWorldName()))) {
                    PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_15.RESPAWN);
                    packetWrapper2.write(Type.INT, n == 0 ? -1 : 0);
                    packetWrapper2.write(Type.LONG, 0L);
                    packetWrapper2.write(Type.UNSIGNED_BYTE, (short)0);
                    packetWrapper2.write(Type.STRING, "default");
                    packetWrapper2.send(Protocol1_15_2To1_16.class);
                }
                clientWorld.setEnvironment(n);
                packetWrapper.write(Type.STRING, "default");
                packetWrapper.read(Type.BOOLEAN);
                if (packetWrapper.read(Type.BOOLEAN).booleanValue()) {
                    packetWrapper.set(Type.STRING, 0, "flat");
                }
                packetWrapper.read(Type.BOOLEAN);
                worldNameTracker.setWorldName(string);
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.JOIN_GAME, new PacketHandlers(this){
            final EntityPackets1_16 this$0;
            {
                this.this$0 = entityPackets1_16;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map((Type)Type.BYTE, Type.NOTHING);
                this.map(Type.STRING_ARRAY, Type.NOTHING);
                this.map(Type.NBT, Type.NOTHING);
                this.map(EntityPackets1_16.access$000(this.this$0));
                this.handler(4::lambda$register$0);
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(this::lambda$register$1);
            }

            private void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                clientWorld.setEnvironment(packetWrapper.get(Type.INT, 1));
                this.this$0.tracker(packetWrapper.user()).addEntity(packetWrapper.get(Type.INT, 0), Entity1_16Types.PLAYER);
                packetWrapper.write(Type.STRING, "default");
                packetWrapper.passthrough(Type.VAR_INT);
                packetWrapper.passthrough(Type.BOOLEAN);
                packetWrapper.passthrough(Type.BOOLEAN);
                packetWrapper.read(Type.BOOLEAN);
                if (packetWrapper.read(Type.BOOLEAN).booleanValue()) {
                    packetWrapper.set(Type.STRING, 0, "flat");
                }
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                WorldNameTracker worldNameTracker = packetWrapper.user().get(WorldNameTracker.class);
                worldNameTracker.setWorldName(packetWrapper.read(Type.STRING));
            }
        });
        this.registerTracker(ClientboundPackets1_16.SPAWN_EXPERIENCE_ORB, Entity1_16Types.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_16.SPAWN_PAINTING, Entity1_16Types.PAINTING);
        this.registerTracker(ClientboundPackets1_16.SPAWN_PLAYER, Entity1_16Types.PLAYER);
        this.registerRemoveEntities(ClientboundPackets1_16.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_16.ENTITY_METADATA, Types1_16.METADATA_LIST, Types1_14.METADATA_LIST);
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.ENTITY_PROPERTIES, this::lambda$registerPackets$0);
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.PLAYER_INFO, new PacketHandlers(this){
            final EntityPackets1_16 this$0;
            {
                this.this$0 = entityPackets1_16;
            }

            @Override
            public void register() {
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.passthrough(Type.VAR_INT);
                int n2 = packetWrapper.passthrough(Type.VAR_INT);
                for (int i = 0; i < n2; ++i) {
                    packetWrapper.passthrough(Type.UUID);
                    if (n == 0) {
                        packetWrapper.passthrough(Type.STRING);
                        int n3 = packetWrapper.passthrough(Type.VAR_INT);
                        for (int j = 0; j < n3; ++j) {
                            packetWrapper.passthrough(Type.STRING);
                            packetWrapper.passthrough(Type.STRING);
                            packetWrapper.passthrough(Type.OPTIONAL_STRING);
                        }
                        packetWrapper.passthrough(Type.VAR_INT);
                        packetWrapper.passthrough(Type.VAR_INT);
                        ((Protocol1_15_2To1_16)EntityPackets1_16.access$100(this.this$0)).getTranslatableRewriter().processText(packetWrapper.passthrough(Type.OPTIONAL_COMPONENT));
                        continue;
                    }
                    if (n == 1) {
                        packetWrapper.passthrough(Type.VAR_INT);
                        continue;
                    }
                    if (n == 2) {
                        packetWrapper.passthrough(Type.VAR_INT);
                        continue;
                    }
                    if (n != 3) continue;
                    ((Protocol1_15_2To1_16)EntityPackets1_16.access$200(this.this$0)).getTranslatableRewriter().processText(packetWrapper.passthrough(Type.OPTIONAL_COMPONENT));
                }
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler(this::lambda$registerRewrites$1);
        this.mapEntityType(Entity1_16Types.ZOMBIFIED_PIGLIN, Entity1_15Types.ZOMBIE_PIGMAN);
        this.mapTypes(Entity1_16Types.values(), Entity1_15Types.class);
        this.mapEntityTypeWithData(Entity1_16Types.HOGLIN, Entity1_16Types.COW).jsonName();
        this.mapEntityTypeWithData(Entity1_16Types.ZOGLIN, Entity1_16Types.COW).jsonName();
        this.mapEntityTypeWithData(Entity1_16Types.PIGLIN, Entity1_16Types.ZOMBIFIED_PIGLIN).jsonName();
        this.mapEntityTypeWithData(Entity1_16Types.STRIDER, Entity1_16Types.MAGMA_CUBE).jsonName();
        this.filter().type(Entity1_16Types.ZOGLIN).cancel(16);
        this.filter().type(Entity1_16Types.HOGLIN).cancel(15);
        this.filter().type(Entity1_16Types.PIGLIN).cancel(16);
        this.filter().type(Entity1_16Types.PIGLIN).cancel(17);
        this.filter().type(Entity1_16Types.PIGLIN).cancel(18);
        this.filter().type(Entity1_16Types.STRIDER).index(15).handler(EntityPackets1_16::lambda$registerRewrites$2);
        this.filter().type(Entity1_16Types.STRIDER).cancel(16);
        this.filter().type(Entity1_16Types.STRIDER).cancel(17);
        this.filter().type(Entity1_16Types.STRIDER).cancel(18);
        this.filter().type(Entity1_16Types.FISHING_BOBBER).cancel(8);
        this.filter().filterFamily(Entity1_16Types.ABSTRACT_ARROW).cancel(8);
        this.filter().filterFamily(Entity1_16Types.ABSTRACT_ARROW).handler(EntityPackets1_16::lambda$registerRewrites$3);
        this.filter().type(Entity1_16Types.WOLF).index(16).handler(this::lambda$registerRewrites$4);
        this.filter().type(Entity1_16Types.WOLF).index(20).handler(this::lambda$registerRewrites$5);
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_16Types.getTypeFromId(n);
    }

    private void lambda$registerRewrites$5(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n;
        WolfDataMaskStorage wolfDataMaskStorage;
        StoredEntityData storedEntityData = this.tracker(metaHandlerEvent.user()).entityDataIfPresent(metaHandlerEvent.entityId());
        byte by = 0;
        if (storedEntityData != null && (wolfDataMaskStorage = storedEntityData.get(WolfDataMaskStorage.class)) != null) {
            by = wolfDataMaskStorage.tameableMask();
        }
        byte by2 = (byte)((n = ((Integer)metadata.value()).intValue()) > 0 ? by | 2 : by & 0xFFFFFFFD);
        metaHandlerEvent.createExtraMeta(new Metadata(16, Types1_14.META_TYPES.byteType, by2));
        metaHandlerEvent.cancel();
    }

    private void lambda$registerRewrites$4(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        byte by = (Byte)metadata.value();
        StoredEntityData storedEntityData = this.tracker(metaHandlerEvent.user()).entityData(metaHandlerEvent.entityId());
        storedEntityData.put(new WolfDataMaskStorage(by));
    }

    private static void lambda$registerRewrites$3(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        if (metaHandlerEvent.index() >= 8) {
            metaHandlerEvent.setIndex(metaHandlerEvent.index() + 1);
        }
    }

    private static void lambda$registerRewrites$2(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        boolean bl = (Boolean)metadata.value();
        metadata.setTypeAndValue(Types1_14.META_TYPES.varIntType, bl ? 1 : 3);
    }

    private void lambda$registerRewrites$1(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        JsonElement jsonElement;
        metadata.setMetaType(Types1_14.META_TYPES.byId(metadata.metaType().typeId()));
        MetaType metaType = metadata.metaType();
        if (metaType == Types1_14.META_TYPES.itemType) {
            metadata.setValue(((Protocol1_15_2To1_16)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue()));
        } else if (metaType == Types1_14.META_TYPES.blockStateType) {
            metadata.setValue(((Protocol1_15_2To1_16)this.protocol).getMappingData().getNewBlockStateId((Integer)metadata.getValue()));
        } else if (metaType == Types1_14.META_TYPES.particleType) {
            this.rewriteParticle((Particle)metadata.getValue());
        } else if (metaType == Types1_14.META_TYPES.optionalComponentType && (jsonElement = (JsonElement)metadata.value()) != null) {
            ((Protocol1_15_2To1_16)this.protocol).getTranslatableRewriter().processText(jsonElement);
        }
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.VAR_INT);
        int n = packetWrapper.passthrough(Type.INT);
        for (int i = 0; i < n; ++i) {
            String string = packetWrapper.read(Type.STRING);
            String string2 = ((Protocol1_15_2To1_16)this.protocol).getMappingData().getAttributeMappings().get(string);
            packetWrapper.write(Type.STRING, string2 != null ? string2 : Key.stripMinecraftNamespace(string));
            packetWrapper.passthrough(Type.DOUBLE);
            int n2 = packetWrapper.passthrough(Type.VAR_INT);
            for (int j = 0; j < n2; ++j) {
                packetWrapper.passthrough(Type.UUID);
                packetWrapper.passthrough(Type.DOUBLE);
                packetWrapper.passthrough(Type.BYTE);
            }
        }
    }

    static ValueTransformer access$000(EntityPackets1_16 entityPackets1_16) {
        return entityPackets1_16.dimensionTransformer;
    }

    static Protocol access$100(EntityPackets1_16 entityPackets1_16) {
        return entityPackets1_16.protocol;
    }

    static Protocol access$200(EntityPackets1_16 entityPackets1_16) {
        return entityPackets1_16.protocol;
    }
}

