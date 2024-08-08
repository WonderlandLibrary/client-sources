package org.json.simple;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class JSONObject extends HashMap implements Map, JSONAware, JSONStreamAware {
   private static final long serialVersionUID = -503443796854799292L;

   public JSONObject() {
   }

   public JSONObject(Map var1) {
      super(var1);
   }

   public static void writeJSONString(Map var0, Writer var1) throws IOException {
      if (var0 == null) {
         var1.write("null");
      } else {
         boolean var2 = true;
         Iterator var3 = var0.entrySet().iterator();
         var1.write(123);

         while(var3.hasNext()) {
            if (var2) {
               var2 = false;
            } else {
               var1.write(44);
            }

            Entry var4 = (Entry)var3.next();
            var1.write(34);
            var1.write(escape(String.valueOf(var4.getKey())));
            var1.write(34);
            var1.write(58);
            JSONValue.writeJSONString(var4.getValue(), var1);
         }

         var1.write(125);
      }
   }

   public void writeJSONString(Writer var1) throws IOException {
      writeJSONString(this, var1);
   }

   public static String toJSONString(Map var0) {
      if (var0 == null) {
         return "null";
      } else {
         StringBuffer var1 = new StringBuffer();
         boolean var2 = true;
         Iterator var3 = var0.entrySet().iterator();
         var1.append('{');

         while(var3.hasNext()) {
            if (var2) {
               var2 = false;
            } else {
               var1.append(',');
            }

            Entry var4 = (Entry)var3.next();
            toJSONString(String.valueOf(var4.getKey()), var4.getValue(), var1);
         }

         var1.append('}');
         return var1.toString();
      }
   }

   public String toJSONString() {
      return toJSONString(this);
   }

   private static String toJSONString(String var0, Object var1, StringBuffer var2) {
      var2.append('"');
      if (var0 == null) {
         var2.append("null");
      } else {
         JSONValue.escape(var0, var2);
      }

      var2.append('"').append(':');
      var2.append(JSONValue.toJSONString(var1));
      return var2.toString();
   }

   public String toString() {
      return this.toJSONString();
   }

   public static String toString(String var0, Object var1) {
      StringBuffer var2 = new StringBuffer();
      toJSONString(var0, var1, var2);
      return var2.toString();
   }

   public static String escape(String var0) {
      return JSONValue.escape(var0);
   }
}
