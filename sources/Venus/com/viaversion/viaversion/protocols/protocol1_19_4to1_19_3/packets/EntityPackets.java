/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.packets;

import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_4Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_19_3;
import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.Protocol1_19_4To1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.storage.PlayerVehicleTracker;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;

public final class EntityPackets
extends EntityRewriter<ClientboundPackets1_19_3, Protocol1_19_4To1_19_3> {
    public EntityPackets(Protocol1_19_4To1_19_3 protocol1_19_4To1_19_3) {
        super(protocol1_19_4To1_19_3);
    }

    @Override
    public void registerPackets() {
        ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.JOIN_GAME, new PacketHandlers(this){
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
                this.handler(this.this$0.playerTrackerHandler());
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                CompoundTag compoundTag = packetWrapper.get(Type.NBT, 0);
                CompoundTag compoundTag2 = ((Protocol1_19_4To1_19_3)EntityPackets.access$000(this.this$0)).getMappingData().damageTypesRegistry();
                compoundTag.put("minecraft:damage_type", compoundTag2);
                CompoundTag compoundTag3 = (CompoundTag)compoundTag.get("minecraft:worldgen/biome");
                ListTag listTag = (ListTag)compoundTag3.get("value");
                for (Tag tag : listTag) {
                    CompoundTag compoundTag4 = (CompoundTag)((CompoundTag)tag).get("element");
                    StringTag stringTag = (StringTag)compoundTag4.get("precipitation");
                    byte by = stringTag.getValue().equals("none") ? (byte)0 : 1;
                    compoundTag4.put("has_precipitation", new ByteTag(by));
                }
            }
        });
        ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.PLAYER_POSITION, new PacketHandlers(this){
            final EntityPackets this$0;
            {
                this.this$0 = entityPackets;
            }

            @Override
            protected void register() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                PlayerVehicleTracker playerVehicleTracker;
                if (packetWrapper.read(Type.BOOLEAN).booleanValue() && (playerVehicleTracker = packetWrapper.user().get(PlayerVehicleTracker.class)).getVehicleId() != -1) {
                    PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_19_4.BUNDLE);
                    packetWrapper2.send(Protocol1_19_4To1_19_3.class);
                    PacketWrapper packetWrapper3 = packetWrapper.create(ClientboundPackets1_19_4.SET_PASSENGERS);
                    packetWrapper3.write(Type.VAR_INT, playerVehicleTracker.getVehicleId());
                    packetWrapper3.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
                    packetWrapper3.send(Protocol1_19_4To1_19_3.class);
                    packetWrapper.send(Protocol1_19_4To1_19_3.class);
                    packetWrapper.cancel();
                    PacketWrapper packetWrapper4 = packetWrapper.create(ClientboundPackets1_19_4.BUNDLE);
                    packetWrapper4.send(Protocol1_19_4To1_19_3.class);
                    playerVehicleTracker.setVehicleId(-1);
                }
            }
        });
        ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.SET_PASSENGERS, new PacketHandlers(this){
            final EntityPackets this$0;
            {
                this.this$0 = entityPackets;
            }

            @Override
            protected void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT_ARRAY_PRIMITIVE);
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int[] nArray;
                PlayerVehicleTracker playerVehicleTracker = packetWrapper.user().get(PlayerVehicleTracker.class);
                int n = packetWrapper.user().getEntityTracker(Protocol1_19_4To1_19_3.class).clientEntityId();
                int n2 = packetWrapper.get(Type.VAR_INT, 0);
                if (playerVehicleTracker.getVehicleId() == n2) {
                    playerVehicleTracker.setVehicleId(-1);
                }
                for (int n3 : nArray = packetWrapper.get(Type.VAR_INT_ARRAY_PRIMITIVE, 0)) {
                    if (n3 != n) continue;
                    playerVehicleTracker.setVehicleId(n2);
                    break;
                }
            }
        });
        ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.ENTITY_TELEPORT, new PacketHandlers(this){
            final EntityPackets this$0;
            {
                this.this$0 = entityPackets;
            }

            @Override
            protected void register() {
                this.handler(4::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n;
                int n2 = packetWrapper.read(Type.VAR_INT);
                if (n2 != (n = packetWrapper.user().getEntityTracker(Protocol1_19_4To1_19_3.class).clientEntityId())) {
                    packetWrapper.write(Type.VAR_INT, n2);
                    return;
                }
                packetWrapper.setPacketType(ClientboundPackets1_19_4.PLAYER_POSITION);
                packetWrapper.passthrough(Type.DOUBLE);
                packetWrapper.passthrough(Type.DOUBLE);
                packetWrapper.passthrough(Type.DOUBLE);
                packetWrapper.write(Type.FLOAT, Float.valueOf((float)packetWrapper.read(Type.BYTE).byteValue() * 360.0f / 256.0f));
                packetWrapper.write(Type.FLOAT, Float.valueOf((float)packetWrapper.read(Type.BYTE).byteValue() * 360.0f / 256.0f));
                packetWrapper.read(Type.BOOLEAN);
                packetWrapper.write(Type.BYTE, (byte)0);
                packetWrapper.write(Type.VAR_INT, -1);
            }
        });
        ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.ENTITY_ANIMATION, new PacketHandlers(this){
            final EntityPackets this$0;
            {
                this.this$0 = entityPackets;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(5::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                short s = packetWrapper.read(Type.UNSIGNED_BYTE);
                if (s != 1) {
                    packetWrapper.write(Type.UNSIGNED_BYTE, s);
                    return;
                }
                packetWrapper.setPacketType(ClientboundPackets1_19_4.HIT_ANIMATION);
                packetWrapper.write(Type.FLOAT, Float.valueOf(0.0f));
            }
        });
        ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.RESPAWN, new PacketHandlers(this){
            final EntityPackets this$0;
            {
                this.this$0 = entityPackets;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.STRING);
                this.handler(this.this$0.worldDataTrackerHandlerByKey());
                this.handler(6::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.user().put(new PlayerVehicleTracker(packetWrapper.user()));
            }
        });
        ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.ENTITY_STATUS, this::lambda$registerPackets$0);
        this.registerTrackerWithData1_19(ClientboundPackets1_19_3.SPAWN_ENTITY, Entity1_19_4Types.FALLING_BLOCK);
        this.registerRemoveEntities(ClientboundPackets1_19_3.REMOVE_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_19_3.ENTITY_METADATA, Types1_19_3.METADATA_LIST, Types1_19_4.METADATA_LIST);
    }

    private int damageTypeFromEntityEvent(byte by) {
        switch (by) {
            case 33: {
                return 1;
            }
            case 36: {
                return 0;
            }
            case 37: {
                return 0;
            }
            case 57: {
                return 0;
            }
            case 2: 
            case 44: {
                return 1;
            }
        }
        return 1;
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler(EntityPackets::lambda$registerRewrites$1);
        this.registerMetaTypeHandler(Types1_19_4.META_TYPES.itemType, Types1_19_4.META_TYPES.blockStateType, Types1_19_4.META_TYPES.optionalBlockStateType, Types1_19_4.META_TYPES.particleType);
        this.filter().filterFamily(Entity1_19_4Types.MINECART_ABSTRACT).index(11).handler(this::lambda$registerRewrites$2);
        this.filter().filterFamily(Entity1_19_4Types.BOAT).index(11).handler(EntityPackets::lambda$registerRewrites$3);
        this.filter().filterFamily(Entity1_19_4Types.ABSTRACT_HORSE).removeIndex(18);
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_19_4Types.getTypeFromId(n);
    }

    private static void lambda$registerRewrites$3(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = (Integer)metadata.value();
        if (n > 4) {
            metadata.setValue(n + 1);
        }
    }

    private void lambda$registerRewrites$2(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = (Integer)metadata.value();
        metadata.setValue(((Protocol1_19_4To1_19_3)this.protocol).getMappingData().getNewBlockStateId(n));
    }

    private static void lambda$registerRewrites$1(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = metadata.metaType().typeId();
        if (n >= 14) {
            ++n;
        }
        metadata.setMetaType(Types1_19_4.META_TYPES.byId(n));
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.read(Type.INT);
        byte by = packetWrapper.read(Type.BYTE);
        int n2 = this.damageTypeFromEntityEvent(by);
        if (n2 != -1) {
            packetWrapper.setPacketType(ClientboundPackets1_19_4.DAMAGE_EVENT);
            packetWrapper.write(Type.VAR_INT, n);
            packetWrapper.write(Type.VAR_INT, n2);
            packetWrapper.write(Type.VAR_INT, 0);
            packetWrapper.write(Type.VAR_INT, 0);
            packetWrapper.write(Type.BOOLEAN, false);
            return;
        }
        packetWrapper.write(Type.INT, n);
        packetWrapper.write(Type.BYTE, by);
    }

    static Protocol access$000(EntityPackets entityPackets) {
        return entityPackets.protocol;
    }
}

