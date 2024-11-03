package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.viaversion.viaversion.util.Key;
import java.util.HashMap;
import java.util.Map;

public class EntityNameRewriter {
   private static final Map<String, String> entityNames = new HashMap<>();

   private static void reg(String past, String future) {
      entityNames.put(Key.namespaced(past), Key.namespaced(future));
   }

   public static String rewrite(String entName) {
      String entityName = entityNames.get(entName);
      if (entityName != null) {
         return entityName;
      } else {
         entityName = entityNames.get(Key.namespaced(entName));
         return entityName != null ? entityName : entName;
      }
   }

   static {
      reg("commandblock_minecart", "command_block_minecart");
      reg("ender_crystal", "end_crystal");
      reg("evocation_fangs", "evoker_fangs");
      reg("evocation_illager", "evoker");
      reg("eye_of_ender_signal", "eye_of_ender");
      reg("fireworks_rocket", "firework_rocket");
      reg("illusion_illager", "illusioner");
      reg("snowman", "snow_golem");
      reg("villager_golem", "iron_golem");
      reg("vindication_illager", "vindicator");
      reg("xp_bottle", "experience_bottle");
      reg("xp_orb", "experience_orb");
   }
}
