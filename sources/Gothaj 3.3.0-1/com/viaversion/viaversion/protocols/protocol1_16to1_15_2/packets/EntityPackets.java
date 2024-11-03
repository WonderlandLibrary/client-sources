package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.WorldIdentifiers;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.metadata.MetadataRewriter1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.storage.InventoryTracker1_16;
import com.viaversion.viaversion.util.Key;
import java.util.Arrays;
import java.util.UUID;

public class EntityPackets {
   private static final PacketHandler DIMENSION_HANDLER = wrapper -> {
      WorldIdentifiers map = Via.getConfig().get1_16WorldNamesMap();
      WorldIdentifiers userMap = wrapper.user().get(WorldIdentifiers.class);
      if (userMap != null) {
         map = userMap;
      }

      int dimension = wrapper.read(Type.INT);
      String dimensionName;
      String outputName;
      switch (dimension) {
         case -1:
            dimensionName = "minecraft:the_nether";
            outputName = map.nether();
            break;
         case 0:
            dimensionName = "minecraft:overworld";
            outputName = map.overworld();
            break;
         case 1:
            dimensionName = "minecraft:the_end";
            outputName = map.end();
            break;
         default:
            Via.getPlatform().getLogger().warning("Invalid dimension id: " + dimension);
            dimensionName = "minecraft:overworld";
            outputName = map.overworld();
      }

      wrapper.write(Type.STRING, dimensionName);
      wrapper.write(Type.STRING, outputName);
   };
   public static final CompoundTag DIMENSIONS_TAG = new CompoundTag();
   private static final String[] WORLD_NAMES = new String[]{"minecraft:overworld", "minecraft:the_nether", "minecraft:the_end"};

   private static CompoundTag createOverworldEntry() {
      CompoundTag tag = new CompoundTag();
      tag.put("name", new StringTag("minecraft:overworld"));
      tag.put("has_ceiling", new ByteTag((byte)0));
      addSharedOverwaldEntries(tag);
      return tag;
   }

   private static CompoundTag createOverworldCavesEntry() {
      CompoundTag tag = new CompoundTag();
      tag.put("name", new StringTag("minecraft:overworld_caves"));
      tag.put("has_ceiling", new ByteTag((byte)1));
      addSharedOverwaldEntries(tag);
      return tag;
   }

   private static void addSharedOverwaldEntries(CompoundTag tag) {
      tag.put("piglin_safe", new ByteTag((byte)0));
      tag.put("natural", new ByteTag((byte)1));
      tag.put("ambient_light", new FloatTag(0.0F));
      tag.put("infiniburn", new StringTag("minecraft:infiniburn_overworld"));
      tag.put("respawn_anchor_works", new ByteTag((byte)0));
      tag.put("has_skylight", new ByteTag((byte)1));
      tag.put("bed_works", new ByteTag((byte)1));
      tag.put("has_raids", new ByteTag((byte)1));
      tag.put("logical_height", new IntTag(256));
      tag.put("shrunk", new ByteTag((byte)0));
      tag.put("ultrawarm", new ByteTag((byte)0));
   }

   private static CompoundTag createNetherEntry() {
      CompoundTag tag = new CompoundTag();
      tag.put("piglin_safe", new ByteTag((byte)1));
      tag.put("natural", new ByteTag((byte)0));
      tag.put("ambient_light", new FloatTag(0.1F));
      tag.put("infiniburn", new StringTag("minecraft:infiniburn_nether"));
      tag.put("respawn_anchor_works", new ByteTag((byte)1));
      tag.put("has_skylight", new ByteTag((byte)0));
      tag.put("bed_works", new ByteTag((byte)0));
      tag.put("fixed_time", new LongTag(18000L));
      tag.put("has_raids", new ByteTag((byte)0));
      tag.put("name", new StringTag("minecraft:the_nether"));
      tag.put("logical_height", new IntTag(128));
      tag.put("shrunk", new ByteTag((byte)1));
      tag.put("ultrawarm", new ByteTag((byte)1));
      tag.put("has_ceiling", new ByteTag((byte)1));
      return tag;
   }

   private static CompoundTag createEndEntry() {
      CompoundTag tag = new CompoundTag();
      tag.put("piglin_safe", new ByteTag((byte)0));
      tag.put("natural", new ByteTag((byte)0));
      tag.put("ambient_light", new FloatTag(0.0F));
      tag.put("infiniburn", new StringTag("minecraft:infiniburn_end"));
      tag.put("respawn_anchor_works", new ByteTag((byte)0));
      tag.put("has_skylight", new ByteTag((byte)0));
      tag.put("bed_works", new ByteTag((byte)0));
      tag.put("fixed_time", new LongTag(6000L));
      tag.put("has_raids", new ByteTag((byte)1));
      tag.put("name", new StringTag("minecraft:the_end"));
      tag.put("logical_height", new IntTag(256));
      tag.put("shrunk", new ByteTag((byte)0));
      tag.put("ultrawarm", new ByteTag((byte)0));
      tag.put("has_ceiling", new ByteTag((byte)0));
      return tag;
   }

