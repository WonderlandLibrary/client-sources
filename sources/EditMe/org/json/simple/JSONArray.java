package org.json.simple;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSONArray extends ArrayList implements List, JSONAware, JSONStreamAware {
   private static final long serialVersionUID = 3957988303675231981L;

   public static void writeJSONString(List var0, Writer var1) throws IOException {
      if (var0 == null) {
         var1.write("null");
      } else {
         boolean var2 = true;
         Iterator var3 = var0.iterator();
         var1.write(91);

         while(var3.hasNext()) {
            if (var2) {
               var2 = false;
            } else {
               var1.write(44);
            }

            Object var4 = var3.next();
            if (var4 == null) {
               var1.write("null");
            } else {
               JSONValue.writeJSONString(var4, var1);
            }
         }

         var1.write(93);
      }
   }

   public void writeJSONString(Writer var1) throws IOException {
      writeJSONString(this, var1);
   }

   public static String toJSONString(List var0) {
      if (var0 == null) {
         return "null";
      } else {
         boolean var1 = true;
         StringBuffer var2 = new StringBuffer();
         Iterator var3 = var0.iterator();
         var2.append('[');

         while(var3.hasNext()) {
            if (var1) {
               var1 = false;
            } else {
               var2.append(',');
            }

            Object var4 = var3.next();
            if (var4 == null) {
               var2.append("null");
            } else {
               var2.append(JSONValue.toJSONString(var4));
            }
         }

         var2.append(']');
         return var2.toString();
      }
   }

   public String toJSONString() {
      return toJSONString(this);
   }

   public String toString() {
      return this.toJSONString();
   }
}
