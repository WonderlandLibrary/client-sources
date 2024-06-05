package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.Cw;
import net.aMz;
import net.aRY;

public class ItemRewriter {
   public static final Map ENTTIY_NAME_TO_ID = new HashMap();
   public static final Map ENTTIY_ID_TO_NAME = new HashMap();
   public static final Map POTION_NAME_TO_ID = new HashMap();
   public static final Map POTION_ID_TO_NAME = new HashMap();
   private static final Int2IntMap a = new Int2IntOpenHashMap(36, 1.0F);

   public static void a(aMz var0) {
      int var1 = Cw.a();
      if(var0 != null) {
         if(var0.e() == 383 && var0.c() == 0) {
            CompoundTag var2 = var0.d();
            int var3 = 0;
            if(var2.get("EntityTag") instanceof CompoundTag) {
               CompoundTag var4 = (CompoundTag)var2.get("EntityTag");
               if(var4.get("id") instanceof StringTag) {
                  StringTag var5 = (StringTag)var4.get("id");
                  if(ENTTIY_NAME_TO_ID.containsKey(var5.getValue())) {
                     var3 = ((Integer)ENTTIY_NAME_TO_ID.get(var5.getValue())).intValue();
                  }
               }

               var2.remove("EntityTag");
            }

            var0.a(var2);
            var0.a((short)var3);
         }

         if(var0.e() == 373) {
            CompoundTag var6 = var0.d();
            int var11 = 0;
            if(var6.get("Potion") instanceof StringTag) {
               StringTag var13 = (StringTag)var6.get("Potion");
               String var15 = var13.getValue().replace("minecraft:", "");
               if(POTION_NAME_TO_ID.containsKey(var15)) {
                  var11 = ((Integer)POTION_NAME_TO_ID.get(var15)).intValue();
               }

               var6.remove("Potion");
            }

            var0.a(var6);
            var0.a((short)var11);
         }

         if(var0.e() == 438) {
            CompoundTag var7 = var0.d();
            int var12 = 0;
            var0.a((int)373);
            if(var7.get("Potion") instanceof StringTag) {
               StringTag var14 = (StringTag)var7.get("Potion");
               String var16 = var14.getValue().replace("minecraft:", "");
               if(POTION_NAME_TO_ID.containsKey(var16)) {
                  var12 = ((Integer)POTION_NAME_TO_ID.get(var16)).intValue() + 8192;
               }

               var7.remove("Potion");
            }

            var0.a(var7);
            var0.a((short)var12);
         }

         boolean var8 = var0.e() >= 198 && var0.e() <= 212;
         var8 = var8 | (var0.e() == 397 && var0.c() == 5);
         var8 = var8 | (var0.e() >= 432 && var0.e() <= 448);
         var0.a((int)1);
         var0.a((short)0);
      }

   }

   public static void b(aMz var0) {
      Cw.b();
      int var2 = var0.e();
      if(var2 == 387) {
         CompoundTag var3 = var0.d();
         ListTag var4 = (ListTag)var3.get("pages");
      }
   }

   private static String a(String var0) {
      int var1 = Cw.b();
      if(!var0.startsWith(" ")) {
         return var0;
      } else {
         var0 = "§r" + var0;
         return var0;
      }
   }

