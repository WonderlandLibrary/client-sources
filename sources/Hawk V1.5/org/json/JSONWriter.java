package org.json;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class JSONWriter {
   private boolean comma = false;
   private final JSONObject[] stack = new JSONObject[200];
   private int top = 0;
   private static final int maxdepth = 200;
   protected Appendable writer;
   protected char mode = 'i';

   public JSONWriter key(String var1) throws JSONException {
      if (var1 == null) {
         throw new JSONException("Null key.");
      } else if (this.mode == 'k') {
         try {
            JSONObject var2 = this.stack[this.top - 1];
            if (var2.has(var1)) {
               throw new JSONException(String.valueOf((new StringBuilder()).append("Duplicate key \"").append(var1).append("\"")));
            } else {
               var2.put(var1, true);
               if (this.comma) {
                  this.writer.append(',');
               }

               this.writer.append(JSONObject.quote(var1));
               this.writer.append(':');
               this.comma = false;
               this.mode = 'o';
               return this;
            }
         } catch (IOException var3) {
            throw new JSONException(var3);
         }
      } else {
         throw new JSONException("Misplaced key.");
      }
   }

   public static String valueToString(Object var0) throws JSONException {
      if (var0 != null && !var0.equals((Object)null)) {
         String var5;
         if (var0 instanceof JSONString) {
            try {
               var5 = ((JSONString)var0).toJSONString();
            } catch (Exception var3) {
               throw new JSONException(var3);
            }

            if (var5 != null) {
               return var5;
            } else {
               throw new JSONException(String.valueOf((new StringBuilder()).append("Bad value from toJSONString: ").append(var5)));
            }
         } else if (var0 instanceof Number) {
            var5 = JSONObject.numberToString((Number)var0);
            return JSONObject.NUMBER_PATTERN.matcher(var5).matches() ? var5 : JSONObject.quote(var5);
         } else if (!(var0 instanceof Boolean) && !(var0 instanceof JSONObject) && !(var0 instanceof JSONArray)) {
            if (var0 instanceof Map) {
               Map var4 = (Map)var0;
               return (new JSONObject(var4)).toString();
            } else if (var0 instanceof Collection) {
               Collection var1 = (Collection)var0;
               return (new JSONArray(var1)).toString();
            } else if (var0.getClass().isArray()) {
               return (new JSONArray(var0)).toString();
            } else {
               return var0 instanceof Enum ? JSONObject.quote(((Enum)var0).name()) : JSONObject.quote(var0.toString());
            }
         } else {
            return var0.toString();
         }
      } else {
         return "null";
      }
   }

   public JSONWriter array() throws JSONException {
      if (this.mode != 'i' && this.mode != 'o' && this.mode != 'a') {
         throw new JSONException("Misplaced array.");
      } else {
         this.push((JSONObject)null);
         this.append("[");
         this.comma = false;
         return this;
      }
   }

   public JSONWriter endObject() throws JSONException {
      return this.end('k', '}');
   }

   private void pop(char var1) throws JSONException {
      if (this.top <= 0) {
         throw new JSONException("Nesting error.");
      } else {
         int var2 = this.stack[this.top - 1] == null ? 97 : 107;
         if (var2 != var1) {
            throw new JSONException("Nesting error.");
         } else {
            --this.top;
            this.mode = (char)(this.top == 0 ? 100 : (this.stack[this.top - 1] == null ? 97 : 107));
         }
      }
   }

   private void push(JSONObject var1) throws JSONException {
      if (this.top >= 200) {
         throw new JSONException("Nesting too deep.");
      } else {
         this.stack[this.top] = var1;
         this.mode = (char)(var1 == null ? 97 : 107);
         ++this.top;
      }
   }

   private JSONWriter end(char var1, char var2) throws JSONException {
      if (this.mode != var1) {
         throw new JSONException(var1 == 'a' ? "Misplaced endArray." : "Misplaced endObject.");
      } else {
         this.pop(var1);

         try {
            this.writer.append(var2);
         } catch (IOException var4) {
            throw new JSONException(var4);
         }

         this.comma = true;
         return this;
      }
   }

   public JSONWriter value(double var1) throws JSONException {
      return this.value(var1);
   }

   public JSONWriter value(boolean var1) throws JSONException {
      return this.append(var1 ? "true" : "false");
   }

   private JSONWriter append(String var1) throws JSONException {
      if (var1 == null) {
         throw new JSONException("Null pointer");
      } else if (this.mode != 'o' && this.mode != 'a') {
         throw new JSONException("Value out of sequence.");
      } else {
         try {
            if (this.comma && this.mode == 'a') {
               this.writer.append(',');
            }

            this.writer.append(var1);
         } catch (IOException var3) {
            throw new JSONException(var3);
         }

         if (this.mode == 'o') {
            this.mode = 'k';
         }

         this.comma = true;
         return this;
      }
   }

   public JSONWriter(Appendable var1) {
      this.writer = var1;
   }

   public JSONWriter object() throws JSONException {
      if (this.mode == 'i') {
         this.mode = 'o';
      }

      if (this.mode != 'o' && this.mode != 'a') {
         throw new JSONException("Misplaced object.");
      } else {
         this.append("{");
         this.push(new JSONObject());
         this.comma = false;
         return this;
      }
   }

   public JSONWriter value(Object var1) throws JSONException {
      return this.append(valueToString(var1));
   }

   public JSONWriter endArray() throws JSONException {
      return this.end('a', ']');
   }

   public JSONWriter value(long var1) throws JSONException {
      return this.append(Long.toString(var1));
   }
}
