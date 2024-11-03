package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.BlockIdData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.SoundSource;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.SpawnEggRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Key;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class InventoryPackets extends ItemRewriter<ClientboundPackets1_12_1, ServerboundPackets1_13, Protocol1_13To1_12_2> {
   private static final String NBT_TAG_NAME = "ViaVersion|" + Protocol1_13To1_12_2.class.getSimpleName();

   public InventoryPackets(Protocol1_13To1_12_2 protocol) {
      super(protocol, null, null);
   }

   @Override
   public void registerPackets() {
      this.protocol.registerClientbound(ClientboundPackets1_12_1.SET_SLOT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(Type.ITEM1_8, Type.ITEM1_13);
            this.handler(InventoryPackets.this.itemToClientHandler(Type.ITEM1_13));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_12_1.WINDOW_ITEMS, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.ITEM1_8_SHORT_ARRAY, Type.ITEM1_13_SHORT_ARRAY);
            this.handler(InventoryPackets.this.itemArrayToClientHandler(Type.ITEM1_13_SHORT_ARRAY));
         }
      });
      this.protocol
         .registerClientbound(
            ClientboundPackets1_12_1.WINDOW_PROPERTY,
            new PacketHandlers() {
               @Override
               public void register() {
                  this.map(Type.UNSIGNED_BYTE);
                  this.map(Type.SHORT);
                  this.map(Type.SHORT);
                  this.handler(
                     wrapper -> {
                        short property = wrapper.get(Type.SHORT, 0);
                        if (property >= 4 && property <= 6) {
                           wrapper.set(
                              Type.SHORT,
                              1,
                              (short)InventoryPackets.this.protocol.getMappingData().getEnchantmentMappings().getNewId(wrapper.get(Type.SHORT, 1))
                           );
                        }
                     }
                  );
               }
            }
         );
      this.protocol.registerClientbound(ClientboundPackets1_12_1.PLUGIN_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(wrapper -> {
               String channel = wrapper.get(Type.STRING, 0);
               if (channel.equalsIgnoreCase("MC|StopSound")) {
                  String originalSource = wrapper.read(Type.STRING);
                  String originalSound = wrapper.read(Type.STRING);
                  wrapper.clearPacket();
                  wrapper.setPacketType(ClientboundPackets1_13.STOP_SOUND);
                  byte flags = 0;
                  wrapper.write(Type.BYTE, flags);
                  if (!originalSource.isEmpty()) {
                     flags = (byte)(flags | 1);
                     Optional<SoundSource> finalSource = SoundSource.findBySource(originalSource);
                     if (!finalSource.isPresent()) {
                        if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                           Via.getPlatform().getLogger().info("Could not handle unknown sound source " + originalSource + " falling back to default: master");
                        }

                        finalSource = Optional.of(SoundSource.MASTER);
                     }

                     wrapper.write(Type.VAR_INT, finalSource.get().getId());
                  }

                  if (!originalSound.isEmpty()) {
                     flags = (byte)(flags | 2);
                     wrapper.write(Type.STRING, originalSound);
                  }

                  wrapper.set(Type.BYTE, 0, flags);
               } else {
                  if (channel.equalsIgnoreCase("MC|TrList")) {
                     channel = "minecraft:trader_list";
                     wrapper.passthrough(Type.INT);
                     int size = wrapper.passthrough(Type.UNSIGNED_BYTE);

                     for (int i = 0; i < size; i++) {
                        Item input = wrapper.read(Type.ITEM1_8);
                        InventoryPackets.this.handleItemToClient(input);
                        wrapper.write(Type.ITEM1_13, input);
                        Item output = wrapper.read(Type.ITEM1_8);
                        InventoryPackets.this.handleItemToClient(output);
                        wrapper.write(Type.ITEM1_13, output);
                        boolean secondItem = wrapper.passthrough(Type.BOOLEAN);
                        if (secondItem) {
                           Item second = wrapper.read(Type.ITEM1_8);
                           InventoryPackets.this.handleItemToClient(second);
                           wrapper.write(Type.ITEM1_13, second);
                        }

                        wrapper.passthrough(Type.BOOLEAN);
                        wrapper.passthrough(Type.INT);
                        wrapper.passthrough(Type.INT);
                     }
                  } else {
                     channel = InventoryPackets.getNewPluginChannelId(channel);
                     if (channel == null) {
                        if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                           Via.getPlatform().getLogger().warning("Ignoring outgoing plugin message with channel: " + channel);
                        }

                        wrapper.cancel();
                        return;
                     }

                     if (channel.equals("minecraft:register") || channel.equals("minecraft:unregister")) {
                        String[] channels = new String(wrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                        List<String> rewrittenChannels = new ArrayList<>();

                        for (String s : channels) {
                           String rewritten = InventoryPackets.getNewPluginChannelId(s);
                           if (rewritten != null) {
                              rewrittenChannels.add(rewritten);
                           } else if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                              Via.getPlatform().getLogger().warning("Ignoring plugin channel in outgoing REGISTER: " + s);
                           }
                        }

                        if (rewrittenChannels.isEmpty()) {
                           wrapper.cancel();
                           return;
                        }

                        wrapper.write(Type.REMAINING_BYTES, Joiner.on('\u0000').join(rewrittenChannels).getBytes(StandardCharsets.UTF_8));
                     }
                  }

                  wrapper.set(Type.STRING, 0, channel);
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_12_1.ENTITY_EQUIPMENT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.ITEM1_8, Type.ITEM1_13);
            this.handler(InventoryPackets.this.itemToClientHandler(Type.ITEM1_13));
         }
      });
      this.protocol.registerServerbound(ServerboundPackets1_13.CLICK_WINDOW, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.VAR_INT);
            this.map(Type.ITEM1_13, Type.ITEM1_8);
            this.handler(InventoryPackets.this.itemToServerHandler(Type.ITEM1_8));
         }
      });
      this.protocol.registerServerbound(ServerboundPackets1_13.PLUGIN_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(wrapper -> {
               String channel = wrapper.get(Type.STRING, 0);
               channel = InventoryPackets.getOldPluginChannelId(channel);
               if (channel == null) {
                  if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                     Via.getPlatform().getLogger().warning("Ignoring incoming plugin message with channel: " + channel);
                  }

                  wrapper.cancel();
               } else {
                  if (channel.equals("REGISTER") || channel.equals("UNREGISTER")) {
                     String[] channels = new String(wrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                     List<String> rewrittenChannels = new ArrayList<>();

                     for (String s : channels) {
                        String rewritten = InventoryPackets.getOldPluginChannelId(s);
                        if (rewritten != null) {
                           rewrittenChannels.add(rewritten);
                        } else if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                           Via.getPlatform().getLogger().warning("Ignoring plugin channel in incoming REGISTER: " + s);
                        }
                     }

                     wrapper.write(Type.REMAINING_BYTES, Joiner.on('\u0000').join(rewrittenChannels).getBytes(StandardCharsets.UTF_8));
                  }

                  wrapper.set(Type.STRING, 0, channel);
               }
            });
         }
      });
      this.protocol.registerServerbound(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.SHORT);
            this.map(Type.ITEM1_13, Type.ITEM1_8);
            this.handler(InventoryPackets.this.itemToServerHandler(Type.ITEM1_8));
         }
      });
   }

   @Override
   public Item handleItemToClient(Item item) {
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
            boolean banner = item.identifier() == 425;
            if ((banner || item.identifier() == 442) && tag.get("BlockEntityTag") instanceof CompoundTag) {
               CompoundTag blockEntityTag = tag.get("BlockEntityTag");
               if (blockEntityTag.get("Base") instanceof IntTag) {
                  IntTag base = blockEntityTag.get("Base");
                  if (banner) {
                     rawId = 6800 + base.asInt();
                  }

                  base.setValue(15 - base.asInt());
               }

               if (blockEntityTag.get("Patterns") instanceof ListTag) {
                  for (Tag pattern : (ListTag)blockEntityTag.get("Patterns")) {
                     if (pattern instanceof CompoundTag) {
                        Tag c = ((CompoundTag)pattern).get("Color");
                        if (c instanceof NumberTag) {
                           ((CompoundTag)pattern).put("Color", new IntTag(15 - ((NumberTag)c).asInt()));
                        }
                     }
                  }
               }
            }

            if (tag.get("display") instanceof CompoundTag) {
               CompoundTag display = tag.get("display");
               if (display.get("Name") instanceof StringTag) {
                  StringTag name = display.get("Name");
                  display.put(NBT_TAG_NAME + "|Name", new StringTag(name.getValue()));
                  name.setValue(ComponentUtil.legacyToJsonString(name.getValue(), true));
               }
            }

            if (tag.get("ench") instanceof ListTag) {
               ListTag ench = tag.get("ench");
               ListTag enchantments = new ListTag(CompoundTag.class);

               for (Tag enchEntry : ench) {
                  NumberTag idTag;
                  if (enchEntry instanceof CompoundTag && (idTag = ((CompoundTag)enchEntry).get("id")) != null) {
                     CompoundTag enchantmentEntry = new CompoundTag();
                     short oldId = idTag.asShort();
                     String newId = (String)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get(oldId);
                     if (newId == null) {
                        newId = "viaversion:legacy/" + oldId;
                     }

                     enchantmentEntry.put("id", new StringTag(newId));
                     enchantmentEntry.put("lvl", new ShortTag(((CompoundTag)enchEntry).<NumberTag>get("lvl").asShort()));
                     enchantments.add(enchantmentEntry);
                  }
               }

               tag.remove("ench");
               tag.put("Enchantments", enchantments);
            }

            if (tag.get("StoredEnchantments") instanceof ListTag) {
               ListTag storedEnch = tag.get("StoredEnchantments");
               ListTag newStoredEnch = new ListTag(CompoundTag.class);

               for (Tag enchEntryx : storedEnch) {
                  if (enchEntryx instanceof CompoundTag) {
                     CompoundTag enchantmentEntry = new CompoundTag();
                     short oldId = ((CompoundTag)enchEntryx).<NumberTag>get("id").asShort();
                     String newId = (String)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get(oldId);
                     if (newId == null) {
                        newId = "viaversion:legacy/" + oldId;
                     }

                     enchantmentEntry.put("id", new StringTag(newId));
                     enchantmentEntry.put("lvl", new ShortTag(((CompoundTag)enchEntryx).<NumberTag>get("lvl").asShort()));
                     newStoredEnch.add(enchantmentEntry);
                  }
               }

               tag.remove("StoredEnchantments");
               tag.put("StoredEnchantments", newStoredEnch);
            }

            if (tag.get("CanPlaceOn") instanceof ListTag) {
               ListTag old = tag.get("CanPlaceOn");
               ListTag newCanPlaceOn = new ListTag(StringTag.class);
               tag.put(NBT_TAG_NAME + "|CanPlaceOn", old.copy());

               for (Tag oldTag : old) {
                  Object value = oldTag.getValue();
                  String oldId = Key.stripMinecraftNamespace(value.toString());
                  String numberConverted = BlockIdData.numberIdToString.get(Ints.tryParse(oldId));
                  if (numberConverted != null) {
                     oldId = numberConverted;
                  }

                  String[] newValues = BlockIdData.blockIdMapping.get(oldId.toLowerCase(Locale.ROOT));
                  if (newValues != null) {
                     for (String newValue : newValues) {
                        newCanPlaceOn.add(new StringTag(newValue));
                     }
                  } else {
                     newCanPlaceOn.add(new StringTag(oldId.toLowerCase(Locale.ROOT)));
                  }
               }

               tag.put("CanPlaceOn", newCanPlaceOn);
            }

            if (tag.get("CanDestroy") instanceof ListTag) {
               ListTag old = tag.get("CanDestroy");
               ListTag newCanDestroy = new ListTag(StringTag.class);
               tag.put(NBT_TAG_NAME + "|CanDestroy", old.copy());

               for (Tag oldTag : old) {
                  Object valuex = oldTag.getValue();
                  String oldIdx = Key.stripMinecraftNamespace(valuex.toString());
                  String numberConvertedx = BlockIdData.numberIdToString.get(Ints.tryParse(oldIdx));
                  if (numberConvertedx != null) {
                     oldIdx = numberConvertedx;
                  }

                  String[] newValues = BlockIdData.blockIdMapping.get(oldIdx.toLowerCase(Locale.ROOT));
                  if (newValues != null) {
                     for (String newValue : newValues) {
                        newCanDestroy.add(new StringTag(newValue));
                     }
                  } else {
                     newCanDestroy.add(new StringTag(oldIdx.toLowerCase(Locale.ROOT)));
                  }
               }

               tag.put("CanDestroy", newCanDestroy);
            }

            if (item.identifier() == 383) {
               if (tag.get("EntityTag") instanceof CompoundTag) {
                  CompoundTag entityTag = tag.get("EntityTag");
                  if (entityTag.get("id") instanceof StringTag) {
                     StringTag identifier = entityTag.get("id");
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
               } else {
                  rawId = 25100288;
               }
            }

            if (tag.isEmpty()) {
               tag = null;
               item.setTag(null);
            }
         }

         if (Protocol1_13To1_12_2.MAPPINGS.getItemMappings().getNewId(rawId) == -1) {
            if (!isDamageable(item.identifier()) && item.identifier() != 358) {
               if (tag == null) {
                  item.setTag(tag = new CompoundTag());
               }

               tag.put(NBT_TAG_NAME, new IntTag(originalId));
            }

            if (item.identifier() == 31 && item.data() == 0) {
               rawId = 512;
            } else if (Protocol1_13To1_12_2.MAPPINGS.getItemMappings().getNewId(rawId & -16) != -1) {
               rawId &= -16;
            } else {
               if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                  Via.getPlatform().getLogger().warning("Failed to get 1.13 item for " + item.identifier());
               }

               rawId = 16;
            }
         }

         item.setIdentifier(Protocol1_13To1_12_2.MAPPINGS.getItemMappings().getNewId(rawId));
         item.setData((short)0);
         return item;
      }
   }

   public static String getNewPluginChannelId(String old) {
      switch (old) {
         case "MC|TrList":
            return "minecraft:trader_list";
         case "MC|Brand":
            return "minecraft:brand";
         case "MC|BOpen":
            return "minecraft:book_open";
         case "MC|DebugPath":
            return "minecraft:debug/paths";
         case "MC|DebugNeighborsUpdate":
            return "minecraft:debug/neighbors_update";
         case "REGISTER":
            return "minecraft:register";
         case "UNREGISTER":
            return "minecraft:unregister";
         case "BungeeCord":
            return "bungeecord:main";
         case "bungeecord:main":
            return null;
         default:
            String mappedChannel = (String)Protocol1_13To1_12_2.MAPPINGS.getChannelMappings().get(old);
            return mappedChannel != null ? mappedChannel : MappingData.validateNewChannel(old);
      }
   }

   @Override
   public Item handleItemToServer(Item item) {
      if (item == null) {
         return null;
      } else {
         Integer rawId = null;
         boolean gotRawIdFromTag = false;
         CompoundTag tag = item.tag();
         if (tag != null && tag.get(NBT_TAG_NAME) instanceof IntTag) {
            rawId = tag.<NumberTag>get(NBT_TAG_NAME).asInt();
            tag.remove(NBT_TAG_NAME);
            gotRawIdFromTag = true;
         }

         if (rawId == null) {
            int oldId = Protocol1_13To1_12_2.MAPPINGS.getItemMappings().inverse().getNewId(item.identifier());
            if (oldId != -1) {
               Optional<String> eggEntityId = SpawnEggRewriter.getEntityId(oldId);
               if (eggEntityId.isPresent()) {
                  rawId = 25100288;
                  if (tag == null) {
                     item.setTag(tag = new CompoundTag());
                  }

                  if (!tag.contains("EntityTag")) {
                     CompoundTag entityTag = new CompoundTag();
                     entityTag.put("id", new StringTag(eggEntityId.get()));
                     tag.put("EntityTag", entityTag);
                  }
               } else {
                  rawId = oldId >> 4 << 16 | oldId & 15;
               }
            }
         }

         if (rawId == null) {
            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
               Via.getPlatform().getLogger().warning("Failed to get 1.12 item for " + item.identifier());
            }

            rawId = 65536;
         }

         item.setIdentifier((short)(rawId >> 16));
         item.setData((short)(rawId & 65535));
         if (tag != null) {
            if (isDamageable(item.identifier()) && tag.get("Damage") instanceof IntTag) {
               if (!gotRawIdFromTag) {
                  item.setData((short)((Integer)tag.<Tag>get("Damage").getValue()).intValue());
               }

               tag.remove("Damage");
            }

            if (item.identifier() == 358 && tag.get("map") instanceof IntTag) {
               if (!gotRawIdFromTag) {
                  item.setData((short)((Integer)tag.<Tag>get("map").getValue()).intValue());
               }

               tag.remove("map");
            }

            if ((item.identifier() == 442 || item.identifier() == 425) && tag.get("BlockEntityTag") instanceof CompoundTag) {
               CompoundTag blockEntityTag = tag.get("BlockEntityTag");
               if (blockEntityTag.get("Base") instanceof IntTag) {
                  IntTag base = blockEntityTag.get("Base");
                  base.setValue(15 - base.asInt());
               }

               if (blockEntityTag.get("Patterns") instanceof ListTag) {
                  for (Tag pattern : (ListTag)blockEntityTag.get("Patterns")) {
                     if (pattern instanceof CompoundTag) {
                        IntTag c = ((CompoundTag)pattern).get("Color");
                        c.setValue(15 - c.asInt());
                     }
                  }
               }
            }

            if (tag.get("display") instanceof CompoundTag) {
               CompoundTag display = tag.get("display");
               if (display.get("Name") instanceof StringTag) {
                  StringTag name = display.get("Name");
                  StringTag via = display.remove(NBT_TAG_NAME + "|Name");
                  name.setValue(via != null ? via.getValue() : ComponentUtil.jsonToLegacy(name.getValue()));
               }
            }

            if (tag.get("Enchantments") instanceof ListTag) {
               ListTag enchantments = tag.get("Enchantments");
               ListTag ench = new ListTag(CompoundTag.class);

               for (Tag enchantmentEntry : enchantments) {
                  if (enchantmentEntry instanceof CompoundTag) {
                     CompoundTag enchEntry = new CompoundTag();
                     String newId = (String)((CompoundTag)enchantmentEntry).<Tag>get("id").getValue();
                     Short oldId = (Short)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get(newId);
                     if (oldId == null && newId.startsWith("viaversion:legacy/")) {
                        oldId = Short.valueOf(newId.substring(18));
                     }

                     if (oldId != null) {
                        enchEntry.put("id", new ShortTag(oldId));
                        enchEntry.put("lvl", new ShortTag(((CompoundTag)enchantmentEntry).<NumberTag>get("lvl").asShort()));
                        ench.add(enchEntry);
                     }
                  }
               }

               tag.remove("Enchantments");
               tag.put("ench", ench);
            }

            if (tag.get("StoredEnchantments") instanceof ListTag) {
               ListTag storedEnch = tag.get("StoredEnchantments");
               ListTag newStoredEnch = new ListTag(CompoundTag.class);

               for (Tag enchantmentEntryx : storedEnch) {
                  if (enchantmentEntryx instanceof CompoundTag) {
                     CompoundTag enchEntryx = new CompoundTag();
                     String newIdx = (String)((CompoundTag)enchantmentEntryx).<Tag>get("id").getValue();
                     Short oldIdx = (Short)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get(newIdx);
                     if (oldIdx == null && newIdx.startsWith("viaversion:legacy/")) {
                        oldIdx = Short.valueOf(newIdx.substring(18));
                     }

                     if (oldIdx != null) {
                        enchEntryx.put("id", new ShortTag(oldIdx));
                        enchEntryx.put("lvl", new ShortTag(((CompoundTag)enchantmentEntryx).<NumberTag>get("lvl").asShort()));
                        newStoredEnch.add(enchEntryx);
                     }
                  }
               }

               tag.remove("StoredEnchantments");
               tag.put("StoredEnchantments", newStoredEnch);
            }

            if (tag.get(NBT_TAG_NAME + "|CanPlaceOn") instanceof ListTag) {
               tag.put("CanPlaceOn", tag.remove(NBT_TAG_NAME + "|CanPlaceOn"));
            } else if (tag.get("CanPlaceOn") instanceof ListTag) {
               ListTag old = tag.get("CanPlaceOn");
               ListTag newCanPlaceOn = new ListTag(StringTag.class);

               for (Tag oldTag : old) {
                  Object value = oldTag.getValue();
                  String[] newValues = BlockIdData.fallbackReverseMapping.get(value instanceof String ? Key.stripMinecraftNamespace((String)value) : null);
                  if (newValues != null) {
                     for (String newValue : newValues) {
                        newCanPlaceOn.add(new StringTag(newValue));
                     }
                  } else {
                     newCanPlaceOn.add(oldTag);
                  }
               }

               tag.put("CanPlaceOn", newCanPlaceOn);
            }

            if (tag.get(NBT_TAG_NAME + "|CanDestroy") instanceof ListTag) {
               tag.put("CanDestroy", tag.remove(NBT_TAG_NAME + "|CanDestroy"));
            } else if (tag.get("CanDestroy") instanceof ListTag) {
               ListTag old = tag.get("CanDestroy");
               ListTag newCanDestroy = new ListTag(StringTag.class);

               for (Tag oldTagx : old) {
                  Object value = oldTagx.getValue();
                  String[] newValues = BlockIdData.fallbackReverseMapping.get(value instanceof String ? Key.stripMinecraftNamespace((String)value) : null);
                  if (newValues != null) {
                     for (String newValue : newValues) {
                        newCanDestroy.add(new StringTag(newValue));
                     }
                  } else {
                     newCanDestroy.add(oldTagx);
                  }
               }

               tag.put("CanDestroy", newCanDestroy);
            }
         }

         return item;
      }
   }

   public static String getOldPluginChannelId(String newId) {
      newId = MappingData.validateNewChannel(newId);
      if (newId == null) {
         return null;
      } else {
         switch (newId) {
            case "minecraft:trader_list":
               return "MC|TrList";
            case "minecraft:book_open":
               return "MC|BOpen";
            case "minecraft:debug/paths":
               return "MC|DebugPath";
            case "minecraft:debug/neighbors_update":
               return "MC|DebugNeighborsUpdate";
            case "minecraft:register":
               return "REGISTER";
            case "minecraft:unregister":
               return "UNREGISTER";
            case "minecraft:brand":
               return "MC|Brand";
            case "bungeecord:main":
               return "BungeeCord";
            default:
               String mappedChannel = (String)Protocol1_13To1_12_2.MAPPINGS.getChannelMappings().inverse().get(newId);
               if (mappedChannel != null) {
                  return mappedChannel;
               } else {
                  return newId.length() > 20 ? newId.substring(0, 20) : newId;
               }
         }
      }
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
}
