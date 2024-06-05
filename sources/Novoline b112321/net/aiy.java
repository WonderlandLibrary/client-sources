package net;

import java.util.function.Supplier;
import net.Sh;
import net.Y9;
import net.acH;
import net.amQ;
import org.jetbrains.annotations.NotNull;

public interface aiy extends Supplier {
   Object get();

   boolean a();

   static default aiy d(@NotNull Supplier var0) {
      return new Y9(var0);
   }

   static default aiy c(@NotNull Supplier var0) {
      return new Sh(var0);
   }

   static default aiy a(@NotNull Supplier var0) {
      return new acH(var0);
   }

   static default aiy b(@NotNull Supplier var0) {
      return new amQ(var0);
   }
}
