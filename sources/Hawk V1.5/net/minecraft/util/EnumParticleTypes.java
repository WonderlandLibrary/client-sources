package net.minecraft.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Map;

public enum EnumParticleTypes {
   ITEM_TAKE("ITEM_TAKE", 40, "take", 40, false),
   SUSPENDED("SUSPENDED", 7, "suspended", 7, false),
   SUSPENDED_DEPTH("SUSPENDED_DEPTH", 8, "depthsuspend", 8, false),
   VILLAGER_HAPPY("VILLAGER_HAPPY", 21, "happyVillager", 21, false),
   SMOKE_LARGE("SMOKE_LARGE", 12, "largesmoke", 12, false);

   private final String field_179369_Q;
   private final int field_179372_R;
   DRIP_LAVA("DRIP_LAVA", 19, "dripLava", 19, false),
   CLOUD("CLOUD", 29, "cloud", 29, false),
   SPELL_MOB_AMBIENT("SPELL_MOB_AMBIENT", 16, "mobSpellAmbient", 16, false),
   ENCHANTMENT_TABLE("ENCHANTMENT_TABLE", 25, "enchantmenttable", 25, false),
   SMOKE_NORMAL("SMOKE_NORMAL", 11, "smoke", 11, false);

   private final int field_179366_T;
   BLOCK_DUST("BLOCK_DUST", 38, "blockdust_", 38, false, 1),
   VILLAGER_ANGRY("VILLAGER_ANGRY", 20, "angryVillager", 20, false),
   EXPLOSION_NORMAL("EXPLOSION_NORMAL", 0, "explode", 0, true),
   SPELL_WITCH("SPELL_WITCH", 17, "witchMagic", 17, false),
   LAVA("LAVA", 27, "lava", 27, false);

   private static final String[] field_179368_V;
   SNOW_SHOVEL("SNOW_SHOVEL", 32, "snowshovel", 32, false),
   EXPLOSION_HUGE("EXPLOSION_HUGE", 2, "hugeexplosion", 2, true),
   CRIT_MAGIC("CRIT_MAGIC", 10, "magicCrit", 10, false);

   private final boolean field_179371_S;
   SLIME("SLIME", 33, "slime", 33, false);

   private static final Map field_179365_U = Maps.newHashMap();
   NOTE("NOTE", 23, "note", 23, false),
   TOWN_AURA("TOWN_AURA", 22, "townaura", 22, false),
   WATER_WAKE("WATER_WAKE", 6, "wake", 6, false),
   EXPLOSION_LARGE("EXPLOSION_LARGE", 1, "largeexplode", 1, true),
   SPELL_MOB("SPELL_MOB", 15, "mobSpell", 15, false);

   private static final EnumParticleTypes[] ENUM$VALUES = new EnumParticleTypes[]{EXPLOSION_NORMAL, EXPLOSION_LARGE, EXPLOSION_HUGE, FIREWORKS_SPARK, WATER_BUBBLE, WATER_SPLASH, WATER_WAKE, SUSPENDED, SUSPENDED_DEPTH, CRIT, CRIT_MAGIC, SMOKE_NORMAL, SMOKE_LARGE, SPELL, SPELL_INSTANT, SPELL_MOB, SPELL_MOB_AMBIENT, SPELL_WITCH, DRIP_WATER, DRIP_LAVA, VILLAGER_ANGRY, VILLAGER_HAPPY, TOWN_AURA, NOTE, PORTAL, ENCHANTMENT_TABLE, FLAME, LAVA, FOOTSTEP, CLOUD, REDSTONE, SNOWBALL, SNOW_SHOVEL, SLIME, HEART, BARRIER, ITEM_CRACK, BLOCK_CRACK, BLOCK_DUST, WATER_DROP, ITEM_TAKE, MOB_APPEARANCE};
   FIREWORKS_SPARK("FIREWORKS_SPARK", 3, "fireworksSpark", 3, false);