   public static void c(aMz var0) {
      int var1 = Cw.a();
      if(var0 != null) {
         if(var0.e() == 383 && var0.c() != 0) {
            CompoundTag var2 = var0.d();
            if(var2 == null) {
               var2 = new CompoundTag("tag");
            }

            CompoundTag var3 = new CompoundTag("EntityTag");
            String var4 = (String)ENTTIY_ID_TO_NAME.get(Integer.valueOf(var0.c()));
            StringTag var5 = new StringTag("id", var4);
            var3.put(var5);
            var2.put(var3);
            var0.a(var2);
            var0.a((short)0);
         }

         if(var0.e() == 373) {
            CompoundTag var6 = var0.d();
            if(var6 == null) {
               var6 = new CompoundTag("tag");
            }

            if(var0.c() >= 16384) {
               var0.a((int)438);
               var0.a((short)(var0.c() - 8192));
            }

            String var8 = potionNameFromDamage(var0.c());
            StringTag var11 = new StringTag("Potion", "minecraft:" + var8);
            var6.put(var11);
            var0.a(var6);
            var0.a((short)0);
         }

         if(var0.e() == 387) {
            CompoundTag var7 = var0.d();
            if(var7 == null) {
               var7 = new CompoundTag("tag");
            }

            ListTag var9 = (ListTag)var7.get("pages");
            if(var9 == null) {
               var9 = new ListTag("pages", Collections.singletonList(new StringTag(aRY.b("").toString())));
               var7.put(var9);
               var0.a(var7);
               return;
            }

            int var12 = 0;
            if(var12 < var9.size()) {
               if(!(var9.get(var12) instanceof StringTag)) {
                  ;
               }

               StringTag var14 = (StringTag)var9.get(var12);
               var14.setValue(aRY.b(var14.getValue()).toString());
               ++var12;
            }

            var0.a(var7);
         }
      }

   }

   public static String potionNameFromDamage(short var0) {
      Cw.a();
      String var2 = (String)POTION_ID_TO_NAME.get(Integer.valueOf(var0));
      if(var2 != null) {
         return var2;
      } else if(var0 == 0) {
         return "water";
      } else {
         int var3 = var0 & 15;
         int var4 = var0 & 63;
         boolean var5 = (var0 & 32) > 0;
         boolean var6 = (var0 & 64) > 0;
         boolean var7 = true;
         boolean var8 = true;
         switch(var3) {
         case 1:
            String var9 = "regeneration";
         case 2:
            String var20 = "swiftness";
         case 3:
            String var21 = "fire_resistance";
            var7 = false;
         case 4:
            String var22 = "poison";
         case 5:
            String var23 = "healing";
            var8 = false;
         case 6:
            String var24 = "night_vision";
            var7 = false;
         case 8:
            String var25 = "weakness";
            var7 = false;
         case 9:
            String var26 = "strength";
         case 10:
            String var27 = "slowness";
            var7 = false;
         case 11:
            String var28 = "leaping";
         case 12:
            String var29 = "harming";
            var8 = false;
         case 13:
            String var30 = "water_breathing";
            var7 = false;
         case 14:
            String var31 = "invisibility";
            var7 = false;
         case 7:
         default:
            var7 = false;
            var8 = false;
            switch(var4) {
            case 0:
               String var32 = "mundane";
            case 16:
               String var33 = "awkward";
            case 32:
               String var34 = "thick";
            default:
               String var35 = "empty";
               if(var3 > 0) {
                  if(var7 && var5) {
                     var35 = "strong_" + var35;
                  }

                  if(var8 && var6) {
                     var35 = "long_" + var35;
                  }
               }

               return var35;
            }
         }
      }
   }

   public static int getNewEffectID(int var0) {
      int var1 = Cw.a();
      if(var0 >= 16384) {
         var0 -= 8192;
      }

      int var2 = a.get(var0);
      if(var2 != -1) {
         return var2;
      } else {
         var0 = ((Integer)POTION_NAME_TO_ID.get(potionNameFromDamage((short)var0))).intValue();
         return (var2 = a.get(var0)) != -1?var2:0;
      }
   }

   private static void registerEntity(int var0, String var1) {
      ENTTIY_ID_TO_NAME.put(Integer.valueOf(var0), var1);
      ENTTIY_NAME_TO_ID.put(var1, Integer.valueOf(var0));
   }

   private static void registerPotion(int var0, String var1) {
      a.put(var0, POTION_ID_TO_NAME.size());
      POTION_ID_TO_NAME.put(Integer.valueOf(var0), var1);
      POTION_NAME_TO_ID.put(var1, Integer.valueOf(var0));
   }

