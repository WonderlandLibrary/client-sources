package org.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JSONArray implements Iterable<Object> {
   private final ArrayList<Object> myArrayList;

   public boolean isNull(int var1) {
      return JSONObject.NULL.equals(this.opt(var1));
   }

   public JSONArray put(Collection<?> var1) {
      return this.put((Object)(new JSONArray(var1)));
   }

   public String optString(int var1) {
      return this.optString(var1, "");
   }

   public BigInteger optBigInteger(int var1, BigInteger var2) {
      Object var3 = this.opt(var1);
      return JSONObject.objectToBigInteger(var3, var2);
   }

   public Writer write(Writer var1, int var2, int var3) throws JSONException {
      try {
         boolean var4 = false;
         int var5 = this.length();
         var1.write(91);
         if (var5 == 1) {
            try {
               JSONObject.writeValue(var1, this.myArrayList.get(0), var2, var3);
            } catch (Exception var10) {
               throw new JSONException("Unable to write JSONArray value at index: 0", var10);
            }
         } else if (var5 != 0) {
            int var6 = var3 + var2;

            for(int var7 = 0; var7 < var5; ++var7) {
               if (var4) {
                  var1.write(44);
               }

               if (var2 > 0) {
                  var1.write(10);
               }

               JSONObject.indent(var1, var6);

               try {
                  JSONObject.writeValue(var1, this.myArrayList.get(var7), var2, var6);
               } catch (Exception var9) {
                  throw new JSONException(String.valueOf((new StringBuilder()).append("Unable to write JSONArray value at index: ").append(var7)), var9);
               }

               var4 = true;
            }

            if (var2 > 0) {
               var1.write(10);
            }

            JSONObject.indent(var1, var3);
         }

         var1.write(93);
         return var1;
      } catch (IOException var11) {
         throw new JSONException(var11);
      }
   }

   public String getString(int var1) throws JSONException {
      Object var2 = this.get(var1);
      if (var2 instanceof String) {
         return (String)var2;
      } else {
         throw wrongValueFormatException(var1, "String", (Throwable)null);
      }
   }

   public int length() {
      return this.myArrayList.size();
   }

   public <E extends Enum<E>> E getEnum(Class<E> var1, int var2) throws JSONException {
      Enum var3 = this.optEnum(var1, var2);
      if (var3 == null) {
         throw wrongValueFormatException(var2, String.valueOf((new StringBuilder()).append("enum of type ").append(JSONObject.quote(var1.getSimpleName()))), (Throwable)null);
      } else {
         return var3;
      }
   }

   public JSONArray put(float var1) throws JSONException {
      return this.put((Object)var1);
   }

   public Number getNumber(int var1) throws JSONException {
      Object var2 = this.get(var1);

      try {
         return var2 instanceof Number ? (Number)var2 : JSONObject.stringToNumber(var2.toString());
      } catch (Exception var4) {
         throw wrongValueFormatException(var1, "number", var4);
      }
   }

   public boolean similar(Object var1) {
      if (!(var1 instanceof JSONArray)) {
         return false;
      } else {
         int var2 = this.length();
         if (var2 != ((JSONArray)var1).length()) {
            return false;
         } else {
            for(int var3 = 0; var3 < var2; ++var3) {
               Object var4 = this.myArrayList.get(var3);
               Object var5 = ((JSONArray)var1).myArrayList.get(var3);
               if (var4 != var5) {
                  if (var4 == null) {
                     return false;
                  }

                  if (var4 instanceof JSONObject) {
                     if (!((JSONObject)var4).similar(var5)) {
                        return false;
                     }
                  } else if (var4 instanceof JSONArray) {
                     if (!((JSONArray)var4).similar(var5)) {
                        return false;
                     }
                  } else if (!var4.equals(var5)) {
                     return false;
                  }
               }
            }

            return true;
         }
      }
   }

   public Object get(int var1) throws JSONException {
      Object var2 = this.opt(var1);
      if (var2 == null) {
         throw new JSONException(String.valueOf((new StringBuilder()).append("JSONArray[").append(var1).append("] not found.")));
      } else {
         return var2;
      }
   }

   public float getFloat(int var1) throws JSONException {
      Object var2 = this.get(var1);
      if (var2 instanceof Number) {
         return (Float)var2;
      } else {
         try {
            return Float.parseFloat(var2.toString());
         } catch (Exception var4) {
            throw wrongValueFormatException(var1, "float", var4);
         }
      }
   }

   public JSONArray put(int var1, Object var2) throws JSONException {
      if (var1 < 0) {
         throw new JSONException(String.valueOf((new StringBuilder()).append("JSONArray[").append(var1).append("] not found.")));
      } else if (var1 < this.length()) {
         JSONObject.testValidity(var2);
         this.myArrayList.set(var1, var2);
         return this;
      } else if (var1 == this.length()) {
         return this.put(var2);
      } else {
         this.myArrayList.ensureCapacity(var1 + 1);

         while(var1 != this.length()) {
            this.myArrayList.add(JSONObject.NULL);
         }

         return this.put(var2);
      }
   }

   public JSONArray put(int var1, boolean var2) throws JSONException {
      return this.put(var1, (Object)(var2 ? Boolean.TRUE : Boolean.FALSE));
   }

   public Object optQuery(JSONPointer var1) {
      try {
         return var1.queryFrom(this);
      } catch (JSONPointerException var3) {
         return null;
      }
   }

   public JSONObject toJSONObject(JSONArray var1) throws JSONException {
      if (var1 != null && !var1.isEmpty() && !this.isEmpty()) {
         JSONObject var2 = new JSONObject(var1.length());

         for(int var3 = 0; var3 < var1.length(); ++var3) {
            var2.put(var1.getString(var3), this.opt(var3));
         }

         return var2;
      } else {
         return null;
      }
   }

   public JSONArray put(int var1, Collection<?> var2) throws JSONException {
      return this.put(var1, (Object)(new JSONArray(var2)));
   }

   public BigInteger getBigInteger(int var1) throws JSONException {
      Object var2 = this.get(var1);
      BigInteger var3 = JSONObject.objectToBigInteger(var2, (BigInteger)null);
      if (var3 == null) {
         throw wrongValueFormatException(var1, "BigInteger", var2, (Throwable)null);
      } else {
         return var3;
      }
   }

   public boolean optBoolean(int var1, boolean var2) {
      try {
         return this.getBoolean(var1);
      } catch (Exception var4) {
         return var2;
      }
   }

   public JSONArray put(int var1, double var2) throws JSONException {
      return this.put(var1, (Object)var2);
   }

   public long optLong(int var1, long var2) {
      Number var4 = this.optNumber(var1, (Number)null);
      return var4 == null ? var2 : var4.longValue();
   }

   public int optInt(int var1) {
      return this.optInt(var1, 0);
   }

   public long optLong(int var1) {
      return this.optLong(var1, 0L);
   }

   public JSONArray(JSONTokener var1) throws JSONException {
      this();
      if (var1.nextClean() != '[') {
         throw var1.syntaxError("A JSONArray text must start with '['");
      } else {
         char var2 = var1.nextClean();
         if (var2 == 0) {
            throw var1.syntaxError("Expected a ',' or ']'");
         } else if (var2 != ']') {
            var1.back();

            while(true) {
               if (var1.nextClean() == ',') {
                  var1.back();
                  this.myArrayList.add(JSONObject.NULL);
               } else {
                  var1.back();
                  this.myArrayList.add(var1.nextValue());
               }

               switch(var1.nextClean()) {
               case '\u0000':
                  throw var1.syntaxError("Expected a ',' or ']'");
               case ',':
                  var2 = var1.nextClean();
                  if (var2 == 0) {
                     throw var1.syntaxError("Expected a ',' or ']'");
                  }

                  if (var2 == ']') {
                     return;
                  }

                  var1.back();
                  break;
               case ']':
                  return;
               default:
                  throw var1.syntaxError("Expected a ',' or ']'");
               }
            }
         }
      }
   }

   public JSONArray put(Map<?, ?> var1) {
      return this.put((Object)(new JSONObject(var1)));
   }

   public String toString(int var1) throws JSONException {
      StringWriter var2 = new StringWriter();
      synchronized(var2.getBuffer()) {
         return this.write(var2, var1, 0).toString();
      }
   }

   public JSONArray(Object var1) throws JSONException {
      this();
      if (!var1.getClass().isArray()) {
         throw new JSONException("JSONArray initial value should be a string or collection or array.");
      } else {
         int var2 = Array.getLength(var1);
         this.myArrayList.ensureCapacity(var2);

         for(int var3 = 0; var3 < var2; ++var3) {
            this.put(JSONObject.wrap(Array.get(var1, var3)));
         }

      }
   }

   public boolean isEmpty() {
      return this.myArrayList.isEmpty();
   }

   public JSONArray put(int var1) {
      return this.put((Object)var1);
   }

   public JSONArray optJSONArray(int var1) {
      Object var2 = this.opt(var1);
      return var2 instanceof JSONArray ? (JSONArray)var2 : null;
   }

   public long getLong(int var1) throws JSONException {
      Object var2 = this.get(var1);
      if (var2 instanceof Number) {
         return ((Number)var2).longValue();
      } else {
         try {
            return Long.parseLong(var2.toString());
         } catch (Exception var4) {
            throw wrongValueFormatException(var1, "long", var4);
         }
      }
   }

   public <E extends Enum<E>> E optEnum(Class<E> var1, int var2, E var3) {
      try {
         Object var4 = this.opt(var2);
         if (JSONObject.NULL.equals(var4)) {
            return var3;
         } else if (var1.isAssignableFrom(var4.getClass())) {
            Enum var5 = (Enum)var4;
            return var5;
         } else {
            return Enum.valueOf(var1, var4.toString());
         }
      } catch (IllegalArgumentException var6) {
         return var3;
      } catch (NullPointerException var7) {
         return var3;
      }
   }

   public Object query(JSONPointer var1) {
      return var1.queryFrom(this);
   }

   public JSONArray put(double var1) throws JSONException {
      return this.put((Object)var1);
   }

   public boolean getBoolean(int var1) throws JSONException {
      Object var2 = this.get(var1);
      if (!var2.equals(Boolean.FALSE) && (!(var2 instanceof String) || !((String)var2).equalsIgnoreCase("false"))) {
         if (!var2.equals(Boolean.TRUE) && (!(var2 instanceof String) || !((String)var2).equalsIgnoreCase("true"))) {
            throw wrongValueFormatException(var1, "boolean", (Throwable)null);
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public double optDouble(int var1, double var2) {
      Number var4 = this.optNumber(var1, (Number)null);
      if (var4 == null) {
         return var2;
      } else {
         double var5 = var4.doubleValue();
         return var5;
      }
   }

   public Object remove(int var1) {
      return var1 >= 0 && var1 < this.length() ? this.myArrayList.remove(var1) : null;
   }

   private static JSONException wrongValueFormatException(int var0, String var1, Throwable var2) {
      return new JSONException(String.valueOf((new StringBuilder()).append("JSONArray[").append(var0).append("] is not a ").append(var1).append(".")), var2);
   }

   public JSONArray put(int var1, long var2) throws JSONException {
      return this.put(var1, (Object)var2);
   }

   public JSONArray put(boolean var1) {
      return this.put((Object)(var1 ? Boolean.TRUE : Boolean.FALSE));
   }

   public String optString(int var1, String var2) {
      Object var3 = this.opt(var1);
      return JSONObject.NULL.equals(var3) ? var2 : var3.toString();
   }

   public JSONArray put(Object var1) {
      JSONObject.testValidity(var1);
      this.myArrayList.add(var1);
      return this;
   }

   public Writer write(Writer var1) throws JSONException {
      return this.write(var1, 0, 0);
   }

   public BigDecimal getBigDecimal(int var1) throws JSONException {
      Object var2 = this.get(var1);
      BigDecimal var3 = JSONObject.objectToBigDecimal(var2, (BigDecimal)null);
      if (var3 == null) {
         throw wrongValueFormatException(var1, "BigDecimal", var2, (Throwable)null);
      } else {
         return var3;
      }
   }

   public JSONArray getJSONArray(int var1) throws JSONException {
      Object var2 = this.get(var1);
      if (var2 instanceof JSONArray) {
         return (JSONArray)var2;
      } else {
         throw wrongValueFormatException(var1, "JSONArray", (Throwable)null);
      }
   }

   public double optDouble(int var1) {
      return this.optDouble(var1, Double.NaN);
   }

   private static JSONException wrongValueFormatException(int var0, String var1, Object var2, Throwable var3) {
      return new JSONException(String.valueOf((new StringBuilder()).append("JSONArray[").append(var0).append("] is not a ").append(var1).append(" (").append(var2).append(").")), var3);
   }

   public JSONArray put(int var1, Map<?, ?> var2) throws JSONException {
      this.put(var1, (Object)(new JSONObject(var2)));
      return this;
   }

   public JSONArray put(long var1) {
      return this.put((Object)var1);
   }

   public int optInt(int var1, int var2) {
      Number var3 = this.optNumber(var1, (Number)null);
      return var3 == null ? var2 : var3.intValue();
   }

   public JSONArray put(int var1, float var2) throws JSONException {
      return this.put(var1, (Object)var2);
   }

   public JSONArray(Collection<?> var1) {
      if (var1 == null) {
         this.myArrayList = new ArrayList();
      } else {
         this.myArrayList = new ArrayList(var1.size());
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            Object var3 = var2.next();
            this.myArrayList.add(JSONObject.wrap(var3));
         }
      }

   }

   public double getDouble(int var1) throws JSONException {
      Object var2 = this.get(var1);
      if (var2 instanceof Number) {
         return ((Number)var2).doubleValue();
      } else {
         try {
            return Double.parseDouble(var2.toString());
         } catch (Exception var4) {
            throw wrongValueFormatException(var1, "double", var4);
         }
      }
   }

   public boolean optBoolean(int var1) {
      return this.optBoolean(var1, false);
   }

   public float optFloat(int var1, float var2) {
      Number var3 = this.optNumber(var1, (Number)null);
      if (var3 == null) {
         return var2;
      } else {
         float var4 = var3.floatValue();
         return var4;
      }
   }

   public List<Object> toList() {
      ArrayList var1 = new ArrayList(this.myArrayList.size());
      Iterator var2 = this.myArrayList.iterator();

      while(true) {
         while(var2.hasNext()) {
            Object var3 = var2.next();
            if (var3 != null && !JSONObject.NULL.equals(var3)) {
               if (var3 instanceof JSONArray) {
                  var1.add(((JSONArray)var3).toList());
               } else if (var3 instanceof JSONObject) {
                  var1.add(((JSONObject)var3).toMap());
               } else {
                  var1.add(var3);
               }
            } else {
               var1.add((Object)null);
            }
         }

         return var1;
      }
   }

   public BigDecimal optBigDecimal(int var1, BigDecimal var2) {
      Object var3 = this.opt(var1);
      return JSONObject.objectToBigDecimal(var3, var2);
   }

   public JSONArray put(int var1, int var2) throws JSONException {
      return this.put(var1, (Object)var2);
   }

   public String join(String var1) throws JSONException {
      int var2 = this.length();
      if (var2 == 0) {
         return "";
      } else {
         StringBuilder var3 = new StringBuilder(JSONObject.valueToString(this.myArrayList.get(0)));

         for(int var4 = 1; var4 < var2; ++var4) {
            var3.append(var1).append(JSONObject.valueToString(this.myArrayList.get(var4)));
         }

         return String.valueOf(var3);
      }
   }

   public Object query(String var1) {
      return this.query(new JSONPointer(var1));
   }

   public JSONArray(String var1) throws JSONException {
      this(new JSONTokener(var1));
   }

   public Iterator<Object> iterator() {
      return this.myArrayList.iterator();
   }

   public float optFloat(int var1) {
      return this.optFloat(var1, Float.NaN);
   }

   public Object opt(int var1) {
      return var1 >= 0 && var1 < this.length() ? this.myArrayList.get(var1) : null;
   }

   public int getInt(int var1) throws JSONException {
      Object var2 = this.get(var1);
      if (var2 instanceof Number) {
         return ((Number)var2).intValue();
      } else {
         try {
            return Integer.parseInt(var2.toString());
         } catch (Exception var4) {
            throw wrongValueFormatException(var1, "int", var4);
         }
      }
   }

   public Number optNumber(int var1, Number var2) {
      Object var3 = this.opt(var1);
      if (JSONObject.NULL.equals(var3)) {
         return var2;
      } else if (var3 instanceof Number) {
         return (Number)var3;
      } else if (var3 instanceof String) {
         try {
            return JSONObject.stringToNumber((String)var3);
         } catch (Exception var5) {
            return var2;
         }
      } else {
         return var2;
      }
   }

   public JSONArray() {
      this.myArrayList = new ArrayList();
   }

   public JSONObject getJSONObject(int var1) throws JSONException {
      Object var2 = this.get(var1);
      if (var2 instanceof JSONObject) {
         return (JSONObject)var2;
      } else {
         throw wrongValueFormatException(var1, "JSONObject", (Throwable)null);
      }
   }

   public JSONObject optJSONObject(int var1) {
      Object var2 = this.opt(var1);
      return var2 instanceof JSONObject ? (JSONObject)var2 : null;
   }

   public String toString() {
      try {
         return this.toString(0);
      } catch (Exception var2) {
         return null;
      }
   }

   public <E extends Enum<E>> E optEnum(Class<E> var1, int var2) {
      return this.optEnum(var1, var2, (Enum)null);
   }

   public Object optQuery(String var1) {
      return this.optQuery(new JSONPointer(var1));
   }

   public Number optNumber(int var1) {
      return this.optNumber(var1, (Number)null);
   }
}
