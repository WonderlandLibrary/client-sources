package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets;

import com.google.common.collect.Sets;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.Protocol1_16_1To1_16_2;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.storage.BiomeStorage;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16_2;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.EntityPackets;
import java.util.Set;

public class EntityPackets1_16_2 extends EntityRewriter<ClientboundPackets1_16_2, Protocol1_16_1To1_16_2> {
   private final Set<String> oldDimensions = Sets.newHashSet(new String[]{"minecraft:overworld", "minecraft:the_nether", "minecraft:the_end"});
   private boolean warned;

   public EntityPackets1_16_2(Protocol1_16_1To1_16_2 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
      this.registerTrackerWithData(ClientboundPackets1_16_2.SPAWN_ENTITY, EntityTypes1_16_2.FALLING_BLOCK);
      this.registerSpawnTracker(ClientboundPackets1_16_2.SPAWN_MOB);
      this.registerTracker(ClientboundPackets1_16_2.SPAWN_EXPERIENCE_ORB, EntityTypes1_16_2.EXPERIENCE_ORB);
      this.registerTracker(ClientboundPackets1_16_2.SPAWN_PAINTING, EntityTypes1_16_2.PAINTING);
      this.registerTracker(ClientboundPackets1_16_2.SPAWN_PLAYER, EntityTypes1_16_2.PLAYER);
      this.registerRemoveEntities(ClientboundPackets1_16_2.DESTROY_ENTITIES);
      this.registerMetadataRewriter(ClientboundPackets1_16_2.ENTITY_METADATA, Types1_16.METADATA_LIST);
      this.protocol.registerClientbound(ClientboundPackets1_16_2.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.handler(wrapper -> {
               boolean hardcore = wrapper.read(Type.BOOLEAN);
               short gamemode = (short)wrapper.read(Type.BYTE).byteValue();
               if (hardcore) {
                  gamemode = (short)(gamemode | 8);
               }

               wrapper.write(Type.UNSIGNED_BYTE, gamemode);
            });
            this.map(Type.BYTE);
            this.map(Type.STRING_ARRAY);
            this.handler(wrapper -> {
               CompoundTag registry = wrapper.read(Type.NAMED_COMPOUND_TAG);
               if (wrapper.user().getProtocolInfo().getProtocolVersion() <= ProtocolVersion.v1_15_2.getVersion()) {
                  CompoundTag biomeRegistry = registry.get("minecraft:worldgen/biome");
                  ListTag biomes = biomeRegistry.get("value");
                  BiomeStorage biomeStorage = wrapper.user().get(BiomeStorage.class);
                  biomeStorage.clear();

                  for (Tag biome : biomes) {
                     CompoundTag biomeCompound = (CompoundTag)biome;
                     StringTag name = biomeCompound.get("name");
                     NumberTag id = biomeCompound.get("id");
                     biomeStorage.addBiome(name.getValue(), id.asInt());
                  }
               } else if (!EntityPackets1_16_2.this.warned) {
                  EntityPackets1_16_2.this.warned = true;
                  ViaBackwards.getPlatform().getLogger().warning("1.16 and 1.16.1 clients are only partially supported and may have wrong biomes displayed.");
               }

               wrapper.write(Type.NAMED_COMPOUND_TAG, EntityPackets.DIMENSIONS_TAG);
               CompoundTag dimensionData = wrapper.read(Type.NAMED_COMPOUND_TAG);
               wrapper.write(Type.STRING, EntityPackets1_16_2.this.getDimensionFromData(dimensionData));
            });
            this.map(Type.STRING);
            this.map(Type.LONG);
            this.handler(wrapper -> {
               int maxPlayers = wrapper.read(Type.VAR_INT);
               wrapper.write(Type.UNSIGNED_BYTE, (short)Math.min(maxPlayers, 255));
            });
            this.handler(EntityPackets1_16_2.this.getTrackerHandler(EntityTypes1_16_2.PLAYER, Type.INT));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16_2.RESPAWN, wrapper -> {
         CompoundTag dimensionData = wrapper.read(Type.NAMED_COMPOUND_TAG);
         wrapper.write(Type.STRING, this.getDimensionFromData(dimensionData));
      });
   }

   private String getDimensionFromData(CompoundTag dimensionData) {
      StringTag effectsLocation = dimensionData.get("effects");
      return effectsLocation != null && this.oldDimensions.contains(effectsLocation.getValue()) ? effectsLocation.getValue() : "minecraft:overworld";
   }

   @Override
   protected void registerRewrites() {
      this.registerMetaTypeHandler(
         Types1_16.META_TYPES.itemType,
         Types1_16.META_TYPES.blockStateType,
         null,
         Types1_16.META_TYPES.particleType,
         Types1_16.META_TYPES.componentType,
         Types1_16.META_TYPES.optionalComponentType
      );
      this.mapTypes(EntityTypes1_16_2.values(), EntityTypes1_16.class);
      this.mapEntityTypeWithData(EntityTypes1_16_2.PIGLIN_BRUTE, EntityTypes1_16_2.PIGLIN).jsonName();
      this.filter().filterFamily(EntityTypes1_16_2.ABSTRACT_PIGLIN).index(15).toIndex(16);
      this.filter().filterFamily(EntityTypes1_16_2.ABSTRACT_PIGLIN).index(16).toIndex(15);
   }

   @Override
   public EntityType typeFromId(int typeId) {
      return EntityTypes1_16_2.getTypeFromId(typeId);
   }
}
