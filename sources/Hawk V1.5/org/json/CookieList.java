package org.json;

import java.util.Iterator;

public class CookieList {
   public static String toString(JSONObject var0) throws JSONException {
      boolean var1 = false;
      StringBuilder var2 = new StringBuilder();
      Iterator var3 = var0.keySet().iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         Object var5 = var0.opt(var4);
         if (!JSONObject.NULL.equals(var5)) {
            if (var1) {
               var2.append(';');
            }

            var2.append(Cookie.escape(var4));
            var2.append("=");
            var2.append(Cookie.escape(var5.toString()));
            var1 = true;
         }
      }

      return String.valueOf(var2);
   }

   public static JSONObject toJSONObject(String var0) throws JSONException {
      JSONObject var1 = new JSONObject();
      JSONTokener var2 = new JSONTokener(var0);

      while(var2.more()) {
         String var3 = Cookie.unescape(var2.nextTo('='));
         var2.next('=');
         var1.put(var3, (Object)Cookie.unescape(var2.nextTo(';')));
         var2.next();
      }

      return var1;
   }
}
