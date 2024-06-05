package org.json;

public class Cookie {
   public static String toString(JSONObject var0) throws JSONException {
      StringBuilder var1 = new StringBuilder();
      var1.append(escape(var0.getString("name")));
      var1.append("=");
      var1.append(escape(var0.getString("value")));
      if (var0.has("expires")) {
         var1.append(";expires=");
         var1.append(var0.getString("expires"));
      }

      if (var0.has("domain")) {
         var1.append(";domain=");
         var1.append(escape(var0.getString("domain")));
      }

      if (var0.has("path")) {
         var1.append(";path=");
         var1.append(escape(var0.getString("path")));
      }

      if (var0.optBoolean("secure")) {
         var1.append(";secure");
      }

      return String.valueOf(var1);
   }

   public static String unescape(String var0) {
      int var1 = var0.length();
      StringBuilder var2 = new StringBuilder(var1);

      for(int var3 = 0; var3 < var1; ++var3) {
         char var4 = var0.charAt(var3);
         if (var4 == '+') {
            var4 = ' ';
         } else if (var4 == '%' && var3 + 2 < var1) {
            int var5 = JSONTokener.dehexchar(var0.charAt(var3 + 1));
            int var6 = JSONTokener.dehexchar(var0.charAt(var3 + 2));
            if (var5 >= 0 && var6 >= 0) {
               var4 = (char)(var5 * 16 + var6);
               var3 += 2;
            }
         }

         var2.append(var4);
      }

      return String.valueOf(var2);
   }

   public static String escape(String var0) {
      String var2 = var0.trim();
      int var3 = var2.length();
      StringBuilder var4 = new StringBuilder(var3);

      for(int var5 = 0; var5 < var3; ++var5) {
         char var1 = var2.charAt(var5);
         if (var1 >= ' ' && var1 != '+' && var1 != '%' && var1 != '=' && var1 != ';') {
            var4.append(var1);
         } else {
            var4.append('%');
            var4.append(Character.forDigit((char)(var1 >>> 4 & 15), 16));
            var4.append(Character.forDigit((char)(var1 & 15), 16));
         }
      }

      return String.valueOf(var4);
   }

   public static JSONObject toJSONObject(String var0) throws JSONException {
      JSONObject var2 = new JSONObject();
      JSONTokener var4 = new JSONTokener(var0);
      var2.put("name", (Object)var4.nextTo('='));
      var4.next('=');
      var2.put("value", (Object)var4.nextTo(';'));
      var4.next();

      String var1;
      Object var3;
      for(; var4.more(); var2.put(var1, var3)) {
         var1 = unescape(var4.nextTo("=;"));
         if (var4.next() != '=') {
            if (!var1.equals("secure")) {
               throw var4.syntaxError("Missing '=' in cookie parameter.");
            }

            var3 = Boolean.TRUE;
         } else {
            var3 = unescape(var4.nextTo(';'));
            var4.next();
         }
      }

      return var2;
   }
}
