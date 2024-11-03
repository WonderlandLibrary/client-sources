package com.viaversion.viarewind.protocol.protocol1_8to1_9.rewriter;

import com.viaversion.viarewind.api.rewriter.item.Replacement;
import com.viaversion.viarewind.api.rewriter.item.ReplacementItemRewriter;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.utils.Enchantments;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import java.util.ArrayList;
import java.util.List;

public class ReplacementItemRewriter1_8 extends ReplacementItemRewriter<Protocol1_8To1_9> {
   public static final String VIA_REWIND_TAG_KEY = "ViaRewind1_8to1_9";

   public ReplacementItemRewriter1_8(Protocol1_8To1_9 protocol) {
      super(protocol, ProtocolVersion.v1_9);
   }

   @Override
   public void register() {
      this.registerItem(198, new Replacement(50, 0, "End Rod"));
      this.registerItem(434, new Replacement(391, "Beetroot"));
      this.registerItem(435, new Replacement(361, "Beetroot Seeds"));
      this.registerItem(436, new Replacement(282, "Beetroot Soup"));
      this.registerItem(432, new Replacement(322, "Chorus Fruit"));
      this.registerItem(433, new Replacement(393, "Popped Chorus Fruit"));
      this.registerItem(437, new Replacement(373, "Dragons Breath"));
      this.registerItem(443, new Replacement(299, "Elytra"));
      this.registerItem(426, new Replacement(410, "End Crystal"));
      this.registerItem(442, new Replacement(425, "Shield"));
      this.registerItem(439, new Replacement(262, "Spectral Arrow"));
      this.registerItem(440, new Replacement(262, "Tipped Arrow"));
      this.registerItem(444, new Replacement(333, "Spruce Boat"));
      this.registerItem(445, new Replacement(333, "Birch Boat"));
      this.registerItem(446, new Replacement(333, "Jungle Boat"));
      this.registerItem(447, new Replacement(333, "Acacia Boat"));
      this.registerItem(448, new Replacement(333, "Dark Oak Boat"));
      this.registerItem(204, new Replacement(43, 7, "Purpur Double Slab"));
      this.registerItem(205, new Replacement(44, 7, "Purpur Slab"));
      this.registerBlock(209, new Replacement(119));
      this.registerBlock(198, 0, new Replacement(50, 5));
      this.registerBlock(198, 1, new Replacement(50, 5));
      this.registerBlock(198, 2, new Replacement(50, 4));
      this.registerBlock(198, 3, new Replacement(50, 3));
      this.registerBlock(198, 4, new Replacement(50, 2));
      this.registerBlock(198, 5, new Replacement(50, 1));
      this.registerBlock(204, new Replacement(43, 7));
      this.registerBlock(205, 0, new Replacement(44, 7));
      this.registerBlock(205, 8, new Replacement(44, 15));
      this.registerBlock(207, new Replacement(141));
      this.registerBlock(137, new Replacement(137, 0));
      this.registerItemBlock(199, new Replacement(35, 10, "Chorus Plant"));
      this.registerItemBlock(200, new Replacement(35, 2, "Chorus Flower"));
      this.registerItemBlock(201, new Replacement(155, 0, "Purpur Block"));
      this.registerItemBlock(202, new Replacement(155, 2, "Purpur Pillar"));
      this.registerItemBlock(203, 0, new Replacement(156, 0, "Purpur Stairs"));
      this.registerItemBlock(203, 1, new Replacement(156, 1, "Purpur Stairs"));
      this.registerItemBlock(203, 2, new Replacement(156, 2, "Purpur Stairs"));
      this.registerItemBlock(203, 3, new Replacement(156, 3, "Purpur Stairs"));
      this.registerItemBlock(203, 4, new Replacement(156, 4, "Purpur Stairs"));
      this.registerItemBlock(203, 5, new Replacement(156, 5, "Purpur Stairs"));
      this.registerItemBlock(203, 6, new Replacement(156, 6, "Purpur Stairs"));
      this.registerItemBlock(203, 7, new Replacement(156, 7, "Purpur Stairs"));
      this.registerItemBlock(203, 8, new Replacement(156, 8, "Purpur Stairs"));
      this.registerItemBlock(206, new Replacement(121, 0, "Endstone Bricks"));
      this.registerItemBlock(207, new Replacement(141, "Beetroot Block"));
      this.registerItemBlock(208, new Replacement(2, 0, "Grass Path"));
      this.registerItemBlock(209, new Replacement(90, "End Gateway"));
      this.registerItemBlock(210, new Replacement(137, 0, "Repeating Command Block"));
      this.registerItemBlock(211, new Replacement(137, 0, "Chain Command Block"));
      this.registerItemBlock(212, new Replacement(79, 0, "Frosted Ice"));
      this.registerItemBlock(214, new Replacement(87, 0, "Nether Wart Block"));
      this.registerItemBlock(215, new Replacement(112, 0, "Red Nether Brick"));
      this.registerItemBlock(217, new Replacement(166, 0, "Structure Void"));
      this.registerItemBlock(255, new Replacement(137, 0, "Structure Block"));
      this.registerItemBlock(397, 5, new Replacement(397, 0, "Dragon Head"));
   }

