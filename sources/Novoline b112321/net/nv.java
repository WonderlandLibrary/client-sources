package net;

import java.util.Optional;
import net.Dl;
import viaversion.viaversion.api.entities.EntityType;

public class nv {
   public static Optional a(int var0) {
      return Dl.a(var0);
   }

   public static boolean a(Dl var0, EntityType var1) {
      return var0.is(var1);
   }

   public static int a(Dl var0) {
      return var0.getId();
   }
}
