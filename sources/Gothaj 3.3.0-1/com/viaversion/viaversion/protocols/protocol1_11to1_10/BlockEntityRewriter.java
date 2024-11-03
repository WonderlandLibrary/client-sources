package com.viaversion.viaversion.protocols.protocol1_11to1_10;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.viaversion.viaversion.util.Key;

public class BlockEntityRewriter {
   private static BiMap<String, String> oldToNewNames = HashBiMap.create();

   private static void rewrite(String oldName, String newName) {
      oldToNewNames.put(oldName, Key.namespaced(newName));
   }

   public static BiMap<String, String> inverse() {
      return oldToNewNames.inverse();
   }

   public static String toNewIdentifier(String oldId) {
      String newName = (String)oldToNewNames.get(oldId);
      return newName != null ? newName : oldId;
   }

   static {
      rewrite("Furnace", "furnace");
      rewrite("Chest", "chest");
      rewrite("EnderChest", "ender_chest");
      rewrite("RecordPlayer", "jukebox");
      rewrite("Trap", "dispenser");
      rewrite("Dropper", "dropper");
      rewrite("Sign", "sign");
      rewrite("MobSpawner", "mob_spawner");
      rewrite("Music", "noteblock");
      rewrite("Piston", "piston");
      rewrite("Cauldron", "brewing_stand");
      rewrite("EnchantTable", "enchanting_table");
      rewrite("Airportal", "end_portal");
      rewrite("Beacon", "beacon");
      rewrite("Skull", "skull");
      rewrite("DLDetector", "daylight_detector");
      rewrite("Hopper", "hopper");
      rewrite("Comparator", "comparator");
      rewrite("FlowerPot", "flower_pot");
      rewrite("Banner", "banner");
      rewrite("Structure", "structure_block");
      rewrite("EndGateway", "end_gateway");
      rewrite("Control", "command_block");
   }
}
