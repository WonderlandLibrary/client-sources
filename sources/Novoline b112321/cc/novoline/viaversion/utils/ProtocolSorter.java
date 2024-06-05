package cc.novoline.viaversion.utils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import viaversion.viaversion.api.protocol.ProtocolVersion;

public class ProtocolSorter {
   private static final LinkedList protocolVersions = new LinkedList();
   private static int count = 0;

   public static LinkedList getProtocolVersions() {
      return protocolVersions;
   }

   static {
      for(Field var5 : ProtocolVersion.class.getDeclaredFields()) {
         if(var5.getType().equals(ProtocolVersion.class)) {
            ++count;

            try {
               ProtocolVersion var6 = (ProtocolVersion)var5.get((Object)null);
               if(count >= 8 && !var6.getName().equals("UNKNOWN")) {
                  getProtocolVersions().add(var6);
               }
            } catch (IllegalAccessException var7) {
               var7.printStackTrace();
            }
         }
      }

      Collections.reverse(getProtocolVersions());
   }

   private static IllegalAccessException a(IllegalAccessException var0) {
      return var0;
   }
}
