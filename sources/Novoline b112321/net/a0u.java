package net;

import net.acE;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMapperFactory;

public class a0u {
   private static acE[] b;

   public static ObjectMapper a(ObjectMapperFactory var0, Class var1) {
      return var0.getMapper(var1);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[2]);
      }

   }
}
