package org.json;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class JSONPointer {
   private static final String ENCODING = "utf-8";
   private final List<String> refTokens;

   private static String unescape(String var0) {
      return var0.replace("~1", "/").replace("~0", "~").replace("\\\"", "\"").replace("\\\\", "\\");
   }

   private static String escape(String var0) {
      return var0.replace("~", "~0").replace("/", "~1").replace("\\", "\\\\").replace("\"", "\\\"");
   }

   public Object queryFrom(Object var1) throws JSONPointerException {
      if (this.refTokens.isEmpty()) {
         return var1;
      } else {
         Object var2 = var1;
         Iterator var3 = this.refTokens.iterator();

         while(var3.hasNext()) {
            String var4 = (String)var3.next();
            if (var2 instanceof JSONObject) {
               var2 = ((JSONObject)var2).opt(unescape(var4));
            } else {
               if (!(var2 instanceof JSONArray)) {
                  throw new JSONPointerException(String.format("value [%s] is not an array or object therefore its key %s cannot be resolved", var2, var4));
               }

               var2 = readByIndexToken(var2, var4);
            }
         }

         return var2;
      }
   }

   public JSONPointer(String var1) {
      if (var1 == null) {
         throw new NullPointerException("pointer cannot be null");
      } else if (!var1.isEmpty() && !var1.equals("#")) {
         String var2;
         if (var1.startsWith("#/")) {
            var2 = var1.substring(2);

            try {
               var2 = URLDecoder.decode(var2, "utf-8");
            } catch (UnsupportedEncodingException var6) {
               throw new RuntimeException(var6);
            }
         } else {
            if (!var1.startsWith("/")) {
               throw new IllegalArgumentException("a JSON pointer should start with '/' or '#/'");
            }

            var2 = var1.substring(1);
         }

         this.refTokens = new ArrayList();
         int var3 = -1;
         boolean var4 = false;

         do {
            int var7 = var3 + 1;
            var3 = var2.indexOf(47, var7);
            if (var7 != var3 && var7 != var2.length()) {
               String var5;
               if (var3 >= 0) {
                  var5 = var2.substring(var7, var3);
                  this.refTokens.add(unescape(var5));
               } else {
                  var5 = var2.substring(var7);
                  this.refTokens.add(unescape(var5));
               }
            } else {
               this.refTokens.add("");
            }
         } while(var3 >= 0);

      } else {
         this.refTokens = Collections.emptyList();
      }
   }

   public static JSONPointer.Builder builder() {
      return new JSONPointer.Builder();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder("");
      Iterator var2 = this.refTokens.iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         var1.append('/').append(escape(var3));
      }

      return String.valueOf(var1);
   }

   public String toURIFragment() {
      try {
         StringBuilder var1 = new StringBuilder("#");
         Iterator var2 = this.refTokens.iterator();

         while(var2.hasNext()) {
            String var3 = (String)var2.next();
            var1.append('/').append(URLEncoder.encode(var3, "utf-8"));
         }

         return String.valueOf(var1);
      } catch (UnsupportedEncodingException var4) {
         throw new RuntimeException(var4);
      }
   }

   private static Object readByIndexToken(Object param0, String param1) throws JSONPointerException {
      // $FF: Couldn't be decompiled
   }

   public JSONPointer(List<String> var1) {
      this.refTokens = new ArrayList(var1);
   }

   public static class Builder {
      private final List<String> refTokens = new ArrayList();

      public JSONPointer.Builder append(int var1) {
         this.refTokens.add(String.valueOf(var1));
         return this;
      }

      public JSONPointer build() {
         return new JSONPointer(this.refTokens);
      }

      public JSONPointer.Builder append(String var1) {
         if (var1 == null) {
            throw new NullPointerException("token cannot be null");
         } else {
            this.refTokens.add(var1);
            return this;
         }
      }
   }
}
