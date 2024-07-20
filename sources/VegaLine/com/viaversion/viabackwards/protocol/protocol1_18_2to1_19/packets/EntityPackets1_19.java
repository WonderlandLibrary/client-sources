/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets;

import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.Protocol1_18_2To1_19;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage.DimensionRegistryStorage;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage.StoredPainting;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_19Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_18;
import com.viaversion.viaversion.api.type.types.version.Types1_19;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;

public final class EntityPackets1_19
extends EntityRewriter<ClientboundPackets1_19, Protocol1_18_2To1_19> {
    public EntityPackets1_19(Protocol1_18_2To1_19 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        this.registerTracker(ClientboundPackets1_19.SPAWN_EXPERIENCE_ORB, Entity1_19Types.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_19.SPAWN_PLAYER, Entity1_19Types.PLAYER);
        this.registerMetadataRewriter(ClientboundPackets1_19.ENTITY_METADATA, Types1_19.METADATA_LIST, Types1_18.METADATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_19.REMOVE_ENTITIES);
        ((Protocol1_18_2To1_19)this.protocol).registerClientbound(ClientboundPackets1_19.SPAWN_ENTITY, new PacketHandlers(){

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
                this.handler(wrapper -> {
                    byte headYaw = wrapper.read(Type.BYTE);
                    int data = wrapper.read(Type.VAR_INT);
                    EntityType entityType = EntityPackets1_19.this.trackAndMapEntity(wrapper);
                    if (entityType.isOrHasParent(Entity1_19Types.LIVINGENTITY)) {
                        wrapper.write(Type.BYTE, headYaw);
                        byte pitch = wrapper.get(Type.BYTE, 0);
                        byte yaw = wrapper.get(Type.BYTE, 1);
                        wrapper.set(Type.BYTE, 0, yaw);
                        wrapper.set(Type.BYTE, 1, pitch);
                        wrapper.setPacketType(ClientboundPackets1_18.SPAWN_MOB);
                        return;
                    }
                    if (entityType == Entity1_19Types.PAINTING) {
                        wrapper.cancel();
                        int entityId = wrapper.get(Type.VAR_INT, 0);
                        StoredEntityData entityData = EntityPackets1_19.this.tracker(wrapper.user()).entityData(entityId);
                        Position position = new Position(wrapper.get(Type.DOUBLE, 0).intValue(), wrapper.get(Type.DOUBLE, 1).intValue(), wrapper.get(Type.DOUBLE, 2).intValue());
                        entityData.put(new StoredPainting(entityId, wrapper.get(Type.UUID, 0), position, data));
                        return;
                    }
                    if (entityType == Entity1_19Types.FALLING_BLOCK) {
                        data = ((Protocol1_18_2To1_19)EntityPackets1_19.this.protocol).getMappingData().getNewBlockStateId(data);
                    }
                    wrapper.write(Type.INT, data);
                });
            }
        });
        ((Protocol1_18_2To1_19)this.protocol).registerClientbound(ClientboundPackets1_19.ENTITY_EFFECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.handler(wrapper -> {
                    if (wrapper.read(Type.BOOLEAN).booleanValue()) {
                        wrapper.read(Type.NBT);
                    }
                });
            }
        });
        ((Protocol1_18_2To1_19)this.protocol).registerClientbound(ClientboundPackets1_19.JOIN_GAME, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.map(Type.NBT);
                this.handler(wrapper -> {
                    Object dimensionCompound;
                    DimensionRegistryStorage dimensionRegistryStorage = wrapper.user().get(DimensionRegistryStorage.class);
                    dimensionRegistryStorage.clear();
                    String dimensionKey = wrapper.read(Type.STRING);
                    CompoundTag registry = wrapper.get(Type.NBT, 0);
                    ListTag dimensions = (ListTag)((CompoundTag)registry.get("minecraft:dimension_type")).get("value");
                    boolean found = false;
                    for (Tag dimension : dimensions) {
                        dimensionCompound = (CompoundTag)dimension;
                        StringTag nameTag = (StringTag)((CompoundTag)dimensionCompound).get("name");
                        CompoundTag dimensionData = (CompoundTag)((CompoundTag)dimensionCompound).get("element");
                        dimensionRegistryStorage.addDimension(nameTag.getValue(), dimensionData.clone());
                        if (found || !nameTag.getValue().equals(dimensionKey)) continue;
                        wrapper.write(Type.NBT, dimensionData);
                        found = true;
                    }
                    if (!found) {
                        throw new IllegalStateException("Could not find dimension " + dimensionKey + " in dimension registry");
                    }
                    CompoundTag biomeRegistry = (CompoundTag)registry.get("minecraft:worldgen/biome");
                    ListTag biomes = (ListTag)biomeRegistry.get("value");
                    dimensionCompound = biomes.getValue().iterator();
                    while (dimensionCompound.hasNext()) {
                        Tag biome = (Tag)dimensionCompound.next();
                        CompoundTag biomeCompound = (CompoundTag)((CompoundTag)biome).get("element");
                        biomeCompound.put("category", new StringTag("none"));
                    }
                    EntityPackets1_19.this.tracker(wrapper.user()).setBiomesSent(biomes.size());
                    ListTag chatTypes = (ListTag)((CompoundTag)registry.remove("minecraft:chat_type")).get("value");
                    for (Tag chatType : chatTypes) {
                        CompoundTag chatTypeCompound = (CompoundTag)chatType;
                        NumberTag idTag = (NumberTag)chatTypeCompound.get("id");
                        dimensionRegistryStorage.addChatType(idTag.asInt(), chatTypeCompound);
                    }
                });
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.read(Type.OPTIONAL_GLOBAL_POSITION);
                this.handler(EntityPackets1_19.this.worldDataTrackerHandler(1));
                this.handler(EntityPackets1_19.this.playerTrackerHandler());
            }
        });
        ((Protocol1_18_2To1_19)this.protocol).registerClientbound(ClientboundPackets1_19.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    String dimensionKey = wrapper.read(Type.STRING);
                    CompoundTag dimension = wrapper.user().get(DimensionRegistryStorage.class).dimension(dimensionKey);
                    if (dimension == null) {
                        throw new IllegalArgumentException("Could not find dimension " + dimensionKey + " in dimension registry");
                    }
                    wrapper.write(Type.NBT, dimension);
                });
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.read(Type.OPTIONAL_GLOBAL_POSITION);
                this.handler(EntityPackets1_19.this.worldDataTrackerHandler(0));
            }
        });
        ((Protocol1_18_2To1_19)this.protocol).registerClientbound(ClientboundPackets1_19.PLAYER_INFO, wrapper -> {
            int action = wrapper.passthrough(Type.VAR_INT);
            int entries = wrapper.passthrough(Type.VAR_INT);
            for (int i = 0; i < entries; ++i) {
                wrapper.passthrough(Type.UUID);
                if (action == 0) {
                    wrapper.passthrough(Type.STRING);
                    int properties = wrapper.passthrough(Type.VAR_INT);
                    for (int j = 0; j < properties; ++j) {
                        wrapper.passthrough(Type.STRING);
                        wrapper.passthrough(Type.STRING);
                        wrapper.passthrough(Type.OPTIONAL_STRING);
                    }
                    wrapper.passthrough(Type.VAR_INT);
                    wrapper.passthrough(Type.VAR_INT);
                    wrapper.passthrough(Type.OPTIONAL_COMPONENT);
                    wrapper.read(Type.OPTIONAL_PROFILE_KEY);
                    continue;
                }
                if (action == 1 || action == 2) {
                    wrapper.passthrough(Type.VAR_INT);
                    continue;
                }
                if (action != 3) continue;
                wrapper.passthrough(Type.OPTIONAL_COMPONENT);
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler((event, meta) -> {
            int pose;
            MetaType type2;
            if (meta.metaType().typeId() <= Types1_18.META_TYPES.poseType.typeId()) {
                meta.setMetaType(Types1_18.META_TYPES.byId(meta.metaType().typeId()));
            }
            if ((type2 = meta.metaType()) == Types1_18.META_TYPES.particleType) {
                Particle particle2 = (Particle)meta.getValue();
                ParticleMappings particleMappings = ((Protocol1_18_2To1_19)this.protocol).getMappingData().getParticleMappings();
                if (particle2.getId() == particleMappings.id("sculk_charge")) {
                    event.cancel();
                    return;
                }
                if (particle2.getId() == particleMappings.id("shriek")) {
                    event.cancel();
                    return;
                }
                if (particle2.getId() == particleMappings.id("vibration")) {
                    event.cancel();
                    return;
                }
                this.rewriteParticle(particle2);
            } else if (type2 == Types1_18.META_TYPES.poseType && (pose = ((Integer)meta.value()).intValue()) >= 8) {
                meta.setValue(0);
            }
        });
        this.registerMetaTypeHandler(Types1_18.META_TYPES.itemType, Types1_18.META_TYPES.blockStateType, null, null, Types1_18.META_TYPES.componentType, Types1_18.META_TYPES.optionalComponentType);
        this.filter().filterFamily(Entity1_19Types.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
            int data = (Integer)meta.getValue();
            meta.setValue(((Protocol1_18_2To1_19)this.protocol).getMappingData().getNewBlockStateId(data));
        });
        this.filter().type(Entity1_19Types.PAINTING).index(8).handler((event, meta) -> {
            event.cancel();
            StoredEntityData entityData = this.tracker(event.user()).entityDataIfPresent(event.entityId());
            StoredPainting storedPainting = entityData.remove(StoredPainting.class);
            if (storedPainting != null) {
                PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_18.SPAWN_PAINTING, event.user());
                packet.write(Type.VAR_INT, storedPainting.entityId());
                packet.write(Type.UUID, storedPainting.uuid());
                packet.write(Type.VAR_INT, (Integer)meta.value());
                packet.write(Type.POSITION1_14, storedPainting.position());
                packet.write(Type.BYTE, storedPainting.direction());
                try {
                    packet.send(Protocol1_18_2To1_19.class);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        this.filter().type(Entity1_19Types.CAT).index(19).handler((event, meta) -> meta.setMetaType(Types1_18.META_TYPES.varIntType));
        this.filter().type(Entity1_19Types.FROG).cancel(16);
        this.filter().type(Entity1_19Types.FROG).cancel(17);
        this.filter().type(Entity1_19Types.FROG).cancel(18);
        this.filter().type(Entity1_19Types.WARDEN).cancel(16);
        this.filter().type(Entity1_19Types.GOAT).cancel(18);
        this.filter().type(Entity1_19Types.GOAT).cancel(19);
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
        this.mapEntityTypeWithData(Entity1_19Types.FROG, Entity1_19Types.RABBIT).jsonName();
        this.mapEntityTypeWithData(Entity1_19Types.TADPOLE, Entity1_19Types.PUFFERFISH).jsonName();
        this.mapEntityTypeWithData(Entity1_19Types.CHEST_BOAT, Entity1_19Types.BOAT);
        this.mapEntityTypeWithData(Entity1_19Types.WARDEN, Entity1_19Types.IRON_GOLEM).jsonName();
        this.mapEntityTypeWithData(Entity1_19Types.ALLAY, Entity1_19Types.VEX).jsonName();
    }

    @Override
    public EntityType typeFromId(int typeId) {
        return Entity1_19Types.getTypeFromId(typeId);
    }
}

