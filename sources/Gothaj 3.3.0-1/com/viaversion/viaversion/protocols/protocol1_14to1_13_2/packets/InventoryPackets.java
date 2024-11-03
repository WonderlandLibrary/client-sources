package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;

import com.google.common.collect.Sets;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Key;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class InventoryPackets extends ItemRewriter<ClientboundPackets1_13, ServerboundPackets1_14, Protocol1_14To1_13_2> {
   private static final String NBT_TAG_NAME = "ViaVersion|" + Protocol1_14To1_13_2.class.getSimpleName();
   private static final Set<String> REMOVED_RECIPE_TYPES = Sets.newHashSet(new String[]{"crafting_special_banneraddpattern", "crafting_special_repairitem"});
   private static final ComponentRewriter<ClientboundPackets1_13> COMPONENT_REWRITER = new ComponentRewriter<ClientboundPackets1_13>(
      null, ComponentRewriter.ReadType.JSON
   ) {
      @Override
      protected void handleTranslate(JsonObject object, String translate) {
         super.handleTranslate(object, translate);
         if (translate.startsWith("block.") && translate.endsWith(".name")) {
            object.addProperty("translate", translate.substring(0, translate.length() - 5));
         }
      }
   };

   public InventoryPackets(Protocol1_14To1_13_2 protocol) {
      super(protocol, Type.ITEM1_13_2, Type.ITEM1_13_2_ARRAY);
   }

   @Override
   public void registerPackets() {
      this.registerSetCooldown(ClientboundPackets1_13.COOLDOWN);
      this.registerAdvancements(ClientboundPackets1_13.ADVANCEMENTS, Type.ITEM1_13_2);
      this.protocol.registerClientbound(ClientboundPackets1_13.OPEN_WINDOW, null, wrapper -> {
         Short windowId = wrapper.read(Type.UNSIGNED_BYTE);
         String type = wrapper.read(Type.STRING);
         JsonElement title = wrapper.read(Type.COMPONENT);
         COMPONENT_REWRITER.processText(title);
         Short slots = wrapper.read(Type.UNSIGNED_BYTE);
         if (type.equals("EntityHorse")) {
            wrapper.setPacketType(ClientboundPackets1_14.OPEN_HORSE_WINDOW);
            int entityId = wrapper.read(Type.INT);
            wrapper.write(Type.UNSIGNED_BYTE, windowId);
            wrapper.write(Type.VAR_INT, slots.intValue());
            wrapper.write(Type.INT, entityId);
         } else {
            wrapper.setPacketType(ClientboundPackets1_14.OPEN_WINDOW);
            wrapper.write(Type.VAR_INT, windowId.intValue());
            int typeId = -1;
            switch (type) {
               case "minecraft:crafting_table":
                  typeId = 11;
                  break;
               case "minecraft:furnace":
                  typeId = 13;
                  break;
               case "minecraft:dropper":
               case "minecraft:dispenser":
                  typeId = 6;
                  break;
               case "minecraft:enchanting_table":
                  typeId = 12;
                  break;
               case "minecraft:brewing_stand":
                  typeId = 10;
                  break;
               case "minecraft:villager":
                  typeId = 18;
                  break;
               case "minecraft:beacon":
                  typeId = 8;
                  break;
               case "minecraft:anvil":
                  typeId = 7;
                  break;
               case "minecraft:hopper":
                  typeId = 15;
                  break;
               case "minecraft:shulker_box":
                  typeId = 19;
                  break;
               case "minecraft:container":
               case "minecraft:chest":
               default:
                  if (slots > 0 && slots <= 54) {
                     typeId = slots / 9 - 1;
                  }
            }

            if (typeId == -1) {
               Via.getPlatform().getLogger().warning("Can't open inventory for 1.14 player! Type: " + type + " Size: " + slots);
            }

            wrapper.write(Type.VAR_INT, typeId);
            wrapper.write(Type.COMPONENT, title);
         }
      });
      this.registerWindowItems(ClientboundPackets1_13.WINDOW_ITEMS, Type.ITEM1_13_2_SHORT_ARRAY);
      this.registerSetSlot(ClientboundPackets1_13.SET_SLOT, Type.ITEM1_13_2);
      this.protocol.registerClientbound(ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(wrapper -> {
               String channel = Key.namespaced(wrapper.get(Type.STRING, 0));
               if (channel.equals("minecraft:trader_list")) {
                  wrapper.setPacketType(ClientboundPackets1_14.TRADE_LIST);
                  wrapper.resetReader();
                  wrapper.read(Type.STRING);
                  int windowId = wrapper.read(Type.INT);
                  EntityTracker1_14 tracker = wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                  tracker.setLatestTradeWindowId(windowId);
                  wrapper.write(Type.VAR_INT, windowId);
                  int size = wrapper.passthrough(Type.UNSIGNED_BYTE);

                  for (int i = 0; i < size; i++) {
                     InventoryPackets.this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13_2));
                     InventoryPackets.this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13_2));
                     boolean secondItem = wrapper.passthrough(Type.BOOLEAN);
                     if (secondItem) {
                        InventoryPackets.this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13_2));
                     }

                     wrapper.passthrough(Type.BOOLEAN);
                     wrapper.passthrough(Type.INT);
                     wrapper.passthrough(Type.INT);
                     wrapper.write(Type.INT, 0);
                     wrapper.write(Type.INT, 0);
                     wrapper.write(Type.FLOAT, 0.0F);
                  }

                  wrapper.write(Type.VAR_INT, 0);
                  wrapper.write(Type.VAR_INT, 0);
                  wrapper.write(Type.BOOLEAN, false);
                  wrapper.clearInputBuffer();
               } else if (channel.equals("minecraft:book_open")) {
                  int hand = wrapper.read(Type.VAR_INT);
                  wrapper.clearPacket();
                  wrapper.setPacketType(ClientboundPackets1_14.OPEN_BOOK);
                  wrapper.write(Type.VAR_INT, hand);
               }
            });
         }
      });
      this.registerEntityEquipment(ClientboundPackets1_13.ENTITY_EQUIPMENT, Type.ITEM1_13_2);
      RecipeRewriter<ClientboundPackets1_13> recipeRewriter = new RecipeRewriter<>(this.protocol);
      this.protocol.registerClientbound(ClientboundPackets1_13.DECLARE_RECIPES, wrapper -> {
         int size = wrapper.passthrough(Type.VAR_INT);
         int deleted = 0;

         for (int i = 0; i < size; i++) {
            String id = wrapper.read(Type.STRING);
            String type = wrapper.read(Type.STRING);
            if (REMOVED_RECIPE_TYPES.contains(type)) {
               deleted++;
            } else {
               wrapper.write(Type.STRING, type);
               wrapper.write(Type.STRING, id);
               recipeRewriter.handleRecipeType(wrapper, type);
            }
         }

         wrapper.set(Type.VAR_INT, 0, size - deleted);
      });
      this.registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW, Type.ITEM1_13_2);
      this.protocol.registerServerbound(ServerboundPackets1_14.SELECT_TRADE, wrapper -> {
         PacketWrapper resyncPacket = wrapper.create(ServerboundPackets1_13.CLICK_WINDOW);
         EntityTracker1_14 tracker = wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
         resyncPacket.write(Type.UNSIGNED_BYTE, (short)tracker.getLatestTradeWindowId());
         resyncPacket.write(Type.SHORT, (short)-999);
         resyncPacket.write(Type.BYTE, (byte)2);
         resyncPacket.write(Type.SHORT, (short)ThreadLocalRandom.current().nextInt());
         resyncPacket.write(Type.VAR_INT, 5);
         CompoundTag tag = new CompoundTag();
         tag.put("force_resync", new DoubleTag(Double.NaN));
         resyncPacket.write(Type.ITEM1_13_2, new DataItem(1, (byte)1, (short)0, tag));
         resyncPacket.scheduleSendToServer(Protocol1_14To1_13_2.class);
      });
      this.registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.ITEM1_13_2);
      this.registerSpawnParticle(ClientboundPackets1_13.SPAWN_PARTICLE, Type.ITEM1_13_2, Type.FLOAT);
   }

   @Override
   public Item handleItemToClient(Item item) {
      if (item == null) {
         return null;
      } else {
         item.setIdentifier(Protocol1_14To1_13_2.MAPPINGS.getNewItemId(item.identifier()));
         if (item.tag() == null) {
            return item;
         } else {
            Tag displayTag = item.tag().get("display");
            if (displayTag instanceof CompoundTag) {
               CompoundTag display = (CompoundTag)displayTag;
               Tag loreTag = display.get("Lore");
               if (loreTag instanceof ListTag) {
                  ListTag lore = (ListTag)loreTag;
                  display.put(NBT_TAG_NAME + "|Lore", new ListTag(lore.copy().getValue()));

                  for (Tag loreEntry : lore) {
                     if (loreEntry instanceof StringTag) {
                        String jsonText = ComponentUtil.legacyToJsonString(((StringTag)loreEntry).getValue(), true);
                        ((StringTag)loreEntry).setValue(jsonText);
                     }
                  }
               }
            }

            return item;
         }
      }
   }

   @Override
   public Item handleItemToServer(Item item) {
      if (item == null) {
         return null;
      } else {
         item.setIdentifier(Protocol1_14To1_13_2.MAPPINGS.getOldItemId(item.identifier()));
         if (item.tag() == null) {
            return item;
         } else {
            Tag displayTag = item.tag().get("display");
            if (displayTag instanceof CompoundTag) {
               CompoundTag display = (CompoundTag)displayTag;
               Tag loreTag = display.get("Lore");
               if (loreTag instanceof ListTag) {
                  ListTag lore = (ListTag)loreTag;
                  ListTag savedLore = display.remove(NBT_TAG_NAME + "|Lore");
                  if (savedLore != null) {
                     display.put("Lore", new ListTag(savedLore.getValue()));
                  } else {
                     for (Tag loreEntry : lore) {
                        if (loreEntry instanceof StringTag) {
                           ((StringTag)loreEntry).setValue(ComponentUtil.jsonToLegacy(((StringTag)loreEntry).getValue()));
                        }
                     }
                  }
               }
            }

            return item;
         }
      }
   }
}
