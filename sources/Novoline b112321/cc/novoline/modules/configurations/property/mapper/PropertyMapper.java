package cc.novoline.modules.configurations.property.mapper;

import cc.novoline.modules.configurations.property.Property;
import cc.novoline.modules.configurations.property.mapper.PropertyFieldData;
import java.lang.reflect.Field;
import java.util.Map;
import net.X9;
import net.acE;
import ninja.leaping.configurate.objectmapping.ObjectMapper;

public final class PropertyMapper extends ObjectMapper {
   private static String[] d;

   protected PropertyMapper(Class var1) throws X9 {
      super(var1);
   }

   protected void collectFields(Map var1, Class var2) throws X9 {
      boolean var4 = false;
      b();
      Field[] var5 = var2.getDeclaredFields();
      int var6 = var5.length;
      int var7 = 0;
      if(var7 < var6) {
         Field var8 = var5[var7];
         if(Property.class.isAssignableFrom(var8.getType()) && var8.isAnnotationPresent(cc.novoline.modules.configurations.annotation.Property.class)) {
            var4 = true;
            var8.setAccessible(true);
            PropertyFieldData var9 = new PropertyFieldData(var8, (String)null);
            cc.novoline.modules.configurations.annotation.Property var10 = (cc.novoline.modules.configurations.annotation.Property)var8.getAnnotation(cc.novoline.modules.configurations.annotation.Property.class);
            String var11 = mapPropertyPath(var8, var10);
            if(!var1.containsKey(var11)) {
               var1.put(var11, var9);
            }
         }

         ++var7;
      }

      if(!var4) {
         super.collectFields(var1, var2);
      }

      if(acE.b() == null) {
         b(new String[2]);
      }

   }

   public static String mapPropertyPath(Field var0, cc.novoline.modules.configurations.annotation.Property var1) {
      return var1.value();
   }

   public static void b(String[] var0) {
      d = var0;
   }

   public static String[] b() {
      return d;
   }

   private static X9 a(X9 var0) {
      return var0;
   }

   static {
      if(b() != null) {
         b(new String[5]);
      }

   }
}
