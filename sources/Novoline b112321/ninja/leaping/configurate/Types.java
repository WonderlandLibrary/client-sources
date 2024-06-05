package ninja.leaping.configurate;

import net.acE;
import ninja.leaping.configurate.ValueType;

public final class Types {
   public static String asString(Object var0) {
      acE[] var1 = ValueType.b();
      return var0 == null?null:var0.toString();
   }

   public static String strictAsString(Object var0) {
      acE[] var1 = ValueType.b();
      return var0 instanceof String?(String)var0:null;
   }

   public static Float asFloat(Object var0) {
      acE[] var1 = ValueType.b();
      if(var0 == null) {
         return null;
      } else if(var0 instanceof Float) {
         return (Float)var0;
      } else if(var0 instanceof Integer) {
         return Float.valueOf(((Number)var0).floatValue());
      } else {
         Object var10000 = var0;

         try {
            return Float.valueOf(Float.parseFloat(var10000.toString()));
         } catch (IllegalArgumentException var3) {
            return null;
         }
      }
   }

   public static Float strictAsFloat(Object var0) {
      acE[] var1 = ValueType.b();
      return var0 == null?null:(!(var0 instanceof Float) && !(var0 instanceof Integer)?null:Float.valueOf(((Number)var0).floatValue()));
   }

   public static Double asDouble(Object var0) {
      acE[] var1 = ValueType.b();
      if(var0 == null) {
         return null;
      } else if(var0 instanceof Double) {
         return (Double)var0;
      } else if(!(var0 instanceof Integer) && !(var0 instanceof Long) && !(var0 instanceof Float)) {
         Object var10000 = var0;

         try {
            return Double.valueOf(Double.parseDouble(var10000.toString()));
         } catch (IllegalArgumentException var3) {
            return null;
         }
      } else {
         return Double.valueOf(((Number)var0).doubleValue());
      }
   }

   public static Double strictAsDouble(Object var0) {
      acE[] var1 = ValueType.b();
      return var0 == null?null:(!(var0 instanceof Double) && !(var0 instanceof Float) && !(var0 instanceof Integer) && !(var0 instanceof Long)?null:Double.valueOf(((Number)var0).doubleValue()));
   }

   public static Integer asInt(Object var0) {
      acE[] var1 = ValueType.b();
      if(var0 == null) {
         return null;
      } else if(var0 instanceof Integer) {
         return (Integer)var0;
      } else {
         if(var0 instanceof Float || var0 instanceof Double) {
            double var2 = ((Number)var0).doubleValue();
            if(var2 == Math.floor(var2)) {
               return Integer.valueOf((int)var2);
            }
         }

         Object var10000 = var0;

         try {
            return Integer.valueOf(Integer.parseInt(var10000.toString()));
         } catch (IllegalArgumentException var4) {
            return null;
         }
      }
   }

   public static Integer strictAsInt(Object var0) {
      acE[] var1 = ValueType.b();
      return var0 == null?null:(var0 instanceof Integer?(Integer)var0:null);
   }

   public static Long asLong(Object var0) {
      acE[] var1 = ValueType.b();
      if(var0 == null) {
         return null;
      } else if(var0 instanceof Long) {
         return (Long)var0;
      } else if(var0 instanceof Integer) {
         return Long.valueOf(((Number)var0).longValue());
      } else {
         if(var0 instanceof Float || var0 instanceof Double) {
            double var2 = ((Number)var0).doubleValue();
            if(var2 == Math.floor(var2)) {
               return Long.valueOf((long)var2);
            }
         }

         Object var10000 = var0;

         try {
            return Long.valueOf(Long.parseLong(var10000.toString()));
         } catch (IllegalArgumentException var4) {
            return null;
         }
      }
   }

   public static Long strictAsLong(Object var0) {
      acE[] var1 = ValueType.b();
      return var0 == null?null:(var0 instanceof Long?(Long)var0:(var0 instanceof Integer?Long.valueOf(((Number)var0).longValue()):null));
   }

   public static Boolean asBoolean(Object var0) {
      acE[] var1 = ValueType.b();
      if(var0 == null) {
         return null;
      } else if(var0 instanceof Boolean) {
         return (Boolean)var0;
      } else if(var0 instanceof Number) {
         return Boolean.valueOf(!var0.equals(Integer.valueOf(0)));
      } else {
         String var2 = var0.toString();
         return !var2.equals("true") && !var2.equals("t") && !var2.equals("yes") && !var2.equals("y") && !var2.equals("1")?(!var2.equals("false") && !var2.equals("f") && !var2.equals("no") && !var2.equals("n") && !var2.equals("0")?null:Boolean.valueOf(false)):Boolean.valueOf(true);
      }
   }

   public static Boolean f(Object var0) {
      acE[] var1 = ValueType.b();
      return var0 == null?null:(var0 instanceof Boolean?(Boolean)var0:null);
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
