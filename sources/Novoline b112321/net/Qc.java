package net;

import java.lang.reflect.Field;
import net.optifine.ReflectorRaw;

public class Qc {
   public static Field a(Class var0, Field var1, Class var2, int var3) {
      return ReflectorRaw.getFieldAfter(var0, var1, var2, var3);
   }

   public static Field[] a(Object var0, Field[] var1, Class var2, Object var3) {
      return ReflectorRaw.getFields(var0, var1, var2, var3);
   }
}