   public static void register(Protocol1_16To1_15_2 protocol) {
      MetadataRewriter1_16To1_15_2 metadataRewriter = protocol.get(MetadataRewriter1_16To1_15_2.class);
      protocol.registerClientbound(ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY, ClientboundPackets1_16.SPAWN_ENTITY, wrapper -> {
         int entityId = wrapper.passthrough(Type.VAR_INT);
         byte type = wrapper.read(Type.BYTE);
         if (type != 1) {
            wrapper.cancel();
         } else {
            wrapper.user().<EntityTracker>getEntityTracker(Protocol1_16To1_15_2.class).addEntity(entityId, EntityTypes1_16.LIGHTNING_BOLT);
            wrapper.write(Type.UUID, UUID.randomUUID());
            wrapper.write(Type.VAR_INT, EntityTypes1_16.LIGHTNING_BOLT.getId());
            wrapper.passthrough(Type.DOUBLE);
            wrapper.passthrough(Type.DOUBLE);
            wrapper.passthrough(Type.DOUBLE);
            wrapper.write(Type.BYTE, (byte)0);
            wrapper.write(Type.BYTE, (byte)0);
            wrapper.write(Type.INT, 0);
            wrapper.write(Type.SHORT, (short)0);
            wrapper.write(Type.SHORT, (short)0);
            wrapper.write(Type.SHORT, (short)0);
         }
      });
      metadataRewriter.registerTrackerWithData(ClientboundPackets1_15.SPAWN_ENTITY, EntityTypes1_16.FALLING_BLOCK);
      metadataRewriter.registerTracker(ClientboundPackets1_15.SPAWN_MOB);
      metadataRewriter.registerTracker(ClientboundPackets1_15.SPAWN_PLAYER, EntityTypes1_16.PLAYER);
      metadataRewriter.registerMetadataRewriter(ClientboundPackets1_15.ENTITY_METADATA, Types1_14.METADATA_LIST, Types1_16.METADATA_LIST);
      metadataRewriter.registerRemoveEntities(ClientboundPackets1_15.DESTROY_ENTITIES);
      protocol.registerClientbound(ClientboundPackets1_15.RESPAWN, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(EntityPackets.DIMENSION_HANDLER);
            this.map(Type.LONG);
            this.map(Type.UNSIGNED_BYTE);
            this.handler(wrapper -> {
               wrapper.write(Type.BYTE, (byte)-1);
               String levelType = wrapper.read(Type.STRING);
               wrapper.write(Type.BOOLEAN, false);
               wrapper.write(Type.BOOLEAN, levelType.equals("flat"));
               wrapper.write(Type.BOOLEAN, true);
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_15.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.UNSIGNED_BYTE);
            this.handler(wrapper -> {
               wrapper.write(Type.BYTE, (byte)-1);
               wrapper.write(Type.STRING_ARRAY, Arrays.copyOf(EntityPackets.WORLD_NAMES, EntityPackets.WORLD_NAMES.length));
               wrapper.write(Type.NAMED_COMPOUND_TAG, EntityPackets.DIMENSIONS_TAG.copy());
            });
            this.handler(EntityPackets.DIMENSION_HANDLER);
            this.map(Type.LONG);
            this.map(Type.UNSIGNED_BYTE);
            this.handler(wrapper -> {
               wrapper.user().<EntityTracker>getEntityTracker(Protocol1_16To1_15_2.class).addEntity(wrapper.get(Type.INT, 0), EntityTypes1_16.PLAYER);
               String type = wrapper.read(Type.STRING);
               wrapper.passthrough(Type.VAR_INT);
               wrapper.passthrough(Type.BOOLEAN);
               wrapper.passthrough(Type.BOOLEAN);
               wrapper.write(Type.BOOLEAN, false);
               wrapper.write(Type.BOOLEAN, type.equals("flat"));
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_15.ENTITY_PROPERTIES, wrapper -> {
         wrapper.passthrough(Type.VAR_INT);
         int size = wrapper.passthrough(Type.INT);
         int actualSize = size;

         for (int i = 0; i < size; i++) {
            String key = wrapper.read(Type.STRING);
            String attributeIdentifier = (String)protocol.getMappingData().getAttributeMappings().get(key);
            if (attributeIdentifier == null) {
               attributeIdentifier = Key.namespaced(key);
               if (!Key.isValid(attributeIdentifier)) {
                  if (!Via.getConfig().isSuppressConversionWarnings()) {
                     Via.getPlatform().getLogger().warning("Invalid attribute: " + key);
                  }

                  actualSize--;
                  wrapper.read(Type.DOUBLE);
                  int modifierSize = wrapper.read(Type.VAR_INT);

                  for (int j = 0; j < modifierSize; j++) {
                     wrapper.read(Type.UUID);
                     wrapper.read(Type.DOUBLE);
                     wrapper.read(Type.BYTE);
                  }
                  continue;
               }
            }

            wrapper.write(Type.STRING, attributeIdentifier);
            wrapper.passthrough(Type.DOUBLE);
            int modifierSize = wrapper.passthrough(Type.VAR_INT);

            for (int j = 0; j < modifierSize; j++) {
               wrapper.passthrough(Type.UUID);
               wrapper.passthrough(Type.DOUBLE);
               wrapper.passthrough(Type.BYTE);
            }
         }

         if (size != actualSize) {
            wrapper.set(Type.INT, 0, actualSize);
         }
      });
      protocol.registerServerbound(ServerboundPackets1_16.ANIMATION, wrapper -> {
         InventoryTracker1_16 inventoryTracker = wrapper.user().get(InventoryTracker1_16.class);
         if (inventoryTracker.isInventoryOpen()) {
            wrapper.cancel();
         }
      });
   }

   static {
      ListTag list = new ListTag(CompoundTag.class);
      list.add(createOverworldEntry());
      list.add(createOverworldCavesEntry());
      list.add(createNetherEntry());
      list.add(createEndEntry());
      DIMENSIONS_TAG.put("dimension", list);
   }
}
