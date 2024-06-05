package net;

import java.util.HashMap;
import java.util.Map;
import net.amt;

public class As {
   private static final Map a = new HashMap();

   private static void a(String var0, String var1) {
      a.put("minecraft:" + var0, "minecraft:" + var1);
   }

   public static String a(String var0) {
      amt.c();
      String var2 = (String)a.get(var0);
      if(var2 != null) {
         return var2;
      } else {
         var2 = (String)a.get("minecraft:" + var0);
         return var2 != null?var2:var0;
      }
   }

   static {
      a("commandblock_minecart", "command_block_minecart");
      a("ender_crystal", "end_crystal");
      a("evocation_fangs", "evoker_fangs");
      a("evocation_illager", "evoker");
      a("eye_of_ender_signal", "eye_of_ender");
      a("fireworks_rocket", "firework_rocket");
      a("illusion_illager", "illusioner");
      a("snowman", "snow_golem");
      a("villager_golem", "iron_golem");
      a("vindication_illager", "vindicator");
      a("xp_bottle", "experience_bottle");
      a("xp_orb", "experience_orb");
   }
}
