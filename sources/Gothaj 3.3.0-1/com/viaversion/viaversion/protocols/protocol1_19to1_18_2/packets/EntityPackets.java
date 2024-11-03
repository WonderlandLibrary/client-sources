package com.viaversion.viaversion.protocols.protocol1_19to1_18_2.packets;

import com.google.common.collect.Maps;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.data.entity.DimensionData;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_18;
import com.viaversion.viaversion.api.type.types.version.Types1_19;
import com.viaversion.viaversion.data.entity.DimensionDataImpl;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.stringified.SNBT;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EntityPackets extends EntityRewriter<ClientboundPackets1_18, Protocol1_19To1_18_2> {
   private static final String CHAT_REGISTRY_SNBT = "{\n  \"minecraft:chat_type\": {\n    \"type\": \"minecraft:chat_type\",\n    \"value\": [\n      {\n        \"name\": \"minecraft:system\",\n        \"id\": 1,\n        \"element\": {\n          \"chat\": {},\n          \"narration\": {\n            \"priority\": \"system\"\n          }\n        }\n      },\n      {\n        \"name\": \"minecraft:game_info\",\n        \"id\": 2,\n        \"element\": {\n          \"overlay\": {}\n        }\n      }\n    ]\n  }\n}";
   public static final CompoundTag CHAT_REGISTRY = SNBT.deserializeCompoundTag(
         "{\n  \"minecraft:chat_type\": {\n    \"type\": \"minecraft:chat_type\",\n    \"value\": [\n      {\n        \"name\": \"minecraft:system\",\n        \"id\": 1,\n        \"element\": {\n          \"chat\": {},\n          \"narration\": {\n            \"priority\": \"system\"\n          }\n        }\n      },\n      {\n        \"name\": \"minecraft:game_info\",\n        \"id\": 2,\n        \"element\": {\n          \"overlay\": {}\n        }\n      }\n    ]\n  }\n}"
      )
      .get("minecraft:chat_type");

   public EntityPackets(Protocol1_19To1_18_2 protocol) {
      super(protocol);
   }

   @Override
   public void registerPackets() {
      this.registerTracker(ClientboundPackets1_18.SPAWN_PLAYER, EntityTypes1_19.PLAYER);
      this.registerMetadataRewriter(ClientboundPackets1_18.ENTITY_METADATA, Types1_18.METADATA_LIST, Types1_19.METADATA_LIST);
      this.registerRemoveEntities(ClientboundPackets1_18.REMOVE_ENTITIES);
      this.protocol.registerClientbound(ClientboundPackets1_18.SPAWN_ENTITY, new PacketHandlers() {
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
            this.map(Type.INT, Type.VAR_INT);
            this.handler(EntityPackets.this.trackerHandler());
            this.handler(wrapper -> {
               int entityId = wrapper.get(Type.VAR_INT, 0);
               EntityType entityType = EntityPackets.this.<EntityTracker>tracker(wrapper.user()).entityType(entityId);
               if (entityType == EntityTypes1_19.FALLING_BLOCK) {
                  wrapper.set(Type.VAR_INT, 2, EntityPackets.this.protocol.getMappingData().getNewBlockStateId(wrapper.get(Type.VAR_INT, 2)));
               }
            });
         }
      });
      this.protocol
         .registerClientbound(
            ClientboundPackets1_18.SPAWN_PAINTING,
            ClientboundPackets1_19.SPAWN_ENTITY,
            new PacketHandlers() {
               @Override
               public void register() {
                  this.map(Type.VAR_INT);
                  this.map(Type.UUID);
                  this.handler(
                     wrapper -> {
                        wrapper.write(Type.VAR_INT, EntityTypes1_19.PAINTING.getId());
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
                        List<Metadata> metadata = new ArrayList<>();
                        metadata.add(
                           new Metadata(
                              8,
                              Types1_19.META_TYPES.paintingVariantType,
                              EntityPackets.this.protocol.getMappingData().getPaintingMappings().getNewIdOrDefault(motive, 0)
                           )
                        );
                        metaPacket.write(Types1_19.METADATA_LIST, metadata);
                        metaPacket.send(Protocol1_19To1_18_2.class);
                     }
                  );
               }
            }
         );
      this.protocol.registerClientbound(ClientboundPackets1_18.SPAWN_MOB, ClientboundPackets1_19.SPAWN_ENTITY, new PacketHandlers() {
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
            this.create(Type.VAR_INT, Integer.valueOf(0));
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.handler(EntityPackets.this.trackerHandler());
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_18.ENTITY_EFFECT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.create(Type.BOOLEAN, Boolean.valueOf(false));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_18.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.STRING_ARRAY);
            this.map(Type.NAMED_COMPOUND_TAG);
            this.handler(wrapper -> {
               CompoundTag tag = wrapper.get(Type.NAMED_COMPOUND_TAG, 0);
               tag.put("minecraft:chat_type", EntityPackets.CHAT_REGISTRY.copy());
               ListTag dimensions = tag.<CompoundTag>get("minecraft:dimension_type").get("value");
               Map<String, DimensionData> dimensionDataMap = new HashMap<>(dimensions.size());
               Map<CompoundTag, String> dimensionsMap = new HashMap<>(dimensions.size());

               for (Tag dimension : dimensions) {
                  CompoundTag dimensionCompound = (CompoundTag)dimension;
                  CompoundTag element = dimensionCompound.get("element");
                  String name = (String)dimensionCompound.<Tag>get("name").getValue();
                  EntityPackets.addMonsterSpawnData(element);
                  dimensionDataMap.put(name, new DimensionDataImpl(element));
                  dimensionsMap.put(element.copy(), name);
               }

               EntityPackets.this.<EntityTracker>tracker(wrapper.user()).setDimensions(dimensionDataMap);
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
      this.protocol.registerClientbound(ClientboundPackets1_18.RESPAWN, new PacketHandlers() {
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
      this.protocol.registerClientbound(ClientboundPackets1_18.PLAYER_INFO, wrapper -> {
         int action = wrapper.passthrough(Type.VAR_INT);
         int entries = wrapper.passthrough(Type.VAR_INT);

         for (int i = 0; i < entries; i++) {
            wrapper.passthrough(Type.UUID);
            if (action != 0) {
               if (action == 1 || action == 2) {
                  wrapper.passthrough(Type.VAR_INT);
               } else if (action == 3) {
                  JsonElement displayName = wrapper.read(Type.OPTIONAL_COMPONENT);
                  if (!Protocol1_19To1_18_2.isTextComponentNull(displayName)) {
                     wrapper.write(Type.OPTIONAL_COMPONENT, displayName);
                  } else {
                     wrapper.write(Type.OPTIONAL_COMPONENT, null);
                  }
               }
            } else {
               wrapper.passthrough(Type.STRING);
               int properties = wrapper.passthrough(Type.VAR_INT);

               for (int j = 0; j < properties; j++) {
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
            }
         }
      });
   }

   private static void writeDimensionKey(PacketWrapper wrapper, DimensionRegistryStorage registryStorage) throws Exception {
      CompoundTag currentDimension = wrapper.read(Type.NAMED_COMPOUND_TAG);
      addMonsterSpawnData(currentDimension);
      String dimensionKey = registryStorage.dimensionKey(currentDimension);
      if (dimensionKey == null) {
         if (!Via.getConfig().isSuppressConversionWarnings()) {
            Via.getPlatform()
               .getLogger()
               .warning(
                  "The server tried to send dimension data from a dimension the client wasn't told about on join. Plugins and mods have to make sure they are not creating new dimension types while players are online, and proxies need to make sure they don't scramble dimension data. Received dimension: "
                     + currentDimension
                     + ". Known dimensions: "
                     + registryStorage.dimensions()
               );
         }

         dimensionKey = (String)registryStorage.dimensions()
            .entrySet()
            .stream()
            .map(it -> new Pair<>(it, Maps.difference(currentDimension.getValue(), it.getKey().getValue()).entriesInCommon()))
            .filter(it -> it.value().containsKey("min_y") && it.value().containsKey("height"))
            .max(Comparator.comparingInt(it -> it.value().size()))
            .orElseThrow(() -> new IllegalArgumentException("Dimension not found in registry data from join packet: " + currentDimension))
            .key()
            .getValue();
      }

      wrapper.write(Type.STRING, dimensionKey);
   }

   private static int to3dId(int id) {
      switch (id) {
         case -1:
            return 1;
         case 0:
            return 3;
         case 1:
            return 4;
         case 2:
            return 2;
         case 3:
            return 5;
         default:
            throw new IllegalArgumentException("Unknown 2d id: " + id);
      }
   }

   private static void addMonsterSpawnData(CompoundTag dimension) {
      dimension.put("monster_spawn_block_light_limit", new IntTag(0));
      dimension.put("monster_spawn_light_level", new IntTag(11));
   }

   @Override
   protected void registerRewrites() {
      this.filter().handler((event, meta) -> {
         meta.setMetaType(Types1_19.META_TYPES.byId(meta.metaType().typeId()));
         MetaType type = meta.metaType();
         if (type == Types1_19.META_TYPES.particleType) {
            Particle particle = (Particle)meta.getValue();
            ParticleMappings particleMappings = this.protocol.getMappingData().getParticleMappings();
            if (particle.getId() == particleMappings.id("vibration")) {
               particle.getArguments().remove(0);
               String resourceLocation = Key.stripMinecraftNamespace(particle.<String>getArgument(0).getValue());
               if (resourceLocation.equals("entity")) {
                  particle.getArguments().add(2, new Particle.ParticleData<>(Type.FLOAT, 0.0F));
               }
            }

            this.rewriteParticle(particle);
         }
      });
      this.registerMetaTypeHandler(Types1_19.META_TYPES.itemType, Types1_19.META_TYPES.blockStateType, null, null);
      this.filter().filterFamily(EntityTypes1_19.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
         int data = (Integer)meta.getValue();
         meta.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
      });
      this.filter().type(EntityTypes1_19.CAT).index(19).handler((event, meta) -> meta.setMetaType(Types1_19.META_TYPES.catVariantType));
   }

   @Override
   public void onMappingDataLoaded() {
      this.mapTypes();
   }

   @Override
   public EntityType typeFromId(int type) {
      return EntityTypes1_19.getTypeFromId(type);
   }
}
