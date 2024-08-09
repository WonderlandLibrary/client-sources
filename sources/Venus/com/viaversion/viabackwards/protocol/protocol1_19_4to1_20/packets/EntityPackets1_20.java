/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.packets;

import com.google.common.collect.Sets;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.Protocol1_19_4To1_20;
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
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import com.viaversion.viaversion.util.Key;
import java.util.Set;

public final class EntityPackets1_20
extends EntityRewriter<ClientboundPackets1_19_4, Protocol1_19_4To1_20> {
    private final Set<String> newTrimPatterns = Sets.newHashSet("host_armor_trim_smithing_template", "raiser_armor_trim_smithing_template", "silence_armor_trim_smithing_template", "shaper_armor_trim_smithing_template", "wayfinder_armor_trim_smithing_template");
    private static final Quaternion Y_FLIPPED_ROTATION = new Quaternion(0.0f, 1.0f, 0.0f, 0.0f);

    public EntityPackets1_20(Protocol1_19_4To1_20 protocol1_19_4To1_20) {
        super(protocol1_19_4To1_20);
    }

    @Override
    public void registerPackets() {
        this.registerTrackerWithData1_19(ClientboundPackets1_19_4.SPAWN_ENTITY, Entity1_19_4Types.FALLING_BLOCK);
        this.registerMetadataRewriter(ClientboundPackets1_19_4.ENTITY_METADATA, Types1_20.METADATA_LIST, Types1_19_4.METADATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_19_4.REMOVE_ENTITIES);
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.JOIN_GAME, new PacketHandlers(this){
            final EntityPackets1_20 this$0;
            {
                this.this$0 = entityPackets1_20;
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
                this.read(Type.VAR_INT);
                this.handler(this.this$0.dimensionDataHandler());
                this.handler(this.this$0.biomeSizeTracker());
                this.handler(this.this$0.worldDataTrackerHandlerByKey());
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ListTag listTag;
                CompoundTag compoundTag = packetWrapper.get(Type.NBT, 0);
                if (compoundTag.contains("minecraft:trim_pattern")) {
                    listTag = (ListTag)((CompoundTag)compoundTag.get("minecraft:trim_pattern")).get("value");
                } else {
                    CompoundTag compoundTag2 = Protocol1_19_4To1_20.MAPPINGS.getTrimPatternRegistry().clone();
                    compoundTag.put("minecraft:trim_pattern", compoundTag2);
                    listTag = (ListTag)compoundTag2.get("value");
                }
                for (Tag tag : listTag) {
                    CompoundTag compoundTag3 = (CompoundTag)((CompoundTag)tag).get("element");
                    StringTag stringTag = (StringTag)compoundTag3.get("template_item");
                    if (!EntityPackets1_20.access$000(this.this$0).contains(Key.stripMinecraftNamespace(stringTag.getValue()))) continue;
                    stringTag.setValue("minecraft:spire_armor_trim_smithing_template");
                }
            }
        });
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.RESPAWN, new PacketHandlers(this){
            final EntityPackets1_20 this$0;
            {
                this.this$0 = entityPackets1_20;
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
                this.read(Type.VAR_INT);
                this.handler(this.this$0.worldDataTrackerHandlerByKey());
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler(EntityPackets1_20::lambda$registerRewrites$0);
        this.registerMetaTypeHandler(Types1_19_4.META_TYPES.itemType, Types1_19_4.META_TYPES.blockStateType, Types1_19_4.META_TYPES.optionalBlockStateType, Types1_19_4.META_TYPES.particleType, Types1_19_4.META_TYPES.componentType, Types1_19_4.META_TYPES.optionalComponentType);
        this.filter().filterFamily(Entity1_19_4Types.MINECART_ABSTRACT).index(11).handler(this::lambda$registerRewrites$1);
        this.filter().filterFamily(Entity1_19_4Types.ITEM_DISPLAY).handler(EntityPackets1_20::lambda$registerRewrites$2);
        this.filter().filterFamily(Entity1_19_4Types.ITEM_DISPLAY).index(12).handler(this::lambda$registerRewrites$3);
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_19_4Types.getTypeFromId(n);
    }

    private Quaternion rotateY180(Quaternion quaternion) {
        return new Quaternion(-quaternion.z(), quaternion.w(), quaternion.x(), -quaternion.y());
    }

    private void lambda$registerRewrites$3(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        Quaternion quaternion = (Quaternion)metadata.value();
        metadata.setValue(this.rotateY180(quaternion));
    }

    private static void lambda$registerRewrites$2(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        if (metaHandlerEvent.trackedEntity().hasSentMetadata() || metaHandlerEvent.hasExtraMeta()) {
            return;
        }
        if (metaHandlerEvent.metaAtIndex(12) == null) {
            metaHandlerEvent.createExtraMeta(new Metadata(12, Types1_19_4.META_TYPES.quaternionType, Y_FLIPPED_ROTATION));
        }
    }

    private void lambda$registerRewrites$1(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = (Integer)metadata.value();
        metadata.setValue(((Protocol1_19_4To1_20)this.protocol).getMappingData().getNewBlockStateId(n));
    }

    private static void lambda$registerRewrites$0(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        metadata.setMetaType(Types1_19_4.META_TYPES.byId(metadata.metaType().typeId()));
    }

    static Set access$000(EntityPackets1_20 entityPackets1_20) {
        return entityPackets1_20.newTrimPatterns;
    }
}

