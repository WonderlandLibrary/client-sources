/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.api.type.types.version.Types1_17;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;

public final class EntityPackets1_17
extends EntityRewriter<ClientboundPackets1_17, Protocol1_16_4To1_17> {
    private boolean warned;

    public EntityPackets1_17(Protocol1_16_4To1_17 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        this.registerTrackerWithData(ClientboundPackets1_17.SPAWN_ENTITY, Entity1_17Types.FALLING_BLOCK);
        this.registerSpawnTracker(ClientboundPackets1_17.SPAWN_MOB);
        this.registerTracker(ClientboundPackets1_17.SPAWN_EXPERIENCE_ORB, Entity1_17Types.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_17.SPAWN_PAINTING, Entity1_17Types.PAINTING);
        this.registerTracker(ClientboundPackets1_17.SPAWN_PLAYER, Entity1_17Types.PLAYER);
        this.registerMetadataRewriter(ClientboundPackets1_17.ENTITY_METADATA, Types1_17.METADATA_LIST, Types1_16.METADATA_LIST);
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.REMOVE_ENTITY, ClientboundPackets1_16_2.DESTROY_ENTITIES, wrapper -> {
            int entityId = wrapper.read(Type.VAR_INT);
            this.tracker(wrapper.user()).removeEntity(entityId);
            int[] array = new int[]{entityId};
            wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, array);
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.JOIN_GAME, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.map(Type.NBT);
                this.map(Type.NBT);
                this.map(Type.STRING);
                this.handler(wrapper -> {
                    byte previousGamemode = wrapper.get(Type.BYTE, 0);
                    if (previousGamemode == -1) {
                        wrapper.set(Type.BYTE, 0, (byte)0);
                    }
                });
                this.handler(EntityPackets1_17.this.getTrackerHandler(Entity1_17Types.PLAYER, Type.INT));
                this.handler(EntityPackets1_17.this.worldDataTrackerHandler(1));
                this.handler(wrapper -> {
                    CompoundTag registry = wrapper.get(Type.NBT, 0);
                    CompoundTag biomeRegistry = (CompoundTag)registry.get("minecraft:worldgen/biome");
                    ListTag biomes = (ListTag)biomeRegistry.get("value");
                    for (Tag biome : biomes) {
                        CompoundTag biomeCompound = (CompoundTag)((CompoundTag)biome).get("element");
                        StringTag category = (StringTag)biomeCompound.get("category");
                        if (!category.getValue().equalsIgnoreCase("underground")) continue;
                        category.setValue("none");
                    }
                    CompoundTag dimensionRegistry = (CompoundTag)registry.get("minecraft:dimension_type");
                    ListTag dimensions = (ListTag)dimensionRegistry.get("value");
                    for (Tag dimension : dimensions) {
                        CompoundTag dimensionCompound = (CompoundTag)((CompoundTag)dimension).get("element");
                        EntityPackets1_17.this.reduceExtendedHeight(dimensionCompound, false);
                    }
                    EntityPackets1_17.this.reduceExtendedHeight(wrapper.get(Type.NBT, 1), true);
                });
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.NBT);
                this.map(Type.STRING);
                this.handler(EntityPackets1_17.this.worldDataTrackerHandler(0));
                this.handler(wrapper -> EntityPackets1_17.this.reduceExtendedHeight(wrapper.get(Type.NBT, 0), true));
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.PLAYER_POSITION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> wrapper.read(Type.BOOLEAN));
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.ENTITY_PROPERTIES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(wrapper -> wrapper.write(Type.INT, wrapper.read(Type.VAR_INT)));
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.COMBAT_ENTER, ClientboundPackets1_16_2.COMBAT_EVENT, 0);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.COMBAT_END, ClientboundPackets1_16_2.COMBAT_EVENT, 1);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.COMBAT_KILL, ClientboundPackets1_16_2.COMBAT_EVENT, 2);
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler((event, meta) -> {
            meta.setMetaType(Types1_16.META_TYPES.byId(meta.metaType().typeId()));
            MetaType type2 = meta.metaType();
            if (type2 == Types1_16.META_TYPES.particleType) {
                Particle particle2 = (Particle)meta.getValue();
                if (particle2.getId() == 16) {
                    particle2.getArguments().subList(4, 7).clear();
                } else if (particle2.getId() == 37) {
                    particle2.setId(0);
                    particle2.getArguments().clear();
                    return;
                }
                this.rewriteParticle(particle2);
            } else if (type2 == Types1_16.META_TYPES.poseType) {
                int pose = (Integer)meta.value();
                if (pose == 6) {
                    meta.setValue(1);
                } else if (pose > 6) {
                    meta.setValue(pose - 1);
                }
            }
        });
        this.registerMetaTypeHandler(Types1_16.META_TYPES.itemType, Types1_16.META_TYPES.blockStateType, null, null, Types1_16.META_TYPES.componentType, Types1_16.META_TYPES.optionalComponentType);
        this.mapTypes(Entity1_17Types.values(), Entity1_16_2Types.class);
        this.filter().type(Entity1_17Types.AXOLOTL).cancel(17);
        this.filter().type(Entity1_17Types.AXOLOTL).cancel(18);
        this.filter().type(Entity1_17Types.AXOLOTL).cancel(19);
        this.filter().type(Entity1_17Types.GLOW_SQUID).cancel(16);
        this.filter().type(Entity1_17Types.GOAT).cancel(17);
        this.mapEntityTypeWithData(Entity1_17Types.AXOLOTL, Entity1_17Types.TROPICAL_FISH).jsonName();
        this.mapEntityTypeWithData(Entity1_17Types.GOAT, Entity1_17Types.SHEEP).jsonName();
        this.mapEntityTypeWithData(Entity1_17Types.GLOW_SQUID, Entity1_17Types.SQUID).jsonName();
        this.mapEntityTypeWithData(Entity1_17Types.GLOW_ITEM_FRAME, Entity1_17Types.ITEM_FRAME);
        this.filter().type(Entity1_17Types.SHULKER).addIndex(17);
        this.filter().removeIndex(7);
    }

    @Override
    public EntityType typeFromId(int typeId) {
        return Entity1_17Types.getTypeFromId(typeId);
    }

    private void reduceExtendedHeight(CompoundTag tag, boolean warn) {
        IntTag minY = (IntTag)tag.get("min_y");
        IntTag height = (IntTag)tag.get("height");
        IntTag logicalHeight = (IntTag)tag.get("logical_height");
        if (minY.asInt() != 0 || height.asInt() > 256 || logicalHeight.asInt() > 256) {
            if (warn && !this.warned) {
                ViaBackwards.getPlatform().getLogger().warning("Custom worlds heights are NOT SUPPORTED for 1.16 players and older and may lead to errors!");
                ViaBackwards.getPlatform().getLogger().warning("You have min/max set to " + minY.asInt() + "/" + height.asInt());
                this.warned = true;
            }
            height.setValue(Math.min(256, height.asInt()));
            logicalHeight.setValue(Math.min(256, logicalHeight.asInt()));
        }
    }
}

