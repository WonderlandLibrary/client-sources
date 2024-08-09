/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.packets;

import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.Protocol1_19_3To1_19_4;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_4Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_19_3;
import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;

public final class EntityPackets1_19_4
extends EntityRewriter<ClientboundPackets1_19_4, Protocol1_19_3To1_19_4> {
    public EntityPackets1_19_4(Protocol1_19_3To1_19_4 protocol1_19_3To1_19_4) {
        super(protocol1_19_3To1_19_4, Types1_19_3.META_TYPES.optionalComponentType, Types1_19_3.META_TYPES.booleanType);
    }

    @Override
    public void registerPackets() {
        this.registerTrackerWithData1_19(ClientboundPackets1_19_4.SPAWN_ENTITY, Entity1_19_4Types.FALLING_BLOCK);
        this.registerRemoveEntities(ClientboundPackets1_19_4.REMOVE_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_19_4.ENTITY_METADATA, Types1_19_4.METADATA_LIST, Types1_19_3.METADATA_LIST);
        ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.JOIN_GAME, new PacketHandlers(this){
            final EntityPackets1_19_4 this$0;
            {
                this.this$0 = entityPackets1_19_4;
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
                CompoundTag compoundTag = packetWrapper.get(Type.NBT, 0);
                compoundTag.remove("minecraft:trim_pattern");
                compoundTag.remove("minecraft:trim_material");
                compoundTag.remove("minecraft:damage_type");
                CompoundTag compoundTag2 = (CompoundTag)compoundTag.get("minecraft:worldgen/biome");
                ListTag listTag = (ListTag)compoundTag2.get("value");
                for (Tag tag : listTag) {
                    CompoundTag compoundTag3;
                    ByteTag byteTag = (ByteTag)(compoundTag3 = (CompoundTag)((CompoundTag)tag).get("element")).get("has_precipitation");
                    compoundTag3.put("precipitation", new StringTag(byteTag.asByte() == 1 ? "rain" : "none"));
                }
            }
        });
        ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.PLAYER_POSITION, new PacketHandlers(this){
            final EntityPackets1_19_4 this$0;
            {
                this.this$0 = entityPackets1_19_4;
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
                this.create(Type.BOOLEAN, false);
            }
        });
        ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.DAMAGE_EVENT, ClientboundPackets1_19_3.ENTITY_STATUS, new PacketHandlers(this){
            final EntityPackets1_19_4 this$0;
            {
                this.this$0 = entityPackets1_19_4;
            }

            @Override
            public void register() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.read(Type.VAR_INT);
                this.read(Type.VAR_INT);
                this.read(Type.VAR_INT);
                this.handler(3::lambda$register$0);
                this.create(Type.BYTE, (byte)2);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.read(Type.BOOLEAN).booleanValue()) {
                    packetWrapper.read(Type.DOUBLE);
                    packetWrapper.read(Type.DOUBLE);
                    packetWrapper.read(Type.DOUBLE);
                }
            }
        });
        ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.HIT_ANIMATION, ClientboundPackets1_19_3.ENTITY_ANIMATION, new PacketHandlers(this){
            final EntityPackets1_19_4 this$0;
            {
                this.this$0 = entityPackets1_19_4;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.read(Type.FLOAT);
                this.create(Type.UNSIGNED_BYTE, (short)1);
            }
        });
        ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.RESPAWN, new PacketHandlers(this){
            final EntityPackets1_19_4 this$0;
            {
                this.this$0 = entityPackets1_19_4;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.STRING);
                this.handler(this.this$0.worldDataTrackerHandlerByKey());
            }
        });
    }

    @Override
    public void registerRewrites() {
        this.filter().handler(EntityPackets1_19_4::lambda$registerRewrites$0);
        this.registerMetaTypeHandler(Types1_19_3.META_TYPES.itemType, Types1_19_3.META_TYPES.blockStateType, null, Types1_19_3.META_TYPES.particleType, Types1_19_3.META_TYPES.componentType, Types1_19_3.META_TYPES.optionalComponentType);
        this.filter().filterFamily(Entity1_19_4Types.MINECART_ABSTRACT).index(11).handler(this::lambda$registerRewrites$1);
        this.filter().filterFamily(Entity1_19_4Types.BOAT).index(11).handler(EntityPackets1_19_4::lambda$registerRewrites$2);
        this.filter().type(Entity1_19_4Types.TEXT_DISPLAY).index(22).handler(this::lambda$registerRewrites$3);
        this.filter().filterFamily(Entity1_19_4Types.DISPLAY).handler(EntityPackets1_19_4::lambda$registerRewrites$4);
        this.filter().type(Entity1_19_4Types.INTERACTION).removeIndex(8);
        this.filter().type(Entity1_19_4Types.INTERACTION).removeIndex(9);
        this.filter().type(Entity1_19_4Types.INTERACTION).removeIndex(10);
        this.filter().type(Entity1_19_4Types.SNIFFER).removeIndex(17);
        this.filter().type(Entity1_19_4Types.SNIFFER).removeIndex(18);
        this.filter().filterFamily(Entity1_19_4Types.ABSTRACT_HORSE).addIndex(18);
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
        EntityData.MetaCreator metaCreator = EntityPackets1_19_4::lambda$onMappingDataLoaded$5;
        this.mapEntityTypeWithData(Entity1_19_4Types.TEXT_DISPLAY, Entity1_19_4Types.ARMOR_STAND).spawnMetadata(metaCreator);
        this.mapEntityTypeWithData(Entity1_19_4Types.ITEM_DISPLAY, Entity1_19_4Types.ARMOR_STAND).spawnMetadata(metaCreator);
        this.mapEntityTypeWithData(Entity1_19_4Types.BLOCK_DISPLAY, Entity1_19_4Types.ARMOR_STAND).spawnMetadata(metaCreator);
        this.mapEntityTypeWithData(Entity1_19_4Types.INTERACTION, Entity1_19_4Types.ARMOR_STAND).spawnMetadata(metaCreator);
        this.mapEntityTypeWithData(Entity1_19_4Types.SNIFFER, Entity1_19_4Types.RAVAGER).jsonName();
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_19_4Types.getTypeFromId(n);
    }

    private static void lambda$onMappingDataLoaded$5(WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(new Metadata(0, Types1_19_3.META_TYPES.byteType, (byte)32));
        wrappedMetadata.add(new Metadata(5, Types1_19_3.META_TYPES.booleanType, true));
        wrappedMetadata.add(new Metadata(15, Types1_19_3.META_TYPES.byteType, (byte)17));
    }

    private static void lambda$registerRewrites$4(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        if (metaHandlerEvent.index() > 7) {
            metaHandlerEvent.cancel();
        }
    }

    private void lambda$registerRewrites$3(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        metaHandlerEvent.setIndex(2);
        metadata.setMetaType(Types1_19_3.META_TYPES.optionalComponentType);
        metaHandlerEvent.createExtraMeta(new Metadata(3, Types1_19_3.META_TYPES.booleanType, true));
        JsonElement jsonElement = (JsonElement)metadata.value();
        ((Protocol1_19_3To1_19_4)this.protocol).getTranslatableRewriter().processText(jsonElement);
    }

    private static void lambda$registerRewrites$2(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = (Integer)metadata.value();
        if (n > 4) {
            metadata.setValue(n - 1);
        }
    }

    private void lambda$registerRewrites$1(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = (Integer)metadata.value();
        metadata.setValue(((Protocol1_19_3To1_19_4)this.protocol).getMappingData().getNewBlockStateId(n));
    }

    private static void lambda$registerRewrites$0(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = metadata.metaType().typeId();
        if (n >= 25) {
            return;
        }
        if (n >= 15) {
            --n;
        }
        metadata.setMetaType(Types1_19_3.META_TYPES.byId(n));
    }
}

