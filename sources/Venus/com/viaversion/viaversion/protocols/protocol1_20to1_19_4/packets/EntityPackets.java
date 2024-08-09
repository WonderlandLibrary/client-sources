/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_20to1_19_4.packets;

import com.viaversion.viaversion.api.minecraft.Quaternion;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_4Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
import com.viaversion.viaversion.api.type.types.version.Types1_20;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_20to1_19_4.Protocol1_20To1_19_4;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;

public final class EntityPackets
extends EntityRewriter<ClientboundPackets1_19_4, Protocol1_20To1_19_4> {
    private static final Quaternion Y_FLIPPED_ROTATION = new Quaternion(0.0f, 1.0f, 0.0f, 0.0f);

    public EntityPackets(Protocol1_20To1_19_4 protocol1_20To1_19_4) {
        super(protocol1_20To1_19_4);
    }

    @Override
    public void registerPackets() {
        this.registerTrackerWithData1_19(ClientboundPackets1_19_4.SPAWN_ENTITY, Entity1_19_4Types.FALLING_BLOCK);
        this.registerMetadataRewriter(ClientboundPackets1_19_4.ENTITY_METADATA, Types1_19_4.METADATA_LIST, Types1_20.METADATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_19_4.REMOVE_ENTITIES);
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.JOIN_GAME, new PacketHandlers(this){
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
                this.map(Type.LONG);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.map(Type.OPTIONAL_GLOBAL_POSITION);
                this.create(Type.VAR_INT, 0);
                this.handler(this.this$0.dimensionDataHandler());
                this.handler(this.this$0.biomeSizeTracker());
                this.handler(this.this$0.worldDataTrackerHandlerByKey());
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Tag tag;
                Tag tag22;
                CompoundTag compoundTag = packetWrapper.get(Type.NBT, 0);
                CompoundTag compoundTag2 = (CompoundTag)compoundTag.get("minecraft:damage_type");
                ListTag listTag = (ListTag)compoundTag2.get("value");
                int n = -1;
                for (Tag tag22 : listTag) {
                    tag = (IntTag)((CompoundTag)tag22).get("id");
                    n = Math.max(n, ((IntTag)tag).asInt());
                }
                CompoundTag compoundTag3 = new CompoundTag();
                tag22 = new CompoundTag();
                ((CompoundTag)tag22).put("scaling", new StringTag("always"));
                ((CompoundTag)tag22).put("exhaustion", new FloatTag(0.0f));
                ((CompoundTag)tag22).put("message_id", new StringTag("badRespawnPoint"));
                compoundTag3.put("id", new IntTag(n + 1));
                compoundTag3.put("name", new StringTag("minecraft:outside_border"));
                compoundTag3.put("element", tag22);
                listTag.add(compoundTag3);
                tag = new CompoundTag();
                CompoundTag compoundTag4 = new CompoundTag();
                compoundTag4.put("scaling", new StringTag("always"));
                compoundTag4.put("exhaustion", new FloatTag(0.0f));
                compoundTag4.put("message_id", new StringTag("badRespawnPoint"));
                ((CompoundTag)tag).put("id", new IntTag(n + 2));
                ((CompoundTag)tag).put("name", new StringTag("minecraft:generic_kill"));
                ((CompoundTag)tag).put("element", compoundTag4);
                listTag.add(tag);
            }
        });
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.RESPAWN, new PacketHandlers(this){
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
                this.map(Type.BYTE);
                this.map(Type.OPTIONAL_GLOBAL_POSITION);
                this.create(Type.VAR_INT, 0);
                this.handler(this.this$0.worldDataTrackerHandlerByKey());
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler(EntityPackets::lambda$registerRewrites$0);
        this.registerMetaTypeHandler(Types1_20.META_TYPES.itemType, Types1_20.META_TYPES.blockStateType, Types1_20.META_TYPES.optionalBlockStateType, Types1_20.META_TYPES.particleType);
        this.filter().filterFamily(Entity1_19_4Types.DISPLAY).handler(EntityPackets::lambda$registerRewrites$1);
        this.filter().filterFamily(Entity1_19_4Types.DISPLAY).index(12).handler(this::lambda$registerRewrites$2);
        this.filter().filterFamily(Entity1_19_4Types.MINECART_ABSTRACT).index(11).handler(this::lambda$registerRewrites$3);
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_19_4Types.getTypeFromId(n);
    }

    private Quaternion rotateY180(Quaternion quaternion) {
        return new Quaternion(-quaternion.z(), quaternion.w(), quaternion.x(), -quaternion.y());
    }

    private void lambda$registerRewrites$3(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = (Integer)metadata.value();
        metadata.setValue(((Protocol1_20To1_19_4)this.protocol).getMappingData().getNewBlockStateId(n));
    }

    private void lambda$registerRewrites$2(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        Quaternion quaternion = (Quaternion)metadata.value();
        metadata.setValue(this.rotateY180(quaternion));
    }

    private static void lambda$registerRewrites$1(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        if (metaHandlerEvent.trackedEntity().hasSentMetadata() || metaHandlerEvent.hasExtraMeta()) {
            return;
        }
        if (metaHandlerEvent.metaAtIndex(12) == null) {
            metaHandlerEvent.createExtraMeta(new Metadata(12, Types1_20.META_TYPES.quaternionType, Y_FLIPPED_ROTATION));
        }
    }

    private static void lambda$registerRewrites$0(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        metadata.setMetaType(Types1_20.META_TYPES.byId(metadata.metaType().typeId()));
    }
}

