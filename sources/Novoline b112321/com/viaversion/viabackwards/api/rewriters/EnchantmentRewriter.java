package com.viaversion.viabackwards.api.rewriters;

import com.github.steveice10.opennbt.tag.builtin.ByteTag;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import com.github.steveice10.opennbt.tag.builtin.ShortTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.aMz;
import net.aqu;

public class EnchantmentRewriter {
   private final Map enchantmentMappings;
   private final String a;
   private final boolean jsonFormat;

   public EnchantmentRewriter(String var1, boolean var2) {
      this.enchantmentMappings = new HashMap();
      this.a = var1;
      this.jsonFormat = var2;
   }

   public EnchantmentRewriter(String var1) {
      this(var1, true);
   }

   public void registerEnchantment(String var1, String var2) {
      this.enchantmentMappings.put(var1, var2);
   }

   public void a(aMz var1) {
      aqu.d();
      CompoundTag var3 = var1.d();
      if(var3 != null) {
         if(var3.get("Enchantments") instanceof ListTag) {
            this.b(var3, false);
         }

         if(var3.get("StoredEnchantments") instanceof ListTag) {
            this.b(var3, true);
         }

      }
   }

   public void b(aMz var1) {
      aqu.d();
      CompoundTag var3 = var1.d();
      if(var3 != null) {
         if(var3.contains(this.a + "|Enchantments")) {
            this.a(var3, false);
         }

         if(var3.contains(this.a + "|StoredEnchantments")) {
            this.a(var3, true);
         }

      }
   }

   public void b(CompoundTag var1, boolean var2) {
      int var3 = aqu.e();
      String var4 = "StoredEnchantments";
      ListTag var5 = (ListTag)var1.get(var4);
      ListTag var6 = new ListTag(this.a + "|" + var4, CompoundTag.class);
      ArrayList var7 = new ArrayList();
      Iterator var8 = var5.clone().iterator();
      if(var8.hasNext()) {
         Tag var9 = (Tag)var8.next();
         String var10 = (String)((CompoundTag)var9).get("id").getValue();
         String var11 = (String)this.enchantmentMappings.get(var10);
         if(var11 != null) {
            var5.remove(var9);
            Number var12 = (Number)((CompoundTag)var9).get("lvl").getValue();
            String var13 = var11 + " " + getRomanNumber(var12.intValue());
            if(this.jsonFormat) {
               var13 = ChatRewriter.a(var13).toString();
            }

            var7.add(new StringTag("", var13));
            var6.add(var9);
         }
      }

      if(!var7.isEmpty()) {
         if(!var2 && var5.size() == 0) {
            CompoundTag var14 = new CompoundTag("");
            var14.put(new StringTag("id", ""));
            var14.put(new ShortTag("lvl", (short)0));
            var5.add(var14);
            var1.put(new ByteTag(this.a + "|dummyEnchant"));
         }

         var1.put(var6);
         CompoundTag var15 = (CompoundTag)var1.get("display");
         if(var15 == null) {
            var1.put(var15 = new CompoundTag("display"));
         }

         ListTag var16 = (ListTag)var15.get("Lore");
         if(var16 == null) {
            var15.put(var16 = new ListTag("Lore", StringTag.class));
         }

         var7.addAll(var16.getValue());
         var16.setValue(var7);
      }

   }

   public void a(CompoundTag var1, boolean var2) {
      int var3 = aqu.e();
      String var4 = "StoredEnchantments";
      ListTag var5 = (ListTag)var1.get(this.a + "|" + var4);
      ListTag var6 = (ListTag)var1.get(var4);
      if(var6 == null) {
         var6 = new ListTag(var4, CompoundTag.class);
      }

      if(var1.remove(this.a + "|dummyEnchant") != null) {
         Iterator var7 = var6.clone().iterator();
         if(var7.hasNext()) {
            Tag var8 = (Tag)var7.next();
            String var9 = (String)((CompoundTag)var8).get("id").getValue();
            if(var9.isEmpty()) {
               var6.remove(var8);
            }
         }
      }

      CompoundTag var11 = (CompoundTag)var1.get("display");
      ListTag var12 = var11 != null?(ListTag)var11.get("Lore"):null;
      Iterator var13 = var5.clone().iterator();
      if(var13.hasNext()) {
         Tag var10 = (Tag)var13.next();
         var6.add(var10);
         if(var12 != null && var12.size() != 0) {
            var12.remove(var12.get(0));
         }
      }

      if(var12 != null && var12.size() == 0) {
         var11.remove("Lore");
         if(var11.isEmpty()) {
            var1.remove("display");
         }
      }

      var1.put(var6);
      var1.remove(var5.getName());
   }

   public static String getRomanNumber(int var0) {
      int var1 = aqu.d();
      switch(var0) {
      case 1:
         return "I";
      case 2:
         return "II";
      case 3:
         return "III";
      case 4:
         return "IV";
      case 5:
         return "V";
      case 6:
         return "VI";
      case 7:
         return "VII";
      case 8:
         return "VIII";
      case 9:
         return "IX";
      case 10:
         return "X";
      default:
         return Integer.toString(var0);
      }
   }
}
