package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.storage.InventoryTracker1_16;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.UUIDUtil;
import java.util.UUID;

public class InventoryPackets extends ItemRewriter<ClientboundPackets1_15, ServerboundPackets1_16, Protocol1_16To1_15_2> {
   public InventoryPackets(Protocol1_16To1_15_2 protocol) {
      super(protocol, Type.ITEM1_13_2, Type.ITEM1_13_2_ARRAY);
   }

   @Override
   public void registerPackets() {
      final PacketHandler cursorRemapper = wrapper -> {
         PacketWrapper clearPacket = wrapper.create(ClientboundPackets1_16.SET_SLOT);
         clearPacket.write(Type.UNSIGNED_BYTE, Short.valueOf((short)-1));
         clearPacket.write(Type.SHORT, Short.valueOf((short)-1));
         clearPacket.write(Type.ITEM1_13_2, null);
         clearPacket.send(Protocol1_16To1_15_2.class);
      };
      this.protocol.registerClientbound(ClientboundPackets1_15.OPEN_WINDOW, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.COMPONENT);
            this.handler(cursorRemapper);
            this.handler(wrapper -> {
               InventoryTracker1_16 inventoryTracker = wrapper.user().get(InventoryTracker1_16.class);
               int windowType = wrapper.get(Type.VAR_INT, 1);
               if (windowType >= 20) {
                  wrapper.set(Type.VAR_INT, 1, ++windowType);
               }

               inventoryTracker.setInventoryOpen(true);
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_15.CLOSE_WINDOW, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(cursorRemapper);
            this.handler(wrapper -> {
               InventoryTracker1_16 inventoryTracker = wrapper.user().get(InventoryTracker1_16.class);
               inventoryTracker.setInventoryOpen(false);
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_15.WINDOW_PROPERTY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.handler(wrapper -> {
               short property = wrapper.get(Type.SHORT, 0);
               if (property >= 4 && property <= 6) {
                  short enchantmentId = wrapper.get(Type.SHORT, 1);
                  if (enchantmentId >= 11) {
                     wrapper.set(Type.SHORT, 1, ++enchantmentId);
                  }
               }
            });
         }
      });
      this.registerSetCooldown(ClientboundPackets1_15.COOLDOWN);
      this.registerWindowItems(ClientboundPackets1_15.WINDOW_ITEMS, Type.ITEM1_13_2_SHORT_ARRAY);
      this.registerTradeList(ClientboundPackets1_15.TRADE_LIST);
      this.registerSetSlot(ClientboundPackets1_15.SET_SLOT, Type.ITEM1_13_2);
      this.registerAdvancements(ClientboundPackets1_15.ADVANCEMENTS, Type.ITEM1_13_2);
      this.protocol.registerClientbound(ClientboundPackets1_15.ENTITY_EQUIPMENT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int slot = wrapper.read(Type.VAR_INT);
               wrapper.write(Type.BYTE, (byte)slot);
               InventoryPackets.this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13_2));
            });
         }
      });
      new RecipeRewriter<>(this.protocol).register(ClientboundPackets1_15.DECLARE_RECIPES);
      this.registerClickWindow(ServerboundPackets1_16.CLICK_WINDOW, Type.ITEM1_13_2);
      this.registerCreativeInvAction(ServerboundPackets1_16.CREATIVE_INVENTORY_ACTION, Type.ITEM1_13_2);
      this.protocol.registerServerbound(ServerboundPackets1_16.CLOSE_WINDOW, wrapper -> {
         InventoryTracker1_16 inventoryTracker = wrapper.user().get(InventoryTracker1_16.class);
         inventoryTracker.setInventoryOpen(false);
      });
      this.protocol.registerServerbound(ServerboundPackets1_16.EDIT_BOOK, wrapper -> this.handleItemToServer(wrapper.passthrough(Type.ITEM1_13_2)));
      this.registerSpawnParticle(ClientboundPackets1_15.SPAWN_PARTICLE, Type.ITEM1_13_2, Type.DOUBLE);
   }

   @Override
   public Item handleItemToClient(Item item) {
      if (item == null) {
         return null;
      } else {
         CompoundTag tag = item.tag();
         if (item.identifier() == 771 && tag != null) {
            Tag ownerTag = tag.get("SkullOwner");
            if (ownerTag instanceof CompoundTag) {
               CompoundTag ownerCompundTag = (CompoundTag)ownerTag;
               Tag idTag = ownerCompundTag.get("Id");
               if (idTag instanceof StringTag) {
                  UUID id = UUID.fromString((String)idTag.getValue());
                  ownerCompundTag.put("Id", new IntArrayTag(UUIDUtil.toIntArray(id)));
               }
            }
         } else if (item.identifier() == 759 && tag != null) {
            Tag pages = tag.get("pages");
            if (pages instanceof ListTag) {
               for (Tag pageTag : (ListTag)pages) {
                  if (pageTag instanceof StringTag) {
                     StringTag page = (StringTag)pageTag;
                     page.setValue(this.protocol.getComponentRewriter().processText(page.getValue()).toString());
                  }
               }
            }
         }

         oldToNewAttributes(item);
         item.setIdentifier(Protocol1_16To1_15_2.MAPPINGS.getNewItemId(item.identifier()));
         return item;
      }
   }

   @Override
   public Item handleItemToServer(Item item) {
      if (item == null) {
         return null;
      } else {
         item.setIdentifier(Protocol1_16To1_15_2.MAPPINGS.getOldItemId(item.identifier()));
         if (item.identifier() == 771 && item.tag() != null) {
            CompoundTag tag = item.tag();
            Tag ownerTag = tag.get("SkullOwner");
            if (ownerTag instanceof CompoundTag) {
               CompoundTag ownerCompundTag = (CompoundTag)ownerTag;
               Tag idTag = ownerCompundTag.get("Id");
               if (idTag instanceof IntArrayTag) {
                  UUID id = UUIDUtil.fromIntArray((int[])idTag.getValue());
                  ownerCompundTag.put("Id", new StringTag(id.toString()));
               }
            }
         }

         newToOldAttributes(item);
         return item;
      }
   }

   public static void oldToNewAttributes(Item item) {
      if (item.tag() != null) {
         ListTag attributes = item.tag().get("AttributeModifiers");
         if (attributes != null) {
            for (Tag tag : attributes) {
               CompoundTag attribute = (CompoundTag)tag;
               rewriteAttributeName(attribute, "AttributeName", false);
               rewriteAttributeName(attribute, "Name", false);
               Tag leastTag = attribute.get("UUIDLeast");
               if (leastTag != null) {
                  Tag mostTag = attribute.get("UUIDMost");
                  int[] uuidIntArray = UUIDUtil.toIntArray(((NumberTag)leastTag).asLong(), ((NumberTag)mostTag).asLong());
                  attribute.put("UUID", new IntArrayTag(uuidIntArray));
               }
            }
         }
      }
   }

   public static void newToOldAttributes(Item item) {
      if (item.tag() != null) {
         ListTag attributes = item.tag().get("AttributeModifiers");
         if (attributes != null) {
            for (Tag tag : attributes) {
               CompoundTag attribute = (CompoundTag)tag;
               rewriteAttributeName(attribute, "AttributeName", true);
               rewriteAttributeName(attribute, "Name", true);
               IntArrayTag uuidTag = attribute.get("UUID");
               if (uuidTag != null && uuidTag.getValue().length == 4) {
                  UUID uuid = UUIDUtil.fromIntArray(uuidTag.getValue());
                  attribute.put("UUIDLeast", new LongTag(uuid.getLeastSignificantBits()));
                  attribute.put("UUIDMost", new LongTag(uuid.getMostSignificantBits()));
               }
            }
         }
      }
   }

   public static void rewriteAttributeName(CompoundTag compoundTag, String entryName, boolean inverse) {
      StringTag attributeNameTag = compoundTag.get(entryName);
      if (attributeNameTag != null) {
         String attributeName = attributeNameTag.getValue();
         if (inverse) {
            attributeName = Key.namespaced(attributeName);
         }

         String mappedAttribute = (String)(inverse
               ? Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings().inverse()
               : Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings())
            .get(attributeName);
         if (mappedAttribute != null) {
            attributeNameTag.setValue(mappedAttribute);
         }
      }
   }
}
