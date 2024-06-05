package com.viaversion.viabackwards.api.rewriters;

import com.github.steveice10.opennbt.tag.builtin.ByteTag;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.IntTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import com.github.steveice10.opennbt.tag.builtin.ShortTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import com.viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.aqu;

public class LegacyEnchantmentRewriter {
   private final Map enchantmentMappings = new HashMap();
   private final String nbtTagName;
   private Set hideLevelForEnchants;

   public LegacyEnchantmentRewriter(String var1) {
      this.nbtTagName = var1;
   }

   public void registerEnchantment(int var1, String var2) {
      this.enchantmentMappings.put(Short.valueOf((short)var1), var2);
   }

   public void a(CompoundTag var1, boolean var2) {
      int var3 = aqu.d();
      String var4 = "StoredEnchantments";
      ListTag var5 = (ListTag)var1.get(var4);
      ListTag var6 = new ListTag(this.nbtTagName + "|" + var4, CompoundTag.class);
      ArrayList var7 = new ArrayList();
      Iterator var8 = var5.clone().iterator();
      if(var8.hasNext()) {
         Tag var9 = (Tag)var8.next();
         Short var10 = (Short)((CompoundTag)var9).get("id").getValue();
         String var11 = (String)this.enchantmentMappings.get(var10);
         if(var11 != null) {
            var5.remove(var9);
            Number var12 = (Number)((CompoundTag)var9).get("lvl").getValue();
            if(this.hideLevelForEnchants != null && this.hideLevelForEnchants.contains(var10)) {
               var7.add(new StringTag("", var11));
            }

            var7.add(new StringTag("", var11 + " " + EnchantmentRewriter.getRomanNumber(var12.shortValue())));
            var6.add(var9);
         }
      }

      if(!var7.isEmpty()) {
         if(!var2 && var5.size() == 0) {
            CompoundTag var13 = new CompoundTag("");
            var13.put(new ShortTag("id", (short)0));
            var13.put(new ShortTag("lvl", (short)0));
            var5.add(var13);
            var1.put(new ByteTag(this.nbtTagName + "|dummyEnchant"));
            IntTag var15 = (IntTag)var1.get("HideFlags");
            if(var15 == null) {
               var15 = new IntTag("HideFlags");
            }

            var1.put(new IntTag(this.nbtTagName + "|oldHideFlags", var15.getValue().intValue()));
            int var17 = var15.getValue().intValue() | 1;
            var15.setValue(var17);
            var1.put(var15);
         }

         var1.put(var6);
         CompoundTag var14 = (CompoundTag)var1.get("display");
         if(var14 == null) {
            var1.put(var14 = new CompoundTag("display"));
         }

         ListTag var16 = (ListTag)var14.get("Lore");
         if(var16 == null) {
            var14.put(var16 = new ListTag("Lore", StringTag.class));
         }

         var7.addAll(var16.getValue());
         var16.setValue(var7);
      }

   }

   public void b(CompoundTag var1, boolean var2) {
      int var3 = aqu.d();
      String var4 = "StoredEnchantments";
      ListTag var5 = (ListTag)var1.get(this.nbtTagName + "|" + var4);
      ListTag var6 = (ListTag)var1.get(var4);
      if(var6 == null) {
         var6 = new ListTag(var4, CompoundTag.class);
      }

      if(var1.remove(this.nbtTagName + "|dummyEnchant") != null) {
         Iterator var7 = var6.clone().iterator();
         if(var7.hasNext()) {
            Tag var8 = (Tag)var7.next();
            Short var9 = (Short)((CompoundTag)var8).get("id").getValue();
            Short var10 = (Short)((CompoundTag)var8).get("lvl").getValue();
            if(var9.shortValue() == 0 && var10.shortValue() == 0) {
               var6.remove(var8);
            }
         }

         IntTag var12 = (IntTag)var1.remove(this.nbtTagName + "|oldHideFlags");
         if(var12 != null) {
            var1.put(new IntTag("HideFlags", var12.getValue().intValue()));
         }

         var1.remove("HideFlags");
      }

      CompoundTag var13 = (CompoundTag)var1.get("display");
      ListTag var14 = var13 != null?(ListTag)var13.get("Lore"):null;
      Iterator var15 = var5.clone().iterator();
      if(var15.hasNext()) {
         Tag var16 = (Tag)var15.next();
         var6.add(var16);
         if(var14 != null && var14.size() != 0) {
            var14.remove(var14.get(0));
         }
      }

      if(var14 != null && var14.size() == 0) {
         var13.remove("Lore");
         if(var13.isEmpty()) {
            var1.remove("display");
         }
      }

      var1.put(var6);
      var1.remove(var5.getName());
      if(PacketRemapper.b() == null) {
         ++var3;
         aqu.b(var3);
      }

   }

   public void setHideLevelForEnchants(int... var1) {
      this.hideLevelForEnchants = new HashSet();
      int var2 = aqu.d();
      int var4 = var1.length;
      int var5 = 0;
      if(var5 < var4) {
         int var6 = var1[var5];
         this.hideLevelForEnchants.add(Short.valueOf((short)var6));
         ++var5;
      }

   }
}
