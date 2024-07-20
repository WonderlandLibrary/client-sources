/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19to1_18_2.packets;

import com.google.common.collect.Maps;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.data.entity.DimensionData;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_19Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.nbt.BinaryTagIO;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_18;
import com.viaversion.viaversion.api.type.types.version.Types1_19;
import com.viaversion.viaversion.data.entity.DimensionDataImpl;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.Protocol1_19To1_18_2;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.storage.DimensionRegistryStorage;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.Pair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class EntityPackets
extends EntityRewriter<ClientboundPackets1_18, Protocol1_19To1_18_2> {
    private static final String CHAT_REGISTRY_SNBT = "{\n  \"minecraft:chat_type\": {\n    \"type\": \"minecraft:chat_type\",\n    \"value\": [\n      {\n        \"name\": \"minecraft:system\",\n        \"id\": 1,\n        \"element\": {\n          \"chat\": {},\n          \"narration\": {\n            \"priority\": \"system\"\n          }\n        }\n      },\n      {\n        \"name\": \"minecraft:game_info\",\n        \"id\": 2,\n        \"element\": {\n          \"overlay\": {}\n        }\n      }\n    ]\n  }\n}";
    public static final CompoundTag CHAT_REGISTRY;

    public EntityPackets(Protocol1_19To1_18_2 protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        this.registerTracker(ClientboundPackets1_18.SPAWN_PLAYER, Entity1_19Types.PLAYER);
        this.registerMetadataRewriter(ClientboundPackets1_18.ENTITY_METADATA, Types1_18.METADATA_LIST, Types1_19.METADATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_18.REMOVE_ENTITIES);
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_18.SPAWN_ENTITY, new PacketHandlers(){

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
                    byte yaw = wrapper.get(Type.BYTE, 1);
                    wrapper.write(Type.BYTE, yaw);
                });
                this.map((Type)Type.INT, Type.VAR_INT);
                this.handler(EntityPackets.this.trackerHandler());
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Type.VAR_INT, 0);
                    EntityType entityType = EntityPackets.this.tracker(wrapper.user()).entityType(entityId);
                    if (entityType == Entity1_19Types.FALLING_BLOCK) {
                        wrapper.set(Type.VAR_INT, 2, ((Protocol1_19To1_18_2)EntityPackets.this.protocol).getMappingData().getNewBlockStateId(wrapper.get(Type.VAR_INT, 2)));
                    }
                });
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_18.SPAWN_PAINTING, ClientboundPackets1_19.SPAWN_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.handler(wrapper -> {
                    wrapper.write(Type.VAR_INT, Entity1_19Types.PAINTING.getId());
                    int motive = wrapper.read(Type.VAR_INT);
                    Position blockPosition = wrapper.read(Type.POSITION1_14);
                    byte direction = wrapper.read(Type.BYTE);
                    wrapper.write(Type.DOUBLE, (double)blockPosition.x() + 0.5);
                    wrapper.write(Type.DOUBLE, (double)blockPosition.y() + 0.5);
                    wrapper.write(Type.DOUBLE, (double)blockPosition.z() + 0.5);
                    wrapper.write(Type.BYTE, (byte)0);
                    wrapper.write(Type.BYTE, (byte)0);
                    wrapper.write(Type.BYTE, (byte)0);
                    wrapper.write(Type.VAR_INT, EntityPackets.to3dId(direction));
                    wrapper.write(Type.SHORT, (short)0);
                    wrapper.write(Type.SHORT, (short)0);
                    wrapper.write(Type.SHORT, (short)0);
                    wrapper.send(Protocol1_19To1_18_2.class);
                    wrapper.cancel();
                    PacketWrapper metaPacket = wrapper.create(ClientboundPackets1_19.ENTITY_METADATA);
                    metaPacket.write(Type.VAR_INT, wrapper.get(Type.VAR_INT, 0));
                    ArrayList<Metadata> metadata = new ArrayList<Metadata>();
                    metadata.add(new Metadata(8, Types1_19.META_TYPES.paintingVariantType, ((Protocol1_19To1_18_2)EntityPackets.this.protocol).getMappingData().getPaintingMappings().getNewIdOrDefault(motive, 0)));
                    metaPacket.write(Types1_19.METADATA_LIST, metadata);
                    metaPacket.send(Protocol1_19To1_18_2.class);
                });
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_18.SPAWN_MOB, ClientboundPackets1_19.SPAWN_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.handler(wrapper -> {
                    byte yaw = wrapper.read(Type.BYTE);
                    byte pitch = wrapper.read(Type.BYTE);
                    wrapper.write(Type.BYTE, pitch);
                    wrapper.write(Type.BYTE, yaw);
                });
                this.map(Type.BYTE);
                this.create(Type.VAR_INT, 0);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(EntityPackets.this.trackerHandler());
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_18.ENTITY_EFFECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.create(Type.BOOLEAN, false);
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_18.JOIN_GAME, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.map(Type.NBT);
                this.handler(wrapper -> {
                    CompoundTag tag = wrapper.get(Type.NBT, 0);
                    tag.put("minecraft:chat_type", CHAT_REGISTRY.clone());
                    ListTag dimensions = (ListTag)((CompoundTag)tag.get("minecraft:dimension_type")).get("value");
                    HashMap<String, DimensionData> dimensionDataMap = new HashMap<String, DimensionData>(dimensions.size());
                    HashMap<CompoundTag, String> dimensionsMap = new HashMap<CompoundTag, String>(dimensions.size());
                    for (Tag dimension : dimensions) {
                        CompoundTag dimensionCompound = (CompoundTag)dimension;
                        CompoundTag element = (CompoundTag)dimensionCompound.get("element");
                        String name = (String)((Tag)dimensionCompound.get("name")).getValue();
                        EntityPackets.addMonsterSpawnData(element);
                        dimensionDataMap.put(name, new DimensionDataImpl(element));
                        dimensionsMap.put(element.clone(), name);
                    }
                    EntityPackets.this.tracker(wrapper.user()).setDimensions(dimensionDataMap);
                    DimensionRegistryStorage registryStorage = wrapper.user().get(DimensionRegistryStorage.class);
                    registryStorage.setDimensions(dimensionsMap);
                    EntityPackets.writeDimensionKey(wrapper, registryStorage);
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
                this.create(Type.OPTIONAL_GLOBAL_POSITION, null);
                this.handler(EntityPackets.this.playerTrackerHandler());
                this.handler(EntityPackets.this.worldDataTrackerHandlerByKey());
                this.handler(EntityPackets.this.biomeSizeTracker());
                this.handler(wrapper -> {
                    PacketWrapper displayPreviewPacket = wrapper.create(ClientboundPackets1_19.SET_DISPLAY_CHAT_PREVIEW);
                    displayPreviewPacket.write(Type.BOOLEAN, false);
                    displayPreviewPacket.scheduleSend(Protocol1_19To1_18_2.class);
                });
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_18.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> EntityPackets.writeDimensionKey(wrapper, wrapper.user().get(DimensionRegistryStorage.class)));
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.create(Type.OPTIONAL_GLOBAL_POSITION, null);
                this.handler(EntityPackets.this.worldDataTrackerHandlerByKey());
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_18.PLAYER_INFO, wrapper -> {
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
                    JsonElement displayName = wrapper.read(Type.OPTIONAL_COMPONENT);
                    if (!Protocol1_19To1_18_2.isTextComponentNull(displayName)) {
                        wrapper.write(Type.OPTIONAL_COMPONENT, displayName);
                    } else {
                        wrapper.write(Type.OPTIONAL_COMPONENT, null);
                    }
                    wrapper.write(Type.OPTIONAL_PROFILE_KEY, null);
                    continue;
                }
                if (action == 1 || action == 2) {
                    wrapper.passthrough(Type.VAR_INT);
                    continue;
                }
                if (action != 3) continue;
                JsonElement displayName = wrapper.read(Type.OPTIONAL_COMPONENT);
                if (!Protocol1_19To1_18_2.isTextComponentNull(displayName)) {
                    wrapper.write(Type.OPTIONAL_COMPONENT, displayName);
                    continue;
                }
                wrapper.write(Type.OPTIONAL_COMPONENT, null);
            }
        });
    }

    private static void writeDimensionKey(PacketWrapper wrapper, DimensionRegistryStorage registryStorage) throws Exception {
        CompoundTag currentDimension = wrapper.read(Type.NBT);
        EntityPackets.addMonsterSpawnData(currentDimension);
        String dimensionKey = registryStorage.dimensionKey(currentDimension);
        if (dimensionKey == null) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
                Via.getPlatform().getLogger().warning("The server tried to send dimension data from a dimension the client wasn't told about on join. Plugins and mods have to make sure they are not creating new dimension types while players are online, and proxies need to make sure they don't scramble dimension data. Received dimension: " + currentDimension + ". Known dimensions: " + registryStorage.dimensions());
            }
            dimensionKey = (String)((Map.Entry)registryStorage.dimensions().entrySet().stream().map(it -> new Pair((Map.Entry)it, Maps.difference(currentDimension.getValue(), ((CompoundTag)it.getKey()).getValue()).entriesInCommon())).filter(it -> ((Map)it.value()).containsKey("min_y") && ((Map)it.value()).containsKey("height")).max(Comparator.comparingInt(it -> ((Map)it.value()).size())).orElseThrow(() -> new IllegalArgumentException("Dimension not found in registry data from join packet: " + currentDimension)).key()).getValue();
        }
        wrapper.write(Type.STRING, dimensionKey);
    }

    private static int to3dId(int id) {
        switch (id) {
            case -1: {
                return 1;
            }
            case 2: {
                return 2;
            }
            case 0: {
                return 3;
            }
            case 1: {
                return 4;
            }
            case 3: {
                return 5;
            }
        }
        throw new IllegalArgumentException("Unknown 2d id: " + id);
    }

    private static void addMonsterSpawnData(CompoundTag dimension) {
        dimension.put("monster_spawn_block_light_limit", new IntTag(0));
        dimension.put("monster_spawn_light_level", new IntTag(11));
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler((event, meta) -> {
            meta.setMetaType(Types1_19.META_TYPES.byId(meta.metaType().typeId()));
            MetaType type2 = meta.metaType();
            if (type2 == Types1_19.META_TYPES.particleType) {
                Particle particle2 = (Particle)meta.getValue();
                ParticleMappings particleMappings = ((Protocol1_19To1_18_2)this.protocol).getMappingData().getParticleMappings();
                if (particle2.getId() == particleMappings.id("vibration")) {
                    particle2.getArguments().remove(0);
                    String resourceLocation = Key.stripMinecraftNamespace((String)particle2.getArguments().get(0).get());
                    if (resourceLocation.equals("entity")) {
                        particle2.getArguments().add(2, new Particle.ParticleData(Type.FLOAT, Float.valueOf(0.0f)));
                    }
                }
                this.rewriteParticle(particle2);
            }
        });
        this.registerMetaTypeHandler(Types1_19.META_TYPES.itemType, Types1_19.META_TYPES.blockStateType, null, null);
        this.filter().filterFamily(Entity1_19Types.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
            int data = (Integer)meta.getValue();
            meta.setValue(((Protocol1_19To1_18_2)this.protocol).getMappingData().getNewBlockStateId(data));
        });
        this.filter().type(Entity1_19Types.CAT).index(19).handler((event, meta) -> meta.setMetaType(Types1_19.META_TYPES.catVariantType));
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
    }

    @Override
    public EntityType typeFromId(int type2) {
        return Entity1_19Types.getTypeFromId(type2);
    }

    static {
        try {
            CHAT_REGISTRY = (CompoundTag)BinaryTagIO.readString(CHAT_REGISTRY_SNBT).get("minecraft:chat_type");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

