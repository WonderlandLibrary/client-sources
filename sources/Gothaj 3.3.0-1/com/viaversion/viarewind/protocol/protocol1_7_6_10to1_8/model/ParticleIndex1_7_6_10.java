package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.model;

import java.util.HashMap;

public enum ParticleIndex1_7_6_10 {
   EXPLOSION_NORMAL("explode"),
   EXPLOSION_LARGE("largeexplode"),
   EXPLOSION_HUGE("hugeexplosion"),
   FIREWORKS_SPARK("fireworksSpark"),
   WATER_BUBBLE("bubble"),
   WATER_SPLASH("splash"),
   WATER_WAKE("wake"),
   SUSPENDED("suspended"),
   SUSPENDED_DEPTH("depthsuspend"),
   CRIT("crit"),
   CRIT_MAGIC("magicCrit"),
   SMOKE_NORMAL("smoke"),
   SMOKE_LARGE("largesmoke"),
   SPELL("spell"),
   SPELL_INSTANT("instantSpell"),
   SPELL_MOB("mobSpell"),
   SPELL_MOB_AMBIENT("mobSpellAmbient"),
   SPELL_WITCH("witchMagic"),
   DRIP_WATER("dripWater"),
   DRIP_LAVA("dripLava"),
   VILLAGER_ANGRY("angryVillager"),
   VILLAGER_HAPPY("happyVillager"),
   TOWN_AURA("townaura"),
   NOTE("note"),
   PORTAL("portal"),
   ENCHANTMENT_TABLE("enchantmenttable"),
   FLAME("flame"),
   LAVA("lava"),
   FOOTSTEP("footstep"),
   CLOUD("cloud"),
   REDSTONE("reddust"),
   SNOWBALL("snowballpoof"),
   SNOW_SHOVEL("snowshovel"),
   SLIME("slime"),
   HEART("heart"),
   BARRIER("barrier"),
   ICON_CRACK("iconcrack", 2),
   BLOCK_CRACK("blockcrack", 1),
   BLOCK_DUST("blockdust", 1),
   WATER_DROP("droplet"),
   ITEM_TAKE("take"),
   MOB_APPEARANCE("mobappearance");

   public final String name;
   public final int extra;
   private static final HashMap<String, ParticleIndex1_7_6_10> particleMap = new HashMap<>();

   private ParticleIndex1_7_6_10(String name) {
      this(name, 0);
   }

   private ParticleIndex1_7_6_10(String name, int extra) {
      this.name = name;
      this.extra = extra;
   }

   public static ParticleIndex1_7_6_10 find(String part) {
      return particleMap.get(part);
   }

   public static ParticleIndex1_7_6_10 find(int id) {
      if (id < 0) {
         return null;
      } else {
         ParticleIndex1_7_6_10[] values = values();
         return id >= values.length ? null : values[id];
      }
   }

   static {
      ParticleIndex1_7_6_10[] particles = values();

      for (ParticleIndex1_7_6_10 particle : particles) {
         particleMap.put(particle.name, particle);
      }
   }
}
