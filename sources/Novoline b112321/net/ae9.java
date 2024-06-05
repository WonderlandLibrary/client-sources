package net;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.adZ;
import net.as0;

public final class ae9 {
   private static final List a = new CopyOnWriteArrayList();

   public static void a(adZ var0) {
      a.add(var0);
   }

   public static List a(as0 var0) {
      return (List)a.stream().filter(ae9::lambda$getSettingsByMod$0).collect(Collectors.toCollection(CopyOnWriteArrayList::<init>));
   }

   public static List a() {
      return a;
   }

   private static boolean lambda$getSettingsByMod$0(as0 var0, adZ var1) {
      return var1.Q().equals(var0);
   }
}