   static {
      registerEntity(1, "Item");
      registerEntity(2, "XPOrb");
      registerEntity(7, "ThrownEgg");
      registerEntity(8, "LeashKnot");
      registerEntity(9, "Painting");
      registerEntity(10, "Arrow");
      registerEntity(11, "Snowball");
      registerEntity(12, "Fireball");
      registerEntity(13, "SmallFireball");
      registerEntity(14, "ThrownEnderpearl");
      registerEntity(15, "EyeOfEnderSignal");
      registerEntity(16, "ThrownPotion");
      registerEntity(17, "ThrownExpBottle");
      registerEntity(18, "ItemFrame");
      registerEntity(19, "WitherSkull");
      registerEntity(20, "PrimedTnt");
      registerEntity(21, "FallingSand");
      registerEntity(22, "FireworksRocketEntity");
      registerEntity(30, "ArmorStand");
      registerEntity(40, "MinecartCommandBlock");
      registerEntity(41, "Boat");
      registerEntity(42, "MinecartRideable");
      registerEntity(43, "MinecartChest");
      registerEntity(44, "MinecartFurnace");
      registerEntity(45, "MinecartTNT");
      registerEntity(46, "MinecartHopper");
      registerEntity(47, "MinecartSpawner");
      registerEntity(48, "Mob");
      registerEntity(49, "Monster");
      registerEntity(50, "Creeper");
      registerEntity(51, "Skeleton");
      registerEntity(52, "Spider");
      registerEntity(53, "Giant");
      registerEntity(54, "Zombie");
      registerEntity(55, "Slime");
      registerEntity(56, "Ghast");
      registerEntity(57, "PigZombie");
      registerEntity(58, "Enderman");
      registerEntity(59, "CaveSpider");
      registerEntity(60, "Silverfish");
      registerEntity(61, "Blaze");
      registerEntity(62, "LavaSlime");
      registerEntity(63, "EnderDragon");
      registerEntity(64, "WitherBoss");
      registerEntity(65, "Bat");
      registerEntity(66, "Witch");
      registerEntity(67, "Endermite");
      registerEntity(68, "Guardian");
      registerEntity(90, "Pig");
      registerEntity(91, "Sheep");
      registerEntity(92, "Cow");
      registerEntity(93, "Chicken");
      registerEntity(94, "Squid");
      registerEntity(95, "Wolf");
      registerEntity(96, "MushroomCow");
      registerEntity(97, "SnowMan");
      registerEntity(98, "Ozelot");
      registerEntity(99, "VillagerGolem");
      registerEntity(100, "EntityHorse");
      registerEntity(101, "Rabbit");
      registerEntity(120, "Villager");
      registerEntity(200, "EnderCrystal");
      registerPotion(-1, "empty");
      registerPotion(0, "water");
      registerPotion(64, "mundane");
      registerPotion(32, "thick");
      registerPotion(16, "awkward");
      registerPotion(8198, "night_vision");
      registerPotion(8262, "long_night_vision");
      registerPotion(8206, "invisibility");
      registerPotion(8270, "long_invisibility");
      registerPotion(8203, "leaping");
      registerPotion(8267, "long_leaping");
      registerPotion(8235, "strong_leaping");
      registerPotion(8195, "fire_resistance");
      registerPotion(8259, "long_fire_resistance");
      registerPotion(8194, "swiftness");
      registerPotion(8258, "long_swiftness");
      registerPotion(8226, "strong_swiftness");
      registerPotion(8202, "slowness");
      registerPotion(8266, "long_slowness");
      registerPotion(8205, "water_breathing");
      registerPotion(8269, "long_water_breathing");
      registerPotion(8261, "healing");
      registerPotion(8229, "strong_healing");
      registerPotion(8204, "harming");
      registerPotion(8236, "strong_harming");
      registerPotion(8196, "poison");
      registerPotion(8260, "long_poison");
      registerPotion(8228, "strong_poison");
      registerPotion(8193, "regeneration");
      registerPotion(8257, "long_regeneration");
      registerPotion(8225, "strong_regeneration");
      registerPotion(8201, "strength");
      registerPotion(8265, "long_strength");
      registerPotion(8233, "strong_strength");
      registerPotion(8200, "weakness");
      registerPotion(8264, "long_weakness");
   }
}