   private static final String __OBFID = "CL_00002317";
   CRIT("CRIT", 9, "crit", 9, false),
   BLOCK_CRACK("BLOCK_CRACK", 37, "blockcrack_", 37, false, 1),
   PORTAL("PORTAL", 24, "portal", 24, false),
   HEART("HEART", 34, "heart", 34, false),
   BARRIER("BARRIER", 35, "barrier", 35, false),
   MOB_APPEARANCE("MOB_APPEARANCE", 41, "mobappearance", 41, true),
   REDSTONE("REDSTONE", 30, "reddust", 30, false),
   SNOWBALL("SNOWBALL", 31, "snowballpoof", 31, false),
   DRIP_WATER("DRIP_WATER", 18, "dripWater", 18, false),
   WATER_BUBBLE("WATER_BUBBLE", 4, "bubble", 4, false),
   ITEM_CRACK("ITEM_CRACK", 36, "iconcrack_", 36, false, 2),
   FOOTSTEP("FOOTSTEP", 28, "footstep", 28, false);

   private static final EnumParticleTypes[] $VALUES = new EnumParticleTypes[]{EXPLOSION_NORMAL, EXPLOSION_LARGE, EXPLOSION_HUGE, FIREWORKS_SPARK, WATER_BUBBLE, WATER_SPLASH, WATER_WAKE, SUSPENDED, SUSPENDED_DEPTH, CRIT, CRIT_MAGIC, SMOKE_NORMAL, SMOKE_LARGE, SPELL, SPELL_INSTANT, SPELL_MOB, SPELL_MOB_AMBIENT, SPELL_WITCH, DRIP_WATER, DRIP_LAVA, VILLAGER_ANGRY, VILLAGER_HAPPY, TOWN_AURA, NOTE, PORTAL, ENCHANTMENT_TABLE, FLAME, LAVA, FOOTSTEP, CLOUD, REDSTONE, SNOWBALL, SNOW_SHOVEL, SLIME, HEART, BARRIER, ITEM_CRACK, BLOCK_CRACK, BLOCK_DUST, WATER_DROP, ITEM_TAKE, MOB_APPEARANCE};
   WATER_DROP("WATER_DROP", 39, "droplet", 39, false),
   SPELL_INSTANT("SPELL_INSTANT", 14, "instantSpell", 14, false),
   SPELL("SPELL", 13, "spell", 13, false),
   WATER_SPLASH("WATER_SPLASH", 5, "splash", 5, false),
   FLAME("FLAME", 26, "flame", 26, false);

   static {
      ArrayList var0 = Lists.newArrayList();
      EnumParticleTypes[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EnumParticleTypes var4 = var1[var3];
         field_179365_U.put(var4.func_179348_c(), var4);
         if (!var4.func_179346_b().endsWith("_")) {
            var0.add(var4.func_179346_b());
         }
      }

      field_179368_V = (String[])var0.toArray(new String[var0.size()]);
   }

   public int func_179345_d() {
      return this.field_179366_T;
   }

   public boolean func_179343_f() {
      return this.field_179366_T > 0;
   }

   public String func_179346_b() {
      return this.field_179369_Q;
   }

   public boolean func_179344_e() {
      return this.field_179371_S;
   }

   public static String[] func_179349_a() {
      return field_179368_V;
   }

   private EnumParticleTypes(String var3, int var4, String var5, int var6, boolean var7, int var8) {
      this.field_179369_Q = var5;
      this.field_179372_R = var6;
      this.field_179371_S = var7;
      this.field_179366_T = var8;
   }

   public int func_179348_c() {
      return this.field_179372_R;
   }

   private EnumParticleTypes(String var3, int var4, String var5, int var6, boolean var7) {
      this(var3, var4, var5, var6, var7, 0);
   }

   public static EnumParticleTypes func_179342_a(int var0) {
      return (EnumParticleTypes)field_179365_U.get(var0);
   }
}
