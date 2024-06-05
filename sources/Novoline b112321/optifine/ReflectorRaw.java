package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import optifine.MatchBlock;
import optifine.Reflector;
import optifine.ReflectorField;

public class ReflectorRaw {
   public static Field getField(Class param0, Class param1) {
      // $FF: Couldn't be decompiled
   }

   public static Field[] getFields(Class var0, Class var1) {
      Class var10000 = var0;

      try {
         Field[] var2 = var10000.getDeclaredFields();
         return getFields(var2, var1);
      } catch (Exception var3) {
         return null;
      }
   }

   public static Field[] getFields(Field[] param0, Class param1) {
      // $FF: Couldn't be decompiled
   }

   public static Field[] getFieldsAfter(Class var0, Field var1, Class var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      Class var10000 = var0;

      try {
         Field[] var4 = var10000.getDeclaredFields();
         List var5 = Arrays.asList(var4);
         int var6 = var5.indexOf(var1);
         if(var6 < 0) {
            return new Field[0];
         } else {
            List var7 = var5.subList(var6 + 1, var5.size());
            Field[] var8 = (Field[])((Field[])((Field[])var7.toArray(new Field[var7.size()])));
            return getFields(var8, var2);
         }
      } catch (Exception var9) {
         return null;
      }
   }

   public static Field[] getFields(Object param0, Field[] param1, Class param2, Object param3) {
      // $FF: Couldn't be decompiled
   }

   public static Field getField(Class var0, Class var1, int var2) {
      MatchBlock.b();
      Field[] var4 = getFields(var0, var1);
      return var2 >= 0 && var2 < var4.length?var4[var2]:null;
   }

   public static Field getFieldAfter(Class var0, Field var1, Class var2, int var3) {
      MatchBlock.b();
      Field[] var5 = getFieldsAfter(var0, var1, var2);
      return var3 >= 0 && var3 < var5.length?var5[var3]:null;
   }

   public static Object getFieldValue(Object var0, Class var1, Class var2) {
      MatchBlock.b();
      ReflectorField var4 = getReflectorField(var1, var2);
      return var4 == null?null:(!var4.exists()?null:Reflector.getFieldValue(var0, var4));
   }

   public static Object getFieldValue(Object var0, Class var1, Class var2, int var3) {
      MatchBlock.b();
      ReflectorField var5 = getReflectorField(var1, var2, var3);
      return var5 == null?null:(!var5.exists()?null:Reflector.getFieldValue(var0, var5));
   }

   public static boolean setFieldValue(Object var0, Class var1, Class var2, Object var3) {
      MatchBlock.b();
      ReflectorField var5 = getReflectorField(var1, var2);
      return var5 == null?false:(!var5.exists()?false:Reflector.setFieldValue(var0, var5, var3));
   }

   public static boolean setFieldValue(Object var0, Class var1, Class var2, int var3, Object var4) {
      MatchBlock.b();
      ReflectorField var6 = getReflectorField(var1, var2, var3);
      return var6 == null?false:(!var6.exists()?false:Reflector.setFieldValue(var0, var6, var4));
   }

   public static ReflectorField getReflectorField(Class var0, Class var1) {
      Field var2 = getField(var0, var1);
      return null;
   }

   public static ReflectorField getReflectorField(Class var0, Class var1, int var2) {
      Field var3 = getField(var0, var1, var2);
      return null;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
