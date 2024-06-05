package org.json;

public class CDL {
   public static JSONArray toJSONArray(JSONTokener var0) throws JSONException {
      return toJSONArray(rowToJSONArray(var0), var0);
   }

   public static String toString(JSONArray var0) throws JSONException {
      JSONObject var1 = var0.optJSONObject(0);
      if (var1 != null) {
         JSONArray var2 = var1.names();
         if (var2 != null) {
            return String.valueOf((new StringBuilder()).append(rowToString(var2)).append(toString(var2, var0)));
         }
      }

      return null;
   }

   public static JSONArray toJSONArray(JSONArray var0, JSONTokener var1) throws JSONException {
      if (var0 != null && var0.length() != 0) {
         JSONArray var2 = new JSONArray();

         while(true) {
            JSONObject var3 = rowToJSONObject(var0, var1);
            if (var3 == null) {
               return var2.length() == 0 ? null : var2;
            }

            var2.put((Object)var3);
         }
      } else {
         return null;
      }
   }

   public static String toString(JSONArray var0, JSONArray var1) throws JSONException {
      if (var0 != null && var0.length() != 0) {
         StringBuilder var2 = new StringBuilder();

         for(int var3 = 0; var3 < var1.length(); ++var3) {
            JSONObject var4 = var1.optJSONObject(var3);
            if (var4 != null) {
               var2.append(rowToString(var4.toJSONArray(var0)));
            }
         }

         return String.valueOf(var2);
      } else {
         return null;
      }
   }

   private static String getValue(JSONTokener var0) throws JSONException {
      char var1;
      do {
         var1 = var0.next();
      } while(var1 == ' ' || var1 == '\t');

      switch(var1) {
      case '\u0000':
         return null;
      case '"':
      case '\'':
         char var2 = var1;
         StringBuilder var3 = new StringBuilder();

         while(true) {
            var1 = var0.next();
            if (var1 == var2) {
               char var4 = var0.next();
               if (var4 != '"') {
                  if (var4 > 0) {
                     var0.back();
                  }

                  return String.valueOf(var3);
               }
            }

            if (var1 == 0 || var1 == '\n' || var1 == '\r') {
               throw var0.syntaxError(String.valueOf((new StringBuilder()).append("Missing close quote '").append(var2).append("'.")));
            }

            var3.append(var1);
         }
      case ',':
         var0.back();
         return "";
      default:
         var0.back();
         return var0.nextTo(',');
      }
   }

   public static JSONArray toJSONArray(JSONArray var0, String var1) throws JSONException {
      return toJSONArray(var0, new JSONTokener(var1));
   }

   public static String rowToString(JSONArray var0) {
      StringBuilder var1 = new StringBuilder();

      for(int var2 = 0; var2 < var0.length(); ++var2) {
         if (var2 > 0) {
            var1.append(',');
         }

         Object var3 = var0.opt(var2);
         if (var3 != null) {
            String var4 = var3.toString();
            if (var4.length() > 0 && (var4.indexOf(44) >= 0 || var4.indexOf(10) >= 0 || var4.indexOf(13) >= 0 || var4.indexOf(0) >= 0 || var4.charAt(0) == '"')) {
               var1.append('"');
               int var5 = var4.length();

               for(int var6 = 0; var6 < var5; ++var6) {
                  char var7 = var4.charAt(var6);
                  if (var7 >= ' ' && var7 != '"') {
                     var1.append(var7);
                  }
               }

               var1.append('"');
            } else {
               var1.append(var4);
            }
         }
      }

      var1.append('\n');
      return String.valueOf(var1);
   }

   public static JSONObject rowToJSONObject(JSONArray var0, JSONTokener var1) throws JSONException {
      JSONArray var2 = rowToJSONArray(var1);
      return var2 != null ? var2.toJSONObject(var0) : null;
   }

   public static JSONArray rowToJSONArray(JSONTokener var0) throws JSONException {
      JSONArray var1 = new JSONArray();

      while(true) {
         String var2 = getValue(var0);
         char var3 = var0.next();
         if (var2 == null || var1.length() == 0 && var2.length() == 0 && var3 != ',') {
            return null;
         }

         var1.put((Object)var2);

         while(var3 != ',') {
            if (var3 != ' ') {
               if (var3 != '\n' && var3 != '\r' && var3 != 0) {
                  throw var0.syntaxError(String.valueOf((new StringBuilder()).append("Bad character '").append(var3).append("' (").append(var3).append(").")));
               }

               return var1;
            }

            var3 = var0.next();
         }
      }
   }

   public static JSONArray toJSONArray(String var0) throws JSONException {
      return toJSONArray(new JSONTokener(var0));
   }
}
