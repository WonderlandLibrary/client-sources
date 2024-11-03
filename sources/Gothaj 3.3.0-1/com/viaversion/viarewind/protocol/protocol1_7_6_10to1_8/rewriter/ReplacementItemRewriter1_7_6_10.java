package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.rewriter;

import com.viaversion.viarewind.api.rewriter.item.Replacement;
import com.viaversion.viarewind.api.rewriter.item.ReplacementItemRewriter;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.utils.ChatUtil;
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
import java.util.ArrayList;
import java.util.List;

public class ReplacementItemRewriter1_7_6_10 extends ReplacementItemRewriter<Protocol1_7_6_10To1_8> {
   private static final String VIA_REWIND_TAG_KEY = "ViaRewind1_7_6_10to1_8";

   public ReplacementItemRewriter1_7_6_10(Protocol1_7_6_10To1_8 protocol) {
      super(protocol, ProtocolVersion.v1_8);
   }

   @Override
   public void register() {
      this.registerBlock(176, new Replacement(63));
      this.registerBlock(177, new Replacement(68));
      this.registerBlock(193, new Replacement(64));
      this.registerBlock(194, new Replacement(64));
      this.registerBlock(195, new Replacement(64));
      this.registerBlock(196, new Replacement(64));
      this.registerBlock(197, new Replacement(64));
      this.registerBlock(77, 5, new Replacement(69, 6));
      this.registerBlock(77, 13, new Replacement(69, 14));
      this.registerBlock(77, 0, new Replacement(69, 0));
      this.registerBlock(77, 8, new Replacement(69, 8));
      this.registerBlock(143, 5, new Replacement(69, 6));
      this.registerBlock(143, 13, new Replacement(69, 14));
      this.registerBlock(143, 0, new Replacement(69, 0));
      this.registerBlock(143, 8, new Replacement(69, 8));
      this.registerBlock(178, new Replacement(151));
      this.registerBlock(182, 0, new Replacement(44, 1));
      this.registerBlock(182, 8, new Replacement(44, 9));
      this.registerItem(425, new Replacement(323, "Banner"));
      this.registerItem(409, new Replacement(406, "Prismarine Shard"));
      this.registerItem(410, new Replacement(406, "Prismarine Crystal"));
      this.registerItem(416, new Replacement(280, "Armor Stand"));
      this.registerItem(423, new Replacement(363, "Raw Mutton"));
      this.registerItem(424, new Replacement(364, "Cooked Mutton"));
      this.registerItem(411, new Replacement(365, "Raw Rabbit"));
      this.registerItem(412, new Replacement(366, "Cooked Rabbit"));
      this.registerItem(413, new Replacement(282, "Rabbit Stew"));
      this.registerItem(414, new Replacement(375, "Rabbit's Foot"));
      this.registerItem(415, new Replacement(334, "Rabbit Hide"));
      this.registerItem(373, 8203, new Replacement(373, 0, "Potion of Leaping"));
      this.registerItem(373, 8235, new Replacement(373, 0, "Potion of Leaping"));
      this.registerItem(373, 8267, new Replacement(373, 0, "Potion of Leaping"));
      this.registerItem(373, 16395, new Replacement(373, 0, "Splash Potion of Leaping"));
      this.registerItem(373, 16427, new Replacement(373, 0, "Splash Potion of Leaping"));
      this.registerItem(373, 16459, new Replacement(373, 0, "Splash Potion of Leaping"));
      this.registerItem(383, 30, new Replacement(383, "Spawn ArmorStand"));
      this.registerItem(383, 67, new Replacement(383, "Spawn Endermite"));
      this.registerItem(383, 68, new Replacement(383, "Spawn Guardian"));
      this.registerItem(383, 101, new Replacement(383, "Spawn Rabbit"));
      this.registerItem(19, 1, new Replacement(19, 0, "Wet Sponge"));
      this.registerItem(182, new Replacement(44, 1, "Red Sandstone Slab"));
      this.registerItemBlock(166, new Replacement(20, "Barrier"));
      this.registerItemBlock(167, new Replacement(96, "Iron Trapdoor"));
      this.registerItemBlock(1, 1, new Replacement(1, 0, "Granite"));
      this.registerItemBlock(1, 2, new Replacement(1, 0, "Polished Granite"));
      this.registerItemBlock(1, 3, new Replacement(1, 0, "Diorite"));
      this.registerItemBlock(1, 4, new Replacement(1, 0, "Polished Diorite"));
      this.registerItemBlock(1, 5, new Replacement(1, 0, "Andesite"));
      this.registerItemBlock(1, 6, new Replacement(1, 0, "Polished Andesite"));
      this.registerItemBlock(168, 0, new Replacement(1, 0, "Prismarine"));
      this.registerItemBlock(168, 1, new Replacement(98, 0, "Prismarine Bricks"));
      this.registerItemBlock(168, 2, new Replacement(98, 1, "Dark Prismarine"));
      this.registerItemBlock(169, new Replacement(89, "Sea Lantern"));
      this.registerItemBlock(165, new Replacement(95, 5, "Slime Block"));
      this.registerItemBlock(179, 0, new Replacement(24, "Red Sandstone"));
      this.registerItemBlock(179, 1, new Replacement(24, "Chiseled Red Sandstone"));
      this.registerItemBlock(179, 2, new Replacement(24, "Smooth Sandstone"));
      this.registerItemBlock(181, new Replacement(43, 1, "Double Red Sandstone Slab"));
      this.registerItemBlock(180, new Replacement(128, "Red Sandstone Stairs"));
      this.registerItemBlock(188, new Replacement(85, "Spruce Fence"));
      this.registerItemBlock(189, new Replacement(85, "Birch Fence"));
      this.registerItemBlock(190, new Replacement(85, "Jungle Fence"));
      this.registerItemBlock(191, new Replacement(85, "Dark Oak Fence"));
      this.registerItemBlock(192, new Replacement(85, "Acacia Fence"));
      this.registerItemBlock(183, new Replacement(107, "Spruce Fence Gate"));
      this.registerItemBlock(184, new Replacement(107, "Birch Fence Gate"));
      this.registerItemBlock(185, new Replacement(107, "Jungle Fence Gate"));
      this.registerItemBlock(186, new Replacement(107, "Dark Oak Fence Gate"));
      this.registerItemBlock(187, new Replacement(107, "Acacia Fence Gate"));
      this.registerItemBlock(427, new Replacement(324, "Spruce Door"));
      this.registerItemBlock(428, new Replacement(324, "Birch Door"));
      this.registerItemBlock(429, new Replacement(324, "Jungle Door"));
      this.registerItemBlock(430, new Replacement(324, "Dark Oak Door"));
      this.registerItemBlock(431, new Replacement(324, "Acacia Door"));
      this.registerItemBlock(157, new Replacement(28, "Activator Rail"));
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

         CompoundTag viaRewindTag = new CompoundTag();
         tag.put("ViaRewind1_7_6_10to1_8", viaRewindTag);
         viaRewindTag.put("id", new ShortTag((short)item.identifier()));
         viaRewindTag.put("data", new ShortTag(item.data()));
         CompoundTag display = tag.get("display");
         if (display != null && display.contains("Name")) {
            viaRewindTag.put("displayName", new StringTag((String)display.<Tag>get("Name").getValue()));
         }

         if (display != null && display.contains("Lore")) {
            viaRewindTag.put("lore", new ListTag(display.<ListTag>get("Lore").getValue()));
         }

         if (tag.contains("ench") || tag.contains("StoredEnchantments")) {
            ListTag enchTag = tag.contains("ench") ? tag.get("ench") : tag.get("StoredEnchantments");
            List<Tag> lore = new ArrayList<>();

            for (Tag ench : new ArrayList<>(enchTag.getValue())) {
               short id = ((CompoundTag)ench).<NumberTag>get("id").asShort();
               short lvl = ((CompoundTag)ench).<NumberTag>get("lvl").asShort();
               if (id == 8) {
                  String s = "§r§7Depth Strider ";
                  enchTag.remove(ench);
                  s = s + Enchantments.ENCHANTMENTS.getOrDefault(lvl, "enchantment.level." + lvl);
                  lore.add(new StringTag(s));
               }
            }

            if (!lore.isEmpty()) {
               if (display == null) {
                  tag.put("display", display = new CompoundTag());
                  viaRewindTag.put("noDisplay", new ByteTag());
               }

               ListTag loreTag = display.get("Lore");
               if (loreTag == null) {
                  display.put("Lore", loreTag = new ListTag(StringTag.class));
               }

               lore.addAll(loreTag.getValue());
               loreTag.setValue(lore);
            }
         }

         if (item.identifier() == 387 && tag.contains("pages")) {
            ListTag pages = tag.get("pages");
            ListTag oldPages = new ListTag(StringTag.class);
            viaRewindTag.put("pages", oldPages);

            for (int i = 0; i < pages.size(); i++) {
               StringTag page = pages.get(i);
               String value = page.getValue();
               oldPages.add(new StringTag(value));
               value = ChatUtil.jsonToLegacy(value);
               page.setValue(value);
            }
         }

         this.replace(item);
         if (viaRewindTag.size() == 2
            && (Short)viaRewindTag.<Tag>get("id").getValue() == item.identifier()
            && (Short)viaRewindTag.<Tag>get("data").getValue() == item.data()) {
            item.tag().remove("ViaRewind1_7_6_10to1_8");
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
         if (tag != null && item.tag().contains("ViaRewind1_7_6_10to1_8")) {
            CompoundTag viaVersionTag = tag.remove("ViaRewind1_7_6_10to1_8");
            item.setIdentifier((Short)viaVersionTag.<Tag>get("id").getValue());
            item.setData((Short)viaVersionTag.<Tag>get("data").getValue());
            if (viaVersionTag.contains("noDisplay")) {
               tag.remove("display");
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

            if (item.identifier() == 387) {
               ListTag oldPages = viaVersionTag.get("pages");
               tag.remove("pages");
               tag.put("pages", oldPages);
            }

            return item;
         } else {
            return item;
         }
      }
   }
}