   @Override
   public Item handleItemToClient(Item item) {
      if (item == null) {
         return null;
      } else {
         CompoundTag tag = item.tag();
         if (tag == null) {
            item.setTag(tag = new CompoundTag());
         }

         CompoundTag viaVersionTag = new CompoundTag();
         tag.put("ViaRewind1_8to1_9", viaVersionTag);
         viaVersionTag.put("id", new ShortTag((short)item.identifier()));
         viaVersionTag.put("data", new ShortTag(item.data()));
         CompoundTag display = tag.get("display");
         if (display != null && display.contains("Name")) {
            viaVersionTag.put("displayName", new StringTag((String)display.<Tag>get("Name").getValue()));
         }

         if (display != null && display.contains("Lore")) {
            viaVersionTag.put("lore", new ListTag(display.<ListTag>get("Lore").getValue()));
         }

         if (tag.contains("ench") || tag.contains("StoredEnchantments")) {
            ListTag enchTag = tag.contains("ench") ? tag.get("ench") : tag.get("StoredEnchantments");
            List<Tag> lore = new ArrayList<>();

            for (Tag ench : new ArrayList<>(enchTag.getValue())) {
               short id = ((CompoundTag)ench).<NumberTag>get("id").asShort();
               short lvl = ((CompoundTag)ench).<NumberTag>get("lvl").asShort();
               String s;
               if (id == 70) {
                  s = "§r§7Mending ";
               } else {
                  if (id != 9) {
                     continue;
                  }

                  s = "§r§7Frost Walker ";
               }

               enchTag.remove(ench);
               s = s + Enchantments.ENCHANTMENTS.getOrDefault(lvl, "enchantment.level." + lvl);
               lore.add(new StringTag(s));
            }

            if (!lore.isEmpty()) {
               if (display == null) {
                  tag.put("display", display = new CompoundTag());
                  viaVersionTag.put("noDisplay", new ByteTag());
               }

               ListTag loreTag = display.get("Lore");
               if (loreTag == null) {
                  display.put("Lore", loreTag = new ListTag(StringTag.class));
               }

               lore.addAll(loreTag.getValue());
               loreTag.setValue(lore);
            }
         }

         if (item.data() != 0 && tag.contains("Unbreakable")) {
            ByteTag unbreakable = tag.get("Unbreakable");
            if (unbreakable.asByte() != 0) {
               viaVersionTag.put("Unbreakable", new ByteTag(unbreakable.asByte()));
               tag.remove("Unbreakable");
               if (display == null) {
                  tag.put("display", display = new CompoundTag());
                  viaVersionTag.put("noDisplay", new ByteTag());
               }

               ListTag loreTag = display.get("Lore");
               if (loreTag == null) {
                  display.put("Lore", loreTag = new ListTag(StringTag.class));
               }

               loreTag.add(new StringTag("§9Unbreakable"));
            }
         }

         if (tag.contains("AttributeModifiers")) {
            viaVersionTag.put("AttributeModifiers", tag.<Tag>get("AttributeModifiers").copy());
         }

         if (item.identifier() == 383 && item.data() == 0) {
            int data = 0;
            if (tag.contains("EntityTag")) {
               CompoundTag entityTag = tag.get("EntityTag");
               if (entityTag.contains("id")) {
                  StringTag id = entityTag.get("id");
                  if (ItemRewriter.ENTITY_NAME_TO_ID.containsKey(id.getValue())) {
                     data = ItemRewriter.ENTITY_NAME_TO_ID.get(id.getValue());
                  } else if (display == null) {
                     tag.put("display", display = new CompoundTag());
                     viaVersionTag.put("noDisplay", new ByteTag());
                     display.put("Name", new StringTag("§rSpawn " + id.getValue()));
                  }
               }
            }

            item.setData((short)data);
         }

         this.replace(item);
         if (item.identifier() == 373 || item.identifier() == 438 || item.identifier() == 441) {
            int data = 0;
            if (tag.contains("Potion")) {
               StringTag potion = tag.get("Potion");
               String potionName = potion.getValue().replace("minecraft:", "");
               if (PotionMappings.POTION_NAME_TO_ID.containsKey(potionName)) {
                  data = PotionMappings.POTION_NAME_TO_ID.get(potionName);
               }

               if (item.identifier() == 438) {
                  potionName = potionName + "_splash";
               } else if (item.identifier() == 441) {
                  potionName = potionName + "_lingering";
               }

               if ((display == null || !display.contains("Name")) && PotionMappings.POTION_NAME_INDEX.containsKey(potionName)) {
                  if (display == null) {
                     tag.put("display", display = new CompoundTag());
                     viaVersionTag.put("noDisplay", new ByteTag());
                  }

                  display.put("Name", new StringTag(PotionMappings.POTION_NAME_INDEX.get(potionName)));
               }
            }

            if (item.identifier() == 438 || item.identifier() == 441) {
               item.setIdentifier(373);
               data += 8192;
            }

            item.setData((short)data);
         }

         if (tag.contains("AttributeModifiers")) {
            ListTag attributes = tag.get("AttributeModifiers");

            for (int i = 0; i < attributes.size(); i++) {
               CompoundTag attribute = attributes.get(i);
               String name = (String)attribute.<Tag>get("AttributeName").getValue();
               if (!Protocol1_8To1_9.VALID_ATTRIBUTES.contains(name)) {
                  attributes.remove(attribute);
                  i--;
               }
            }
         }

         if (viaVersionTag.size() == 2
            && (Short)viaVersionTag.<Tag>get("id").getValue() == item.identifier()
            && (Short)viaVersionTag.<Tag>get("data").getValue() == item.data()) {
            item.tag().remove("ViaRewind1_8to1_9");
            if (item.tag().isEmpty()) {
               item.setTag(null);
            }
         }

         return item;
      }
   }

