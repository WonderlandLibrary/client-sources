package net.minecraft.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Map;

public enum EnumParticleTypes {
   EXPLOSION_NORMAL("explode", 0, true),
   EXPLOSION_LARGE("largeexplode", 1, true),
   EXPLOSION_HUGE("hugeexplosion", 2, true),
   FIREWORKS_SPARK("fireworksSpark", 3, false),
   WATER_BUBBLE("bubble", 4, false),
   WATER_SPLASH("splash", 5, false),
   WATER_WAKE("wake", 6, false),
   SUSPENDED("suspended", 7, false),
   SUSPENDED_DEPTH("depthsuspend", 8, false),
   CRIT("crit", 9, false),
   CRIT_MAGIC("magicCrit", 10, false),
   SMOKE_NORMAL("smoke", 11, false),
   SMOKE_LARGE("largesmoke", 12, false),
   SPELL("spell", 13, false),
   SPELL_INSTANT("instantSpell", 14, false),
   SPELL_MOB("mobSpell", 15, false),
   SPELL_MOB_AMBIENT("mobSpellAmbient", 16, false),
   SPELL_WITCH("witchMagic", 17, false),
   DRIP_WATER("dripWater", 18, false),
   DRIP_LAVA("dripLava", 19, false),
   VILLAGER_ANGRY("angryVillager", 20, false),
   VILLAGER_HAPPY("happyVillager", 21, false),
   TOWN_AURA("townaura", 22, false),
   NOTE("note", 23, false),
   PORTAL("portal", 24, false),
   ENCHANTMENT_TABLE("enchantmenttable", 25, false),
   FLAME("flame", 26, false),
   LAVA("lava", 27, false),
   FOOTSTEP("footstep", 28, false),
   CLOUD("cloud", 29, false),
   REDSTONE("reddust", 30, false),
   SNOWBALL("snowballpoof", 31, false),
   SNOW_SHOVEL("snowshovel", 32, false),
   SLIME("slime", 33, false),
   HEART("heart", 34, false),
   BARRIER("barrier", 35, false),
   ITEM_CRACK("iconcrack_", 36, false, 2),
   BLOCK_CRACK("blockcrack_", 37, false, 1),
   BLOCK_DUST("blockdust_", 38, false, 1),
   WATER_DROP("droplet", 39, false),
   ITEM_TAKE("take", 40, false),
   MOB_APPEARANCE("mobappearance", 41, true);

   private final String particleName;
   private final int particleID;
   private final boolean shouldIgnoreRange;
   private final int argumentCount;
   private static final Map PARTICLES = Maps.newHashMap();
   private static final String[] PARTICLE_NAMES;

   private EnumParticleTypes(String var3, int var4, boolean var5, int var6) {
      this.particleName = var3;
      this.particleID = var4;
      this.shouldIgnoreRange = var5;
      this.argumentCount = var6;
   }

   private EnumParticleTypes(String var3, int var4, boolean var5) {
      this(var3, var4, var5, 0);
   }

   public static String[] getParticleNames() {
      return PARTICLE_NAMES;
   }

   public String getParticleName() {
      return this.particleName;
   }

   public int getParticleID() {
      return this.particleID;
   }

   public int getArgumentCount() {
      return this.argumentCount;
   }

   public boolean getShouldIgnoreRange() {
      return this.shouldIgnoreRange;
   }

   public boolean hasArguments() {
      return this.argumentCount > 0;
   }

   public static EnumParticleTypes getParticleFromId(int var0) {
      return (EnumParticleTypes)PARTICLES.get(Integer.valueOf(var0));
   }

   static {
      ArrayList var8 = Lists.newArrayList();

      for(EnumParticleTypes var12 : values()) {
         PARTICLES.put(Integer.valueOf(var12.getParticleID()), var12);
         if(!var12.getParticleName().endsWith("_")) {
            var8.add(var12.getParticleName());
         }
      }

      PARTICLE_NAMES = (String[])((String[])var8.toArray(new String[var8.size()]));
   }
}
