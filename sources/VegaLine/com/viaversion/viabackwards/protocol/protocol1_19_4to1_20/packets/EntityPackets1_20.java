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
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
import com.viaversion.viaversion.api.type.types.version.Types1_20;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.util.Key;
import java.util.Set;

public final class EntityPackets1_20
extends EntityRewriter<ClientboundPackets1_19_4, Protocol1_19_4To1_20> {
    private final Set<String> newTrimPatterns = Sets.newHashSet("host_armor_trim_smithing_template", "raiser_armor_trim_smithing_template", "silence_armor_trim_smithing_template", "shaper_armor_trim_smithing_template", "wayfinder_armor_trim_smithing_template");
    private static final Quaternion Y_FLIPPED_ROTATION = new Quaternion(0.0f, 1.0f, 0.0f, 0.0f);

    public EntityPackets1_20(Protocol1_19_4To1_20 protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        this.registerTrackerWithData1_19(ClientboundPackets1_19_4.SPAWN_ENTITY, Entity1_19_4Types.FALLING_BLOCK);
        this.registerMetadataRewriter(ClientboundPackets1_19_4.ENTITY_METADATA, Types1_20.METADATA_LIST, Types1_19_4.METADATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_19_4.REMOVE_ENTITIES);
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.JOIN_GAME, new PacketHandlers(){

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
                this.handler(EntityPackets1_20.this.dimensionDataHandler());
                this.handler(EntityPackets1_20.this.biomeSizeTracker());
                this.handler(EntityPackets1_20.this.worldDataTrackerHandlerByKey());
                this.handler(wrapper -> {
                    ListTag values;
                    CompoundTag registry = wrapper.get(Type.NBT, 0);
                    if (registry.contains("minecraft:trim_pattern")) {
                        values = (ListTag)((CompoundTag)registry.get("minecraft:trim_pattern")).get("value");
                    } else {
                        CompoundTag trimPatternRegistry = Protocol1_19_4To1_20.MAPPINGS.getTrimPatternRegistry().clone();
                        registry.put("minecraft:trim_pattern", trimPatternRegistry);
                        values = (ListTag)trimPatternRegistry.get("value");
                    }
                    for (Tag entry : values) {
                        CompoundTag element = (CompoundTag)((CompoundTag)entry).get("element");
                        StringTag templateItem = (StringTag)element.get("template_item");
                        if (!EntityPackets1_20.this.newTrimPatterns.contains(Key.stripMinecraftNamespace(templateItem.getValue()))) continue;
                        templateItem.setValue("minecraft:spire_armor_trim_smithing_template");
                    }
                });
            }
        });
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.RESPAWN, new PacketHandlers(){

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
                this.handler(EntityPackets1_20.this.worldDataTrackerHandlerByKey());
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler((event, meta) -> meta.setMetaType(Types1_19_4.META_TYPES.byId(meta.metaType().typeId())));
        this.registerMetaTypeHandler(Types1_19_4.META_TYPES.itemType, Types1_19_4.META_TYPES.blockStateType, Types1_19_4.META_TYPES.optionalBlockStateType, Types1_19_4.META_TYPES.particleType, Types1_19_4.META_TYPES.componentType, Types1_19_4.META_TYPES.optionalComponentType);
        this.filter().filterFamily(Entity1_19_4Types.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
            int blockState = (Integer)meta.value();
            meta.setValue(((Protocol1_19_4To1_20)this.protocol).getMappingData().getNewBlockStateId(blockState));
        });
        this.filter().filterFamily(Entity1_19_4Types.ITEM_DISPLAY).handler((event, meta) -> {
            if (event.trackedEntity().hasSentMetadata() || event.hasExtraMeta()) {
                return;
            }
            if (event.metaAtIndex(12) == null) {
                event.createExtraMeta(new Metadata(12, Types1_19_4.META_TYPES.quaternionType, Y_FLIPPED_ROTATION));
            }
        });
        this.filter().filterFamily(Entity1_19_4Types.ITEM_DISPLAY).index(12).handler((event, meta) -> {
            Quaternion quaternion = (Quaternion)meta.value();
            meta.setValue(this.rotateY180(quaternion));
        });
    }

    @Override
    public EntityType typeFromId(int type2) {
        return Entity1_19_4Types.getTypeFromId(type2);
    }

    private Quaternion rotateY180(Quaternion quaternion) {
        return new Quaternion(-quaternion.z(), quaternion.w(), quaternion.x(), -quaternion.y());
    }
}