   @Override
   public Item handleItemToServer(Item item) {
      if (item == null) {
         return null;
      } else {
         CompoundTag tag = item.tag();
         if (item.identifier() == 383 && item.data() != 0) {
            if (tag == null) {
               item.setTag(tag = new CompoundTag());
            }

            if (!tag.contains("EntityTag") && ItemRewriter.ENTITY_ID_TO_NAME.containsKey(Integer.valueOf(item.data()))) {
               CompoundTag entityTag = new CompoundTag();
               entityTag.put("id", new StringTag(ItemRewriter.ENTITY_ID_TO_NAME.get(Integer.valueOf(item.data()))));
               tag.put("EntityTag", entityTag);
            }

            item.setData((short)0);
         }

         if (item.identifier() == 373 && (tag == null || !tag.contains("Potion"))) {
            if (tag == null) {
               item.setTag(tag = new CompoundTag());
            }

            if (item.data() >= 16384) {
               item.setIdentifier(438);
               item.setData((short)(item.data() - 8192));
            }

            String name = item.data() == 8192 ? "water" : ItemRewriter.potionNameFromDamage(item.data());
            tag.put("Potion", new StringTag("minecraft:" + name));
            item.setData((short)0);
         }

         if (tag != null && item.tag().contains("ViaRewind1_8to1_9")) {
            CompoundTag viaVersionTag = tag.remove("ViaRewind1_8to1_9");
            item.setIdentifier((Short)viaVersionTag.<Tag>get("id").getValue());
            item.setData((Short)viaVersionTag.<Tag>get("data").getValue());
            if (viaVersionTag.contains("noDisplay")) {
               tag.remove("display");
            }

            if (viaVersionTag.contains("Unbreakable")) {
               tag.put("Unbreakable", viaVersionTag.<Tag>get("Unbreakable").copy());
            }

            if (viaVersionTag.contains("displayName")) {
               CompoundTag display = tag.get("display");
               if (display == null) {
                  tag.put("display", display = new CompoundTag());
               }

               StringTag name = display.get("Name");
               if (name == null) {
                  display.put("Name", new StringTag((String)viaVersionTag.<Tag>get("displayName").getValue()));
               } else {
                  name.setValue((String)viaVersionTag.<Tag>get("displayName").getValue());
               }
            } else if (tag.contains("display")) {
               tag.<CompoundTag>get("display").remove("Name");
            }

            if (viaVersionTag.contains("lore")) {
               CompoundTag displayx = tag.get("display");
               if (displayx == null) {
                  tag.put("display", displayx = new CompoundTag());
               }

               ListTag lore = displayx.get("Lore");
               if (lore == null) {
                  displayx.put("Lore", new ListTag((List<Tag>)viaVersionTag.<Tag>get("lore").getValue()));
               } else {
                  lore.setValue((List<Tag>)viaVersionTag.<Tag>get("lore").getValue());
               }
            } else if (tag.contains("display")) {
               tag.<CompoundTag>get("display").remove("Lore");
            }

            tag.remove("AttributeModifiers");
            if (viaVersionTag.contains("AttributeModifiers")) {
               tag.put("AttributeModifiers", viaVersionTag.get("AttributeModifiers"));
            }

            return item;
         } else {
            return item;
         }
      }
   }
}
