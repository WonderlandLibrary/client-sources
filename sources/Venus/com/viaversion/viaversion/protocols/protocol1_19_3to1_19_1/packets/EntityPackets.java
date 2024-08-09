/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.packets;

import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_3Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.BitSetType;
import com.viaversion.viaversion.api.type.types.version.Types1_19;
import com.viaversion.viaversion.api.type.types.version.Types1_19_3;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ClientboundPackets1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.Protocol1_19_3To1_19_1;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import java.util.BitSet;
import java.util.UUID;

public final class EntityPackets
extends EntityRewriter<ClientboundPackets1_19_1, Protocol1_19_3To1_19_1> {
    private static final BitSetType PROFILE_ACTIONS_ENUM_TYPE = new BitSetType(6);

    public EntityPackets(Protocol1_19_3To1_19_1 protocol1_19_3To1_19_1) {
        super(protocol1_19_3To1_19_1);
    }

    @Override
    public void registerPackets() {
        this.registerTrackerWithData1_19(ClientboundPackets1_19_1.SPAWN_ENTITY, Entity1_19_3Types.FALLING_BLOCK);
        this.registerMetadataRewriter(ClientboundPackets1_19_1.ENTITY_METADATA, Types1_19.METADATA_LIST, Types1_19_3.METADATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_19_1.REMOVE_ENTITIES);
        ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound(ClientboundPackets1_19_1.JOIN_GAME, new PacketHandlers(this){
            final EntityPackets this$0;
            {
                this.this$0 = entityPackets;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.map(Type.NBT);
                this.map(Type.STRING);
                this.map(Type.STRING);
                this.handler(this.this$0.dimensionDataHandler());
                this.handler(this.this$0.biomeSizeTracker());
                this.handler(this.this$0.worldDataTrackerHandlerByKey());
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_19_3.UPDATE_ENABLED_FEATURES);
                packetWrapper2.write(Type.VAR_INT, 1);
                packetWrapper2.write(Type.STRING, "minecraft:vanilla");
                packetWrapper2.scheduleSend(Protocol1_19_3To1_19_1.class);
            }
        });
        ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound(ClientboundPackets1_19_1.RESPAWN, new PacketHandlers(this){
            final EntityPackets this$0;
            {
                this.this$0 = entityPackets;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.handler(this.this$0.worldDataTrackerHandlerByKey());
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                boolean bl = packetWrapper.read(Type.BOOLEAN);
                byte by = 2;
                if (bl) {
                    by = (byte)(by | 1);
                }
                packetWrapper.write(Type.BYTE, by);
            }
        });
        ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound(ClientboundPackets1_19_1.PLAYER_INFO, ClientboundPackets1_19_3.PLAYER_INFO_UPDATE, EntityPackets::lambda$registerPackets$0);
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler(EntityPackets::lambda$registerRewrites$1);
        this.registerMetaTypeHandler(Types1_19_3.META_TYPES.itemType, Types1_19_3.META_TYPES.blockStateType, null, Types1_19_3.META_TYPES.particleType);
        this.filter().index(6).handler(EntityPackets::lambda$registerRewrites$2);
        this.filter().filterFamily(Entity1_19_3Types.MINECART_ABSTRACT).index(11).handler(this::lambda$registerRewrites$3);
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_19_3Types.getTypeFromId(n);
    }

    private void lambda$registerRewrites$3(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = (Integer)metadata.getValue();
        metadata.setValue(((Protocol1_19_3To1_19_1)this.protocol).getMappingData().getNewBlockStateId(n));
    }

    private static void lambda$registerRewrites$2(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = (Integer)metadata.value();
        if (n >= 10) {
            metadata.setValue(n + 1);
        }
    }

    private static void lambda$registerRewrites$1(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = metadata.metaType().typeId();
        metadata.setMetaType(Types1_19_3.META_TYPES.byId(n >= 2 ? n + 1 : n));
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.read(Type.VAR_INT);
        if (n == 4) {
            int n2 = packetWrapper.read(Type.VAR_INT);
            UUID[] uUIDArray = new UUID[n2];
            for (int i = 0; i < n2; ++i) {
                uUIDArray[i] = packetWrapper.read(Type.UUID);
            }
            packetWrapper.write(Type.UUID_ARRAY, uUIDArray);
            packetWrapper.setPacketType(ClientboundPackets1_19_3.PLAYER_INFO_REMOVE);
            return;
        }
        BitSet bitSet = new BitSet(6);
        if (n == 0) {
            bitSet.set(0, 6);
        } else {
            bitSet.set(n == 1 ? n + 1 : n + 2);
        }
        packetWrapper.write(PROFILE_ACTIONS_ENUM_TYPE, bitSet);
        int n3 = packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n3; ++i) {
            packetWrapper.passthrough(Type.UUID);
            if (n == 0) {
                int n4;
                packetWrapper.passthrough(Type.STRING);
                int n5 = packetWrapper.passthrough(Type.VAR_INT);
                for (n4 = 0; n4 < n5; ++n4) {
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.OPTIONAL_STRING);
                }
                n4 = packetWrapper.read(Type.VAR_INT);
                int n6 = packetWrapper.read(Type.VAR_INT);
                JsonElement jsonElement = packetWrapper.read(Type.OPTIONAL_COMPONENT);
                packetWrapper.read(Type.OPTIONAL_PROFILE_KEY);
                packetWrapper.write(Type.BOOLEAN, false);
                packetWrapper.write(Type.VAR_INT, n4);
                packetWrapper.write(Type.BOOLEAN, true);
                packetWrapper.write(Type.VAR_INT, n6);
                packetWrapper.write(Type.OPTIONAL_COMPONENT, jsonElement);
                continue;
            }
            if (n == 1 || n == 2) {
                packetWrapper.passthrough(Type.VAR_INT);
                continue;
            }
            if (n != 3) continue;
            packetWrapper.passthrough(Type.OPTIONAL_COMPONENT);
        }
    }
}

