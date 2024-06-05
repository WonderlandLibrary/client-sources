package org.json;

import java.util.Iterator;
import java.util.Locale;

public class HTTP {
   public static final String CRLF = "\r\n";

   public static JSONObject toJSONObject(String var0) throws JSONException {
      JSONObject var1 = new JSONObject();
      HTTPTokener var2 = new HTTPTokener(var0);
      String var3 = var2.nextToken();
      if (var3.toUpperCase(Locale.ROOT).startsWith("HTTP")) {
         var1.put("HTTP-Version", (Object)var3);
         var1.put("Status-Code", (Object)var2.nextToken());
         var1.put("Reason-Phrase", (Object)var2.nextTo('\u0000'));
         var2.next();
      } else {
         var1.put("Method", (Object)var3);
         var1.put("Request-URI", (Object)var2.nextToken());
         var1.put("HTTP-Version", (Object)var2.nextToken());
      }

      while(var2.more()) {
         String var4 = var2.nextTo(':');
         var2.next(':');
         var1.put(var4, (Object)var2.nextTo('\u0000'));
         var2.next();
      }

      return var1;
   }

   public static String toString(JSONObject var0) throws JSONException {
      StringBuilder var1 = new StringBuilder();
      if (var0.has("Status-Code") && var0.has("Reason-Phrase")) {
         var1.append(var0.getString("HTTP-Version"));
         var1.append(' ');
         var1.append(var0.getString("Status-Code"));
         var1.append(' ');
         var1.append(var0.getString("Reason-Phrase"));
      } else {
         if (!var0.has("Method") || !var0.has("Request-URI")) {
            throw new JSONException("Not enough material for an HTTP header.");
         }

         var1.append(var0.getString("Method"));
         var1.append(' ');
         var1.append('"');
         var1.append(var0.getString("Request-URI"));
         var1.append('"');
         var1.append(' ');
         var1.append(var0.getString("HTTP-Version"));
      }

      var1.append("\r\n");
      Iterator var2 = var0.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         String var4 = var0.optString(var3);
         if (!"HTTP-Version".equals(var3) && !"Status-Code".equals(var3) && !"Reason-Phrase".equals(var3) && !"Method".equals(var3) && !"Request-URI".equals(var3) && !JSONObject.NULL.equals(var4)) {
            var1.append(var3);
            var1.append(": ");
            var1.append(var0.optString(var3));
            var1.append("\r\n");
         }
      }

      var1.append("\r\n");
      return String.valueOf(var1);
   }
}
