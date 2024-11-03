package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.google.common.primitives.Ints;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.FlowerPotHandler;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_13;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_3;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.BlockIdData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.SpawnEggRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class BlockItemPackets1_13 extends ItemRewriter<ClientboundPackets1_13, ServerboundPackets1_12_1, Protocol1_12_2To1_13> {
   private final Map<String, String> enchantmentMappings = new HashMap<>();
   private final String extraNbtTag;

   public BlockItemPackets1_13(Protocol1_12_2To1_13 protocol) {
      super(protocol);
      this.extraNbtTag = "VB|" + protocol.getClass().getSimpleName() + "|2";
   }

   public static boolean isDamageable(int id) {
      return id >= 256 && id <= 259
         || id == 261
         || id >= 267 && id <= 279
         || id >= 283 && id <= 286
         || id >= 290 && id <= 294
         || id >= 298 && id <= 317
         || id == 346
         || id == 359
         || id == 398
         || id == 442
         || id == 443;
   }

   @Override
   protected void registerPackets() {
      this.protocol.registerClientbound(ClientboundPackets1_13.COOLDOWN, wrapper -> {
         int itemId = wrapper.read(Type.VAR_INT);
         int oldId = this.protocol.getMappingData().getItemMappings().getNewId(itemId);
         if (oldId == -1) {
            wrapper.cancel();
         } else if (SpawnEggRewriter.getEntityId(oldId).isPresent()) {
            wrapper.write(Type.VAR_INT, 6128);
         } else {
            wrapper.write(Type.VAR_INT, oldId >> 4);
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_13.BLOCK_ACTION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int blockId = wrapper.get(Type.VAR_INT, 0);
               if (blockId == 73) {
                  blockId = 25;
               } else if (blockId == 99) {
                  blockId = 33;
               } else if (blockId == 92) {
                  blockId = 29;
               } else if (blockId == 142) {
                  blockId = 54;
               } else if (blockId == 305) {
                  blockId = 146;
               } else if (blockId == 249) {
                  blockId = 130;
               } else if (blockId == 257) {
                  blockId = 138;
               } else if (blockId == 140) {
                  blockId = 52;
               } else if (blockId == 472) {
                  blockId = 209;
               } else if (blockId >= 483 && blockId <= 498) {
                  blockId = blockId - 483 + 219;
               }

               wrapper.set(Type.VAR_INT, 0, blockId);
            });
         }
      });
      this.protocol
         .registerClientbound(
            ClientboundPackets1_13.BLOCK_ENTITY_DATA,
            new PacketHandlers() {
               @Override
               public void register() {
                  this.map(Type.POSITION1_8);
                  this.map(Type.UNSIGNED_BYTE);
                  this.map(Type.NAMED_COMPOUND_TAG);
                  this.handler(
                     wrapper -> {
                        BackwardsBlockEntityProvider provider = Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
                        if (wrapper.get(Type.UNSIGNED_BYTE, 0) == 5) {
                           wrapper.cancel();
                        }

                        wrapper.set(
                           Type.NAMED_COMPOUND_TAG,
                           0,
                           provider.transform(wrapper.user(), wrapper.get(Type.POSITION1_8, 0), wrapper.get(Type.NAMED_COMPOUND_TAG, 0))
                        );
                     }
                  );
               }
            }
         );
      this.protocol.registerClientbound(ClientboundPackets1_13.UNLOAD_CHUNK, wrapper -> {
         int chunkMinX = wrapper.passthrough(Type.INT) << 4;
         int chunkMinZ = wrapper.passthrough(Type.INT) << 4;
         int chunkMaxX = chunkMinX + 15;
         int chunkMaxZ = chunkMinZ + 15;
         BackwardsBlockStorage blockStorage = wrapper.user().get(BackwardsBlockStorage.class);
         blockStorage.getBlocks().entrySet().removeIf(entry -> {
            Position position = entry.getKey();
            return position.x() >= chunkMinX && position.z() >= chunkMinZ && position.x() <= chunkMaxX && position.z() <= chunkMaxZ;
         });
      });
      this.protocol.registerClientbound(ClientboundPackets1_13.BLOCK_CHANGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8);
            this.handler(wrapper -> {
               int blockState = wrapper.read(Type.VAR_INT);
               Position position = wrapper.get(Type.POSITION1_8, 0);
               BackwardsBlockStorage storage = wrapper.user().get(BackwardsBlockStorage.class);
               storage.checkAndStore(position, blockState);
               wrapper.write(Type.VAR_INT, BlockItemPackets1_13.this.protocol.getMappingData().getNewBlockStateId(blockState));
               BlockItemPackets1_13.flowerPotSpecialTreatment(wrapper.user(), blockState, position);
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_13.MULTI_BLOCK_CHANGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
            this.handler(wrapper -> {
               BackwardsBlockStorage storage = wrapper.user().get(BackwardsBlockStorage.class);

               for (BlockChangeRecord record : wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                  int chunkX = wrapper.get(Type.INT, 0);
                  int chunkZ = wrapper.get(Type.INT, 1);
                  int block = record.getBlockId();
                  Position position = new Position(record.getSectionX() + chunkX * 16, record.getY(), record.getSectionZ() + chunkZ * 16);
                  storage.checkAndStore(position, block);
                  BlockItemPackets1_13.flowerPotSpecialTreatment(wrapper.user(), block, position);
                  record.setBlockId(BlockItemPackets1_13.this.protocol.getMappingData().getNewBlockStateId(block));
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_13.WINDOW_ITEMS, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.ITEM1_13_ARRAY, Type.ITEM1_8_SHORT_ARRAY);
            this.handler(BlockItemPackets1_13.this.itemArrayToClientHandler(Type.ITEM1_8_SHORT_ARRAY));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_13.SET_SLOT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(Type.ITEM1_13, Type.ITEM1_8);
            this.handler(BlockItemPackets1_13.this.itemToClientHandler(Type.ITEM1_8));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_13.CHUNK_DATA, wrapper -> {
         ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
         ChunkType1_9_3 type_old = ChunkType1_9_3.forEnvironment(clientWorld.getEnvironment());
         ChunkType1_13 type = ChunkType1_13.forEnvironment(clientWorld.getEnvironment());
         Chunk chunk = wrapper.read(type);
         BackwardsBlockEntityProvider provider = Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
         BackwardsBlockStorage storage = wrapper.user().get(BackwardsBlockStorage.class);

         for (CompoundTag tag : chunk.getBlockEntities()) {
            Tag idTag = tag.get("id");
            if (idTag != null) {
               String id = (String)idTag.getValue();
               if (provider.isHandled(id)) {
                  int sectionIndex = tag.<NumberTag>get("y").asInt() >> 4;
                  if (sectionIndex >= 0 && sectionIndex <= 15) {
                     ChunkSection section = chunk.getSections()[sectionIndex];
                     int x = tag.<NumberTag>get("x").asInt();
                     int y = tag.<NumberTag>get("y").asInt();
                     int z = tag.<NumberTag>get("z").asInt();
                     Position position = new Position(x, (short)y, z);
                     int block = section.palette(PaletteType.BLOCKS).idAt(x & 15, y & 15, z & 15);
                     storage.checkAndStore(position, block);
                     provider.transform(wrapper.user(), position, tag);
                  }
               }
            }
         }

         for (int i = 0; i < chunk.getSections().length; i++) {
            ChunkSection section = chunk.getSections()[i];
            if (section != null) {
               DataPalette palette = section.palette(PaletteType.BLOCKS);

               for (int y = 0; y < 16; y++) {
                  for (int z = 0; z < 16; z++) {
                     for (int x = 0; x < 16; x++) {
                        int block = palette.idAt(x, y, z);
                        if (FlowerPotHandler.isFlowah(block)) {
                           Position pos = new Position(x + (chunk.getX() << 4), (short)(y + (i << 4)), z + (chunk.getZ() << 4));
                           storage.checkAndStore(pos, block);
                           CompoundTag nbt = provider.transform(wrapper.user(), pos, "minecraft:flower_pot");
                           chunk.getBlockEntities().add(nbt);
                        }
                     }
                  }
               }

               for (int j = 0; j < palette.size(); j++) {
                  int mappedBlockStateId = this.protocol.getMappingData().getNewBlockStateId(palette.idByIndex(j));
                  palette.setIdByIndex(j, mappedBlockStateId);
               }
            }
         }

         if (chunk.isBiomeData()) {
            for (int ix = 0; ix < 256; ix++) {
               int biome = chunk.getBiomeData()[ix];
               int newId = -1;
               switch (biome) {
                  case 40:
                  case 41:
                  case 42:
                  case 43:
                     newId = 9;
                     break;
                  case 44:
                  case 45:
                  case 46:
                     newId = 0;
                     break;
                  case 47:
                  case 48:
                  case 49:
                     newId = 24;
                     break;
                  case 50:
                     newId = 10;
               }

               if (newId != -1) {
                  chunk.getBiomeData()[ix] = newId;
               }
            }
         }

         wrapper.write(type_old, chunk);
      });
      this.protocol.registerClientbound(ClientboundPackets1_13.EFFECT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.POSITION1_8);
            this.map(Type.INT);
            this.handler(wrapper -> {
               int id = wrapper.get(Type.INT, 0);
               int data = wrapper.get(Type.INT, 1);
               if (id == 1010) {
                  wrapper.set(Type.INT, 1, BlockItemPackets1_13.this.protocol.getMappingData().getItemMappings().getNewId(data) >> 4);
               } else if (id == 2001) {
                  data = BlockItemPackets1_13.this.protocol.getMappingData().getNewBlockStateId(data);
                  int blockId = data >> 4;
                  int blockData = data & 15;
                  wrapper.set(Type.INT, 1, blockId & 4095 | blockData << 12);
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_13.MAP_DATA, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.map(Type.BOOLEAN);
            this.handler(wrapper -> {
               int iconCount = wrapper.passthrough(Type.VAR_INT);

               for (int i = 0; i < iconCount; i++) {
                  int type = wrapper.read(Type.VAR_INT);
                  byte x = wrapper.read(Type.BYTE);
                  byte z = wrapper.read(Type.BYTE);
                  byte direction = wrapper.read(Type.BYTE);
                  wrapper.read(Type.OPTIONAL_COMPONENT);
                  if (type > 9) {
                     wrapper.set(Type.VAR_INT, 1, wrapper.get(Type.VAR_INT, 1) - 1);
                  } else {
                     wrapper.write(Type.BYTE, (byte)(type << 4 | direction & 15));
                     wrapper.write(Type.BYTE, x);
                     wrapper.write(Type.BYTE, z);
                  }
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_13.ENTITY_EQUIPMENT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.ITEM1_13, Type.ITEM1_8);
            this.handler(BlockItemPackets1_13.this.itemToClientHandler(Type.ITEM1_8));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_13.WINDOW_PROPERTY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.handler(wrapper -> {
               short property = wrapper.get(Type.SHORT, 0);
               if (property >= 4 && property <= 6) {
                  short oldId = wrapper.get(Type.SHORT, 1);
                  wrapper.set(Type.SHORT, 1, (short)BlockItemPackets1_13.this.protocol.getMappingData().getEnchantmentMappings().getNewId(oldId));
               }
            });
         }
      });
      this.protocol.registerServerbound(ServerboundPackets1_12_1.CREATIVE_INVENTORY_ACTION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.SHORT);
            this.map(Type.ITEM1_8, Type.ITEM1_13);
            this.handler(BlockItemPackets1_13.this.itemToServerHandler(Type.ITEM1_13));
         }
      });
      this.protocol.registerServerbound(ServerboundPackets1_12_1.CLICK_WINDOW, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.VAR_INT);
            this.map(Type.ITEM1_8, Type.ITEM1_13);
            this.handler(BlockItemPackets1_13.this.itemToServerHandler(Type.ITEM1_13));
         }
      });
   }

   @Override
   protected void registerRewrites() {
      this.enchantmentMappings.put("minecraft:loyalty", "§7Loyalty");
      this.enchantmentMappings.put("minecraft:impaling", "§7Impaling");
      this.enchantmentMappings.put("minecraft:riptide", "§7Riptide");
      this.enchantmentMappings.put("minecraft:channeling", "§7Channeling");
   }

   @Override
   public Item handleItemToClient(Item item) {
      if (item == null) {
         return null;
      } else {
         int originalId = item.identifier();
         Integer rawId = null;
         boolean gotRawIdFromTag = false;
         CompoundTag tag = item.tag();
         Tag originalIdTag;
         if (tag != null && (originalIdTag = tag.remove(this.extraNbtTag)) != null) {
            rawId = ((NumberTag)originalIdTag).asInt();
            gotRawIdFromTag = true;
         }

         if (rawId == null) {
            super.handleItemToClient(item);
            if (item.identifier() == -1) {
               if (originalId == 362) {
                  rawId = 15007744;
               } else {
                  if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                     ViaBackwards.getPlatform().getLogger().warning("Failed to get 1.12 item for " + originalId);
                  }

                  rawId = 65536;
               }
            } else {
               if (tag == null) {
                  tag = item.tag();
               }

               rawId = this.itemIdToRaw(item.identifier(), item, tag);
            }
         }

         item.setIdentifier(rawId >> 16);
         item.setData((short)(rawId & 65535));
         if (tag != null) {
            if (isDamageable(item.identifier())) {
               Tag damageTag = tag.remove("Damage");
               if (!gotRawIdFromTag && damageTag instanceof IntTag) {
                  item.setData((short)((Integer)damageTag.getValue()).intValue());
               }
            }

            if (item.identifier() == 358) {
               Tag mapTag = tag.remove("map");
               if (!gotRawIdFromTag && mapTag instanceof IntTag) {
                  item.setData((short)((Integer)mapTag.getValue()).intValue());
               }
            }

            this.invertShieldAndBannerId(item, tag);
            CompoundTag display = tag.get("display");
            if (display != null) {
               StringTag name = display.get("Name");
               if (name != null) {
                  display.put(this.extraNbtTag + "|Name", new StringTag(name.getValue()));
                  name.setValue(this.protocol.jsonToLegacy(name.getValue()));
               }
            }

            this.rewriteEnchantmentsToClient(tag, false);
            this.rewriteEnchantmentsToClient(tag, true);
            this.rewriteCanPlaceToClient(tag, "CanPlaceOn");
            this.rewriteCanPlaceToClient(tag, "CanDestroy");
         }

         return item;
      }
   }

   private int itemIdToRaw(int oldId, Item item, CompoundTag tag) {
      Optional<String> eggEntityId = SpawnEggRewriter.getEntityId(oldId);
      if (eggEntityId.isPresent()) {
         if (tag == null) {
            item.setTag(tag = new CompoundTag());
         }

         if (!tag.contains("EntityTag")) {
            CompoundTag entityTag = new CompoundTag();
            entityTag.put("id", new StringTag(eggEntityId.get()));
            tag.put("EntityTag", entityTag);
         }

         return 25100288;
      } else {
         return oldId >> 4 << 16 | oldId & 15;
      }
   }

   private void rewriteCanPlaceToClient(CompoundTag tag, String tagName) {
      if (tag.get(tagName) instanceof ListTag) {
         ListTag blockTag = tag.get(tagName);
         if (blockTag != null) {
            ListTag newCanPlaceOn = new ListTag(StringTag.class);
            tag.put(this.extraNbtTag + "|" + tagName, blockTag.copy());

            for (Tag oldTag : blockTag) {
               Object value = oldTag.getValue();
               String[] newValues = value instanceof String ? BlockIdData.fallbackReverseMapping.get(Key.stripMinecraftNamespace((String)value)) : null;
               if (newValues != null) {
                  for (String newValue : newValues) {
                     newCanPlaceOn.add(new StringTag(newValue));
                  }
               } else {
                  newCanPlaceOn.add(oldTag);
               }
            }

            tag.put(tagName, newCanPlaceOn);
         }
      }
   }

   private void rewriteEnchantmentsToClient(CompoundTag tag, boolean storedEnch) {
      String key = storedEnch ? "StoredEnchantments" : "Enchantments";
      ListTag enchantments = tag.get(key);
      if (enchantments != null) {
         ListTag noMapped = new ListTag(CompoundTag.class);
         ListTag newEnchantments = new ListTag(CompoundTag.class);
         List<Tag> lore = new ArrayList<>();
         boolean hasValidEnchants = false;

         for (Tag enchantmentEntryTag : enchantments.copy()) {
            CompoundTag enchantmentEntry = (CompoundTag)enchantmentEntryTag;
            Tag idTag = enchantmentEntry.get("id");
            if (idTag instanceof StringTag) {
               String newId = (String)idTag.getValue();
               NumberTag levelTag = enchantmentEntry.get("lvl");
               if (levelTag != null) {
                  int levelValue = levelTag.asInt();
                  short level = levelValue < 32767 ? (short)levelValue : 32767;
                  String mappedEnchantmentId = this.enchantmentMappings.get(newId);
                  if (mappedEnchantmentId != null) {
                     lore.add(new StringTag(mappedEnchantmentId + " " + EnchantmentRewriter.getRomanNumber(level)));
                     noMapped.add(enchantmentEntry);
                  } else if (!newId.isEmpty()) {
                     Short oldId = (Short)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get(Key.stripMinecraftNamespace(newId));
                     if (oldId == null) {
                        if (!newId.startsWith("viaversion:legacy/")) {
                           noMapped.add(enchantmentEntry);
                           if (ViaBackwards.getConfig().addCustomEnchantsToLore()) {
                              String name = newId;
                              int index = newId.indexOf(58) + 1;
                              if (index != 0 && index != newId.length()) {
                                 name = newId.substring(index);
                              }

                              name = "§7" + Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase(Locale.ENGLISH);
                              lore.add(new StringTag(name + " " + EnchantmentRewriter.getRomanNumber(level)));
                           }

                           if (Via.getManager().isDebug()) {
                              ViaBackwards.getPlatform().getLogger().warning("Found unknown enchant: " + newId);
                           }
                           continue;
                        }

                        oldId = Short.valueOf(newId.substring(18));
                     }

                     if (level != 0) {
                        hasValidEnchants = true;
                     }

                     CompoundTag newEntry = new CompoundTag();
                     newEntry.put("id", new ShortTag(oldId));
                     newEntry.put("lvl", new ShortTag(level));
                     newEnchantments.add(newEntry);
                  }
               }
            }
         }

         if (!storedEnch && !hasValidEnchants) {
            IntTag hideFlags = tag.get("HideFlags");
            if (hideFlags == null) {
               hideFlags = new IntTag();
               tag.put(this.extraNbtTag + "|DummyEnchant", new ByteTag());
            } else {
               tag.put(this.extraNbtTag + "|OldHideFlags", new IntTag(hideFlags.asByte()));
            }

            if (newEnchantments.size() == 0) {
               CompoundTag enchEntry = new CompoundTag();
               enchEntry.put("id", new ShortTag((short)0));
               enchEntry.put("lvl", new ShortTag((short)0));
               newEnchantments.add(enchEntry);
            }

            int value = hideFlags.asByte() | 1;
            hideFlags.setValue(value);
            tag.put("HideFlags", hideFlags);
         }

         if (noMapped.size() != 0) {
            tag.put(this.extraNbtTag + "|" + key, noMapped);
            if (!lore.isEmpty()) {
               CompoundTag display = tag.get("display");
               if (display == null) {
                  tag.put("display", display = new CompoundTag());
               }

               ListTag loreTag = display.get("Lore");
               if (loreTag == null) {
                  display.put("Lore", loreTag = new ListTag(StringTag.class));
                  tag.put(this.extraNbtTag + "|DummyLore", new ByteTag());
               } else if (loreTag.size() != 0) {
                  ListTag oldLore = new ListTag(StringTag.class);

                  for (Tag value : loreTag) {
                     oldLore.add(value.copy());
                  }

                  tag.put(this.extraNbtTag + "|OldLore", oldLore);
                  lore.addAll(loreTag.getValue());
               }

               loreTag.setValue(lore);
            }
         }

         tag.remove("Enchantments");
         tag.put(storedEnch ? key : "ench", newEnchantments);
      }
   }

   @Override
   public Item handleItemToServer(Item item) {
      if (item == null) {
         return null;
      } else {
         CompoundTag tag = item.tag();
         int originalId = item.identifier() << 16 | item.data() & '\uffff';
         int rawId = item.identifier() << 4 | item.data() & 15;
         if (isDamageable(item.identifier())) {
            if (tag == null) {
               item.setTag(tag = new CompoundTag());
            }

            tag.put("Damage", new IntTag(item.data()));
         }

         if (item.identifier() == 358) {
            if (tag == null) {
               item.setTag(tag = new CompoundTag());
            }

            tag.put("map", new IntTag(item.data()));
         }

         if (tag != null) {
            this.invertShieldAndBannerId(item, tag);
            Tag display = tag.get("display");
            if (display instanceof CompoundTag) {
               CompoundTag displayTag = (CompoundTag)display;
               StringTag name = displayTag.get("Name");
               if (name != null) {
                  StringTag via = displayTag.remove(this.extraNbtTag + "|Name");
                  name.setValue(via != null ? via.getValue() : ComponentUtil.legacyToJsonString(name.getValue()));
               }
            }

            this.rewriteEnchantmentsToServer(tag, false);
            this.rewriteEnchantmentsToServer(tag, true);
            this.rewriteCanPlaceToServer(tag, "CanPlaceOn");
            this.rewriteCanPlaceToServer(tag, "CanDestroy");
            if (item.identifier() == 383) {
               CompoundTag entityTag = tag.get("EntityTag");
               StringTag identifier;
               if (entityTag != null && (identifier = entityTag.get("id")) != null) {
                  rawId = SpawnEggRewriter.getSpawnEggId(identifier.getValue());
                  if (rawId == -1) {
                     rawId = 25100288;
                  } else {
                     entityTag.remove("id");
                     if (entityTag.isEmpty()) {
                        tag.remove("EntityTag");
                     }
                  }
               } else {
                  rawId = 25100288;
               }
            }

            if (tag.isEmpty()) {
               tag = null;
               item.setTag(null);
            }
         }

         int identifier = item.identifier();
         item.setIdentifier(rawId);
         super.handleItemToServer(item);
         if (item.identifier() != rawId && item.identifier() != -1) {
            return item;
         } else {
            item.setIdentifier(identifier);
            int newId = -1;
            if (this.protocol.getMappingData().getItemMappings().inverse().getNewId(rawId) == -1) {
               if (!isDamageable(item.identifier()) && item.identifier() != 358) {
                  if (tag == null) {
                     item.setTag(tag = new CompoundTag());
                  }

                  tag.put(this.extraNbtTag, new IntTag(originalId));
               }

               if (item.identifier() == 229) {
                  newId = 362;
               } else if (item.identifier() == 31 && item.data() == 0) {
                  rawId = 512;
               } else if (this.protocol.getMappingData().getItemMappings().inverse().getNewId(rawId & -16) != -1) {
                  rawId &= -16;
               } else {
                  if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                     ViaBackwards.getPlatform().getLogger().warning("Failed to get 1.13 item for " + item.identifier());
                  }

                  rawId = 16;
               }
            }

            if (newId == -1) {
               newId = this.protocol.getMappingData().getItemMappings().inverse().getNewId(rawId);
            }

            item.setIdentifier(newId);
            item.setData((short)0);
            return item;
         }
      }
   }

   private void rewriteCanPlaceToServer(CompoundTag tag, String tagName) {
      if (tag.get(tagName) instanceof ListTag) {
         ListTag blockTag = tag.remove(this.extraNbtTag + "|" + tagName);
         if (blockTag != null) {
            tag.put(tagName, blockTag.copy());
         } else if ((blockTag = tag.get(tagName)) != null) {
            ListTag newCanPlaceOn = new ListTag(StringTag.class);

            for (Tag oldTag : blockTag) {
               Object value = oldTag.getValue();
               String oldId = Key.stripMinecraftNamespace(value.toString());
               int key = Ints.tryParse(oldId);
               String numberConverted = BlockIdData.numberIdToString.get(key);
               if (numberConverted != null) {
                  oldId = numberConverted;
               }

               String lowerCaseId = oldId.toLowerCase(Locale.ROOT);
               String[] newValues = BlockIdData.blockIdMapping.get(lowerCaseId);
               if (newValues != null) {
                  for (String newValue : newValues) {
                     newCanPlaceOn.add(new StringTag(newValue));
                  }
               } else {
                  newCanPlaceOn.add(new StringTag(lowerCaseId));
               }
            }

            tag.put(tagName, newCanPlaceOn);
         }
      }
   }

   private void rewriteEnchantmentsToServer(CompoundTag tag, boolean storedEnch) {
      String key = storedEnch ? "StoredEnchantments" : "Enchantments";
      ListTag enchantments = tag.get(storedEnch ? key : "ench");
      if (enchantments != null) {
         ListTag newEnchantments = new ListTag(CompoundTag.class);
         boolean dummyEnchant = false;
         if (!storedEnch) {
            IntTag hideFlags = tag.remove(this.extraNbtTag + "|OldHideFlags");
            if (hideFlags != null) {
               tag.put("HideFlags", new IntTag(hideFlags.asByte()));
               dummyEnchant = true;
            } else if (tag.remove(this.extraNbtTag + "|DummyEnchant") != null) {
               tag.remove("HideFlags");
               dummyEnchant = true;
            }
         }

         for (Tag enchEntry : enchantments) {
            CompoundTag enchantmentEntry = new CompoundTag();
            short oldId = ((CompoundTag)enchEntry).<NumberTag>get("id").asShort();
            short level = ((CompoundTag)enchEntry).<NumberTag>get("lvl").asShort();
            if (!dummyEnchant || oldId != 0 || level != 0) {
               String newId = (String)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get(oldId);
               if (newId == null) {
                  newId = "viaversion:legacy/" + oldId;
               }

               enchantmentEntry.put("id", new StringTag(newId));
               enchantmentEntry.put("lvl", new ShortTag(level));
               newEnchantments.add(enchantmentEntry);
            }
         }

         ListTag noMapped = tag.remove(this.extraNbtTag + "|Enchantments");
         if (noMapped != null) {
            for (Tag value : noMapped) {
               newEnchantments.add(value);
            }
         }

         CompoundTag display = tag.get("display");
         if (display == null) {
            tag.put("display", display = new CompoundTag());
         }

         ListTag oldLore = tag.remove(this.extraNbtTag + "|OldLore");
         if (oldLore != null) {
            ListTag lore = display.get("Lore");
            if (lore == null) {
               tag.put("Lore", lore = new ListTag());
            }

            lore.setValue(oldLore.getValue());
         } else if (tag.remove(this.extraNbtTag + "|DummyLore") != null) {
            display.remove("Lore");
            if (display.isEmpty()) {
               tag.remove("display");
            }
         }

         if (!storedEnch) {
            tag.remove("ench");
         }

         tag.put(key, newEnchantments);
      }
   }

   private void invertShieldAndBannerId(Item item, CompoundTag tag) {
      if (item.identifier() == 442 || item.identifier() == 425) {
         Tag blockEntityTag = tag.get("BlockEntityTag");
         if (blockEntityTag instanceof CompoundTag) {
            CompoundTag blockEntityCompoundTag = (CompoundTag)blockEntityTag;
            Tag base = blockEntityCompoundTag.get("Base");
            if (base instanceof IntTag) {
               IntTag baseTag = (IntTag)base;
               baseTag.setValue(15 - baseTag.asInt());
            }

            Tag patterns = blockEntityCompoundTag.get("Patterns");
            if (patterns instanceof ListTag) {
               for (Tag pattern : (ListTag)patterns) {
                  if (pattern instanceof CompoundTag) {
                     IntTag colorTag = ((CompoundTag)pattern).get("Color");
                     colorTag.setValue(15 - colorTag.asInt());
                  }
               }
            }
         }
      }
   }

   private static void flowerPotSpecialTreatment(UserConnection user, int blockState, Position position) throws Exception {
      if (FlowerPotHandler.isFlowah(blockState)) {
         BackwardsBlockEntityProvider beProvider = Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
         CompoundTag nbt = beProvider.transform(user, position, "minecraft:flower_pot");
         PacketWrapper blockUpdateRemove = PacketWrapper.create(ClientboundPackets1_12_1.BLOCK_CHANGE, user);
         blockUpdateRemove.write(Type.POSITION1_8, position);
         blockUpdateRemove.write(Type.VAR_INT, 0);
         blockUpdateRemove.scheduleSend(Protocol1_12_2To1_13.class);
         PacketWrapper blockCreate = PacketWrapper.create(ClientboundPackets1_12_1.BLOCK_CHANGE, user);
         blockCreate.write(Type.POSITION1_8, position);
         blockCreate.write(Type.VAR_INT, Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(blockState));
         blockCreate.scheduleSend(Protocol1_12_2To1_13.class);
         PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_12_1.BLOCK_ENTITY_DATA, user);
         wrapper.write(Type.POSITION1_8, position);
         wrapper.write(Type.UNSIGNED_BYTE, (short)5);
         wrapper.write(Type.NAMED_COMPOUND_TAG, nbt);
         wrapper.scheduleSend(Protocol1_12_2To1_13.class);
      }
   }
}
