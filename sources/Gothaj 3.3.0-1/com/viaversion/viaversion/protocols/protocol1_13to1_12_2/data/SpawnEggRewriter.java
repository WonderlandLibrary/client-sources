package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.viaversion.viaversion.util.Key;
import java.util.Optional;

public class SpawnEggRewriter {
   private static final BiMap<String, Integer> spawnEggs = HashBiMap.create();

   private static void registerSpawnEgg(String name) {
      spawnEggs.put(Key.namespaced(name), spawnEggs.size());
   }

   public static int getSpawnEggId(String entityIdentifier) {
      return !spawnEggs.containsKey(entityIdentifier) ? -1 : 25100288 | (Integer)spawnEggs.get(entityIdentifier) & 65535;
   }

   public static Optional<String> getEntityId(int spawnEggId) {
      return spawnEggId >> 16 != 383 ? Optional.empty() : Optional.ofNullable((String)spawnEggs.inverse().get(spawnEggId & 65535));
   }

   static {
      registerSpawnEgg("bat");
      registerSpawnEgg("blaze");
      registerSpawnEgg("cave_spider");
      registerSpawnEgg("chicken");
      registerSpawnEgg("cow");
      registerSpawnEgg("creeper");
      registerSpawnEgg("donkey");
      registerSpawnEgg("elder_guardian");
      registerSpawnEgg("enderman");
      registerSpawnEgg("endermite");
      registerSpawnEgg("evocation_illager");
      registerSpawnEgg("ghast");
      registerSpawnEgg("guardian");
      registerSpawnEgg("horse");
      registerSpawnEgg("husk");
      registerSpawnEgg("llama");
      registerSpawnEgg("magma_cube");
      registerSpawnEgg("mooshroom");
      registerSpawnEgg("mule");
      registerSpawnEgg("ocelot");
      registerSpawnEgg("parrot");
      registerSpawnEgg("pig");
      registerSpawnEgg("polar_bear");
      registerSpawnEgg("rabbit");
      registerSpawnEgg("sheep");
      registerSpawnEgg("shulker");
      registerSpawnEgg("silverfish");
      registerSpawnEgg("skeleton");
      registerSpawnEgg("skeleton_horse");
      registerSpawnEgg("slime");
      registerSpawnEgg("spider");
      registerSpawnEgg("squid");
      registerSpawnEgg("stray");
      registerSpawnEgg("vex");
      registerSpawnEgg("villager");
      registerSpawnEgg("vindication_illager");
      registerSpawnEgg("witch");
      registerSpawnEgg("wither_skeleton");
      registerSpawnEgg("wolf");
      registerSpawnEgg("zombie");
      registerSpawnEgg("zombie_horse");
      registerSpawnEgg("zombie_pigman");
      registerSpawnEgg("zombie_villager");
   }
}
