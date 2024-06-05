package net;

import java.util.Optional;
import net.N0;
import viaversion.viaversion.api.entities.EntityType;

public class a2a {
   public static int a(N0 var0) {
      return var0.getId();
   }

   public static Optional a(int var0) {
      return N0.a(var0);
   }

   public static boolean b(N0 var0, EntityType var1) {
      return var0.is(var1);
   }

   public static boolean a(N0 var0, EntityType var1) {
      return var0.isOrHasParent(var1);
   }
}
