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
import com.viaversion.viaversion.api.protocol.Protocol;
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
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
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

    public EntityPackets(Protocol1_19To1_18_2 protocol1_19To1_18_2) {
        super(protocol1_19To1_18_2);
    }

    @Override
    public void registerPackets() {
        this.registerTracker(ClientboundPackets1_18.SPAWN_PLAYER, Entity1_19Types.PLAYER);
        this.registerMetadataRewriter(ClientboundPackets1_18.ENTITY_METADATA, Types1_18.METADATA_LIST, Types1_19.METADATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_18.REMOVE_ENTITIES);
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_18.SPAWN_ENTITY, new PacketHandlers(this){
            final EntityPackets this$0;
            {
                this.this$0 = entityPackets;
            }

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
                this.handler(1::lambda$register$0);
                this.map((Type)Type.INT, Type.VAR_INT);
                this.handler(this.this$0.trackerHandler());
                this.handler(this::lambda$register$1);
            }

            private void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityType entityType = this.this$0.tracker(packetWrapper.user()).entityType(n);
                if (entityType == Entity1_19Types.FALLING_BLOCK) {
                    packetWrapper.set(Type.VAR_INT, 2, ((Protocol1_19To1_18_2)EntityPackets.access$000(this.this$0)).getMappingData().getNewBlockStateId(packetWrapper.get(Type.VAR_INT, 2)));
                }
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                byte by = packetWrapper.get(Type.BYTE, 1);
                packetWrapper.write(Type.BYTE, by);
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_18.SPAWN_PAINTING, ClientboundPackets1_19.SPAWN_ENTITY, new PacketHandlers(this){
            final EntityPackets this$0;
            {
                this.this$0 = entityPackets;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.VAR_INT, Entity1_19Types.PAINTING.getId());
                int n = packetWrapper.read(Type.VAR_INT);
                Position position = packetWrapper.read(Type.POSITION1_14);
                byte by = packetWrapper.read(Type.BYTE);
                packetWrapper.write(Type.DOUBLE, (double)position.x() + 0.5);
                packetWrapper.write(Type.DOUBLE, (double)position.y() + 0.5);
                packetWrapper.write(Type.DOUBLE, (double)position.z() + 0.5);
                packetWrapper.write(Type.BYTE, (byte)0);
                packetWrapper.write(Type.BYTE, (byte)0);
                packetWrapper.write(Type.BYTE, (byte)0);
                packetWrapper.write(Type.VAR_INT, EntityPackets.access$100(by));
                packetWrapper.write(Type.SHORT, (short)0);
                packetWrapper.write(Type.SHORT, (short)0);
                packetWrapper.write(Type.SHORT, (short)0);
                packetWrapper.send(Protocol1_19To1_18_2.class);
                packetWrapper.cancel();
                PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_19.ENTITY_METADATA);
                packetWrapper2.write(Type.VAR_INT, packetWrapper.get(Type.VAR_INT, 0));
                ArrayList<Metadata> arrayList = new ArrayList<Metadata>();
                arrayList.add(new Metadata(8, Types1_19.META_TYPES.paintingVariantType, ((Protocol1_19To1_18_2)EntityPackets.access$200(this.this$0)).getMappingData().getPaintingMappings().getNewIdOrDefault(n, 0)));
                packetWrapper2.write(Types1_19.METADATA_LIST, arrayList);
                packetWrapper2.send(Protocol1_19To1_18_2.class);
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_18.SPAWN_MOB, ClientboundPackets1_19.SPAWN_ENTITY, new PacketHandlers(this){
            final EntityPackets this$0;
            {
                this.this$0 = entityPackets;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.handler(3::lambda$register$0);
                this.map(Type.BYTE);
                this.create(Type.VAR_INT, 0);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(this.this$0.trackerHandler());
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                byte by = packetWrapper.read(Type.BYTE);
                byte by2 = packetWrapper.read(Type.BYTE);
                packetWrapper.write(Type.BYTE, by2);
                packetWrapper.write(Type.BYTE, by);
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_18.ENTITY_EFFECT, new PacketHandlers(this){
            final EntityPackets this$0;
            {
                this.this$0 = entityPackets;
            }

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
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_18.JOIN_GAME, new PacketHandlers(this){
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
                this.handler(this::lambda$register$0);
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
                this.handler(this.this$0.playerTrackerHandler());
                this.handler(this.this$0.worldDataTrackerHandlerByKey());
                this.handler(this.this$0.biomeSizeTracker());
                this.handler(5::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_19.SET_DISPLAY_CHAT_PREVIEW);
                packetWrapper2.write(Type.BOOLEAN, false);
                packetWrapper2.scheduleSend(Protocol1_19To1_18_2.class);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                CompoundTag compoundTag = packetWrapper.get(Type.NBT, 0);
                compoundTag.put("minecraft:chat_type", CHAT_REGISTRY.clone());
                ListTag listTag = (ListTag)((CompoundTag)compoundTag.get("minecraft:dimension_type")).get("value");
                HashMap<String, DimensionData> hashMap = new HashMap<String, DimensionData>(listTag.size());
                HashMap<CompoundTag, String> hashMap2 = new HashMap<CompoundTag, String>(listTag.size());
                for (Tag tag : listTag) {
                    CompoundTag compoundTag2 = (CompoundTag)tag;
                    CompoundTag compoundTag3 = (CompoundTag)compoundTag2.get("element");
                    String string = (String)((Tag)compoundTag2.get("name")).getValue();
                    EntityPackets.access$300(compoundTag3);
                    hashMap.put(string, new DimensionDataImpl(compoundTag3));
                    hashMap2.put(compoundTag3.clone(), string);
                }
                this.this$0.tracker(packetWrapper.user()).setDimensions(hashMap);
                DimensionRegistryStorage dimensionRegistryStorage = packetWrapper.user().get(DimensionRegistryStorage.class);
                dimensionRegistryStorage.setDimensions(hashMap2);
                EntityPackets.access$400(packetWrapper, dimensionRegistryStorage);
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_18.RESPAWN, new PacketHandlers(this){
            final EntityPackets this$0;
            {
                this.this$0 = entityPackets;
            }

            @Override
            public void register() {
                this.handler(6::lambda$register$0);
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.create(Type.OPTIONAL_GLOBAL_POSITION, null);
                this.handler(this.this$0.worldDataTrackerHandlerByKey());
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                EntityPackets.access$400(packetWrapper, packetWrapper.user().get(DimensionRegistryStorage.class));
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_18.PLAYER_INFO, EntityPackets::lambda$registerPackets$0);
    }

    private static void writeDimensionKey(PacketWrapper packetWrapper, DimensionRegistryStorage dimensionRegistryStorage) throws Exception {
        CompoundTag compoundTag = packetWrapper.read(Type.NBT);
        EntityPackets.addMonsterSpawnData(compoundTag);
        String string = dimensionRegistryStorage.dimensionKey(compoundTag);
        if (string == null) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
                Via.getPlatform().getLogger().warning("The server tried to send dimension data from a dimension the client wasn't told about on join. Plugins and mods have to make sure they are not creating new dimension types while players are online, and proxies need to make sure they don't scramble dimension data. Received dimension: " + compoundTag + ". Known dimensions: " + dimensionRegistryStorage.dimensions());
            }
            string = (String)((Map.Entry)dimensionRegistryStorage.dimensions().entrySet().stream().map(arg_0 -> EntityPackets.lambda$writeDimensionKey$1(compoundTag, arg_0)).filter(EntityPackets::lambda$writeDimensionKey$2).max(Comparator.comparingInt(EntityPackets::lambda$writeDimensionKey$3)).orElseThrow(() -> EntityPackets.lambda$writeDimensionKey$4(compoundTag)).key()).getValue();
        }
        packetWrapper.write(Type.STRING, string);
    }

    private static int to3dId(int n) {
        switch (n) {
            case -1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 0: {
                return 0;
            }
            case 1: {
                return 1;
            }
            case 3: {
                return 0;
            }
        }
        throw new IllegalArgumentException("Unknown 2d id: " + n);
    }

    private static void addMonsterSpawnData(CompoundTag compoundTag) {
        compoundTag.put("monster_spawn_block_light_limit", new IntTag(0));
        compoundTag.put("monster_spawn_light_level", new IntTag(11));
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler(this::lambda$registerRewrites$5);
        this.registerMetaTypeHandler(Types1_19.META_TYPES.itemType, Types1_19.META_TYPES.blockStateType, null, null);
        this.filter().filterFamily(Entity1_19Types.MINECART_ABSTRACT).index(11).handler(this::lambda$registerRewrites$6);
        this.filter().type(Entity1_19Types.CAT).index(19).handler(EntityPackets::lambda$registerRewrites$7);
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_19Types.getTypeFromId(n);
    }

    private static void lambda$registerRewrites$7(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        metadata.setMetaType(Types1_19.META_TYPES.catVariantType);
    }

    private void lambda$registerRewrites$6(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = (Integer)metadata.getValue();
        metadata.setValue(((Protocol1_19To1_18_2)this.protocol).getMappingData().getNewBlockStateId(n));
    }

    private void lambda$registerRewrites$5(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        metadata.setMetaType(Types1_19.META_TYPES.byId(metadata.metaType().typeId()));
        MetaType metaType = metadata.metaType();
        if (metaType == Types1_19.META_TYPES.particleType) {
            Particle particle = (Particle)metadata.getValue();
            ParticleMappings particleMappings = ((Protocol1_19To1_18_2)this.protocol).getMappingData().getParticleMappings();
            if (particle.getId() == particleMappings.id("vibration")) {
                particle.getArguments().remove(0);
                String string = Key.stripMinecraftNamespace((String)particle.getArguments().get(0).get());
                if (string.equals("entity")) {
                    particle.getArguments().add(2, new Particle.ParticleData(Type.FLOAT, Float.valueOf(0.0f)));
                }
            }
            this.rewriteParticle(particle);
        }
    }

    private static IllegalArgumentException lambda$writeDimensionKey$4(CompoundTag compoundTag) {
        return new IllegalArgumentException("Dimension not found in registry data from join packet: " + compoundTag);
    }

    private static int lambda$writeDimensionKey$3(Pair pair) {
        return ((Map)pair.value()).size();
    }

    private static boolean lambda$writeDimensionKey$2(Pair pair) {
        return ((Map)pair.value()).containsKey("min_y") && ((Map)pair.value()).containsKey("height");
    }

    private static Pair lambda$writeDimensionKey$1(CompoundTag compoundTag, Map.Entry entry) {
        return new Pair(entry, Maps.difference(compoundTag.getValue(), ((CompoundTag)entry.getKey()).getValue()).entriesInCommon());
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT);
        int n2 = packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n2; ++i) {
            packetWrapper.passthrough(Type.UUID);
            if (n == 0) {
                packetWrapper.passthrough(Type.STRING);
                int n3 = packetWrapper.passthrough(Type.VAR_INT);
                for (int j = 0; j < n3; ++j) {
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.OPTIONAL_STRING);
                }
                packetWrapper.passthrough(Type.VAR_INT);
                packetWrapper.passthrough(Type.VAR_INT);
                JsonElement jsonElement = packetWrapper.read(Type.OPTIONAL_COMPONENT);
                if (!Protocol1_19To1_18_2.isTextComponentNull(jsonElement)) {
                    packetWrapper.write(Type.OPTIONAL_COMPONENT, jsonElement);
                } else {
                    packetWrapper.write(Type.OPTIONAL_COMPONENT, null);
                }
                packetWrapper.write(Type.OPTIONAL_PROFILE_KEY, null);
                continue;
            }
            if (n == 1 || n == 2) {
                packetWrapper.passthrough(Type.VAR_INT);
                continue;
            }
            if (n != 3) continue;
            JsonElement jsonElement = packetWrapper.read(Type.OPTIONAL_COMPONENT);
            if (!Protocol1_19To1_18_2.isTextComponentNull(jsonElement)) {
                packetWrapper.write(Type.OPTIONAL_COMPONENT, jsonElement);
                continue;
            }
            packetWrapper.write(Type.OPTIONAL_COMPONENT, null);
        }
    }

    static Protocol access$000(EntityPackets entityPackets) {
        return entityPackets.protocol;
    }

    static int access$100(int n) {
        return EntityPackets.to3dId(n);
    }

    static Protocol access$200(EntityPackets entityPackets) {
        return entityPackets.protocol;
    }

    static void access$300(CompoundTag compoundTag) {
        EntityPackets.addMonsterSpawnData(compoundTag);
    }

    static void access$400(PacketWrapper packetWrapper, DimensionRegistryStorage dimensionRegistryStorage) throws Exception {
        EntityPackets.writeDimensionKey(packetWrapper, dimensionRegistryStorage);
    }

    static {
        try {
            CHAT_REGISTRY = (CompoundTag)BinaryTagIO.readString(CHAT_REGISTRY_SNBT).get("minecraft:chat_type");
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }
}

