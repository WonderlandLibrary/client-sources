package net;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.acE;

public class ay8 {
   private static acE[] b;

   public static boolean b(Optional var0) {
      return var0.isPresent();
   }

   public static Object a(Optional var0) {
      return var0.get();
   }

   public static Optional a(Object var0) {
      return Optional.ofNullable(var0);
   }

   public static void a(Optional var0, Consumer var1) {
      var0.ifPresent(var1);
   }

   public static Optional a() {
      return Optional.empty();
   }

   public static Optional b(Object var0) {
      return Optional.of(var0);
   }

   public static Optional a(Optional var0, Function var1) {
      return var0.map(var1);
   }

   public static Object a(Optional var0, Object var1) {
      return var0.orElse(var1);
   }

   public static Object a(Optional var0, Supplier var1) {
      return var0.orElseThrow(var1);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[4]);
      }

   }
}
