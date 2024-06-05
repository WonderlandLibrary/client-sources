package net;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

public class ag1 {
   private static final LinkedList b = new LinkedList();
   private static int a = 0;

   public static LinkedList a() {
      return b;
   }

   static {
      for(Field var5 : ProtocolVersion.class.getDeclaredFields()) {
         if(var5.getType().equals(ProtocolVersion.class)) {
            ++a;

            try {
               ProtocolVersion var6 = (ProtocolVersion)var5.get((Object)null);
               if(a >= 8 && !var6.getName().equals("UNKNOWN")) {
                  a().add(var6);
               }
            } catch (IllegalAccessException var7) {
               var7.printStackTrace();
            }
         }
      }

      Collections.reverse(a());
   }

   private static IllegalAccessException a(IllegalAccessException var0) {
      return var0;
   }
}
