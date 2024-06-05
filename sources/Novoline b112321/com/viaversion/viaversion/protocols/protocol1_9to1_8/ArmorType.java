package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import java.util.HashMap;
import java.util.Map;
import net.Cw;

public enum ArmorType {
   LEATHER_HELMET(1, 298, "minecraft:leather_helmet"),
   LEATHER_CHESTPLATE(3, 299, "minecraft:leather_chestplate"),
   LEATHER_LEGGINGS(2, 300, "minecraft:leather_leggings"),
   LEATHER_BOOTS(1, 301, "minecraft:leather_boots"),
   CHAINMAIL_HELMET(2, 302, "minecraft:chainmail_helmet"),
   CHAINMAIL_CHESTPLATE(5, 303, "minecraft:chainmail_chestplate"),
   CHAINMAIL_LEGGINGS(4, 304, "minecraft:chainmail_leggings"),
   CHAINMAIL_BOOTS(1, 305, "minecraft:chainmail_boots"),
   IRON_HELMET(2, 306, "minecraft:iron_helmet"),
   IRON_CHESTPLATE(6, 307, "minecraft:iron_chestplate"),
   IRON_LEGGINGS(5, 308, "minecraft:iron_leggings"),
   IRON_BOOTS(2, 309, "minecraft:iron_boots"),
   DIAMOND_HELMET(3, 310, "minecraft:diamond_helmet"),
   DIAMOND_CHESTPLATE(8, 311, "minecraft:diamond_chestplate"),
   DIAMOND_LEGGINGS(6, 312, "minecraft:diamond_leggings"),
   DIAMOND_BOOTS(3, 313, "minecraft:diamond_boots"),
   GOLD_HELMET(2, 314, "minecraft:gold_helmet"),
   GOLD_CHESTPLATE(5, 315, "minecraft:gold_chestplate"),
   GOLD_LEGGINGS(3, 316, "minecraft:gold_leggings"),
   GOLD_BOOTS(1, 317, "minecraft:gold_boots"),
   NONE(0, 0, "none");

   private static final Map armor = new HashMap();
   private final int armorPoints;
   private final int id;
   private final String type;

   private ArmorType(int var3, int var4, String var5) {
      this.armorPoints = var3;
      this.id = var4;
      this.type = var5;
   }

   public int getArmorPoints() {
      return this.armorPoints;
   }

   public String getType() {
      return this.type;
   }

   public static ArmorType a(int var0) {
      Cw.a();
      ArmorType var2 = (ArmorType)armor.get(Integer.valueOf(var0));
      return var2 == null?NONE:var2;
   }

   public static ArmorType a(String var0) {
      ArmorType[] var2 = values();
      Cw.a();
      int var3 = var2.length;
      int var4 = 0;
      if(var4 < var3) {
         ArmorType var5 = var2[var4];
         if(var5.getType().equals(var0)) {
            return var5;
         }

         ++var4;
      }

      return NONE;
   }

   public static boolean isArmor(int var0) {
      return armor.containsKey(Integer.valueOf(var0));
   }

   public static boolean b(String var0) {
      Cw.b();
      ArmorType[] var2 = values();
      int var3 = var2.length;
      int var4 = 0;
      if(var4 < var3) {
         ArmorType var5 = var2[var4];
         if(var5.getType().equals(var0)) {
            return true;
         }

         ++var4;
      }

      return false;
   }

   public int getId() {
      return this.id;
   }

   static {
      for(ArmorType var11 : values()) {
         armor.put(Integer.valueOf(var11.getId()), var11);
      }

   }
}
