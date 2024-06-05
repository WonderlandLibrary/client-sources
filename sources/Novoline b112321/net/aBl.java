package net;

import cc.novoline.utils.web.JsonObtainer;
import org.json.JSONObject;

public class aBl {
   private static String[] b;

   public static JSONObject a(String var0) {
      return JsonObtainer.obtainJson(var0);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[2]);
      }

   }
}
