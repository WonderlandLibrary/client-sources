package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Optional;
import net.amt;

public class SpawnEggRewriter {
   private static final BiMap spawnEggs = HashBiMap.create();

   private static void registerSpawnEgg(String var0) {
      spawnEggs.put(var0, Integer.valueOf(spawnEggs.size()));
   }

   public static int getSpawnEggId(String var0) {
      boolean var1 = amt.c();
      return !spawnEggs.containsKey(var0)?-1:25100288 | ((Integer)spawnEggs.get(var0)).intValue() & '\uffff';
   }

   public static Optional getEntityId(int var0) {
      return var0 >> 16 != 383?Optional.empty():Optional.ofNullable(spawnEggs.inverse().get(Integer.valueOf(var0 & '\uffff')));
   }

   static {
      registerSpawnEgg("minecraft:bat");
      registerSpawnEgg("minecraft:blaze");
      registerSpawnEgg("minecraft:cave_spider");
      registerSpawnEgg("minecraft:chicken");
      registerSpawnEgg("minecraft:cow");
      registerSpawnEgg("minecraft:creeper");
      registerSpawnEgg("minecraft:donkey");
      registerSpawnEgg("minecraft:elder_guardian");
      registerSpawnEgg("minecraft:enderman");
      registerSpawnEgg("minecraft:endermite");
      registerSpawnEgg("minecraft:evocation_illager");
      registerSpawnEgg("minecraft:ghast");
      registerSpawnEgg("minecraft:guardian");
      registerSpawnEgg("minecraft:horse");
      registerSpawnEgg("minecraft:husk");
      registerSpawnEgg("minecraft:llama");
      registerSpawnEgg("minecraft:magma_cube");
      registerSpawnEgg("minecraft:mooshroom");
      registerSpawnEgg("minecraft:mule");
      registerSpawnEgg("minecraft:ocelot");
      registerSpawnEgg("minecraft:parrot");
      registerSpawnEgg("minecraft:pig");
      registerSpawnEgg("minecraft:polar_bear");
      registerSpawnEgg("minecraft:rabbit");
      registerSpawnEgg("minecraft:sheep");
      registerSpawnEgg("minecraft:shulker");
      registerSpawnEgg("minecraft:silverfish");
      registerSpawnEgg("minecraft:skeleton");
      registerSpawnEgg("minecraft:skeleton_horse");
      registerSpawnEgg("minecraft:slime");
      registerSpawnEgg("minecraft:spider");
      registerSpawnEgg("minecraft:squid");
      registerSpawnEgg("minecraft:stray");
      registerSpawnEgg("minecraft:vex");
      registerSpawnEgg("minecraft:villager");
      registerSpawnEgg("minecraft:vindication_illager");
      registerSpawnEgg("minecraft:witch");
      registerSpawnEgg("minecraft:wither_skeleton");
      registerSpawnEgg("minecraft:wolf");
      registerSpawnEgg("minecraft:zombie");
      registerSpawnEgg("minecraft:zombie_horse");
      registerSpawnEgg("minecraft:zombie_pigman");
      registerSpawnEgg("minecraft:zombie_villager");
   }
}
