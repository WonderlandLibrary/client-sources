package net;

import com.google.common.reflect.TypeToken;
import java.util.function.Predicate;
import net.ZV;
import net.agh;
import net.lZ;

final class aje {
   private final Predicate b;
   private final ZV a;

   private aje(Predicate var1, ZV var2) {
      this.b = var1;
      this.a = var2;
   }

   private aje(TypeToken var1, ZV var2) {
      this((Predicate)(new lZ(var1)), var2);
   }

   aje(TypeToken var1, ZV var2, agh var3) {
      this(var1, var2);
   }

   aje(Predicate var1, ZV var2, agh var3) {
      this(var1, var2);
   }

   static Predicate b(aje var0) {
      return var0.b;
   }

   static ZV a(aje var0) {
      return var0.a;
   }
}
