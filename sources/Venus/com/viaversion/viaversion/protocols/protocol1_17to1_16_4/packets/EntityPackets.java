/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets;

import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.api.type.types.version.Types1_17;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;

public final class EntityPackets
extends EntityRewriter<ClientboundPackets1_16_2, Protocol1_17To1_16_4> {
    public EntityPackets(Protocol1_17To1_16_4 protocol1_17To1_16_4) {
        super(protocol1_17To1_16_4);
        this.mapTypes(Entity1_16_2Types.values(), Entity1_17Types.class);
    }

    @Override
    public void registerPackets() {
        this.registerTrackerWithData(ClientboundPackets1_16_2.SPAWN_ENTITY, Entity1_17Types.FALLING_BLOCK);
        this.registerTracker(ClientboundPackets1_16_2.SPAWN_MOB);
        this.registerTracker(ClientboundPackets1_16_2.SPAWN_PLAYER, Entity1_17Types.PLAYER);
        this.registerMetadataRewriter(ClientboundPackets1_16_2.ENTITY_METADATA, Types1_16.METADATA_LIST, Types1_17.METADATA_LIST);
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_16_2.DESTROY_ENTITIES, null, EntityPackets::lambda$registerPackets$0);
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_16_2.JOIN_GAME, new PacketHandlers(this){
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
                this.map(Type.NBT);
                this.handler(1::lambda$register$0);
                this.handler(this.this$0.playerTrackerHandler());
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                CompoundTag compoundTag = (CompoundTag)packetWrapper.get(Type.NBT, 0).get("minecraft:dimension_type");
                ListTag listTag = (ListTag)compoundTag.get("value");
                for (Tag tag : listTag) {
                    CompoundTag compoundTag2 = (CompoundTag)((CompoundTag)tag).get("element");
                    EntityPackets.access$000(compoundTag2);
                }
                CompoundTag compoundTag3 = packetWrapper.get(Type.NBT, 1);
                EntityPackets.access$000(compoundTag3);
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_16_2.RESPAWN, EntityPackets::lambda$registerPackets$1);
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_16_2.ENTITY_PROPERTIES, new PacketHandlers(this){
            final EntityPackets this$0;
            {
                this.this$0 = entityPackets;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.VAR_INT, packetWrapper.read(Type.INT));
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_16_2.PLAYER_POSITION, new PacketHandlers(this){
            final EntityPackets this$0;
            {
                this.this$0 = entityPackets;
            }

            @Override
            public void register() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BOOLEAN, false);
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_16_2.COMBAT_EVENT, null, EntityPackets::lambda$registerPackets$2);
        ((Protocol1_17To1_16_4)this.protocol).cancelClientbound(ClientboundPackets1_16_2.ENTITY_MOVEMENT);
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler(EntityPackets::lambda$registerRewrites$3);
        this.registerMetaTypeHandler(Types1_17.META_TYPES.itemType, Types1_17.META_TYPES.blockStateType, null, Types1_17.META_TYPES.particleType);
        this.filter().filterFamily(Entity1_17Types.ENTITY).addIndex(7);
        this.filter().filterFamily(Entity1_17Types.MINECART_ABSTRACT).index(11).handler(this::lambda$registerRewrites$4);
        this.filter().type(Entity1_17Types.SHULKER).removeIndex(17);
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_17Types.getTypeFromId(n);
    }

    private static void addNewDimensionData(CompoundTag compoundTag) {
        compoundTag.put("min_y", new IntTag(0));
        compoundTag.put("height", new IntTag(256));
    }

    private void lambda$registerRewrites$4(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = (Integer)metadata.getValue();
        metadata.setValue(((Protocol1_17To1_16_4)this.protocol).getMappingData().getNewBlockStateId(n));
    }

    private static void lambda$registerRewrites$3(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n;
        metadata.setMetaType(Types1_17.META_TYPES.byId(metadata.metaType().typeId()));
        if (metadata.metaType() == Types1_17.META_TYPES.poseType && (n = ((Integer)metadata.value()).intValue()) > 5) {
            metadata.setValue(n + 1);
        }
    }

    private static void lambda$registerPackets$2(PacketWrapper packetWrapper) throws Exception {
        ClientboundPackets1_17 clientboundPackets1_17;
        int n = packetWrapper.read(Type.VAR_INT);
        switch (n) {
            case 0: {
                clientboundPackets1_17 = ClientboundPackets1_17.COMBAT_ENTER;
                break;
            }
            case 1: {
                clientboundPackets1_17 = ClientboundPackets1_17.COMBAT_END;
                break;
            }
            case 2: {
                clientboundPackets1_17 = ClientboundPackets1_17.COMBAT_KILL;
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid combat type received: " + n);
            }
        }
        packetWrapper.setPacketType(clientboundPackets1_17);
    }

    private static void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        CompoundTag compoundTag = packetWrapper.passthrough(Type.NBT);
        EntityPackets.addNewDimensionData(compoundTag);
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        int[] nArray = packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
        packetWrapper.cancel();
        Object t = packetWrapper.user().getEntityTracker(Protocol1_17To1_16_4.class);
        for (int n : nArray) {
            t.removeEntity(n);
            PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_17.REMOVE_ENTITY);
            packetWrapper2.write(Type.VAR_INT, n);
            packetWrapper2.send(Protocol1_17To1_16_4.class);
        }
    }

    static void access$000(CompoundTag compoundTag) {
        EntityPackets.addNewDimensionData(compoundTag);
    }
}

