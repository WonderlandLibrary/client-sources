package com.viaversion.viarewind.api.rewriter.item;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.util.ChatColorUtil;

public class Replacement {
   private final int id;
   private final int data;
   private final String name;
   private String resetName;
   private String bracketName;

   public Replacement(int id) {
      this(id, -1);
   }

   public Replacement(int id, int data) {
      this(id, data, null);
   }

   public Replacement(int id, String name) {
      this(id, -1, name);
   }

   public Replacement(int id, int data, String name) {
      this.id = id;
      this.data = data;
      this.name = name;
   }

   public void buildNames(String protocolVersion) {
      if (this.name != null) {
         this.resetName = ChatColorUtil.translateAlternateColorCodes("&r" + protocolVersion + " " + this.name);
         this.bracketName = ChatColorUtil.translateAlternateColorCodes(" &r&7(" + protocolVersion + " " + this.name + "&r&7)");
      }
   }

   public int getId() {
      return this.id;
   }

   public int getData() {
      return this.data;
   }

   public String getName() {
      return this.name;
   }

   public Item replace(Item item) {
      item.setIdentifier(this.id);
      if (this.data != -1) {
         item.setData((short)this.data);
      }

      if (this.name != null) {
         CompoundTag rootTag = item.tag() == null ? new CompoundTag() : item.tag();
         if (!rootTag.contains("display")) {
            rootTag.put("display", new CompoundTag());
         }

         CompoundTag display = rootTag.get("display");
         if (display.contains("Name")) {
            StringTag name = display.get("Name");
            if (!name.getValue().equals(this.resetName) && !name.getValue().endsWith(this.bracketName)) {
               name.setValue(name.getValue() + this.bracketName);
            }
         } else {
            display.put("Name", new StringTag(this.resetName));
         }

         item.setTag(rootTag);
      }

      return item;
   }

   public int replaceData(int data) {
      return this.data == -1 ? data : this.data;
   }
}
